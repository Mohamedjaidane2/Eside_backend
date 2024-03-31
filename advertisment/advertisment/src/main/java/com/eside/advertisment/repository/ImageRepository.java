package com.eside.advertisment.repository;

import com.eside.advertisment.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    Optional<Image> findByName(String name);
    List<Image> findByProduct_Id(Long id );

    Optional<Image> findByPath(String path);

}