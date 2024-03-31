package com.eside.advertisment.dtos.ImagesDtos;

import com.eside.advertisment.dtos.AdvertisementDtos.AdvertisementDto;
import com.eside.advertisment.dtos.ProductDtos.ProductDto;
import com.eside.advertisment.model.Advertisment;
import com.eside.advertisment.model.Image;
import com.eside.advertisment.model.Product;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    private Product product;


    public static ImageDto customMapping(Image image) {
        return   ImageDto.builder()
                .id(image.getId())
                .name(image.getName())
                .type(image.getType())
                .path(image.getPath())
                .build();

    }

    public static List<ImageDto> customListMapping(List<Image> images){
        if (images == null) return null;
        ArrayList<ImageDto> imageDtoArrayList = new ArrayList<>();
        for (Image image : images) {
            ImageDto imageDto = customMapping(image);
            imageDtoArrayList.add(imageDto);
        }
        return imageDtoArrayList;
    }

}