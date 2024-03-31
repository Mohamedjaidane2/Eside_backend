package com.eside.advertisment.repository;

import com.eside.advertisment.model.Advertisment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdvertismentRepository extends JpaRepository<Advertisment,Long> {

    List<Advertisment> findByUserAccountId(Long id);
}
