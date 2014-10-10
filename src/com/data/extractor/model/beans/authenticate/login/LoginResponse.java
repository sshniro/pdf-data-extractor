package com.data.extractor.model.beans.authenticate.login;


public class LoginResponse {

    private String redirectUrl;

    private String errorCause;

    private Boolean isAuthenticated;

    public String getRedirectUrl() {        return redirectUrl;    }

    public void setRedirectUrl(String redirectUrl) {        this.redirectUrl = redirectUrl;    }

    public String getErrorCause() {        return errorCause;    }

    public void setErrorCause(String errorCause) {        this.errorCause = errorCause;    }

    public Boolean getIsAuthenticated() {        return isAuthenticated;    }

    public void setIsAuthenticated(Boolean isAuthenticated) {        this.isAuthenticated = isAuthenticated;    }


}
