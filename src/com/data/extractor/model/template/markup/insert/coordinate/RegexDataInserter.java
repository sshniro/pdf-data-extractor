package com.data.extractor.model.template.markup.insert.coordinate;

import com.data.extractor.model.beans.template.info.regex.RegexDataElement;
import com.data.extractor.model.beans.template.info.regex.RegexDataParser;
import com.data.extractor.model.beans.template.info.text.TextDataElement;
import com.data.extractor.model.beans.template.info.text.TextDataParser;
import com.data.extractor.model.data.access.layer.TemplateInfoDAO;
import com.mongodb.MongoClient;

import java.net.UnknownHostException;
import java.util.List;

public class RegexDataInserter {

    public void insert(RegexDataParser regexDataParser, MongoClient mongoClient) throws UnknownHostException {

        List<RegexDataElement> regexDataElements= regexDataParser.getRegexDataElementList();
        TemplateInfoDAO templateInfoDAO=new TemplateInfoDAO(mongoClient);
        RegexDataElement regexDataElement;
        int templateInfoSize=0;

        for(int i=0;i<regexDataElements.size();i++){

            regexDataElement=regexDataElements.get(i);

            /* Only check once from the DB when the loop starts */
            if(i==0){
                templateInfoSize=templateInfoDAO.getTemplateInfoSize(regexDataParser.getId(),regexDataParser.getDataType());
            }

            if (templateInfoSize == 0) {
                /* If there is no record exists create a new record and insert */
                templateInfoDAO.createTemplateInfo(regexDataParser.getId() ,regexDataParser.getDataType(),regexDataElement);
                templateInfoSize=1;

            } else {
                /* If record exists update the record */
                templateInfoDAO.updateTemplateInfo(regexDataParser,regexDataElement);
            }

        }

    }
}
