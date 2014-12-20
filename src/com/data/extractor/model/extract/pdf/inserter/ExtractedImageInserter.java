package com.data.extractor.model.extract.pdf.inserter;

import com.data.extractor.model.beans.extract.pdf.ExtractStatus;
import com.data.extractor.model.beans.template.info.image.ImageDataElement;
import com.data.extractor.model.beans.template.info.image.ImageDataParser;
import com.data.extractor.model.beans.template.info.text.TextDataElement;
import com.data.extractor.model.data.access.layer.ExtractedDataDAO;
import com.data.extractor.model.db.connect.dbInitializer;
import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class ExtractedImageInserter {

    public void insert(ImageDataParser imageDataParser,ExtractStatus extractStatus,MongoClient mongoClient){

        List<ImageDataElement> imageDataElements= imageDataParser.getImageDataElements();
        ExtractedDataDAO extractedDataDAO = new ExtractedDataDAO(mongoClient);
        ImageDataElement imageDataElement;
        int recordsSize=0;

        for(int i=0;i<imageDataElements.size();i++){

            imageDataElement=imageDataElements.get(i);

            /* Only check once from the DB when the loop starts */
            if(i==0){

                recordsSize = extractedDataDAO.getRecordsSizeOfId(imageDataParser.getId(),imageDataParser.getDataType());
            }

            if (recordsSize == 0) {
                /* If there is no record exists create a new record and insert */
                extractedDataDAO.createTemplateInfo(imageDataParser.getId() , extractStatus.getParent() , imageDataParser.getDataType(), imageDataElement);
                recordsSize = 1;

            } else {
                /* If record exists update the record */
                extractedDataDAO.updateTemplateInfo(imageDataParser.getId(),imageDataParser,imageDataElement);
            }
        }
    }
}
