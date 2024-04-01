package com.eside.auth.exception;


import lombok.Getter;

public class InvalidOperationException extends RuntimeException{
    @Getter
    private final String message;

    public InvalidOperationException(String message) {

        super(message);
        this.message = message;
    }
}