package com.eside.advertisment.dtos.CategoryDtos;

import com.eside.advertisment.dtos.SubCategoryDtos.SubCategoryDto;
import com.eside.advertisment.model.Category;
import com.eside.advertisment.model.Product;
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
public class CategoryDto {

    private Long id;

    private String name;

    private String description;

    private Date creationDate;

    private List<SubCategoryDto> subCategoryDtoList;

    public static CategoryDto customMapping (Category category){
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .creationDate(category.getCreationDate())
                .description(category.getDescription())
                .subCategoryDtoList(SubCategoryDto.customListMapping(category.getSubCategories()))
                .build();
    }
}
