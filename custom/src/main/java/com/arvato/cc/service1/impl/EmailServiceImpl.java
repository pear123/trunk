package com.arvato.cc.service1.impl;

import com.arvato.cc.dao1.EmailDao;
import com.arvato.cc.model.Email;
import com.arvato.cc.service1.EmailService;
import com.arvato.cc.util.CommonHelper;
import com.arvato.cc.util.PasswordUtils;
import com.arvato.cc.util.WatsonsContextHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;;

/**
 * Created with IntelliJ IDEA.
 * User: pang008
 * Date: 07/11/12
 * Time: 12:14
 * To change this template use File | Settings | File Templates.
 */
@Service
public class EmailServiceImpl implements EmailService {
    private static final Logger log = LoggerFactory.getLogger(EmailServiceImpl.class);
    @Value("${mail.from}")
    private String from;  //邮箱服务器

    @Value("${mail.password}")
    private String password;  //邮箱密码

    @Value("${mail.uil}")
    private String uil;  //邮箱密码
    @Autowired
    private EmailDao emailDao;
    @Resource(name = "orderProducerExecutor")
    private ThreadPoolTaskExecutor orderProducerExecutor;

    public void send(String subject, String content, String[] tos) {
        orderProducerExecutor.execute(WatsonsContextHelper.<Runnable>getBean("emailSendTask", subject, content, tos));
    }

    public List<Email> findEmailByEmail(String email) {
        return emailDao.findEmailByEmail(email);
    }

    public Email findEmailByToken(String token) {
        return emailDao.findEmailByToken(token);
    }

    public void saveOrUpdate(Email email) {
          emailDao.saveOrUpdate(email);
    }

    public void sendResetPasswordEmail(String email) {
        log.debug("send email start");
        Session session = getSession();
        MimeMessage message = new MimeMessage(session);
        try {
            message.setSubject("您正在找回密码,请先激活验证链接");
            message.setSentDate(new Date());
            message.setFrom(new InternetAddress(from));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
            String token= generateResetPwdLink(email);
                    message.setContent(email+"你好！<br/>您正在进行BSH密码找回，点击以下链接完成验证，重新设置密码。:<br/><a href='" +String.format(uil,email,token) +"'>"+String.format(uil,email, token)+"</a>" +
                    "<br/>（该链接在2小时内有效，2小时后需要重新获取验证邮件）<br/>如果该链接无法点击，请将其复制粘贴到你的浏览器地址栏中访问<br/>如果这不是您的邮件，请忽略此邮件。<br/>这是BSH系统邮件，请勿回复。<br/>","text/html;charset=utf-8");
            // 发送邮件
            Transport.send(message);
            Email emails=new Email();
            emails.setEmail(email);
            emails.setToken(token);
            emails.setTime(CommonHelper.getThisTimestamp());
            emails.setStatus(0);
            emailDao.saveOrUpdate(emails);
            log.debug("send email end");
        } catch (Exception e) {
            e.printStackTrace();
            log.debug("send email Execetion:"+e.getMessage());
        }
    }

    /**
     * 读取配置文件
     * @return
     */
    private Properties readProperties() {
        String fileName = "config/sendEmail.properties";
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(fileName);

        Properties p = new Properties();
        try {
            p.load(inputStream);
        } catch (IOException e1) {
            log.debug("readProperties IOException:" + e1.getMessage());
        }
        return p;
    }

    public  Session getSession() {
         Properties props=readProperties();
         Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });
        return session;
    }

    public static String generateResetPwdLink(String email) {
        Random random=new Random();
        int num=random.nextInt(1)*100;
        Timestamp time=CommonHelper.getThisTimestamp();
        String token=email+num+time;
        return PasswordUtils.encrypt(token);
    }

}
