package com.data.extractor.model.template.markup;

import com.data.extractor.model.beans.markup.template.MarkUpRequest;
import com.data.extractor.model.beans.markup.template.MarkUpResponse;
import com.data.extractor.model.beans.template.info.pattern.FormPairData;
import com.google.gson.Gson;
import com.mongodb.MongoClient;

import java.io.IOException;
import java.util.List;

public class MarkUpRequestProcessor {
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

            if(dataType.equals("pattern") ){
                TextDataExtractor textDataExtractor = new TextDataExtractor();
                markUpResponse=textDataExtractor.extractText(jsonRequest,mongoClient);

                PatternDataExtractor patternDataExtractor = new PatternDataExtractor();
                List<FormPairData> pairDatas = patternDataExtractor.processRequest(markUpResponse.getExtractedData());
                markUpResponse.setFormPairDatas(pairDatas);
            }

        }

        if(status.equals("insert")){
            InsertRequestProcessor requestProcessor=new InsertRequestProcessor();
            requestProcessor.processRequest(jsonRequest,mongoClient);
        }

        return markUpResponse;
    }
}
