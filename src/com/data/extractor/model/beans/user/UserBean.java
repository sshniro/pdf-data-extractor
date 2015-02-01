package com.data.extractor.model.beans.user;

import com.google.gson.annotations.Expose;

/**
 * Created by niro273 on 1/30/15.
 */
public class UserBean {

    @Expose
    private String userName;
    @Expose
    private String pass;
    @Expose
    private String request;
    @Expose
    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
