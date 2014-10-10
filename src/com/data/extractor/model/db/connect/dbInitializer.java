package com.data.extractor.model.db.connect;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

public class dbInitializer {

    public DB getDB(MongoClient mongoClient,String dbName){
        return mongoClient.getDB(dbName);
    }

    public DBCollection getCollection(DB mongoClient,String collectionName){
        return mongoClient.getCollection(collectionName);
    }
}
