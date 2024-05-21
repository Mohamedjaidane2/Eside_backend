package com.eside.auth.client;

import com.eside.auth.externalData.EmailStructure;
import jakarta.mail.MessagingException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "email-service",url = "${feign.client.email.url}")
@Profile({"dev", "prod"})
public interface EmailClient {

    @PostMapping("/send")
    void sendMail (@RequestBody EmailStructure emailStructure) throws MessagingException;
}

