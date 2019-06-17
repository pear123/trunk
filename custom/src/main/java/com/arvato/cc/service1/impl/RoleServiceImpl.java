package com.arvato.cc.service1.impl;

import com.arvato.cc.dao1.ResourceDao;
import com.arvato.cc.dao1.RoleDao;
import com.arvato.cc.dao1.RoleResourceDao;
import com.arvato.cc.dao1.UserRoleDao;
import com.arvato.cc.model.Resource;
import com.arvato.cc.model.Role;
import com.arvato.cc.model.RoleResource;
import com.arvato.cc.model.UserRole;
import com.arvato.cc.service1.RoleService;
import com.arvato.cc.util.*;
import com.arvato.jdf.dao.Page;
import com.arvato.jdf.dao.PropertyFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tuckey.web.filters.urlrewrite.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: pang008
 * Date: 10/10/12
 * Time: 14:35
 * To change this template use File | Settings | File Templates.
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private RoleResourceDao roleResourceDao;
    @Autowired
    private ResourceDao resourceDao;

    @Autowired
    private UserRoleDao userRoleDao;

    public List<ExtTreeNode> buildRoleTree() {
        List<Role> roleList = roleDao.findEnabledAll();
        List<ExtTreeNode> convertList = null;
        if (null != roleList && roleList.size() > 0) {
            int size = roleList.size();
            convertList = new ArrayList<ExtTreeNode>(size);

            for (Role role : roleList) {
                if ("ROLE_ANONYMOUS".equals(role.getRoleId())) {
                    continue;
                }

                convertList.add(role.convertTreeNode());
            }
        }
        return convertList;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<ExtTreeNodeSingle> buildRoleTreeSingle() {
        List<Role> roleList = roleDao.findEnabledAll();
        List<ExtTreeNodeSingle> convertList = null;
        if (null != roleList && roleList.size() > 0) {
            int size = roleList.size();
            convertList = new ArrayList<ExtTreeNodeSingle>(size);

            for (Role role : roleList) {
                if ("ROLE_ANONYMOUS".equals(role.getRoleId())) {
                    continue;
                }

                convertList.add(role.convertTreeNodeSingle());
            }
        }
        return convertList;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Role saveOrUpdate(Role role) {
        int sysId = 0;
        if(role.getRoleSysId() != null){
            sysId = role.getRoleSysId();
        }
        Role ccRole = null;

//        String resources = role.getField1();
//        role.setField1("");
        if (sysId>0) {
            ccRole = roleDao.get(sysId);
            if (null != ccRole) {
                ccRole.setRoleId(role.getRoleId());
                ccRole.setRoleDescription(role.getRoleDescription());
                ccRole.setRoleName(role.getRoleName());
                ccRole.setUpdateTime(CommonHelper.getThisTimestamp());
                ccRole.setUpdateOp(UserSecurityUtil.getCurrentUsername());
            } else {
                ccRole = role;
                ccRole.setCreateTime(CommonHelper.getThisTimestamp());
                ccRole.setCreateOp(UserSecurityUtil.getCurrentUsername());
            }
        } else {
            ccRole = role;
            ccRole.setCreateTime(CommonHelper.getThisTimestamp());
            ccRole.setCreateOp(UserSecurityUtil.getCurrentUsername());
        }
        Set<RoleResource> originalRoleResource = ccRole.getRoleResources();
        ccRole.setRoleResources(null);
        ccRole = roleDao.save(ccRole);

        roleResourceDao.remove(originalRoleResource);
        return ccRole;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void saveRoleResource(String resourceId, Role omsRole) {
        if (!StringUtils.isBlank(resourceId)) {
            String[] resourceIds = resourceId.split(",");

            RoleResource roleResource = null;
            Resource resource = null;
            for (String rId : resourceIds) {
                resource = resourceDao.get(new Integer(rId));
                if (null == resource) {
                    continue;
                }

                roleResource = new RoleResource();
                roleResource.setRole(omsRole);
                roleResource.setResource(resource);
                roleResource.setVersion(0);
                roleResourceDao.saveRoleResource(roleResource);
            }
        }
    }

    public String checkRoleIsReferenceByUser(Integer sysId) {
        Role role = this.roleDao.get(sysId);
        if (null == role) {
            return "SUCCESS";
        }
        Set<UserRole> userRoleSet = role.getUserRoles();
        if (null == userRoleSet || userRoleSet.size() == 0) {
            return "SUCCESS";
        }

        StringBuffer sb = new StringBuffer(userRoleSet.size());
        int count = 0;
        for (UserRole userRole : userRoleSet) {
            sb.append(userRole.getUser().getUserId());
            count ++;
            if(count != userRoleSet.size()){
                sb.append(",");
            }
        }

        return sb.toString();  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Boolean remove(Integer sysId) {
        roleResourceDao.deleteByRole(sysId);
        userRoleDao.deleteByRole(sysId);
        roleDao.delete(sysId);
        return true;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Role save(Role role) {
        return roleDao.save(role);  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Page<Role> findUserRolePropertyFilter(Page page, PropertyFilter propertyFilter) {
        return roleDao.findPropertyFilter(page, propertyFilter);  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Page<Role> findUserRole(Page page) {
        return roleDao.find(page);  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * 资源信息列表
     * kobs
     */
    public List<Resource> selectResourceToJson(List<Resource> omsResourceList) {
        List<Resource> resourceFormList = new ArrayList<Resource>();
        for (Resource or : omsResourceList) {
            Resource r = new Resource();
            r.setResourceDescription(or.getResourceDescription());
            r.setResourceName(or.getResourceName());
            r.setResourcePattern(or.getResourcePattern());
//			r.setOmsResourceStatus(or.getOmsResourceStatus());
            r.setResourceSysId(or.getResourceSysId());
            if (or.getResourceStatus().equals("0")) {
                r.setResourceStatus("禁用");
            } else {
                r.setResourceStatus("启用");
            }
            resourceFormList.add(r);
        }
        return resourceFormList;
    }

    public Object[] getOmsRoleResourceById(String id) {
        return roleDao.getOmsRoleResourceSysIds(id);
    }

    public Role selectRoleByIdToJson(Integer id) {
        Role or = roleDao.get(id);
        Role r = new Role();
//		r.setEcOrganization(or.getEcOrganization());
        r.setRoleDescription(or.getRoleDescription());
        r.setRoleId(or.getRoleId());
        r.setRoleName(or.getRoleName());
        r.setRoleStatus(or.getRoleStatus());
        r.setRoleSysId(or.getRoleSysId());
        return r;
    }

   public Role findByRoleId(Integer sysId){
       return roleDao.get(sysId);
   }

    public String saveOmsRole(Role omsRole, String[] params, String userName) {
        String result = "success";
        if (!Validate.isNullOrEmpty(omsRole.getRoleSysId())) {
            // 修改
            // 判断roleName是否已存在
            List<Role> roleName = roleDao.findByRoleName(new String[]{omsRole.getRoleName()});
            if (!(roleName.size() > 0) || roleName.get(0).getRoleSysId().equals(omsRole.getRoleSysId())) {
//		if (roleName.size() > 0 && roleName.get(0).getOmsRoleSysId().equals(omsRole.getOmsRoleSysId())) {
                Role ors = roleDao.get(omsRole.getRoleSysId());
                ors.setRoleSysId(omsRole.getRoleSysId());
                ors.setRoleId(omsRole.getRoleId());
                ors.setRoleName(omsRole.getRoleName());
                ors.setRoleStatus(omsRole.getRoleStatus());
                ors.setRoleDescription(omsRole.getRoleDescription());
                ors.setCreateOp(userName);
//                ors.setEcOrganization(omsRole.getEcOrganization());
                ors.setUpdateTime(CommonHelper.getThisTimestamp());
                ors.setUpdateOp(omsRole.getRoleName());

                List<RoleResource> owResource = roleResourceDao.findByRoleSysId(ors.getRoleSysId());
                //先删除OmsRoleResource下所有OmsRoleSysId的数据
                for (RoleResource omsRoleResource : owResource) {
                    roleResourceDao.deleteResource(omsRoleResource.getRoleResourceSysId());
                }
                // 更新omsRoleResource
                if (params != null) {
                    for (int i = 0; i < params.length; i++) {
                        Resource or = resourceDao.get(new Integer(params[i].toString()));
                        RoleResource orr = new RoleResource();
                        orr.setRole(ors);
                        orr.setResource(or);
                        roleResourceDao.saveRoleResource(orr);
                    }
                }
            } else {
                return result = "nameError";
            }
        } else {
            // 判断roleName是否已存在
            List<Role> roleName = roleDao.findByRoleName(new String[]{omsRole.getRoleName()});
            List<Role> roleId = roleDao.findByRoleId(new Integer[]{Integer.parseInt(omsRole.getRoleId())});
            if (roleId.size() > 0) {
                return result = "idError";
            } else if (roleName.size() > 0) {
                return result = "nameError";
            } else {
                // 保存
                omsRole.setRoleSysId(omsRole.getRoleSysId());
                omsRole.setRoleId(omsRole.getRoleId());
                omsRole.setRoleName(omsRole.getRoleName());
                omsRole.setRoleDescription(omsRole.getRoleDescription());
                omsRole.setRoleStatus(omsRole.getRoleStatus());
                omsRole.setCreateTime(CommonHelper.getThisTimestamp());
                omsRole.setUpdateTime(CommonHelper.getThisTimestamp());
                omsRole.setCreateOp(userName);
                omsRole.setUpdateOp(userName);
                omsRole = roleDao.save(omsRole);
                if (params != null) {
                    for (int i = 0; i < params.length; i++) {
                        Resource or = resourceDao.get(new Integer(params[i].toString()));
                        RoleResource orr = new RoleResource();
                        orr.setRole(omsRole);
                        orr.setResource(or);
                        roleResourceDao.saveRoleResource(orr);
                    }
                }
            }
        }
        return result;
    }

    public List<Role> getAll() {
        return roleDao.getAll();
    }

    public Object[] getUserRoleByUserId(Integer id) {
        return roleDao.getRoleSysIds(id);
    }


    public Object[] getRoleNameByUserId(Integer id){
        return roleDao.getRoleNameBySysIds(id);
    }

    public List<Role>  validateRoleName (String roleName){
        return roleDao.findRoleByName(roleName);
    }
}
