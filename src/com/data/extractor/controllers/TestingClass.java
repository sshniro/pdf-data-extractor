package com.data.extractor.controllers;

import com.data.extractor.model.beans.template.info.pattern.*;
import com.data.extractor.model.beans.template.info.regex.RegexDataElement;

import java.util.ArrayList;
import java.util.List;

public class TestingClass {

    public static void main(String[] args) {

        PatternDataParser patternDataParser = new PatternDataParser();
        List<PatternDataElement> patternDataElementList = new ArrayList<PatternDataElement>();

        PatternDataElement patternDataElement = new PatternDataElement();

        ColumnDataElement columnDataElement = new ColumnDataElement();
        ColumnDataElement columnDataElement2 = new ColumnDataElement();
        RegexDataElement regexDataElement = new RegexDataElement();

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



    }

}
