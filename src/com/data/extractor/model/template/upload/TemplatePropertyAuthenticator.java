package com.data.extractor.model.template.upload;


import com.data.extractor.model.beans.templates.Template;
import com.data.extractor.model.beans.templates.TemplatesParser;
import com.data.extractor.model.beans.upload.template.UploadStatus;
import com.data.extractor.model.db.connect.dbInitializer;
import com.google.gson.Gson;
import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.List;

public class TemplatePropertyAuthenticator {

    /*
    Templates Collection Data Format :
    {'mainCategory':'mainCategory',subCategory:'subCategory','templates':['templateName']}
     */
    private static final String dbName="staging";
    private static final String templatesColl="templates";


    public UploadStatus isNameValid(UploadStatus uploadStatus,MongoClient mongoClient){

        dbInitializer dbInitializer =new dbInitializer();
        DB db=dbInitializer.getDB(mongoClient,dbName);
        DBCollection templateCollection = dbInitializer.getCollection(db,templatesColl);

        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("mainCategory", uploadStatus.getMainCategory());
        basicDBObject.put("subCategory", uploadStatus.getSubCategory());

        DBCursor templateCursor = templateCollection.find(basicDBObject);

        /* If there is a Record Present parse the MongoObject returned */
        if (templateCursor.hasNext()){
            Gson gson = new Gson();
            TemplatesParser templateInfo = gson.fromJson(templateCursor.next().toString()
                    ,TemplatesParser.class);
            List<Template> templates=templateInfo.getTemplates();

            for(Template t:templates){

                /* Check if the templateName alreasy exists in the template Collection */
                if(t.getTemplateName().equals(uploadStatus.getTemplateName())){

                    uploadStatus.setIsTemplateNameValid(false);
                    uploadStatus.setTemplateNameErrorCause("Template Name Already Taken");
                    return uploadStatus;
                }
            }
        }

        uploadStatus.setIsTemplateNameValid(true);

        return uploadStatus;
    }
}
