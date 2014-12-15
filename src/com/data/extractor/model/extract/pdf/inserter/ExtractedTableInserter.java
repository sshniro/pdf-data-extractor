package com.data.extractor.model.extract.pdf.inserter;

import com.data.extractor.model.beans.extract.pdf.ExtractStatus;
import com.data.extractor.model.beans.template.info.table.Cell;
import com.data.extractor.model.beans.template.info.table.Column;
import com.data.extractor.model.beans.template.info.table.TableDataElement;
import com.data.extractor.model.beans.template.info.table.TableDataParser;
import com.data.extractor.model.beans.template.info.text.TextDataElement;
import com.data.extractor.model.data.access.layer.ExtractedDataDAO;
import com.data.extractor.model.db.connect.dbInitializer;
import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;


public class ExtractedTableInserter {

    private static final String host="localhost";
    private static final String dbName="staging";
    private static final String templatesColl="extractedData";
    private static final int    port=27017;


    public void insert(TableDataParser tableDataParser,ExtractStatus extractStatus , MongoClient mongoClient){

//        dbInitializer dbInitializer = new dbInitializer();
//        DB db=dbInitializer.getDB(mongoClient,dbName);
//        DBCollection templateCollection = dbInitializer.getCollection(db, templatesColl);
//
//        BasicDBObject searchQuery = new BasicDBObject();
//        searchQuery.put("mainCategory", tableDataParser.getMainCategory());
//        searchQuery.put("subCategory", tableDataParser.getSubCategory());
//        searchQuery.put("templateName", tableDataParser.getTemplateName());
//        searchQuery.put("documentId", extractStatus.getDocumentId());
//        searchQuery.put("dataType", tableDataParser.getDataType());
//
//        List<TableDataElement> tableDataElements = tableDataParser.getTableDataElements();
//
//        for(TableDataElement ta:tableDataElements){
//            DBCursor templateCursor = templateCollection.find(searchQuery);
//
//            if (templateCursor.hasNext()) {
//                /* If record exists update the record   */
//                updateRecord(ta,templateCollection, searchQuery);
//            } else {
//                /* If there is no record exists create record and input */
//                createNewRecord(tableDataParser, ta,templateCollection,extractStatus.getDocumentId());
//            }
//        }


        List<TableDataElement> tableDataElements= tableDataParser.getTableDataElements();
        ExtractedDataDAO extractedDataDAO = new ExtractedDataDAO(mongoClient);
        TableDataElement tableDataElement;
        int recordsSize=0;

        for(int i=0;i<tableDataElements.size();i++){

            tableDataElement=tableDataElements.get(i);

            /* Only check once from the DB when the loop starts */
            if(i==0){

                recordsSize = extractedDataDAO.getRecordsSizeOfId(tableDataParser.getId(),tableDataParser.getDataType());
            }

            if (recordsSize == 0) {
                /* If there is no record exists create a new record and insert */
                extractedDataDAO.createTemplateInfo(tableDataParser.getId() , extractStatus.getParent() , tableDataParser.getDataType(),tableDataElement);
                recordsSize = 1;

            } else {
                /* If record exists update the record */
                extractedDataDAO.updateTemplateInfo(tableDataParser,tableDataElement);
            }
        }
    }
//
//    public void createNewRecord(TableDataParser tableDataParser, TableDataElement tableDataElement,
//                                DBCollection templateCollection,String documentId ) {
//
//        BasicDBObject insertObject = new BasicDBObject();
//        insertObject.put("mainCategory", tableDataParser.getMainCategory());
//        insertObject.put("subCategory", tableDataParser.getSubCategory());
//        insertObject.put("templateName", tableDataParser.getTemplateName());
//        insertObject.put("documentId", documentId);
//        insertObject.put("dataType", tableDataParser.getDataType());
//
//        List<BasicDBObject> tableDataElementsInsert=new ArrayList<BasicDBObject>();
//
//        BasicDBObject tableElementObject=new BasicDBObject();
//
//        tableElementObject.put("metaId",tableDataElement.getMetaId());
//        tableElementObject.put("pageNumber",tableDataElement.getPageNumber());
//        tableElementObject.put("pageRotation", tableDataElement.getPageRotation());
//
//        tableElementObject.put("totalX1",tableDataElement.getTotalX1());
//        tableElementObject.put("totalY1", tableDataElement.getTotalY1());
//        tableElementObject.put("totalWidth",tableDataElement.getTotalWidth());
//        tableElementObject.put("totalHeight",tableDataElement.getTotalHeight());
//
//        List<Column> columns=tableDataElement.getColumns();
//        ArrayList columnData = new ArrayList();
//        //String extractedValues;
//
//
//        for(Column c:columns){
//
//            //extractedValues=c.getExtractedValues();
//            //String[] splitData=processExtractedTable(extractedValues);
//            List<Cell> cellList=c.getCellList();
//            List<BasicDBObject> row=new ArrayList<BasicDBObject>();
//            for(Cell cell:cellList){
//                row.add(new BasicDBObject("row",cell.getValue().toString()));
//            }
//            columnData.add(new BasicDBObject("metaId",c.getMetaId()).append("rowValues",row));
//
//        }
//
//        tableElementObject.put("columns",columnData);
//        tableDataElementsInsert.add(tableElementObject);
//        insertObject.put("tableDataElements",tableDataElementsInsert);
//        templateCollection.insert(insertObject);
//
//    }
//
//    public String[] processExtractedTable(String extractedValues){
//        String[] splitData=extractedValues.split("\\r?\\n");
//        return splitData;
//    }
//
//    public void updateRecord(TableDataElement tableDataElement,
//                             DBCollection templateCollection , BasicDBObject searchQuery ){
//
//        BasicDBObject tableElementObject=new BasicDBObject();
//
//        tableElementObject.put("metaId",tableDataElement.getMetaId());
//        tableElementObject.put("pageNumber",tableDataElement.getPageNumber());
//        tableElementObject.put("pageRotation", tableDataElement.getPageRotation());
//
//        tableElementObject.put("totalX1",tableDataElement.getTotalX1());
//        tableElementObject.put("totalY1", tableDataElement.getTotalY1());
//        tableElementObject.put("totalWidth",tableDataElement.getTotalWidth());
//        tableElementObject.put("totalHeight",tableDataElement.getTotalHeight());
//
//        List<Column> columns=tableDataElement.getColumns();
//        ArrayList columnData = new ArrayList();
//        //String extractedValues;
//
//
//        for(Column c:columns){
//
//            //extractedValues=c.getExtractedValues();
//            //String[] splitData=processExtractedTable(extractedValues);
//            List<Cell> cellList=c.getCellList();
//            List<BasicDBObject> row=new ArrayList<BasicDBObject>();
//            for(Cell cell:cellList){
//                row.add(new BasicDBObject("row",cell.getValue().toString()));
//            }
//            columnData.add(new BasicDBObject("metaId",c.getMetaId()).append("rowValues",row));
//
//        }
//
//        tableElementObject.put("columns",columnData);
//
//        BasicDBObject updateObject = new BasicDBObject();
//        updateObject.put("$push", new BasicDBObject("tableDataElements", tableElementObject));
//        templateCollection.update(searchQuery, updateObject);
//
//    }
}
