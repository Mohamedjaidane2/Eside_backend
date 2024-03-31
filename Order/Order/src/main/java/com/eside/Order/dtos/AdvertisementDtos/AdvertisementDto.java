package com.eside.Order.dtos.AdvertisementDtos;
import com.eside.Order.enums.AdvertisementSoldStatusEnum;
import com.eside.Order.enums.AdvertisementStatusEnum;
import com.eside.Order.externalData.Advertisment;

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
public class AdvertisementDto {

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
    //private ProductDto product ;
    private Long orderId;
    public static AdvertisementDto customMapping(Advertisment advertisment) {
        return   AdvertisementDto.builder()
                .id(advertisment.getId())
                .title(advertisment.getTitle())
                .description(advertisment.getDescription())
                .price(advertisment.getPrice())
                .oldPrice(advertisment.getOldPrice())
                .advertisementSoldStatusEnum(advertisment.getAdvertisementSoldStatusEnum())
                .advertisementStatusEnum(advertisment.getAdvertisementStatusEnum())
                .creationDate(advertisment.getCreationDate())
                .updateDate(advertisment.getUpdateDate())
                .userAccountId(advertisment.getUserAccountId())
                //.product(ProductDto.customMapping(advertisment.getProduct()))
                .orderId(advertisment.getOrderId())
                .build();
        //Order order = advertisement.getOrder();
        //if (order != null) {
        //    builder.orderId(order.getOrderId());
        //}
    }

    public static List<AdvertisementDto> customListMapping(List<Advertisment> advertisements){
        if (advertisements == null) return null;
        ArrayList<AdvertisementDto> advertisementDtoArrayList = new ArrayList<>();
        for (Advertisment advertisement : advertisements) {
            AdvertisementDto advertisementDto = customMapping(advertisement);
            advertisementDtoArrayList.add(advertisementDto);
        }
        return advertisementDtoArrayList;
    }
}
