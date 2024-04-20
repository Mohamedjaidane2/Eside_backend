package com.eside.advertisment.dtos.SubCategoryDtos;

import com.eside.advertisment.enums.ColorEnum;
import com.eside.advertisment.enums.ProductStatusEnum;
import com.eside.advertisment.model.Category;
import com.eside.advertisment.model.Image;
import com.eside.advertisment.model.Product;
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

    private List<Product> Products;

    private com.eside.advertisment.model.Category Category;

    private Date creationDate;

    public static SubCategoryDto customMapping (Category category){
        return SubCategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .creationDate(category.getCreationDate())
                .build();
    }
}
