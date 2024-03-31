package com.eside.Order.exception;


public class InvalidOperationException extends RuntimeException{

    public InvalidOperationException(String message) {
        super(message);
    }
}