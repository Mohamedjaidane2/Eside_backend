package com.eside.advertisment.service;

import com.eside.advertisment.dtos.ProductDtos.ProductDto;
import com.eside.advertisment.dtos.ProductDtos.ProductNewDto;
import com.eside.advertisment.dtos.ProductDtos.ProductUpdateDto;
import com.eside.advertisment.dtos.SuccessDto;
import com.eside.advertisment.model.Product;

import java.util.List;

public interface ProductService {

    ProductDto addProduct(ProductNewDto productNewDto);

    SuccessDto updateProduct(ProductUpdateDto productUpdateDto , Long productId);


    ProductDto getProductById(Long productId);

    List<ProductDto> getAllProduct();

    ProductDto getProductByAdvertisment ( Long id);
    SuccessDto deleteProductById( Long productId);

}
