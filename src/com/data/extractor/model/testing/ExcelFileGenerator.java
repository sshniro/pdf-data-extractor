package com.data.extractor.model.testing;

import com.data.extractor.model.beans.template.info.regex.RegexDataElement;
import com.data.extractor.model.beans.template.info.regex.RegexDataParser;
import com.data.extractor.model.beans.template.info.regex.RegexPairElement;
import com.data.extractor.model.data.access.layer.ExtractedDataDAO;
import com.mongodb.MongoClient;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class ExcelFileGenerator {

    private static int rowCount = 1;

    public Boolean generateExcel(String parentId,String nodeId,String rootPath,MongoClient mongoClient){



        ExtractedDataDAO extractedDataDAO = new ExtractedDataDAO(mongoClient);

        //Blank workbook
        XSSFWorkbook workbook = new XSSFWorkbook();

        //Create a blank sheet
        XSSFSheet sheet = workbook.createSheet("Document Extraction Data");

        addRecordsForRegex(sheet,extractedDataDAO,nodeId);
        writeToExcel(workbook,rootPath,parentId,nodeId);


        return true;
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

    public static void writeToExcel(XSSFWorkbook workbook ,String rootPath,String parentId,String nodeId){

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
    }
}
