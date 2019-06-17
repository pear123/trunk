/**
 * Copyright (c) 2000-2010 arvato systems (Shanghai)
 *
 * $Id: ExtTreeNode.java 2011-5-6 16:05:15 Justin $
 */
package com.arvato.cc.util;

import java.util.List;

/**
 *
 * @author Justin
 */
public class ExtTreeNodeSingle {

    protected String id;
    protected String parentId;
    protected Integer index;
    protected Integer depth = 0;
    protected String text;
    protected Boolean expanded = false;
    protected boolean leaf;
    protected String cls;
    protected String iconCls;
    protected Boolean root = false;
    protected Boolean isLase = false;
    private Boolean isFirst = false;
    private Boolean allowDrop = true;
    private Boolean allowDrag = true;
    private Boolean loaded = false;
    private Boolean loading = false;
    private String href = null;
    private String hrefTarge = null;
    private String qtip = null;
    private String qtitle = null;
    private List<ExtTreeNodeSingle> childNodes;

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the parentId
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * @param parentId the parentId to set
     */
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    /**
     * @return the index
     */
    public Integer getIndex() {
        return index;
    }

    /**
     * @param index the index to set
     */
    public void setIndex(Integer index) {
        this.index = index;
    }

    /**
     * @return the depth
     */
    public Integer getDepth() {
        return depth;
    }

    /**
     * @param depth the depth to set
     */
    public void setDepth(Integer depth) {
        this.depth = depth;
    }

    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @return the expanded
     */
    public Boolean getExpanded() {
        return expanded;
    }

    /**
     * @param expanded the expanded to set
     */
    public void setExpanded(Boolean expanded) {
        this.expanded = expanded;
    }




    /**
     * @return the leaf
     */
    public boolean isLeaf() {
        return leaf;
    }

    /**
     * @param leaf the leaf to set
     */
    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    /**
     * @return the cls
     */
    public String getCls() {
        return cls;
    }

    /**
     * @param cls the cls to set
     */
    public void setCls(String cls) {
        this.cls = cls;
    }

    /**
     * @return the iconCls
     */
    public String getIconCls() {
        return iconCls;
    }

    /**
     * @param iconCls the iconCls to set
     */
    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    /**
     * @return the root
     */
    public Boolean getRoot() {
        return root;
    }

    /**
     * @param root the root to set
     */
    public void setRoot(Boolean root) {
        this.root = root;
    }

    /**
     * @return the isLase
     */
    public Boolean getIsLase() {
        return isLase;
    }

    /**
     * @param isLase the isLase to set
     */
    public void setIsLase(Boolean isLase) {
        this.isLase = isLase;
    }

    /**
     * @return the isFirst
     */
    public Boolean getIsFirst() {
        return isFirst;
    }

    /**
     * @param isFirst the isFirst to set
     */
    public void setIsFirst(Boolean isFirst) {
        this.isFirst = isFirst;
    }

    /**
     * @return the allowDrop
     */
    public Boolean getAllowDrop() {
        return allowDrop;
    }

    /**
     * @param allowDrop the allowDrop to set
     */
    public void setAllowDrop(Boolean allowDrop) {
        this.allowDrop = allowDrop;
    }

    /**
     * @return the allowDrag
     */
    public Boolean getAllowDrag() {
        return allowDrag;
    }

    /**
     * @param allowDrag the allowDrag to set
     */
    public void setAllowDrag(Boolean allowDrag) {
        this.allowDrag = allowDrag;
    }

    /**
     * @return the loaded
     */
    public Boolean getLoaded() {
        return loaded;
    }

    /**
     * @param loaded the loaded to set
     */
    public void setLoaded(Boolean loaded) {
        this.loaded = loaded;
    }

    /**
     * @return the loading
     */
    public Boolean getLoading() {
        return loading;
    }

    /**
     * @param loading the loading to set
     */
    public void setLoading(Boolean loading) {
        this.loading = loading;
    }

    /**
     * @return the href
     */
    public String getHref() {
        return href;
    }

    /**
     * @param href the href to set
     */
    public void setHref(String href) {
        this.href = href;
    }

    /**
     * @return the hrefTarge
     */
    public String getHrefTarge() {
        return hrefTarge;
    }

    /**
     * @param hrefTarge the hrefTarge to set
     */
    public void setHrefTarge(String hrefTarge) {
        this.hrefTarge = hrefTarge;
    }

    /**
     * @return the qtip
     */
    public String getQtip() {
        return qtip;
    }

    /**
     * @param qtip the qtip to set
     */
    public void setQtip(String qtip) {
        this.qtip = qtip;
    }

    /**
     * @return the qtitle
     */
    public String getQtitle() {
        return qtitle;
    }

    /**
     * @param qtitle the qtitle to set
     */
    public void setQtitle(String qtitle) {
        this.qtitle = qtitle;
    }

    /**
     * @return the childNodes
     */
    public List<ExtTreeNodeSingle> getChildNodes() {
        return childNodes;
    }

    /**
     * @param childNodes the childNodes to set
     */
    public void setChildNodes(List<ExtTreeNodeSingle> childNodes) {
        this.childNodes = childNodes;
    }
}
