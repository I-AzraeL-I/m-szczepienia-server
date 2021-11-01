package com.mycompany.mszczepienia.exception;

public class PeselAlreadyExistsException extends RuntimeException {

    public PeselAlreadyExistsException(String pesel, String message) {
        super(String.format("Failed for [%s]: %s", pesel, message));
    }
}
