package com.arvato.cc.dao1;

import com.arvato.cc.model.RoleResource;

import java.util.List;
import java.util.Set;

public interface RoleResourceDao {

    public void saveRoleResource(RoleResource omsRoleResource);

    Object[] getOmsRoleResourceSysIds(String id);

    List<RoleResource> findByRoleSysId(Integer id);


    void deleteResource(Integer omsRoleResourceSysId);

    void remove(Set<RoleResource> roleResourceSet);

    void remove(Integer[] sysIds);

    void removeByRoleAndResource(String roleSysId, String[] resourceIds);

    Boolean deleteByRole(Integer roleId);

    Boolean deleteByResource(Integer resourceId);
}

