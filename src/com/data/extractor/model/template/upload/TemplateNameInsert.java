package com.data.extractor.model.template.upload;

import com.data.extractor.model.beans.upload.template.UploadStatus;
import com.data.extractor.model.db.connect.dbInitializer;
import com.mongodb.*;

import java.net.UnknownHostException;


public class TemplateNameInsert {
    /*
    Templates Collection Data Format :
    {'mainCategory':'mainCategory',subCategory:'subCategory','templates':['templateName':'templateName']}
     */
    private static final String dbName="staging";
    private static final String templatesColl="templates";

    public UploadStatus insertTemplateName(UploadStatus uploadStatus,MongoClient mongoClient){

        dbInitializer dbInitializer =new dbInitializer();
        DB db=dbInitializer.getDB(mongoClient,dbName);
        DBCollection templatesCollection = dbInitializer.getCollection(db, templatesColl);

        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put("mainCategory", uploadStatus.getMainCategory());
        searchQuery.put("subCategory", uploadStatus.getSubCategory());

        BasicDBObject templateObject = new BasicDBObject();
        templateObject.put("templateName", uploadStatus.getTemplateName());
        templateObject.put("pdfFile",uploadStatus.getUploadedPdfFile());

        BasicDBObject updateObject=new BasicDBObject();
        updateObject.put("$push",new BasicDBObject("templates", templateObject));
        templatesCollection.update(searchQuery, updateObject);

        return uploadStatus;

    }
}
