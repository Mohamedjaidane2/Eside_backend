package com.eside.advertisment.dtos.ImagesDtos;

import com.eside.advertisment.model.Image;
import com.eside.advertisment.model.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ImageDto {

    private Long id;

    private String name;

    private String type;

    private String path;

    //private Product product;

}