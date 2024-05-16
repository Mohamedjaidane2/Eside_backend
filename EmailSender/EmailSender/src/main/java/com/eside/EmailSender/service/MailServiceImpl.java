package com.eside.EmailSender.service;

import com.eside.EmailSender.model.EmailTemplateName;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements IMailService{

    public static final String SENDER="jaidanemohammed@gmail.com";
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Async
    @Override
    public void sendMail(
            String to,
            String userName,
            EmailTemplateName emailTemplate,
            String confirmationUrl,
            String activationCode,
            String subject
    ) throws MessagingException {
        String templateName ;
        if(emailTemplate ==null){
            templateName="confirm-email";
        }else {
            templateName = emailTemplate.name();
        }

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(
                mimeMessage,
                MimeMessageHelper.MULTIPART_MODE_MIXED,
                StandardCharsets.UTF_8.name()
        );
        Map<String,Object> propreties = new HashMap<>();
        propreties.put("username",userName);
        propreties.put("confirmationUrl",confirmationUrl);
        propreties.put("activation_code",activationCode);
        Context context = new Context();
        context.setVariables(propreties);


        helper.setFrom(SENDER);
        helper.setTo(to);
        helper.setSubject(subject);

        String template = templateEngine.process(templateName ,context);

        helper.setText(template,true);

        mailSender.send(mimeMessage);
    }
}
