package com.arvato.platform.controller.gift;

import com.arvato.cc.constant.Constants;
import com.arvato.cc.form.ComboStore;
import com.arvato.cc.model.*;
import com.arvato.cc.service1.*;
import com.arvato.cc.util.CommonHelper;
import com.arvato.cc.util.UserSecurityUtil;
import com.arvato.cc.util.Validate;
import com.arvato.jdf.dao.PropertyFilter;
import com.arvato.jdf.web.json.JsonResult;
import com.arvato.platform.controller.sys.CommonController;
import com.arvato.platform.util.ExtFilter;
import com.arvato.platform.util.ExtHelper;
import com.arvato.platform.util.ExtPageHelper;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.StaleObjectStateException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.regex.Pattern;

@Controller
public class GiftController extends CommonController {
    private static final Log log = LogFactory.getLog(GiftController.class);

    @Autowired
    private GiftDataService giftDataService;

    @Autowired
    private CurrentOperationService currentOperationService;

    @Autowired
    private OperationLogService operationLogService;

    @Autowired
    private UserService userService;
    @RequestMapping(value = "/matrn/index")
    public String uploadIndex() {
        return "gift/gift";
    }


    @RequestMapping(value = "/gift/list.json")
    @ResponseBody
    public JsonResult list(HttpServletRequest request) {
        ExtFilter[] extFilters = ExtHelper.getFilters(request);
        PropertyFilter propertyFilter = null;
        if (extFilters != null) {
            propertyFilter = ExtHelper.convertToPropertyFilter(extFilters);
        } else {
            propertyFilter = new PropertyFilter();
        }
        return extPageResult(giftDataService.findPropertyFilter(ExtPageHelper.getFromRequest(request), propertyFilter));
    }

