package com.arvato.cc.dao1.impl;

import com.arvato.cc.dao1.UserRoleDao;
import com.arvato.cc.model.UserRole;
import com.arvato.jdf.dao.HibernateDao;
import com.arvato.jdf.dao.Page;
import com.arvato.jdf.dao.PropertyFilter;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 * User: pang008
 * Date: 10/10/12
 * Time: 17:23
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class UserRoleDaoImpl extends HibernateDao<UserRole, Integer> implements UserRoleDao {
    public UserRole saveUserRole(UserRole role) {
        return super.save(role);
    }

    public Page<UserRole> findPropertyFilter(Page page, PropertyFilter propertyFilter) {
        return super.findByPropertyFilter(page, propertyFilter);  //To change body of implemented methods use File | Settings | File Templates.
    }


    public void deleteUserRole(Integer s) {
        super.delete(s);
        this.getSession().flush();
    }

    public Object[] getOmsRoleSysIdsDoDelete(String  userId) {
        Object[] roleSysIds = super.find("select userRoleSysId from UserRole where user.userId = '" + userId.trim() + "'").toArray();
        return roleSysIds;
    }

    public Boolean deleteByRole(Integer roleId) {
        SQLQuery sqlQuery = this.getSession().createSQLQuery("delete from user_role where role_sys_id=?");
        sqlQuery.setParameter(0, roleId);
        return sqlQuery.executeUpdate() == 1 ? true : false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
