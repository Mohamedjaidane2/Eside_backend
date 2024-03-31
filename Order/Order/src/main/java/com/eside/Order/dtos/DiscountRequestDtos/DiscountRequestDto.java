package com.eside.Order.dtos.DiscountRequestDtos;

import com.eside.Order.enums.DiscountRequestStatus;
import com.eside.Order.model.DiscountRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class DiscountRequestDto {
    private Long id ;

    private Long orderId;

    private double requestedAmount;

    private DiscountRequestStatus discountRequestStatus;

    private double counterDiscountAmount;

    private Date requestDate;



    public static List<DiscountRequestDto> customListMapping(List<DiscountRequest> discountRequests) {
        if(discountRequests ==null) return null;
        ArrayList<DiscountRequestDto> discountRequestDtoArrayList = new ArrayList<>();
        for (DiscountRequest discountRequest : discountRequests){
            DiscountRequestDto discountRequestDto = customMapping(discountRequest);
            discountRequestDtoArrayList.add(discountRequestDto);
        }
        return discountRequestDtoArrayList;
    }
    public static DiscountRequestDto customMapping (DiscountRequest discountRequest){
        return DiscountRequestDto.builder()
                .id(discountRequest.getId())
                .orderId(discountRequest.getOrder().getOrderId())
                .requestedAmount(discountRequest.getRequestedAmount())
                .discountRequestStatus(discountRequest.getEDiscountRequestStats())
                .counterDiscountAmount(discountRequest.getCounterDiscountAmount())
                .requestDate(discountRequest.getRequestDate())
                .build();
    }
}
