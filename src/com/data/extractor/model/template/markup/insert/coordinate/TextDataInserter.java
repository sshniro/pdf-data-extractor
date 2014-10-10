package com.data.extractor.model.template.markup.insert.coordinate;

import com.data.extractor.model.beans.template.info.text.TextDataElement;
import com.data.extractor.model.beans.template.info.text.TextDataParser;
import com.data.extractor.model.data.access.layer.TemplateInfoDAO;
import com.data.extractor.model.db.connect.dbInitializer;
import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class TextDataInserter {

    public void insert(TextDataParser textDataParser, MongoClient mongoClient) throws UnknownHostException {

        List<TextDataElement> textDataElements = textDataParser.getTextDataElements();
        TemplateInfoDAO templateInfoDAO=new TemplateInfoDAO(mongoClient);
        TextDataElement textDataElement;
        int templateInfoSize=0;

        for(int i=0;i<textDataElements.size();i++){

            textDataElement=textDataElements.get(i);

            /* Only check once from the DB when the loop starts */
            if(i==0){
                templateInfoSize=templateInfoDAO.getTemplateInfoSize(textDataParser.getMainCategory(),textDataParser.getSubCategory(),
                                                                    textDataParser.getTemplateName(),textDataParser.getDataType());
            }

            if (templateInfoSize == 0) {
                /* If there is no record exists create a new record and insert */
                templateInfoDAO.createTemplateInfo(textDataParser.getMainCategory(),textDataParser.getSubCategory(),
                        textDataParser.getTemplateName(),textDataParser.getDataType(),textDataElement);
                templateInfoSize=1;

            } else {
                /* If record exists update the record */
                templateInfoDAO.updateTemplateInfo(textDataParser,textDataElement);
            }

        }

    }
}
