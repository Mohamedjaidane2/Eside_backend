package com.eside.payment.client;

import com.eside.payment.externalData.Account;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "account-service",url = "http://localhost:8222/api/account")
public interface AccountClient {

    @PostMapping("/get/id")
    Account getAccountByIdFromPayment (@RequestBody Long id );
}