    /**
     * 保存和修改物料
     * @param request
     * @return
     */
    @RequestMapping(value = "/gift/saveGift.json", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult saveGift(HttpServletRequest request) {
        boolean result = false;
        try {
            String id = request.getParameter("skuId");
            String skuNo = request.getParameter("skuNo");
            String name = request.getParameter("name");
            String memo = request.getParameter("memo");
            String recordSkuNo=request.getParameter("recordSkuNo");
            String brand=request.getParameter("brand");
          //  String version=request.getParameter("version");
            String null_error_msg = Constants.null_error_msg.toString();
            if(Validate.isNullOrEmpty(brand))  {
                return new JsonResult(false, "", String.format(null_error_msg, "Brand"));
            }

            if (Validate.isNullOrEmpty(skuNo)) {
                return new JsonResult(false, "", String.format(null_error_msg, "SAP物料号"));
            } else if(!Pattern.matches("^[0-9a-zA-Z]+", skuNo)){
                return new JsonResult(false,"","SAP物料号只能由数字和字母组成");
            }
            if (!Validate.isNullOrEmpty(recordSkuNo)){
                if (!skuNo.equals(recordSkuNo)){
                    GiftData giftData= giftDataService.findSkuNo(skuNo);
                    if (!Validate.isNullOrEmpty(giftData)){
                        return new JsonResult(false,"","SAP物料号不能重复");
                    }
                }
            }
            if (Validate.isNullOrEmpty(name)) {
                return new JsonResult(false, "", String.format(null_error_msg, "商品名称"));
            }

            if (Validate.isNullOrEmpty(memo)) {
                return new JsonResult(false, "", String.format(null_error_msg, "客户备注简写"));
            }

            GiftData giftData =new GiftData();
            if (Validate.isNullOrEmpty(id)) {
                //do save
                //将上传的操作记录到历史操作表中
                OperationHistory operationHistory = operationLogService.generateOperationHistory("", Constants.OperationType.ADD_GIFT.toString(), Constants.Model.Gift.toString());
                operationLogService.saveOrUpdate(operationHistory);
            } else {
                //do modify
                giftData = giftDataService.findById(Integer.valueOf(id));
                if (Validate.isNullOrEmpty(giftData)){
                    return new JsonResult(false, "",  "该记录已被删除");
                }
                OperationHistory operationHistory = operationLogService.generateOperationHistory("", Constants.OperationType.MOD_GIFT.toString(), Constants.Model.Gift.toString());
                operationLogService.saveOrUpdate(operationHistory);
            }
            giftData.setBrand(brand);
            giftData.setSkuNo(skuNo);
            giftData.setName(name);
            giftData.setMemo(memo);
            giftData.setStatus(Constants.UploadStatus.ACTIVE.toString());
            giftDataService.save(giftData);
            result = true;
        }catch (DataIntegrityViolationException cex){
            cex.printStackTrace();
            return new JsonResult(false, "",  "正在进行操作，请稍后修改");
        }catch (Exception ex) {
            result = false;
            ex.printStackTrace();
        }
        return jsonResult(result);
    }

    @RequestMapping(value = "/gift/findById.json", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult findById(HttpServletRequest request) {
        String id = request.getParameter("id");
        if (Validate.isNullOrEmpty(id)) {
            return jsonResult(false);
        }
        return jsonResult(giftDataService.findById(Integer.valueOf(id)));
    }

    @RequestMapping(value = "/gift/deleteGift.json", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult deleteGift(HttpServletRequest request) {
        String id = request.getParameter("id");
        boolean result = false;
        if (Validate.isNullOrEmpty(id)) {
            result = false;
            return jsonResult(result);
        }
        try {
            giftDataService.deleteGift(id);
            OperationHistory operationHistory = operationLogService.generateOperationHistory("", Constants.OperationType.DEL_GIFT.toString(), Constants.Model.Gift.toString());
            operationLogService.saveOrUpdate(operationHistory);
            result = true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return jsonResult(result);
    }

    @RequestMapping(value = "/gift/findBrand.json")
    @ResponseBody
    public JsonResult findBrand(HttpServletRequest request) {
        List<ComboStore> list = giftDataService.findBrand();
        return jsonResult(list);
    }

    @RequestMapping(value = "/gift/findCategory.json")
    @ResponseBody
    public JsonResult findCategory(HttpServletRequest request) {
        List<ComboStore> list = giftDataService.findCategory();
        return jsonResult(list);
    }

    @RequestMapping(value = "/gift/findSkuNo.json",method = RequestMethod.POST)
    @ResponseBody
    public JsonResult findSkuNo(HttpServletRequest request) {
        String skuNo = request.getParameter("inputSkuNo");
        try {
            skuNo = new String(skuNo.getBytes("iso-8859-1"),"utf-8") ;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        GiftData giftData= giftDataService.findSkuNo(skuNo);
        if (!Validate.isNullOrEmpty(giftData)){
            return new JsonResult(false,"","SAP物料号不能重复");
        }else{
            return jsonResult(true);
        }

    }

    @RequestMapping(value = "/gift/findSkuNoRemoveMy.json",method = RequestMethod.POST)
    @ResponseBody
    public JsonResult findSkuNoRemoveMy(HttpServletRequest request) {
        String inputSkuNo = request.getParameter("inputSkuNo");
        String recordSkuNo = request.getParameter("recordSkuNo");
        try {
            inputSkuNo = new String(inputSkuNo.getBytes("iso-8859-1"),"utf-8") ;
            recordSkuNo = new String(recordSkuNo.getBytes("iso-8859-1"),"utf-8") ;
            if(inputSkuNo.equals(recordSkuNo)){
                return jsonResult(true);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        GiftData giftData= giftDataService.findSkuNo(inputSkuNo);
        if (!Validate.isNullOrEmpty(giftData)){
            return new JsonResult(false,"","SAP物料号不能重复");
        }else{
            return jsonResult(true);
        }
    }

    @RequestMapping(value = "/gift/doCurrentOperation.json", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult giftOperation(HttpServletRequest request) {
        try {
            CurrentOperation currentOperation=null;
            List<CurrentOperation> currentOperationList=null;
            String operationOp=null;
            User user=null;
            String operationType=request.getParameter("operationType");
            currentOperation = currentOperationService.getCurrentOperation(Constants.Model.Gift.toString(), Constants.Function.upload.toString());
            if (!Validate.isNullOrEmpty(currentOperation)){
                operationOp=currentOperation.getOperateOp();
                user=userService.findOmsUserByOmsUserId(operationOp);
                return new JsonResult(false, "",operationOp+ "正在进行上传，请稍后操作，如有紧急，请联系"+operationOp+"，邮箱是："+user.getUserEmail());
            }
            if ("modify".equals(operationType)){
                String sysId=request.getParameter("sysId");
                currentOperationList = currentOperationService.getCurrentOperationByModel(Constants.Model.Gift.toString());
                if (!Validate.isNullOrEmpty(currentOperationList)) {
                    for(int i=0;i<currentOperationList.size();i++){
                        currentOperation=currentOperationList.get(i);
                        if ("delete".equals(currentOperation.getSubModel())){
                            for (int j=0;j<currentOperation.getSysId().split(";").length;j++){
                                if (sysId.equals(currentOperation.getSysId().split(";")[j])){
                                    operationOp=currentOperation.getOperateOp();
                                    user=userService.findOmsUserByOmsUserId(operationOp);
                                    return new JsonResult(false, "",  operationOp+"正在进行删除，请稍后操作,如有紧急，请联系"+operationOp+"，邮箱是："+user.getUserEmail());
                                }
                            }
                        }else if("modify".equals(currentOperation.getSubModel())){
                            if(sysId.equals(currentOperation.getSysId())){
                                operationOp=currentOperation.getOperateOp();
                                user=userService.findOmsUserByOmsUserId(operationOp);
                                return new JsonResult(false, "", operationOp+"正在修改该记录，请稍后操作,如有紧急，请联系"+operationOp+"，邮箱是："+user.getUserEmail());
                            }
                        }
                    }
                }
                currentOperationService.saveCurrentOperation(Constants.Model.Gift.toString(),operationType,sysId);

            }else if("save".equals(operationType)){
                currentOperation = currentOperationService.getCurrentOperation(Constants.Model.Gift.toString(), Constants.Function.save.toString());
                if (!Validate.isNullOrEmpty(currentOperation)) {
                    operationOp=currentOperation.getOperateOp();
                    user=userService.findOmsUserByOmsUserId(operationOp);
                    return new JsonResult(false, "",operationOp+"正在添加，请稍后操作,如有紧急，请联系"+operationOp+"，邮箱是："+user.getUserEmail());
                }
                currentOperationService.saveCurrentOperation(Constants.Model.Gift.toString(),Constants.Function.save.toString());
                // currentOperationService.deleteCurrentOperation(Constants.Model.Gift.toString());
            }else if ("delete".equals(operationType)){
                String id=request.getParameter("id");
                String ids[]=id.split(";");
                for(int i=0;i<ids.length;i++){
                    currentOperationList = currentOperationService.getCurrentOperationByModel(Constants.Model.Gift.toString());
                    if (!Validate.isNullOrEmpty(currentOperationList)){
                        for (int j=0;j<currentOperationList.size();j++) {
                            currentOperation = currentOperationList.get(j);
                            if (!Validate.isNullOrEmpty(currentOperation.getSysId())){
                                if ("delete".equals(currentOperation.getSubModel())){
                                    for(int k=0;k<currentOperation.getSysId().split(";").length;k++){
                                        if (currentOperation.getSysId().split(";")[k].equals(ids[i])){
                                            operationOp=currentOperation.getOperateOp();
                                            user=userService.findOmsUserByOmsUserId(operationOp);
                                            return new JsonResult(false, "",operationOp+"正在删除该记录，请稍后操作,如有紧急，请联系"+operationOp+"，邮箱是："+user.getUserEmail());
                                        }
                                    }
                                }else if("modify".equals(currentOperation.getSubModel())){
                                    if (ids[i].equals(currentOperation.getSysId())){
                                        operationOp=currentOperation.getOperateOp();
                                        user=userService.findOmsUserByOmsUserId(operationOp);
                                        return new JsonResult(false, "", operationOp+"正在修改该记录，请稍后操作,如有紧急，请联系"+operationOp+"，邮箱是："+user.getUserEmail());
                                    }
                                }
                            }
                        }
                    }
                }
                currentOperationService.saveCurrentOperation(Constants.Model.Gift.toString(),Constants.Function.delete.toString(),id);
            }
        }catch (Exception ex){
            ex.printStackTrace();
            log.debug("query or save operation wrong :"+ex.getMessage());
            return new JsonResult(false,"","请求发生失败，请稍后重试");
        }
        return new JsonResult(true,"","");
    }

    @RequestMapping(value = "/gift/doDeleteCurrentOperation.json", method = RequestMethod.POST)
    @ResponseBody
        public JsonResult giftDeleteOperation(HttpServletRequest request) {
        try {
        String operationType=request.getParameter("operationType");
        if ("modify".equals(operationType)){
            String sysId=request.getParameter("sysId");
            boolean isNotExist = currentOperationService.getCurrentOperationLogBySysId(Constants.Model.Gift.toString(), sysId);
            if (!isNotExist) {
                currentOperationService.deleteCurrentOperationBySysId(Constants.Model.Gift.toString(),sysId);
            }
        }else if("save".equals(operationType)){
            boolean isNotExist = currentOperationService.getCurrentOperationLog(Constants.Model.Gift.toString(),Constants.Function.save.toString());
            if (!isNotExist) {
                currentOperationService.deleteCurrentOperationBySubModel(Constants.Model.Gift.toString(), Constants.Function.save.toString());
            }
        }else if ("delete".equals(operationType)){
            boolean isNotExist = currentOperationService.getCurrentOperationLog(Constants.Model.Gift.toString(),Constants.Function.delete.toString());
            if (!isNotExist) {
                currentOperationService.deleteCurrentOperationBySubModel(Constants.Model.Gift.toString(), Constants.Function.delete.toString());
            }
        }
        }catch (Exception ex){
            ex.printStackTrace();
            log.debug("delete operation wrong :"+ex.getMessage());
            return new JsonResult(false,"","请求发生失败，请稍后重试");
        }
        return this.jsonResult(true);
    }

    @RequestMapping(value = "/gift/doDeleteGiftCurrentOperationByUser.json", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult giftDeleteOperationByUser(HttpServletRequest request) {
       String userName= UserSecurityUtil.getCurrentUsername();
        try {
            boolean isNotExist = currentOperationService.getCurrentOperationLogByUser(Constants.Model.Gift.toString(), userName);
            if (!isNotExist) {
                currentOperationService.deleteCurrentOperation(Constants.Model.Gift.toString(),userName);
            }else{
                return new JsonResult(false, "",  "当前没有被你锁住的操作，无需解锁");
            }
        }catch (Exception ex){
            ex.printStackTrace();
            log.debug("unlock GiftOperation wrong:"+ex.getMessage());
            return new JsonResult(false,"","解锁失败");
        }
        return new JsonResult(true,"","解锁成功");
    }
}
