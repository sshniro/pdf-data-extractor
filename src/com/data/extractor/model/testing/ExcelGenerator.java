package com.data.extractor.model.testing;

import com.data.extractor.model.beans.template.info.ExtractedData;
import com.data.extractor.model.beans.template.info.image.ImageDataParser;
import com.data.extractor.model.beans.template.info.insert.InsertDataParser;
import com.data.extractor.model.beans.template.info.pattern.ColumnDataElement;
import com.data.extractor.model.beans.template.info.pattern.PatternDataElement;
import com.data.extractor.model.beans.template.info.pattern.PatternDataParser;
import com.data.extractor.model.beans.template.info.regex.RegexDataElement;
import com.data.extractor.model.beans.template.info.regex.RegexDataParser;
import com.data.extractor.model.beans.template.info.regex.RegexPairElement;
import com.data.extractor.model.beans.template.info.table.Column;
import com.data.extractor.model.beans.template.info.table.TableDataElement;
import com.data.extractor.model.beans.template.info.table.TableDataParser;
import com.data.extractor.model.beans.template.info.text.TextDataParser;
import com.data.extractor.model.data.access.layer.ExtractedDataDAO;
import com.data.extractor.model.db.connect.dbInitializer;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.net.UnknownHostException;
import java.util.*;

public class ExcelGenerator {

    private static int rowCount=1;

    public static void main(String[] args) throws UnknownHostException {
        MongoClient mongoClient = new MongoClient("localhost",27017);
        InsertDataParser insertDataParser= new InsertDataParser();
        ExtractedDataDAO extractedDataDAO = new ExtractedDataDAO(mongoClient);

        List<TableDataParser> tableDataParserList = extractedDataDAO.getTableRecord("26");
        List<RegexDataParser> regexDataParserList= extractedDataDAO.getRegexRecord("27");
        List<PatternDataParser> patternDataParserList = extractedDataDAO.getPatternRecord("25");

        //insertDataParser.setTableDataParser(tableDataParserList.get(0));

        //Blank workbook
        XSSFWorkbook workbook = new XSSFWorkbook();

        //Create a blank sheet
        XSSFSheet sheet = workbook.createSheet("Document Extraction Data");

        //addRecordsForRegex(sheet,extractedDataDAO,"27");
        addRecordsForTable(sheet, extractedDataDAO, "26");
        //testing(extractedDataDAO, "25");

        //responseGenerator(insertDataParser);
        //write to the Excel File
        writeToExcel(workbook);

    }

    public static XSSFSheet addRecordsForTable(XSSFSheet sheet,ExtractedDataDAO extractedDataDAO,String nodeId){

        //List<PatternDataParser> patternDataParsers = extractedDataDAO.getPatternRecord(nodeId);
        ExtractedData extractedData = testingTable(extractedDataDAO,nodeId);

        List<TableDataElement> tableDataElementList = extractedData.getTableDataElements();

        for (int j=0;j<tableDataElementList.size();j++){

            TableDataElement tableDataElement = tableDataElementList.get(j);

            List<Column> columnList=tableDataElement.getColumns();
            List<List<String>> rows = tableDataElement.getRows();


            // get the column size to iterate
            Row patternTableHeadRow=sheet.createRow(rowCount++);
            Row tableColumnHead=sheet.createRow(rowCount++);
            Cell headTableCell = patternTableHeadRow.createCell(0);
            headTableCell.setCellValue("Table Data");

            for (int k=0;k<columnList.size();k++){
                Cell cell =tableColumnHead.createCell(k);
                cell.setCellValue(columnList.get(k).getMetaName());
            }

            for (int k=0;k<rows.size();k++){

                Row row=sheet.createRow(rowCount++);
                Cell cell;
                List<String> tableRow =rows.get(k);
                for (int l=0;l<tableRow.size();l++){
                    cell = row.createCell(l);
                    cell.setCellValue(tableRow.get(l));
                }
            }
        }

        return sheet;

    }


    public static ExtractedData testingTable(ExtractedDataDAO extractedDataDAO,String nodeId){


        List<TableDataParser> tableDataParserList = extractedDataDAO.getTableRecord(nodeId);
        ExtractedData extractedData=new ExtractedData();

        if (tableDataParserList.size() != 0) {
            extractedData.setTableDataElements(tableDataParserList.get(0).getTableDataElements());
            List<TableDataElement> tableDataElements = extractedData.getTableDataElements();

            for(TableDataElement t:tableDataElements){
                List<Column> columnList = t.getColumns();


                List<String> values=new ArrayList<String>();
                String value=null;
                Column column;

                int rowSize = columnList.get(0).getCellValues().size();
                List<List<String>> rowList = new ArrayList<List<String>>();
                for (int j=0;j<rowSize;j++){

                    for (int k=0;k<columnList.size();k++){
                        column= columnList.get(k);
                        value = column.getCellValues().get(j);
                        values.add(value);
                    }
                    rowList.add(values);
                    values = new ArrayList<String>();
                }
                t.setRows(rowList);

                // remove unwanted values
                for (Column c:columnList){
                    c.setCellValues(null);
                }
            }

        }
        return extractedData;

    }


