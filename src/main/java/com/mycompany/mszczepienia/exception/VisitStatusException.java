package com.mycompany.mszczepienia.exception;

public class VisitStatusException extends RuntimeException{
    public VisitStatusException(String identifier, String message){
        super(String.format("Failed for [%s]: %s", identifier, message));

    }
}
