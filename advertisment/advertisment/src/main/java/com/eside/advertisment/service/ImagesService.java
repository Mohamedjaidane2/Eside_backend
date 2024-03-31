package com.eside.advertisment.service;

import com.eside.advertisment.dtos.ImagesDtos.ImageDto;
import com.eside.advertisment.dtos.ImagesDtos.ImageNewDto;
import com.eside.advertisment.dtos.ProductDtos.ProductDto;
import com.eside.advertisment.dtos.SuccessDto;
import com.eside.advertisment.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImagesService {
    SuccessDto uploadImage(ImageNewDto image) ;
    ImageDto getImageByPath(String imageName);
    List<ImageDto> getByProductId(Long productId);
    ImageDto getById(long id);
}
