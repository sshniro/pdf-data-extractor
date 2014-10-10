package com.data.extractor.controllers;

import com.data.extractor.model.beans.authenticate.login.LoginRequest;
import com.data.extractor.model.beans.authenticate.login.LoginResponse;
import com.data.extractor.model.login.LoginRequestProcessor;
import com.google.gson.Gson;
import com.mongodb.MongoClient;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


public class InitController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        StringBuilder sb = new StringBuilder();
        String s;
        while ((s = request.getReader().readLine()) != null) {
            sb.append(s);
        }

        Gson gson=new Gson();
        LoginRequest loginRequest;
        loginRequest=gson.fromJson(sb.toString(),LoginRequest.class);

        /* Get the mongo client from the servletContext */
        MongoClient mongoClient = (MongoClient) request.getServletContext().getAttribute("MONGO_CLIENT");
        LoginRequestProcessor requestProcessor=new LoginRequestProcessor();

        /* sets loginResponse.isAuthenticated ==true if successfully authenticated */
        LoginResponse loginResponse;
        loginResponse=requestProcessor.processRequest(loginRequest,mongoClient);

        if(loginResponse.getIsAuthenticated()){
            HttpSession session=request.getSession();

            /* Set session attributes to redirect to login Page if the user Not Logged In */
            session.setAttribute("loggedIn",true);
            session.setAttribute("userName",loginRequest.getUserName());

            loginResponse.setRedirectUrl("ManageCategories.jsp");
            response.getWriter().print(gson.toJson(loginResponse));
        }else {
            response.getWriter().print(gson.toJson(loginResponse));
        }
    }
}
