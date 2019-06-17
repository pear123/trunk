package com.arvato.platform.controller.Interceptors;

import com.arvato.cc.constant.Constants;
import com.arvato.cc.util.UserSecurityUtil;
import com.arvato.jdf.web.json.JsonResult;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: caol005
 * Date: 15-9-18
 * Time: 上午11:24
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        String username = UserSecurityUtil.getCurrentUsername();
        if(!StringUtils.isNotBlank(username) || "anonymousUser".equals(username)){
            String uri = request.getRequestURI();
            if(uri.contains(".")){
                response.setContentType("text/html; charset=UTF-8");
                String msg = JSONObject.fromObject(new JsonResult(true, "session-invalidate", "session-invalidate")).toString();
                try {
                    response.getWriter().write(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else {
                response.sendRedirect("/bsh/login");
            }
            return false;
        }
        return true;
    }
}
