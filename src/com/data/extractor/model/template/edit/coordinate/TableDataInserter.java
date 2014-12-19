package com.data.extractor.model.template.edit.coordinate;

import com.data.extractor.model.beans.template.info.table.TableDataElement;
import com.data.extractor.model.beans.template.info.table.TableDataParser;
import com.data.extractor.model.data.access.layer.ExtractedDataDAO;
import com.data.extractor.model.data.access.layer.TemplateInfoDAO;
import com.mongodb.MongoClient;

import java.util.List;

public class TableDataInserter {

    public void insert(TableDataParser tableDataParser, String parentId ,MongoClient mongoClient){

        List<TableDataElement> tableDataElements= tableDataParser.getTableDataElements();
        ExtractedDataDAO extractedDataDAO=new ExtractedDataDAO(mongoClient);
        TableDataElement tableDataElement;
        int templateInfoSize=0;

        /* If any data has existed previously remove it */
        templateInfoSize=extractedDataDAO.getRecordsSizeOfId(tableDataParser.getId(), tableDataParser.getDataType());

        if (templateInfoSize != 0) {
            /* If there is no record exists create a new record and insert */
            extractedDataDAO.removeRecord(tableDataParser.getId(), parentId, tableDataParser.getDataType());
        }

        for(int i=0;i<tableDataElements.size();i++){

            tableDataElement=tableDataElements.get(i);

            /* Only check once from the DB when the loop starts */
            if(i==0){
                templateInfoSize=extractedDataDAO.getRecordsSizeOfId(tableDataParser.getId(),tableDataParser.getDataType());
            }

            if (templateInfoSize == 0) {
                /* If there is no record exists create a new record and input */
                extractedDataDAO.createTemplateInfo(tableDataParser.getId(), parentId ,tableDataParser.getDataType(), tableDataElement);
                templateInfoSize=1;
            } else {
                /* If record exists update the record */
                extractedDataDAO.updateTemplateInfo(tableDataParser, tableDataElement);
            }
        }
    }
}
