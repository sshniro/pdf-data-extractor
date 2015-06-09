package com.data.extractor.controllers;

import com.data.extractor.model.beans.navigation.NavRequest;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class NavigationController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        StringBuilder sb = new StringBuilder();
        String s;
        while ((s = request.getReader().readLine()) != null) {
            sb.append(s);
        }
        Gson gson=new Gson();


        NavRequest navRequest;
        navRequest=gson.fromJson(sb.toString(),NavRequest.class);
        response.getWriter().print(navRequest.getRedirect()+".jsp");

    }
}
