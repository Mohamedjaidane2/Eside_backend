package com.eside.EmailSender.controller;

import com.eside.EmailSender.model.EmailStructure;
import com.eside.EmailSender.service.IMailService;
import com.eside.EmailSender.service.MailServiceImpl;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/mail")
@RequiredArgsConstructor
public class MailController {

    private final IMailService mailService;
    @PostMapping("/send")
    public void sendMail( @RequestBody EmailStructure emailStructure) throws MessagingException {
        System.out.println("________________________________________________________________________________");
        System.out.println("recived data "+emailStructure);
        mailService.sendMail(
                emailStructure.getTo(),
                emailStructure.getUserName(),
                emailStructure.getEmailTemplate(),
                emailStructure.getConfirmationUrl(),
                emailStructure.getActivationCode(),
                emailStructure.getSubject()
        );
    }

}
