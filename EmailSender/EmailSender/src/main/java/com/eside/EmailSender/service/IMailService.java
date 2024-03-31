package com.eside.EmailSender.service;

import com.eside.EmailSender.model.EmailStructure;

public interface IMailService {

    public void sendMail (String mail , EmailStructure emailStructure);
}
