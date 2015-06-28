package com.data.extractor.model.testing;

import com.data.extractor.model.beans.template.info.ExtractedData;
import com.data.extractor.model.beans.template.info.pattern.ColumnDataElement;
import com.data.extractor.model.beans.template.info.pattern.PatternDataElement;
import com.data.extractor.model.beans.template.info.pattern.PatternDataParser;
import com.data.extractor.model.beans.template.info.regex.RegexDataElement;
import com.data.extractor.model.beans.template.info.regex.RegexDataParser;
import com.data.extractor.model.beans.template.info.regex.RegexPairElement;
import com.data.extractor.model.beans.template.info.table.Column;
import com.data.extractor.model.beans.template.info.table.TableDataElement;
import com.data.extractor.model.beans.template.info.table.TableDataParser;
import com.data.extractor.model.data.access.layer.ExtractedDataDAO;
import com.mongodb.MongoClient;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelFileGenerator {

    private static int rowCount = 1;

    public String generateExcel(String parentId,String nodeId,String rootPath,MongoClient mongoClient){

        ExtractedDataDAO extractedDataDAO = new ExtractedDataDAO(mongoClient);
        rowCount = 1;

        //Blank workbook
        XSSFWorkbook workbook = new XSSFWorkbook();

        //Create a blank sheet
        XSSFSheet sheet = workbook.createSheet("Document Extraction Data");

        sheet = addRecordsForRegex(sheet,extractedDataDAO,nodeId);
        sheet = addRecordsForPattern(sheet,extractedDataDAO,nodeId);
        sheet = addRecordsForTable(sheet,extractedDataDAO,nodeId);
        String excelPath = writeToExcel(workbook,rootPath,parentId,nodeId);


        return excelPath;
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

    public static String writeToExcel(XSSFWorkbook workbook ,String rootPath,String parentId,String nodeId){
        String excelPath=null;
        // Make Download Path
        File saveLocation = new File(rootPath + File.separator + "uploads"+File.separator+"temp" + File.separator + parentId +
                File.separator + nodeId + File.separator);
        if (!saveLocation.exists()) {
            boolean status = saveLocation.mkdirs();
        }

        try
        {

            // Concatenate TemplateName with Document Name
            File excelFile = new File(saveLocation + File.separator + nodeId +".xlsx");

            excelPath= saveLocation.getAbsolutePath() + File.separator + nodeId + ".xlsx";


            if(excelFile.exists())
            {
                System.out.println("Excel File is Already available on disk.");
                //Write the workbook in file system
                FileOutputStream out = new FileOutputStream(excelFile);
                workbook.write(out);
                out.close();
            }else
            {
                //Write the workbook in file system
                FileOutputStream out = new FileOutputStream(excelFile);
                workbook.write(out);
                out.close();

                System.out.println("Excel file is written successfully on disk.");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return excelPath;
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


}
