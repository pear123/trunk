package com.arvato.cc.util;

/**
 * Created with IntelliJ IDEA.
 * User: pang008
 * Date: 01/11/12
 * Time: 01:19
 * To change this template use File | Settings | File Templates.
 */
public class ResourceTreeNode extends ExtTreeNode {
    private String type;

    private String description;

    private String url;

    private String code;

//    private Set<ResourceTreeNode> children;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

//    public Set<ResourceTreeNode> getChildren() {
//        return children;
//    }
//
//    public void setChildren(Set<ResourceTreeNode> children) {
//        this.children = children;
//    }
}
