package com.eside.payment.model;

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
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue()
    private Long id;

    private Double amount;

    private String action ;

    private boolean is_canceled;

    private Double balanceBeforeAction;

    private Double current_balance;


    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "transaction_date", updatable = false)
    private Date transaction_date;

    @ManyToOne
    @JoinColumn(name = "wallet_id", nullable = false)
    private Wallet wallet;


}