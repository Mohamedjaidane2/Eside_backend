package com.eside.advertisment.repository;

import com.eside.advertisment.model.Advertisment;
import com.eside.advertisment.model.Category;
import com.eside.advertisment.model.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubCategoryRepository extends JpaRepository<SubCategory,Long> {
    Optional<SubCategory> findByName (String name);
}
