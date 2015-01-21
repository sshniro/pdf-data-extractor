package com.data.extractor.model.template.edit;

import com.data.extractor.model.beans.extract.pdf.ExtractStatus;
import com.data.extractor.model.beans.manage.categories.Node;
import com.data.extractor.model.beans.markup.template.MarkUpResponse;
import com.data.extractor.model.beans.template.info.table.Cell;
import com.data.extractor.model.beans.template.info.table.Column;
import com.data.extractor.model.beans.template.info.table.TableDataElement;
import com.data.extractor.model.beans.template.info.table.TableDataParser;
import com.data.extractor.model.beans.upload.template.UploadStatus;
import com.data.extractor.model.data.access.layer.ExtractedFilesDAO;
import com.data.extractor.model.data.access.layer.TemplatesDAO;
import com.data.extractor.model.extract.pdf.table.DataProcessor;
import com.data.extractor.model.template.edit.coordinates.TableDataCoordinates;
import com.google.gson.Gson;
import com.mongodb.MongoClient;
import org.apache.pdfbox.exceptions.CryptographyException;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.IOException;
import java.util.List;

/*
Class @Returns the extracted values of the table to be shown as preview when marking the
Table
 */
public class TableDataExtractor {

    public MarkUpResponse extractTable(String jsonRequest,MongoClient mongoClient){

        MarkUpResponse markUpResponse=new MarkUpResponse();

        /* Assign the input from the user to the tableDataParser */
        Gson gson=new Gson();
        TableDataParser tableDataParser;
        tableDataParser=gson.fromJson(jsonRequest, TableDataParser.class);
        ExtractedFilesDAO xFilesDAO = new ExtractedFilesDAO(mongoClient);


        List<TableDataElement> tableDataElements=tableDataParser.getTableDataElements();
        try {

            List<UploadStatus> uploadStatusList = xFilesDAO.getRecord(tableDataParser.getId());
            tableDataParser.setPdfFile(uploadStatusList.get(0).getUploadedPdfFile());
            PDDocument doc =PDDocument.load(tableDataParser.getPdfFile());

            TableDataCoordinates tableDataCoordinates=new TableDataCoordinates();
            /* Set Values for the PDF width, Height and Rotation for the first tableDataElement*/
            tableDataCoordinates.setPdfProperties(doc, tableDataParser);
            /* Recalculate and set coordinates according to the actual pdf width and height & Page Rotation */
            tableDataCoordinates.calculateCoordinates(tableDataParser);

            ExtractStatus extractStatus=new ExtractStatus();
            extractStatus.setUploadedPdfFile(tableDataParser.getPdfFile());

            DataProcessor processor=new DataProcessor();
            tableDataParser = processor.processTable(tableDataParser,extractStatus);

            Boolean firstColumn=true;
            StringBuilder sb=new StringBuilder();
            String eol = System.getProperty("line.separator");
            int columnNumber;
            for(TableDataElement ta:tableDataElements){
                List<Column> columns=ta.getColumns();
                columnNumber = 1;
                for(Column coll:columns){

                    List<Cell> cells=coll.getCellList();
                    if(!firstColumn)
                    sb.append(eol+ "Column " + columnNumber + " : ");
                    else sb.append("Column " + columnNumber + " : ");

                    if(firstColumn){
                        firstColumn=false;
                    }

                    for (Cell ce:cells){
                        sb.append(ce.getValue() + " , ");

                    }
                    columnNumber++;
                }
            }
            markUpResponse.setDataType(tableDataParser.getDataType());
            markUpResponse.setExtractedData(sb.toString());

            doc.close();
        } catch (IOException e) {
             //Exception for file not found and Image not Found
            e.printStackTrace();
        } catch (CryptographyException e) {
            e.printStackTrace();
        }

        return markUpResponse;
    }
}
