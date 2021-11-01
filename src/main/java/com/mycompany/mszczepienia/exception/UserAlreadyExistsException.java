package com.mycompany.mszczepienia.exception;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(String username, String message) {
        super(String.format("Failed for [%s]: %s", username, message));
    }
}
