/**
 * Copyright (c) 2000-2010 arvato systems (Shanghai)
 *
 * $Id: TreeNode.java 2011-5-6 16:05:15 Justin $
 */
package com.arvato.cc.model;

import com.arvato.jdf.util.ConvertHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.Serializable;
import java.util.*;

/**
 * @author Justin
 */
@JsonSerialize
public class TreeNode implements Serializable {

    public static final String ROOT_NODE_PARENTID = "root_node_parentid";
    private static final long serialVersionUID = 6303541091287469341L;
    private String id;
    private String parentId;
    private String text;
    private boolean leaf;
    private Integer sort;
    private TreeNode parent;
    private List<TreeNode> children;
    private Map<String, Object> attributes;

    public TreeNode() {
    }

    public TreeNode(String id) {
        this.id = id;

    }

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
     * @return the children
     */
    public List<TreeNode> getChildren() {
        return children;
    }

    /**
     * @param children the children to set
     */
    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }

    public void addChild(TreeNode node) {
        if (node != null) {
            if (children == null) {
                children = new ArrayList<TreeNode>();
            }
            children.add(node);
        }
    }

    /**
     * @return the sort
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * @param sort the sort to set
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * @return the options
     */
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    /**
     * @return the options
     */
    public Object getAttribute(String key) {
        if (attributes == null) {
            return null;
        }
        return attributes.get(key);
    }

    /**
     * @param options the options to set
     */
    public void setAttributes(Map<String, Object> options) {
        this.attributes = options;
    }

    /**
     * @param key
     * @param val
     */
    public void addAttribute(String key, Object val) {
        if (attributes == null) {
            attributes = new HashMap<String, Object>();
        }
        attributes.put(key, val);
    }


    public void move(String srcNodeId, String desParentId, int index) {

        if (!this.id.equals(srcNodeId)) {

            TreeNode srcNode = this.findNode(srcNodeId);
            TreeNode desNode = this.findNode(desParentId);

            if (desNode.getChildren() == null) {
                desNode.setChildren(new ArrayList<TreeNode>());
            }
            if (index < 0 || index > desNode.getChildren().size()) {
                desNode.getChildren().add(srcNode);
            } else {
                desNode.getChildren().add(index, srcNode);
            }

            srcNode.getParent().getChildren().remove(srcNode);
            srcNode.setParent(desNode);
            srcNode.setParentId(desNode.getId());
        }
    }

    public List<TreeNode> findNodesByIdPrefix(String prefix) {
        List<TreeNode> rsList = new ArrayList<TreeNode>();

        if (StringUtils.startsWith(this.id, prefix)) {
            rsList.add(this);
        }

        if (CollectionUtils.isNotEmpty(this.children)) {
            for (TreeNode node : this.children) {
                rsList.addAll(node.findNodesByIdPrefix(prefix));
            }
        }

        return rsList;
    }

    /**
     * @param id - the id to be found
     */
    public TreeNode findNode(String id) {

        if (StringUtils.isEmpty(id)) {
            return null;
        }

        if (getId().equals(id)) {
            return this;
        }

        if (CollectionUtils.isNotEmpty(children)) {
            for (TreeNode node : children) {
                TreeNode foundNode = node.findNode(id);
                if (foundNode != null) {
                    return foundNode;
                }
            }
        }
        return null;
    }

    /**
     * @param onlyLeaf
     */
    public List<String> getChildrenIds(boolean onlyLeaf) {
        if (CollectionUtils.isNotEmpty(children)) {
            List<String> currentIds = ConvertHelper.convertBeanPropertyToList(children, "id");

            for (TreeNode node : children) {
                List<String> childrenIds = node.getChildrenIds();
                if (childrenIds != null) {
                    currentIds.addAll(childrenIds);
                }
            }

            return currentIds;
        }
        return null;
    }

    /**
     *
     */
    public List<String> getChildrenIds() {
        return getChildrenIds(false);
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
     * @return the parent
     */
    public TreeNode getParent() {
        return parent;
    }

    /**
     * @param parent the parent to set
     */
    public void setParent(TreeNode parent) {
        this.parent = parent;
    }

    /**
     * @return the parent
     */
    public boolean isRootNode() {
        if (getParentId().equals(ROOT_NODE_PARENTID)) {
            return true;
        }
        return false;
    }

    /**
     *
     */
    public List<TreeNode> getLinkedParents() {
        List<TreeNode> nodes = new ArrayList<TreeNode>();

        TreeNode currentNode = this;

        while (!currentNode.isRootNode()) {
            TreeNode currentParent = currentNode.getParent();
            nodes.add(currentParent);
            currentNode = currentParent;
        }

        Collections.reverse(nodes);
        return nodes;
    }

    /**
     * 取得每个层级的所有子节点
     *
     * @return 每个层级的所有子节点
     */
    public List<TreeNode> getAllChildren() {

        List<TreeNode> allChildren = getChildren();
        if (CollectionUtils.isNotEmpty(allChildren)) {
            for (TreeNode child : allChildren) {
                if (CollectionUtils.isNotEmpty(child.getAllChildren())) {
                    allChildren.addAll(child.getAllChildren());
                }
            }
        }
        return allChildren;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TreeNode other = (TreeNode) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        if ((this.parentId == null) ? (other.parentId != null) : !this.parentId.equals(other.parentId)) {
            return false;
        }
        if ((this.text == null) ? (other.text != null) : !this.text.equals(other.text)) {
            return false;
        }
        if (this.leaf != other.leaf) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 67 * hash + (this.parentId != null ? this.parentId.hashCode() : 0);
        hash = 67 * hash + (this.text != null ? this.text.hashCode() : 0);
        hash = 67 * hash + (this.leaf ? 1 : 0);
        hash = 67 * hash + (this.sort != null ? this.sort.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        if (CollectionUtils.isNotEmpty(this.children)) {
            String str = "childrens:";
            for (TreeNode treeNode : children) {
                str += treeNode.getId() + "_";
            }
            return "TreeNode{" + "id=" + id + ", parentId=" + parentId + str + ", text=" + text + ", leaf=" + leaf + ", sort=" + sort + ", attributes=" + attributes + '}';
        } else {
            return "TreeNode{" + "id=" + id + ", parentId=" + parentId + ", text=" + text + ", leaf=" + leaf + ", sort=" + sort + ", attributes=" + attributes + '}';
        }
    }
}
