package com.arvato.platform.controller.sys;

import com.arvato.cc.model.CResource;
import com.arvato.cc.service1.ResourceService;
import com.arvato.platform.security.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: pang008
 * Date: 13/09/12
 * Time: 12:17
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class SystemController extends CommonController {
//    @Autowired
//    private OmsCredentialService credentialService;

    @Autowired
    private ResourceService resourceService;

    @RequestMapping(value = "/login")
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/determine")
    public String determine(Model model, HttpServletRequest request) {
        SecurityContextImpl securityContextImpl = (SecurityContextImpl) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
        String userName = securityContextImpl.getAuthentication().getName();
        List<CResource> menus = resourceService.getAllMenus(userName);
        request.getSession().setAttribute("menus",menus);
//        model.addAttribute("menus",menus);
        return "loginDetermine";
    }
    @RequestMapping(value = "/welcome")
    public String commonPage(Model model, HttpServletRequest request) {
        return "welcome";
    }

    @RequestMapping(value = "/loginException")
    public String loginView(Model model, HttpServletRequest request, HttpServletResponse response) {
        String error = request.getParameter("error");
//        if(Validate.isNullOrEmpty(error)){
//            return "loginIndex";
//        }
        if (!"2".equals(error)) {

            AuthenticationException authenticationException = (AuthenticationException) request.getSession(false).getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
            if (null != authenticationException) {
                if (authenticationException instanceof BadCredentialsException) {
                    UserInfo userInfo = (UserInfo) authenticationException.getExtraInformation();
                    if (null != userInfo) {
//                        OmsLoginException loginException = new OmsLoginException();
//                        loginException.setLoginAccount(userInfo.getUsername());
//                        loginException.setLoginType(Constants.LockedTypeOfLogin.Account.name());
//                        loginException.setCreateTime(CommonHelper.getThisTimestamp());
//                        loginException.setEnable("1");
//                        credentialService.saveLoginException(loginException);
//                        String message = credentialService.isLockedAccount(userInfo.getUsername());
//                        if (StringUtils.isNotBlank(message)) {
//                            model.addAttribute("msg", message);
//                            model.addAttribute("isNeedTranslate", "1");
//                        } else {
                            model.addAttribute("msg", "login.error.name.pass");
//                        }
                    } else {
                        model.addAttribute("msg", "login.error.name.pass");
                    }
                } else if (authenticationException instanceof LockedException) {
                    model.addAttribute("msg", "login.error.locked");
                } else if (authenticationException instanceof DisabledException) {
                    model.addAttribute("msg", "login.error.lock");
                    UserInfo userInfo = (UserInfo) authenticationException.getExtraInformation();
                    if (null != userInfo) {
                        model.addAttribute("username", userInfo.getUsername());
                    }
                } else if (authenticationException instanceof CredentialsExpiredException) {
                    model.addAttribute("msg", "login.error.credential.expired");
                    UserInfo userInfo = (UserInfo) authenticationException.getExtraInformation();
                    if (null != userInfo) {
                        model.addAttribute("username", userInfo.getUsername());
                    }
                }
            }
        } else {
            model.addAttribute("msg", "login.error.captcha");
        }

        return "loginIndex";
    }


    @RequestMapping(value = "/sys/user/list")
    public String userIndex() {
        return "user/userList";
    }

    @RequestMapping(value = "/accessDenied")
    public String accessDenied() {
        return "accessDenied/index";
    }
}
