package com.data.extractor.model.data.access.layer;


import com.data.extractor.model.beans.templates.Counter;
import com.data.extractor.model.db.connect.dbInitializer;
import com.google.gson.Gson;
import com.mongodb.*;

public class CounterDAO {

    private static final String dbName="staging";
    private static final String coll="counter";

    private DBCollection counterColl;

    public CounterDAO(MongoClient mongoClient){
        dbInitializer dbInitializer =new dbInitializer();

        DB db=dbInitializer.getDB(mongoClient,dbName);
        this.counterColl = dbInitializer.getCollection(db,coll);

    }

    public int getNextId(){

        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put("reference","nodeId");
        int nextId = 0;

        DBCursor cursor = counterColl.find(searchQuery);
        if(cursor.size() == 0){
            searchQuery.put("id",0);
            counterColl.insert( searchQuery );
        }else {
            Gson gson = new Gson();
            Counter counter = gson.fromJson(cursor.next().toString(),Counter.class);
            nextId = counter.getId()+1;
            counter.setId(counter.getId()+1);
            BasicDBObject updateQuery = new BasicDBObject();
            updateQuery.append("$set", new BasicDBObject("id", counter.getId()));
            counterColl.update(searchQuery,updateQuery);
        }
        return nextId;
    }



}
