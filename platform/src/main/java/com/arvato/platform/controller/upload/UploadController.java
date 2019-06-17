package com.arvato.platform.controller.upload;

import com.arvato.cc.constant.Constants;
import com.arvato.cc.model.CurrentOperation;
import com.arvato.cc.service1.*;
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
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: caol005
 * Date: 15-7-14
 * Time: 下午1:48
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class UploadController extends CommonController {
    private static final Log log = LogFactory.getLog(UploadController.class);


    @Autowired
    private UploadSkuService uploadSkuService;

    @Autowired
    private UploadInvoiceService UploadInvoiceService;

    @Autowired
    private UploadBillService uploadBillService;

    @Autowired
    private GiftDataService giftDataService;

    @Autowired
    private CpdDataService cpdDataService;

    @Autowired
    private CurrentOperationService currentOperationService;

    @Autowired
    private SyncService syncService;


    /**
     * 跳转到Sap发票数据页面
     *
     * @return
     */
    @RequestMapping(value = "/upload/invoice")
    public String uploadInvoiceIndex() {
        return "upload/invoice";
    }

    /**
     * 上传sap发票数据
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/upload/doUploadInvoice.json", method = RequestMethod.POST)
    public void doUploadInvoice(HttpServletRequest request, HttpServletResponse response) {
        //get current uploading user
        response.setContentType("text/html; charset=UTF-8");
        MultipartHttpServletRequest multireq = (MultipartHttpServletRequest) request;
        CommonsMultipartFile file = (CommonsMultipartFile) multireq.getFile("upload");
        String fileName = file.getOriginalFilename();
        String fileType = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();
        String result = UploadInvoiceService.uploadFile(file, fileType, "sap/");

        boolean is_success = Constants.upload_success_msg.equals(result) ? true : false;
       String msg = JSONObject.fromObject(new JsonResult(is_success, result, result)).toString();
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.write(msg);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != out) {
                out.flush();
                out.close();
            }
            currentOperationService.deleteCurrentOperation(Constants.Model.Sap.toString());
        }
    }


    /**
     * 跳转到Sku数据页面
     *
     * @return
     */
    @RequestMapping(value = "/upload/sku")
    public String uploadSkuIndex() {
        return "upload/sku";
    }


    /**
     * 上传sap发票数据
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/upload/doUploadSku.json", method = RequestMethod.POST)
    public void doUploadSku(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html; charset=UTF-8");
        MultipartHttpServletRequest multireq = (MultipartHttpServletRequest) request;
        CommonsMultipartFile file = (CommonsMultipartFile) multireq.getFile("upload");
        String fileName = file.getOriginalFilename();
        String fileType = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();
        String result = uploadSkuService.uploadFile(file, fileType, "sku/");

        boolean is_success = Constants.upload_success_msg.equals(result) ? true : false;
        String msg = JSONObject.fromObject(new JsonResult(is_success, result, result)).toString();
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.write(msg);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != out) {
                out.flush();
                out.close();
            }
            currentOperationService.deleteCurrentOperation(Constants.Model.Sku.toString());
        }
    }


    /**
     * 跳转到发货单页面
     *
     * @return
     */
    @RequestMapping(value = "/upload/bill")
    public String uploadBillIndex() {
        return "upload/bill";
    }

    /**
     * 上传发货单数据
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/upload/doUploadBill.json", method = RequestMethod.POST)
    public void doUploadBill(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html; charset=UTF-8");
        //get current uploading user
        MultipartHttpServletRequest multireq = (MultipartHttpServletRequest) request;
        CommonsMultipartFile file = (CommonsMultipartFile) multireq.getFile("upload");
        String fileName = file.getOriginalFilename();
        String fileType = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();
        String result = uploadBillService.uploadFile(file, fileType, "bill/");


        boolean is_success = Constants.upload_success_msg.equals(result) ? true : false;
        String msg = JSONObject.fromObject(new JsonResult(is_success, result, result)).toString();
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.write(msg);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != out) {
                out.flush();
                out.close();
            }
            currentOperationService.deleteCurrentOperation(Constants.Model.Bill.toString());
        }
    }



    /**
     * 跳转CPD页面
     *
     * @return
     */
    @RequestMapping(value = "/upload/cpd")
    public String uploadCpdIndex() {
        return "upload/cpd";
    }

    /**
     * 上传CPD数据
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/upload/doUploadCpd.json", method = RequestMethod.POST)
    public void doUploadCpd(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html; charset=UTF-8");
        MultipartHttpServletRequest multireq = (MultipartHttpServletRequest) request;
        CommonsMultipartFile file = (CommonsMultipartFile) multireq.getFile("upload");
        String fileName = file.getOriginalFilename();
        String fileType = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();
        String result = cpdDataService.uploadFile(file, fileType, "Cpd/");

        boolean is_success = Constants.upload_success_msg.equals(result) ? true : false;
         String msg = JSONObject.fromObject(new JsonResult(is_success, result, result)).toString();
        try {
            response.getWriter().write(msg);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            currentOperationService.deleteCurrentOperation(Constants.Model.CPD.toString());
        }
    }

    @RequestMapping(value = "/upload/doDeleteCpdCurrentOperationByUser.json", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult giftDeleteCpdOperationByUser(HttpServletRequest request) {
        String userName= UserSecurityUtil.getCurrentUsername();
        try {
            boolean isNotExist = currentOperationService.getCurrentOperationLogByUser(Constants.Model.CPD.toString(), userName);
            if (!isNotExist) {
                currentOperationService.deleteCurrentOperation(Constants.Model.CPD.toString(),userName);
            }else{
                return new JsonResult(false, "",  "当前没有被你锁住的操作，无需解锁");
            }
        }catch (Exception ex){
            ex.printStackTrace();
            log.debug("unlock CpdOperation wrong:"+ex.getMessage());
            return new JsonResult(false,"","解锁失败");
        }
        return new JsonResult(true,"","解锁成功");
    }
    @RequestMapping(value = "/upload/doDeleteBillCurrentOperationByUser.json", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult giftDeleteBillOperationByUser(HttpServletRequest request) {
        String userName= UserSecurityUtil.getCurrentUsername();
        try {
            boolean isNotExist = currentOperationService.getCurrentOperationLogByUser(Constants.Model.Bill.toString(), userName);
            if (!isNotExist) {
                currentOperationService.deleteCurrentOperation(Constants.Model.Bill.toString(),userName);
            }else{
                return new JsonResult(false, "",  "当前没有被你锁住的操作，无需解锁");
            }
        }catch (Exception ex){
            ex.printStackTrace();
            log.debug("unlock BillOperation wrong:"+ex.getMessage());
            return new JsonResult(false,"","解锁失败");
        }
        return new JsonResult(true,"","解锁成功");
    }

    @RequestMapping(value = "/upload/doDeleteInvoiceCurrentOperationByUser.json", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult giftDeleteInvoiceOperationByUser(HttpServletRequest request) {
        String userName= UserSecurityUtil.getCurrentUsername();
        try {
            boolean isNotExist = currentOperationService.getCurrentOperationLogByUser(Constants.Model.Sap.toString(), userName);
            if (!isNotExist) {
                currentOperationService.deleteCurrentOperation(Constants.Model.Sap.toString(),userName);
            }else{
                return new JsonResult(false, "",  "当前没有被你锁住的操作，无需解锁");
            }
        }catch (Exception ex){
            ex.printStackTrace();
            log.debug("unlock SapOperation wrong:"+ex.getMessage());
            return new JsonResult(false,"","解锁失败");
        }
        return new JsonResult(true,"","解锁成功");
    }

    /**
     * 跳转物料页面
     *
     * @return
     */
    @RequestMapping(value = "/upload/gift")
    public String uploadGiftIndex() {
        return "upload/gift";
    }



    /**
     * 上传物料数据
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/upload/doUploadGift.json", method = RequestMethod.POST)
    public void doUploadGift(HttpServletRequest request, HttpServletResponse response) {
            MultipartHttpServletRequest multireq = (MultipartHttpServletRequest) request;
            CommonsMultipartFile file = (CommonsMultipartFile) multireq.getFile("upload");
            String fileName = file.getOriginalFilename();
            String fileType = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();
           String result = giftDataService.uploadFile(file, fileType, "Gift/");
            response.setContentType("text/html; charset=UTF-8");
            boolean is_success = Constants.upload_success_msg.equals(result) ? true : false;
            String msg = JSONObject.fromObject(new JsonResult(is_success, result, result)).toString();
            try {
                response.getWriter().write(msg);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                currentOperationService.deleteCurrentOperation(Constants.Model.Gift.toString());
            }
    }

    @RequestMapping(value = "/upload/validateGiftUploadCurrentOperation.json", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult validateGiftUploadCurrentOperation(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html; charset=UTF-8");
        try{
            String msg= syncService.syncGift();
            if (!Validate.isNullOrEmpty(msg)){
                return new JsonResult(false, "",msg);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new JsonResult(true, "",  "");
    }

    @RequestMapping(value = "/upload/validateSkuUploadCurrentOperation.json", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult validateSkuUploadCurrentOperation(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html; charset=UTF-8");
        try{
            String msg= syncService.syncSku();
            if (!Validate.isNullOrEmpty(msg)){
                return new JsonResult(false, "",msg);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new JsonResult(true, "",  "");
    }

    @RequestMapping(value = "/upload/validateInvoiceUploadCurrentOperation.json", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult validateInvoiceUploadCurrentOperation(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html; charset=UTF-8");
        try{
            String msg= syncService.syncInvoice();
            if (!Validate.isNullOrEmpty(msg)){
                return new JsonResult(false, "",msg);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new JsonResult(true, "",  "");
    }

    @RequestMapping(value = "/upload/validateCpdUploadCurrentOperation.json", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult validateCpdUploadCurrentOperation(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html; charset=UTF-8");
        try{
            String msg= syncService.syncCpd();
            if (!Validate.isNullOrEmpty(msg)){
                return new JsonResult(false, "",msg);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new JsonResult(true, "",  "");
    }

    @RequestMapping(value = "/upload/validateBillUploadCurrentOperation.json", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult validateBillUploadCurrentOperation(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html; charset=UTF-8");
        try{
            String msg= syncService.syncBill();
            if (!Validate.isNullOrEmpty(msg)){
                return new JsonResult(false, "",msg);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new JsonResult(true, "",  "");
    }

}
