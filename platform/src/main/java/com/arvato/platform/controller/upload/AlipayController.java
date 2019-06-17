package com.arvato.platform.controller.upload;

import com.arvato.cc.constant.Constants;
import com.arvato.cc.log.LogMessageUtil;
import com.arvato.cc.model.CurrentOperation;
import com.arvato.cc.service1.*;
import com.arvato.cc.util.CommonHelper;
import com.arvato.cc.util.UserSecurityUtil;
import com.arvato.cc.util.Validate;
import com.arvato.jdf.web.json.JsonResult;
import com.arvato.platform.controller.sys.CommonController;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: caol005
 * Date: 15-7-14
 * Time: 下午1:48
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class AlipayController extends CommonController {
    private static final Log log = LogFactory.getLog(AlipayController.class);
    private static LogMessageUtil log_msg = new LogMessageUtil("ALIPAY");

    @Autowired
    private AlipayService alipayService;

    @Autowired
    private CurrentOperationService currentOperationService;

    @Autowired
    private SyncService syncService;

    @RequestMapping(value = "/upload/alipay")
    public String uploadIndex() {
        return "upload/alipay";
    }

    @RequestMapping(value = "/upload/doUploadAlipay.json", method = RequestMethod.POST)
    public void doUploadAlipay(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html; charset=UTF-8");
        MultipartHttpServletRequest multireq = (MultipartHttpServletRequest) request;
        CommonsMultipartFile file = (CommonsMultipartFile) multireq.getFile("upload");
        String fileName = file.getOriginalFilename();
        String fileType = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();
        String result = alipayService.uploadFile(file, fileType, "alipay/");
        boolean is_success = Constants.upload_success_msg.equals(result) ? true : false;
        String msg = JSONObject.fromObject(new JsonResult(is_success, result, result)).toString();
        try {
            response.getWriter().write(msg);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            currentOperationService.deleteCurrentOperation(Constants.Model.Alipay.toString());
        }
    }

    @RequestMapping(value = "/upload/doDeleteAlipayCurrentOperationByUser.json", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult giftDeleteOperationByUser(HttpServletRequest request) {
        String userName= UserSecurityUtil.getCurrentUsername();
        try {
            boolean isNotExist = currentOperationService.getCurrentOperationLogByUser(Constants.Model.Alipay.toString(), userName);
            if (!isNotExist) {
                currentOperationService.deleteCurrentOperation(Constants.Model.Alipay.toString(),userName);
            }else{
                return new JsonResult(false, "",  "当前没有被你锁住的操作，无需解锁");
            }
        }catch (Exception ex){
            ex.printStackTrace();
            log.debug("unlock AlipayOperation wrong:"+ex.getMessage());
            return new JsonResult(false,"","解锁失败");
        }
        return new JsonResult(true,"","解锁成功");
    }

    @RequestMapping(value = "/upload/validateAlipayUploadCurrentOperation.json", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult validateAlipayUploadCurrentOperation(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html; charset=UTF-8");
        try{
            String msg= syncService.syncAlipay();
            if (!Validate.isNullOrEmpty(msg)){
                return new JsonResult(false, "",msg);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new JsonResult(true, "",  "");
    }

}
