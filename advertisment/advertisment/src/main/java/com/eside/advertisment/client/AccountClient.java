package com.eside.advertisment.client;

import com.eside.advertisment.config.FeignConfig;
import com.eside.advertisment.externalData.Account;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "account-service",url = "http://localhost:8222/api/account" , configuration = FeignConfig.class)
public interface AccountClient {

    @GetMapping("/get/{id}")
    Account getAccountByIdFromAccount (@PathVariable Long id );
}
