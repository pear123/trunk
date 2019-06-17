package com.arvato.cc.dao1.impl;

import com.arvato.cc.dao1.RoleResourceDao;
import com.arvato.cc.model.RoleResource;
import com.arvato.jdf.dao.HibernateDao;
import org.hibernate.SQLQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Repository
public class RoleResourceDaoHibernate extends HibernateDao<RoleResource, Integer> implements RoleResourceDao {

    private final static Logger logger = LoggerFactory.getLogger(RoleResourceDao.class);

    public void saveRoleResource(RoleResource roleResource) {
        super.save(roleResource);
    }

    public Object[] getOmsRoleResourceSysIds(String id) {
        Object[] roleResourceSysIds = super.find("select resource.resourceSysId from RoleResource where role.roleSysId = '" + id.trim() + "'").toArray();
        return roleResourceSysIds;
    }

    @SuppressWarnings("unchecked")
    public List<RoleResource> findByRoleSysId(Integer id) {
        List<RoleResource> owResource = new ArrayList<RoleResource>();
        String HQL = "from RoleResource where role.roleSysId='" + id + "'";
        owResource = super.find(HQL, null);
        return owResource;
    }

    public void deleteResource(Integer roleResourceSysId) {
        super.delete(roleResourceSysId);
        this.getSession().flush();
    }

    public void remove(Set<RoleResource> roleResourceSet) {
        if (null != roleResourceSet && roleResourceSet.size() > 0) {
            for (RoleResource roleResource : roleResourceSet) {
                super.delete(roleResource);
            }
        }
    }

    public void remove(Integer[] sysIds) {
        if (null != sysIds && sysIds.length > 0) {
            for (Integer sysId : sysIds) {
                super.delete(get(sysId));
            }
        }
    }

    public void removeByRoleAndResource(String roleSysId, String[] resourceIds) {
        SQLQuery sqlQuery = this.getSession().createSQLQuery("delete from role_resource where ROLE_SYS_ID=? and RESOURCE_SYS_ID=?");
        for (String resourceId : resourceIds) {
            sqlQuery.setParameter(0, roleSysId);
            sqlQuery.setParameter(1, resourceId);
            logger.debug("remove ROLE_SYS_ID[" + roleSysId + "] and RESOURCE_SYS_ID" + resourceId + "] with result[" + sqlQuery.executeUpdate() + "]");
        }
    }

    public Boolean deleteByRole(Integer roleId) {
        SQLQuery sqlQuery = this.getSession().createSQLQuery("delete from role_resource where ROLE_SYS_ID=?");
        sqlQuery.setParameter(0, roleId);
        return sqlQuery.executeUpdate() == 1 ? true : false;
    }

    public Boolean deleteByResource(Integer resourceId) {
        SQLQuery sqlQuery = this.getSession().createSQLQuery("delete from role_resource  where RESOURCE_SYS_ID=?");
        sqlQuery.setParameter(0, resourceId);
        return sqlQuery.executeUpdate() == 1 ? true : false;
    }
}
