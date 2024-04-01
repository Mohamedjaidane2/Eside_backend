package com.eside.auth.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SuccessDto {
    private String message;
}