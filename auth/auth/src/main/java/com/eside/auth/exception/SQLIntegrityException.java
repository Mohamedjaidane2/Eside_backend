package com.eside.auth.exception;

public class SQLIntegrityException extends RuntimeException{

    public SQLIntegrityException(String message) {
        super(message);
    }
}
