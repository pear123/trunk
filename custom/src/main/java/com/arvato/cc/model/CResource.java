package com.arvato.cc.model;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: ZHAN405
 * Date: 15-8-26
 * Time: 下午12:58
 * To change this template use File | Settings | File Templates.
 */
public class CResource implements java.io.Serializable{
    private Integer resourceSysId;
    private String resourceName;
    private String resourcePattern;
    private String resourceStatus;
    private String resourceDescription;
    private String resourceType;
    private String resourceCode;

    public String getResourceCode() {
        return resourceCode;
    }

    public void setResourceCode(String resourceCode) {
        this.resourceCode = resourceCode;
    }

    public List<Resource> getSubResourceNodes() {
        return subResourceNodes;
    }

    public void setSubResourceNodes(List<Resource> subResourceNodes) {
        this.subResourceNodes = subResourceNodes;
    }

    private String parentResourceSysId;
    private List<Resource> subResourceNodes = new ArrayList<Resource>();

    public Integer getResourceSysId() {
        return resourceSysId;
    }

    public void setResourceSysId(Integer resourceSysId) {
        this.resourceSysId = resourceSysId;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getResourcePattern() {
        return resourcePattern;
    }

    public void setResourcePattern(String resourcePattern) {
        this.resourcePattern = resourcePattern;
    }

    public String getResourceStatus() {
        return resourceStatus;
    }

    public void setResourceStatus(String resourceStatus) {
        this.resourceStatus = resourceStatus;
    }

    public String getResourceDescription() {
        return resourceDescription;
    }

    public void setResourceDescription(String resourceDescription) {
        this.resourceDescription = resourceDescription;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getParentResourceSysId() {
        return parentResourceSysId;
    }

    public void setParentResourceSysId(String parentResourceSysId) {
        this.parentResourceSysId = parentResourceSysId;
    }

}
