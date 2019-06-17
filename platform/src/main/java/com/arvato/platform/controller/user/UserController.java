package com.arvato.platform.controller.user;

import com.arvato.cc.constant.Constants;
import com.arvato.cc.form.CRole;
import com.arvato.cc.model.*;
import com.arvato.cc.service1.EmailService;
import com.arvato.cc.service1.ResourceService;
import com.arvato.cc.service1.RoleService;
import com.arvato.cc.service1.UserService;
import com.arvato.cc.util.CommonHelper;
import com.arvato.cc.util.RoleTreeNode;
import com.arvato.cc.util.UserSecurityUtil;
import com.arvato.cc.util.Validate;
import com.arvato.jdf.dao.Page;
import com.arvato.jdf.dao.PropertyFilter;
import com.arvato.jdf.security.resource.ResourceDetailsMonitor;
import com.arvato.jdf.web.json.JsonResult;
import com.arvato.platform.controller.sys.CommonController;
import com.arvato.platform.util.ExtFilter;
import com.arvato.platform.util.ExtHelper;
import com.arvato.platform.util.ExtPageHelper;
import com.octo.captcha.service.image.ImageCaptchaService;
import net.sf.json.JSONArray;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tuckey.web.filters.urlrewrite.utils.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import OmsRecentCredentialService;

/**
 * 用户管理
 */
@Controller
public class UserController extends CommonController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private ResourceService resourceService;

    @Autowired
    private EmailService emailService;
    @javax.annotation.Resource(name = "resourceDetailsMonitor")
    private ResourceDetailsMonitor resourceDetailsMonitor;

    @javax.annotation.Resource(name = "asyncCaptchaService")
    private ImageCaptchaService asyncCaptchaService;


