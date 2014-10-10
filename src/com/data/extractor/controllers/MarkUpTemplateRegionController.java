package com.data.extractor.controllers;

import com.data.extractor.model.beans.markup.template.MarkUpResponse;
import com.data.extractor.model.template.markup.MarkUpRequestProcessor;
import com.google.gson.Gson;
import com.mongodb.MongoClient;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MarkUpTemplateRegionController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MarkUpRequestProcessor requestProcessor=new MarkUpRequestProcessor();

        StringBuilder sb = new StringBuilder();
        String s;
        while ((s = request.getReader().readLine()) != null) {
            sb.append(s);
        }
        /* Get the mongo client from the servletContext */
        MongoClient mongoClient = (MongoClient) request.getServletContext().getAttribute("MONGO_CLIENT");

        MarkUpResponse markUpResponse=requestProcessor.processRequest(sb.toString(),mongoClient);

        Gson gson = new Gson();
        response.getWriter().print(gson.toJson(markUpResponse));
    }
}
