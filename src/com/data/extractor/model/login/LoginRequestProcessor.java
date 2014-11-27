package com.data.extractor.model.login;


import com.data.extractor.model.beans.authenticate.login.LoginRequest;
import com.data.extractor.model.beans.authenticate.login.LoginResponse;
import com.data.extractor.model.data.access.layer.UsersDAO;
import com.mongodb.MongoClient;

public class LoginRequestProcessor {

    public LoginResponse processRequest(LoginRequest loginRequest,MongoClient mongoClient){
        /* set initially authenticated to false */
        loginRequest.setIsAuthenticated(false);

        UsersDAO usersDAO=new UsersDAO(mongoClient);
        /* If user credentials are correct set authenticated to true */
        if (usersDAO.isUserExists(loginRequest)){
            loginRequest.setIsAuthenticated(true);
        }else {
            loginRequest.setErrorCause("Invalid UserName or Password");
        }

        LoginResponse loginResponse=new LoginResponse();
        /* Convert loginRequest properties to loginResponse excluding userName & password*/
        loginResponse.setIsAuthenticated(loginRequest.getIsAuthenticated());
        loginResponse.setErrorCause(loginRequest.getErrorCause());

        return loginResponse;

    }
}
