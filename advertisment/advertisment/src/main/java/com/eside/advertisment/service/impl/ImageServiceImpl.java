package com.eside.advertisment.service.impl;

import com.eside.advertisment.dtos.ImagesDtos.ImageDto;
import com.eside.advertisment.dtos.ImagesDtos.ImageNewDto;
import com.eside.advertisment.dtos.SuccessDto;
import com.eside.advertisment.exception.EntityNotFoundException;
import com.eside.advertisment.model.Image;
import com.eside.advertisment.repository.ImageRepository;
import com.eside.advertisment.service.ImagesService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.exception.ContextedRuntimeException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.zip.DataFormatException;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImagesService {


    private final ImageRepository imageRepository;


    @Override
    public SuccessDto uploadImage(ImageNewDto image) {
        Image image1 = Image.builder().path(image.getPath())
                .name(image.getName())
                .type(image.getType())
                .build();
        imageRepository.save(image1);
        return SuccessDto.builder()
                .message("Successfuly Saved!")
                .build();
    }

    @Override
    public ImageDto getImageByPath(String imagePath) {
        Image image = imageRepository.findByPath(imagePath)
                .orElseThrow(()->{
                   throw new EntityNotFoundException("image not found !");
                });
        return ImageDto.customMapping(image);
    }

    @Override
    public List<ImageDto> getByProductId(Long productId) {
        List<Image> images = imageRepository.findByProduct_Id(productId);
        return ImageDto.customListMapping(images);
    }

    @Override
    public ImageDto getById(long id) {
        Image image = imageRepository.findById(id)
                .orElseThrow(()->{
                    throw new EntityNotFoundException("image not found !");
                });
        return ImageDto.customMapping(image);
    }
}

