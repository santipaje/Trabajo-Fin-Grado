package com.socialhub.tfg.service;

import com.socialhub.tfg.domain.User;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;

public interface EmailService {
    void sendEmailVerification(User user, String token) throws MessagingException;

    void sendPassResetEmail(User user, String token) throws MessagingException;
}
