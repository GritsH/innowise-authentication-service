package com.grits.authenticationservice.exception;

import org.springframework.http.HttpStatus;

public class SignupException extends GlobalServiceException {

    public SignupException(Exception ex) {
        super("Failed creating application user. Keycloak user was rolled back" + ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}