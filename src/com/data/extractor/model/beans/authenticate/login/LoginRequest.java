package com.data.extractor.model.beans.authenticate.login;


import com.google.gson.annotations.Expose;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class LoginRequest {

    @Expose
    private String userName;
    @Expose
    private String pass;

    private String errorCause;

    private Boolean isAuthenticated;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPass() {        return pass;    }

    public void setPass(String pass) {        this.pass = pass;    }

    public Boolean getIsAuthenticated() {        return isAuthenticated;    }

    public void setIsAuthenticated(Boolean isAuthenticated) {        this.isAuthenticated = isAuthenticated;   }

    public String getErrorCause() {        return errorCause;    }

    public void setErrorCause(String errorCause) {        this.errorCause = errorCause;    }
}
