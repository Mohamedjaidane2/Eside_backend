package com.eside.payment.repository;

import com.eside.payment.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet,Long> {

    Optional<Wallet> findByAccountId(Long aLong);
}
