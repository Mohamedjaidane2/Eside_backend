package com.eside.account.repository;

import com.eside.account.model.Account;
import com.eside.account.model.Information;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InformationRepository extends JpaRepository<Information , Long> {

    Information findByAccount (Account account);
}
