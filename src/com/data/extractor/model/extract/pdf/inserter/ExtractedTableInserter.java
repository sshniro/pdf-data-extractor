package com.data.extractor.model.extract.pdf.inserter;

import com.data.extractor.model.beans.extract.pdf.ExtractStatus;
import com.data.extractor.model.beans.template.info.table.Cell;
import com.data.extractor.model.beans.template.info.table.Column;
import com.data.extractor.model.beans.template.info.table.TableDataElement;
import com.data.extractor.model.beans.template.info.table.TableDataParser;
import com.data.extractor.model.beans.template.info.text.TextDataElement;
import com.data.extractor.model.data.access.layer.ExtractedDataDAO;
import com.data.extractor.model.db.connect.dbInitializer;
import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;


public class ExtractedTableInserter {

    private static final String host="localhost";
    private static final String dbName="staging";
    private static final String templatesColl="extractedData";
    private static final int    port=27017;


    public void insert(TableDataParser tableDataParser,ExtractStatus extractStatus , MongoClient mongoClient){

        List<TableDataElement> tableDataElements= tableDataParser.getTableDataElements();
        ExtractedDataDAO extractedDataDAO = new ExtractedDataDAO(mongoClient);
        TableDataElement tableDataElement;
        int recordsSize=0;

        for(int i=0;i<tableDataElements.size();i++){

            tableDataElement=tableDataElements.get(i);

            /* Only check once from the DB when the loop starts */
            if(i==0){

                recordsSize = extractedDataDAO.getRecordsSizeOfId(tableDataParser.getId(),tableDataParser.getDataType());
            }

            if (recordsSize == 0) {
                /* If there is no record exists create a new record and insert */
                extractedDataDAO.createTemplateInfo(tableDataParser.getId() , extractStatus.getParent() , tableDataParser.getDataType(),tableDataElement);
                recordsSize = 1;

            } else {
                /* If record exists update the record */
                extractedDataDAO.updateTemplateInfo(tableDataParser.getId(),tableDataParser,tableDataElement);
            }
        }
    }
}
