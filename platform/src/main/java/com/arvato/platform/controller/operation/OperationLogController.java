package com.arvato.platform.controller.operation;

import com.arvato.cc.service1.OperationLogService;
import com.arvato.jdf.dao.PropertyFilter;
import com.arvato.jdf.web.json.JsonResult;
import com.arvato.platform.controller.sys.CommonController;
import com.arvato.platform.util.ExtFilter;
import com.arvato.platform.util.ExtHelper;
import com.arvato.platform.util.ExtPageHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 * User: caol005
 * Date: 15-8-10
 * Time: 上午9:14
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class OperationLogController extends CommonController {
    private static final Log log = LogFactory.getLog(OperationLogController.class);

    @Autowired
    private OperationLogService operationLogService;

    @RequestMapping(value = "/operationHistory/lastedOperationLog.json")
    @ResponseBody
    public JsonResult list(HttpServletRequest request) {
        String operationType = request.getParameter("operationType");
        return this.jsonResult(operationLogService.findLastedOperationHistory(operationType));
    }

}
