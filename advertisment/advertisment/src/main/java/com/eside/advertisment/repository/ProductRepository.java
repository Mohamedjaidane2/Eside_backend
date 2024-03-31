package com.eside.advertisment.repository;

import com.eside.advertisment.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository <Product,Long> {
    Optional<Product> findByAdvertisement_Id(Long id);
}
