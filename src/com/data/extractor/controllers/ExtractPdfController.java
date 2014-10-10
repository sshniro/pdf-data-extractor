package com.data.extractor.controllers;

import com.data.extractor.model.beans.extract.pdf.ExtractResponse;
import com.data.extractor.model.beans.extract.pdf.ExtractStatus;
import com.data.extractor.model.beans.template.info.insert.InsertDataParser;
import com.data.extractor.model.extract.pdf.ExtractRequestProcessor;
import com.data.extractor.model.extract.pdf.ResponseGenerator;
import com.data.extractor.model.testing.DataExtractor;
import com.google.gson.Gson;
import com.mongodb.MongoClient;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;


public class ExtractPdfController extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ExtractRequestProcessor extractRequestProcessor = new ExtractRequestProcessor();
        ExtractStatus extractStatus = new ExtractStatus();
        extractStatus.setRootPath(getServletContext().getRealPath(File.separator));

        /* Get the mongo client from the servletContext */
        MongoClient mongoClient = (MongoClient) request.getServletContext().getAttribute("MONGO_CLIENT");
        InsertDataParser dataExtracted = new InsertDataParser();

        extractRequestProcessor.processRequest(dataExtracted , request , extractStatus , mongoClient);

        ResponseGenerator responseGenerator=new ResponseGenerator();
        Gson gson=new Gson();
        String extractedData=null;
        ExtractResponse extractResponse;

        if(extractStatus.getStatus()) {
            DataExtractor dataExtractor = new DataExtractor();
            extractedData = dataExtractor.convertToString(dataExtracted);
        }

        extractResponse=responseGenerator.generateResponse(extractStatus , extractedData);

        response.getWriter().print(gson.toJson(extractResponse));

    }
}
