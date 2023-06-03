package com.projetoIntegradorII.HouseBarber.service.Utils;


public interface EmailService {
    void sendSimpleMessage(String to, String subject, String text);
}
