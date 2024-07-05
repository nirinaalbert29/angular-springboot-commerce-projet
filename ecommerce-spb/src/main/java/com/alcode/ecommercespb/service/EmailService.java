package com.alcode.ecommercespb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender emailSender;

    public void sendSimpleMessage(String to, String subject, String text) {
        try {
        	
        	MimeMessage message = emailSender.createMimeMessage();
        	MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
        	helper.setFrom("E-commerce-Angular-SpringBoot");  // Utilisation du nom sans l'adresse email complète
        	helper.setTo(to);
        	helper.setSubject("Login E-commerce validation");
        	String content = "<p style=\"font-family: Arial, sans-serif; color: #333;\">Bonjour,</p>"
        	                 + "<p style=\"font-family: Arial, sans-serif; color: #333;\">Voici votre message personnalisé avec un "
        	                 + "<a href=\"http://localhost:8080\" style=\"color: #007bff; text-decoration: none;\">lien</a>.</p>";

        	helper.setText(content, true);
        	emailSender.send(message);


        } catch (Exception e) {
            logger.error("Failed to send email to: {}", to, e);
        }
    }
}
