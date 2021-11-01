package com.mycompany.mszczepienia.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String identifier, String message) {
        super(String.format("Failed for [%s]: %s", identifier, message));
    }
}
