package com.data.extractor.controllers;

import com.data.extractor.model.beans.extract.pdf.ExtractResponse;
import com.data.extractor.model.beans.extract.pdf.ExtractStatus;
import com.data.extractor.model.beans.template.info.image.ImageDataElement;
import com.data.extractor.model.beans.template.info.image.ImageDataParser;
import com.data.extractor.model.beans.template.info.insert.InsertDataParser;
import com.data.extractor.model.beans.template.info.table.Cell;
import com.data.extractor.model.beans.template.info.table.Column;
import com.data.extractor.model.beans.template.info.table.TableDataElement;
import com.data.extractor.model.beans.template.info.table.TableDataParser;
import com.data.extractor.model.beans.template.info.text.TextDataElement;
import com.data.extractor.model.beans.template.info.text.TextDataParser;
import com.data.extractor.model.beans.upload.template.UploadStatus;
import com.data.extractor.model.data.access.layer.ExtractedDataDAO;
import com.data.extractor.model.extract.pdf.ResponseGenerator;
import com.data.extractor.model.testing.DataExtractor;
import com.google.gson.Gson;
import com.mongodb.MongoClient;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
    This controller is used to get the data of the edited document [Template]
 */
public class ExtractEditTempController extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        StringBuilder sb = new StringBuilder();
        String s;
        while ((s = request.getReader().readLine()) != null) {
            sb.append(s);
        }
        Gson gson=new Gson();

        ExtractStatus extractStatus= gson.fromJson(sb.toString(),ExtractStatus.class);
        extractStatus.setStatus(true);
        InsertDataParser insertDataParser = new InsertDataParser();

        /* Get the mongo client from the servletContext */
        MongoClient mongoClient = (MongoClient) request.getServletContext().getAttribute("MONGO_CLIENT");

        ExtractedDataDAO extractedDataDAO = new ExtractedDataDAO(mongoClient);
        List<TextDataParser> textDataParserList = extractedDataDAO.getTextRecord(extractStatus.getId());
        List<ImageDataParser> imageDataParserList = extractedDataDAO.getImageRecord(extractStatus.getId());
        List<TableDataParser> tableDataParserList = extractedDataDAO.getTableRecord(extractStatus.getId());

        if(textDataParserList.size() != 0){
            insertDataParser.setTextDataParser(textDataParserList.get(0));
        }
        if(imageDataParserList.size() != 0){
            insertDataParser.setImageDataParser(imageDataParserList.get(0));
        }
        if(tableDataParserList.size() != 0){
            insertDataParser.setTableDataParser(tableDataParserList.get(0));
        }

        ResponseGenerator responseGenerator=new ResponseGenerator();
        HttpSession session=request.getSession();
        DataExtractor dataExtractor = new DataExtractor();
        String extractedData = null;

        extractedData = dataExtractor.convertToString(insertDataParser,true);
        ExtractResponse extractResponse=responseGenerator.generateResponse(extractStatus , extractedData);
        /* Remove session to only trigger the data presentation function in the ExtractPdf Page after template Edit */
        session.removeAttribute("editJsonResponse");
        response.getWriter().print(gson.toJson(extractResponse));

    }

    public static List<List<String>> findSimilarities(TableDataParser tableDataParser){
        List<TableDataElement> tableDataElements= tableDataParser.getTableDataElements();
        List<String> metaNamesList = new ArrayList<String>();

        List<String> matchedNames = new ArrayList<String>();


        for (TableDataElement ta : tableDataElements){
            metaNamesList.add(new String(ta.getMetaName()));
        }

        for (String metaName : metaNamesList){
            if (metaNamesList.contains(metaName)){
                if(!matchedNames.contains(metaName)){
                    matchedNames.add(metaName);
                }
            }
        }

        List<List<String>> complex = new ArrayList<List<String>>();
        List<String> lessComplex;
        for (String name : matchedNames){
            lessComplex=new ArrayList<String>();
            for(TableDataElement ta : tableDataElements){
                if(ta.getMetaName().equals(name)){
                    lessComplex.add(ta.getMetaId());
                }
            }

            if(lessComplex.size() != 0){
                complex.add(lessComplex);
            }
        }

        return complex;
    }
}
