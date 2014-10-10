package com.data.extractor.model.template.upload;

import com.data.extractor.model.beans.upload.template.UploadResponse;
import com.data.extractor.model.beans.upload.template.UploadStatus;
import com.google.gson.Gson;
import org.apache.commons.codec.binary.Base64;

public class ResponseGenerator {

    public String generateJsonResponse(UploadStatus uploadStatus){
        UploadResponse uploadResponse=new UploadResponse();

        uploadResponse.setMainCategory(uploadStatus.getMainCategory());
        uploadResponse.setSubCategory(uploadStatus.getSubCategory());
        uploadResponse.setTemplateName(uploadStatus.getTemplateName());


        String[] encodedRelativePaths=new String[uploadStatus.getImageRelativePaths().length];
        String[] relativePaths=uploadStatus.getImageRelativePaths();


        for(int i=0; i<relativePaths.length;i++){
            encodedRelativePaths[i] = new String(Base64.encodeBase64(relativePaths[i].getBytes()));
        }

        uploadStatus.setImageRelativePaths(encodedRelativePaths);
        uploadResponse.setImageRelativePaths(uploadStatus.getImageRelativePaths());



        Gson gson = new Gson();

        // convert java object to JSON format, and returned as JSON formatted string
        return gson.toJson(uploadResponse);
    }
}
