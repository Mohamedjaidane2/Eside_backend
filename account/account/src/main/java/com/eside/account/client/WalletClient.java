package com.eside.account.client;

import com.eside.account.config.FeignConfig;
import com.eside.account.dtos.SuccessDto;
import com.eside.account.externalDto.WalletDto;
import com.eside.account.externalDto.WalletNewDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "wallet-service",url = "${feign.client.payment.url}" , configuration = FeignConfig.class)
@Profile({"dev", "prod"})
public interface WalletClient {
    @PostMapping("/create")
    WalletDto createWallet (@RequestBody WalletNewDto walletNewDto );

}
