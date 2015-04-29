package com.data.extractor.model.data.access.layer;

import com.data.extractor.model.beans.upload.template.UploadStatus;
import com.data.extractor.model.db.connect.dbInitializer;
import com.google.gson.Gson;
import com.mongodb.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExtractedFilesDAO {

    private static final String dbName="staging";
    private static final String templateInfoColl="xFiles";

    private DBCollection collection;

    public ExtractedFilesDAO(MongoClient mongoClient){
        dbInitializer dbInitializer =new dbInitializer();

        DB db=dbInitializer.getDB(mongoClient,dbName);
        this.collection = dbInitializer.getCollection(db,templateInfoColl);
    }

    public void createRecord(String id, String parent , String pdfFile,String docName){

        BasicDBObject insertObject=new BasicDBObject();

        insertObject.put("id", id);
        insertObject.put("parent", parent);
        insertObject.put("uploadedPdfFile", pdfFile);
        insertObject.put("docName", docName);
        insertObject.put("dateCreated",new Date());

        collection.insert(insertObject);
    }

    public List<UploadStatus> getRecord(String id){
        BasicDBObject searchObject = new BasicDBObject();

        searchObject.put("id", id);
        DBCursor cursor=collection.find(searchObject);

        List<UploadStatus> uploadStatusList=new ArrayList<UploadStatus>();
        UploadStatus uploadStatus;
        Gson gson = new Gson();
        while (cursor.hasNext()){
            uploadStatus = gson.fromJson(cursor.next().toString(),UploadStatus.class);
            uploadStatusList.add(uploadStatus);
        }
        return uploadStatusList;
    }

}
