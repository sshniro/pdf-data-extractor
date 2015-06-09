package com.data.extractor.controllers;

import com.data.extractor.model.beans.manage.categories.ManageCategoriesData;
import com.data.extractor.model.beans.template.info.image.ImageDataParser;
import com.data.extractor.model.beans.template.info.insert.InsertDataParser;
import com.data.extractor.model.beans.template.info.table.TableDataParser;
import com.data.extractor.model.beans.template.info.text.TextDataParser;
import com.data.extractor.model.beans.upload.template.UploadStatus;
import com.data.extractor.model.convert.PdfToImage;
import com.data.extractor.model.data.access.layer.TemplateInfoDAO;
import com.data.extractor.model.template.edit.PdfToImageConverter;
import com.data.extractor.model.template.upload.ResponseGenerator;
import com.google.gson.Gson;
import com.mongodb.MongoClient;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;

/*
This controller sets the Insert Data Parser to the session for editing Markup
 */

public class EditTemplateController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        StringBuilder sb = new StringBuilder();
        String s;
        while ((s = request.getReader().readLine()) != null) {
            sb.append(s);
        }
        Gson gson=new Gson();

        UploadStatus uploadStatus = gson.fromJson(sb.toString(),UploadStatus.class);

        /* Get the mongo client from the servletContext */
        MongoClient mongoClient = (MongoClient) request.getServletContext().getAttribute("MONGO_CLIENT");
        uploadStatus.setRootPath(getServletContext().getRealPath(File.separator));

        PdfToImageConverter converter =new PdfToImageConverter();
        uploadStatus = converter.convertToImage(uploadStatus,mongoClient);

        HttpSession session=request.getSession();
        String uploadJsonResponse = new ResponseGenerator().generateJsonResponse(uploadStatus , true);
        session.setAttribute("uploadJsonResponse", uploadJsonResponse);
        response.getWriter().print("success");
    }
}
