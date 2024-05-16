package com.eside.auth.client;

import com.eside.EmailSender.service.MailServiceImpl;
import com.eside.auth.externalData.Account;
import com.eside.auth.externalData.EmailStructure;
import com.eside.auth.externalData.NewAccountDto;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "email-service",url = "http://localhost:8222/api/v1/mail")
public interface EmailClient {

    @PostMapping("/send")
    void sendMail (@RequestBody EmailStructure emailStructure) throws MessagingException;
}

