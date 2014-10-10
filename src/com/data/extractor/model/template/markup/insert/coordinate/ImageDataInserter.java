package com.data.extractor.model.template.markup.insert.coordinate;


import com.data.extractor.model.beans.template.info.image.ImageDataElement;
import com.data.extractor.model.beans.template.info.image.ImageDataParser;
import com.data.extractor.model.data.access.layer.TemplateInfoDAO;
import com.mongodb.*;

import java.util.List;

public class ImageDataInserter {

    public void insert(ImageDataParser imageDataParser, MongoClient mongoClient){

        List<ImageDataElement> imageDataElements = imageDataParser.getImageDataElements();
        TemplateInfoDAO templateInfoDAO=new TemplateInfoDAO(mongoClient);
        ImageDataElement imageDataElement;
        int templateInfoSize=0;

        for(int i=0;i<imageDataElements.size();i++){

            imageDataElement=imageDataElements.get(i);

            /* Only check once from the DB when the loop starts */
            if(i==0){
                templateInfoSize=templateInfoDAO.getTemplateInfoSize(imageDataParser.getMainCategory(),imageDataParser.getSubCategory(),
                        imageDataParser.getTemplateName(),imageDataParser.getDataType());
            }

            if (templateInfoSize == 0) {
                /* If there is no record exists create a new record and insert */
                templateInfoDAO.createTemplateInfo(imageDataParser.getMainCategory(),imageDataParser.getSubCategory(),
                        imageDataParser.getTemplateName(),imageDataParser.getDataType(),imageDataElement);
                templateInfoSize=1;

            } else {
                /* If record exists update the record */
                templateInfoDAO.updateTemplateInfo(imageDataParser,imageDataElement);
            }

        }
    }

}
