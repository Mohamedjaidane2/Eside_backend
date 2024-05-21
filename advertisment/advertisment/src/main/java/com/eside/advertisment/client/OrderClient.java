package com.eside.advertisment.client;

import com.eside.advertisment.config.FeignConfig;
import com.eside.advertisment.dtos.SuccessDto;
import com.eside.advertisment.externalData.FavortieDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "order-service",url = "${feign.client.order.url}" , configuration = FeignConfig.class)
@Profile({"dev", "prod"})
public interface OrderClient {

    @DeleteMapping("/delete/{orderId}")
    SuccessDto deleteOrder (@PathVariable Long orderId );

}