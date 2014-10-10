package com.data.extractor.model.template.markup.insert.coordinate;

import com.data.extractor.model.beans.template.info.table.TableDataElement;
import com.data.extractor.model.beans.template.info.table.TableDataParser;
import com.data.extractor.model.data.access.layer.TemplateInfoDAO;
import com.mongodb.*;

import java.util.List;

public class TableDataInserter {

    public void insert(TableDataParser tableDataParser,MongoClient mongoClient){

        List<TableDataElement> tableDataElements= tableDataParser.getTableDataElements();
        TemplateInfoDAO templateInfoDAO=new TemplateInfoDAO(mongoClient);
        TableDataElement tableDataElement;
        int templateInfoSize=0;

        for(int i=0;i<tableDataElements.size();i++){

            tableDataElement=tableDataElements.get(i);

            /* Only check once from the DB when the loop starts */
            if(i==0){
                templateInfoSize=templateInfoDAO.getTemplateInfoSize(tableDataParser.getMainCategory(),tableDataParser.getSubCategory(),
                        tableDataParser.getTemplateName(),tableDataParser.getDataType());
            }

            if (templateInfoSize == 0) {
                /* If there is no record exists create a new record and input */
                templateInfoDAO.createTemplateInfo(tableDataParser.getMainCategory(),tableDataParser.getSubCategory(),
                        tableDataParser.getTemplateName(),tableDataParser.getDataType(),tableDataElement);
                templateInfoSize=1;
            } else {
                /* If record exists update the record */
                templateInfoDAO.updateTemplateInfo(tableDataParser ,tableDataElement);
            }
        }
    }
}
