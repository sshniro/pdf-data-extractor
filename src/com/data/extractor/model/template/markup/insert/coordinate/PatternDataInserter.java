package com.data.extractor.model.template.markup.insert.coordinate;


import com.data.extractor.model.beans.template.info.pattern.HeaderDataBean;
import com.data.extractor.model.beans.template.info.pattern.PatternDataElement;
import com.data.extractor.model.beans.template.info.pattern.PatternDataParser;
import com.data.extractor.model.beans.template.info.text.TextDataElement;
import com.data.extractor.model.beans.template.info.text.TextDataParser;
import com.data.extractor.model.data.access.layer.TemplateInfoDAO;
import com.mongodb.MongoClient;

import java.net.UnknownHostException;
import java.util.List;

public class PatternDataInserter {

    public void insert(PatternDataParser patternDataParser, MongoClient mongoClient) throws UnknownHostException {

        List<PatternDataElement> patternDataElements = patternDataParser.getPatternDataElementList();

        TemplateInfoDAO templateInfoDAO=new TemplateInfoDAO(mongoClient);
        PatternDataElement patternDataElement;
        int templateInfoSize=0;

        for(int i=0;i< patternDataElements.size();i++){

            patternDataElement = patternDataElements.get(i);

            /* Only check once from the DB when the loop starts */
            if(i==0){
                templateInfoSize=templateInfoDAO.getTemplateInfoSize(patternDataParser.getId(),patternDataParser.getDataType());
            }

            if (templateInfoSize == 0) {
                /* If there is no record exists create a new record and insert */
                templateInfoDAO.createTemplateInfo(patternDataParser.getId() ,patternDataParser.getDataType(),patternDataElement);
                templateInfoSize=1;

            } else {
                /* If record exists update the record */
                templateInfoDAO.updateTemplateInfo(patternDataParser,patternDataElement);
            }

        }

    }
}
