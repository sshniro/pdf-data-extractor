package com.data.extractor.model.template.edit.coordinate;

import com.data.extractor.model.beans.template.info.text.TextDataElement;
import com.data.extractor.model.beans.template.info.text.TextDataParser;
import com.data.extractor.model.data.access.layer.ExtractedDataDAO;
import com.mongodb.MongoClient;

import java.net.UnknownHostException;
import java.util.List;

public class TextDataInserter {

    public void insert(TextDataParser textDataParser, String parentId,MongoClient mongoClient) throws UnknownHostException {

        List<TextDataElement> textDataElements = textDataParser.getTextDataElements();
        ExtractedDataDAO extractedDataDAO=new ExtractedDataDAO(mongoClient);
        TextDataElement textDataElement;
        int templateInfoSize=0;

        /* If any data has existed previously remove it */
        templateInfoSize=extractedDataDAO.getRecordsSizeOfId(textDataParser.getId(), textDataParser.getDataType());

        if (templateInfoSize != 0) {
            /* If there is no record exists create a new record and insert */
            extractedDataDAO.removeRecord(textDataParser.getId(), parentId, textDataParser.getDataType());
        }

        for(int i=0;i<textDataElements.size();i++){

            textDataElement=textDataElements.get(i);

            /* Only check once from the DB when the loop starts */
            if(i==0){
                templateInfoSize=extractedDataDAO.getRecordsSizeOfId(textDataParser.getId(),textDataParser.getDataType());
            }

            if (templateInfoSize == 0) {
                /* If there is no record exists create a new record and insert */
                extractedDataDAO.createTemplateInfo(textDataParser.getId(), parentId, textDataParser.getDataType(), textDataElement);
                templateInfoSize=1;

            } else {
                /* If record exists update the record */
                extractedDataDAO.updateTemplateInfo(textDataParser.getId() , textDataParser , textDataElement);
            }

        }

    }
}
