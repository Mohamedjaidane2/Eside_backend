package com.eside.Order.dtos.DiscountRequestDtos;

import com.eside.Order.enums.DiscountRequestStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class DiscountRequestNewDto {
    private Long orderId;

    private double requestedAmount;
}
