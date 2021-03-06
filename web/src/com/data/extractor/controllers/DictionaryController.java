package com.data.extractor.controllers;

import com.data.extractor.model.beans.dictionary.Dictionary;
import com.data.extractor.model.data.access.layer.CounterDAO;
import com.data.extractor.model.data.access.layer.DictionaryDAO;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mongodb.MongoClient;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class DictionaryController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StringBuilder sb = new StringBuilder();
        String s;
        while ((s = request.getReader().readLine()) != null) {
            sb.append(s);
        }
        Gson gson=new Gson();

        Dictionary dictionary = gson.fromJson(sb.toString() , Dictionary.class );
        /* Get the mongo client from the servletContext */
        MongoClient mongoClient = (MongoClient) request.getServletContext().getAttribute("MONGO_CLIENT");


        if(dictionary.getRequest().equals("createNewDataDicItem")){

            CounterDAO counterDAO=new CounterDAO(mongoClient);
            dictionary.setId(counterDAO.getDictionaryId());
            createDictionaryWord(mongoClient, dictionary);

            String jsonStr = "{\"state\": \"success\"}";
            JsonElement element = gson.fromJson (jsonStr, JsonElement.class);
            JsonObject jsonObj = element.getAsJsonObject();
            response.getWriter().print(gson.toJson(jsonObj));
        }


        if(dictionary.getRequest().equals("getAllDicItems")){
            List<Dictionary> dictionaryList = getAllDictionaryRecords(mongoClient);
            response.getWriter().print(gson.toJson(dictionaryList));
        }


        if(dictionary.getRequest().equals("removeDicItem")){
            removeDictionaryRecord(mongoClient , dictionary.getId());

            String jsonStr = "{\"state\": \"success\"}";
            JsonElement element = gson.fromJson (jsonStr, JsonElement.class);
            JsonObject jsonObj = element.getAsJsonObject();

            response.getWriter().print(gson.toJson(jsonObj));
        }
    }

    public static void createDictionaryWord(MongoClient mongoClient , Dictionary dictionary){

        DictionaryDAO dictionaryDAO = new DictionaryDAO(mongoClient);
        CounterDAO counterDAO = new CounterDAO(mongoClient);
        dictionaryDAO.createDictionaryData(dictionary , counterDAO.getDictionaryId());

    }

    public static List<Dictionary> getAllDictionaryRecords(MongoClient mongoClient){
        DictionaryDAO dictionaryDAO = new DictionaryDAO(mongoClient);
        return dictionaryDAO.getAllRecords();
    }

    public static void removeDictionaryRecord(MongoClient mongoClient,int id){
        DictionaryDAO dictionaryDAO = new DictionaryDAO(mongoClient);
        dictionaryDAO.removeDictionaryRecord(id);
    }
}
