package com.eside.Order.dtos.OrderDtos;

import com.eside.Order.enums.OrderStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class OrderUpdateDto {

    private Date orderDate;

    private Float totalPrice;

    private OrderStatusEnum orderStatus;

    private Long advertisement;

    private Long account;
}
