package com.arvato.cc.service1;

import com.arvato.cc.model.CCUser;
import com.arvato.cc.model.Resource;
import com.arvato.cc.model.Role;
import com.arvato.cc.model.User;
import com.arvato.jdf.dao.Page;
import com.arvato.jdf.dao.PropertyFilter;

import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 * User: pang008
 * Date: 10/10/12
 * Time: 14:34
 * To change this template use File | Settings | File Templates.
 */
public interface UserService {
    User save(User omsUser);

    Page<User> findPropertyFilter(Page page, PropertyFilter propertyFilter);

    Page<User> find(Page page);

    Resource save(Resource resource);

    Page<Resource> findResourcePropertyFilter(Page page, PropertyFilter propertyFilter);

    Page<Resource> findResource(Page page);

    Role save(Role role);

    Page<Role> findUserRolePropertyFilter(Page page, PropertyFilter propertyFilter);

    Page<Role> findUserRole(Page page);

    public String saveOmsResource(Resource omsResource,String userName);

    public Resource selectResourceByIdToJson(Integer id);

    CCUser getUser(Integer id);

    User get(Integer userSysId);

    String saveUser(User user, String createOp,String userEmail, Integer[] str_role,  HttpServletRequest request, String modifyValue);

    User findOmsUserByOmsUserId(String omsUserId);

    User findUserByUserEmail(String userEmail);

    Boolean remove(String sysId);
}
