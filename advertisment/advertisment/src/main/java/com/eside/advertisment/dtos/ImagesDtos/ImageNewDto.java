package com.eside.advertisment.dtos.ImagesDtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ImageNewDto {

    private String name;

    private String type;

    private String path;
}
