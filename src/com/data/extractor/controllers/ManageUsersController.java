package com.data.extractor.controllers;

import com.data.extractor.model.beans.user.UserBean;
import com.data.extractor.model.data.access.layer.CounterDAO;
import com.data.extractor.model.data.access.layer.UsersDAO;
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

public class ManageUsersController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        StringBuilder sb = new StringBuilder();
        String s;
        while ((s = request.getReader().readLine()) != null) {
            sb.append(s);
        }

        Gson gson = new Gson();
        UserBean userBean = gson.fromJson(sb.toString(),UserBean.class);


        /* Get the mongo client from the servletContext */
        MongoClient mongoClient = (MongoClient) request.getServletContext().getAttribute("MONGO_CLIENT");
        UsersDAO usersDAO = new UsersDAO(mongoClient);

        if(userBean.getRequest().equals("createUser")){

            String jsonStr;
            int recordSize = usersDAO.getRecordSize(userBean.getUserName());
            if (recordSize == 0 ){

                CounterDAO counterDAO = new CounterDAO(mongoClient);
                Integer id = counterDAO.getNextId("userId");

                usersDAO.createUser(id.toString(),userBean.getUserName(),userBean.getPass(),userBean.getRole(),userBean.getFullName());
                jsonStr= "{\"state\": \"success\"}";
            }else {
                jsonStr = "{\"state\": \"fail\"}";
            }

            JsonElement element = gson.fromJson (jsonStr, JsonElement.class);
            JsonObject jsonObj = element.getAsJsonObject();
            response.getWriter().print(gson.toJson(jsonObj));
        }

        if(userBean.getRequest().equals("removeUser")){
            usersDAO.removeUser(userBean.getId());

            String jsonStr = "{\"state\": \"success\"}";
            JsonElement element = gson.fromJson (jsonStr, JsonElement.class);
            JsonObject jsonObj = element.getAsJsonObject();
            response.getWriter().print(gson.toJson(jsonObj));
        }

        if(userBean.getRequest().equals("getAllUsers")){

            List<UserBean> userBeanList = usersDAO.getAllUsers();
            response.getWriter().print(gson.toJson(userBeanList));
        }

        if(userBean.getRequest().equals("getUser")){

            UserBean user = usersDAO.getUser(userBean.getId());
            response.getWriter().print(gson.toJson(user));
        }


    }

}
