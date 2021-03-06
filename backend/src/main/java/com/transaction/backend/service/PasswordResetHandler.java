package com.transaction.backend.service;

import com.transaction.backend.entity.User;
import com.transaction.backend.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PasswordResetHandler {

    @Value("${app.email.reset.subject}")
    private String subject;

    @Value("${app.email.reset.content}")
    private String content;

    @Value("${app.url}")
    private String appUrl;

    @Value("${app.reset-url}")
    private String resetUrl;

    @Autowired
    private EMailSender EMailSender;

    @Autowired
    private UserRepository userRepository;

    public void sendResetMail(User user) {
        String code = UUID.randomUUID().toString();

        user.setResetCode(code);

        userRepository.save(user);

        String tokenUrl = appUrl + resetUrl + code;

        EMailSender.sendMail(user.getEmail(), subject, String.format(content, tokenUrl));
    }
}
