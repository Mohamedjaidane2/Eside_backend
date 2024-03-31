package com.eside.account.repository;

import com.eside.account.model.Account;
import com.eside.account.model.FeedBack;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedBackRepository extends JpaRepository<FeedBack,Long> {
    List<FeedBack> findBySenderAccount (Account senderAccount );
    List<FeedBack> findByReceiverAccount (Account reciverAccount );
}
