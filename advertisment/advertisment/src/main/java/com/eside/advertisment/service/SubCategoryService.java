package com.eside.advertisment.service;

import com.eside.advertisment.dtos.CategoryDtos.CategoryDto;
import com.eside.advertisment.dtos.CategoryDtos.CategoryNewDto;
import com.eside.advertisment.dtos.CategoryDtos.CategoryUpdateDto;
import com.eside.advertisment.dtos.SubCategoryDtos.SubCategoryDto;
import com.eside.advertisment.dtos.SubCategoryDtos.SubCategoryNewDto;
import com.eside.advertisment.dtos.SubCategoryDtos.SubCategoryUpdateDto;
import com.eside.advertisment.dtos.SuccessDto;

import java.util.List;

public interface SubCategoryService {

    SubCategoryDto findById(Long id);
    List<SubCategoryDto> findAll();
    SuccessDto save(SubCategoryNewDto subCategoryNewDto);
    SuccessDto update(SubCategoryUpdateDto categoryUpdateDto);
    SuccessDto deleteById(Long id);
}
