package com.data.extractor.model.testing;

import com.data.extractor.model.beans.template.info.image.ImageDataParser;
import com.data.extractor.model.beans.template.info.insert.InsertDataParser;
import com.data.extractor.model.beans.template.info.table.Column;
import com.data.extractor.model.beans.template.info.table.TableDataElement;
import com.data.extractor.model.beans.template.info.table.TableDataParser;
import com.data.extractor.model.beans.template.info.text.TextDataParser;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.util.*;

public class ExcelGenerator {

    public void responseGenerator(InsertDataParser insertDataParser)
    {
        //TextDataParser textDataParser = insertDataParser.getTextDataParser();
        //ImageDataParser imageDataParser = insertDataParser.getImageDataParser();
        TableDataParser tableDataParser = insertDataParser.getTableDataParser();


        StringBuilder sb=new StringBuilder("");

        //Blank workbook
        XSSFWorkbook workbook = new XSSFWorkbook();

        //Create a blank sheet
        XSSFSheet sheet = workbook.createSheet("Employee Data1");



        //This data needs to be written (Object[])
        Map<String, Object[]> data = new TreeMap<String, Object[]>();
        //data.put("1", new Object[] {"ID", "NAME", "LASTNAME"});
        //data.put("2", new Object[] {1, "Amit", "Shukla"});
        //data.put("3", new Object[] {2, "Lokesh", "Gupta"});
        //data.put("4", new Object[] {3, "John", "Adwards"});

        if(tableDataParser != null){
            sb.append("\nExtracted Table : \n" );
            List<TableDataElement> tableList= tableDataParser.getTableDataElements();

            for (int i=0 ; i < tableList.size() ; i++) {

                TableDataElement table=tableList.get(i);
                sb.append("Table " ).append(i+1).append(" -name (").append(table.getMetaId()).append(")").append("\n");
                List<Column> columnList=table.getColumns();

                int rowCount= columnList.get(0).getCellList().size();
                for(Integer a=0 ; a<rowCount ; a++){

                    Object[] objects=new Object[columnList.size()];

                     for (int j=0 ; j < columnList.size() ; j++){
                        objects[j]=columnList.get(j).getCellList().get(a).getValue();
                    }

                    data.put( a.toString() , objects);
                }

                for (int j=0 ; j < columnList.size() ; j++){

                    Object[] objects=new Object[columnList.size()];

                    Column column=columnList.get(j);
                    sb.append("Column ").append(j+1).append(" -name (").append(column.getMetaId()).append(") : ");
                    List<com.data.extractor.model.beans.template.info.table.Cell> cellList = column.getCellList();

                    for(int c=0 ; c < cellList.size() ; c++ ){


                        com.data.extractor.model.beans.template.info.table.Cell cell=cellList.get(c);
                        sb.append(cell.getValue());
                        /* append ',' until the element before the last */
                        if(c != cellList.size()-1){
                            sb.append(" , ");
                        }
                    }
                    sb.append("\n");
                }
            }
        }





        //Iterate over data and write to sheet
        Set<String> keyset = data.keySet();
        int rownum = 0;
        for (String key : keyset)
        {
            Row row = sheet.createRow(rownum++);
            Object [] objArr = data.get(key);
            int cellnum = 0;
            for (Object obj : objArr)
            {
                Cell cell = row.createCell(cellnum++);
                if(obj instanceof String)
                    cell.setCellValue((String)obj);
                else if(obj instanceof Integer)
                    cell.setCellValue((Integer)obj);
            }
        }
        try
        {
            File myFile=new File("/home/niro273/brandix_doc_mining/ExcelDemosWithPOI/howtodoinjava_demo.xlsx");

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
                FileOutputStream out = new FileOutputStream(new File("I:/Test_Excel/howtodoinjava__.xlmx"));
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
