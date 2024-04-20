package com.eside.advertisment.service.impl;

import com.eside.advertisment.dtos.CategoryDtos.CategoryDto;
import com.eside.advertisment.dtos.CategoryDtos.CategoryNewDto;
import com.eside.advertisment.dtos.CategoryDtos.CategoryUpdateDto;
import com.eside.advertisment.dtos.ProductDtos.ProductDto;
import com.eside.advertisment.dtos.SuccessDto;
import com.eside.advertisment.enums.AdvertisementSoldStatusEnum;
import com.eside.advertisment.enums.AdvertisementStatusEnum;
import com.eside.advertisment.exception.EntityNotFoundException;
import com.eside.advertisment.externalData.Account;
import com.eside.advertisment.model.Advertisment;
import com.eside.advertisment.model.Category;
import com.eside.advertisment.model.Product;
import com.eside.advertisment.repository.CategoryRepository;
import com.eside.advertisment.service.AdvertismentService;
import com.eside.advertisment.service.CategoryService;
import com.eside.advertisment.utils.SuccessMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    @Override
    public CategoryDto findById(Long id) {
        return null;
    }

    @Override
    public List<CategoryDto> findAll() {
        return categoryRepository.findAll()
                .stream()
                .map(CategoryDto::customMapping)
                .collect(Collectors.toList());
    }

    @Override
    public SuccessDto save(CategoryNewDto categoryNewDto) {
        Optional<Category> categoryOptional = categoryRepository.findByName(categoryNewDto.getName());

        if (categoryOptional.isPresent()) {
            throw new EntityNotFoundException("Category with this name already exist!");
        }

        Category category = Category.builder()
                .name(categoryNewDto.getName())
                .description(categoryNewDto.getDescription())
                .creationDate(new Date())
                .build();
        categoryRepository.save(category);
        return SuccessDto.builder()
                .message(SuccessMessage.SUCCESSFULLY_CREATED)
                .build();
    }

    @Override
    public SuccessDto update(CategoryUpdateDto categoryUpdateDto) {
        return null;
    }

    @Override
    public SuccessDto deleteById(Long id) {
        return null;
    }
}
