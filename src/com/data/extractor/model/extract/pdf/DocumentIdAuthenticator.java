package com.data.extractor.model.extract.pdf;


import com.data.extractor.model.beans.extract.pdf.ExtractStatus;
import com.data.extractor.model.db.connect.dbInitializer;
import com.mongodb.*;

import java.net.UnknownHostException;

public class DocumentIdAuthenticator {

    /*
   extractedData Collection Data Format :
   {'mainCategory':'mainCategory',subCategory:'subCategory','templateName':'templateName'
   documentId:'documentId',dataType:'text/image/table', image/text/table~DataElements : 'dataElements'}
    */
    private static final String dbName="staging";
    private static final String dataColl="extractedData";


    public ExtractStatus isDocIdValid(ExtractStatus extractStatus , MongoClient mongoClient) throws UnknownHostException {

        dbInitializer dbInitializer =new dbInitializer();
        DB db=dbInitializer.getDB(mongoClient,dbName);
        DBCollection dataCollection = dbInitializer.getCollection(db,dataColl);

        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("parent", extractStatus.getParent());
        basicDBObject.put("documentId", extractStatus.getDocumentId());

        DBCursor templateCursor = dataCollection.find(basicDBObject);

        if (templateCursor.hasNext()){
            extractStatus.setStatus(false);
            extractStatus.setErrorCause("Document ID Already Taken");
        }
        else {
            extractStatus.setStatus(true);
        }

        return extractStatus;
    }
}
