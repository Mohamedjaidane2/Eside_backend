package com.eside.advertisment.controller;

import com.eside.advertisment.dtos.ImagesDtos.ImageDto;
import com.eside.advertisment.dtos.ImagesDtos.ImageNewDto;
import com.eside.advertisment.dtos.ImagesDtos.ImageResponseDto;
import com.eside.advertisment.dtos.SuccessDto;
import com.eside.advertisment.exception.InvalidOperationException;
import com.eside.advertisment.model.Image;
import com.eside.advertisment.service.impl.ImageServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/image")
@RequiredArgsConstructor
public class ImageController {

    private final ImageServiceImpl imageService;

    @PostMapping("/upload/firebase/")
    public ResponseEntity<ImageResponseDto> upload(@RequestParam("file") MultipartFile multipartFile) throws IOException {

            return ResponseEntity.ok(imageService.upload(multipartFile));

    }
    @PostMapping("/upload/")
    public ResponseEntity<SuccessDto> uploadImage(@RequestBody ImageNewDto image)  {
        return ResponseEntity.status(HttpStatus.OK).body(imageService.uploadImage(image));
    }

    @GetMapping("/get-by-path/{filePath}") // Updated mapping to /download/{fileName}
    public ResponseEntity<?> getByPath(@PathVariable String filePath) {
        ImageDto imageData = imageService.getImageByPath(filePath);
        return ResponseEntity.status(HttpStatus.OK)
                .body(imageData);
    }
    @GetMapping("/get-by-name/{name}") // Updated mapping to /download/{fileName}
    public ResponseEntity<?> getByName(@PathVariable String name) {
        ImageDto imageData = imageService.getImageByName(name);
        return ResponseEntity.status(HttpStatus.OK)
                .body(imageData);
    }

    @GetMapping("/get-by-product/{productId}") // Updated mapping to /get-by-product/{productId}
    public ResponseEntity<List<ImageDto>> getByProductId(@PathVariable Long productId) {
        List<ImageDto> imageData = imageService.getByProductId(productId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(imageData);
    }

    @GetMapping("/get-by-id/{imageId}") // Updated mapping to /get-by-id/{imageId}
    public ResponseEntity<?> getById(@PathVariable Long imageId) {
        ImageDto imageData = imageService.getById(imageId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(imageData);
    }
}
