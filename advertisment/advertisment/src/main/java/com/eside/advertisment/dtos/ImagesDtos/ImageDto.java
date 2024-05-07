package com.eside.advertisment.dtos.ImagesDtos;

import com.eside.advertisment.dtos.SubCategoryDtos.SubCategoryDto;
import com.eside.advertisment.model.Image;
import com.eside.advertisment.model.Product;
import com.eside.advertisment.model.SubCategory;
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
    public static ImageDto customMapping (Image image){
        return ImageDto.builder()
                .id(image.getId())
                .path(image.getPath())
                .type(image.getType())
                .build();
    }
    public static List<ImageDto> customListMapping(List<Image> images) {
        if (images == null) return null;
        List<ImageDto> imageDtoList = new ArrayList<>();
        for (Image image : images) {
            ImageDto imageDto = customMapping(image);
            imageDtoList.add(imageDto);
        }
        return imageDtoList;
    }
}