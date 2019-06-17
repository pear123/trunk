package com.arvato.cc.dao1;

import com.arvato.cc.model.Role;
import com.arvato.jdf.dao.Page;
import com.arvato.jdf.dao.PropertyFilter;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: pang008
 * Date: 10/10/12
 * Time: 21:55
 * To change this template use File | Settings | File Templates.
 */
public interface RoleDao {
    List<Role> findEnabledAll();

    Role save(Role role);

    Page<Role> findPropertyFilter(Page page, PropertyFilter propertyFilter);

    Page<Role> find(Page page);

    public Object[] getOmsRoleResourceSysIds(String id);

//    public OmsRole get(String resourceSysId);

    Role get(Integer id);

    List<Role> findByRoleName(String[] strings);

    List<Role> findByRoleId(Integer[] strings);

    List<Role> getAll();

    Object[] getRoleSysIds(Integer id);

    Object[] getRoleNameBySysIds(Integer id);

    void delete(Integer id);

    List<Role> findRoleByName(String roleName);


    List<Role> getByRoleId(String roleId);
}
