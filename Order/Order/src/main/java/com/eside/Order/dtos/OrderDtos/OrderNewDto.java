package com.eside.Order.dtos.OrderDtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class OrderNewDto {
    private Long accountId;

    private Long advertisementId;
}
