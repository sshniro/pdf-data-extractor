package com.data.extractor.controllers;

import com.data.extractor.model.beans.manage.categories.ManageCategoriesData;
import com.data.extractor.model.beans.template.info.image.ImageDataParser;
import com.data.extractor.model.beans.template.info.insert.InsertDataParser;
import com.data.extractor.model.beans.template.info.table.TableDataParser;
import com.data.extractor.model.beans.template.info.text.TextDataParser;
import com.data.extractor.model.beans.upload.template.UploadStatus;
import com.data.extractor.model.convert.PdfToImage;
import com.data.extractor.model.data.access.layer.TemplateInfoDAO;
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

public class EditTemplateController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        StringBuilder sb = new StringBuilder();
        String s;
        while ((s = request.getReader().readLine()) != null) {
            sb.append(s);
        }
        Gson gson=new Gson();
        ManageCategoriesData data = gson.fromJson(sb.toString(),ManageCategoriesData.class);

        /* Get the mongo client from the servletContext */
        MongoClient mongoClient = (MongoClient) request.getServletContext().getAttribute("MONGO_CLIENT");
        TemplateInfoDAO templateInfoDAO = new TemplateInfoDAO(mongoClient);

        List<TextDataParser> textDataParserList = templateInfoDAO.getTextTemplateInfo(data.getId(), "text");
        List<ImageDataParser> imageDataParserList= templateInfoDAO.getImageTemplateInfo(data.getId(), "image");
        List<TableDataParser> tableDataParserList= templateInfoDAO.getTableTemplateInfo(data.getId(), "table");


        InsertDataParser insertDataParser = new InsertDataParser();
        if(textDataParserList.size() != 0)
            insertDataParser.setTextDataParser(textDataParserList.get(0));
        if(imageDataParserList.size() != 0)
            insertDataParser.setImageDataParser(imageDataParserList.get(0));
        if(tableDataParserList.size() != 0)
            insertDataParser.setTableDataParser(tableDataParserList.get(0));



        PdfToImage pdfToImage =new PdfToImage();
        UploadStatus uploadStatus = new UploadStatus();
        uploadStatus.setInsertDataParser(insertDataParser);
        File uploadLocation = new File(getServletContext().getRealPath(File.separator) + File.separator + "uploads"+File.separator+"temp" + File.separator + data.getParent() +
                File.separator + data.getId());


        //extractStatus.setPdfLocation(uploadLocation.getAbsolutePath());
        //extractStatus.setPdfName(extractStatus.getId()); // templateName space replaced with "_"
        //extractStatus.setUploadedPdfFile(extractStatus.getPdfLocation()+File.separator+extractStatus.getPdfName()+".pdf");
        uploadStatus.setPdfLocation(uploadLocation.getAbsolutePath());
        uploadStatus.setUploadedPdfFile(uploadStatus.getPdfLocation() + File.separator + data.getId() + ".pdf");

        HttpSession session=request.getSession();
        String uploadJsonResponse = new ResponseGenerator().generateJsonResponse(uploadStatus , true);
        session.setAttribute("uploadJsonResponse", uploadJsonResponse);
        response.getWriter().print("success");

        //response.getWriter().print(gson.toJson(insertDataParser));

    }

}
