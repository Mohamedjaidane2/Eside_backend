package com.eside.advertisment.dtos.ProductDtos;

import com.eside.advertisment.enums.ColorEnum;
import com.eside.advertisment.enums.ProductStatusEnum;
import com.eside.advertisment.model.Advertisment;
import com.eside.advertisment.model.Image;
import com.eside.advertisment.model.Product;
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
public class ProductDto {

    private Long id;

    private List<Long> imageIds;

    private ProductStatusEnum ProductStatus;

    private String categoryName;

    private String SubcategoryName;

    private ColorEnum color;

    private String features;

    private Date creationDate;


    public static ProductDto customMapping (Product product){
        List<Long> idsList = new ArrayList<>();
        for (Image image : product.getImages()){
            idsList.add(image.getId());
        }
        return ProductDto.builder()
                .id(product.getId())
                .categoryName(product.getSubCategory().getCategory().getName())
                .SubcategoryName(product.getSubCategory().getName())
                .imageIds(idsList)
                .ProductStatus(product.getProductStatus())
                .color(product.getColor())
                .features(product.getFeatures())
                .creationDate(product.getCreationDate())
                .build();
    }
}
