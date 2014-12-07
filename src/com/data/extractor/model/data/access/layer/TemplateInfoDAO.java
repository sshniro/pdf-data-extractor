package com.data.extractor.model.data.access.layer;


import com.data.extractor.model.beans.template.info.RawDataElement;
import com.data.extractor.model.beans.template.info.image.ImageDataElement;
import com.data.extractor.model.beans.template.info.image.ImageDataParser;
import com.data.extractor.model.beans.template.info.table.Column;
import com.data.extractor.model.beans.template.info.table.TableDataElement;
import com.data.extractor.model.beans.template.info.table.TableDataParser;
import com.data.extractor.model.beans.template.info.text.TextDataElement;
import com.data.extractor.model.beans.template.info.text.TextDataParser;
import com.data.extractor.model.db.connect.dbInitializer;
import com.data.extractor.model.interfaces.TemplateInfo;
import com.google.gson.Gson;
import com.mongodb.*;

import java.util.ArrayList;
import java.util.List;

public class TemplateInfoDAO {

    private static final String dbName="staging";
    private static final String templateInfoColl="templateInfo";

    private DBCollection infoColl;

    public TemplateInfoDAO(MongoClient mongoClient){
        dbInitializer dbInitializer =new dbInitializer();

        DB db=dbInitializer.getDB(mongoClient,dbName);
        this.infoColl = dbInitializer.getCollection(db,templateInfoColl);
    }


    /* Method remove all text image and table data elements from the collection */

    public void removeTemplateInfo(String mainCategory){

        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put("mainCategory",mainCategory);
        infoColl.remove(searchQuery);
    }

    /* Method remove all text image and table data elements from the collection */

    public void removeTemplateInfo(String mainCategory,String subCategory){

        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put("mainCategory",mainCategory);
        searchQuery.put("subCategory",subCategory);
        infoColl.remove(searchQuery);
    }

    /* Method remove all text image and table data elements from the collection */

    public void removeTemplateInfo(String mainCategory,String subCategory,String templateName){

        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put("mainCategory",mainCategory);
        searchQuery.put("subCategory",subCategory);
        searchQuery.put("templateName",templateName);

        infoColl.remove(searchQuery);
    }

    /* @Returns the size of the templateInfo record size for the given mainCat , subCat , tempName , dataType
    * Used preliminary before creating a new record to check whether if there already a record exists
    * */

    public int getTemplateInfoSize(String mainCat , String subCat,String tempName,String dataType){

        BasicDBObject searchQuery = new BasicDBObject();

        searchQuery.put("mainCategory", mainCat);
        searchQuery.put("subCategory", subCat);
        searchQuery.put("templateName", tempName);
        searchQuery.put("dataType", dataType);

        DBCursor templateCursor = infoColl.find(searchQuery);

        return templateCursor.size();
    }

    // TODO override this method from templates
    public int getTemplateInfoSize(String nodeId, String dataType){
        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put("id" , nodeId);
        searchQuery.put("dataType" , dataType);

        DBCursor templateCursor = infoColl.find(searchQuery);
        return templateCursor.size();
    }

    public List<TextDataParser> getTextTemplateInfo(String id,String dataType){

        BasicDBObject searchQuery = new BasicDBObject();
        TextDataParser textDataParser;
        List<TextDataParser> textDataParserList=new ArrayList<TextDataParser>();

        searchQuery.put("id", id);
        searchQuery.put("dataType", dataType);

        DBCursor templateCursor = infoColl.find(searchQuery);

        while (templateCursor.hasNext()){
            // If there is a Record Present parse the MongoObject returned
            Gson gson = new Gson();
            textDataParser = gson.fromJson(templateCursor.next().toString()
                    ,TextDataParser.class);
            textDataParserList.add(textDataParser);
        }

        return textDataParserList;
    }

    public List<ImageDataParser> getImageTemplateInfo(String id,String dataType){

        BasicDBObject searchQuery = new BasicDBObject();
        ImageDataParser imageDataParser;
        List<ImageDataParser> imageDataParserList=new ArrayList<ImageDataParser>();

        searchQuery.put("id", id);
        searchQuery.put("dataType", dataType);

        DBCursor templateCursor = infoColl.find(searchQuery);

        while (templateCursor.hasNext()){
            // If there is a Record Present parse the MongoObject returned
            Gson gson = new Gson();
            imageDataParser = gson.fromJson(templateCursor.next().toString()
                    ,ImageDataParser.class);
            imageDataParserList.add(imageDataParser);
        }

        return imageDataParserList;
    }

