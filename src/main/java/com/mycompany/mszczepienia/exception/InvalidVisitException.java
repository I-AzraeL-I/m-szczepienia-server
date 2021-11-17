package com.mycompany.mszczepienia.exception;

public class InvalidVisitException extends RuntimeException {

    public InvalidVisitException(String identifier, String message) {
        super(String.format("Failed for [%s]: %s", identifier, message));
    }
}
