package com.eside.advertisment.repository;

import com.eside.advertisment.model.Advertisment;
import com.eside.advertisment.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    Optional<Category> findByName (String name);
}
