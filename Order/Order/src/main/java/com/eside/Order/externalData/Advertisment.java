package com.eside.Order.externalData;

import com.eside.Order.enums.AdvertisementSoldStatusEnum;
import com.eside.Order.enums.AdvertisementStatusEnum;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Advertisment {
    private Long id;
    private String title;
    private String description;
    private Double price;
    private Double oldPrice;
    private AdvertisementSoldStatusEnum advertisementSoldStatusEnum ;
    private AdvertisementStatusEnum advertisementStatusEnum ;
    private Date creationDate;
    private Date updateDate;
    private Long userAccountId;
    private Long orderId ;
}
