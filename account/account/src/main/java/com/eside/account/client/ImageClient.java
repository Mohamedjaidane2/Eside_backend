package com.eside.account.client;

import com.eside.account.config.FeignConfig;
import com.eside.account.externalDto.ImageDto;
import com.eside.account.model.Account;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "image-service",url = "${feign.client.image.url}" , configuration = FeignConfig.class)
@Profile({"dev", "prod"})
public interface ImageClient {

    @GetMapping("/get-by-name/{name}")
    ImageDto getByNameFromAccount (@PathVariable String name );
}
