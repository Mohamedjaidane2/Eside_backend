package com.eside.Order.client;

import com.eside.Order.config.FeignConfig;
import com.eside.Order.externalData.Account;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "account-service",url = "${feign.client.account.url}" , configuration = FeignConfig.class)
@Profile({"dev", "prod"})
public interface AccountClient {

    @GetMapping("/get/{id}")
    Account getAccountByIdFromOrder (@PathVariable Long id );
}
