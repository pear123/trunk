/**
 * Copyright (c) 2000-2010 arvato systems (Shanghai)
 *
 * $Id: ExtPageResult.java 2011-5-19 21:32:14 Justin $
 */
package com.arvato.cc.util;

import com.arvato.jdf.dao.Page;
import com.arvato.jdf.web.json.JsonResult;

/**
 * @author Justin
 */
public class ExtPageResult extends JsonResult {

    public ExtPageResult(boolean success, String message, Object result) {
        super(success, message, result);
    }

    /**
     * @return the result
     */

    public Object getResult() {
        if (isPageResult(super.getResult())) {
            Page page = (Page) super.getResult();
            return page.getResult();
        }
        return getResult();
    }

    public long getRecordCount() {
        if (isPageResult(super.getResult())) {
            Page page = (Page) super.getResult();
            return page.getRecordCount();
        }
        return -1;
    }

    private boolean isPageResult(Object result) {
        if (result instanceof Page) {
            return true;
        }
        return false;
    }
}