//    @Autowired
//    private OmsRecentCredentialService omsRecentCredentialService;

    /**
     * 用户管理 userList.ftl
     *
     * @return
     */
    @RequestMapping("/user/index")
    public String indexView() {
        return "user/userList";
    }

    /**
     * 用户列表
     *
     * @param request
     * @return
     */
    @RequestMapping("/user/list.json")
    @ResponseBody
    public JsonResult userList(HttpServletRequest request) {
        ExtFilter[] extFilters = ExtHelper.getFilters(request);
        if (extFilters == null) {
            Page<User> userPage = userService.find(ExtPageHelper.getFromRequest(request));
            List<User> userList = userPage.getResult();
            Page cUserPage = ExtPageHelper.getFromRequest(request);
            cUserPage.setPageNo(userPage.getPageNo());
            cUserPage.setPageSize(userPage.getPageSize());
            cUserPage.setRecordCount(userPage.getRecordCount());
            List<CCUser> ccUserList = new ArrayList<CCUser>();
            for (User user : userList) {
                CCUser ccuser = userService.getUser(user.getUserSysId());
                Object[] roles = roleService.getRoleNameByUserId(user.getUserSysId());
                ccuser.setUserRoles(getRoleNameToString(roles));
                ccUserList.add(ccuser);
            }
            return extPageResult(cUserPage.setResult(ccUserList));
        } else {
            PropertyFilter propertyFilter = ExtHelper.convertToPropertyFilter(extFilters);
            return extPageResult(userService.findPropertyFilter(ExtPageHelper.getFromRequest(request), propertyFilter));
        }
    }

    /**
     * 用户角色管理
     *
     * @return
     */
    @RequestMapping("/userRole/index")
    public String userRoleIndex() {
        return "user/userRoleList";
    }

    /**
     * 用户角色列表
     *
     * @return
     */
    @RequestMapping("/userRole/list.json")
    @ResponseBody
    public JsonResult userRoleList(HttpServletRequest request) {
        ExtFilter[] extFilters = ExtHelper.getFilters(request);
        if (extFilters == null) {
            return extPageResult(userService.findUserRole(ExtPageHelper.getFromRequest(request)));
        } else {
            PropertyFilter propertyFilter = ExtHelper.convertToPropertyFilter(extFilters);
            return extPageResult(userService.findUserRolePropertyFilter(ExtPageHelper.getFromRequest(request), propertyFilter));
        }
    }

    /**
     * 用户资源管理
     *
     * @return
     */
    @RequestMapping("/userRoleResource/index")
    public String userRoleResourceIndex() {
        return "user/resourceList";
    }

    /**
     * 用户资源列表
     *
     * @return
     */
    @RequestMapping("/userRoleResource/list.json")
    @ResponseBody
    public JsonResult userRoleResourceList(HttpServletRequest request) {
        ExtFilter[] extFilters = ExtHelper.getFilters(request);
        if (extFilters == null) {
            return extPageResult(userService.findResource(ExtPageHelper.getFromRequest(request)));
        } else {
            PropertyFilter propertyFilter = ExtHelper.convertToPropertyFilter(extFilters);
            return extPageResult(userService.findResourcePropertyFilter(ExtPageHelper.getFromRequest(request), propertyFilter));
        }
    }

    /**
     * 保存资源
     *
     * @param request
     * @param model
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/resource/saveResource.do", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult saveCustomer(Resource resource, HttpServletRequest request, Model model, HttpSession session) throws Exception {
//		boolean b= true;
        String userName = (String) request.getSession().getAttribute("OMS_USER_NAME");
//		String omsSubSystem = request.getParameter("omsSubSystem");
//		String omsModule = request.getParameter("omsModule");
//		String omsSubModule = request.getParameter("omsSubModule");
        String resourceName = request.getParameter("resourceName");
        String resourcePattern = request.getParameter("resourcePattern");
        String resourceDescription = request.getParameter("resourceDescription");
        String resourceStatus = request.getParameter("resourceStatus");
//		omsResource.setOmsSubSystem(omsSubSystem);
//		omsResource.setOmsModule(omsModule);
//		omsResource.setOmsSubModule(omsSubModule);
        resource.setResourceName(resourceName);
        resource.setResourcePattern(resourcePattern);
        resource.setResourceDescription(resourceDescription);
        resource.setResourceStatus(resourceStatus);
        String opResult = this.userService.saveOmsResource(resource, userName);
        /*if(!"success".equals(opResult)){
              b = false;
          }*/
        return this.jsonResult(opResult);

    }

    /**
     * 资源详细页
     *
     * @param omsResource
     * @param model
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/resource/resourceById.json")
    @ResponseBody
    public JsonResult resourceById(Resource omsResource,
                                   Model model, HttpServletRequest request) throws Exception {
        String sysId = request.getParameter("sysId");
        Resource r = this.userService.selectResourceByIdToJson(new Integer(sysId));
        return this.jsonResult(r);
    }


    /**
     * 修改资源信息
     *
     * @param resource
     * @param model
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/resource/updateResource.do", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult updateResource(Resource resource,
                                     Model model, HttpServletRequest request) throws Exception {
//		boolean b = true;
        String userName = (String) request.getSession().getAttribute("OMS_USER_NAME");
        String resourceSysId = request.getParameter("resourceSysId");
//		String omsSubSystem = request.getParameter("subSystem");
//		String omsModule = request.getParameter("module");
//		String omsSubModule = request.getParameter("subModule");
        String resourceName = request.getParameter("resourceName");
        String resourcePattern = request.getParameter("resourcePattern");
        String resourceDescription = request.getParameter("resourceDescription");
        String size = request.getParameter("size" + resourceSysId);
//        omsResource.setResourceSysId(omsResourceSysId);
//		omsResource.setOmsSubModule(omsSubModule);
//		omsResource.setOmsSubSystem(omsSubSystem);
//		omsResource.setOmsModule(omsModule);
        resource.setResourceName(resourceName);
        resource.setResourcePattern(resourcePattern);
        resource.setResourceDescription(resourceDescription);
        resource.setResourceStatus(size);
        String opResult = this.userService.saveOmsResource(resource, userName);
        /*if(!"success".equals(opResult)){
              b=false;
          }*/
        return this.jsonResult(opResult);
    }


    /**
     * 角色详细页
     *
     * @param omsRole
     * @param model
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/role/roleById.json", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult resourceById(Role omsRole, Model model, HttpServletRequest request) throws Exception {
        String sysId = request.getParameter("sysId");
        Object[] orr = roleService.getOmsRoleResourceById(sysId);
//        String resources = "";
//        if (!Validate.isNullOrEmpty(orr)) {
//            for (int i = 0; i < orr.length; i++) {
//                resources += orr[i].toString();
//                if (i < (orr.length - 1)) {
//                    resources += ",";
//                }
//            }
//        }
       StringBuffer buffer=new StringBuffer();
        if (!Validate.isNullOrEmpty(orr)) {
            for (int i = 0; i < orr.length; i++) {
              buffer.append(orr[i]);
                if (i < (orr.length - 1)) {
                   buffer.append(",");
                }
            }
        }
        String resources=buffer.toString();
        Role r = this.roleService.selectRoleByIdToJson(new Integer(sysId));
//        r.setRoleResources(resources);//获得资源
        return this.jsonResult(r);
    }


    /**
     * 首次加载页面时下拉框中的值  resource
     */
    @RequestMapping(value = "/role/getRoleResources.json")
    @ResponseBody
    public JsonResult getResource() {
        List<Resource> oResource = this.resourceService.selectOmsResource();
        List<Resource> oResourceAfter = new ArrayList<Resource>();
        for (Resource r : oResource) {
            if (r.getResourceStatus().equals(new String("1"))) {
                oResourceAfter.add(r);
            }
        }
        List<Resource> resources = this.resourceService.selectResourceToJson(oResourceAfter);
        Page<Resource> page = new Page<Resource>(0, 10);
        page.setResult(resources);
        page.setRecordCount(resources.size());
        return this.extPageResult(page);
    }

    /**
     * 保存角色
     *
     * @param request
     * @param model
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/role/saveRole.do")
    @ResponseBody
    public JsonResult saveCustomer(Role omsRole, HttpServletRequest request, Model model, HttpSession session) throws Exception {
        String[] orResource = request.getParameterValues("omsRoleResource");
        String userName = (String) request.getSession().getAttribute("OMS_USER_NAME");
//		String ecOrganization = request.getParameter("ecOrganization");
        String omsRoleId = request.getParameter("omsRoleId");
        String omsRoleName = request.getParameter("omsRoleName");
        String omsRoleDescription = request.getParameter("omsRoleDescription");
        String omsRoleStatus = request.getParameter("omsRoleStatus");
//		omsRole.setEcOrganization(ecOrganization);
        omsRole.setRoleId(omsRoleId);
        omsRole.setRoleName(omsRoleName);
        omsRole.setRoleDescription(omsRoleDescription);
        omsRole.setRoleStatus(omsRoleStatus);
        String opResult = this.roleService.saveOmsRole(omsRole, orResource, userName);
        /*if(!"success".equals(opResult)){
              b = false;
          }*/
        return this.jsonResult(opResult);
    }

    /**
     * 修改用户信息
     *
     * @param role
     * @param model
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/role/updateRole.do", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult updateRole(Role role,
                                 Model model, HttpServletRequest request) throws Exception {
//		boolean b = true;
        String[] orResource = request.getParameterValues("omsRoleResource");
        String userName = (String) request.getSession().getAttribute("OMS_USER_NAME");
        String omsRoleSysId = request.getParameter("omsRoleSysId");
       // String ecOrganization = request.getParameter("ecOrganization");
        String omsRoleId = request.getParameter("omsRoleId");
        String omsRoleName = request.getParameter("omsRoleName");
        String omsRoleDescription = request.getParameter("omsRoleDescription");
        String size = request.getParameter("size" + omsRoleSysId);
        role.setRoleSysId(Integer.valueOf(omsRoleSysId));
//        omsRole.setEcOrganization(ecOrganization);
        role.setRoleId(omsRoleId);
        role.setRoleName(omsRoleName);
        role.setRoleDescription(omsRoleDescription);
        role.setRoleStatus(size);


        String opResult = this.roleService.saveOmsRole(role, orResource, userName);
        /*if(!"success".equals(opResult)){
              b=false;
          }*/
        return this.jsonResult(opResult);
    }

    @RequestMapping("/user/remove.json")
    @ResponseBody
    public JsonResult removeUser(HttpServletRequest request) {
        String id = request.getParameter("id");
        if (!StringUtils.isBlank(id)) {
            return jsonResult(userService.remove(id));
        } else {
            return jsonResult("Parameter is blank.");
        }
    }

    /**
     * 首次加载页面时下拉框中的值
     * role extjs  kobs
     */
    @RequestMapping(value = "/user/getUserRoles.json")
    @ResponseBody
    private JsonResult getRole() {
        List<Role> rs = roleService.getAll();
        List<Role> rsAfter = new ArrayList<Role>();
        for (Role r : rs) {
            if ("ROLE_ANONYMOUS".equals(r.getRoleId())) {
                continue;
            }
            if (r.getRoleStatus().equals(new String("1"))) {
                rsAfter.add(r);
            }
        }
        List<CRole> CRoles = getRole(rsAfter);
        Page<CRole> page = new Page<CRole>(0, 10);
        page.setResult(CRoles);
        page.setRecordCount(CRoles.size());
        return this.extPageResult(page);
    }

    /**
     * extjs cao 类型的转化
     *
     * @return
     */
    private List<CRole> getRole(List<Role> roles) {
        List<CRole> CRoles = new ArrayList<CRole>();
        CRole CRole = null;
        for (Role role : roles) {
            CRole = new CRole();
            CRole.setCcRoleId(role.getRoleId().toString());
            CRole.setCcRoleName(role.getRoleName());
            CRole.setCcRoleSysId(role.getRoleSysId().toString());
            CRoles.add(CRole);
        }
        return CRoles;
    }


    /**
     * 修改时信息的初始化和添加的首次展示
     */
    @RequestMapping(value = "/user/form.json")
    @ResponseBody
    public JsonResult form(@RequestParam(value = "userSysId", required = false) Integer id) {
        if (id != null) {
            CCUser user = userService.getUser(id);//根据Id得到用户
            Object[] roles = roleService.getUserRoleByUserId(id);
            user.setUserRoles(getArrayToString(roles));
            return this.jsonResult(user);
        } else {
            return this.jsonResult(false);
        }
    }

    //字符串拼接
    private String getArrayToString(Object[] objs) {
        String str = "";
        if (objs.length > 0) {
            for (int i = 0; i < objs.length; i++) {
                str += objs[i].toString();
                if (i < (objs.length - 1))
                    str += ",";
            }
        }
        return str;
    }

    public String getRoleNameToString(Object[] objs) {
        String str = "";
        if (objs.length > 0) {
            for (int i = 0; i < objs.length; i++) {
                str += objs[i].toString();
                if (i < (objs.length - 1)) {
                    str += ",";
                }
            }
            if (str.length() > 20) {
                str = str.substring(0, 20) + "...";
            }
        }
        return str;
    }


    /**
     * 修改密码
     *
     * @Author tracy
     * @since init
     */
    @RequestMapping(value = "/user/updatePassword")
    @ResponseBody
    public JsonResult updatePassword(HttpServletRequest request, HttpSession session) {
        String userId = request.getParameter("omsUserId");
        String oldPasswordWord = request.getParameter("oldPasswordWord");//原密码
        String omsUserPassWord = request.getParameter("omsUserPassWord");//新密码
        String omsUserPassWordConfirm = request.getParameter("omsUserPassWordConfirm");//确认新密码

        User user = userService.findOmsUserByOmsUserId(userId);
        String encodedOldPwd = CommonHelper.encodeWithMD5(oldPasswordWord, null);
        String newPwd = CommonHelper.encodeWithMD5(omsUserPassWord, null);

        //原密码验证错误
        if (!encodedOldPwd.equals(user.getUserPassword())) {
            return this.jsonResult(Constants.PasswordSaveExceptionStatus.NotEqualsOriginalPasscode.name());
        }

        //新密码两次输入不一致
        if (!omsUserPassWord.equals(omsUserPassWordConfirm)) {
            return this.jsonResult(Constants.PasswordSaveExceptionStatus.NotEqualsNewPasscode.name());
        }

        //记录修改密码凭证的记录
//        OmsRecentCredential omsRecentCredential = new OmsRecentCredential();
//        omsRecentCredential.setOmsUserId(userId);
//        omsRecentCredential.setPasscode(newPwd);
//        omsRecentCredential.setCreateTime(CommonHelper.getThisTimestamp());
//        omsRecentCredential.setUpdateTime(CommonHelper.getThisTimestamp());
//        omsRecentCredential.setOperator(userId);
//        logger.debug("before saving, sys clears oldest credential records.");
//        //保留前10次的修改密码凭证
//        omsRecentCredentialService.clearOldestCredentialRecords(userId, Integer.parseInt(Constants.CredentialCode.RecentCredentialRecord.getVal()));
//        //保存前先查询，这次的密码，在之前是否使用，如果已使用，保存失败
//        Boolean isSuccess = omsRecentCredentialService.save(omsRecentCredential);
//        logger.debug("after saving as result[" + isSuccess + "], sys clears oldest credential records.");
//        omsRecentCredentialService.clearOldestCredentialRecords(userId, Integer.parseInt(Constants.CredentialCode.RecentCredentialRecord.getVal()));
//        if (!isSuccess.booleanValue()) {
//            return this.jsonResult(Constants.PasswordSaveExceptionStatus.ExistInRecentRecord.name());
//        }
//        omsUser.setAccountIsInit("0");
//        omsUser.setCredentialsLastModifiedTime(CommonHelper.getThisTimestamp());
        user.setUserPassword(newPwd);
        userService.save(user);
        logger.debug("save info of user[username:" + userId + "] successfully");
        return this.jsonResult("success");

    }


    /**
     * 执行保存的方法
     * cao
     */
    @RequestMapping(value = "/user/saveUser.do")
    @ResponseBody
    public JsonResult save(HttpServletRequest request, HttpSession session) {
        //从request中取值
        String userId = request.getParameter("userId");
        String userEmail = request.getParameter("userEmail");
        String userPassword = request.getParameter("userPassword");
//		String[] str_store = request.getParameterValues("omsUserStore");
        String[] roleStr = request.getParameterValues("userRole");
        String userNick = request.getParameter("userName");
        String userRemark = request.getParameter("userRemark");
        String cbModifyPass = request.getParameter("cbModifyPass");
        String userStatus = ""; //request.getParameter("size");
        String createOp = UserSecurityUtil.getCurrentUsername();
        Integer[] roles = null;
        if (null != roleStr && roleStr.length > 0) {
            roles = new Integer[roleStr.length];
            for (int i = 0; i < roleStr.length; i++) {
                String roleId = roleStr[i];
                if (!"".equals(roleId)) {
                    roles[i] = Integer.valueOf(roleId);
                }
            }
        }
        //创建User对象
        String userSysId = request.getParameter("userSysId");
        User cUser = null;
        if (!Validate.isNullOrEmpty(userSysId)) {
            cUser = userService.get(new Integer(userSysId));
            cUser.setUpdateOp(createOp);
            cUser.setUpdateTime(CommonHelper.getThisTimestamp());
            userStatus = request.getParameter("size" + userSysId);
            cUser.setUserStatus(userStatus);
            cUser.setUserSysId(Integer.parseInt(userSysId));
        } else {
            cUser = new User();
            cUser.setUserId(userId);
            cUser.setCreateOp(createOp);
            cUser.setVersion(0);
            cUser.setCreateTime(CommonHelper.getThisTimestamp());
            userStatus = request.getParameter("size");
            cUser.setUserStatus(userStatus);
            cUser.setUpdateOp(createOp);
            cUser.setUpdateTime(CommonHelper.getThisTimestamp());
            cUser.setUserEmail(userEmail);
            String newPwd = CommonHelper.encodeWithMD5(userPassword, null);
            cUser.setUserPassword(newPwd);
        }
        cUser.setUserName(userNick);
        if ("true".equals(cbModifyPass)) {
            String newPwd = CommonHelper.encodeWithMD5(userPassword, null);
            cUser.setUserPassword(newPwd);
        }
        cUser.setUserRemark(userRemark);

        //是否要修改密码
        String modifyValue = request.getParameter("operatPwd");   //当需要修改密码的时候值为：on ，否则为null
        //保存操作
        String result = userService.saveUser(cUser, createOp,userEmail, roles, request, modifyValue);
        resourceDetailsMonitor.refresh();
        return this.jsonResult(result);
    }

    @RequestMapping(value = "/user/addNewResource")
    @ResponseBody
    public JsonResult addNewResource(HttpServletRequest request) {
        String sysId = request.getParameter("id");
        String groupId = request.getParameter("parentId");
        String name = request.getParameter("text");
        String code = request.getParameter("code");
        String url = request.getParameter("url");
        String desc = request.getParameter("description");
        String type = request.getParameter("type");
        Resource resource = new Resource();
        if (StringUtils.isBlank(groupId)) {
            groupId = "0";
        }
        resource.setParentResourceSysId(groupId);
        if (!"".equals(sysId)) {
            resource.setResourceSysId(new Integer(sysId));
        }
        resource.setResourceName(name);
        resource.setResourceCode(code);
        resource.setResourceDescription(desc);
        resource.setResourceType(type);
        resource.setResourcePattern(url);
        resource.setResourceStatus("1");
        resource.setCreateTime(CommonHelper.getThisTimestamp());
        resource.setCreateOp(UserSecurityUtil.getCurrentUsername());
        Map<String, Object> map = new HashMap<String, Object>();
        resource = resourceService.saveOrUpdate(resource);
        if (null != resource) {
            map.put("success", true);
            map.put("resource", resource.convertTreeNode());
        } else {
            map.put("success", false);
        }

        resourceDetailsMonitor.refresh();
        return jsonResult(map);
    }

    @RequestMapping("/userRoleResource/tree.json")
    @ResponseBody
    public JSONArray userRoleResourceTree(HttpServletRequest request) {
        String parentId = request.getParameter("node");
        if (StringUtils.isBlank(parentId)) {
            parentId = "0";
        }
        return JSONArray.fromObject(resourceService.buildResourceTreeByParentIdSingle(parentId));
    }

    @RequestMapping("/userRoleResource/checkResource")
    @ResponseBody
    public JsonResult checkResource(HttpServletRequest request) {
        String id = request.getParameter("id");
        if (!StringUtils.isBlank(id)) {
            return jsonResult(resourceService.checkResourceIsReferenceByRole(new Integer(id)));
        } else {
            return jsonResult("Parameter is blank.");
        }
    }

    @RequestMapping("/userRoleResource/remove")
    @ResponseBody
    public JsonResult remove(HttpServletRequest request) {
        String id = request.getParameter("id");
        if (!StringUtils.isBlank(id)) {
            resourceDetailsMonitor.refresh();
            return jsonResult(resourceService.remove(new Integer(id)));
        } else {
            return jsonResult("Parameter is blank.");
        }
    }

    @RequestMapping(value = "/user/mergeRole")
    @ResponseBody
    public JsonResult mergeRole(HttpServletRequest request) {
        String id = request.getParameter("id");
        String code = request.getParameter("code");
        String name = request.getParameter("text");
        String description = request.getParameter("description");
        String roles = request.getParameter("resourceIds");
        Role role = new Role();
        Map<String, Object> map = new HashMap<String, Object>();
        //判断角色名称是否存在
        List<Role> roleList = roleService.validateRoleName(name);
        if (!"".equals(id)) {
            role.setRoleSysId(new Integer(id));
        }else{
            code = "ROLE_"+CommonHelper.getThisTimestamp().getTime();
        }
        if (!CollectionUtils.isEmpty(roleList)) {
            Role roleObj = roleService.validateRoleName(name).get(0);
            if (null != roleObj) {
                if (!id.equals(roleObj.getRoleSysId().toString()) || !name.equals(roleObj.getRoleName())) {
                    map.put("error", "RoleNameError");
                    return jsonResult(map);
                }
            }
        }
        role.setRoleId(code);
        role.setRoleName(name);
        role.setRoleDescription(description);
//      role.setField1(roles);
        role.setRoleStatus("1");
        role = roleService.saveOrUpdate(role);
        if (null != role) {
            roleService.saveRoleResource(roles, role);
            map.put("success", true);
            RoleTreeNode node = (RoleTreeNode) role.convertTreeNode();
            node.setResourceIds(roles);
            map.put("role", node);
        } else {
            map.put("success", false);
        }

        resourceDetailsMonitor.refresh();
        return jsonResult(map);
    }

    @RequestMapping("/userRole/tree.json")
    @ResponseBody
    public JSONArray userRoleTree() {
        return JSONArray.fromObject(roleService.buildRoleTreeSingle());
    }

    @RequestMapping("/userRole/checkResource")
    @ResponseBody
    public JsonResult checkRole(HttpServletRequest request) {
        String id = request.getParameter("id");
        if (!StringUtils.isBlank(id)) {
            return jsonResult(roleService.checkRoleIsReferenceByUser(new Integer(id)));
        } else {
            return jsonResult("Parameter is blank.");
        }
    }

    @RequestMapping("/userRole/remove")
    @ResponseBody
    public JsonResult removeRole(HttpServletRequest request) {
        String id = request.getParameter("id");
        if (!StringUtils.isBlank(id)) {
            resourceDetailsMonitor.refresh();
            return jsonResult(roleService.remove(new Integer(id)));
        } else {
            return jsonResult("Parameter is blank.");
        }
    }

    @RequestMapping("/userRole/refresh")
    @ResponseBody
    public JsonResult refreshCacheOfUserRoleResource() {
        resourceDetailsMonitor.refresh();
        return jsonResult("true");
    }

    @RequestMapping("/updateCredential")
    public String updateCredential(HttpServletRequest request, Model model) {
        String username = request.getParameter("username");
        if (StringUtils.isBlank(username)) {
            return redirect("/login");
        }
        model.addAttribute("username", username);
        return "updateCredential";
    }

    @RequestMapping("/validateCode")
    @ResponseBody
    public JsonResult validateCode(HttpServletRequest request) {
        String userCode = request.getParameter("code");
        return jsonResult(validateUserCode(request, userCode));
    }

    private boolean validateUserCode(HttpServletRequest request, String userCode) {
        return asyncCaptchaService.validateResponseForID(request.getSession().getId(), userCode).booleanValue();
    }

    @RequestMapping("/validateOriginalPasscode")
    @ResponseBody
    public JsonResult validateOriginalPasscode(HttpServletRequest request) {
        String username = request.getParameter("username");
        String passcode = request.getParameter("passcode");
        User user = userService.findOmsUserByOmsUserId(username);
        String originalPasscode = user.getUserPassword();

        return jsonResult(originalPasscode.equals(CommonHelper.encodeWithMD5(passcode, null)));
    }

    @RequestMapping(value = "/user/resetPassword")
    public String resetPassword(HttpServletRequest request) {
        try {
            logger.debug("resetpassword start");
            String token = request.getParameter("token");
            String email=request.getParameter("email");
            if (Validate.isNullOrEmpty(token)) {
                request.setAttribute("info","请求失败，token不存在！");
                return "/user/resetPasswordSuccess";
            }
            Email emails = emailService.findEmailByToken(token);
            if (Validate.isNullOrEmpty(emails)) {
                request.setAttribute("info","请求失败，邮箱不存在！");
                return "/user/resetPasswordSuccess";
            }
            User user=userService.findUserByUserEmail(emails.getEmail());
            if (Validate.isNullOrEmpty(user)){
                request.setAttribute("info","邮箱被更改，链接失效");
                return "/user/resetPasswordSuccess";
            }
            Timestamp startTime = emails.getTime();
            Timestamp currentionTime = new Timestamp(System.currentTimeMillis());
            long minTime = (currentionTime.getTime() - startTime.getTime()) / (1000 * 60);
            Integer status = emails.getStatus();
            if (1 == status) {
                //链接是否失效
                request.setAttribute("info","链接已失效!");
                return "/user/resetPasswordSuccess";
            }
            if (2 == status) {
                //链接是否已经点击了
                request.setAttribute("info","链接已经被点击完成修改了！") ;
                return "/user/resetPasswordSuccess";
            }
            if (120 < minTime) {
                emails.setStatus(1);
                request.setAttribute("info","链接已失效");
                return "/user/resetPasswordSuccess";
            }
            emailService.saveOrUpdate(emails);
            request.setAttribute("token",token);
            request.setAttribute("email",email);
            logger.debug("resetpassword end");
            return "user/resetPassword";
        }catch (Exception ex){
            ex.printStackTrace();
            request.setAttribute("info","请求失败");
            logger.debug(ex.getMessage());
            return "/user/resetPasswordSuccess";
        }
    }

    /**
     * 验证邮箱是否存在，存在发送邮件，不存在 ，返回
     * @Author songsong
     * @since init
     */
    @RequestMapping(value = "/user/validateAndSendEmail")
    @ResponseBody
    public  JsonResult validateAndSendEmail(HttpServletRequest request){
         try{
             logger.debug("send email start");
             String userEmail=request.getParameter("userEmail");
             if (Validate.isNullOrEmpty(userEmail)){
                 return this.jsonResult(Constants.EmailExceptionStatus.EmailIsEmpty.name());
             }
             User user=userService.findUserByUserEmail(userEmail);
             if (Validate.isNullOrEmpty(user)){
                 return this.jsonResult(Constants.EmailExceptionStatus.IlegalEmail.name());
             }
             emailService.sendResetPasswordEmail(userEmail);
             logger.debug("send email end");
             return  this.jsonResult("success");

         }catch (Exception ex){
             ex.printStackTrace();
             logger.debug(ex.getMessage());
             return  this.jsonResult("failure");
         }
    }

    /**
     * 忘记密码修改密码
     *
     * @Author xss
     */
    @RequestMapping(value = "/user/updatePasswordBySendEmail")
    public String updatePasswordBySendEmail(HttpServletRequest request,HttpSession session) {
        try{
            logger.debug("updatePassword start");
            String userPassWord = request.getParameter("userPassword");//新密码
            String userPassWordConfirm = request.getParameter("userPassWordConfirm");//确认新密码
            String token=request.getParameter("token");
            String email=request.getParameter("email");
           // request.setAttribute("info","");
            //新密码两次输入不一致
            if (!userPassWord.equals(userPassWordConfirm)) {
                request.setAttribute("info","两次密码不一致！");
                return "user/resetPassword?email="+email+"&token="+token;
            }

            //验证token
            Email emails=emailService.findEmailByToken(token);
            if (Validate.isNullOrEmpty(emails)){
                request.setAttribute("info","你的请求不合法");
                return "user/resetPassword";
            }
            //验证邮箱
            List<Email> emailList=emailService.findEmailByEmail(email);
            if (Validate.isNullOrEmpty(emailList)){
                request.setAttribute("info","你的请求不合法");
                return "user/resetPassword";
            }
            Email emailObject=null;
            //发送多次修改链接，当某次完成修改之后，其余状态全置位2，让其失效
            for(int i=0;i<emailList.size();i++){
                emailObject=emailList.get(i);
                emailObject.setStatus(2);
                emailService.saveOrUpdate(emailObject);
            }
             User user=userService.findUserByUserEmail(email);
            if (!Validate.isNullOrEmpty(user)){
                String newPwd = CommonHelper.encodeWithMD5(userPassWord, null);
                user.setUserPassword(newPwd);
                user.setUpdateTime(CommonHelper.getThisTimestamp());
                userService.save(user);
                request.setAttribute("info","修改密码成功！");
            }else{
                request.setAttribute("info","链接失效");
                return "user/resetPassword";
            }
            logger.debug("updatePassword end");
        }catch (Exception ex){
            ex.printStackTrace();
            logger.debug(ex.getMessage());
            request.setAttribute("info","修改密码失败！");
        }
         return  "/user/resetPasswordSuccess";
    }

//    @RequestMapping(value = "/user/resetPasswordSuccess")
//    public String updatePasswordBySendEmailSuccess(HttpServletRequest request, HttpSession session) {
//        return  "/user/resetPasswordSuccess";
//    }

    @RequestMapping(value = "/user/test")
    public String updatePasswordBySendEmailSuccess(HttpServletRequest request, HttpSession session) {
        return  "/user/test";
    }
}
