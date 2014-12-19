package com.data.extractor.model.template.edit;


import com.data.extractor.model.beans.extract.pdf.ExtractStatus;
import com.data.extractor.model.beans.manage.categories.Node;
import com.data.extractor.model.beans.template.info.image.ImageDataParser;
import com.data.extractor.model.beans.template.info.insert.InsertDataParser;
import com.data.extractor.model.beans.template.info.table.TableDataParser;
import com.data.extractor.model.beans.template.info.text.TextDataParser;
import com.data.extractor.model.beans.upload.template.UploadStatus;
import com.data.extractor.model.data.access.layer.ExtractedFilesDAO;
import com.data.extractor.model.data.access.layer.TemplatesDAO;
import com.data.extractor.model.template.edit.coordinate.ImageDataInserter;
import com.data.extractor.model.template.edit.coordinate.TableDataInserter;
import com.data.extractor.model.template.edit.coordinate.TextDataInserter;
import com.data.extractor.model.template.edit.coordinates.ImageDataCoordinates;
import com.data.extractor.model.template.edit.coordinates.TableDataCoordinates;
import com.data.extractor.model.template.edit.coordinates.TextDataCoordinates;
import com.google.gson.Gson;
import com.mongodb.MongoClient;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.IOException;
import java.util.List;

public class InsertRequestProcessor {

    public void processRequest(String jsonRequest,MongoClient mongoClient) throws IOException {

        Gson gson=new Gson();
        ExtractedFilesDAO xFilesDAO = new ExtractedFilesDAO(mongoClient);
        InsertDataParser insertDataParser = gson.fromJson(jsonRequest,InsertDataParser.class);
        TextDataParser textDataParser=insertDataParser.getTextDataParser();
        ImageDataParser imageDataParser=insertDataParser.getImageDataParser();
        TableDataParser tableDataParser=insertDataParser.getTableDataParser();

        /* If there is a textDataParser is sent from the user and has atleast 1 textDataElement process it*/
        if(textDataParser!=null && textDataParser.getTextDataElements().size() != 0) {

            TextDataInserter textDataInserter=new TextDataInserter();

            List<UploadStatus> uploadStatusList = xFilesDAO.getRecord(textDataParser.getId());

            /* Load the Template PDF in to pdf BOX and return the PDDoc to set pdf Properties*/
            textDataParser.setPdfFile(uploadStatusList.get(0).getUploadedPdfFile());
            PDDocument doc =PDDocument.load(textDataParser.getPdfFile());

            TextDataCoordinates textDataCoordinates=new TextDataCoordinates();
            /* Set Values for the PDF width, Height and Rotation for each textDataElement*/
            textDataCoordinates.setPdfProperties(doc, textDataParser);
            /* Recalculate and set coordinates according to the actual pdf width and height and Page Rotation */
            textDataCoordinates.calculateCoordinates(textDataParser);
            /*Insert the assigned values to the templateInfo MongoDB Collection*/
            textDataInserter.insert(textDataParser, uploadStatusList.get(0).getParent() ,mongoClient);

        }

        /* If there is a imageDataParser is sent from the user and has atleast 1 imageDataElement process it*/
        if(imageDataParser!=null && imageDataParser.getImageDataElements().size() != 0) {

            ImageDataInserter imageDataInserter = new ImageDataInserter();

            /* Load the Template PDF in to pdf BOX and return the PDDoc to set pdf Properties*/
            List<UploadStatus> uploadStatusList = xFilesDAO.getRecord(imageDataParser.getId());

            imageDataParser.setPdfFile(uploadStatusList.get(0).getUploadedPdfFile());
            PDDocument doc =PDDocument.load(imageDataParser.getPdfFile());

            ImageDataCoordinates imageDataCoordinates=new ImageDataCoordinates();
            /* Set Values for the PDF width, Height and Rotation for each imageDataElement*/
            imageDataCoordinates.setPdfProperties(doc, imageDataParser);
            /* Recalculate and set coordinates according to the actual pdf width and height and Page Rotation */
            imageDataCoordinates.calculateCoordinates(imageDataParser);
            /*Insert the assigned values to the templateInfo MongoDB Collection*/
            imageDataInserter.insert(imageDataParser, uploadStatusList.get(0).getParent() ,mongoClient);

        }

        /* If there is a tableDataParser is sent from the user and has atleast 1 tableDataElement process it*/
        if(tableDataParser!=null && tableDataParser.getTableDataElements().size() != 0) {

            TableDataInserter tableDataInserter = new TableDataInserter();

            /* Load the Template PDF in to pdf BOX and return the PDDoc to set pdf Properties*/
            List<UploadStatus> uploadStatusList = xFilesDAO.getRecord(tableDataParser.getId());

            tableDataParser.setPdfFile(uploadStatusList.get(0).getUploadedPdfFile());
            PDDocument doc =PDDocument.load(tableDataParser.getPdfFile());

            TableDataCoordinates tableDataCoordinates=new TableDataCoordinates();
            /* Set Values for the PDF width, Height and Rotation for each tableDataElement*/
            tableDataCoordinates.setPdfProperties(doc, tableDataParser);
            /* Recalculate and set coordinates according to the actual pdf width and height and Page Rotation */
            tableDataCoordinates.calculateCoordinates(tableDataParser);
            /*Insert the assigned values to the templateInfo MongoDB Collection*/
            tableDataInserter.insert(tableDataParser,uploadStatusList.get(0).getParent(),mongoClient);

        }

    }
}
