package com.eside.advertisment.controller;

import com.eside.advertisment.dtos.CategoryDtos.CategoryDto;
import com.eside.advertisment.dtos.SubCategoryDtos.SubCategoryDto;
import com.eside.advertisment.dtos.SubCategoryDtos.SubCategoryNewDto;
import com.eside.advertisment.dtos.SuccessDto;
import com.eside.advertisment.service.SubCategoryService;
import com.eside.advertisment.utils.SuccessMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        return ResponseEntity.ok(subCategoryService.save(subCategoryNewDto));
    }

    @GetMapping("/all")
    //@ApiOperation(value = "Get advertisements by account")
    public ResponseEntity<List<SubCategoryDto>> getAll() {
        List<SubCategoryDto> subCategoryDtoList = subCategoryService.findAll();
        return ResponseEntity.ok(subCategoryDtoList);
    }
}