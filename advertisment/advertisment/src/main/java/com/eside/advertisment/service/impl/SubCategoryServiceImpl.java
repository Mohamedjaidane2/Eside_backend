package com.eside.advertisment.service.impl;

import com.eside.advertisment.dtos.SubCategoryDtos.SubCategoryDto;
import com.eside.advertisment.dtos.SubCategoryDtos.SubCategoryNewDto;
import com.eside.advertisment.dtos.SubCategoryDtos.SubCategoryUpdateDto;
import com.eside.advertisment.dtos.SuccessDto;
import com.eside.advertisment.exception.EntityNotFoundException;
import com.eside.advertisment.exception.InvalidOperationException;
import com.eside.advertisment.model.Category;
import com.eside.advertisment.model.SubCategory;
import com.eside.advertisment.repository.CategoryRepository;
import com.eside.advertisment.repository.SubCategoryRepository;
import com.eside.advertisment.service.AdvertismentService;
import com.eside.advertisment.service.SubCategoryService;
import com.eside.advertisment.utils.SuccessMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class SubCategoryServiceImpl implements SubCategoryService {

    private final SubCategoryRepository subCategoryRepository;
    private final CategoryRepository categoryRepository;
    @Override
    public SubCategoryDto findById(Long id) {
        return null;
    }

    @Override
    public List<SubCategoryDto> findAll() {
        return null;
    }

    @Override
    public SuccessDto save(SubCategoryNewDto subCategoryNewDto) {
        {
            Optional<Category> optionalCategory = categoryRepository.findById(subCategoryNewDto.getCategoryId());

            if (optionalCategory.isEmpty()) {
                throw new EntityNotFoundException("Category not found");
            }
            Optional<SubCategory> subCategoryOptional = subCategoryRepository.findByName(subCategoryNewDto.getName());

            if (subCategoryOptional.isPresent()) {
                throw new InvalidOperationException("SubCategory with this name already exist!");
            }

            SubCategory subCategory = SubCategory.builder()
                    .name(subCategoryNewDto.getName())
                    .description(subCategoryNewDto.getDescription())
                    .Category(optionalCategory.get())
                    .creationDate(new Date())
                    .build();
            subCategoryRepository.save(subCategory);
            return SuccessDto.builder()
                    .message(SuccessMessage.SUCCESSFULLY_CREATED)
                    .build();
        }
    }

    @Override
    public SuccessDto update(SubCategoryUpdateDto categoryUpdateDto) {
        return null;
    }

    @Override
    public SuccessDto deleteById(Long id) {
        return null;
    }
}
