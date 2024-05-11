package com.eside.Order.client;

import com.eside.Order.config.FeignConfig;
import com.eside.Order.dtos.AdvertisementDtos.AdvertisementDto;
import com.eside.Order.dtos.SuccessDto;
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
    @GetMapping("/is-available/{id}")
    Boolean isAvailable (@PathVariable Long id);
    @PutMapping("/updateWhilePayment/{OrderId}/{advertisementId}")
    SuccessDto changerAdvertismentStatusWhilePayment (@PathVariable Long OrderId, @PathVariable Long advertisementId);
    @GetMapping("/account/{accountId}")
    List<AdvertisementDto> getAdvertisementByAccountFromOrder(@PathVariable Long accountId);
    @PutMapping("/deleteOrder/{advertisementId}")
    SuccessDto deleteOrder (@PathVariable Long advertisementId);
    @PutMapping("/updateWhileOrder/{Id}/{advertisementId}")
    Advertisment UpdateAdvertismentStatusFromOrder (@PathVariable Long Id, @PathVariable Long advertisementId);


}