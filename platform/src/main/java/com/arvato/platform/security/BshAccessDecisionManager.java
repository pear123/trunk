package com.arvato.platform.security;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: panguanghua
 * Date: 12-5-14
 * Time: 下午11:33
 * To change this template use File | Settings | File Templates.
 */
public class BshAccessDecisionManager implements AccessDecisionManager{
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {

        if (configAttributes == null) {
            return;
        }

        Iterator<ConfigAttribute> ite = configAttributes.iterator();

        while (ite.hasNext()) {
            ConfigAttribute ca = ite.next();

            String needRole = ((SecurityConfig) ca).getAttribute();

            for (GrantedAuthority ga : authentication.getAuthorities()) {

                String role[] = needRole.split(",");

                for (int i = 0; i < role.length; i++) {

                    if (role[i].equals(ga.getAuthority())) {
                        return;
                    }

                }

            }
        }
        throw new AccessDeniedException("unauthorized");
    }

    public boolean supports(ConfigAttribute attribute) {
        return true;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean supports(Class<?> clazz) {
        return true;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
