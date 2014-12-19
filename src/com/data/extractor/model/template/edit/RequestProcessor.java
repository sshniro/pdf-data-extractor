package com.data.extractor.model.template.edit;

import com.data.extractor.model.beans.markup.template.MarkUpRequest;
import com.data.extractor.model.beans.markup.template.MarkUpResponse;
import com.data.extractor.model.beans.upload.template.UploadResponse;
import com.data.extractor.model.beans.upload.template.UploadStatus;
import com.data.extractor.model.template.upload.ResponseGenerator;
import com.google.gson.Gson;
import com.mongodb.MongoClient;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class RequestProcessor {
    public MarkUpResponse processRequest(String jsonRequest,MongoClient mongoClient ,HttpServletRequest request) throws IOException {
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

        if(status.equals("insert")){
            // Insert the Extracted Data and the new Dimensions of the template if editing available
            InsertRequestProcessor requestProcessor=new InsertRequestProcessor();
            List<UploadStatus> uploadStatusList = requestProcessor.processRequest(jsonRequest, mongoClient);

            UploadStatus uploadStatus= uploadStatusList.get(0);

            HttpSession session=request.getSession();
            UploadResponse editRsponse = new UploadResponse();
            editRsponse.setId(uploadStatus.getId());
            editRsponse.setParent(uploadStatus.getParent());
            session.setAttribute("editJsonResponse", gson.toJson(editRsponse));
        }

        return markUpResponse;
    }
}
