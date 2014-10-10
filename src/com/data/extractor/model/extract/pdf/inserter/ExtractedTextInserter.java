package com.data.extractor.model.extract.pdf.inserter;


import com.data.extractor.model.beans.extract.pdf.ExtractStatus;
import com.data.extractor.model.beans.template.info.text.TextDataElement;
import com.data.extractor.model.beans.template.info.text.TextDataParser;
import com.data.extractor.model.db.connect.dbInitializer;
import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class ExtractedTextInserter {

    private static final String dbName="staging";
    private static final String templatesColl="extractedData";

    public void insert(TextDataParser textDataParser,ExtractStatus extractStatus,MongoClient mongoClient){

        dbInitializer dbInitializer = new dbInitializer();
        DB db=dbInitializer.getDB(mongoClient,dbName);
        DBCollection templateCollection = dbInitializer.getCollection(db, templatesColl);

        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put("mainCategory", textDataParser.getMainCategory());
        searchQuery.put("subCategory", textDataParser.getSubCategory());
        searchQuery.put("templateName", textDataParser.getTemplateName());
        searchQuery.put("documentId", extractStatus.getDocumentId());
        searchQuery.put("dataType", textDataParser.getDataType());

        List<TextDataElement> textDataElements = textDataParser.getTextDataElements();

        for(TextDataElement t:textDataElements){
            DBCursor templateCursor = templateCollection.find(searchQuery);

            if (templateCursor.hasNext()) {
                // If record exists update the record
                updateRecord(t,templateCollection, searchQuery);
            } else { // If there is no record exists create record and input
                createNewRecord(textDataParser, t,templateCollection,extractStatus.getDocumentId());
            }
        }
    }

    public void createNewRecord(TextDataParser textDataParser, TextDataElement textDataElement,
                                DBCollection templateCollection,String documentId ) {

        BasicDBObject insertObject = new BasicDBObject();
        insertObject.put("mainCategory", textDataParser.getMainCategory());
        insertObject.put("subCategory", textDataParser.getSubCategory());
        insertObject.put("templateName", textDataParser.getTemplateName());
        insertObject.put("documentId", documentId);
        insertObject.put("dataType", textDataParser.getDataType());

        List<BasicDBObject> textDataElementsInsert = new ArrayList<BasicDBObject>();

        BasicDBObject textElementObject = new BasicDBObject();

        textElementObject.put("metaId", textDataElement.getMetaId());
        textElementObject.put("extractedText", textDataElement.getExtractedText());
        textDataElementsInsert.add(textElementObject);

        insertObject.put("textDataElements", textDataElementsInsert);

        templateCollection.insert(insertObject);

    }

    public void updateRecord(TextDataElement textDataElement,
                             DBCollection templateCollection , BasicDBObject searchQuery ){

        BasicDBObject textElementObject = new BasicDBObject();

        textElementObject.put("metaId", textDataElement.getMetaId());
        textElementObject.put("extractedText", textDataElement.getExtractedText());

        BasicDBObject updateObject = new BasicDBObject();
        updateObject.put("$push", new BasicDBObject("textDataElements", textElementObject));
        templateCollection.update(searchQuery, updateObject);

    }
}
