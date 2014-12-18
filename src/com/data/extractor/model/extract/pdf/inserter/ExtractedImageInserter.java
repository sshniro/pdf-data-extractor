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

//    private static final String dbName="staging";
//    private static final String templatesColl="extractedData";

    public void insert(ImageDataParser imageDataParser,ExtractStatus extractStatus,MongoClient mongoClient){
//
//        dbInitializer dbInitializer = new dbInitializer();
//        DB db=dbInitializer.getDB(mongoClient,dbName);
//        DBCollection templateCollection = dbInitializer.getCollection(db, templatesColl);
//
//        BasicDBObject searchQuery = new BasicDBObject();
//        searchQuery.put("mainCategory", imageDataParser.getMainCategory());
//        searchQuery.put("subCategory", imageDataParser.getSubCategory());
//        searchQuery.put("templateName", imageDataParser.getTemplateName());
//        searchQuery.put("documentId", extractStatus.getDocumentId());
//        searchQuery.put("dataType", imageDataParser.getDataType());
//
//        List<ImageDataElement> imageDataElements = imageDataParser.getImageDataElements();
//
//        for(ImageDataElement i:imageDataElements){
//            DBCursor templateCursor = templateCollection.find(searchQuery);
//
//            if (templateCursor.hasNext()) {
//                // If record exists update the record
//                updateRecord(i,templateCollection, searchQuery);
//            } else { // If there is no record exists create record and input
//                createNewRecord(imageDataParser, i,templateCollection,extractStatus.getDocumentId());
//            }
//        }


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
                extractedDataDAO.updateTemplateInfo(imageDataParser,imageDataElement);
            }
        }
    }
//
//    public void createNewRecord(ImageDataParser imageDataParser, ImageDataElement imageDataElement,
//                                DBCollection templateCollection,String documentId ) {
//
//        BasicDBObject insertObject = new BasicDBObject();
//        insertObject.put("mainCategory", imageDataParser.getMainCategory());
//        insertObject.put("subCategory", imageDataParser.getSubCategory());
//        insertObject.put("templateName", imageDataParser.getTemplateName());
//        insertObject.put("documentId", documentId);
//        insertObject.put("dataType", imageDataParser.getDataType());
//
//        List<BasicDBObject> imageDataElementsInsert = new ArrayList<BasicDBObject>();
//
//        BasicDBObject imageElementObject = new BasicDBObject();
//
//        imageElementObject.put("metaId", imageDataElement.getMetaId());
//        imageElementObject.put("extractedImage", imageDataElement.getExtractedImage());
//        imageDataElementsInsert.add(imageElementObject);
//
//        insertObject.put("imageDataElements", imageDataElementsInsert);
//
//        templateCollection.insert(insertObject);
//
//    }
//
//    public void updateRecord(ImageDataElement imageDataElement,
//                             DBCollection templateCollection , BasicDBObject searchQuery ){
//
//        BasicDBObject imageElementObject = new BasicDBObject();
//
//        imageElementObject.put("metaId", imageDataElement.getMetaId());
//        imageElementObject.put("extractedImage", imageDataElement.getExtractedImage());
//
//        BasicDBObject updateObject = new BasicDBObject();
//        updateObject.put("$push", new BasicDBObject("imageDataElements", imageElementObject));
//        templateCollection.update(searchQuery, updateObject);
//
//    }
}
