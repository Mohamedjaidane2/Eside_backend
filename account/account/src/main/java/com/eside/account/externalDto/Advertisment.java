package com.eside.account.externalDto;

import com.eside.account.enums.AdvertisementSoldStatusEnum;
import com.eside.account.enums.AdvertisementStatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

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
    private Long favoritesId;
    private String ownerAccountName;
    //private Product product;
    private Long orderId ;

}
