package com.arvato.cc.dao1.impl;

import com.arvato.cc.dao1.EmailDao;
import com.arvato.cc.model.Email;
import com.arvato.cc.util.Validate;
import com.arvato.jdf.dao.HibernateDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by XUSO002 on 2015/9/1 0001.
 */
@Repository
public class EmailDaoImpl extends HibernateDao<Email, Integer> implements EmailDao{

    public void saveOrUpdate(Email email) {
        getSession().saveOrUpdate(email);
    }

    public List<Email> findEmailByEmail(String email) {
        List<Email> emails=super.find("from Email where email=?",email);
        return emails ;
    }

    public Email findEmailByToken(String token) {
        List<Email> emails=super.find("from Email where token=?",token);
        if (Validate.isNullOrEmpty(emails)){
            return null;
        }  else{
            return emails.get(0);
        }
    }
}
