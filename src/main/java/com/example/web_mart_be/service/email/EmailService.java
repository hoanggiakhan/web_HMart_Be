package com.example.web_mart_be.service.email;

public interface EmailService {
    public void sendMessage(String from, String to, String subject, String message);
}
