package com.arvato.cc.service1.impl;

import com.arvato.cc.dao1.ResourceDao;
import com.arvato.cc.dao1.RoleDao;
import com.arvato.cc.dao1.UserDao;
import com.arvato.cc.dao1.UserRoleDao;
import com.arvato.cc.model.*;
import com.arvato.cc.service1.UserService;
import com.arvato.cc.util.CommonHelper;
import com.arvato.cc.util.Validate;
import com.arvato.jdf.dao.Page;
import com.arvato.jdf.dao.PropertyFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: pang008
 * Date: 10/10/12
 * Time: 14:35
 * To change this template use File | Settings | File Templates.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private ResourceDao resourceDao;
    @Autowired
    private UserRoleDao userRoleDao;
//    @Autowired
//    private OmsCredentialService credentialService;

    public User save(User user) {
        return userDao.save(user);  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Page<User> findPropertyFilter(Page page, PropertyFilter propertyFilter) {
        return userDao.findPropertyFilter(page, propertyFilter);  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Page<User> find(Page page) {
        return userDao.find(page);  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Resource save(Resource resource) {
        return resourceDao.save(resource);  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Page<Resource> findResourcePropertyFilter(Page page, PropertyFilter propertyFilter) {
        return resourceDao.findPropertyFilter(page, propertyFilter);  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Page<Resource> findResource(Page page) {
        return resourceDao.find(page);  //To change body of implemented methods use File | Settings | File Templates.
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

    /*
     * 添加资源
	 */
    public String saveOmsResource(Resource ccResource, String userName) {
        String result = "success";
        //如果Id不为空，则为修改
        List<Resource> ors_name = resourceDao.getOmsResourceByResourceName(ccResource.getResourceName().trim());
//		List<OmsResource> ors_pattern = resourceDao.getOmsResourceByResourcePattern(omsResource.getOmsResourcePattern().trim());
        List<Resource> ors_patternAndSystem = resourceDao.getOmsResourceByResourceSystemAndPattern(ccResource.getResourcePattern(), null);
        if (!Validate.isNullOrEmpty(ccResource.getResourceSysId())) {
            //根据资源名和Id一起查找能找到，说明是当前对象本身（没有对resourceName进行修改）
            if (ors_name.size() > 0) {//数据库中有相同的名字
                if (ors_name.get(0).getResourceSysId() != ccResource.getResourceSysId()) {//如果名字相同但是Id却不相同说明不知当前修改的对象，报名字重复错误
                    return "resourceNameError";
                } else { //名字没有修改的情况
                    updateOmsResource(ccResource, userName);
                }
            }
            if (ors_patternAndSystem.size() > 0) { //没有重复的名字提交
                if (ors_patternAndSystem.get(0).getResourceSysId() != ccResource.getResourceSysId()) {//如果名字相同但是Id却不相同说明不知当前修改的对象，报名字重复错误
                    return "resourcePatternAndSystemError";
                } else { //名字没有修改的情况
                    updateOmsResource(ccResource, userName);
                }
            }
            updateOmsResource(ccResource, userName);
        } else {//否则为添加
            //根据资源名查找时候有重复
            if (ors_name.size() > 0)
                return "resourceNameError";
            if (ors_patternAndSystem.size() > 0)
                return "resourcePatternAndSystemError";

            ccResource.setCreateTime(CommonHelper.getThisTimestamp());
            ccResource.setUpdateTime(CommonHelper.getThisTimestamp());
            ccResource.setUpdateOp(userName);
            ccResource.setCreateOp(userName);
            ccResource.setVersion(0);
            this.resourceDao.saveOmsResource(ccResource); // 不重复就添加
        }
        return result;
    }

    /*
      * 执行修改操作
      */
    private void updateOmsResource(Resource ccResource, String userName) {
        Resource cc = this.resourceDao.get(ccResource.getResourceSysId());  //根据id得到数据库中的原始数据
        cc.setUpdateOp(userName);
        cc.setResourceName(ccResource.getResourceName());  //给对象的属性进行赋值
        cc.setResourceDescription(ccResource.getResourceDescription());
        cc.setResourcePattern(ccResource.getResourcePattern());
        cc.setResourceStatus(ccResource.getResourceStatus());
        cc.setUpdateTime(CommonHelper.getThisTimestamp());
        this.resourceDao.save(cc);   //执行保存
    }


    public Resource selectResourceByIdToJson(Integer id) {
        Resource or = this.resourceDao.get(id);
        Resource r = new Resource();
        r.setResourceDescription(or.getResourceDescription());
        r.setResourceName(or.getResourceName());
        r.setResourcePattern(or.getResourcePattern());
        r.setResourceStatus(or.getResourceStatus());
        r.setResourceSysId(or.getResourceSysId());
        return r;
    }

    public CCUser getUser(Integer id) {
        User user = userDao.get(id);
        CCUser ccuser = new CCUser();
        ccuser.setUserEmail(user.getUserEmail());
        ccuser.setUserId(user.getUserId());
        ccuser.setUserName(user.getUserName());
        ccuser.setUserPassword(null);
        ccuser.setUserRemark(user.getUserRemark());
        ccuser.setUserStatus(user.getUserStatus());
        ccuser.setUserSysId(user.getUserSysId().toString());
        return ccuser;
    }

    public User get(Integer userSysId) {
        return userDao.get(userSysId);
    }

    public String saveUser(User user, String userName, String userEmail, Integer[] roles, HttpServletRequest request, String modifyValue) {
//        List<User> ousId = findByCriteria(
//				"from OmsUser where omsUserId = ?", new String[] {user.getUserId().trim() });
//		List<OmsUser> ousEmail = findByCriteria(
//				"from OmsUser where omsUserEmail = ?", new String[] { omsUser
//						.getOmsUserEmail().trim() });
        User ousId = null;
        User ousEmail = userDao.findUserByUserEmail(userEmail);
        String result = "";
        // 用户修改
        if (!Validate.isNullOrEmpty(user.getUserSysId())) {
            boolean existBug = false;
            if (!Validate.isNullOrEmpty(ousEmail)) {
                if (!ousEmail.getUserSysId().equals(user.getUserSysId())) {
                    result = "UserEmailError";
                    existBug = true;
                }
            }
            if (!existBug) {
                // 得到数据库中关联信息的主键
                Object[] rs = userRoleDao.getOmsRoleSysIdsDoDelete(user.getUserId());
                for (Object r : rs) {
                    userRoleDao.deleteUserRole(new Integer(r.toString()));
                }
                // 执行保存操作
                user.setUserEmail(userEmail);
                doSave(user, userName, roles, "modify", modifyValue);
                result = "success";
            }
        } else {// 用户保存
            ousId = userDao.findUserByUserId(user.getUserId());
            if (!Validate.isNullOrEmpty(ousId)) {
                result = "UserIdError";
            } else if (!Validate.isNullOrEmpty(ousEmail)) {
                result = "UserEmailError";
            } else {
                doSave(user, userName, roles, "create", modifyValue);
                result = "success";
            }
        }
        return result;
    }

    /*
     * 保存主表的方法
	 */
    private void doSave(User user, String userName, Integer[] roles, String operation, String modifyValue) {

        // 保存主表
        if ("create".equals(operation)) {
            user.setCreateOp(userName);
            user.setUpdateOp(userName);
            user.setCreateTime(CommonHelper.getThisTimestamp());
            user.setUpdateTime(CommonHelper.getThisTimestamp());
            user.setVersion(0);
            user = this.userDao.save(user);
            if (roles != null && roles.length != 0) {
                saveRoleAndStore(roles, userName, user);
            } else {
                UserRole our = new UserRole();
                List<Role> roleList = this.roleDao.getByRoleId("ROLE_ANONYMOUS");
                our.setRole(roleList.get(0));
                our.setCreateOp(userName);
                our.setCreateTime(CommonHelper.getThisTimestamp());
                our.setUser(user);
                our.setVersion(0);
                userRoleDao.saveUserRole(our);
            }
        } else if ("modify".equals(operation)) {
            User ou = this.userDao.get(user.getUserSysId());
            ou.setUserEmail(user.getUserEmail());
//          ou.setUserId(user.getUserId());
            ou.setUserName(user.getUserName());
            if ("on".equals(modifyValue)) {
                ou.setUserPassword(user.getUserPassword());
//                credentialService.markLoginExceptions(ou.getUserId(), "0");
            }
            ou.setUserRemark(user.getUserRemark());
            ou.setUserStatus(user.getUserStatus());
            ou.setUpdateOp(userName);
            ou.setUpdateTime(CommonHelper.getThisTimestamp());
            ou.setVersion(0);
            ou = this.userDao.save(ou);
            saveRoleAndStore(roles, userName, ou);
        }
    }

    /*
      * 添加关联表数据
      */
    private void saveRoleAndStore(Integer[] roles, String userName, User user) {
        // 保存role关联表
        if (roles != null && roles.length != 0) {
            for (Integer roleId : roles) {
                UserRole our = new UserRole();
                if (null != roleId) {
                    if (roleId > 0) {
                        Role or = this.roleDao.get(roleId);
                        our.setCreateOp(userName);
                        our.setCreateTime(CommonHelper.getThisTimestamp());
                        our.setRole(or);
                        our.setUser(user);
                        our.setVersion(0);
                        userRoleDao.saveUserRole(our);
                    }
                } else {
                    List<Role> roleList = this.roleDao.getByRoleId("ROLE_ANONYMOUS");
                    our.setRole(roleList.get(0));
                    our.setCreateOp(userName);
                    our.setCreateTime(CommonHelper.getThisTimestamp());
                    our.setUser(user);
                    our.setVersion(0);
                    userRoleDao.saveUserRole(our);
                }
            }
        }
    }


    public User findOmsUserByOmsUserId(String omsUserId) {
        return userDao.findUserByUserId(omsUserId);
    }

    public User findUserByUserEmail(String userEmail) {
        return userDao.findUserByUserEmail(userEmail);
    }

    @Override
    public Boolean remove(String sysId) {
        String[] ids = sysId.split(";");
        for (int i = 0; i < ids.length; i++) {
            if (!Validate.isNullOrEmpty(ids[i])) {
                userDao.delete(Integer.parseInt(ids[i]));
            }
        }
        return true;
    }
}