    public List<TableDataParser> getTableTemplateInfo(String id,String dataType){

        BasicDBObject searchQuery = new BasicDBObject();
        TableDataParser tableDataParser;
        List<TableDataParser> tableDataParserList=new ArrayList<TableDataParser>();

        searchQuery.put("id", id);
        searchQuery.put("dataType", dataType);

        DBCursor templateCursor = infoColl.find(searchQuery);

        while (templateCursor.hasNext()){
            // If there is a Record Present parse the MongoObject returned
            Gson gson = new Gson();
            tableDataParser = gson.fromJson(templateCursor.next().toString()
                    ,TableDataParser.class);
            tableDataParserList.add(tableDataParser);
        }

        return tableDataParserList;
    }


    public void createTemplateInfo(String nodeId,String dataType,TextDataElement textDataElement){

        BasicDBObject insertObject = new BasicDBObject();
        insertObject.put("id", nodeId);
        insertObject.put("dataType", dataType);

        List<BasicDBObject> textDataElementsInsert = new ArrayList<BasicDBObject>();

        BasicDBObject textElementObject = new BasicDBObject();

        textElementObject.put("metaId", textDataElement.getMetaId());
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
        rawDataElement.put("elementType", rawData.getElementType());
        rawDataElement.put("startX",rawData.getStartX());
        rawDataElement.put("startY", rawData.getStartY());
        rawDataElement.put("width", rawData.getWidth());
        rawDataElement.put("height", rawData.getWidth());
        rawDataElement.put("baseUiComponentStartX", rawData.getBaseUiComponentStartX());
        rawDataElement.put("baseUiComponentStartY", rawData.getBaseUiComponentStartY());
        rawDataElement.put("baseUiComponentWidth", rawData.getBaseUiComponentWidth());
        rawDataElement.put("baseUiComponentHeight", rawData.getBaseUiComponentHeight());

        textElementObject.put("rawData" , rawDataElement);
        textDataElementsInsert.add(textElementObject);

        insertObject.put("textDataElements", textDataElementsInsert);

        infoColl.insert(insertObject);
    }

   public void createTemplateInfo(String nodeId,String dataType,
                                   ImageDataElement imageDataElement){

        BasicDBObject insertObject = new BasicDBObject();
        insertObject.put("id", nodeId);
        insertObject.put("dataType", dataType);

        List<BasicDBObject> imageDataElementsInsert=new ArrayList<BasicDBObject>();

        BasicDBObject imageElementObject=new BasicDBObject();

        imageElementObject.put("metaId",imageDataElement.getMetaId());
        imageElementObject.put("pageNumber",imageDataElement.getPageNumber());
        imageElementObject.put("pageRotation",imageDataElement.getPageRotation());

        imageElementObject.put("totalX1",imageDataElement.getTotalX1());
        imageElementObject.put("totalY1",imageDataElement.getTotalY1());
        imageElementObject.put("totalWidth",imageDataElement.getTotalWidth());
        imageElementObject.put("totalHeight",imageDataElement.getTotalHeight());

       BasicDBObject rawDataElement = new BasicDBObject();
       RawDataElement rawData= imageDataElement.getRawData();
       rawDataElement.put("id", rawData.getId());
       rawDataElement.put("elementType", rawData.getElementType());
       rawDataElement.put("startX",rawData.getStartX());
       rawDataElement.put("startY", rawData.getStartY());
       rawDataElement.put("width", rawData.getWidth());
       rawDataElement.put("height", rawData.getWidth());
       rawDataElement.put("baseUiComponentStartX", rawData.getBaseUiComponentStartX());
       rawDataElement.put("baseUiComponentStartY", rawData.getBaseUiComponentStartY());
       rawDataElement.put("baseUiComponentWidth", rawData.getBaseUiComponentWidth());
       rawDataElement.put("baseUiComponentHeight", rawData.getBaseUiComponentHeight());

       imageElementObject.put("rawData" , rawDataElement);

        imageDataElementsInsert.add(imageElementObject);

        insertObject.put("imageDataElements", imageDataElementsInsert);

        infoColl.insert(insertObject);
    }


