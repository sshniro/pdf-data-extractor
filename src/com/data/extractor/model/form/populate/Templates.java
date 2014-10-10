package com.data.extractor.model.form.populate;

import com.data.extractor.model.beans.form.populate.FormPopulateData;
import com.data.extractor.model.beans.templates.Template;
import com.data.extractor.model.beans.templates.TemplatesParser;
import com.data.extractor.model.db.connect.dbInitializer;
import com.google.gson.Gson;
import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.List;


public class Templates {
    /*
    Templates Collection Data Format :
    {mainCategory:'mainCategory',subCategory:'subCategory',templates:[templateName:'name1']}
    */
    private static final String host="localhost";
    private static final String dbName="staging";
    private static final String templatesColl="templates";
    private static final int    port=27017;

    public FormPopulateData getTemplates(FormPopulateData formPopulateData,MongoClient mongoClient){

        TemplatesParser templatesParser;
        dbInitializer dbInitializer =new dbInitializer();
        DB db=dbInitializer.getDB(mongoClient,dbName);
        DBCollection templateCollection = dbInitializer.getCollection(db,templatesColl);

        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("mainCategory", formPopulateData.getSelectedMC());
        basicDBObject.put("subCategory", formPopulateData.getSelectedSC());

        /* Returns the DB cursor object where the mainCategory=form.mainCategory & subCategory=form.subCategory */
        DBCursor templateCursor = templateCollection.find(basicDBObject);


        if(templateCursor.hasNext()){
            // If there is a Record Present parse the MongoObject returned
            Gson gson = new Gson();
            templatesParser = gson.fromJson(templateCursor.next().toString()
                    ,TemplatesParser.class);
            List<Template> templates=templatesParser.getTemplates();

            String[] templateNames=new String[templates.size()];

            for(int i=0; i<templates.size();i++){
                templateNames[i]=templates.get(i).getTemplateName();
            }

            formPopulateData.setTemplateNames(templateNames);
        }

        return formPopulateData;
    }

}
