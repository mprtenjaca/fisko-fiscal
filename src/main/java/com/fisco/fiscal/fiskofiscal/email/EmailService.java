package com.fisco.fiscal.fiskofiscal.email;

import com.fisco.fiscal.fiskofiscal.security.config.MyMailSender;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@AllArgsConstructor
public class EmailService implements EmailSender{

    private final static Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void send(String to, String email) {

            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("markoprtenjaca4@gmail.com");
            message.setTo(to);
            message.setText("Click this link to activate your account");
            message.setSubject("Fisco Fiscal Confirm your email");

            javaMailSender.send(message);

            LOGGER.info("Mail sent successfully");
//            MimeMessage mimeMessage = mailSender.javaMailSender().createMimeMessage();
//            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
//            helper.setText(email, true);
//            helper.setTo(to);
//            helper.setSubject("Confirm your email");
//            helper.setFrom("Fisco Fiscal");

    }

//    @Override
//    @Async
//    public void send(String to, String email) {
//        try {
//            MimeMessage mimeMessage = mailSender.javaMailSender().createMimeMessage();
//            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
//            helper.setText(email, true);
//            helper.setTo(to);
//            helper.setSubject("Confirm your email");
//            helper.setFrom("Fisco Fiscal");
//        }catch (MessagingException e){
//            LOGGER.error("Failed to send email", e);
//            throw new IllegalStateException("Failed to send email!");
//        }
//    }
}
