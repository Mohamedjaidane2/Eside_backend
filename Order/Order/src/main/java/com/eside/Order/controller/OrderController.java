package com.eside.Order.controller;

import com.eside.Order.dtos.OrderDtos.OrderDto;
import com.eside.Order.dtos.SuccessDto;
import com.eside.Order.service.OrderService;
import com.eside.Order.service.impl.OrderServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/api/order")
@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderServiceImpl orderService;

    @PostMapping("/create/{accountId}/{advertisementId}")
    public ResponseEntity<OrderDto> createOrder(@PathVariable Long accountId, @PathVariable Long advertisementId) {
        return ResponseEntity.ok(orderService.toOrder(accountId, advertisementId));
    }

    @GetMapping("/{accountId}")
    //@ApiOperation(value = "Get advertisement by ID")
    public ResponseEntity<List<OrderDto>> getOrdertBYAccountId(@PathVariable Long accountId) {
        List<OrderDto> orderDtos = orderService.getOrderByAccount(accountId);
        return ResponseEntity.ok(orderDtos);
    }

    @PutMapping("/cancel/{orderId}")
    public ResponseEntity<SuccessDto> cancelOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.cancelOrderById(orderId));
    }
    @PutMapping("/confirm/{orderId}")
    public ResponseEntity<SuccessDto> confirmOrderById(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.confirmOrderById(orderId));
    }

    @GetMapping("/getById/{orderId}")
    //@ApiOperation(value = "Get advertisement by ID")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable Long orderId) {
        OrderDto order = orderService.getOrderById(orderId);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/all")
    //@ApiOperation(value = "Get advertisement by ID")
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        List<OrderDto> order= orderService.getAllOrders();
        return ResponseEntity.ok(order);
    }

    @GetMapping("/recived/{accountId}")
    //@ApiOperation(value = "Get advertisement by ID")
    public ResponseEntity<List<OrderDto>> getRecivedOrders(@PathVariable Long accountId) {
        List<OrderDto> order= orderService.getRecivedOrder(accountId);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/getStatus/{orderId}")
    //@ApiOperation(value = "Get advertisement by ID")
    public ResponseEntity<String> getOrderProgressStatus(@PathVariable Long orderId) {
        String order= orderService.getOrderProgressStatus(orderId);
        return ResponseEntity.ok(order);
    }
    @DeleteMapping("/delete/{orderId}")
    //@ApiOperation(value = "Get advertisement by ID")
    public ResponseEntity<SuccessDto> deleteOrder(@PathVariable Long orderId) {
        SuccessDto order= orderService.deleteOrder(orderId);
        return ResponseEntity.ok(order);
    }
}