package com.mycompany.mszczepienia.exception;

public class ForgotPasswordException extends RuntimeException{
    public ForgotPasswordException(){
        super("Passwords should be equal");
    }
}
