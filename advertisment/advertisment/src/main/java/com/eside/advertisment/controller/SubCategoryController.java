package com.eside.advertisment.controller;

import com.eside.advertisment.dtos.SubCategoryDtos.SubCategoryNewDto;
import com.eside.advertisment.dtos.SuccessDto;
import com.eside.advertisment.service.SubCategoryService;
import com.eside.advertisment.utils.SuccessMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/subcategories")
public class SubCategoryController {

    private final SubCategoryService subCategoryService;

    @Autowired
    public SubCategoryController(SubCategoryService subCategoryService) {
        this.subCategoryService = subCategoryService;
    }

    @PostMapping("/create")
    public ResponseEntity<SuccessDto> saveSubCategory(@RequestBody SubCategoryNewDto subCategoryNewDto) {
        subCategoryService.save(subCategoryNewDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(SuccessDto.builder()
                .message(SuccessMessage.SUCCESSFULLY_CREATED)
                .build());
    }
}