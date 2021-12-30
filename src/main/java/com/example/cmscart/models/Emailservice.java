package com.example.cmscart.models;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class Emailservice {

  @Autowired
  private JavaMailSender emailSender;
  
  public void sendSimpleMessage(String to, String subject, String text) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom("sairohithpavan2002@gmail.com");
    message.setTo(to);
    message.setSubject(subject);
    message.setText(text);
    emailSender.send(message);
    System.out.println("email sent");
  }
    
}
