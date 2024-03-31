package com.eside.payment.repository;

import com.eside.payment.model.BankData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankDataRepository extends JpaRepository<BankData,Long> {
}
