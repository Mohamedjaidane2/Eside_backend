package com.eside.advertisment.dtos.SubCategoryDtos;

import com.eside.advertisment.enums.ColorEnum;
import com.eside.advertisment.enums.ProductStatusEnum;
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
public class SubCategoryNewDto {

    private String name;

    private String description;

    private Long CategoryId;

    private Date creationDate;
}
