package com.eside.advertisment.controller;


import com.eside.advertisment.dtos.AdvertisementDtos.AdvertisementNewDto;
import com.eside.advertisment.dtos.ProductDtos.ProductDto;
import com.eside.advertisment.dtos.ProductDtos.ProductNewDto;
import com.eside.advertisment.dtos.ProductDtos.ProductUpdateDto;
import com.eside.advertisment.dtos.SuccessDto;
import com.eside.advertisment.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/product")
@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping("/create")
    //@ApiOperation(value = "Create product")
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductNewDto productNewDto) {
        return ResponseEntity.ok(productService.addProduct(productNewDto));
    }

    @PutMapping("/update")
    //@ApiOperation(value = "Update product")
    public ResponseEntity<SuccessDto> updateProduct(@RequestBody ProductUpdateDto productUpdateDto, Long productId) {
        return ResponseEntity.ok(productService.updateProduct(productUpdateDto,productId));
    }

    @GetMapping("/byId/{productId}") // Updated mapping to /byId/{productId}
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long productId) {
        ProductDto product = productService.getProductById(productId);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/byAdvertisment/{advertismentId}") // Updated mapping to /byAdvertisment/{advertismentId}
    public ResponseEntity<ProductDto> getProductByAdvertisment(@PathVariable Long advertismentId) {
        ProductDto product = productService.getProductByAdvertisment(advertismentId);
        return ResponseEntity.ok(product);
    }
    @GetMapping("/all")
    //@ApiOperation(value = "Get all products")
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<ProductDto> products = productService.getAllProduct();
        return ResponseEntity.ok(products);
    }

    @DeleteMapping("/delete/{productId}")
    //@ApiOperation(value = "Delete product by ID")
    public ResponseEntity<SuccessDto> deleteProductById(@PathVariable Long productId) {
        return ResponseEntity.ok(productService.deleteProductById(productId));
    }
}
