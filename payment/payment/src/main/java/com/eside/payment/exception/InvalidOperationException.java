package com.eside.payment.exception;


public class InvalidOperationException extends RuntimeException{

    public InvalidOperationException(String message) {
        super(message);
    }
}