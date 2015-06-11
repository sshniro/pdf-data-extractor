package com.data.extractor.controllers;

import com.data.extractor.model.beans.extract.pdf.ExtractStatus;
import com.data.extractor.model.beans.upload.template.UploadStatus;
import com.data.extractor.model.testing.ExcelFileGenerator;
import com.data.extractor.model.testing.ExcelGenerator;
import com.google.gson.Gson;
import com.mongodb.MongoClient;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

public class ExcelExtractController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StringBuilder sb = new StringBuilder();
        String s;
        while ((s = request.getReader().readLine()) != null) {
            sb.append(s);
        }
        Gson gson=new Gson();

        // get the Document ID from this command
        ExtractStatus extractStatus = gson.fromJson(sb.toString(),ExtractStatus.class);

        extractStatus.setRootPath(getServletContext().getRealPath(File.separator));

                /* Get the mongo client from the servletContext */
        MongoClient mongoClient = (MongoClient) request.getServletContext().getAttribute("MONGO_CLIENT");

        ExcelFileGenerator excelFileGenerator = new ExcelFileGenerator();
        String excelPath = excelFileGenerator.generateExcel(extractStatus.getParent(),extractStatus.getId(),extractStatus.getRootPath(),mongoClient);

        UploadStatus uploadStatus=new UploadStatus();
        uploadStatus.setExcelPath(excelPath);

        response.getWriter().print(gson.toJson(excelPath));

    }
}