    public static void writeToExcel(XSSFWorkbook workbook){
        try
        {
            File myFile=new File("/Users/niro273/Desktop/enhanzer/howtodoinjava_demo.xlsx");

            if(myFile.exists())
            {
                System.out.println("howtodoinjava.xlsx is available on disk.");
                //Write the workbook in file system
                FileOutputStream out = new FileOutputStream(myFile);
                workbook.write(out);
                out.close();
            }else
            {
                //Write the workbook in file system
                FileOutputStream out = new FileOutputStream(myFile);
                workbook.write(out);
                out.close();

                System.out.println("howtodoinjava_demo_test.xlsx written successfully on disk.");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static XSSFSheet addRecordsForRegex(XSSFSheet sheet,ExtractedDataDAO extractedDataDAO,String nodeId){

        RegexDataParser regexDataParser =null;
        List<RegexDataParser> regexDataParserList = extractedDataDAO.getRegexRecord(nodeId);

        Row emptyRow = sheet.createRow(rowCount++);
        Cell cell = emptyRow.createCell(0);
        cell.setCellValue("");


        for (int i=0;i<regexDataParserList.size();i++){
            Row regexHeader = sheet.createRow(rowCount++);
            Cell headerCell1 = regexHeader.createCell(0);
            Cell headerCell2 = regexHeader.createCell(1);
            Cell headerCell3 = regexHeader.createCell(2);

            headerCell1.setCellValue("MetaName");
            headerCell2.setCellValue("Dictionary ID");
            headerCell3.setCellValue("Value");

            regexDataParser = regexDataParserList.get(i);
            List<RegexDataElement> regexDataElements = regexDataParser.getRegexDataElements();
            for (int j=0;j<regexDataElements.size();j++){
                RegexDataElement regexDataElement = regexDataElements.get(j);
                List<RegexPairElement> regexPairElements = regexDataElement.getRegexPairElements();
                for (int k=0;k<regexPairElements.size();k++){
                    RegexPairElement regexPairElement= regexPairElements.get(k);
                    regexPairElement.getValue();
                    regexDataElement.getDictionaryId();

                    Row row=sheet.createRow(rowCount++);
                    Cell cell1 = row.createCell(0);
                    cell1.setCellValue(regexPairElement.getMetaName());
                    Cell cell2 = row.createCell(1);
                    cell2.setCellValue(regexPairElement.getDictionaryId());
                    Cell cell3 = row.createCell(2);
                    cell3.setCellValue(regexPairElement.getValue());


                }
            }
        }

        return sheet;

    }

    public static ExtractedData testing(ExtractedDataDAO extractedDataDAO,String nodeId){


        List<PatternDataParser> patternDataParserList = extractedDataDAO.getPatternRecord(nodeId);
        ExtractedData extractedData=new ExtractedData();

        if (patternDataParserList.size() != 0) {
            extractedData.setPatternDataElements(patternDataParserList.get(0).getPatternDataElements());
            List<PatternDataElement> patternDataElements = extractedData.getPatternDataElements();

            for(PatternDataElement p:patternDataElements){
                List<ColumnDataElement> columnDataElements = p.getColumnDataElements();

                List<String> values=new ArrayList<String>();
                String value=null;
                ColumnDataElement column;

                int rowSize = columnDataElements.get(0).getCellValues().size();
                List<List<String>> rowList = new ArrayList<List<String>>();
                for (int j=0;j<rowSize;j++){

                    for (int k=0;k<columnDataElements.size();k++){
                        column= columnDataElements.get(k);
                        value = column.getCellValues().get(j);
                        values.add(value);
                    }
                    rowList.add(values);
                    values = new ArrayList<String>();
                }
                p.setRows(rowList);

                // remove unwanted values
                for (ColumnDataElement c:columnDataElements){
                    c.setCellValues(null);
                }

            }

        }
        return extractedData;

    }

    public static XSSFSheet addRecordsForPattern(XSSFSheet sheet,ExtractedDataDAO extractedDataDAO,String nodeId){

        //List<PatternDataParser> patternDataParsers = extractedDataDAO.getPatternRecord(nodeId);
        ExtractedData extractedData = testing(extractedDataDAO,nodeId);

        List<PatternDataElement> patternDataElementList = extractedData.getPatternDataElements();

        for (int j=0;j<patternDataElementList.size();j++){

            PatternDataElement patternDataElement = patternDataElementList.get(j);

            List<ColumnDataElement> columnDataElementList=patternDataElement.getColumnDataElements();
            List<List<String>> rows = patternDataElement.getRows();

            RegexDataElement regexDataElement = patternDataElement.getRegexDataElements();

            // Add a Header for the Cells
            Row patternHeadRow=sheet.createRow(rowCount++);
            Cell headCell = patternHeadRow.createCell(0);
            headCell.setCellValue("Pattern Element Data");

            List<RegexPairElement> regexPairElements = regexDataElement.getRegexPairElements();
            for (int k=0;k<regexPairElements.size();k++){
                RegexPairElement regexPairElement= regexPairElements.get(k);
                regexPairElement.getValue();
                regexDataElement.getDictionaryId();

                Row row=sheet.createRow(rowCount++);
                Cell cell1 = row.createCell(0);
                cell1.setCellValue(regexPairElement.getMetaName());
                Cell cell2 = row.createCell(1);
                cell2.setCellValue(regexPairElement.getDictionaryId());
                Cell cell3 = row.createCell(2);
                cell3.setCellValue(regexPairElement.getValue());
            }

            // get the column size to iterate
            Row patternTableHeadRow=sheet.createRow(rowCount++);
            Cell headTableCell = patternTableHeadRow.createCell(0);
            headTableCell.setCellValue("Pattern Element Data Tabular");

            for (int k=0;k<rows.size();k++){

                Row row=sheet.createRow(rowCount++);
                Cell cell;
                List<String> tableRow =rows.get(k);
                for (int l=0;l<tableRow.size();l++){
                    cell = row.createCell(l);
                    cell.setCellValue(tableRow.get(l));
                }
            }
        }

        return sheet;

    }


    public static void responseGenerator(InsertDataParser insertDataParser)
    {
        //TextDataParser textDataParser = insertDataParser.getTextDataParser();
        //ImageDataParser imageDataParser = insertDataParser.getImageDataParser();
        TableDataParser tableDataParser = insertDataParser.getTableDataParser();


        StringBuilder sb=new StringBuilder("");

        //Blank workbook
        XSSFWorkbook workbook = new XSSFWorkbook();

        //Create a blank sheet
        XSSFSheet sheet = workbook.createSheet("Document Extraction Data");

//        //This data needs to be written (Object[])
//        Map<String, Object[]> data = new TreeMap<String, Object[]>();
//        Integer rowNum = 1;
//
//        if(tableDataParser != null){
//            sb.append("\nExtracted Table : \n" );
//            List<TableDataElement> tableList= tableDataParser.getTableDataElements();
//
//            for (int i=0 ; i < tableList.size() ; i++) {
//
//                TableDataElement table=tableList.get(i);
//                sb.append("Table " ).append(i+1).append(" -name (").append(table.getMetaId()).append(")").append("\n");
//
//                List<Column> columnList=table.getColumns();
//
//                // Assign column names to the rows
//                Row columnNameRow = sheet.createRow(rowNum++);
//
//                for (int k=0;k<columnList.size();k++){
//                    Cell cell = columnNameRow.createCell(k);
//                    cell.setCellValue(columnList.get(k).getMetaName());
//                }
//
//                int rowCount= columnList.get(0).getCellValues().size();
//                for(Integer a=0 ; a<rowCount ; a++){
//
//                    Object[] objects=new Object[columnList.size()];
//                    Row row = sheet.createRow(rowNum++);
//
//                     for (int j=0 ; j < columnList.size() ; j++){
//                         Cell cell0 = row.createCell(j);
//                         cell0.setCellValue(columnList.get(j).getCellValues().get(a));
//                    }
//                }
//
//                for (int j=0 ; j < columnList.size() ; j++){
//
//                    Object[] objects=new Object[columnList.size()];
//
//                    Column column=columnList.get(j);
//                    sb.append("Column ").append(j+1).append(" -name (").append(column.getMetaId()).append(") : ");
//                    List<String> cellValues = column.getCellValues();
//
//                    for(int c=0 ; c < cellValues.size() ; c++ ){
//
//
//                        String cell=cellValues.get(c);
//                        sb.append(cell);
//                        /* append ',' until the element before the last */
//                        if(c != cellValues.size()-1){
//                            sb.append(" , ");
//                        }
//                    }
//                    sb.append("\n");
//                }
//            }
//        }
//
//
//
//
////
////        //Iterate over data and write to sheet
////        Set<String> keyset = data.keySet();
////        int rownum = 0;
////        for (String key : keyset)
////        {
////            Row row = sheet.createRow(rownum++);
////            Object [] objArr = data.get(key);
////            int cellnum = 0;
////            for (Object obj : objArr)
////            {
////                Cell cell = row.createCell(cellnum++);
////                if(obj instanceof String)
////                    cell.setCellValue((String)obj);
////                else if(obj instanceof Integer)
////                    cell.setCellValue((Integer)obj);
////            }
////        }
        try
        {
            File myFile=new File("/Users/niro273/Desktop/enhanzer/howtodoinjava_demo.xlsx");

            if(myFile.exists())
            {
                System.out.println("howtodoinjava.xlsx is available on disk.");
                //Write the workbook in file system
                FileOutputStream out = new FileOutputStream(myFile);
                workbook.write(out);
                out.close();
            }else
            {
                //Write the workbook in file system
                FileOutputStream out = new FileOutputStream(myFile);
                workbook.write(out);
                out.close();

                System.out.println("howtodoinjava_demo_test.xlsx written successfully on disk.");
            }


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


}
