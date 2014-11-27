package com.data.extractor.model.template.markup.pdf.retreiver;


import com.data.extractor.model.beans.template.info.table.TableDataParser;
import com.data.extractor.model.beans.templates.Template;
import com.data.extractor.model.beans.templates.TemplatesParser;
import com.data.extractor.model.db.connect.dbInitializer;
import com.google.gson.Gson;
import com.mongodb.*;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;

public class TablePDDocument {

    private static final String dbName="staging";
    private static final String templatesColl="templates";

    public PDDocument retrievePDDoc (TableDataParser tableDataParser,MongoClient mongoClient) throws IOException {
        tableDataParser=retrieveUploadedPDf(tableDataParser,mongoClient);
        String pdfFile=tableDataParser.getPdfFile();
        PDDocument doc =PDDocument.load(pdfFile);
        return doc;
    }

    public TableDataParser retrieveUploadedPDf(TableDataParser tableDataParser,MongoClient mongoClient){

        dbInitializer dbInitializer =new dbInitializer();
        DB db=dbInitializer.getDB(mongoClient,dbName);
        DBCollection templateCollection = dbInitializer.getCollection(db,templatesColl);

        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("mainCategory", tableDataParser.getMainCategory());
        basicDBObject.put("subCategory", tableDataParser.getSubCategory());

        DBCursor templateCursor = templateCollection.find(basicDBObject);

        if (templateCursor.hasNext()){
            // If there is a Record Present parse the MongoObject and Return the pdf File Location
            Gson gson = new Gson();
            TemplatesParser templateInfo = gson.fromJson(templateCursor.next().toString()
                    ,TemplatesParser.class);
            List<Template> templates=templateInfo.getTemplates();

            for(Template t:templates){
                if(t.getTemplateName().equals(tableDataParser.getTemplateName())){
                    tableDataParser.setPdfFile(t.getPdfFile());
                }
            }
        }
        return  tableDataParser;
    }
}
