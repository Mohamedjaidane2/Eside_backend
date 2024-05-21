package com.eside.auth.client;

import com.eside.auth.externalData.Account;
import com.eside.auth.externalData.NewAccountDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "account-service",url = "${feign.client.account.url}")
//@FeignClient(name = "account-service",url = "http://account-service:8093/api/account")
@Profile({"dev", "prod"})
public interface AccountClient {

    @PostMapping("/create")
    Account createAccountFromAuth (@RequestBody NewAccountDto newAccountDto);
}
