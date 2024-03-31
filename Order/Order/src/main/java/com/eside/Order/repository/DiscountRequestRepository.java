package com.eside.Order.repository;

import com.eside.Order.model.DiscountRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountRequestRepository extends JpaRepository<DiscountRequest,Long> {
}
