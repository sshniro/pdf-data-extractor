package com.data.extractor.controllers;

import com.data.extractor.model.beans.manage.categories.ManageCategoriesData;
import com.data.extractor.model.data.access.layer.CounterDAO;
import com.data.extractor.model.manage.categories.RequestProcessor;
import com.google.gson.Gson;
import com.mongodb.MongoClient;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class ManageCategoriesController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        StringBuilder sb = new StringBuilder();
        String s;
        while ((s = request.getReader().readLine()) != null) {
            sb.append(s);
        }
        Gson gson = new Gson();
        ManageCategoriesData manageCategoriesData;
        manageCategoriesData=gson.fromJson(sb.toString(),ManageCategoriesData.class);

        /* Get the mongo client from the servletContext */
        MongoClient mongoClient = (MongoClient) request.getServletContext().getAttribute("MONGO_CLIENT");
        RequestProcessor requestProcessor=new RequestProcessor(mongoClient);
        /* @return manageCategoriesData with relevantData set if successful or error message */
        requestProcessor.processRequest(manageCategoriesData,mongoClient);

        response.getWriter().print(gson.toJson(manageCategoriesData));
    }
}
