package com.eside.advertisment.service;

import com.eside.advertisment.dtos.CategoryDtos.CategoryDto;
import com.eside.advertisment.dtos.CategoryDtos.CategoryNewDto;
import com.eside.advertisment.dtos.CategoryDtos.CategoryUpdateDto;
import com.eside.advertisment.dtos.SuccessDto;
import com.eside.advertisment.model.Category;

import java.util.List;

public interface CategoryService {
    CategoryDto findById(Long id);
    List<CategoryDto> findAll();
    SuccessDto save(CategoryNewDto categoryNewDto);
    SuccessDto update(CategoryUpdateDto categoryUpdateDto);
    SuccessDto deleteById(Long id);
}
