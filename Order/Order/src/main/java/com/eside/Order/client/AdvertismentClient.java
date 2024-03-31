package com.eside.Order.client;

import com.eside.Order.config.FeignConfig;
import com.eside.Order.dtos.AdvertisementDtos.AdvertisementDto;
import com.eside.Order.externalData.Account;
import com.eside.Order.externalData.Advertisment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "advertisment-service",url = "http://localhost:8222/api/advertisement" , configuration = FeignConfig.class)
public interface AdvertismentClient {
    @GetMapping("/{advertisementId}")
    Advertisment getAdvertismentByIdFromOrder (@PathVariable Long advertisementId);
    @PutMapping("/updateWhileOrder/{OrderId}/{advertisementId}")
    Advertisment UpdateAdvertismentStatusFromOrder (@PathVariable Long OrderId, @PathVariable Long advertisementId);
    @GetMapping("/account/{accountId}")
    List<AdvertisementDto> getAdvertisementByAccountFromOrder(@PathVariable Long accountId);



}
