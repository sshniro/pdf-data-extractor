package com.data.extractor.controllers;

import com.data.extractor.model.beans.form.populate.FormPopulateData;
import com.data.extractor.model.form.populate.MainCategories;
import com.data.extractor.model.form.populate.SubCategories;
import com.data.extractor.model.form.populate.Templates;
import com.google.gson.Gson;
import com.mongodb.MongoClient;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FormPopulateController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        StringBuilder sb = new StringBuilder();
        String s;
        while ((s = request.getReader().readLine()) != null) {
            sb.append(s);
        }
        Gson gson=new Gson();

        FormPopulateData formPopulateData;
        formPopulateData=gson.fromJson(sb.toString(),FormPopulateData.class);

        /* Get the mongo client from the servletContext */
        MongoClient mongoClient = (MongoClient) request.getServletContext().getAttribute("MONGO_CLIENT");

        if(formPopulateData.getRequest().equals("getMainCategories")){
            MainCategories mainCategories=new MainCategories();
            mainCategories.getMainCategories(formPopulateData,mongoClient);

        }
        if(formPopulateData.getRequest().equals("getSubCategories")){
            SubCategories subCategories=new SubCategories();
            subCategories.getSubCategories(formPopulateData,mongoClient);
        }

        if(formPopulateData.getRequest().equals("getTemplates")){
            Templates templates=new Templates();
            templates.getTemplates(formPopulateData,mongoClient);
        }
        response.getWriter().print(gson.toJson(formPopulateData));
    }

}
