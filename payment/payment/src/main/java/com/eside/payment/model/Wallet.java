package com.eside.payment.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "wallet")
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long wallet_id;

    private double balance;

    private Long accountId;

    @OneToMany(mappedBy="wallet")
    private List<Transaction> transactions;

    @OneToMany(mappedBy="wallet")
    private List<BankData> bankDataList;

}