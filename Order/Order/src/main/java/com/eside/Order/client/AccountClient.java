package com.eside.Order.client;

import com.eside.Order.config.FeignConfig;
import com.eside.Order.externalData.Account;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "account-service",url = "http://localhost:8222/api/account" , configuration = FeignConfig.class)
public interface AccountClient {

    @PostMapping("/get/id")
    Account getAccountByIdFromOrder (@RequestBody Long id );
}
