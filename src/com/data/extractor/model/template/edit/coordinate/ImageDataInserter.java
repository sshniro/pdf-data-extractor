package com.data.extractor.model.template.edit.coordinate;


import com.data.extractor.model.beans.template.info.image.ImageDataElement;
import com.data.extractor.model.beans.template.info.image.ImageDataParser;
import com.data.extractor.model.data.access.layer.ExtractedDataDAO;
import com.data.extractor.model.data.access.layer.TemplateInfoDAO;
import com.mongodb.MongoClient;

import java.util.List;

public class ImageDataInserter {

    public void insert(ImageDataParser imageDataParser, String parentId ,MongoClient mongoClient){

        List<ImageDataElement> imageDataElements = imageDataParser.getImageDataElements();
        ExtractedDataDAO extractedDataDAO=new ExtractedDataDAO(mongoClient);
        ImageDataElement imageDataElement;
        int templateInfoSize=0;

        /* If any data has existed previously remove it */
        templateInfoSize=extractedDataDAO.getRecordsSizeOfId(imageDataParser.getId(), imageDataParser.getDataType());

        if (templateInfoSize != 0) {
            /* If there is no record exists create a new record and insert */
            extractedDataDAO.removeRecord(imageDataParser.getId(), parentId, imageDataParser.getDataType());
        }

        for(int i=0;i<imageDataElements.size();i++){

            imageDataElement=imageDataElements.get(i);

            /* Only check once from the DB when the loop starts */
            if(i==0){
                templateInfoSize=extractedDataDAO.getRecordsSizeOfId(imageDataParser.getId() ,imageDataParser.getDataType());
            }

            if (templateInfoSize == 0) {
                /* If there is no record exists create a new record and insert */
                extractedDataDAO.createTemplateInfo(imageDataParser.getId(), parentId ,imageDataParser.getDataType(), imageDataElement);
                templateInfoSize=1;

            } else {
                /* If record exists update the record */
                extractedDataDAO.updateTemplateInfo(imageDataParser, imageDataElement);
            }

        }
    }

}
