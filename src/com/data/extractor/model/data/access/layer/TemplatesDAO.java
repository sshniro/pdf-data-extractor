package com.data.extractor.model.data.access.layer;


import com.data.extractor.model.beans.templates.TemplatesParser;
import com.data.extractor.model.db.connect.dbInitializer;
import com.data.extractor.model.interfaces.Templates;
import com.google.gson.Gson;
import com.mongodb.*;

import java.util.ArrayList;
import java.util.List;

public class TemplatesDAO implements Templates {

    /*
    Templates Collection Data Format :
    {'mainCategory':'mainCategory',subCategory:'subCategory',templates:[templateName:'name1']}
    */
    private static final String dbName="staging";
    private static final String coll="templates";

    private DBCollection templatesColl;

    public TemplatesDAO(MongoClient mongoClient){
        dbInitializer dbInitializer =new dbInitializer();

        DB db=dbInitializer.getDB(mongoClient,dbName);
        this.templatesColl = dbInitializer.getCollection(db,coll);

    }

    @Override
    public void createTemplate(String mainCategory){

        BasicDBObject insertObject=new BasicDBObject();
        insertObject.put("mainCategory", mainCategory);
        templatesColl.insert(insertObject);
    }

    @Override
    public void updateTemplate(String mainCategory,String subCategory){
        BasicDBObject searchQuery=new BasicDBObject();
        searchQuery.put("mainCategory", mainCategory);
        DBCursor templatesCursor = templatesColl.find(searchQuery);

        /* If only one record exits for the mainCategory*/
        if (templatesCursor.size()==1) {
            Gson gson=new Gson();
            TemplatesParser templatesParser=gson.fromJson(templatesCursor.next().toString(), TemplatesParser.class);

            /* If mainCategory doesn't have a subCategory appended to it */
            if(templatesParser.getSubCategory()==null){
                BasicDBObject updateDoc = new BasicDBObject();
                updateDoc.append("$set", new BasicDBObject().append("subCategory", subCategory));
                templatesColl.update(searchQuery, updateDoc);


            }else {
                /* If mainCategory has a subCategory already appended, So create a new record */
                BasicDBObject insertObject=new BasicDBObject();
                insertObject.put("mainCategory", mainCategory);
                insertObject.put("subCategory", subCategory);
                templatesColl.insert(insertObject);

            }
        }else {

            /* If mainCategory has more that one record create a new record */
            BasicDBObject insertObject=new BasicDBObject();
            insertObject.put("mainCategory", mainCategory);
            insertObject.put("subCategory", subCategory);
            templatesColl.insert(insertObject);
        }
    }

    @Override
    public void removeTemplate(String mainCategory) {
        BasicDBObject searchQuery=new BasicDBObject();
        searchQuery.put("mainCategory", mainCategory);
        templatesColl.remove(searchQuery);
    }

    @Override
    public void removeTemplate(String mainCategory, String subCategory) {
        BasicDBObject searchQuery=new BasicDBObject();
        searchQuery.put("mainCategory", mainCategory);
        searchQuery.put("subCategory", subCategory);
        templatesColl.remove(searchQuery);
    }

    @Override
    /*
    Removes only the templateName from the templates array with ' pull ' command
     */
    public void removeTemplate(String mainCategory, String subCategory, String templateName) {
        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put("mainCategory", mainCategory);
        searchQuery.put("subCategory", subCategory);

        BasicDBObject removeObject=new BasicDBObject();
        removeObject.put("templates",new BasicDBObject("templateName", templateName));

        /* Remove the templateNames from the templates array from the templates Collection */
        templatesColl.update(searchQuery, new BasicDBObject("$pull",removeObject));
    }

    @Override
    public List<TemplatesParser> getTemplates(String mainCategory){

        TemplatesParser template;
        List<TemplatesParser> templatesParserList=new ArrayList<TemplatesParser>();

        BasicDBObject basicDBObject=new BasicDBObject();
        basicDBObject.put("mainCategory", mainCategory);
        DBCursor templatesCursor = templatesColl.find(basicDBObject);
        Gson gson=new Gson();

        while (templatesCursor.hasNext()){
            template=gson.fromJson(templatesCursor.next().toString(),TemplatesParser.class);
            templatesParserList.add(template);
        }
        return templatesParserList;
    }

    @Override
    public List<TemplatesParser> getTemplates(String mainCategory,String subCategory){

        TemplatesParser template;
        List<TemplatesParser> templatesParserList=new ArrayList<TemplatesParser>();

        BasicDBObject basicDBObject=new BasicDBObject();
        basicDBObject.put("mainCategory", mainCategory);
        basicDBObject.put("subCategory", subCategory);
        DBCursor templatesCursor = templatesColl.find(basicDBObject);
        Gson gson=new Gson();

        while (templatesCursor.hasNext()){
            template=gson.fromJson(templatesCursor.next().toString(),TemplatesParser.class);
            templatesParserList.add(template);
        }
        return templatesParserList;
    }

}
