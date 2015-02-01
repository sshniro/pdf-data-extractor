package com.data.extractor.model.data.access.layer;


import com.data.extractor.model.beans.manage.categories.ManageCategoriesData;
import com.data.extractor.model.beans.manage.categories.Node;
import com.data.extractor.model.beans.templates.TemplatesParser;
import com.data.extractor.model.beans.user.UserBean;
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

    public void addUserToNode(String id , String parent , String userId){
        BasicDBObject searchQuery= new BasicDBObject();

        searchQuery.put("id",id);
        searchQuery.put("parent",parent);

        BasicDBObject userObj = new BasicDBObject("id",userId);

        BasicDBObject updateObject = new BasicDBObject();
        updateObject.put("$push", new BasicDBObject("users", userId));
        templatesColl.update(searchQuery, updateObject);
    }

    public void removeUserFromNode(String id,String parent , String userId){
        BasicDBObject searchQuery = new BasicDBObject();

        searchQuery.put("id",id);
        searchQuery.put("parent",parent);

        BasicDBObject userObj = new BasicDBObject("id",userId);

        BasicDBObject updateObject = new BasicDBObject();
        updateObject.put("$pull", new BasicDBObject("users", userId));
        templatesColl.update(searchQuery, updateObject);
    }


    public List<TemplatesParser> getTemplates(List<String> categories){
        TemplatesParser template;
        List<TemplatesParser> templatesParserList=new ArrayList<TemplatesParser>();

        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put( "categories" , categories);
        DBCursor templatesCursor = templatesColl.find(searchQuery);
        Gson gson=new Gson();

        while (templatesCursor.hasNext()){
            template = gson.fromJson(templatesCursor.next().toString(),TemplatesParser.class);
            templatesParserList.add(template);
        }
        return templatesParserList;
    }

    public void createNode(String id , String parent , String text ){
        DBObject insertQuery = new BasicDBObject();
        insertQuery.put("id",id);
        insertQuery.put("parent" , parent);
        insertQuery.put("text" , text);
        templatesColl.insert(insertQuery);

    }

    public void createLeafNode(String id , String parent , String text, String pdfFile ){
        DBObject insertQuery = new BasicDBObject();
        insertQuery.put("id",id);
        insertQuery.put("parent" , parent);
        insertQuery.put("text" , text);
        insertQuery.put("pdfFile" , pdfFile);
        insertQuery.put("node" , "leaf" );
        templatesColl.insert(insertQuery);

    }

    public  ManageCategoriesData getAllNodes(ManageCategoriesData data){

        DBCursor cursor = templatesColl.find(null);

        Gson gson=new Gson();
        Node node;
        List<Node> nodeList=new ArrayList<Node>();
        while(cursor.hasNext()){
            node= gson.fromJson(cursor.next().toString(),Node.class);
            nodeList.add(node);
        }
        data.setNodes(nodeList);
        return data;
    }

    public List<Node> getNodes(String parentName){
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("parent", parentName);

        DBCursor templateCursor = templatesColl.find(basicDBObject);

        Node node;
        Gson gson = new Gson();
        List<Node> nodeList= new ArrayList<Node>();
        while (templateCursor.hasNext()){
            node = gson.fromJson(templateCursor.next().toString(),Node.class);
            nodeList.add(node);
        }
        return nodeList;
    }

    public Node getNode(String nodeId){
        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put("id", nodeId);

        DBObject dbObject = templatesColl.findOne(searchQuery);

        Gson gson=new Gson();
        return gson.fromJson(dbObject.toString(),Node.class);
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

    @Override
    public void removeTemplate(String mainCategory) {

    }

    @Override
    public void removeTemplate(String mainCategory, String subCategory) {

    }

    @Override
    public void removeTemplate(String mainCategory, String subCategory, String templateName) {

    }

}
