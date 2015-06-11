package com.data.extractor.model.data.access.layer;


import com.data.extractor.model.beans.authenticate.login.LoginRequest;
import com.data.extractor.model.db.connect.dbInitializer;
import com.mongodb.*;

public class UsersDAO {

    /*
    users Collection Data Format :
    {'userName':'userName',pass:'pass','org':'brandix'}
     */
    private static final String dbName="staging";
    private static final String coll="users";

    private DBCollection usersColl;

    public UsersDAO(MongoClient mongoClient){

        dbInitializer dbInitializer =new dbInitializer();

        DB db=dbInitializer.getDB(mongoClient,dbName);
        this.usersColl = dbInitializer.getCollection(db,coll);
    }

    public Boolean isUserExists(LoginRequest loginRequest){
        BasicDBObject basicDBObject = new BasicDBObject();

        basicDBObject.put("userName", loginRequest.getUserName());
        basicDBObject.put("pass", loginRequest.getPass());

        DBCursor templateCursor = usersColl.find(basicDBObject);

        return templateCursor.size() == 1;
    }


}
