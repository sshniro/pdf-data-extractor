package com.data.extractor.model.top.secret;


import com.data.extractor.model.beans.extract.pdf.ExtractStatus;
import com.data.extractor.model.beans.template.info.table.TableDataParser;
import com.data.extractor.model.db.connect.dbInitializer;
import com.google.gson.Gson;
import com.mongodb.*;

import java.net.UnknownHostException;

public class TableDataRetriever {

    private static final String dbName="staging";
    private static final String templatesColl="templateInfo";

    public TableDataParser retrieveTableData(ExtractStatus extractStatus,MongoClient mongoClient){
        TableDataParser tableDataParser=null;

        dbInitializer dbInitializer =new dbInitializer();
        DB db=dbInitializer.getDB(mongoClient,dbName);
        DBCollection templateCollection = dbInitializer.getCollection(db,templatesColl);

        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("mainCategory", extractStatus.getMainCategory());
        basicDBObject.put("subCategory", extractStatus.getSubCategory());
        basicDBObject.put("templateName", extractStatus.getTemplateName());
        basicDBObject.put("dataType", "table");

        DBCursor templateCursor = templateCollection.find(basicDBObject);

        if (templateCursor.hasNext()){
            // If there is a Record Present parse the MongoObject returned
            Gson gson = new Gson();
            tableDataParser = gson.fromJson(templateCursor.next().toString()
                    ,TableDataParser.class);
        }
        return tableDataParser;
    }
}
