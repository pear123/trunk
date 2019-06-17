package com.arvato.cc.dao1;

import com.arvato.cc.model.Email;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by XUSO002 on 2015/9/1 0001.
 */

public interface EmailDao {

    void saveOrUpdate(Email email);

    List<Email> findEmailByEmail(String email);

    Email findEmailByToken(String token);
}
