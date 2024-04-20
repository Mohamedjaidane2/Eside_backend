package com.eside.advertisment.controller;

import com.eside.advertisment.dtos.AdvertisementDtos.AdvertisementDto;
import com.eside.advertisment.dtos.CategoryDtos.CategoryDto;
import com.eside.advertisment.dtos.CategoryDtos.CategoryNewDto;
import com.eside.advertisment.dtos.SuccessDto;
import com.eside.advertisment.service.CategoryService;
import com.eside.advertisment.utils.SuccessMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/create")
    public ResponseEntity<SuccessDto> saveCategory(@RequestBody CategoryNewDto categoryNewDto) {
        return ResponseEntity.ok(categoryService.save(categoryNewDto));
    }
    @GetMapping("/all")
    //@ApiOperation(value = "Get advertisements by account")
    public ResponseEntity<List<CategoryDto>> getAll() {
        List<CategoryDto> categoryDtoList = categoryService.findAll();
        return ResponseEntity.ok(categoryDtoList);
    }
}