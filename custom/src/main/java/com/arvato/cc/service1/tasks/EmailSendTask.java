package com.arvato.cc.service1.tasks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component("emailSendTask")
@Scope("prototype")
public class EmailSendTask implements Runnable {

    private final static Logger logger = LoggerFactory.getLogger(EmailSendTask.class);

    @Resource(name = "mailSender")
    private JavaMailSender mailSender;

    @Resource(name = "templateMessage")
    private SimpleMailMessage templateMessage;

    private String[] tos;

    private String subject;

    private String content;

    public EmailSendTask(String subject, String content, String[] tos) {
        this.subject = subject;
        this.content = content;
        this.tos = tos;
    }

    public void run() {
        try {
            logger.debug("Send email[subject:" + subject + ",content:" + content + "] from [" + templateMessage.getFrom() + "] to [" + tos + "]");
            templateMessage.setSubject(this.subject);
            templateMessage.setText(this.content);
            templateMessage.setTo(tos);
//            mailSender.send(templateMessage);
        } catch (MailException ex) {
            logger.debug("Send email[subject:" + subject + ",content:" + content + "] from [" + templateMessage.getFrom() + "] to [" + tos + "] failure. The error is[" + ex.getMessage() + "]");
            ex.printStackTrace();
        }
    }
}
