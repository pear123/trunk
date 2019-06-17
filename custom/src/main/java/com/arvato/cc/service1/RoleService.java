package com.arvato.cc.service1;

import com.arvato.cc.model.Role;
import com.arvato.cc.util.ExtTreeNode;
import com.arvato.cc.util.ExtTreeNodeSingle;
import com.arvato.jdf.dao.Page;
import com.arvato.jdf.dao.PropertyFilter;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: pang008
 * Date: 10/10/12
 * Time: 14:34
 * To change this template use File | Settings | File Templates.
 */
public interface RoleService {
    List<ExtTreeNode> buildRoleTree();

    List<ExtTreeNodeSingle> buildRoleTreeSingle();

    Role saveOrUpdate(Role role);

    Role save(Role role);

    Page<Role> findUserRolePropertyFilter(Page page, PropertyFilter propertyFilter);

    Page<Role> findUserRole(Page page);

    Object[] getOmsRoleResourceById(String id);

    Role selectRoleByIdToJson(Integer sysId);

    String saveOmsRole(Role ccRole, String[] orResource, String userName);

    List<Role> getAll();

    Object[] getUserRoleByUserId(Integer id);

    Object[] getRoleNameByUserId(Integer id);

    void saveRoleResource(String resourceId, Role role);

    String checkRoleIsReferenceByUser(Integer sysId);

    Boolean remove(Integer sysId);


    Role findByRoleId(Integer sysId);

    List<Role>  validateRoleName(String roleName);
}
