package com.eside.auth.handler;

import com.eside.auth.dtos.ErrorDto;
import com.eside.auth.exception.ActivationTokenException;
import com.eside.auth.exception.EntityNotFoundException;
import com.eside.auth.exception.InvalidOperationException;
import com.eside.auth.exception.SQLIntegrityException;
import jakarta.mail.MessagingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.HashSet;
import java.util.Set;

import static com.eside.auth.handler.BusinessErrorCodes.*;
import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class RestExceptionHandler {


    @ExceptionHandler(LockedException.class)
    public ResponseEntity<ErrorDto> handleException(LockedException exp) {
        return ResponseEntity
                .status(UNAUTHORIZED)
                .body(
                        ErrorDto.builder()
                                .businessErrorCode(ACCOUNT_LOCKED.getCode())
                                .businessErrorDescription(ACCOUNT_LOCKED.getDescription())
                                .error(exp.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ErrorDto> handleException(DisabledException exp) {
        return ResponseEntity
                .status(UNAUTHORIZED)
                .body(
                        ErrorDto.builder()
                                .businessErrorCode(ACCOUNT_DISABLED.getCode())
                                .businessErrorDescription(ACCOUNT_DISABLED.getDescription())
                                .error(exp.getMessage())
                                .build()
                );
    }


    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorDto> handleException() {
        return ResponseEntity
                .status(UNAUTHORIZED)
                .body(
                        ErrorDto.builder()
                                .businessErrorCode(BAD_CREDENTIALS.getCode())
                                .businessErrorDescription(BAD_CREDENTIALS.getDescription())
                                .error("Login and / or Password is incorrect")
                                .build()
                );
    }

    @ExceptionHandler(MessagingException.class)
    public ResponseEntity<ErrorDto> handleException(MessagingException exp) {
        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(
                        ErrorDto.builder()
                                .error(exp.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(ActivationTokenException.class)
    public ResponseEntity<ErrorDto> handleException(ActivationTokenException exp) {
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(
                        ErrorDto.builder()
                                .error(exp.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorDto> handleException(EntityNotFoundException exp) {
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(
                        ErrorDto.builder()
                                .error(exp.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(InvalidOperationException.class)
    public ResponseEntity<ErrorDto> handleException(InvalidOperationException exp) {
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(
                        ErrorDto.builder()
                                .error(exp.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException exp) {
        Set<String> errors = new HashSet<>();
        exp.getBindingResult().getAllErrors()
                .forEach(error -> {
                    //var fieldName = ((FieldError) error).getField();
                    var errorMessage = error.getDefaultMessage();
                    errors.add(errorMessage);
                });

        return ResponseEntity
                .status(BAD_REQUEST)
                .body(
                        ErrorDto.builder()
                                .validationErrors(errors)
                                .build()
                );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleException(Exception exp) {
        exp.printStackTrace();
        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(
                        ErrorDto.builder()
                                .businessErrorDescription("Internal error, please contact the admin")
                                .error(exp.getMessage())
                                .build()
                );
    }

}

