package com.data.extractor.model.template.markup.insert.coordinate;


import com.data.extractor.model.beans.template.info.pattern.*;
import com.data.extractor.model.beans.template.info.regex.*;
import com.data.extractor.model.data.access.layer.TemplateInfoDAO;
import com.mongodb.MongoClient;

import java.net.UnknownHostException;
import java.util.List;

public class PatternDataInserter {

    public void insert(PatternDataParser patternDataParser, MongoClient mongoClient) throws UnknownHostException {

        List<PatternDataElement> patternDataElementList = patternDataParser.getPatternDataElements();

        TemplateInfoDAO templateInfoDAO=new TemplateInfoDAO(mongoClient);
        PatternDataElement patternDataElement;
        int templateInfoSize=0;
        int index = 0;

        patternDataParser = removeNextLine(patternDataParser);

        for (PatternDataElement p : patternDataElementList){

                if(index==0){
                    templateInfoSize=templateInfoDAO.getTemplateInfoSize(patternDataParser.getId(),patternDataParser.getDataType());
                }

                // Create New Record or Update the Record
                if(templateInfoSize == 0){
                    // create new record;
                    templateInfoDAO.createTemplateInfo(patternDataParser.getId() ,patternDataParser.getDataType(),p);
                }else {
                    // Record Exists so update the record
                    templateInfoDAO.updateTemplateInfo(patternDataParser,p);
                }
            index = 1;
            templateInfoSize=1;
        }
    }

    public PatternDataParser removeNextLine(PatternDataParser patternDataParser){

        List<PatternDataElement> patternDataElementList= patternDataParser.getPatternDataElements();

        for (PatternDataElement patternElement:patternDataElementList){
            RegexDataElement regexDataElement = patternElement.getRegexDataElements();
            List<ColumnDataElement> columnDataElementList = patternElement.getColumnDataElements();

            List<RegexPairElement> regexPairElementList = regexDataElement.getRegexPairElements();

            for (RegexPairElement regexPair:regexPairElementList){

                RegexStartElement regexStartElement = regexPair.getRegexStartElement();
                RegexEndElement regexEndElement = regexPair.getRegexEndElement();

                regexStartElement.setTag(regexStartElement.getTag().trim());
                regexEndElement.setTag(regexEndElement.getTag().trim());
            }

            for (ColumnDataElement columnElement : columnDataElementList){

                ColumnStartElement columnStartElement = columnElement.getColumnStartElement();
                ColumnEndElement columnEndElement= columnElement.getColumnEndElement();

                columnStartElement.setTag(columnStartElement.getTag().trim());
                columnEndElement.setTag(columnEndElement.getTag().trim());
            }
        }

        return patternDataParser;
    }

}
