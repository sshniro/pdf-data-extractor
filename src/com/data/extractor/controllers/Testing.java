package com.data.extractor.controllers;

import com.data.extractor.model.beans.template.info.RawDataElement;
import com.data.extractor.model.beans.template.info.insert.InsertDataParser;
import com.data.extractor.model.beans.template.info.regex.*;
import com.data.extractor.model.beans.template.info.table.TableDataElement;
import com.data.extractor.model.beans.template.info.table.TableDataParser;
import com.data.extractor.model.beans.user.UserBean;
import com.data.extractor.model.data.access.layer.TemplateInfoDAO;
import com.data.extractor.model.data.access.layer.TemplatesDAO;
import com.data.extractor.model.data.access.layer.UsersDAO;
import com.data.extractor.model.db.connect.dbInitializer;
import com.google.gson.Gson;
import com.mongodb.*;

import java.io.File;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class Testing {

    private static final String dbName="staging";
    private static final String collectionName="extractedData";
    private static DBCollection collection;

    public static void main(String[] args) throws UnknownHostException {


        String fullPath = "http://enh-external.cloudapp.net:8080/home/enhanzer/tomcat/apace-tomcat/webapps/ROOT/uploads/temp/26/19/19.xlsx";
        String[] relativePath = fullPath.split("uploads",2);
        relativePath[1] = File.separator + "uploads" + relativePath[1];
        System.out.println(relativePath[1]);














//        InsertDataParser insertDataParser=new InsertDataParser();
//        insertDataParser = setValues(insertDataParser);
//
//        List<List<String>> complex;
//
//        complex = findSimilarities(insertDataParser.getTableDataParser());
//        System.out.println("testing");

//        MongoClient mongoClient = new MongoClient("localhost",27017);
//        dbInitializer dbInitializer =new dbInitializer();
//
//        DB db=dbInitializer.getDB(mongoClient,dbName);
//        collection = dbInitializer.getCollection(db,collectionName);
//
//        BasicDBObject searchQuery = new BasicDBObject();
//
//        searchQuery.put("id", "31");
//        searchQuery.put("dataType", "regex");

//        DBCursor cursor = collection.find(searchQuery);
//        Gson gson = new Gson();
//        List<RegexDataParser> regexDataParserList = new ArrayList<RegexDataParser>();
//        while (cursor.hasNext()){
//            RegexDataParser regexDataParser= gson.fromJson(cursor.next().toString(), RegexDataParser.class);
//            regexDataParserList.add(regexDataParser);
//        }
//
//        BasicDBObject regexElementObj = new BasicDBObject();
//
//        regexElementObj.put("metaName", 1);
//        regexElementObj.put("dictionaryId", 2);
//
//        BasicDBObject regexPairObj = new BasicDBObject();
//        ArrayList regexPairData = new ArrayList();
//
//
//
//            BasicDBObject startElementObj = new BasicDBObject();
//            BasicDBObject endElementObj = new BasicDBObject();
//
//            startElementObj.put("tag",3);
//            endElementObj.put("tag",4);
//
//            regexPairData.add(new BasicDBObject("regexStartElement",startElementObj)
//                    .append("regexEndElement",endElementObj).append("value",6)
//                    .append("metaName",8).append("dictionaryId",7)
//                    .append("dictionaryName",6));
//
//
//        regexElementObj.put("regexPairElements", regexPairData);
//
//        BasicDBObject updateObject = new BasicDBObject();
//        updateObject.put("$push", new BasicDBObject("regexDataElements", regexElementObj));
//        collection.update(searchQuery, updateObject);
//
//
//        int x=0;

    }

    public static List<List<String>> findSimilarities(TableDataParser tableDataParser){
        List<TableDataElement> tableDataElements= tableDataParser.getTableDataElements();
        List<String> metaNamesList = new ArrayList<String>();

        List<String> matchedNames = new ArrayList<String>();


        for (TableDataElement ta : tableDataElements){
            metaNamesList.add(new String(ta.getMetaName()));
        }

        for (String metaName : metaNamesList){
            if (metaNamesList.contains(metaName)){
                if(!matchedNames.contains(metaName)){
                    matchedNames.add(metaName);
                }
            }
        }

        List<List<String>> complex = new ArrayList<List<String>>();
        List<String> lessComplex;
        for (String name : matchedNames){
            lessComplex=new ArrayList<String>();
            for(TableDataElement ta : tableDataElements){
                if(ta.getMetaName().equals(name)){
                    lessComplex.add(ta.getMetaId());
                }
            }

            if(lessComplex.size() != 0){
                complex.add(lessComplex);
            }
        }

        return complex;
    }


    public static InsertDataParser setValues(InsertDataParser insertDataParser){

        TableDataParser tableDataParser = new TableDataParser();

        TableDataElement tableDataElement1 = new TableDataElement();
        TableDataElement tableDataElement2 = new TableDataElement();
        TableDataElement tableDataElement3 = new TableDataElement();
        TableDataElement tableDataElement4 = new TableDataElement();
        TableDataElement tableDataElement5 = new TableDataElement();
        TableDataElement tableDataElement6 = new TableDataElement();

        tableDataElement1.setMetaName("table1");
        tableDataElement2.setMetaName("table2");
        tableDataElement3.setMetaName("table1");
        tableDataElement4.setMetaName("table1");
        tableDataElement5.setMetaName("table2");
        tableDataElement6.setMetaName("table3");

        tableDataElement1.setMetaId("meta1");
        tableDataElement2.setMetaId("meta2");
        tableDataElement3.setMetaId("meta3");
        tableDataElement4.setMetaId("meta4");
        tableDataElement5.setMetaId("meta5");
        tableDataElement6.setMetaId("meta6");


        List<TableDataElement> tableDataElementList = new ArrayList<TableDataElement>();
        tableDataElementList.add(tableDataElement1);
        tableDataElementList.add(tableDataElement2);
        tableDataElementList.add(tableDataElement3);
        tableDataElementList.add(tableDataElement4);
        tableDataElementList.add(tableDataElement5);
        tableDataElementList.add(tableDataElement6);

        tableDataParser.setTableDataElements(tableDataElementList);

        insertDataParser.setTableDataParser(tableDataParser);


        return insertDataParser;
    }
}
