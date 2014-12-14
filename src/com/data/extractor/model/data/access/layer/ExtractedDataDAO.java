package com.data.extractor.model.data.access.layer;


import com.data.extractor.model.beans.template.info.RawDataElement;
import com.data.extractor.model.beans.template.info.text.TextDataElement;
import com.data.extractor.model.beans.template.info.text.TextDataParser;
import com.data.extractor.model.db.connect.dbInitializer;
import com.mongodb.*;

import java.util.ArrayList;
import java.util.List;

public class ExtractedDataDAO {

    private static final String dbName="staging";
    private static final String collectionName="dictionary";

    private DBCollection collection;

    public ExtractedDataDAO(MongoClient mongoClient){
        dbInitializer dbInitializer =new dbInitializer();

        DB db=dbInitializer.getDB(mongoClient,dbName);
        this.collection = dbInitializer.getCollection(db,collectionName);

    }

    public int getRecordsSizeOfId(String nodeId, String dataType){
        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put("id" , nodeId);
        searchQuery.put("dataType" , dataType);

        DBCursor templateCursor = collection.find(searchQuery);
        return templateCursor.size();
    }
    public void createTemplateInfo(String nodeId, String parentId ,String dataType,TextDataElement textDataElement){

        BasicDBObject insertObject = new BasicDBObject();
        insertObject.put("id", nodeId);
        insertObject.put("parent", parentId);
        insertObject.put("dataType", dataType);

        List<BasicDBObject> textDataElementsInsert = new ArrayList<BasicDBObject>();

        BasicDBObject textElementObject = new BasicDBObject();

        textElementObject.put("metaId", textDataElement.getMetaId());
        textElementObject.put("elementId", textDataElement.getElementId());
        textElementObject.put("extractedText", textDataElement.getExtractedText());
        textElementObject.put("pageNumber", textDataElement.getPageNumber());
        textElementObject.put("pageRotation", textDataElement.getPageRotation());

        textElementObject.put("totalX1", textDataElement.getTotalX1());
        textElementObject.put("totalY1", textDataElement.getTotalY1());
        textElementObject.put("totalWidth", textDataElement.getTotalWidth());
        textElementObject.put("totalHeight", textDataElement.getTotalHeight());

        textElementObject.put("metaX1", textDataElement.getMetaX1());
        textElementObject.put("metaY1", textDataElement.getMetaY1());
        textElementObject.put("metaWidth", textDataElement.getMetaWidth());
        textElementObject.put("metaHeight", textDataElement.getMetaHeight());

        BasicDBObject rawDataElement = new BasicDBObject();
        RawDataElement rawData= textDataElement.getRawData();
        rawDataElement.put("id", rawData.getId());
        rawDataElement.put("elementId", rawData.getElementId());
        rawDataElement.put("elementType", rawData.getElementType());
        rawDataElement.put("startX",rawData.getStartX());
        rawDataElement.put("startY", rawData.getStartY());
        rawDataElement.put("width", rawData.getWidth());
        rawDataElement.put("height", rawData.getHeight());
        rawDataElement.put("baseUiComponentStartX", rawData.getBaseUiComponentStartX());
        rawDataElement.put("baseUiComponentStartY", rawData.getBaseUiComponentStartY());
        rawDataElement.put("baseUiComponentWidth", rawData.getBaseUiComponentWidth());
        rawDataElement.put("baseUiComponentHeight", rawData.getBaseUiComponentHeight());

        RawDataElement metaRawData = textDataElement.getMetaRawData();
        BasicDBObject metaRawDataElement = new BasicDBObject();

        metaRawDataElement.put("id", metaRawData.getId());
        metaRawDataElement.put("elementId", metaRawData.getElementId());
        metaRawDataElement.put("elementType", metaRawData.getElementType());
        metaRawDataElement.put("startX",metaRawData.getStartX());
        metaRawDataElement.put("startY", metaRawData.getStartY());
        metaRawDataElement.put("width", metaRawData.getWidth());
        metaRawDataElement.put("height", metaRawData.getHeight());
        metaRawDataElement.put("baseUiComponentStartX", metaRawData.getBaseUiComponentStartX());
        metaRawDataElement.put("baseUiComponentStartY", metaRawData.getBaseUiComponentStartY());
        metaRawDataElement.put("baseUiComponentWidth", metaRawData.getBaseUiComponentWidth());
        metaRawDataElement.put("baseUiComponentHeight", metaRawData.getBaseUiComponentHeight());

        textElementObject.put("rawData" , rawDataElement);
        textElementObject.put("metaRawData" , metaRawDataElement);
        textDataElementsInsert.add(textElementObject);

        insertObject.put("textDataElements", textDataElementsInsert);

        collection.insert(insertObject);

    }

