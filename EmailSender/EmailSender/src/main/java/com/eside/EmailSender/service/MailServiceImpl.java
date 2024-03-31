package com.eside.EmailSender.service;

import com.eside.EmailSender.model.EmailStructure;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements IMailService{

    public static final String SENDER="jaidanemohammed@gmail.com";
    private final JavaMailSender mailSender;

    @Override
    public void sendMail(String mail, EmailStructure emailStructure) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(SENDER);
        simpleMailMessage.setSubject(emailStructure.getSubject());
        simpleMailMessage.setText(emailStructure.getMessage());
        simpleMailMessage.setTo(mail);

        mailSender.send(simpleMailMessage);
    }
}
