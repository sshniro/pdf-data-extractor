package com.data.extractor.model.extract.pdf.inserter;


import com.data.extractor.model.beans.extract.pdf.ExtractStatus;
import com.data.extractor.model.beans.template.info.text.TextDataElement;
import com.data.extractor.model.beans.template.info.text.TextDataParser;
import com.data.extractor.model.data.access.layer.ExtractedDataDAO;
import com.data.extractor.model.data.access.layer.TemplateInfoDAO;
import com.data.extractor.model.db.connect.dbInitializer;
import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class ExtractedTextInserter {

    public void insert(TextDataParser textDataParser,ExtractStatus extractStatus,MongoClient mongoClient){

        List<TextDataElement> textDataElements = textDataParser.getTextDataElements();
        ExtractedDataDAO extractedDataDAO = new ExtractedDataDAO(mongoClient);
        TextDataElement textDataElement;
        int recordsSize=0;

        for(int i=0;i<textDataElements.size();i++){

            textDataElement=textDataElements.get(i);

            /* Only check once from the DB when the loop starts */
            if(i==0){

                recordsSize = extractedDataDAO.getRecordsSizeOfId(textDataParser.getId(),textDataParser.getDataType());
            }

            if (recordsSize == 0) {
                /* If there is no record exists create a new record and insert */
                extractedDataDAO.createTemplateInfo(textDataParser.getId() , extractStatus.getParent() , textDataParser.getDataType(),textDataElement);
                recordsSize = 1;

            } else {
                /* If record exists update the record */
                extractedDataDAO.updateTemplateInfo(textDataParser.getId(),textDataParser,textDataElement);
            }
        }
    }
}
