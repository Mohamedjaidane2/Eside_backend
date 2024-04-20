package com.eside.advertisment.dtos.SubCategoryDtos;

import com.eside.advertisment.enums.ColorEnum;
import com.eside.advertisment.enums.ProductStatusEnum;
import com.eside.advertisment.model.Category;
import com.eside.advertisment.model.Image;
import com.eside.advertisment.model.Product;
import com.eside.advertisment.model.SubCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class SubCategoryDto {

    private Long id;

    private String name;

    private String description;

    //private List<Product> Products;

    //private com.eside.advertisment.model.Category Category;

    private Date creationDate;

    public static SubCategoryDto customMapping (SubCategory subcategory){
        return SubCategoryDto.builder()
                .id(subcategory.getId())
                .name(subcategory.getName())
                .description(subcategory.getDescription())
                .creationDate(subcategory.getCreationDate())
                .build();
    }
}
