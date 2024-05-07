package com.eside.account.repository;

import com.eside.account.model.Account;
import com.eside.account.model.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite,Long> {
    Optional<Favorite> findByAccountId(Long accountId);
    Optional<Favorite> findByAccountIdAndAdvertismentId(Long accountId, Long advertismentId);
    Optional<List<Favorite>> findAllByAccountId(Long accountId);



}
