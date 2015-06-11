package com.data.extractor.controllers;

import com.data.extractor.model.beans.template.info.insert.InsertDataParser;
import com.data.extractor.model.beans.template.info.table.TableDataElement;
import com.data.extractor.model.beans.template.info.table.TableDataParser;

import java.util.ArrayList;
import java.util.List;

public class Testing {
    public static void main(String[] args) {
        InsertDataParser insertDataParser=new InsertDataParser();
        insertDataParser = setValues(insertDataParser);

        List<List<String>> complex;

        complex = findSimilarities(insertDataParser.getTableDataParser());
        System.out.println("testing");



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
