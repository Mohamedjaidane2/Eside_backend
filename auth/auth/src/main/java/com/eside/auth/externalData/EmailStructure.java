package com.eside.auth.externalData;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
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
