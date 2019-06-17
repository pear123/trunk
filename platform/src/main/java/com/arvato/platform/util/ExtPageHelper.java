/**
 * Copyright (c) 2000-2010 arvato systems (Shanghai)
 *
 * $Id: ExtPageHelper.java 2011-6-8 10:20:41 Justin $
 */
package com.arvato.platform.util;

import com.arvato.jdf.dao.Page;
import com.arvato.jdf.dao.PropertyOrder;
import com.arvato.jdf.util.PageHelper;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Justin
 */
public class ExtPageHelper {
    
    public static final Logger logger = LoggerFactory.getLogger(ExtPageHelper.class);
    public static final String EXT_SORT_KEY = "sort";
    
    public static Page getFromRequest(HttpServletRequest request) {
        Page page = PageHelper.getFromRequest(request);
        String sortStr = request.getParameter(EXT_SORT_KEY);
        
        if (StringUtils.isNotBlank(sortStr)) {
            try {
                ExtSort[] extSorts = new ObjectMapper().readValue(sortStr, ExtSort[].class);
                List<PropertyOrder> orders = new ArrayList<PropertyOrder>(extSorts.length);
                for (ExtSort extSort : extSorts) {
                    orders.add(extSort.toPropertyOrder());
                }
                page.setOrders(orders.toArray(new PropertyOrder[orders.size()]));
            } catch (IOException ex) {
                logger.warn(ex.getMessage());
            }            
        }
        return page;
    }
}
