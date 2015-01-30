package com.data.extractor.model.data.access.layer;

import com.data.extractor.model.beans.dictionary.Dictionary;
import com.data.extractor.model.db.connect.dbInitializer;
import com.google.gson.Gson;
import com.mongodb.*;

import java.util.ArrayList;
import java.util.List;

public class DictionaryDAO {

    private static final String dbName="staging";
    private static final String coll="dictionary";

    private DBCollection dictionaryColl;

    public DictionaryDAO(MongoClient mongoClient){
        dbInitializer dbInitializer =new dbInitializer();

        DB db=dbInitializer.getDB(mongoClient,dbName);
        this.dictionaryColl = dbInitializer.getCollection(db,coll);

    }

    public List<Dictionary> getAllRecords(){

        Dictionary dictionary = null;
        Gson gson = new Gson();
        List<Dictionary> dictionaryList = new ArrayList<Dictionary>();

        DBCursor dbCursor = dictionaryColl.find();

        while(dbCursor.hasNext()){
            dictionary = gson.fromJson(dbCursor.next().toString(),Dictionary.class);
            dictionaryList.add(dictionary);
        }

        return dictionaryList;
    }

    public void createDictionaryData(Dictionary dictionary , int dictionaryId){

        BasicDBObject insertObject = new BasicDBObject();

        insertObject.put("id", dictionary.getId());
        insertObject.put("name", dictionary.getName());
        insertObject.put("dataType", dictionary.getDataType());
        insertObject.put("description", dictionary.getDescription());
        insertObject.put("length", dictionary.getLength());
        insertObject.put("allowedValues", dictionary.getAllowedValues());
        insertObject.put("defaultValues", dictionary.getDefaultValues());

        dictionaryColl.insert(insertObject);
    }

    public void removeDictionaryRecord(int id){

        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put("id",id);
        dictionaryColl.remove(searchQuery);
    }

    public void updateDictionaryRecord(int id , Dictionary dictionary){
        BasicDBObject updateQuery = new BasicDBObject();
        updateQuery.put("$set",new BasicDBObject("name",dictionary.getName()).append(
                        "dataType", dictionary.getDataType()).append("description",dictionary.getDataType())
                        .append("length", dictionary.getLength()));

        BasicDBObject searchQuery = new BasicDBObject("id" , id);

        dictionaryColl.update(searchQuery , updateQuery);
    }
}
