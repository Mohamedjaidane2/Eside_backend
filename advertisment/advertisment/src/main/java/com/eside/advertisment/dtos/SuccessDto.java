package com.eside.advertisment.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SuccessDto {
    private String message;
}