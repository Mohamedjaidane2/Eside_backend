package com.eside.account.client;

import com.eside.account.config.FeignConfig;
import com.eside.account.model.Account;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "account-service",url = "http://localhost:8222/api/account" , configuration = FeignConfig.class)
public interface AccountClient {

    @GetMapping("/get/{id}")
    Account getAccountByIdFromAccount (@PathVariable Long id );
}
