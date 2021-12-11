package com.mycompany.mszczepienia.exception;

public class VisitNotFoundException extends RuntimeException {

    public VisitNotFoundException(String identifier, String message) {
        super(String.format("Failed for [%s]: %s", identifier, message));
    }
}
