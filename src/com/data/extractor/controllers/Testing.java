package com.data.extractor.controllers;

import com.data.extractor.model.beans.template.info.RawDataElement;
import com.data.extractor.model.beans.template.info.insert.InsertDataParser;
import com.data.extractor.model.beans.template.info.regex.RegexDataElement;
import com.data.extractor.model.beans.template.info.regex.RegexEndElement;
import com.data.extractor.model.beans.template.info.regex.RegexPairElement;
import com.data.extractor.model.beans.template.info.regex.RegexStartElement;
import com.data.extractor.model.beans.template.info.table.TableDataElement;
import com.data.extractor.model.beans.template.info.table.TableDataParser;
import com.data.extractor.model.data.access.layer.TemplateInfoDAO;
import com.mongodb.MongoClient;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class Testing {
    public static void main(String[] args) throws UnknownHostException {
//        InsertDataParser insertDataParser=new InsertDataParser();
//        insertDataParser = setValues(insertDataParser);
//
//        List<List<String>> complex;
//
//        complex = findSimilarities(insertDataParser.getTableDataParser());
//        System.out.println("testing");

        MongoClient mongoClient = new MongoClient("localhost",27017);
        RegexDataElement regexDataElement = new RegexDataElement();
        RawDataElement rawDataElement = new RawDataElement();

        RegexStartElement regexStartElement = new RegexStartElement();
        RegexEndElement regexEndElement = new RegexEndElement();

        regexStartElement.setTag("a");

        regexEndElement.setTag("a");

        RegexPairElement regexPairElement = new RegexPairElement();
        regexPairElement.setRegexStartElement(regexStartElement);
        regexPairElement.setRegexEndElement(regexEndElement);

        List<RegexPairElement> regexPairElements = new ArrayList<RegexPairElement>();
        regexPairElements.add(regexPairElement);

        regexDataElement.setRegexPairElements(regexPairElements);
        regexDataElement.setMetaName("1");
        regexDataElement.setRawData(rawDataElement);

        TemplateInfoDAO templateInfoDAO = new TemplateInfoDAO(mongoClient);

        templateInfoDAO.createTemplateInfo("1","regex",regexDataElement);

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
