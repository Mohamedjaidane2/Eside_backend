package com.eside.Order.dtos.DiscountRequestDtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class DiscountRequestUpdateDto {
    private Long orderId;
    private double requestedAmount;
}
