package com.arvato.platform.security;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: panguanghua
 * Date: 12-5-14
 * Time: 下午11:35
 * To change this template use File | Settings | File Templates.
 */
public class BshSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

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
