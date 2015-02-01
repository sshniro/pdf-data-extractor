package com.data.extractor.controllers;

import com.data.extractor.model.beans.authentication.AuthenticationRequest;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mongodb.MongoClient;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SessionController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StringBuilder sb = new StringBuilder();
        String s;
        while ((s = request.getReader().readLine()) != null) {
            sb.append(s);
        }

        Gson gson=new Gson();
        AuthenticationRequest authenticationRequest=gson.fromJson(sb.toString(),AuthenticationRequest.class);

        /* Get the mongo client from the servletContext */
        MongoClient mongoClient = (MongoClient) request.getServletContext().getAttribute("MONGO_CLIENT");

        if(authenticationRequest.getRequest().equals("logout")){
            HttpSession session=request.getSession();

            /* remove session attributes and to redirect to loginJSP */
            session.removeAttribute("loggedIn");
            session.removeAttribute("userName");

            String jsonStr = "{\"state\": \"success\"}";
            JsonElement element = gson.fromJson (jsonStr, JsonElement.class);
            JsonObject jsonObj = element.getAsJsonObject();
            response.getWriter().print(gson.toJson(jsonObj));
        }
    }
}
