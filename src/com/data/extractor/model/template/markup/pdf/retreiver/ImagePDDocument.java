package com.data.extractor.model.template.markup.pdf.retreiver;

import com.data.extractor.model.beans.template.info.image.ImageDataParser;
import com.data.extractor.model.beans.templates.Template;
import com.data.extractor.model.beans.templates.TemplatesParser;
import com.data.extractor.model.db.connect.dbInitializer;
import com.google.gson.Gson;
import com.mongodb.*;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;


public class ImagePDDocument {

    private static final String dbName="staging";
    private static final String templatesColl="templates";

    public PDDocument retrievePDDoc (ImageDataParser imageDataParser,MongoClient mongoClient) throws IOException {

        imageDataParser=retrieveUploadedPDf(imageDataParser , mongoClient);
        String pdfFile=imageDataParser.getPdfFile();
        PDDocument doc =PDDocument.load(pdfFile);
        return doc;
    }

    public ImageDataParser retrieveUploadedPDf(ImageDataParser imageDataParser , MongoClient mongoClient){

        dbInitializer dbInitializer =new dbInitializer();
        DB db=dbInitializer.getDB(mongoClient,dbName);
        DBCollection templateCollection = dbInitializer.getCollection(db,templatesColl);

        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("mainCategory", imageDataParser.getMainCategory());
        basicDBObject.put("subCategory", imageDataParser.getSubCategory());

        DBCursor templateCursor = templateCollection.find(basicDBObject);

        if (templateCursor.hasNext()){
            /* If there is a Record Present parse the MongoObject and Return the pdf File Location  */
            Gson gson = new Gson();
            TemplatesParser templateInfo = gson.fromJson(templateCursor.next().toString()
                    ,TemplatesParser.class);
            List<Template> templates=templateInfo.getTemplates();

            for(Template t:templates){
                if(t.getTemplateName().equals(imageDataParser.getTemplateName())){
                    imageDataParser.setPdfFile(t.getPdfFile());
                }
            }
        }
        return  imageDataParser;
    }
}
