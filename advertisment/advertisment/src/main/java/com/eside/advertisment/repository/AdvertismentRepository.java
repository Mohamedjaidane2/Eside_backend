package com.eside.advertisment.repository;

import com.eside.advertisment.model.Advertisment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdvertismentRepository extends JpaRepository<Advertisment, Long> {

    List<Advertisment> findByUserAccountId(Long id);

    List<Advertisment> findAllByUserAccountIdNot(Long userAccountId);

    List<Advertisment> findAllByProduct_SubCategory_NameAndUserAccountIdNot(String categoryName, Long userAccountId);

    // Top 10 newest advertisements excluding the user's own advertisements
    List<Advertisment> findTop10ByUserAccountIdNotOrderByCreationDateDesc(Long userAccountId);

    // Find all advertisements ordered by creation date excluding the user's own advertisements
    List<Advertisment> findByUserAccountIdNotOrderByCreationDateDesc(Long userAccountId);
}