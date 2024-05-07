package com.eside.Order.service;

import com.eside.Order.dtos.OrderDtos.OrderDto;
import com.eside.Order.dtos.SuccessDto;

import java.util.List;

public interface OrderService {

    OrderDto toOrder(Long accountId, Long advertisementId);

    OrderDto getOrderById(Long orderId);

    List<OrderDto> getOrderByAccount(Long accountId );

    SuccessDto cancelOrderById(Long orderId);

    SuccessDto confirmOrder (Long orderId);

    List<OrderDto> getAllOrders();

    List<OrderDto> getRecivedOrder(Long accountId );

    SuccessDto confirmOrderById(Long orderId);

    String getOrderProgressStatus(Long orderId);

    SuccessDto deleteOrder (Long orderId);
}
