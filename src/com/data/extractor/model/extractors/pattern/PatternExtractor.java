package com.data.extractor.model.extractors.pattern;

import com.data.extractor.model.beans.template.info.pattern.ColumnDataElement;
import com.data.extractor.model.beans.template.info.pattern.ColumnEndElement;
import com.data.extractor.model.beans.template.info.pattern.ColumnStartElement;
import com.data.extractor.model.beans.template.info.pattern.PatternDataElement;
import com.data.extractor.model.beans.template.info.regex.RegexDataElement;
import com.data.extractor.model.beans.template.info.regex.RegexEndElement;
import com.data.extractor.model.beans.template.info.regex.RegexPairElement;
import com.data.extractor.model.beans.template.info.regex.RegexStartElement;
import com.data.extractor.model.beans.template.info.table.Cell;

import java.util.ArrayList;
import java.util.List;

public class PatternExtractor {

    public List<PatternDataElement> extractPattern(String rawText , PatternDataElement patternDataElement){

        String[] splits = null;
        String[] regexSplits = null;

        String regexLastEnded = rawText;
        String columnLastEnded = rawText;

        String regexStarted = rawText;
        String columnStarted = rawText;

        String extractedValue;

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
    return extractedPatternElement;
    }

}
