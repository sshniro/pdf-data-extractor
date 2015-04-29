package com.data.extractor.controllers;

import com.data.extractor.model.beans.template.info.pattern.*;
import com.data.extractor.model.beans.template.info.regex.RegexDataElement;
import com.data.extractor.model.beans.template.info.regex.RegexEndElement;
import com.data.extractor.model.beans.template.info.regex.RegexPairElement;
import com.data.extractor.model.beans.template.info.regex.RegexStartElement;
import com.data.extractor.model.data.access.layer.TemplateInfoDAO;
import com.data.extractor.model.template.markup.insert.coordinate.PatternDataInserter;
import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestingClass {

    public static void main(String[] args) throws UnknownHostException {

        PatternDataParser patternDataParser = new PatternDataParser();
        List<PatternDataElement> patternDataElementList = new ArrayList<PatternDataElement>();

        PatternDataElement patternDataElement = new PatternDataElement();
        PatternDataElement patternDataElement2 = new PatternDataElement();
        MongoClient mongoClient = new MongoClient("localhost",27017);

        DB db=mongoClient.getDB("staging");
        DBCollection collection =  db.getCollection("test");

        BasicDBObject insertObject = new BasicDBObject("date",new Date()).append("id","10");
        Date date=new Date(2015,05,15);
        collection.insert(insertObject);

        BasicDBObject searchObject = new BasicDBObject("id","10");
        DBObject dbObject = collection.findOne(searchObject);
        String test= new String();


        // Search Query

        // get the results

    }

}
