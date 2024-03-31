package com.eside.payment.utils;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SuccessDto {
    private String message;
}