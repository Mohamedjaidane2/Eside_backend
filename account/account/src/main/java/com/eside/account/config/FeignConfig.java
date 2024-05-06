
package com.eside.account.config;

import org.springframework.context.annotation.Bean;

public class FeignConfig {
    @Bean
    System.Logger.Level feignLoggerLevel() {
        return System.Logger.Level.ALL;
    }
}
