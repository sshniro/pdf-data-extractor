package com.data.extractor.model.form.populate;

import com.data.extractor.model.beans.form.populate.FormPopulateData;
import com.data.extractor.model.db.connect.dbInitializer;
import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.List;

public class MainCategories {

    /*
    Templates Collection Data Format :
    {mainCategory:'mainCategory',subCategory:'subCategory',templates:[templateName:'name1']}
    */
    private static final String dbName="staging";
    private static final String templatesColl="templates";


    public FormPopulateData getMainCategories(FormPopulateData formPopulateData,MongoClient mongoClient){

        dbInitializer dbInitializer =new dbInitializer();
        DB db=dbInitializer.getDB(mongoClient,dbName);
        DBCollection templateCollection = dbInitializer.getCollection(db,templatesColl);

            /* Returns a array of distinct main categories */
        List list= templateCollection.distinct("mainCategory");
        String[] mainCategories=new String[list.size()];
        for(int i=0 ; i<list.size();i++){
            mainCategories[i]= (String) list.get(i);
        }

        formPopulateData.setMainCategories(mainCategories);
        return formPopulateData;
    }
}
