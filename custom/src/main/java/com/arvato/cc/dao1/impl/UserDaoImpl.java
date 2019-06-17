package com.arvato.cc.dao1.impl;

import com.arvato.cc.dao1.UserDao;
import com.arvato.cc.model.User;
import com.arvato.jdf.dao.HibernateDao;
import com.arvato.jdf.dao.Page;
import com.arvato.jdf.dao.PropertyFilter;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 * User: pang008
 * Date: 10/10/12
 * Time: 14:32
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class UserDaoImpl extends HibernateDao<User, Integer> implements UserDao {

    public Page<User> findPropertyFilter(Page page, PropertyFilter propertyFilter) {
        return super.findByPropertyFilter(page, propertyFilter);
    }

    public Page<User> find(Page page) {
        return find(page, "from User");  //To change body of implemented methods use File | Settings | File Templates.
    }

    public User findUserByUserId(String userId){
        String hql=" from User where userId = ? " ;
        User ccUser = (User)super.findUnique(hql,userId);
        return ccUser;
    }

    public User findUserByUserEmail(String userEmail) {
        String hql="from User where userEmail=?";
        User ccUser = (User)super.findUnique(hql,userEmail);
        return ccUser;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
