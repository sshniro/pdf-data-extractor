package com.data.extractor.model.beans.user;

import com.google.gson.annotations.Expose;

public class UserBean {

    @Expose
    private String id;
    @Expose
    private String userName;
    @Expose
    private String pass;
    @Expose
    private String request;
    @Expose
    private String role;
    @Expose
    private String fullName;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
