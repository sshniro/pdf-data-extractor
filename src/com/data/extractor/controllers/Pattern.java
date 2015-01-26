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
        String processing = null;
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


        // Must have a logic to break this loop
        Boolean status = true;
        while (status){

            for (int i =0; i<regexPairElementList.size();i++){
                RegexPairElement regexPair = regexPairElementList.get(i);
                extractedValue = new String();
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

            while (columnStatus){

                for (int i=0; i < columnDataElementList.size();i++){

                    ColumnDataElement columnDataElement =columnDataElementList.get(i);
                    List<Cell> cellList = columnDataElement.getCellList();

                    ColumnStartElement columnStartElement = columnDataElement.getColumnStartElement();
                    ColumnEndElement columnEndElement = columnDataElement.getColumnEndElement();


                    try {


                        splits = columnStarted.split(columnStartElement.getTag(),2);

                        if(columnEndElement.getTag().equals("eol")){
                            columnEndElement.setTag(System.getProperty("line.separator"));
                        }

                        splits = splits[1].split(columnEndElement.getTag(),2);
                        splits[1] = columnEndElement.getTag() + splits [1];
                        columnStarted = splits[1];
                        Cell cell = new Cell();
                        cell.setValue(splits[0]);
                        cellList.add(cell);

                        if(i == columnDataElementList.size() -1){
                        // Check if the next line start with the word otherwise break.
                            ColumnStartElement testElement = columnDataElementList.get(0).getColumnStartElement();
                            int j = splits[1].indexOf(testElement.getTag());
                            if(j > 3 ){
                                columnStatus =false;
                                break;
                            }
                        }
                        try {

                        }catch (ArrayIndexOutOfBoundsException e){

                        }
                    }catch (ArrayIndexOutOfBoundsException e){
                        columnStatus =false;
                        break;
                    }

                }
            }

        }
        System.out.println("done");


//        r2.setStartTag("heelo");
//        r2.setEndTag("");
//        r2.setMetaName("");
//
//        regexDataElementList1.add(r2);
//
//        ColumnDataElement c1=new ColumnDataElement();
//
//        c1.setColumnName("");
//        c1.setColumnStartTag("");
//        c1.setColumnEndTag("");
//
//        ColumnDataElement c2 = new ColumnDataElement();
//
//        c2.setColumnName("");
//        c2.setColumnStartTag("");
//        c2.setColumnEndTag("");
//
//        columnDataElementList.add(c1);
//        columnDataElementList.add(c2);
//
//        List<List<Cell>> complexCellList = new ArrayList<List<Cell>>();
//        List<Cell> cellList = new ArrayList<Cell>();
//
//        for (ColumnDataElement c : columnDataElementList){
//
//            splits = unprocessed.split(c.getColumnStartTag(),2);
//
//            if(c.getColumnEndTag().equals("eol")){
//                splits  = splits[1].split(System.getProperty("line.separator"),2);
//            }else {
//                splits  = splits[1].split(c.getColumnEndTag(),2);
//            }
//
//            splits[1] = c.getColumnEndTag() + splits[1];
//            String cellValue = splits[0];
//
//
//        }

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


