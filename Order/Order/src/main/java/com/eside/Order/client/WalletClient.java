package com.eside.Order.client;

import com.eside.Order.config.FeignConfig;
import com.eside.Order.dtos.SuccessDto;
import com.eside.Order.externalData.WalletActionDto;
import com.eside.Order.externalData.WalletDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "wallet-service",url = "http://localhost:8222/api/wallet" , configuration = FeignConfig.class)
public interface WalletClient {
    @PostMapping("/add-funds")
    SuccessDto addFundsToWalletFromOrder (@RequestBody WalletActionDto walletActionDto );
    @PostMapping("/withdraw-funds")
    SuccessDto withdrawFundsFromOrder (@RequestBody WalletActionDto walletActionDto );
    @GetMapping("/getbyaccount/{accountId}")
    WalletDto getWalletByAccountId (@PathVariable Long accountId);
}

