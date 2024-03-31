package com.eside.account.exception;

public class SQLIntegrityException extends RuntimeException{

    public SQLIntegrityException(String message) {
        super(message);
    }
}
