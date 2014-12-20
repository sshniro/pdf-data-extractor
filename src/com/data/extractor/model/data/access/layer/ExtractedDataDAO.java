package com.data.extractor.model.data.access.layer;


import com.data.extractor.model.beans.template.info.RawDataElement;
import com.data.extractor.model.beans.template.info.image.ImageDataElement;
import com.data.extractor.model.beans.template.info.image.ImageDataParser;
import com.data.extractor.model.beans.template.info.table.Cell;
import com.data.extractor.model.beans.template.info.table.Column;
import com.data.extractor.model.beans.template.info.table.TableDataElement;
import com.data.extractor.model.beans.template.info.table.TableDataParser;
import com.data.extractor.model.beans.template.info.text.TextDataElement;
import com.data.extractor.model.beans.template.info.text.TextDataParser;
import com.data.extractor.model.beans.templates.TemplatesParser;
import com.data.extractor.model.db.connect.dbInitializer;
import com.google.gson.Gson;
import com.mongodb.*;

import java.util.ArrayList;
import java.util.List;

public class ExtractedDataDAO {

    private static final String dbName="staging";
    private static final String collectionName="extractedData";

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

    public void removeRecord(String nodeId , String parentId ,String dataType){
        BasicDBObject searchQuery = new BasicDBObject();

        searchQuery.put("id",nodeId);
        searchQuery.put("parent",parentId);
        searchQuery.put("dataType",dataType);

        collection.remove(searchQuery);
    }

    public List<TextDataParser> getTextRecord(String nodeId){

        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put("id",nodeId);
        searchQuery.put("dataType","text");

        DBCursor cursor = collection.find(searchQuery);
        Gson gson = new Gson();
        List<TextDataParser> textDataParserList = new ArrayList<TextDataParser>();
        while (cursor.hasNext()){
            TextDataParser textDataParser = gson.fromJson(cursor.next().toString(), TextDataParser.class);
            textDataParserList.add(textDataParser);
        }

        return textDataParserList;
    }

    public List<ImageDataParser> getImageRecord(String nodeId){

        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put("id",nodeId);
        searchQuery.put("dataType","text");

        DBCursor cursor = collection.find(searchQuery);
        Gson gson = new Gson();
        List<ImageDataParser> imageDataParserList = new ArrayList<ImageDataParser>();
        while (cursor.hasNext()){
            ImageDataParser imageDataParser= gson.fromJson(cursor.next().toString(), ImageDataParser.class);
            imageDataParserList.add(imageDataParser);
        }

        return imageDataParserList;
    }

