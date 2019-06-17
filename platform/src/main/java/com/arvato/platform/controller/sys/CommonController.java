/**
 * Copyright (c) 2000-2010 arvato systems (Shanghai)
 *
 * $Id: CommonController.java 2011-5-19 21:55:14 Justin $
 */
package com.arvato.platform.controller.sys;

import com.arvato.cc.util.ExtPageResult;
import com.arvato.cc.util.PaginatedList;
import com.arvato.cc.util.PaginatedListFactory;
import com.arvato.jdf.util.MessageHelper;
import com.arvato.jdf.web.mvc.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Justin
 */
public class CommonController extends BaseController {

    @Autowired
    protected PaginatedListFactory paginatedListFactory;

    protected ExtPageResult extPageResult(Object result) {
        return new ExtPageResult(true, MessageHelper.getString(defautlJsonResultSuccessMsgKey, defautlJsonResultSuccessMsg), result);
    }

    @ModelAttribute("base")
    public String basePath(HttpServletRequest request) {
        return super.basePath(request);
    }

    protected PaginatedList getPaginatedList(HttpServletRequest request) {
        return paginatedListFactory.getPaginatedList(request);
    }

}
