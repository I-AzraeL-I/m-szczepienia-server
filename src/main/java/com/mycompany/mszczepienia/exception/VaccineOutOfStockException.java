package com.mycompany.mszczepienia.exception;

public class VaccineOutOfStockException extends RuntimeException {

    public VaccineOutOfStockException(String identifier, String message) {
        super(String.format("Failed for [%s]: %s", identifier, message));
    }
}
