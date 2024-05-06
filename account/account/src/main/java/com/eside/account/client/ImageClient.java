package com.eside.account.client;

import com.eside.account.config.FeignConfig;
import com.eside.account.externalDto.ImageDto;
import com.eside.account.model.Account;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "image-service",url = "http://localhost:8222/api/image" , configuration = FeignConfig.class)
public interface ImageClient {

    @GetMapping("/get-by-name/{name}")
    ImageDto getByNameFromAccount (@PathVariable String name );
}
