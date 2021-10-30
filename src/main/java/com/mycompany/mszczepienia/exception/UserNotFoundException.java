package com.mycompany.mszczepienia.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String token, String message) {
        super(String.format("Failed for [%s]: %s", token, message));
    }
}
