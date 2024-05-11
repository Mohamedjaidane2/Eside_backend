package com.eside.Order.repository;

import com.eside.Order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {

    List<Order> findAllBySenderIdOrderByOrderDateDesc(Long accountId) ;
    List<Order> findAllByAdvertisementId(Long id) ;
    List<Order> findAllByReciverIdOrderByOrderDateDesc(Long reciverID) ;
    //List<Order> findAllBySenderId(Long senderId) ;

    boolean existsBySenderIdAndAdvertisementId(Long accountId, Long advertisementId);
    List<Order> findAllBySenderIdAndAdvertisementIdOrderByOrderDateDesc(Long accountId, Long advertisementId);

}
