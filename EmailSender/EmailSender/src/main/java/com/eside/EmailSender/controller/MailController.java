package com.eside.EmailSender.controller;

import com.eside.EmailSender.model.EmailStructure;
import com.eside.EmailSender.service.MailServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/mail")
@RequiredArgsConstructor
public class MailController {

    private final MailServiceImpl mailService;
    @PostMapping("/send/{mail}")
    public String sendMail(@PathVariable String mail , @RequestBody EmailStructure emailStructure){
        mailService.sendMail(mail,emailStructure);
        return "Succsesfuly sended !";
    }

}
