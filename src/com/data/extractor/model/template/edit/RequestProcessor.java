package com.data.extractor.model.template.edit;

import com.data.extractor.model.beans.markup.template.MarkUpRequest;
import com.data.extractor.model.beans.markup.template.MarkUpResponse;
import com.google.gson.Gson;
import com.mongodb.MongoClient;

import java.io.IOException;

public class RequestProcessor {
    public MarkUpResponse processRequest(String jsonRequest,MongoClient mongoClient) throws IOException {
        MarkUpResponse markUpResponse=new MarkUpResponse();

        Gson gson=new Gson();
        MarkUpRequest markUpRequest=gson.fromJson(jsonRequest,MarkUpRequest.class);
        String dataType=markUpRequest.getDataType();
        String status=markUpRequest.getStatus();


        if(status.equals("extract")){

            if(dataType.equals("text") ){
                TextDataExtractor textDataExtractor = new TextDataExtractor();
                markUpResponse=textDataExtractor.extractText(jsonRequest,mongoClient);
            }

            if(dataType.equals("image") ){
                ImageDataExtractor imageDataExtractor = new ImageDataExtractor();
                markUpResponse=imageDataExtractor.extractImage(jsonRequest,mongoClient);

            }

            if(dataType.equals("table") ){
                TableDataExtractor tableDataExtractor = new TableDataExtractor();
                markUpResponse=tableDataExtractor.extractTable(jsonRequest,mongoClient);
            }

        }

        if(status.equals("editTemplate")){

        }

        if(status.equals("insert")){
            InsertRequestProcessor requestProcessor=new InsertRequestProcessor();
            requestProcessor.processRequest(jsonRequest,mongoClient);
        }

        return markUpResponse;
    }
}
