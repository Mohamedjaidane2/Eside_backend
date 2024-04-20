package com.eside.advertisment.dtos.CategoryDtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CategoryNewDto {

    private String name;

    private String description;

    private Date creationDate;
}
