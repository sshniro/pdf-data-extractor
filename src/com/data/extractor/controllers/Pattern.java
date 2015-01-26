package com.data.extractor.controllers;

import com.data.extractor.model.beans.template.info.pattern.*;
import com.data.extractor.model.beans.template.info.regex.RegexDataElement;
import com.data.extractor.model.beans.template.info.regex.RegexEndElement;
import com.data.extractor.model.beans.template.info.regex.RegexPairElement;
import com.data.extractor.model.beans.template.info.regex.RegexStartElement;
import com.data.extractor.model.beans.template.info.table.Cell;
import com.mongodb.MongoClient;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Pattern {

    // Extract text from PDF Document
    static StringBuilder pdftoText(String fileName) {
        PDFParser parser;
        String parsedText = null;;
        PDFTextStripper pdfStripper = null;
        PDDocument doc = null;
        COSDocument cosDoc = null;
        StringBuilder sb = new StringBuilder("");
        File file = new File(fileName);
        if (!file.isFile()) {
            System.err.println("File " + fileName + " does not exist.");
            return null;
        }
        try {
            parser = new PDFParser(new FileInputStream(file));
        } catch (IOException e) {
            System.err.println("Unable to open PDF Parser. " + e.getMessage());
            return null;
        }
        try {
            parser.parse();
            cosDoc = parser.getDocument();
            pdfStripper = new PDFTextStripper();
            doc = new PDDocument(cosDoc);
            int pageNumber = doc.getNumberOfPages();
            pdfStripper.setStartPage(1);
            pdfStripper.setEndPage(pageNumber);
            //parsedText = pdfStripper.getText(doc);
            sb.append(pdfStripper.getText(doc));
            //System.out.println(doc.getNumberOfPages());
        } catch (Exception e) {
            System.err
                    .println("An exception occured in parsing the PDF Document."
                            + e.getMessage());
        } finally {
            try {
                if (cosDoc != null)
                    cosDoc.close();
                if (doc != null)
                    doc.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sb;
    }
    public static void main(String args[]){

        StringBuilder sb=pdftoText("/Users/niro273/Desktop/test.pdf");
        String unprocessed = sb.toString();
        String[] splits = null;
        String[] regexSplits = null;

        String regexLastEnded = unprocessed;
        String columnLastEnded = unprocessed;

        String regexStarted = unprocessed;
        String columnStarted = unprocessed;

        String extractedValue;

        PatternDataParser patternDataParser = new PatternDataParser();
        patternDataParser = assignValues(patternDataParser);

        List<PatternDataElement> patternDataElementList = patternDataParser.getPatternDataElements();
        PatternDataElement patternDataElement = patternDataElementList.get(0);

        List<ColumnDataElement> columnDataElementList = patternDataElement.getColumnDataElements();
        RegexDataElement regexDataElement = patternDataElement.getRegexDataElements();

        List<RegexPairElement> regexPairElementList = regexDataElement.getRegexPairElements();

        List<RegexPairElement> extractedPairElementsList = new ArrayList<RegexPairElement>();
        RegexPairElement extractedPairElement = new RegexPairElement();

        List<PatternDataElement> extractedPatternElement = new ArrayList<PatternDataElement>();

        List<ColumnDataElement> extractedColumnList = new ArrayList<ColumnDataElement>();


        // Must have a logic to break this loop
        Boolean status = true;
        while (status){

            for (int i =0; i<regexPairElementList.size();i++){
                RegexPairElement regexPair = regexPairElementList.get(i);
                RegexStartElement regexStartElement = regexPair.getRegexStartElement();
                RegexEndElement regexEndElement = regexPair.getRegexEndElement();

                regexSplits = regexLastEnded.split(regexStartElement.getTag(),2);
                try {

                    if(i == 0){
                        columnStarted = regexSplits[1];
                    }

                    if(regexEndElement.getTag().equals("eol")){
                        regexEndElement.setTag(System.getProperty("line.separator"));
                    }
                    regexSplits = regexSplits[1].split(regexEndElement.getTag(),2);

                    try {
                        regexSplits[1] = regexEndElement.getTag() + regexSplits[1];
                        extractedValue = regexSplits [0];
                        regexPair.setValue(extractedValue);
                        regexLastEnded = regexSplits[1];

                        extractedPairElement = new RegexPairElement();
                        extractedPairElement.setValue(extractedValue);
                        extractedPairElementsList.add(extractedPairElement);
                    }catch (ArrayIndexOutOfBoundsException e){
                        System.out.println("No end Tag with " + regexEndElement.getTag() + " was found");
                        break;
                    }

                }catch (ArrayIndexOutOfBoundsException e){
                    System.out.println("No Start Tag with " + regexStartElement.getTag() + " was found");
                    status =false;
                    break;
                }

            }

            Boolean columnStatus = true;
            ColumnDataElement extractedColumnElement ;

            while (columnStatus){

                for (int i=0; i < columnDataElementList.size();i++){

                    ColumnDataElement columnDataElement =columnDataElementList.get(i);



                    ColumnStartElement columnStartElement = columnDataElement.getColumnStartElement();
                    ColumnEndElement columnEndElement = columnDataElement.getColumnEndElement();

                    try {
                        splits = columnStarted.split(columnStartElement.getTag(),2);

                        if(columnEndElement.getTag().equals("eol")){
                            columnEndElement.setTag(System.getProperty("line.separator"));
                        }

                        splits = splits[1].split(columnEndElement.getTag(),2);
                        splits[1] = columnEndElement.getTag() + splits [1];

                        if (extractedColumnList.size() > i){
                            extractedColumnElement = extractedColumnList.get(i);
                        }else {
                            extractedColumnElement = new ColumnDataElement();
                            extractedColumnList.add(extractedColumnElement);
                        }

                        List<Cell> extractedCellList = extractedColumnElement.getCellList();
                        List<Cell> cellList = columnDataElement.getCellList();

                        columnStarted = splits[1];
                        Cell cell = new Cell();
                        cell.setValue(splits[0]);
                        cellList.add(cell);

                        extractedCellList.add(cell);

                        if(i == columnDataElementList.size() -1){
                        // Check if the next line start with the word otherwise break.
                            ColumnStartElement testElement = columnDataElementList.get(0).getColumnStartElement();
                            int j = splits[1].indexOf(testElement.getTag());
                            if(j > 3 ){
                                columnStatus =false;
                                break;
                            }
                        }
                    }catch (ArrayIndexOutOfBoundsException e){
                        columnStatus =false;
                        break;
                    }
                }
            }


            PatternDataElement pElement = new PatternDataElement();
            if(extractedPairElementsList.size() != 0 ){
                RegexDataElement regexDataElement1 = new RegexDataElement();
                regexDataElement1.setRegexPairElements(extractedPairElementsList);
                pElement.setRegexDataElements(regexDataElement1);
            }
            if(extractedColumnList.size() != 0){
                pElement.setColumnDataElements(extractedColumnList);
            }
            if(extractedPairElementsList.size() != 0 || extractedColumnList.size() != 0){
                extractedPatternElement.add(pElement);
            }

            extractedPairElementsList = new ArrayList<RegexPairElement>();
            extractedColumnList = new ArrayList<ColumnDataElement>();


        }


        for (int j=0 ; j<extractedPatternElement.size();j++){

            PatternDataElement patternElement = extractedPatternElement.get(j);

            RegexDataElement regexDataElements = patternElement.getRegexDataElements();
            List<ColumnDataElement> columnDataElements = patternElement.getColumnDataElements();

            for (int i=0 ; i  < regexDataElements.getRegexPairElements().size(); i++){

                regexDataElements.getRegexPairElements().get(i).setMetaName(patternDataElement.getRegexDataElements().getRegexPairElements().get(i).getMetaName());
                regexDataElements.getRegexPairElements().get(i).setRegexStartElement(patternDataElement.getRegexDataElements().getRegexPairElements().get(i).getRegexStartElement());
                regexDataElements.getRegexPairElements().get(i).setRegexEndElement(patternDataElement.getRegexDataElements().getRegexPairElements().get(i).getRegexEndElement());

            }

            for (int i =0 ; i < columnDataElements.size() ; i++){

                columnDataElements.get(i).setColumnEndElement(patternDataElement.getColumnDataElements().get(i).getColumnEndElement());
                columnDataElements.get(i).setColumnStartElement(patternDataElement.getColumnDataElements().get(i).getColumnStartElement());
                columnDataElements.get(i).setMetaName(patternDataElement.getColumnDataElements().get(i).getMetaName());
            }
        }

    String test = new String("");

    }

    public static List<PatternDataElement> getNewData(List<PatternDataElement> patternDataElements){
        List<PatternDataElement> newData = new ArrayList<PatternDataElement>();
        newData = patternDataElements;
        return newData;
    }

    public static List<PatternDataElement> generateNewModel(List<PatternDataElement> patternDataElements , List<PatternDataElement> patternDataElementList){

        for (int j=0; j < patternDataElements.size() ; j++){

            PatternDataElement p = patternDataElements.get(j);

            p.setRegexDataElements(patternDataElementList.get(j).getRegexDataElements());
            p.setColumnDataElements(patternDataElementList.get(j).getColumnDataElements());
        }

        return patternDataElements;
    }

    public static PatternDataParser assignValues(PatternDataParser patternDataParser){

        List<PatternDataElement> patternDataElementList = new ArrayList<PatternDataElement>();

        PatternDataElement patternDataElement = new PatternDataElement();
        PatternDataElement patternDataElement2 = new PatternDataElement();
        //MongoClient mongoClient = new MongoClient("localhost",27017);

        ColumnDataElement columnDataElement = new ColumnDataElement();
        ColumnDataElement columnDataElement2 = new ColumnDataElement();
        ColumnDataElement columnDataElement3 = new ColumnDataElement();
        RegexDataElement regexDataElement = new RegexDataElement();
        RegexDataElement regexDataElement2 = new RegexDataElement();

        List<RegexDataElement> regexDataElementList = new ArrayList<RegexDataElement>();
        List<ColumnDataElement> columnDataElementList = new ArrayList<ColumnDataElement>();

        ColumnStartElement cs1 = new ColumnStartElement();
        ColumnEndElement ce1 = new ColumnEndElement();

        ColumnStartElement cs2 = new ColumnStartElement();
        ColumnEndElement ce2 = new ColumnEndElement();

        ColumnStartElement cs3 = new ColumnStartElement();
        ColumnEndElement ce3 = new ColumnEndElement();


        cs1.setTag("Size:");
        ce1.setTag("Qty Ordered:");

        cs2.setTag("Qty Ordered:");
        ce2.setTag("Cost per Unit:");

        cs3.setTag("Cost per Unit:");
        ce3.setTag("eol");

        columnDataElement.setColumnStartElement(cs1);
        columnDataElement.setColumnEndElement(ce1);

        columnDataElement2.setColumnStartElement(cs2);
        columnDataElement2.setColumnEndElement(ce2);

        columnDataElement3.setColumnStartElement(cs3);
        columnDataElement3.setColumnEndElement(ce3);

        columnDataElementList.add(columnDataElement);
        columnDataElementList.add(columnDataElement2);
        columnDataElementList.add(columnDataElement3);

        patternDataElement.setColumnDataElements(columnDataElementList);
        patternDataElement2.setColumnDataElements(columnDataElementList);

        RegexStartElement rs1 = new RegexStartElement();
        RegexEndElement re1 = new RegexEndElement();

        RegexStartElement rs2 = new RegexStartElement();
        RegexEndElement re2 = new RegexEndElement();

        rs1.setTag("VSC Color Cde:");
        re1.setTag("VSS Color Code:");

        rs2.setTag("VSS Color Code:");
        re2.setTag("eol");

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


        return patternDataParser;
    }
}


