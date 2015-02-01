package com.data.extractor.model.beans.authentication;

import com.google.gson.annotations.Expose;

public class AuthenticationRequest {

    @Expose
    private String request;

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }
}
