package com.eside.auth.client;

import com.eside.auth.externalData.Account;
import com.eside.auth.externalData.NewAccountDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "account-service",url = "http://localhost:8222/api/account")
public interface AccountClient {

    @PostMapping("/create")
    Account createAccountFromAuth (@RequestBody NewAccountDto newAccountDto);
}
