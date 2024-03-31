package com.eside.Order.model;

import com.eside.Order.enums.DiscountRequestStatus;
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
@Table(name = "requested_discount")
public class DiscountRequest {
    @Id
    @GeneratedValue()
    private Long id ;

    @OneToOne()
    @JoinColumn(name = "order_id")
    private Order order;

    private double requestedAmount;

    @Enumerated(EnumType.STRING)

    private DiscountRequestStatus eDiscountRequestStats;

    private double counterDiscountAmount;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    private Date requestDate;

}
