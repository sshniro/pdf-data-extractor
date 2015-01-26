package com.data.extractor.model.template.markup.insert.coordinate;

import com.data.extractor.model.beans.template.info.regex.*;
import com.data.extractor.model.data.access.layer.TemplateInfoDAO;
import com.mongodb.MongoClient;

import java.net.UnknownHostException;
import java.util.List;

public class RegexDataInserter {

    public void insert(RegexDataParser regexDataParser, MongoClient mongoClient) throws UnknownHostException {

        regexDataParser = removeNextLine(regexDataParser);
        List<RegexDataElement> regexDataElements= regexDataParser.getRegexDataElements();
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

    public RegexDataParser removeNextLine(RegexDataParser regexDataParser){

        List<RegexDataElement> regexDataElements= regexDataParser.getRegexDataElements();

        for (RegexDataElement r:regexDataElements){
            List<RegexPairElement> regexPairElementList = r.getRegexPairElements();
            for (RegexPairElement regexPair:regexPairElementList){
                RegexStartElement regexStartElement = regexPair.getRegexStartElement();
                RegexEndElement regexEndElement = regexPair.getRegexEndElement();

                regexStartElement.setTag(regexStartElement.getTag().trim());
                regexEndElement.setTag(regexEndElement.getTag().trim());
            }
        }

        return regexDataParser;
    }
}
