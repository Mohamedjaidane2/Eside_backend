package com.eside.account.externalDto.AdvertisementDtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class AdvertisementNewDto {

    private String title;

    private String description;

    private Long accountId;

    private Long productId;

    private Double price;
}
