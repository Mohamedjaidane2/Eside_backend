package com.eside.account.exception;

import ch.qos.logback.core.spi.ErrorCodes;
import lombok.Getter;

public class EntityNotFoundException extends RuntimeException{

    public EntityNotFoundException(String message) {
        super(message);
    }
}