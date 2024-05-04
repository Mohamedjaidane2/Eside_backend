package com.eside.advertisment.service.impl;

import com.eside.advertisment.dtos.ImagesDtos.ImageDto;
import com.eside.advertisment.dtos.ImagesDtos.ImageNewDto;
import com.eside.advertisment.dtos.ImagesDtos.ImageResponseDto;
import com.eside.advertisment.dtos.SuccessDto;
import com.eside.advertisment.exception.EntityNotFoundException;
import com.eside.advertisment.exception.InvalidOperationException;
import com.eside.advertisment.model.Image;
import com.eside.advertisment.repository.ImageRepository;
import com.eside.advertisment.service.ImagesService;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.exception.ContextedRuntimeException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.zip.DataFormatException;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImagesService {


    private final ImageRepository imageRepository;
    String uploadDirectory = "src/main/resources/static/images/ads";
    String adsImagesString = "";

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
    public ImageDto getImageByName(String imageName) {
        Image image = imageRepository.findByName(imageName)
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

    @Override
    public String uploadFile(File file, String fileName) throws IOException {
        BlobId blobId = BlobId.of("eside-746f3.appspot.com", fileName); // Replace with your bucker name
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build();
        InputStream inputStream = ImagesService.class.getClassLoader().getResourceAsStream("eside-746f3-firebase-adminsdk-h8sg3-2db80ccf9a.json"); // change the file name with your one
        Credentials credentials = GoogleCredentials.fromStream(inputStream);
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        storage.create(blobInfo, Files.readAllBytes(file.toPath()));

        String DOWNLOAD_URL = "https://firebasestorage.googleapis.com/v0/b/eside-746f3.appspot.com/o/%s?alt=media";
        return String.format(DOWNLOAD_URL, URLEncoder.encode(fileName, StandardCharsets.UTF_8));

    }

    @Override
    public File convertToFile(MultipartFile multipartFile, String fileName) throws IOException {
        File tempFile = new File(fileName);
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(multipartFile.getBytes());
            fos.close();
        }
        return tempFile;
    }

    @Override
    public String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }


    @Override
    public ImageResponseDto upload(MultipartFile multipartFile) throws IOException {
            String fileName = multipartFile.getOriginalFilename();
            if (fileName == null || fileName.isEmpty()) {
                throw new InvalidOperationException("Invalid file name");
            }

            fileName = UUID.randomUUID().toString().concat(this.getExtension(fileName));
            File file = this.convertToFile(multipartFile, fileName);
            String URL = this.uploadFile(file, fileName);
            file.delete();

            if (URL == null || URL.isEmpty()) {
                throw new InvalidOperationException("Failed to upload the file");
            }

            Image image = Image.builder()
                    .path(URL)
                    .name(fileName)
                    .type(this.getExtension(fileName))
                    .build();

            imageRepository.save(image);

            return ImageResponseDto.builder().name(fileName).build();

    }


}

