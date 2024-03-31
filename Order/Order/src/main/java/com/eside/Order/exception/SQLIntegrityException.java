package com.eside.Order.exception;

public class SQLIntegrityException extends RuntimeException{

    public SQLIntegrityException(String message) {
        super(message);
    }
}
