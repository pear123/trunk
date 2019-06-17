package com.arvato.cc.dao1;

import com.arvato.cc.model.UserRole;
import com.arvato.jdf.dao.Page;
import com.arvato.jdf.dao.PropertyFilter;


public interface UserRoleDao {
    UserRole saveUserRole(UserRole role);

    Page<UserRole> findPropertyFilter(Page page, PropertyFilter propertyFilter);

    void deleteUserRole(Integer s);

    Object[] getOmsRoleSysIdsDoDelete(String UserId);

    Boolean deleteByRole(Integer roleId);
}
