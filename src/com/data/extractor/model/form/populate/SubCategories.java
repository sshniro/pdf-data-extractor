package com.data.extractor.model.form.populate;

import com.data.extractor.model.beans.form.populate.FormPopulateData;
import com.data.extractor.model.beans.templates.TemplatesParser;
import com.data.extractor.model.db.connect.dbInitializer;
import com.google.gson.Gson;
import com.mongodb.*;

import java.net.UnknownHostException;


public class SubCategories {

    /*
    Templates Collection Data Format :
    {mainCategory:'mainCategory',subCategory:'subCategory',templates:[templateName:'name1']}
    */
    private static final String dbName="staging";
    private static final String templatesColl="templates";

    public FormPopulateData getSubCategories(FormPopulateData formPopulateData,MongoClient mongoClient){

        dbInitializer dbInitializer =new dbInitializer();
        DB db=dbInitializer.getDB(mongoClient,dbName);
        DBCollection templateCollection = dbInitializer.getCollection(db,templatesColl);
        BasicDBObject basicDBObject = new BasicDBObject();

            /* Returns the DB cursor object where the mainCategory=form.mainCategory  */
        basicDBObject.put("mainCategory", formPopulateData.getSelectedMC());
        DBCursor templateCursor = templateCollection.find(basicDBObject);

        String[] subCategories=new String[templateCursor.size()];
        int i=0;
        while (templateCursor.hasNext()){
            // If there is a Record Present parse the MongoObject returned
            Gson gson = new Gson();
            TemplatesParser templatesParser = gson.fromJson(templateCursor.next().toString()
                    ,TemplatesParser.class);
            subCategories[i]=templatesParser.getSubCategory();
            i++;
        }

        formPopulateData.setSubCategories(subCategories);

        return formPopulateData;
    }
}
