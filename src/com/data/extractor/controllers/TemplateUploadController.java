package com.data.extractor.controllers;

import com.data.extractor.model.beans.upload.template.UploadStatus;
import com.data.extractor.model.template.upload.ResponseGenerator;
import com.data.extractor.model.template.upload.UploadRequestProcessor;
import com.mongodb.MongoClient;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;

/*
    Process the AJAX POST request from the TemplateUpload JSP
    IF uploaded file is PDF and template Name valid dispatch the request to Extract Template Region JSP
    IF file uploaded is not PDF or Template Name already exists sends response back to TemplateUpload JSP
 */
public class TemplateUploadController extends javax.servlet.http.HttpServlet {
        protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

        UploadRequestProcessor requestProcessor = new UploadRequestProcessor();
        UploadStatus uploadStatus = new UploadStatus();
        uploadStatus.setRootPath(getServletContext().getRealPath(File.separator));

        /* Get the mongo client from the servletContext */
        MongoClient mongoClient = (MongoClient) request.getServletContext().getAttribute("MONGO_CLIENT");

        uploadStatus = requestProcessor.processRequest(request, uploadStatus,mongoClient);

        if (uploadStatus.getIsTemplateNameValid()) {
            if (uploadStatus.getPdfUploadStatus()) {
                HttpSession session=request.getSession();
                String uploadJsonResponse = new ResponseGenerator().generateJsonResponse(uploadStatus);
                session.setAttribute("uploadJsonResponse", uploadJsonResponse);
                response.getWriter().print("success");

            }
            else {
                // Code to send response back to the TemplateUpload JSP [File is Not a PDF]
                response.getWriter().print(uploadStatus.getPdfUploadErrorCause());
            }
        } else {
            // Code to send response back to the TemplateUpload JSP [Template Name already Taken]
            response.getWriter().print(uploadStatus.getTemplateNameErrorCause());
        }

    }
}



