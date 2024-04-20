package com.eside.advertisment.service.impl;

import ch.qos.logback.core.spi.ErrorCodes;
import com.eside.advertisment.dtos.ProductDtos.ProductDto;
import com.eside.advertisment.dtos.ProductDtos.ProductNewDto;
import com.eside.advertisment.dtos.ProductDtos.ProductUpdateDto;
import com.eside.advertisment.dtos.SuccessDto;
import com.eside.advertisment.exception.EntityNotFoundException;
import com.eside.advertisment.model.Category;
import com.eside.advertisment.model.Image;
import com.eside.advertisment.model.Product;
import com.eside.advertisment.model.SubCategory;
import com.eside.advertisment.repository.CategoryRepository;
import com.eside.advertisment.repository.ImageRepository;
import com.eside.advertisment.repository.ProductRepository;
import com.eside.advertisment.repository.SubCategoryRepository;
import com.eside.advertisment.service.ProductService;
import com.eside.advertisment.utils.SuccessMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ImageRepository imageRepository;
    private final ProductRepository iProductRepository;
    private final CategoryRepository categoryRepository;
    private final SubCategoryRepository SubcategoryRepository;
    private final ModelMapper modelMapper;
    @Override
    @Transactional
    public ProductDto addProduct(ProductNewDto productNewDto) {
        Category category = categoryRepository.findById(productNewDto.getCategoryId())
                .orElseThrow(()->new EntityNotFoundException("Category not found"));

        SubCategory subCategory = SubcategoryRepository.findById(productNewDto.getSubcategoryId())
                .orElseThrow(()->new EntityNotFoundException("SubCategory not found"));

        Product product = Product.builder()
                .productStatus(productNewDto.getProductStatus())
                .color(productNewDto.getColor())
                .features(productNewDto.getFeatures())
                .subCategory(subCategory)
                .creationDate(new Date())
                .build();
        List<Image> imageList = new ArrayList<>();
        for(Long imageId : productNewDto.getImages()){
            Image searchedImage = imageRepository.findById(imageId)
                    .orElseThrow(()-> new EntityNotFoundException("Image not found"));
            searchedImage.setProduct(product);
            imageRepository.save(searchedImage);
        }
        product.setImages(imageList);
        iProductRepository.save(product);
        return ProductDto.customMapping(product);

    }

    @Override
    public SuccessDto updateProduct(ProductUpdateDto productUpdateDto, Long productId) {
        Product product = iProductRepository.findById(productId)
                .orElseThrow(()->new EntityNotFoundException("Product not found"));

        modelMapper.map(productUpdateDto, product);
        iProductRepository.save(product);

        return SuccessDto.builder()
                .message(SuccessMessage.SUCCESSFULLY_UPDATED)
                .build();
    }

    @Override
    public ProductDto getProductById(Long productId) {

        Optional<Product> product = iProductRepository.findById(productId);
        if (product.isEmpty()) {
            throw new EntityNotFoundException("Product not found");
        }
        return ProductDto.customMapping(product.get());
    }

    @Override
    public List<ProductDto> getAllProduct() {
        return iProductRepository.findAll()
                .stream()
                .map(ProductDto::customMapping)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDto getProductByAdvertisment(Long id) {
        Product product = iProductRepository.findByAdvertisement_Id(id)
                .orElseThrow(()->{throw new EntityNotFoundException("product not found");});
        return ProductDto.customMapping(product);
    }

    @Override
    public SuccessDto deleteProductById( Long productId ) {

        Optional<Product> product = iProductRepository.findById(productId);
        if(product.isEmpty())
            throw new EntityNotFoundException("product not found!");
        iProductRepository.delete(product.get());
        return SuccessDto
                .builder()
                .message(SuccessMessage.SUCCESSFULLY_REMOVED)
                .build();
    }
}
