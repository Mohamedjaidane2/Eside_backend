package com.eside.EmailSender.service;

import com.eside.EmailSender.model.EmailStructure;
import com.eside.EmailSender.model.EmailTemplateName;
import jakarta.mail.MessagingException;

public interface IMailService {



    void sendMail(
            String to,
            String userName,
            EmailTemplateName emailTemplate,
            String confirmationUrl,
            String activationCode,
            String subject
    ) throws MessagingException;
}
