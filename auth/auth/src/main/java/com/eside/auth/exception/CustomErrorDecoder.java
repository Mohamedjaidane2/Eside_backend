package com.eside.auth.exception;

import feign.Response;
import feign.codec.ErrorDecoder;
import jakarta.ws.rs.BadRequestException;
import org.springframework.context.annotation.Bean;

public class CustomErrorDecoder implements ErrorDecoder {

    @Bean
    public ErrorDecoder errorDecoder() {
        return new CustomErrorDecoder();
    }

    @Override
    public Exception decode(String methodKey, Response response) {
        switch (response.status()){
            case 400:
                return new BadRequestException();
            case 404:
                return new EntityNotFoundException("not found");
            case 503:
                return new InvalidOperationException("Api is unavailable -openfiegn-");
            default:
                return new Exception("Exception while using external Api");
        }
    }
}

