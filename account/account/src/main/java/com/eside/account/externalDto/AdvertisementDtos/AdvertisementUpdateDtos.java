package com.eside.account.externalDto.AdvertisementDtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class AdvertisementUpdateDtos {

    private String title;

    private String description;

    private Double price;

}
