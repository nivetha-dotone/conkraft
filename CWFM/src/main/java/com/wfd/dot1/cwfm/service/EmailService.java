package com.wfd.dot1.cwfm.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Set;

@Service
public class EmailService {


//    private final JavaMailSender mailSender;
//
//    public EmailService(JavaMailSender mailSender) {
//        this.mailSender = mailSender;
//    }

    @Autowired
    private  JavaMailSender mailSender;
    public void sendHtmlMail(Set<String> to, String subject, String htmlContent) {

        try {

            MimeMessage message = mailSender.createMimeMessage();

            MimeMessageHelper helper =
                    new MimeMessageHelper(message, true);

            helper.setTo(to.toArray(new String[0]));
            helper.setSubject(subject);
            helper.setText(htmlContent, true);
            helper.setFrom("dot1track-noreply@dot1.in");
            mailSender.send(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
