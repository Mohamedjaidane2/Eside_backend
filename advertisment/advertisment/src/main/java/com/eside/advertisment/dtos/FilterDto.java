package com.eside.advertisment.dtos;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FilterDto {
    private String columnName;

    private List<Object> columnValue;
  /*
    Note : For this to work, the types in "Student" should not be primitives
    like boolean, long etc. and instead should be Boolean, Long etc.
    Since the columnValue is Object.
  */

    //Getters and Setters - you know the drill
}