package com.eside.account.client;

import com.eside.account.config.FeignConfig;
import com.eside.account.externalDto.AdvertisementDtos.AdvertisementDto;
import com.eside.account.externalDto.Advertisment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import java.util.List;

@FeignClient(name = "advertisment-service",url = "http://localhost:8222/api/advertisement" , configuration = FeignConfig.class)
public interface AdvertismentClient {
    @GetMapping("/{advertisementId}")
    Advertisment getAdvertismentByIdFromAccount (@PathVariable Long advertisementId);
    @PutMapping("/updateWhileOrder/{OrderId}/{advertisementId}")
    Advertisment UpdateAdvertismentStatusFromAccount (@PathVariable Long OrderId, @PathVariable Long advertisementId);
    @GetMapping("/account/{accountId}")
    List<AdvertisementDto> getAdvertisementByAccountFromAccount(@PathVariable Long accountId);



}
