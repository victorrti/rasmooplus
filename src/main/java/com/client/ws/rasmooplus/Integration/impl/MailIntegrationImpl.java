package com.client.ws.rasmooplus.Integration.impl;

import com.client.ws.rasmooplus.Integration.MailIntegration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class MailIntegrationImpl implements MailIntegration {
    @Autowired
    private JavaMailSender javaMailSender;
    @Override
    public void send(String mailTo, String message,String subject) {
        SimpleMailMessage simpleMailMessage =  new SimpleMailMessage();
        simpleMailMessage.setTo(mailTo);
        simpleMailMessage.setSubject("Acesso liberado...");
        simpleMailMessage.setText(message);

    }
}
