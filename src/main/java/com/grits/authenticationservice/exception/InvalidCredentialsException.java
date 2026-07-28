package com.grits.authenticationservice.exception;

import org.springframework.http.HttpStatus;

public class InvalidCredentialsException extends GlobalServiceException {

    public InvalidCredentialsException() {
        super("Invalid email and/or password", HttpStatus.UNAUTHORIZED);
    }
}
