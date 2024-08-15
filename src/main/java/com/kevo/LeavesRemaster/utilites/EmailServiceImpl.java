package com.kevo.LeavesRemaster.utilites;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailServiceImpl {
    private final JavaMailSender emailSender;

    @Async
    public void sendMessage(
            String to,
            String subject,
            String text
    ) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("leavesremastered@mail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        System.out.println("Email sent");

        emailSender.send(message);
    }
}
