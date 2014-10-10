package com.data.extractor.model.template.markup;


import com.data.extractor.model.beans.template.info.image.ImageDataParser;
import com.data.extractor.model.beans.template.info.insert.InsertDataParser;
import com.data.extractor.model.beans.template.info.table.TableDataParser;
import com.data.extractor.model.beans.template.info.text.TextDataParser;
import com.data.extractor.model.template.markup.calculate.coordinates.ImageDataCoordinates;
import com.data.extractor.model.template.markup.calculate.coordinates.TableDataCoordinates;
import com.data.extractor.model.template.markup.calculate.coordinates.TextDataCoordinates;
import com.data.extractor.model.template.markup.insert.coordinate.ImageDataInserter;
import com.data.extractor.model.template.markup.insert.coordinate.TableDataInserter;
import com.data.extractor.model.template.markup.insert.coordinate.TextDataInserter;
import com.data.extractor.model.template.markup.pdf.retreiver.ImagePDDocument;
import com.data.extractor.model.template.markup.pdf.retreiver.TablePDDocument;
import com.data.extractor.model.template.markup.pdf.retreiver.TextPDDocument;
import com.google.gson.Gson;
import com.mongodb.MongoClient;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.IOException;

public class InsertRequestProcessor {

    public void processRequest(String jsonRequest,MongoClient mongoClient) throws IOException {

        Gson gson=new Gson();
        InsertDataParser insertDataParser = gson.fromJson(jsonRequest,InsertDataParser.class);
        TextDataParser textDataParser=insertDataParser.getTextDataParser();
        ImageDataParser imageDataParser=insertDataParser.getImageDataParser();
        TableDataParser tableDataParser=insertDataParser.getTableDataParser();

        /* If there is a textDataParser is sent from the user and has atleast 1 textDataElement process it*/
        if(textDataParser!=null && textDataParser.getTextDataElements().size() != 0) {

            TextDataInserter textDataInserter=new TextDataInserter();

            TextPDDocument textPDDocument=new TextPDDocument();

            /* Load the Template PDF in to pdf BOX and return the PDDoc to set pdf Properties*/
            PDDocument doc=textPDDocument.retrievePDDoc(textDataParser,mongoClient);

            TextDataCoordinates textDataCoordinates=new TextDataCoordinates();
            /* Set Values for the PDF width, Height and Rotation for each textDataElement*/
            textDataCoordinates.setPdfProperties(doc, textDataParser);
            /* Recalculate and set coordinates according to the actual pdf width and height and Page Rotation */
            textDataCoordinates.calculateCoordinates(textDataParser);
            /*Insert the assigned values to the templateInfo MongoDB Collection*/
            textDataInserter.insert(textDataParser,mongoClient);

        }

        /* If there is a imageDataParser is sent from the user and has atleast 1 imageDataElement process it*/
        if(imageDataParser!=null && imageDataParser.getImageDataElements().size() != 0) {

            ImageDataInserter imageDataInserter = new ImageDataInserter();

            ImagePDDocument textPDDocument=new ImagePDDocument();
            /* Load the Template PDF in to pdf BOX and return the PDDoc to set pdf Properties*/
            PDDocument doc=textPDDocument.retrievePDDoc(imageDataParser,mongoClient);

            ImageDataCoordinates imageDataCoordinates=new ImageDataCoordinates();
            /* Set Values for the PDF width, Height and Rotation for each imageDataElement*/
            imageDataCoordinates.setPdfProperties(doc, imageDataParser);
            /* Recalculate and set coordinates according to the actual pdf width and height and Page Rotation */
            imageDataCoordinates.calculateCoordinates(imageDataParser);
            /*Insert the assigned values to the templateInfo MongoDB Collection*/
            imageDataInserter.insert(imageDataParser,mongoClient);

        }

        /* If there is a tableDataParser is sent from the user and has atleast 1 tableDataElement process it*/
        if(tableDataParser!=null && tableDataParser.getTableDataElements().size() != 0) {

            TableDataInserter tableDataInserter = new TableDataInserter();

            TablePDDocument tablePDDocument=new TablePDDocument();
            /* Load the Template PDF in to pdf BOX and return the PDDoc to set pdf Properties*/
            PDDocument doc=tablePDDocument.retrievePDDoc(tableDataParser,mongoClient);

            TableDataCoordinates tableDataCoordinates=new TableDataCoordinates();
            /* Set Values for the PDF width, Height and Rotation for each tableDataElement*/
            tableDataCoordinates.setPdfProperties(doc, tableDataParser);
            /* Recalculate and set coordinates according to the actual pdf width and height and Page Rotation */
            tableDataCoordinates.calculateCoordinates(tableDataParser);
            /*Insert the assigned values to the templateInfo MongoDB Collection*/
            tableDataInserter.insert(tableDataParser,mongoClient);

        }

    }
}
