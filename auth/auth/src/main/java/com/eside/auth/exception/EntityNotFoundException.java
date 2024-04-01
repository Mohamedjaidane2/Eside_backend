package com.eside.auth.exception;

import lombok.Getter;

public class EntityNotFoundException extends RuntimeException{

    @Getter
    private final String message;

    public EntityNotFoundException(String message) {
        super(message);
        this.message = message;
    }
}