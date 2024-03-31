package com.eside.advertisment.dtos.ProductDtos;

import com.eside.advertisment.enums.ColorEnum;
import com.eside.advertisment.enums.ProductStatusEnum;
import com.eside.advertisment.model.Image;
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
public class ProductNewDto {

    private List<Long> images;

    private ProductStatusEnum ProductStatus;

    private ColorEnum color;

    private String features;


    //TODO add category and sub category after

}
