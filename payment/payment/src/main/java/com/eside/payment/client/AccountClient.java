package com.eside.payment.client;

import com.eside.payment.externalData.Account;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "account-service",url = "http://localhost:8222/api/account")
public interface AccountClient {

    @GetMapping("/get/{id}")
    Account getAccountByIdFromPayment (@PathVariable Long id );
}