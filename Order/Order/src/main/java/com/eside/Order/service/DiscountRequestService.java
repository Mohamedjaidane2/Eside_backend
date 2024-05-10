package com.eside.Order.service;

import com.eside.Order.dtos.DiscountRequestDtos.DiscountRequestDto;
import com.eside.Order.dtos.DiscountRequestDtos.DiscountRequestNewDto;
import com.eside.Order.dtos.DiscountRequestDtos.DiscountRequestUpdateCounterDto;
import com.eside.Order.dtos.DiscountRequestDtos.DiscountRequestUpdateDto;
import com.eside.Order.dtos.SuccessDto;

import java.util.List;

public interface DiscountRequestService {
    SuccessDto sendDiscount(DiscountRequestNewDto discountRequestNewDto);
    SuccessDto counterDiscount(Long discountRequestId, DiscountRequestUpdateCounterDto discountRequestUpdateDto);
    SuccessDto updateDiscount(Long discountRequestId, DiscountRequestUpdateDto discountRequestUpdateDto);
    DiscountRequestDto getDiscountById(Long discountId);
    List<DiscountRequestDto> getAllDiscount();
    SuccessDto deleteDiscountRequestById(Long discountId);
    List<DiscountRequestDto> getRecivedDiscountRequest(Long recived);
    List<DiscountRequestDto> getSendedDiscountRequest(Long senderId);
    SuccessDto acceptDiscount(Long discountId);
    SuccessDto declineDiscount(Long discountId);
}
