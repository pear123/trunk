/**
 * Copyright (c) 2000-2010 arvato systems (Shanghai)
 *
 * $Id: PageHelper.java 2011-3-14 14:50:51 Justin $
 */
package com.arvato.cc.util;

import com.arvato.jdf.dao.Page;
import com.arvato.jdf.util.MessageHelper;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * 为了使Page对象仅封装分页逻辑，而建立Page工具类
 * @author Justin
 */
public class PageHelper {

    /**
     * logger
     */
    public static final Logger logger = LoggerFactory.getLogger(com.arvato.jdf.util.PageHelper.class);
    /**
     * 获取当前页数的request键值
     */
    public static final String PAGENO_REQUEST_KEY = "page";
    /**
     * 获取每页的结果集数量的request键值
     */
    public static final String PAGESIZE_REQUEST_KEY = "limit";
    /**
     * 获取排序信息的request键值
     */
    public static final String ORDER_REQUEST_KEY = "order";
    /**
     * 获取默认当前页数的属性文件键值
     */
    public static final String DEFAULT_PAGENO_PROPERTY_KEY = "paging.default.pageno";
    /**
     * 获取默认每页的结果集数量的属性文件键值
     */
    public static final String DEFAULT_PAGESIZE_PROPERTY_KEY = "paging.default.pagesize";
    /**
     * 获取默认每页的最大结果集数量的属性文件键值
     */
    public static final String DEFAULT_MAX_PAGESIZE_PROPERTY_KEY = "paging.default.max.pagesize";
    /**
     * 获取默认每页的允许的结果集数量的属性文件键值
     */
    public static final String DEFAULT_AVAILABLE_PAGESIZES_PROPERTY_KEY = "paging.default.available.pagesizes";
    /**
     * 获取默认排序字段的属性文件键值
     */
    public static final String DEFAULT_ORDERS_PROPERTY_KEY = "paging.default.orders";
    /**
     * 允许每页结果集数量的分割符
     */
    public static final String PAGING_PAGESIZES_SEP = ",";

    /**
     * 从系统属性中创建Page对象
     * @return Page
     */
    public static Page create() {
        // create new object
        Page page = new Page();

        String pageno = MessageHelper.getString(DEFAULT_PAGENO_PROPERTY_KEY);
        String pagesize = MessageHelper.getString(DEFAULT_PAGESIZE_PROPERTY_KEY);
        String order = "updateTime";

        setPageStr(page, pageno, pagesize, order);

        return page;
    }

    /**
     * 
     * @param request
     * @return
     */
    public static Page getFromRequest(HttpServletRequest request) {
        // create from properties
        Page page = create();

        String pageno = request.getParameter(PAGENO_REQUEST_KEY);
        String pagesize = request.getParameter(PAGESIZE_REQUEST_KEY);
        String order = request.getParameter(ORDER_REQUEST_KEY);

//        if (!isAvailablePagesize(pagesize)) {
//            pagesize = null;
//        }

        setPageStr(page, pageno, pagesize, order);

        return page;
    }

    /**
     * 判断是否允许的pagesize(结果数量)的设置
     * @param pagesizeStr pagesize字符
     * @return boolean 为真时是允许的pagesize设置
     */
    private static boolean isAvailablePagesize(String pagesizeStr) {
        String availablePagesizesStr = MessageHelper.getString(DEFAULT_AVAILABLE_PAGESIZES_PROPERTY_KEY);
        String maxPagesizeStr = MessageHelper.getString(DEFAULT_MAX_PAGESIZE_PROPERTY_KEY);
        boolean availablePagesize = true;
        boolean leMax = true;

        // 如果允许结果数量配置存在，则判断结果数量是否在允许范围内
        if (StringUtils.isNotBlank(availablePagesizesStr)) {
            String[] pagesizeStrs = StringUtils.split(availablePagesizesStr, PAGING_PAGESIZES_SEP);
            if (!ArrayUtils.contains(pagesizeStrs, pagesizeStr)) {
                availablePagesize = false;
            }
        }

        // 如果最大结果数量配置存在，则判断结果数量是否在允许范围内
        if (StringUtils.isNotBlank(maxPagesizeStr) && StringUtils.isNumeric(maxPagesizeStr)) {
            int maxPagesize = Integer.valueOf(maxPagesizeStr);
            int pageSize = Integer.valueOf(pagesizeStr);
            leMax = pageSize <= maxPagesize;
        }

        return availablePagesize && leMax;
    }

    /**
     * 设置配置字符到Page对象
     * @param page 目标Page对象
     * @param pagenoStr 当前页数字符
     * @param pagesizeStr 结果数量字符
     * @param ordersStr 排序字符
     */
    private static void setPageStr(Page page, String pagenoStr, String pagesizeStr, String ordersStr) {
        if (StringUtils.isNotBlank(pagenoStr) && StringUtils.isNumeric(pagenoStr)) {
            page.setPageNo(Integer.valueOf(pagenoStr));
        }

        if (StringUtils.isNotBlank(pagesizeStr) && StringUtils.isNumeric(pagesizeStr)) {
            page.setPageSize(Integer.valueOf(pagesizeStr));
        }

        if (StringUtils.isNotBlank(ordersStr)) {
            page.setOrder(ordersStr);
        }
    }
}
