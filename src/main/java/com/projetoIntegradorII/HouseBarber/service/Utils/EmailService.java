package com.projetoIntegradorII.HouseBarber.service.Utils;

import javax.mail.MessagingException;

public interface EmailService {
    void sendSimpleMessage(String to, String subject, String text) throws MessagingException ;
}
