/**
 * Copyright (c) 2000-2010 arvato systems (Shanghai)
 *
 * $Id: ExtHelper.java 2011-4-26 20:16:44 Justin $
 */
package com.arvato.platform.util;

import com.arvato.cc.constant.Constants;
import com.arvato.cc.model.TreeNode;
import com.arvato.jdf.dao.Property.MatchType;
import com.arvato.jdf.dao.PropertyFilter;
import com.arvato.jdf.dao.PropertyFilter.JunctionType;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Justin
 */
public class ExtHelper {

    private static final Logger logger = LoggerFactory.getLogger(ExtHelper.class);

    public static ExtFilter[] getFilters(HttpServletRequest request) {
        String jsonString = request.getParameter("filter");
        if (jsonString == null) {
            return null;
        }

        try {
            return new ObjectMapper().readValue(jsonString, ExtFilter[].class);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    public static PropertyFilter convertToPropertyFilter(ExtFilter[] extFilters) {
        if (extFilters == null) {
            return null;
        } else {
            PropertyFilter propertyFilter = new PropertyFilter(JunctionType.AND);
            for (int i = 0; i < extFilters.length; i++) {
                if (extFilters[i].getType().equals(Constants.ComparisonType.COMPARISON_TYPE_LIST.name())) {
                    String filterValueStr = extFilters[i].getValue();
                    String[] filterValueArray = StringUtils.split(filterValueStr, ',');

                    if (filterValueArray.length > 1) {
                        for (String filterValue : filterValueArray) {
                            propertyFilter.add(extFilters[i].getField(), filterValue);
                        }
                       
                        propertyFilter.toChild(JunctionType.OR, extFilters[i].getField());
                    } else {
                        propertyFilter.add(extFilters[i].getField(), extFilters[i].getValue(), convertComparisonToMatchType(extFilters[i].getComparison()));
                    }

                } else {
                    propertyFilter.add(extFilters[i].getField(), convertToCorrespondingValue(extFilters[i]), convertComparisonToMatchType(extFilters[i].getComparison()));
                }

            }
            return propertyFilter;
        }
    }

    private static Object convertToCorrespondingValue(ExtFilter extFilter) {
        if (Constants.ComparisonType.COMPARISON_TYPE_BOOLEAN.name().equals(extFilter.getType())) {
            return Boolean.valueOf(extFilter.getValue());
        } else if (Constants.ComparisonType.COMPARISON_TYPE_NUMERIC.name().equals(extFilter.getType())) {
            return Long.valueOf(extFilter.getValue());
        } else {
            return extFilter.getValue();
        }
    }

    /**
     * 将Ext Grid的比较运算符转换成MatchType类型
     * @param sComparison lt,gt,eq etc.
     * @return MatchType
     */
    private static MatchType convertComparisonToMatchType(String sComparison) {
        if (Constants.ComparisonType.COMPARISON_LT.name().equals(sComparison)) {
            return MatchType.LT;
        } else if (Constants.ComparisonType.COMPARISON_GT.name().equals(sComparison)) {
            return MatchType.GT;
        } else {
            return MatchType.EQ;
        }
    }

    public static List<ExtTreeNode> convertToExtTreeNodes(List<TreeNode> nodes) {

        if (CollectionUtils.isNotEmpty(nodes)) {

            List<ExtTreeNode> rsNodes = new ArrayList<ExtTreeNode>();
            for (TreeNode node : nodes) {

                ExtTreeNode extNode = new ExtTreeNode();

                extNode.setId(node.getId());
                extNode.setIndex(node.getSort());
                extNode.setLeaf(node.isLeaf());
                extNode.setText(node.getText());
                extNode.setExpanded(false);

                if (node.getAttributes() != null) {

                    Object cls = node.getAttribute("cls");
                    if (cls != null) {
                        extNode.setIconCls(cls.toString());
                    }

                    Object checked = node.getAttribute("checked");
                    if(checked != null){
                       extNode.setChecked(Boolean.valueOf(checked.toString()));
                    }
                }
                // extNode.setChildNodes(convertToExtTreeNodes(node.getChildren()));

                extNode.putExtraAttributes(node.getAttributes());

                rsNodes.add(extNode);
            }
            return rsNodes;
        }
        return null;
    }

    public static List<TreeNode> convertToTreeNodes(List<ExtTreeNode> nodes) {
        if (CollectionUtils.isNotEmpty(nodes)) {
            List<TreeNode> rsNodes = new ArrayList<TreeNode>();
            for (ExtTreeNode node : nodes) {
                TreeNode treeNode = new TreeNode();
                treeNode.setId(node.getId());
                treeNode.setSort(node.getIndex());
                treeNode.setLeaf(node.isLeaf());
                treeNode.setText(node.getText());
                treeNode.setParentId(node.getParentId());
                rsNodes.add(treeNode);
            }
            return rsNodes;
        }
        return null;
    }

    public static List<ExtTreeNode> getNodesByDepth(List<ExtTreeNode> nodes, int depth) {
        List<ExtTreeNode> rsNode = new ArrayList<ExtTreeNode>();
        if (CollectionUtils.isNotEmpty(nodes)) {

            for (ExtTreeNode node : nodes) {
                if (node.getDepth().equals(depth)) {
                    rsNode.add(node);
                }
            }
        }
        return rsNode;
    }
}
