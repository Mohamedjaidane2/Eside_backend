package com.eside.advertisment.exception;

public class SQLIntegrityException extends RuntimeException{

    public SQLIntegrityException(String message) {
        super(message);
    }
}