    public void createTemplateInfo(String nodeId,String dataType,
                                   TableDataElement tableDataElement){

        BasicDBObject insertObject=new BasicDBObject();
        insertObject.put("id",nodeId);
        insertObject.put("dataType",dataType);

        List<BasicDBObject> tableDataElementsInsert=new ArrayList<BasicDBObject>();

        BasicDBObject tableElementObject=new BasicDBObject();

        tableElementObject.put("metaId",tableDataElement.getMetaId());
        tableElementObject.put("pageNumber",tableDataElement.getPageNumber());
        tableElementObject.put("pageRotation",tableDataElement.getPageRotation());

        tableElementObject.put("totalX1",tableDataElement.getTotalX1());
        tableElementObject.put("totalY1",tableDataElement.getTotalY1());
        tableElementObject.put("totalWidth",tableDataElement.getTotalWidth());
        tableElementObject.put("totalHeight",tableDataElement.getTotalHeight());

        BasicDBObject rawDataElement = new BasicDBObject();
        RawDataElement rawData= tableDataElement.getRawData();
        rawDataElement.put("id", rawData.getId());
        rawDataElement.put("elementType", rawData.getElementType());
        rawDataElement.put("startX",rawData.getStartX());
        rawDataElement.put("startY", rawData.getStartY());
        rawDataElement.put("width", rawData.getWidth());
        rawDataElement.put("height", rawData.getWidth());
        rawDataElement.put("baseUiComponentStartX", rawData.getBaseUiComponentStartX());
        rawDataElement.put("baseUiComponentStartY", rawData.getBaseUiComponentStartY());
        rawDataElement.put("baseUiComponentWidth", rawData.getBaseUiComponentWidth());
        rawDataElement.put("baseUiComponentHeight", rawData.getBaseUiComponentHeight());

        tableElementObject.put("rawData" , rawDataElement);

        List<Column> columns=tableDataElement.getColumns();
        ArrayList columnData = new ArrayList();

        for(Column c:columns){
            columnData.add(new BasicDBObject("metaId",c.getMetaId()).append("metaX1",c.getMetaX1())
                    .append("metaY1",c.getMetaY1()).append("metaWidth",c.getMetaWidth())
                    .append("metaHeight",c.getMetaHeight()));
        }

        tableElementObject.put("columns",columnData);
        tableDataElementsInsert.add(tableElementObject);
        insertObject.put("tableDataElements",tableDataElementsInsert);
        infoColl.insert(insertObject);

    }

    /* Method updates the previously available record of textDataParser in the templateInfo collection  */

    public void updateTemplateInfo(TextDataParser textDataParser,TextDataElement textDataElement){

        BasicDBObject searchQuery = new BasicDBObject();

        searchQuery.put("id", textDataParser.getId());
        searchQuery.put("dataType", textDataParser.getDataType());

        BasicDBObject textElementObject = new BasicDBObject();

        textElementObject.put("metaId", textDataElement.getMetaId());
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
        rawDataElement.put("elementType", rawData.getElementType());
        rawDataElement.put("startX",rawData.getStartX());
        rawDataElement.put("startY", rawData.getStartY());
        rawDataElement.put("width", rawData.getWidth());
        rawDataElement.put("height", rawData.getWidth());
        rawDataElement.put("baseUiComponentStartX", rawData.getBaseUiComponentStartX());
        rawDataElement.put("baseUiComponentStartY", rawData.getBaseUiComponentStartY());
        rawDataElement.put("baseUiComponentWidth", rawData.getBaseUiComponentWidth());
        rawDataElement.put("baseUiComponentHeight", rawData.getBaseUiComponentHeight());

        textElementObject.put("rawData" , rawDataElement);

        BasicDBObject updateObject = new BasicDBObject();
        updateObject.put("$push", new BasicDBObject("textDataElements", textElementObject));
        infoColl.update(searchQuery, updateObject);
    }

    /* Method updates the previously available record of imageDataParser in the templateInfo collection  */