    public void updateTemplateInfo(TextDataParser textDataParser,TextDataElement textDataElement){

        BasicDBObject searchQuery = new BasicDBObject();

        searchQuery.put("id", textDataParser.getId());
        searchQuery.put("dataType", textDataParser.getDataType());

        BasicDBObject textElementObject = new BasicDBObject();

        textElementObject.put("metaId", textDataElement.getMetaId());
        textElementObject.put("elementId", textDataElement.getElementId());
        textElementObject.put("extractedText", textDataElement.getExtractedText());
        textElementObject.put("pageNumber", textDataElement.getPageNumber());
        textElementObject.put("pageRotation", textDataElement.getPageRotation());

        textElementObject.put("totalX1", textDataElement.getTotalX1());
        textElementObject.put("totalY1", textDataElement.getTotalY1());
        textElementObject.put("totalWidth", textDataElement.getTotalWidth());
        textElementObject.put("totalHeight", textDataElement.getTotalHeight());

        textElementObject.put("metaX1", textDataElement.getMetaX1());
        textElementObject.put("metaY1", textDataElement.getMetaY1());
        textElementObject.put("metaWidth", textDataElement.getMetaWidth());
        textElementObject.put("metaHeight", textDataElement.getMetaHeight());

        BasicDBObject rawDataElement = new BasicDBObject();
        RawDataElement rawData= textDataElement.getRawData();
        rawDataElement.put("id", rawData.getId());
        rawDataElement.put("elementId", rawData.getElementId());
        rawDataElement.put("elementType", rawData.getElementType());
        rawDataElement.put("startX",rawData.getStartX());
        rawDataElement.put("startY", rawData.getStartY());
        rawDataElement.put("width", rawData.getWidth());
        rawDataElement.put("height", rawData.getHeight());
        rawDataElement.put("baseUiComponentStartX", rawData.getBaseUiComponentStartX());
        rawDataElement.put("baseUiComponentStartY", rawData.getBaseUiComponentStartY());
        rawDataElement.put("baseUiComponentWidth", rawData.getBaseUiComponentWidth());
        rawDataElement.put("baseUiComponentHeight", rawData.getBaseUiComponentHeight());

        RawDataElement metaRawData = textDataElement.getMetaRawData();
        BasicDBObject metaRawDataElement = new BasicDBObject();

        metaRawDataElement.put("id", metaRawData.getId());
        metaRawDataElement.put("elementId", metaRawData.getElementId());
        metaRawDataElement.put("elementType", metaRawData.getElementType());
        metaRawDataElement.put("startX",metaRawData.getStartX());
        metaRawDataElement.put("startY", metaRawData.getStartY());
        metaRawDataElement.put("width", metaRawData.getWidth());
        metaRawDataElement.put("height", metaRawData.getHeight());
        metaRawDataElement.put("baseUiComponentStartX", metaRawData.getBaseUiComponentStartX());
        metaRawDataElement.put("baseUiComponentStartY", metaRawData.getBaseUiComponentStartY());
        metaRawDataElement.put("baseUiComponentWidth", metaRawData.getBaseUiComponentWidth());
        metaRawDataElement.put("baseUiComponentHeight", metaRawData.getBaseUiComponentHeight());

        textElementObject.put("rawData" , rawDataElement);
        textElementObject.put("metaRawData" , metaRawDataElement);

        BasicDBObject updateObject = new BasicDBObject();
        updateObject.put("$push", new BasicDBObject("textDataElements", textElementObject));
        collection.update(searchQuery, updateObject);

    }





}
