package com.arvato.cc.dao1;

import com.arvato.cc.model.User;
import com.arvato.jdf.dao.Page;
import com.arvato.jdf.dao.PropertyFilter;


public interface UserDao {
    public User save(User omsUser);

    Page<User> findPropertyFilter(Page page, PropertyFilter propertyFilter);

    Page<User> find(Page page);

    User get(Integer id);

    User findUserByUserId(String omsUserId);

    User findUserByUserEmail(String userEmail);

    void delete(Integer id);
}
