package com.data.extractor.model.extract.pdf.inserter;

import com.data.extractor.model.beans.extract.pdf.ExtractStatus;
import com.data.extractor.model.beans.template.info.image.ImageDataElement;
import com.data.extractor.model.beans.template.info.image.ImageDataParser;
import com.data.extractor.model.beans.template.info.table.TableDataElement;
import com.data.extractor.model.beans.template.info.table.TableDataParser;
import com.data.extractor.model.beans.template.info.text.TextDataElement;
import com.data.extractor.model.beans.template.info.text.TextDataParser;
import com.data.extractor.model.data.access.layer.ExtractedDataDAO;
import com.mongodb.MongoClient;

import java.util.List;

public class ExtractedDataInserter {

    public void insert(TextDataParser textDataParser,ExtractStatus extractStatus,MongoClient mongoClient){

        List<TextDataElement> textDataElements = textDataParser.getTextDataElements();
        ExtractedDataDAO extractedDataDAO = new ExtractedDataDAO(mongoClient);
        TextDataElement textDataElement;
        int recordsSize=0;

        for(int i=0;i<textDataElements.size();i++){

            textDataElement=textDataElements.get(i);

            /* Only check once from the DB when the loop starts */
            if(i==0){

                recordsSize = extractedDataDAO.getRecordsSizeOfId(textDataParser.getId(),textDataParser.getDataType());
            }

            if (recordsSize == 0) {
                /* If there is no record exists create a new record and insert */
                extractedDataDAO.createTemplateInfo(textDataParser.getId() , extractStatus.getParent() , textDataParser.getDataType(),textDataElement);
                recordsSize = 1;

            } else {
                /* If record exists update the record */
                extractedDataDAO.updateTemplateInfo(textDataParser,textDataElement);
            }
        }
    }

    public void insert(ImageDataParser imageDataParser,ExtractStatus extractStatus,MongoClient mongoClient) {

        List<ImageDataElement> imageDataElements = imageDataParser.getImageDataElements();
        ExtractedDataDAO extractedDataDAO = new ExtractedDataDAO(mongoClient);
        ImageDataElement imageDataElement;
        int recordsSize = 0;

        for (int i = 0; i < imageDataElements.size(); i++) {

            imageDataElement = imageDataElements.get(i);

            /* Only check once from the DB when the loop starts */
            if (i == 0) {

                recordsSize = extractedDataDAO.getRecordsSizeOfId(imageDataParser.getId(), imageDataParser.getDataType());
            }

            if (recordsSize == 0) {
                /* If there is no record exists create a new record and insert */
                extractedDataDAO.createTemplateInfo(imageDataParser.getId(), extractStatus.getParent(), imageDataParser.getDataType(), imageDataElement);
                recordsSize = 1;

            } else {
                /* If record exists update the record */
                extractedDataDAO.updateTemplateInfo(imageDataParser, imageDataElement);
            }
        }
    }

    public void insert(TableDataParser tableDataParser,ExtractStatus extractStatus , MongoClient mongoClient){

        List<TableDataElement> tableDataElements= tableDataParser.getTableDataElements();
        ExtractedDataDAO extractedDataDAO = new ExtractedDataDAO(mongoClient);
        TableDataElement tableDataElement;
        int recordsSize=0;

        for(int i=0;i<tableDataElements.size();i++){

            tableDataElement=tableDataElements.get(i);

            /* Only check once from the DB when the loop starts */
            if(i==0){

                recordsSize = extractedDataDAO.getRecordsSizeOfId(tableDataParser.getId(),tableDataParser.getDataType());
            }

            if (recordsSize == 0) {
                /* If there is no record exists create a new record and insert */
                extractedDataDAO.createTemplateInfo(tableDataParser.getId() , extractStatus.getParent() , tableDataParser.getDataType(),tableDataElement);
                recordsSize = 1;

            } else {
                /* If record exists update the record */
                extractedDataDAO.updateTemplateInfo(tableDataParser,tableDataElement);
            }
        }
    }

}
