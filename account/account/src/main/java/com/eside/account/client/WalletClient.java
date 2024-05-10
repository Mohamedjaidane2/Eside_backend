package com.eside.account.client;

import com.eside.account.config.FeignConfig;
import com.eside.account.dtos.SuccessDto;
import com.eside.account.externalDto.WalletDto;
import com.eside.account.externalDto.WalletNewDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "wallet-service",url = "http://localhost:8222/api/wallet" , configuration = FeignConfig.class)
public interface WalletClient {
    @PostMapping("/create")
    WalletDto createWallet (@RequestBody WalletNewDto walletNewDto );

}
