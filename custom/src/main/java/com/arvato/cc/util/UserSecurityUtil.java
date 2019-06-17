package com.arvato.cc.util;

import com.arvato.cc.constant.Constants;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Created with IntelliJ IDEA.
 * User: pang008
 * Date: 19/10/12
 * Time: 00:56
 * To change this template use File | Settings | File Templates.
 */
public abstract class UserSecurityUtil {
    /**
     * Get user name in current session.
     *
     * @return
     */
    //修改当interfaces访问的时候获取不到配置报错问题
    public static String getCurrentUsername() {
        String username;
        try{
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal instanceof UserDetails) {
                username = ((UserDetails) principal).getUsername();
            } else {
                username = principal.toString();
            }
        }catch(Exception e) {
            username=null;
        }
        return StringUtils.isNotBlank(username) ? username : Constants.SYSTEM_OPERATOR;
    }
}
