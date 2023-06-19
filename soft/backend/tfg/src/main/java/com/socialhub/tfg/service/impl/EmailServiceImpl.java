package com.socialhub.tfg.service.impl;

import com.socialhub.tfg.domain.User;
import com.socialhub.tfg.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailServiceImpl.class);
    @Value("${spring.mail.username}")
    private String emailSH;

    @Value("${frontConfirmEmailUrl}")
    private String confirmationUrl;

    @Value("${frontResetPassUrl}")
    private String resetPassUrl;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendEmailVerification(User user, String token) throws MessagingException{
        log.info("Start sendEmailVerification method");
        String subject = "E-mail verification";
        String url = confirmationUrl + "?token=" + token;
        String emailBody = "Hello " + user.getUsername() + ",\n\n"
                + "Thank you for registering on SocialHub. Click on the link below to verify your email address:\n\n"
                + url + "\n\n"
                + "If you have not requested this email, please ignore it.\n\n"
                + "Regards,\n"
                + "The SocialHub team";

        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom(emailSH);
        email.setTo(user.getEmail());
        email.setSubject(subject);
        email.setText(emailBody);

        log.info("Verification email is sent");
        javaMailSender.send(email);
        log.info("End sendEmailVerification method");
    }

    @Override
    public void sendPassResetEmail(User user, String token) throws MessagingException{
        log.info("Start sendPassResetEmail method");
        String subject = "Password Reset email";
        String url = resetPassUrl + "?token=" + token;
        String emailBody = "Hello " + user.getUsername() + ",\n\n"
                + "We received a request to reset your account password. Click on the link below to set a new password:\n\n"
                + url + "\n\n"
                + "If you did not request a password reset, please ignore this email and your password will remain the same.\n\n"
                + "Regards,\n"
                + "The SocialHub team";

        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom(emailSH);
        email.setTo(user.getEmail());
        email.setSubject(subject);
        email.setText(emailBody);

        log.info("Password reset email is sent");
        javaMailSender.send(email);
        log.info("End sendPassResetEmail method");
    }

}
