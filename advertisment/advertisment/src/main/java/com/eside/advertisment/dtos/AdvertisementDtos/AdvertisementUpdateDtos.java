package com.eside.advertisment.dtos.AdvertisementDtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class AdvertisementUpdateDtos {

    private String title;

    private String description;

    private Double price;

}
