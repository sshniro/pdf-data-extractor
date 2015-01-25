package com.data.extractor.model.template.markup.insert.coordinate;


import com.data.extractor.model.beans.template.info.pattern.ColumnDataElement;
import com.data.extractor.model.beans.template.info.pattern.PatternDataElement;
import com.data.extractor.model.beans.template.info.pattern.PatternDataParser;
import com.data.extractor.model.beans.template.info.regex.RegexDataElement;
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

        for (PatternDataElement p : patternDataElementList){

            RegexDataElement regexDataElement= p.getRegexDataElement();
            List<ColumnDataElement> columnDataElementList = p.getColumnDataElements();



                if(index==0){
                    templateInfoSize=templateInfoDAO.getTemplateInfoSize(patternDataParser.getId(),patternDataParser.getDataType());
                }

                // Create New Record or Update the Record
                if(templateInfoSize == 0){
                    // create new record;
                    templateInfoDAO.createTemplateInfo(patternDataParser.getId() ,patternDataParser.getDataType(),p);
                }else {
                    // Record Exists so update the record
                    //templateInfoDAO.updatePatternRegexTemplateInfo(patternDataParser,r);
                }
                index = 1;


            for (ColumnDataElement c : columnDataElementList){

                if(index==0){
                    templateInfoSize=templateInfoDAO.getTemplateInfoSize(patternDataParser.getId(),patternDataParser.getDataType());
                }
                // Create New Record or Update the Record
                if(templateInfoSize == 0){
                    // create new record
                    templateInfoSize=templateInfoDAO.getTemplateInfoSize(patternDataParser.getId(),patternDataParser.getDataType());
                }else {
                    // Record exists so update the record
                    templateInfoDAO.updateTemplateInfo(patternDataParser,p);
                }
                index = 1;
            }
        }


//
//        List<PatternDataElement> patternDataElements = patternDataParser.getPatternDataElementList();
//
//        TemplateInfoDAO templateInfoDAO=new TemplateInfoDAO(mongoClient);
//        PatternDataElement patternDataElement;
//        int templateInfoSize=0;
//
//        for(int i=0;i< patternDataElements.size();i++){
//
//            patternDataElement = patternDataElements.get(i);
//
//            /* Only check once from the DB when the loop starts */
//            if(i==0){
//                templateInfoSize=templateInfoDAO.getTemplateInfoSize(patternDataParser.getId(),patternDataParser.getDataType());
//            }
//
//            if (templateInfoSize == 0) {
//                /* If there is no record exists create a new record and insert */
//                templateInfoDAO.createTemplateInfo(patternDataParser.getId() ,patternDataParser.getDataType(),patternDataElement);
//                templateInfoSize=1;
//
//            } else {
//                /* If record exists update the record */
//                templateInfoDAO.updateTemplateInfo(patternDataParser,patternDataElement);
//            }
//
//        }
//
    }
}
