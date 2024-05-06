package com.eside.Order.service.impl;

import com.eside.Order.client.AccountClient;
import com.eside.Order.client.AdvertismentClient;
import com.eside.Order.dtos.AdvertisementDtos.AdvertisementDto;
import com.eside.Order.dtos.OrderDtos.OrderDto;
import com.eside.Order.dtos.SuccessDto;
import com.eside.Order.enums.AdvertisementSoldStatusEnum;
import com.eside.Order.enums.OrderStatusEnum;
import com.eside.Order.exception.EntityNotFoundException;
import com.eside.Order.exception.InvalidOperationException;
import com.eside.Order.externalData.Account;
import com.eside.Order.externalData.Advertisment;
import com.eside.Order.model.Order;
import com.eside.Order.repository.OrderRepository;
import com.eside.Order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository ;
    private final AccountClient accountClient;
    private final AdvertismentClient advertismentClient;


    @Override
    public OrderDto toOrder(Long accountId, Long advertisementId) {
        Account account = new Account();
        Advertisment advertisment = new Advertisment();
        try {
            account= accountClient.getAccountByIdFromOrder(accountId);
            System.out.println("our account " + account);
        }catch (Exception e){
            throw e ;
        }
        try {
            advertisment= advertismentClient.getAdvertismentByIdFromOrder(advertisementId);
            System.out.println("our advertisment " + advertisment);
        }catch (Exception e){
            throw e ;
        }

        //Check if the account has already placed an order for the specified advertisement
        boolean hasPlacedOrder = orderRepository.existsByAccountIdAndAdvertisementId(accountId, advertisementId);

        if (hasPlacedOrder) {
            throw new InvalidOperationException("You have already placed an order for this advertisement");
        }

        // Check if the advertisement belongs to the same account
        if (advertisment.getUserAccountId().equals(accountId)) {
            throw new InvalidOperationException("You can't place an order on your own advertisement");
        }

        //if (advertisment.getOrderId() != null) {
        //    throw new InvalidOperationException("Someone else has already ordered this advertisement");
        //}

        // Create a new Order using the builder pattern
        Order order = Order.builder()
                .accountId(accountId)
                .advertisementId(advertisementId)
                .orderStatus(OrderStatusEnum.AWAITING_CONFIRMATION)
                .orderAmount(advertisment.getPrice())
                .build();

        // Save the created Order
        orderRepository.save(order);

        return OrderDto.customMapping(order);
    }

    @Override
    public OrderDto getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(()-> new EntityNotFoundException("Order not found")
                );
        return OrderDto.customMapping(order);
    }

    @Override
    public List<OrderDto> getOrderByAccount(Long accountId) {
        List<Order> orders = orderRepository.findByAccountId(accountId);
        return OrderDto.customListMapping(orders);
    }

    @Override
    public SuccessDto cancelOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(()-> new EntityNotFoundException("Order not found")
                );
        order.setOrderStatus(OrderStatusEnum.CANCELLED);
        orderRepository.save(order);
        return SuccessDto.builder()
                .message("Order placed Cancelled !")
                .build();
    }

    @Override
    public SuccessDto confirmOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(()-> new EntityNotFoundException("Order not found")
                );
        Advertisment advertisment = new Advertisment();
        try {
            advertisment= advertismentClient.getAdvertismentByIdFromOrder(order.getAdvertisementId());
            System.out.println("our advertisment " + advertisment);
        }catch (Exception e){
            throw e ;
        }
        advertisment.setAdvertisementSoldStatusEnum(AdvertisementSoldStatusEnum.IN_PROGRESS);
        try {
            advertisment= advertismentClient.UpdateAdvertismentStatusFromOrder(order.getOrderId(),order.getAdvertisementId());
            System.out.println("our output  " + advertisment);
        }catch (Exception e){
            throw e ;
        }
        order.setOrderStatus(OrderStatusEnum.AWAITING_PROCESSING);
        orderRepository.save(order);
        return SuccessDto.builder()
                .message("Order Confirmed successfully !")
                .build();
    }

    @Override
    public List<OrderDto> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(OrderDto::customMapping)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDto> getRecivedOrder(Long accountId) {
        List<AdvertisementDto>  list= new ArrayList<>();
        List<OrderDto> orderList = new ArrayList<>();
        try {
            list = advertismentClient.getAdvertisementByAccountFromOrder(accountId);
            System.out.println("our output  " + list);
        }catch (Exception e){
            throw e ;
        }
        for (AdvertisementDto advertisementDto : list){

            if(advertisementDto.getOrderId()!= null ) {
                Order order = orderRepository.findById(advertisementDto.getOrderId())
                        .orElseThrow(()-> new EntityNotFoundException("Order not found")
                        );
                var orderDto = OrderDto.customMapping(order);
                orderList.add(orderDto);
            };

        }
        return orderList;
    }

    @Override
    public SuccessDto confirmOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(()-> new EntityNotFoundException("Order not found")
                );
        order.setOrderStatus(OrderStatusEnum.AWAITING_PROCESSING);
        orderRepository.save(order);
        return SuccessDto.builder()
                .message("Order accepted !")
                .build();
    }

    @Override
    public String getOrderProgressStatus(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(()-> new EntityNotFoundException("Order not found")
                );
        return order.getOrderStatus().name();
    }
}
