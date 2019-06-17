package com.arvato.cc.util;

import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: pang008
 * Date: 12/11/12
 * Time: 11:41
 * To change this template use File | Settings | File Templates.
 */
public class RoleTreeNode extends ExtTreeNode {
    private String code;
    private String roles;
    private String description;
    private String resourceIds;
    private Set<RoleTreeNode> children;
    private Set<ResourceTreeNode> resources;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<RoleTreeNode> getChildren() {
        return children;
    }

    public void setChildren(Set<RoleTreeNode> children) {
        this.children = children;
    }

    public Set<ResourceTreeNode> getResources() {
        return resources;
    }

    public void setResources(Set<ResourceTreeNode> resources) {
        this.resources = resources;
    }

    public String getResourceIds() {
        return resourceIds;
    }

    public void setResourceIds(String resourceIds) {
        this.resourceIds = resourceIds;
    }
}
