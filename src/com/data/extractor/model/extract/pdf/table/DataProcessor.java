package com.data.extractor.model.extract.pdf.table;


import com.data.extractor.model.beans.doc.text.positions.TextData;
import com.data.extractor.model.beans.extract.pdf.ExtractStatus;
import com.data.extractor.model.beans.template.info.table.Cell;
import com.data.extractor.model.beans.template.info.table.Column;
import com.data.extractor.model.beans.template.info.table.TableDataElement;
import com.data.extractor.model.beans.template.info.table.TableDataParser;
import org.apache.pdfbox.exceptions.CryptographyException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataProcessor {

    public TableDataParser processTable(TableDataParser tableDataParser,ExtractStatus extractStatus) throws IOException, CryptographyException {

        List<TableDataElement> tableDataElements;
        tableDataElements = tableDataParser.getTableDataElements();

        String cellValue[]=new String[2];
        int i;

        for (TableDataElement ta : tableDataElements) {

            List<Column> columnList = ta.getColumns();

            TextLocationRetriever retriever=new TextLocationRetriever();
            List<TextData> textDataList = retriever.getTableData(extractStatus,ta.getPageNumber() - 1);

            // where the first cursor should appear [table start position]
            i = getToStartPos(textDataList, ta);

            List<List<Cell>> listOfCellList=new ArrayList<List<Cell>>(columnList.size());

            textIterationLoop:
            for (; i < textDataList.size(); i++) {

                for (int a = 0; a < columnList.size(); a++) {

                    Column column = columnList.get(a);

                    /* terminate the whole loop if the cursor goes below the whole table */
                    if(isCursorOutOfTable(ta,textDataList,column,i)){
                        System.out.println("table out of reach");
                        break textIterationLoop;
                    }

                    Boolean isB4 = isCursorB4Column(ta, textDataList, column, i);

                    Boolean isAfter = isCursorAfterColumn(ta, textDataList, column, i);

                    Boolean isInside = isCursorInsideColumn(ta, textDataList, column, i);

                    if (isB4) {
                        Boolean isCellEmpty = isCellEmpty(ta, textDataList, column, i);

                        if (!isCellEmpty) {
                            i = iterateToCell(ta, textDataList, column, i);
                            cellValue = extractCell(ta, textDataList, column, i);
                            i = Integer.parseInt(cellValue[1]);
                        }else {
                            cellValue[0]="empty";
                        }
                    }

                    if (isInside) {
                        cellValue = extractCell(ta, textDataList, column, i);
                        i = Integer.parseInt(cellValue[1]);
                    }

                    if(isAfter){
                        cellValue[0]="empty";
                    }

                    Cell cell=new Cell();
                    cell.setValue(cellValue[0]);

                    if( a+1 > listOfCellList.size()){
                        List<Cell> testing123 = new ArrayList<Cell>();
                        listOfCellList.add(testing123);
                    }
                    List<Cell> testList=listOfCellList.get(a);
                    testList.add(cell);

                    if (a == columnList.size() - 1) {
                        a = -1;
                        i=iterateToNxtRow(ta, textDataList, column, i);
                    }

                }
            }

            for (int c=0 ; c < columnList.size() ; c++){
                Column column=columnList.get(c);
                column.setCellList(listOfCellList.get(c));
            }

            /*      ------------------------------------------------------                        */
        }

        return tableDataParser;
    }

    /* Method returns cursor position where the first column values start*/
    public static int getToStartPos(List<TextData> textDatas, TableDataElement ta){

        List<Column> columnList=ta.getColumns();
        int i=0;
        double startX=0;
        double startY=0;
        for(int j=0; j<columnList.size();j++){

            Column column=columnList.get(j);
            //Where the X extraction should take place
            startX = column.getMetaX1();
            //Where the Y extraction should take place
            startY = column.getMetaY1() + column.getMetaHeight();

            if(j==0){
                break;
            }
        }

        for(;i<textDatas.size();i++){
            TextData textData=textDatas.get(i);
            if( startX<textData.getxDirAdj() && startY <textData.getyDirAdj()){
                break;
            }

        }
        return i;
    }

    public static Boolean isCursorB4Column(TableDataElement ta,List<TextData> textDataList, Column column, int cursorPos){

        double startX=column.getMetaX1();
        //Where the Y extraction should end
        double endY = ta.getTotalY1() + ta.getTotalHeight();

        TextData textData=textDataList.get(cursorPos);
        return textData.getxDirAdj() < startX && textData.getyDirAdj() < endY ;

    }

    public static Boolean isCursorAfterColumn(TableDataElement ta,List<TextData> textDataList, Column column, int cursorPos){
        double startX=column.getMetaX1();
        //Where the X extraction should end
        double endX = column.getMetaX1() + column.getMetaWidth();
        //Where the Y extraction should end
        Double endY = ta.getTotalY1() + ta.getTotalHeight();

        TextData textData=textDataList.get(cursorPos);

        return  startX < textData.getxDirAdj() && textData.getyDirAdj() < endY && endX < textData.getxDirAdj() ;
    }

    public static Boolean isCursorInsideColumn(TableDataElement ta,List<TextData> textDataList,Column column, int cursorPos){
        double startX=column.getMetaX1();
        //Where the X extraction should end
        double endX = column.getMetaX1() + column.getMetaWidth();
        //Where the Y extraction should end
        double endY = ta.getTotalY1() + ta.getTotalHeight();

        TextData textData=textDataList.get(cursorPos);
        return  startX < textData.getxDirAdj() && textData.getxDirAdj() <endX &&
                textData.getyDirAdj() < endY ;
    }

    public static Boolean isCellEmpty(TableDataElement ta,List<TextData> textDataList,Column column, int cursorPos){
        double startX=column.getMetaX1();
        //Where the X extraction should end
        double endX = column.getMetaX1() + column.getMetaWidth();

        int i=cursorPos;

        for (;i<textDataList.size();i++){

            float iX=textDataList.get(i).getxDirAdj();
            float iXplus1=textDataList.get(i+1).getxDirAdj();

            // With in the same line not going backwards
            if(iX<iXplus1){

                //cursor+1 has passed the column and the column is empty
                if(endX < iXplus1) {
                    return true;
                }

                //cursor+1 is inside the column
                if(startX<iXplus1 && endX>iXplus1){
                    return false;
                }
            }

            // if cursor+1 retreats to the next line
            if(iXplus1 < iX){
                //check if the cursor+1 has gone backwards than the init start Pos, then mark cell as empty
                // last column empty check
                double initStartX=textDataList.get(cursorPos).getxDirAdj();
                if(iXplus1 < initStartX){
                    return true;
                }
            }

        }
        return false;
    }

    public static int iterateToCell(TableDataElement ta, List<TextData> textDataList, Column column, int cursorPos) {
        int i = cursorPos;
        double startX = column.getMetaX1();
        //Where the X extraction should end
        double endX = column.getMetaX1() + column.getMetaWidth();

        for (; i < textDataList.size(); i++) {

            float iXplus1 = textDataList.get(i + 1).getxDirAdj();

            //cursor+1 is inside the column
            if (startX < iXplus1 && endX > iXplus1) {
                return i+1;
            }

        }
        return i+1;
    }

    public static String[] extractCell(TableDataElement ta,List<TextData> textDataList,Column column, int cursorPos){

        String[] details=new String[2];

        StringBuilder sb=new StringBuilder("");
        //Where the X extraction should take place
        Double startX = column.getMetaX1();
        //Where the Y extraction should take place
        Double startY = column.getMetaY1() + column.getMetaHeight();
        //Where the X extraction should end
        Double endX = column.getMetaX1() + column.getMetaWidth();
        //Where the Y extraction should end
        Double endY = ta.getTotalY1() + ta.getTotalHeight();

        int i=cursorPos;
        for(;i<textDataList.size();i++){

            TextData te=textDataList.get(i);

            /*  If the cursor starts after the column starts
            _______________________________
            | I -->> Cursor Position       |  I -->> Cursor Position
            |______________________________|                                */
            if(startX < te.getxDirAdj() && startY < te.getyDirAdj() && te.getyDirAdj() < endY){
                /*  If the cursor is before the column ends
                _______________________________
                | I -->> Cursor Position       |
                |______________________________|                            */
                if (endX > te.getxDirAdj()) {
                    sb.append(te.getCharacter());

                }

                /*  If the cursor is before the column ends
                _______________________________
                |                              |  I -->> Cursor Position
                |______________________________|                            */
                if (endX < te.getxDirAdj()){
                    break;
                }
            }

            /*  If the cursor is before the column ends
                                     _______________________________
              I -->> Cursor Position|                              |
                                    |______________________________|        */
            else if(startX > te.getxDirAdj() && startY < te.getyDirAdj() && te.getyDirAdj() < endY){
                break;
            }

            /*  If the cursor is before the column ends
                _______________________________
                |                              |
                |______________________________|   <<--- Table Finished
                   I -->> Cursor Position                */
            else if(endY < te.getyDirAdj()){
                break;
            }
        }

        details[0]=sb.toString();
        details[1]= String.valueOf(i-1);

        /* To add the string as empty if the string contains only white space characters*/
        if( (details[0].trim().length() == 1 && details[0].charAt(0) == 160) || details[0].trim().isEmpty() )
            details[0]="empty";
        /* -----------------------------------------------------       */

        return details;
    }

    public static int iterateToNxtRow(TableDataElement ta,List<TextData> textDataList,Column column, int cursorPos){
        float initialEndX=textDataList.get(cursorPos).getxDirAdj();
        int i = cursorPos;

        for (; i < textDataList.size(); i++) {

            float iX = textDataList.get(i).getxDirAdj();
            float iXplus1 = textDataList.get(i + 1).getxDirAdj();

            /*  With in the is backing check if it has backed than the start Pos
                                     _______________________________
                                    |               I -->> Cursor  |   or       I -->> Cursor
              I+1-->> Cursor        |______________________________|      I+1-->> Cursor  */
            if (iXplus1 < iX ) {
                //cursor+1 is inside the column

                /*  Cursor+1 has backed that the initial position of cursor
                                                 _______________________________
                                                |         I initial -->> Cursor|
                                                |______________________________|
              _______________________________     _______________________________
              | I+1-->> Cursor               |   |                              |
              |______________________________|   |______________________________|  */
                if ( iXplus1 < initialEndX) {
                    return i+1;
                }
            }
        }

        return i;
    }

    public static boolean isCursorOutOfTable(TableDataElement ta,List<TextData> textDataList,Column column, int cursorPos){
        //Where the Y extraction should end
        Double endY = ta.getTotalY1() + ta.getTotalHeight();
        return endY < textDataList.get(cursorPos).getyDirAdj();
    }
}


