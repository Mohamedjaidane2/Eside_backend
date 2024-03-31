package com.eside.Order.repository;

import com.eside.Order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {

    List<Order> findByAccountId(Long accountId) ;
    List<Order> findByAdvertisementId(Long accountId) ;

    boolean existsByAccountIdAndAdvertisementId(Long accountId, Long advertisementId);

}