    public void updateTemplateInfo(ImageDataParser imageDataParser,ImageDataElement imageDataElement){

        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put("id", imageDataParser.getId());
        searchQuery.put("dataType", imageDataParser.getDataType());

        BasicDBObject imageElementObject = new BasicDBObject();

        imageElementObject.put("metaId",imageDataElement.getMetaId());
        imageElementObject.put("pageNumber",imageDataElement.getPageNumber());
        imageElementObject.put("pageRotation",imageDataElement.getPageRotation());

        imageElementObject.put("totalX1",imageDataElement.getTotalX1());
        imageElementObject.put("totalY1",imageDataElement.getTotalY1());
        imageElementObject.put("totalWidth",imageDataElement.getTotalWidth());
        imageElementObject.put("totalHeight",imageDataElement.getTotalHeight());

        BasicDBObject rawDataElement = new BasicDBObject();
        RawDataElement rawData= imageDataElement.getRawData();
        rawDataElement.put("id", rawData.getId());
        rawDataElement.put("elementType", rawData.getElementType());
        rawDataElement.put("startX",rawData.getStartX());
        rawDataElement.put("startY", rawData.getStartY());
        rawDataElement.put("width", rawData.getWidth());
        rawDataElement.put("height", rawData.getWidth());
        rawDataElement.put("baseUiComponentStartX", rawData.getBaseUiComponentStartX());
        rawDataElement.put("baseUiComponentStartY", rawData.getBaseUiComponentStartY());
        rawDataElement.put("baseUiComponentWidth", rawData.getBaseUiComponentWidth());
        rawDataElement.put("baseUiComponentHeight", rawData.getBaseUiComponentHeight());

        imageElementObject.put("rawData" , rawDataElement);

        BasicDBObject updateObject=new BasicDBObject();
        updateObject.put("$push",new BasicDBObject("imageDataElements", imageElementObject));
        infoColl.update(searchQuery, updateObject);


    }


    /* Method updates the previously available record of imageDataParser in the templateInfo collection  */

    public void updateTemplateInfo(TableDataParser tableDataParser,TableDataElement tableDataElement){

        BasicDBObject searchQuery = new BasicDBObject();

        searchQuery.put("id", tableDataParser.getId());
        searchQuery.put("dataType", tableDataParser.getDataType());

        BasicDBObject tableElementObject = new BasicDBObject();

        tableElementObject.put("metaId",tableDataElement.getMetaId());
        tableElementObject.put("pageNumber",tableDataElement.getPageNumber());
        tableElementObject.put("pageRotation",tableDataElement.getPageRotation());

        tableElementObject.put("totalX1",tableDataElement.getTotalX1());
        tableElementObject.put("totalY1",tableDataElement.getTotalY1());
        tableElementObject.put("totalWidth",tableDataElement.getTotalWidth());
        tableElementObject.put("totalHeight",tableDataElement.getTotalHeight());

        BasicDBObject rawDataElement = new BasicDBObject();
        RawDataElement rawData= tableDataElement.getRawData();
        rawDataElement.put("id", rawData.getId());
        rawDataElement.put("elementType", rawData.getElementType());
        rawDataElement.put("startX",rawData.getStartX());
        rawDataElement.put("startY", rawData.getStartY());
        rawDataElement.put("width", rawData.getWidth());
        rawDataElement.put("height", rawData.getWidth());
        rawDataElement.put("baseUiComponentStartX", rawData.getBaseUiComponentStartX());
        rawDataElement.put("baseUiComponentStartY", rawData.getBaseUiComponentStartY());
        rawDataElement.put("baseUiComponentWidth", rawData.getBaseUiComponentWidth());
        rawDataElement.put("baseUiComponentHeight", rawData.getBaseUiComponentHeight());

        tableElementObject.put("rawData" , rawDataElement);

        List<Column> columns=tableDataElement.getColumns();
        ArrayList columnData = new ArrayList();

        for(Column c:columns){
            columnData.add(new BasicDBObject("metaId",c.getMetaId()).append("metaX1",c.getMetaX1())
                    .append("metaY1",c.getMetaY1()).append("metaWidth",c.getMetaWidth())
                    .append("metaHeight",c.getMetaHeight()));
        }
        tableElementObject.put("columns",columnData);

        BasicDBObject updateObject=new BasicDBObject();
        updateObject.put("$push",new BasicDBObject("tableDataElements", tableElementObject));
        infoColl.update(searchQuery, updateObject);

    }

}
