package com.arvato.cc.dao1.impl;

import com.arvato.cc.dao1.JdbcTemplateExtend;
import com.arvato.cc.dao1.RoleDao;
import com.arvato.cc.model.Role;
import com.arvato.jdf.dao.HibernateDao;
import com.arvato.jdf.dao.Page;
import com.arvato.jdf.dao.PropertyFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: pang008
 * Date: 10/10/12
 * Time: 21:56
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class RoleDaoImpl extends HibernateDao<Role, Integer> implements RoleDao {

    @Autowired
    private JdbcTemplateExtend jdbcTemplateExtend;

    public List<Role> findEnabledAll() {
        return find("from Role where roleStatus = 1");  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Page<Role> findPropertyFilter(Page page, PropertyFilter propertyFilter) {
        return super.findByPropertyFilter(page, propertyFilter);  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Page<Role> find(Page page) {
        return this.find(page, "from Role");  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Object[] getOmsRoleResourceSysIds(String id) {
        Object[] omsRoleResourceSysIds =this.find("select resource.resourceSysId from RoleResource where omsRole.omsRoleSysId = '" + id.trim()+"'").toArray();
        return omsRoleResourceSysIds;
    }

     public Role get(int id)
     {
        return super.get(id);
     }

    public List<Role> findByRoleName(String[] params) {
		List<Role> roleId = super.find(
                "from Role where omsRoleName=?", params);
		return roleId;
    }

    public List<Role> findByRoleId(Integer[] params) {
        List<Role> roles = super.find(
				"from Role where roleId=?", params);
		return roles;
    }

    public Object[] getRoleSysIds(Integer id) {
        Object[] roleSysIds = super.find("select role.roleSysId from UserRole where user.userSysId = " + id +" and role.roleId != 'ROLE_ANONYMOUS'").toArray();
		return roleSysIds;
    }

    public Object[] getRoleNameBySysIds(Integer id) {
        Object[] roleNames = super.find("select role.roleName from UserRole where user.userSysId = " + id +" and role.roleId !='ROLE_ANONYMOUS'").toArray();
        return roleNames;
    }

    public List<Role> findRoleByName(String roleName){
        List<Role> roleList = super.find(
                "from Role where roleName=?", roleName);
        return roleList;
    }



    public  List<Role> getByRoleId(String roleId){
        List<Role> roleList = super.find(
                "from Role where roleId=?", roleId);
        return roleList;
    }
}
