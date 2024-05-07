package com.eside.advertisment.client;

import com.eside.advertisment.config.FeignConfig;
import com.eside.advertisment.externalData.Account;
import com.eside.advertisment.externalData.FavortieDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "favorites-service",url = "http://localhost:8222/api/favorite" , configuration = FeignConfig.class)
public interface FavoritesClient {

        @GetMapping("/get-all/by-account/{id}")
        List<FavortieDto> getfavorites (@PathVariable Long id );

}
