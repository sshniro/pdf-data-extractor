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
import com.google.gson.Gson;
import com.mongodb.MongoClient;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExtractEditTempController extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        StringBuilder sb = new StringBuilder();
        String s;
        while ((s = request.getReader().readLine()) != null) {
            sb.append(s);
        }
        Gson gson=new Gson();

        ExtractStatus extractStatus= gson.fromJson(sb.toString(),ExtractStatus.class);
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

        String extractedData = convertToString(insertDataParser);
        ExtractResponse extractResponse=responseGenerator.generateResponse(extractStatus , extractedData);
        response.getWriter().print(gson.toJson(extractResponse));

    }

    public static String convertToString(InsertDataParser data){

        TextDataParser textDataParser = data.getTextDataParser();
        ImageDataParser imageDataParser = data.getImageDataParser();
        TableDataParser tableDataParser = data.getTableDataParser();

        StringBuilder sb=new StringBuilder("");

        if(textDataParser != null){

            List<TextDataElement> textList = textDataParser.getTextDataElements();
            sb.append("Extracted Text : \n" );
            for (int i=0 ; i < textList.size() ; i++) {
                TextDataElement text = textList.get(i);
                sb.append("Text ").append(i+1).append(" -name(").append(text.getMetaId()).append(") : ");
                sb.append(replaceNextLineChar(text.getExtractedText()));
                sb.append("\n");
            }

        }


        if(imageDataParser  != null){
            sb.append("Extracted Image : \n" );
            List<ImageDataElement> imageList = imageDataParser.getImageDataElements();
            for (ImageDataElement imageDataElement : imageList) {
                sb.append(imageDataElement.getExtractedImage());
                sb.append("\n");
            }

        }


        if(tableDataParser != null){
            sb.append("\nExtracted Table : \n" );
            List<TableDataElement> tableList= tableDataParser.getTableDataElements();

            for (int i=0 ; i < tableList.size() ; i++) {

                TableDataElement table=tableList.get(i);
                sb.append("Table " ).append(i+1).append(" -name (").append(table.getMetaId()).append(")").append("\n");
                List<Column> columnList=table.getColumns();

                for (int j=0 ; j < columnList.size() ; j++){

                    Column column=columnList.get(j);
                    sb.append("Column ").append(j+1).append(" -name (").append(column.getMetaId()).append(") : ");
                    List<String> cellList = column.getCellValues();

                    for(int c=0 ; c < cellList.size() ; c++ ){

                        String cellValue=cellList.get(c);
                        sb.append(cellValue);
                        /* append ',' until the element before the last */
                        if(c != cellList.size()-1){
                            sb.append(" , ");
                        }
                    }
                    sb.append("\n");
                }
            }
        }

        return sb.toString();
    }

    /* Removes the next line characters if the string has it in front of the line
    * [Because when extracting text with meta area , area which is empty adds a next line character
    * instead of adding a blank]
    * */
    public static  String replaceNextLineChar(String data){

        if(data.charAt(0) != 10){
            return data;
        }else {
            StringBuilder sb=new StringBuilder(data);
            sb.deleteCharAt(0);
            return replaceNextLineChar(sb.toString());
        }
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
