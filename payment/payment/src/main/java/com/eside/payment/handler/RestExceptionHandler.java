package com.eside.payment.handler;


import com.eside.payment.exception.EntityNotFoundException;
import com.eside.payment.exception.InvalidOperationException;
import com.eside.payment.utils.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorDto> handleException(EntityNotFoundException exception, WebRequest webRequest) {
        final HttpStatus notFound = HttpStatus.NOT_FOUND;
        final ErrorDto errorDto = ErrorDto.builder()
                .httpCode(notFound.value())
                .message(exception.getMessage())
                .build();

        return new ResponseEntity<>(errorDto, notFound);
    }

    @ExceptionHandler(InvalidOperationException.class)
    public ResponseEntity<ErrorDto> handleException(InvalidOperationException exception, WebRequest webRequest) {
        final HttpStatus notFound = HttpStatus.BAD_REQUEST;
        final ErrorDto errorDto = ErrorDto
                .builder()
                .httpCode(notFound.value())
                .message(exception.getMessage())
                .build();
        return new ResponseEntity<>(errorDto, notFound);
    }

}