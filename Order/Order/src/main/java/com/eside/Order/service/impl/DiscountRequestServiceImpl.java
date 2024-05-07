package com.eside.Order.service.impl;

import com.eside.Order.dtos.DiscountRequestDtos.DiscountRequestDto;
import com.eside.Order.dtos.DiscountRequestDtos.DiscountRequestNewDto;
import com.eside.Order.dtos.DiscountRequestDtos.DiscountRequestUpdateCounterDto;
import com.eside.Order.dtos.DiscountRequestDtos.DiscountRequestUpdateDto;
import com.eside.Order.dtos.SuccessDto;
import com.eside.Order.enums.DiscountRequestStatus;
import com.eside.Order.enums.OrderStatusEnum;
import com.eside.Order.exception.EntityNotFoundException;
import com.eside.Order.exception.InvalidOperationException;
import com.eside.Order.externalData.Account;
import com.eside.Order.model.DiscountRequest;
import com.eside.Order.model.Order;
import com.eside.Order.repository.DiscountRequestRepository;
import com.eside.Order.repository.OrderRepository;
import com.eside.Order.service.DiscountRequestService;
import com.eside.Order.utils.SuccessMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DiscountRequestServiceImpl implements DiscountRequestService {
    private final OrderRepository orderRepository;
    private final DiscountRequestRepository discountRequestRepository;

    @Override
    public SuccessDto sendDiscount(DiscountRequestNewDto discountRequestNewDto) {

        //Retriving The Order
        Order order = orderRepository.findById(discountRequestNewDto.getOrderId())
                .orElseThrow(()-> new EntityNotFoundException("Order not found")
                );

        //TODO if the order hase not passed before !!!,
        if (order.getDiscountRequest() != null){
            throw new InvalidOperationException("You've already sent a discount request on this advertisement");
        }
        if(discountRequestNewDto.getRequestedAmount()>order.getOrderAmount()){
            throw new InvalidOperationException("Invalid amount ! the requested amount should not be superior than the order amount !");
        }
        // Create a new DiscountRequest using the builder pattern
        DiscountRequest discountRequest = DiscountRequest.builder()
                .counterDiscountAmount(discountRequestNewDto.getRequestedAmount())
                .order(order)
                .eDiscountRequestStats(DiscountRequestStatus.WAITING)
                .requestDate(new Date())
                .build();

        // Save the created DiscountRequest
        discountRequestRepository.save(discountRequest);
        order.setDiscountRequest(discountRequest);
        orderRepository.save(order);
        return SuccessDto.builder()
                .message(SuccessMessage.SUCCESSFULLY_CREATED)
                .build();
    }

    @Override
    public SuccessDto counterDiscount(Long discountRequestId, DiscountRequestUpdateCounterDto discountRequestUpdateDto) {
        // Retrieve the discount request to be countered
        DiscountRequest discountRequest = discountRequestRepository.findById(discountRequestId)
                .orElseThrow(() -> new EntityNotFoundException("Discount Request not found"));

        discountRequest.setCounterDiscountAmount(discountRequestUpdateDto.getCounterDiscountAmount());
        discountRequest.setEDiscountRequestStats(DiscountRequestStatus.COUNTERED);
        discountRequestRepository.save(discountRequest);

        return SuccessDto.builder()
                .message("Discount request countered successfully")
                .build();
    }

    @Override
    public SuccessDto updateDiscount(Long discountRequestId, DiscountRequestUpdateDto discountRequestUpdateDto) {
        // Check if the discount request exists
        DiscountRequest discountRequest = discountRequestRepository.findById(discountRequestId)
                .orElseThrow(() -> new EntityNotFoundException("Discount Request not found"));

      discountRequest.setRequestedAmount(discountRequestUpdateDto.getRequestedAmount());

        // Save the updated DiscountRequest
        discountRequestRepository.save(discountRequest);

        return SuccessDto.builder()
                .message("Discount Amount updated successfully")
                .build();
    }

    @Override
    public DiscountRequestDto getDiscountById(Long discountId) {
        DiscountRequest discountRequest = discountRequestRepository.findById(discountId)
                .orElseThrow(() -> new EntityNotFoundException("Discount Request not found"));
        return DiscountRequestDto.customMapping(discountRequest);
    }

    @Override
    public List<DiscountRequestDto> getAllDiscount() {
        return discountRequestRepository.findAll()
                .stream()
                .map(DiscountRequestDto::customMapping)
                .collect(Collectors.toList());
    }

    @Override
    public SuccessDto deleteDiscountRequestById(Long discountId) {
        DiscountRequest discountRequest = discountRequestRepository.findById(discountId)
                .orElseThrow(() -> new EntityNotFoundException("Discount Request not found"));
        discountRequestRepository.delete(discountRequest);

        return SuccessDto.builder()
                .message(SuccessMessage.SUCCESSFULLY_REMOVED)
                .build();
    }

    @Override
    public List<DiscountRequestDto> getDiscountByAdvertisementOwnerId(Long advertisementOwnerId) {
        List<Order> ordersList = orderRepository.findByAdvertisementId(advertisementOwnerId);
        List<DiscountRequest> discountRequests = new ArrayList<>();
        for (Order order : ordersList){
            discountRequests.add(order.getDiscountRequest());
        }
        return discountRequests.stream()
                .map(DiscountRequestDto::customMapping)
                .collect(Collectors.toList());
    }

    @Override
    public SuccessDto acceptDiscount(Long discountId) {
        // Retrieve the discount request
        DiscountRequest discountRequest = discountRequestRepository.findById(discountId)
                .orElseThrow(() -> new EntityNotFoundException("Discount Request not found"));

        // Retrieve the related advertisement
        Order order = discountRequest.getOrder();

        // Set the price of the advertisement to the requested amount in the discount request
        order.setOrderAmount(discountRequest.getRequestedAmount());
        order.setOrderStatus(OrderStatusEnum.AWAITING_PROCESSING);
        // Save the updated advertisement
        orderRepository.save(order);

        discountRequest.setEDiscountRequestStats(DiscountRequestStatus.ACCEPTED);
        return SuccessDto.builder()
                .message(SuccessMessage.SUCCESSFULLY_ACCEPTED)
                .build();
    }

    @Override
    public SuccessDto declineDiscount(Long discountId) {
        // Retrieve the discount request
        DiscountRequest discountRequest = discountRequestRepository.findById(discountId)
                .orElseThrow(() -> new EntityNotFoundException("Discount Request not found"));

        // Update the eDiscountRequestStats to DECLINED
        discountRequest.setEDiscountRequestStats(DiscountRequestStatus.DECLINED);
        discountRequestRepository.save(discountRequest);

        return SuccessDto.builder()
                .message(SuccessMessage.SUCCESSFULLY_DECLINED)
                .build();
    }
}
