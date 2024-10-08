package com.eside.EmailSender.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailStructure {
    private String to;
    String userName;
    EmailTemplateName emailTemplate;
    String confirmationUrl;
    String activationCode;
    String subject;
}
