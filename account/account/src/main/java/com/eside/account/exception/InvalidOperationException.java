package com.eside.account.exception;


import lombok.Getter;

public class InvalidOperationException extends RuntimeException{

    public InvalidOperationException(String message) {
        super(message);
    }
}