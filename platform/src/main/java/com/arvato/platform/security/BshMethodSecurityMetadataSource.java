package com.arvato.platform.security;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.method.MethodSecurityMetadataSource;

import java.lang.reflect.Method;
import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: panguanghua
 * Date: 12-5-15
 * Time: 上午10:55
 * To change this template use File | Settings | File Templates.
 */
public class BshMethodSecurityMetadataSource implements MethodSecurityMetadataSource {

    public Collection<ConfigAttribute> getAttributes(Method method, Class<?> targetClass) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean supports(Class<?> clazz) {
        return true;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
