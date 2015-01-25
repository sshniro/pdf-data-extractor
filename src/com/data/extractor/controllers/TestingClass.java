package com.data.extractor.controllers;

import com.data.extractor.model.beans.template.info.pattern.*;
import com.data.extractor.model.beans.template.info.regex.RegexDataElement;
import com.data.extractor.model.beans.template.info.regex.RegexEndElement;
import com.data.extractor.model.beans.template.info.regex.RegexPairElement;
import com.data.extractor.model.beans.template.info.regex.RegexStartElement;
import com.data.extractor.model.data.access.layer.TemplateInfoDAO;
import com.data.extractor.model.template.markup.insert.coordinate.PatternDataInserter;
import com.mongodb.MongoClient;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class TestingClass {

    public static void main(String[] args) throws UnknownHostException {

        PatternDataParser patternDataParser = new PatternDataParser();
        List<PatternDataElement> patternDataElementList = new ArrayList<PatternDataElement>();

        PatternDataElement patternDataElement = new PatternDataElement();
        PatternDataElement patternDataElement2 = new PatternDataElement();
        MongoClient mongoClient = new MongoClient("localhost",27017);

        ColumnDataElement columnDataElement = new ColumnDataElement();
        ColumnDataElement columnDataElement2 = new ColumnDataElement();
        RegexDataElement regexDataElement = new RegexDataElement();
        RegexDataElement regexDataElement2 = new RegexDataElement();

        List<RegexDataElement> regexDataElementList = new ArrayList<RegexDataElement>();
        List<ColumnDataElement> columnDataElementList = new ArrayList<ColumnDataElement>();

        ColumnStartElement cs1 = new ColumnStartElement();
        ColumnEndElement ce1 = new ColumnEndElement();

        ColumnStartElement cs2 = new ColumnStartElement();
        ColumnEndElement ce2 = new ColumnEndElement();

        cs1.setTag("");
        ce1.setTag("");

        cs2.setTag("");
        ce2.setTag("");

        columnDataElement.setColumnStartElement(cs1);
        columnDataElement.setColumnEndElement(ce1);

        columnDataElement2.setColumnStartElement(cs2);
        columnDataElement2.setColumnEndElement(ce2);

        columnDataElementList.add(columnDataElement);
        columnDataElementList.add(columnDataElement2);

        patternDataElement.setColumnDataElements(columnDataElementList);
        patternDataElement2.setColumnDataElements(columnDataElementList);

        RegexStartElement rs1 = new RegexStartElement();
        RegexEndElement re1 = new RegexEndElement();

        RegexStartElement rs2 = new RegexStartElement();
        RegexEndElement re2 = new RegexEndElement();

        rs1.setTag("");
        re1.setTag("");

        rs2.setTag("");
        re2.setTag("");

        RegexPairElement regexPairElement = new RegexPairElement();
        RegexPairElement regexPairElement2 = new RegexPairElement();

        regexPairElement.setRegexStartElement(rs1);
        regexPairElement.setRegexEndElement(re1);

        regexPairElement2.setRegexStartElement(rs2);
        regexPairElement2.setRegexEndElement(re2);

        List<RegexPairElement> regexPairElementList = new ArrayList<RegexPairElement>();
        regexPairElementList.add(regexPairElement);
        regexPairElementList.add(regexPairElement2);

        regexDataElement.setRegexPairElements(regexPairElementList);
        regexDataElement2.setRegexPairElements(regexPairElementList);

        regexDataElementList.add(regexDataElement);
        //regexDataElementList.add(regexDataElement2);

        //patternDataElement.setRegexDataElements(regexDataElementList);
        patternDataElement.setRegexDataElements(regexDataElement);
        patternDataElement2.setRegexDataElements(regexDataElement2);

        patternDataElementList.add(patternDataElement);
        patternDataElementList.add(patternDataElement2);

        patternDataParser.setPatternDataElements(patternDataElementList);
        patternDataParser.setDataType("pattern");
        patternDataParser.setId("3");

        TemplateInfoDAO templateInfoDAO = new TemplateInfoDAO(mongoClient);
        templateInfoDAO.getPatternTemplateInfo("3","pattern");

        //PatternDataInserter patternDataInserter = new PatternDataInserter();
        //patternDataInserter.insert(patternDataParser,mongoClient);



    }

}
