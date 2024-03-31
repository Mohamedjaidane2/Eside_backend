package com.eside.Order.handler;

import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class OpenFiegnExceptionHandler {

        @ExceptionHandler(FeignException.NotFound.class)
        @ResponseBody
        public ResponseEntity<Object> handleFeignNotFoundException(FeignException.NotFound ex) {
            // Customize the response body as per your requirement
            String errorMessage = "One of the feign Client request  not found";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }
    }