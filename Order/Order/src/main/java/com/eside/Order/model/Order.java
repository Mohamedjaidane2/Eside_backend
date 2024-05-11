package com.eside.Order.model;

import com.eside.Order.enums.OrderStatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "customer_order")
public class Order {

    @Id
    @GeneratedValue()
    private Long orderId;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "order_date")
    private Date orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatusEnum orderStatus;


    private Long advertisementId;

    private Long senderId;

    private Long reciverId;

    //TODO adding discount request relationship
    @OneToOne()
    @JoinColumn(name = "discount_id")
    private DiscountRequest discountRequest;

    private double orderAmount;
    private double seller_benefits;

}
