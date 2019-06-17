package com.arvato.cc.service1;

import com.arvato.cc.model.Email;
import com.arvato.cc.model.User;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: pang008
 * Date: 07/11/12
 * Time: 12:12
 * To change this template use File | Settings | File Templates.
 */
public interface EmailService {
    /**
     * @param subject
     * @param content
     */
    void send(String subject, String content, String[] tos);

    void sendResetPasswordEmail(String email);

    List<Email> findEmailByEmail(String email);

   Email findEmailByToken(String token);

    void saveOrUpdate(Email email);
}
