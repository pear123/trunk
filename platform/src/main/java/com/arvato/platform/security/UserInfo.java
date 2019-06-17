package com.arvato.platform.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author leon
 */
public class UserInfo extends User {

    private boolean credentialNonInit;

    private Map<String, Object> attributes = new HashMap<String, Object>();

    public UserInfo(String username, String password,
                    boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired,
                    boolean accountNonLocked, boolean credentialNonInit, Collection<? extends GrantedAuthority> authorities) {

        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.setCredentialNonInit(credentialNonInit);
    }

    public boolean getCredentialNonInit() {
        return credentialNonInit;
    }

    public void setCredentialNonInit(boolean credentialNonInit) {
        this.credentialNonInit = credentialNonInit;
    }

    public void setAttribute(String key, Object value) {
        getAttributes().put(key, value);
    }

    public void setAttributes(Map attribute) {
        getAttributes().putAll(attribute);
    }

    public Object getAttribute(String key) {
        return getAttributes().get(key);
    }

    public void removeAttribute(String key) {
        if (getAttributes().containsKey(key)) {
            getAttributes().remove(key);
        }
    }

    /**
     * @return the attributes
     */
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserInfo)) return false;
        if (!super.equals(o)) return false;

        UserInfo userInfo = (UserInfo) o;

        if (!attributes.equals(userInfo.attributes)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + attributes.hashCode();
        return result;
    }
}
