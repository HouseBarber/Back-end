package com.projetoIntegradorII.HouseBarber.service.Utils;


import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSenderImpl mailSender;




    @Override
    public void sendSimpleMessage(String to, String subject, String text) throws MessagingException {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("viniciusnakahara@gmail.com");
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
            mailSender.send(message);
        } catch (Exception ex) {
            throw new RuntimeException();
        }
    }
    
}
