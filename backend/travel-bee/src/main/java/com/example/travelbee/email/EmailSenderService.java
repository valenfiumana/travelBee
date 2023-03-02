package com.example.travelbee.email;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

@Service
public class EmailSenderService {

    private static final Logger logger = Logger.getLogger(EmailSenderService.class);

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SpringTemplateEngine thymeleafTemplateEngine;

    //@Value("https://travel-bee-images.s3.us-east-2.amazonaws.com/icons/logo.PNG")
    //Resource logo;

    @Async
    public void sendEmail(String to, String subject, String htmlBody) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
            helper.setTo(to);
            helper.setFrom("Travel Bee <travelbee.noreply@gmail.com>");
            helper.setSubject(subject);
            helper.setText(htmlBody, true);
            //URL url = new URL("https://travel-bee-images.s3.us-east-2.amazonaws.com/icons/logo.PNG");
            //Resource res = new InputStreamResource(url.openStream());
            //helper.addInline("logo.png", res);

            mailSender.send(message);
            logger.debug("Email sent to: " + to);

        } catch (MessagingException e) {
            logger.error("The mail couldn't be sent", e);
            throw new IllegalStateException("There was an error sending the email");
        } //catch (IOException e) {
            //throw new RuntimeException("There was an error sending the email");
        //}
    }

    public void sendRegisterTemplate(String to, String subject, Map<String, Object> templateModel) throws MessagingException {
        Context thymeleafContext = new Context();
        thymeleafContext.setVariables(templateModel);
        String htmlBody = thymeleafTemplateEngine.process("registerEmail.html", thymeleafContext);

        sendEmail(to, subject, htmlBody);
    }

    public void sendBookingEmail(
            String to, String subject, Map<String, Object> templateModel)
            throws MessagingException {

        Context thymeleafContext = new Context();
        thymeleafContext.setVariables(templateModel);
        String htmlBody = thymeleafTemplateEngine.process("bookingEmail.html", thymeleafContext);

        sendEmail(to, subject, htmlBody);
    }

}