    public List<TableDataParser> getTableRecord(String nodeId){

        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put("id",nodeId);
        searchQuery.put("dataType","text");

        DBCursor cursor = collection.find(searchQuery);
        Gson gson = new Gson();
        List<TableDataParser> tableDataParserList = new ArrayList<TableDataParser>();
        while (cursor.hasNext()){
            TableDataParser tableDataParser= gson.fromJson(cursor.next().toString(), TableDataParser.class);
            tableDataParserList.add(tableDataParser);
        }

        return tableDataParserList;
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

    public void createTemplateInfo(String nodeId, String parentId ,String dataType,
                                   ImageDataElement imageDataElement){

        BasicDBObject insertObject = new BasicDBObject();
        insertObject.put("id", nodeId);
        insertObject.put("parent", parentId);
        insertObject.put("dataType", dataType);

        List<BasicDBObject> imageDataElementsInsert=new ArrayList<BasicDBObject>();

        BasicDBObject imageElementObject=new BasicDBObject();

        imageElementObject.put("metaId",imageDataElement.getMetaId());
        imageElementObject.put("elementId",imageDataElement.getElementId());
        imageElementObject.put("extractedImage",imageDataElement.getExtractedImage());
        imageElementObject.put("pageNumber",imageDataElement.getPageNumber());
        imageElementObject.put("pageRotation",imageDataElement.getPageRotation());

        imageElementObject.put("totalX1",imageDataElement.getTotalX1());
        imageElementObject.put("totalY1",imageDataElement.getTotalY1());
        imageElementObject.put("totalWidth",imageDataElement.getTotalWidth());
        imageElementObject.put("totalHeight",imageDataElement.getTotalHeight());

        BasicDBObject rawDataElement = new BasicDBObject();
        RawDataElement rawData= imageDataElement.getRawData();

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

        imageElementObject.put("rawData" , rawDataElement);

        imageDataElementsInsert.add(imageElementObject);

        insertObject.put("imageDataElements", imageDataElementsInsert);

        collection.insert(insertObject);
    }

    public void createTemplateInfo(String nodeId, String parent,String dataType,
                                   TableDataElement tableDataElement){

        BasicDBObject insertObject=new BasicDBObject();
        insertObject.put("id",nodeId);
        insertObject.put("parent",parent);
        insertObject.put("dataType",dataType);

        List<BasicDBObject> tableDataElementsInsert=new ArrayList<BasicDBObject>();

        BasicDBObject tableElementObject=new BasicDBObject();

        tableElementObject.put("metaId",tableDataElement.getMetaId());
        tableElementObject.put("elementId",tableDataElement.getElementId());
        tableElementObject.put("pageNumber",tableDataElement.getPageNumber());
        tableElementObject.put("pageRotation",tableDataElement.getPageRotation());

        tableElementObject.put("totalX1",tableDataElement.getTotalX1());
        tableElementObject.put("totalY1",tableDataElement.getTotalY1());
        tableElementObject.put("totalWidth",tableDataElement.getTotalWidth());
        tableElementObject.put("totalHeight",tableDataElement.getTotalHeight());

        BasicDBObject rawDataElement = new BasicDBObject();

        RawDataElement rawData= tableDataElement.getRawData();
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

        tableElementObject.put("rawData" , rawDataElement);

        List<Column> columns=tableDataElement.getColumns();
        ArrayList columnData = new ArrayList();

        BasicDBObject columnRawDataObj;
        List<Cell> cellList = null;
        List<String> cellValues= null;
        BasicDBObject cellListObj = null;

        for(Column c:columns){

            RawDataElement columnRawDataElement = c.getRawData();
            columnRawDataObj = new BasicDBObject();

            cellList= c.getCellList();
            cellValues = new ArrayList<String>();

            for (Cell ce : cellList){
                cellValues.add(ce.getValue());
            }

            columnRawDataObj.put("id", columnRawDataElement.getId());
            columnRawDataObj.put("elementId", columnRawDataElement.getElementId());
            columnRawDataObj.put("elementType", columnRawDataElement.getElementType());
            columnRawDataObj.put("startX", columnRawDataElement.getStartX());
            columnRawDataObj.put("startY", columnRawDataElement.getStartY());
            columnRawDataObj.put("width",  columnRawDataElement.getWidth());
            columnRawDataObj.put("height", columnRawDataElement.getHeight());
            columnRawDataObj.put("baseUiComponentStartX", columnRawDataElement.getBaseUiComponentStartX());
            columnRawDataObj.put("baseUiComponentStartY", columnRawDataElement.getBaseUiComponentStartY());
            columnRawDataObj.put("baseUiComponentWidth",  columnRawDataElement.getBaseUiComponentWidth());
            columnRawDataObj.put("baseUiComponentHeight", columnRawDataElement.getBaseUiComponentHeight());

            columnData.add(new BasicDBObject("metaId",c.getMetaId()).append("metaX1",c.getMetaX1())
                            .append("metaY1", c.getMetaY1()).append("metaWidth",c.getMetaWidth())
                            .append("metaHeight", c.getMetaHeight()).append("rawData" , columnRawDataObj).append("cellValues",cellValues));
        }

        tableElementObject.put("columns",columnData);
        tableDataElementsInsert.add(tableElementObject);
        insertObject.put("tableDataElements",tableDataElementsInsert);
        collection.insert(insertObject);

    }


    public void updateTemplateInfo(String nodeId , TextDataParser textDataParser,TextDataElement textDataElement){

        BasicDBObject searchQuery = new BasicDBObject();

        searchQuery.put("id", nodeId);
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

    public void updateTemplateInfo(String nodeId , ImageDataParser imageDataParser,ImageDataElement imageDataElement){

        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put("id", nodeId );
        searchQuery.put("dataType", imageDataParser.getDataType());

        BasicDBObject imageElementObject = new BasicDBObject();

        imageElementObject.put("metaId",imageDataElement.getMetaId());
        imageElementObject.put("elementId",imageDataElement.getElementId());
        imageElementObject.put("extractedImage",imageDataElement.getExtractedImage());
        imageElementObject.put("pageNumber",imageDataElement.getPageNumber());
        imageElementObject.put("pageRotation",imageDataElement.getPageRotation());

        imageElementObject.put("totalX1",imageDataElement.getTotalX1());
        imageElementObject.put("totalY1",imageDataElement.getTotalY1());
        imageElementObject.put("totalWidth",imageDataElement.getTotalWidth());
        imageElementObject.put("totalHeight",imageDataElement.getTotalHeight());

        BasicDBObject rawDataElement = new BasicDBObject();
        RawDataElement rawData= imageDataElement.getRawData();
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

        imageElementObject.put("rawData" , rawDataElement);

        BasicDBObject updateObject=new BasicDBObject();
        updateObject.put("$push",new BasicDBObject("imageDataElements", imageElementObject));
        collection.update(searchQuery, updateObject);


    }

    public void updateTemplateInfo(String nodeId,TableDataParser tableDataParser,TableDataElement tableDataElement){

        BasicDBObject searchQuery = new BasicDBObject();

        searchQuery.put("id", nodeId);
        searchQuery.put("dataType", tableDataParser.getDataType());

        BasicDBObject tableElementObject = new BasicDBObject();

        tableElementObject.put("metaId",tableDataElement.getMetaId());
        tableElementObject.put("elementId",tableDataElement.getElementId());
        tableElementObject.put("pageNumber",tableDataElement.getPageNumber());
        tableElementObject.put("pageRotation",tableDataElement.getPageRotation());

        tableElementObject.put("totalX1",tableDataElement.getTotalX1());
        tableElementObject.put("totalY1",tableDataElement.getTotalY1());
        tableElementObject.put("totalWidth",tableDataElement.getTotalWidth());
        tableElementObject.put("totalHeight",tableDataElement.getTotalHeight());

        BasicDBObject rawDataElement = new BasicDBObject();
        RawDataElement rawData= tableDataElement.getRawData();

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

        tableElementObject.put("rawData" , rawDataElement);

        List<Column> columns=tableDataElement.getColumns();
        ArrayList columnData = new ArrayList();
        BasicDBObject columnRawDataObj;

        List<Cell> cellList = null;
        List<String> cellValues = null;
        BasicDBObject cellListObj = null;

        for(Column c:columns){

            RawDataElement columnRawDataElement = c.getRawData();
            columnRawDataObj = new BasicDBObject();

            cellList= c.getCellList();
            cellValues = new ArrayList<String>();

            for (Cell ce : cellList){
                cellValues.add(ce.getValue());
            }

            columnRawDataObj.put("id", columnRawDataElement.getId());
            columnRawDataObj.put("elementId", columnRawDataElement.getElementId());
            columnRawDataObj.put("elementType", columnRawDataElement.getElementType());
            columnRawDataObj.put("startX",columnRawDataElement.getStartX());
            columnRawDataObj.put("startY", columnRawDataElement.getStartY());
            columnRawDataObj.put("width",  columnRawDataElement.getWidth());
            columnRawDataObj.put("height", columnRawDataElement.getHeight());
            columnRawDataObj.put("baseUiComponentStartX", columnRawDataElement.getBaseUiComponentStartX());
            columnRawDataObj.put("baseUiComponentStartY", columnRawDataElement.getBaseUiComponentStartY());
            columnRawDataObj.put("baseUiComponentWidth",  columnRawDataElement.getBaseUiComponentWidth());
            columnRawDataObj.put("baseUiComponentHeight", columnRawDataElement.getBaseUiComponentHeight());


            columnData.add(new BasicDBObject("metaId",c.getMetaId()).append("metaX1",c.getMetaX1())
                            .append("metaY1",c.getMetaY1()).append("metaWidth",c.getMetaWidth())
                            .append("metaHeight",c.getMetaHeight()).append("rawData",columnRawDataObj)
                            .append("cellValues",cellValues));
        }
        tableElementObject.put("columns",columnData);

        BasicDBObject updateObject=new BasicDBObject();
        updateObject.put("$push",new BasicDBObject("tableDataElements", tableElementObject));
        collection.update(searchQuery, updateObject);

    }






}
