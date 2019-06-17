package com.arvato.platform.controller.sku;

import com.arvato.cc.constant.Constants;
import com.arvato.cc.form.ComboStore;
import com.arvato.cc.model.CurrentOperation;
import com.arvato.cc.model.OperationHistory;
import com.arvato.cc.model.Sku;
import com.arvato.cc.model.User;
import com.arvato.cc.service1.CurrentOperationService;
import com.arvato.cc.service1.OperationLogService;
import com.arvato.cc.service1.SkuService;
import com.arvato.cc.service1.UserService;
import com.arvato.cc.util.UserSecurityUtil;
import com.arvato.cc.util.Validate;
import com.arvato.jdf.dao.PropertyFilter;
import com.arvato.jdf.web.json.JsonResult;
import com.arvato.platform.controller.sys.CommonController;
import com.arvato.platform.util.ExtFilter;
import com.arvato.platform.util.ExtHelper;
import com.arvato.platform.util.ExtPageHelper;
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
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Sku管理
 * Created with IntelliJ IDEA.
 * User: caol005
 * Date: 15-7-14
 * Time: 下午1:48
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class SkuController extends CommonController {
    private static final Log log = LogFactory.getLog(SkuController.class);

    @Autowired
    private SkuService skuService;

    @Autowired
    private OperationLogService operationLogService;

    @Autowired
    private CurrentOperationService currentOperationService;

    @Autowired
    private UserService  userService;
    @RequestMapping(value = "/sku/index")
    public String uploadIndex() {
        return "sku/sku";
    }


    @RequestMapping(value = "/sku/list.json")
    @ResponseBody
    public JsonResult list(HttpServletRequest request) {
        ExtFilter[] extFilters = ExtHelper.getFilters(request);
        PropertyFilter propertyFilter = null;
        if (extFilters != null) {
            propertyFilter = ExtHelper.convertToPropertyFilter(extFilters);
        } else {
            propertyFilter = new PropertyFilter();
        }
        return extPageResult(skuService.findPropertyFilter(ExtPageHelper.getFromRequest(request), propertyFilter));
    }

    /**
     * 保存和修改sku
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/sku/saveSku.json",method = RequestMethod.POST)
    @ResponseBody
    public JsonResult saveSku(HttpServletRequest request,HttpServletResponse response) {
        boolean result=false;
      try{
            String id = request.getParameter("id");
            String brand = request.getParameter("brand");
            String cid = request.getParameter("category");
            String matnr = request.getParameter("matnr");
            String recordMantr=request.getParameter("recordMantr");
            String tmallPrice = request.getParameter("tmallprice");
            String sapPrice = request.getParameter("sapprice");
          //  String version=request.getParameter("version");

            JsonResult jsonResult = new JsonResult();
            jsonResult.setSuccess(false);

            String null_error_msg = Constants.null_error_msg.toString();
            String format_error_msg = Constants.format_error_msg.toString();

            if(Validate.isNullOrEmpty(brand)){
                jsonResult.setResult(String.format(null_error_msg,"品牌"));
                jsonResult.setMessage(String.format(null_error_msg,"品牌"));
                return this.jsonResult(jsonResult);
            }

            if(Validate.isNullOrEmpty(cid)){
                return new JsonResult(false,"",String.format(null_error_msg,"品类"));
            }

            if(Validate.isNullOrEmpty(matnr)){
                return new JsonResult(false,"",String.format(null_error_msg,"型号"));
            }else if(!Pattern.matches("^[0-9a-zA-Z]+", matnr)){
                return new JsonResult(false, "",  "SKU只能由数字和字母组成");
            }
          if (!Validate.isNullOrEmpty(recordMantr)){
              if (!matnr.equals(recordMantr)) {
                  Sku sku= skuService.findByMatnr(matnr);
                  if (!Validate.isNullOrEmpty(sku)){
                      return new JsonResult(false, "", "SKU不能重复");
                  }
              }
          }

            if(Validate.isNullOrEmpty(tmallPrice)){
                return new JsonResult(false,"",String.format(null_error_msg,"Tmall 价格"));
            }

            Double tmall_price = 0.0;
            if(!Pattern.matches("^[+-]?\\d+(\\.\\d+)?$", tmallPrice)) {
                return new JsonResult(false,"",String.format(format_error_msg,"Tmall 价格"));
            } else {
                tmall_price = new Double(tmallPrice);
            }

            if(Validate.isNullOrEmpty(sapPrice)){
                return new JsonResult(false,"",String.format(null_error_msg,"Sap 价格"));
            }

            Double sap_price = 0.0;
            if(!Pattern.matches("^[+-]?\\d+(\\.\\d+)?$", sapPrice)) {
                return new JsonResult(false,"",String.format(format_error_msg,"Tmall 价格"));
            } else {
                sap_price = new Double(sapPrice);
            }

            Sku sku = new Sku();
            if(Validate.isNullOrEmpty(id)){
                //do save
                OperationHistory operationHistory = operationLogService.generateOperationHistory("", Constants.OperationType.ADD_SKU.toString(), Constants.Model.Sku.toString());
                operationLogService.saveOrUpdate(operationHistory);
            }
            else{
                //do modify
                 sku = skuService.findById(Integer.valueOf(id));
                if (Validate.isNullOrEmpty(sku)){
                    return new JsonResult(false, "",  "该记录已被删除");
                }
                OperationHistory operationHistory = operationLogService.generateOperationHistory("", Constants.OperationType.MOD_SKU.toString(), Constants.Model.Sku.toString());
                operationLogService.saveOrUpdate(operationHistory);
            }
            sku.setBrand(brand);
            sku.setCid(cid);
            sku.setMatnr(matnr);
            sku.setSapprice(sap_price);
            sku.setTmallPrice(tmall_price);
            sku.setStatus(Constants.UploadStatus.ACTIVE.toString());
            skuService.save(sku);
            log.debug("add or mod sku [matnr] :"+matnr+"successful");
            result=true;
        }catch (DataIntegrityViolationException cex){
          cex.printStackTrace();
          return new JsonResult(false, "",  "正在进行操作，请稍后修改");
      }catch (Exception ex){
             result=false;
            ex.printStackTrace();
        }
        return jsonResult(result);
    }

    @RequestMapping(value = "/sku/findById.json",method = RequestMethod.POST)
    @ResponseBody
    public JsonResult findById(HttpServletRequest request) {
        String id = request.getParameter("id");
        if(Validate.isNullOrEmpty(id)){
            return jsonResult(false);
        }
        return jsonResult(skuService.findById(Integer.valueOf(id)));
    }

    /**
     * 删除sku
     * @param request
     * @return
     */
    @RequestMapping(value = "/sku/deleteSku.json",method = RequestMethod.POST)
    @ResponseBody
    public JsonResult deleteSku(HttpServletRequest request) {
        String id = request.getParameter("id");
        boolean result=false;
        if(Validate.isNullOrEmpty(id)){
            result=false;
            return jsonResult(result);
        }
        try{
            skuService.deleteSku(id);
            OperationHistory operationHistory = operationLogService.generateOperationHistory("", Constants.OperationType.DEL_SKU.toString(), Constants.Model.Sku.toString());
            operationLogService.saveOrUpdate(operationHistory);
            result=true;
        } catch (Exception ex){
            ex.printStackTrace();
        }
        log.debug("delete sku [id]:"+id+"successful");
        return jsonResult(result);
    }

    /**
     * 查询全部的品牌
     * @param request
     * @return
     */
    @RequestMapping(value = "/sku/findBrand.json")
    @ResponseBody
    public JsonResult findBrand(HttpServletRequest request) {
        List<ComboStore> list = skuService.findBrand();
        return jsonResult(list);
    }

    /**
     * 查询全部的cid
     * @param request
     * @return
     */
    @RequestMapping(value = "/sku/findCategory.json")
    @ResponseBody
    public JsonResult findCategory(HttpServletRequest request) {
        List<ComboStore> list = skuService.findCategory();
        return jsonResult(list);
    }

    /**
     * sku号不能重复
     * @param request
     * @return
     */
    @RequestMapping(value = "/sku/findMatnr.json",method = RequestMethod.POST)
    @ResponseBody
    public JsonResult findMatnr(HttpServletRequest request) {
        String matnr = request.getParameter("inputMatnr");
        try {
            matnr = new String(matnr.getBytes("iso-8859-1"),"utf-8") ;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Sku sku= skuService.findByMatnr(matnr);
        if (!Validate.isNullOrEmpty(sku)){
            return new JsonResult(false,"","SKU不能重复");
        }else{
            return jsonResult(true);
        }
    }

    /**
     * sku号不能重复
     * @param request
     * @return
     */
    @RequestMapping(value = "/sku/findMatnrRemoveMy.json",method = RequestMethod.POST)
    @ResponseBody
    public JsonResult findMatnrRemoveMy(HttpServletRequest request) {
        String inputMatnr = request.getParameter("inputMatnr");
        String recordMantr = request.getParameter("recordMantr");
        try {
            inputMatnr = new String(inputMatnr.getBytes("iso-8859-1"),"utf-8") ;
            recordMantr = new String(recordMantr.getBytes("iso-8859-1"),"utf-8") ;
            if (inputMatnr.equals(recordMantr)){
                return   jsonResult(true);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Sku sku= skuService.findByMatnr(inputMatnr);
        if (!Validate.isNullOrEmpty(sku)){
            return new JsonResult(false,"","SKU不能重复");
        }else{
            return jsonResult(true);
        }
    }


    @RequestMapping(value = "/sku/doCurrentOperation.json", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult giftOperation(HttpServletRequest request) {
        try {
            CurrentOperation currentOperation=null;
            List<CurrentOperation> currentOperationList=null;
            String operationOp=null;
            User user=null;
            String operationType=request.getParameter("operationType");
            currentOperation = currentOperationService.getCurrentOperation(Constants.Model.Sku.toString(), Constants.Function.upload.toString());
            if (!Validate.isNullOrEmpty(currentOperation)){
                operationOp=currentOperation.getOperateOp();
                user=userService.findOmsUserByOmsUserId(operationOp);
                return new JsonResult(false, "",operationOp+ "正在进行上传，请稍后操作，如有紧急，请联系"+operationOp+"，邮箱是："+user.getUserEmail());
            }
            if ("modify".equals(operationType)){
                String sysId=request.getParameter("sysId");
                currentOperationList = currentOperationService.getCurrentOperationByModel(Constants.Model.Sku.toString());
                if (!Validate.isNullOrEmpty(currentOperationList)) {
                    for(int i=0;i<currentOperationList.size();i++){
                        currentOperation=currentOperationList.get(i);
                        if ("delete".equals(currentOperation.getSubModel())){
                            for (int j=0;j<currentOperation.getSysId().split(";").length;j++){
                                if (sysId.equals(currentOperation.getSysId().split(";")[j])){
                                    operationOp=currentOperation.getOperateOp();
                                    user=userService.findOmsUserByOmsUserId(operationOp);
                                    return new JsonResult(false, "",  operationOp+"正在删除该记录，请稍后操作,如有紧急，请联系"+operationOp+"，邮箱是："+user.getUserEmail());
                                }
                            }
                        }else if("modify".equals(currentOperation.getSubModel())){
                                if(sysId.equals(currentOperation.getSysId())){
                                    operationOp=currentOperation.getOperateOp();
                                    user=userService.findOmsUserByOmsUserId(operationOp);
                                    return new JsonResult(false, "",  operationOp+"正在进行修改该记录，请稍后操作,如有紧急，请联系"+operationOp+"，邮箱是："+user.getUserEmail());
                                }
                        }
                    }
                }
                currentOperationService.saveCurrentOperation(Constants.Model.Sku.toString(),Constants.Function.modify.toString(),sysId);
            }else if("save".equals(operationType)){
                currentOperation = currentOperationService.getCurrentOperation(Constants.Model.Sku.toString(), Constants.Function.save.toString());
                if (!Validate.isNullOrEmpty(currentOperation)) {
                    operationOp=currentOperation.getOperateOp();
                    user=userService.findOmsUserByOmsUserId(operationOp);
                    return new JsonResult(false, "", operationOp+"正在添加，请稍后操作,如有紧急，请联系"+operationOp+"，邮箱是："+user.getUserEmail());
                }
                currentOperationService.saveCurrentOperation(Constants.Model.Sku.toString(),Constants.Function.save.toString());
                // currentOperationService.deleteCurrentOperation(Constants.Model.Gift.toString());
            }else if("delete".equals(operationType)){
                String id=request.getParameter("id");
                String ids[]=id.split(";");
                for (int i=0;i<ids.length;i++){
                    currentOperationList = currentOperationService.getCurrentOperationByModel(Constants.Model.Sku.toString());
                    if (!Validate.isNullOrEmpty(currentOperationList)){
                        for (int j=0;j<currentOperationList.size();j++){
                            currentOperation=currentOperationList.get(j);
                            if (!Validate.isNullOrEmpty(currentOperation.getSysId())){
                                if ("delete".equals(currentOperation.getSubModel())){
                                    for(int k=0;k<currentOperation.getSysId().split(";").length;k++){
                                       if (currentOperation.getSysId().split(";")[k].equals(ids[i])){
                                           operationOp=currentOperation.getOperateOp();
                                           user=userService.findOmsUserByOmsUserId(operationOp);
                                           return new JsonResult(false, "", operationOp+"正在删除该记录，请稍后操作,如有紧急，请联系"+operationOp+"，邮箱是："+user.getUserEmail());
                                       }
                                    }
                                }else if ("modify".equals(currentOperation.getSubModel())){
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
                currentOperationService.saveCurrentOperation(Constants.Model.Sku.toString(),Constants.Function.delete.toString(),id);
            }
        }catch (Exception ex){
            ex.printStackTrace();
            log.debug("query or save operation wrong :"+ex.getMessage());
            return new JsonResult(false,"","请求发生失败，请稍后重试");
        }
        return new JsonResult(true,"","");
    }

    @RequestMapping(value = "/sku/doDeleteSkuCurrentOperationByUser.json", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult giftDeleteOperationByUser(HttpServletRequest request) {
        String userName= UserSecurityUtil.getCurrentUsername();
        try {
            boolean isNotExist = currentOperationService.getCurrentOperationLogByUser(Constants.Model.Sku.toString(), userName);
            if (!isNotExist) {
                currentOperationService.deleteCurrentOperation(Constants.Model.Sku.toString(),userName);
            }else{
                return new JsonResult(false, "",  "当前没有被你锁住的操作，无需解锁");
            }
        }catch (Exception ex){
            ex.printStackTrace();
            log.debug("unlock SkuOperation wrong:"+ex.getMessage());
            return new JsonResult(false,"","解锁失败");
        }
        return new JsonResult(true,"","解锁成功");
    }

    @RequestMapping(value = "/sku/doDeleteCurrentOperation.json", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult giftDeleteOperation(HttpServletRequest request) {
        try {
            String operationType=request.getParameter("operationType");
            if ("modify".equals(operationType)){
                String sysId=request.getParameter("sysId");
                boolean isNotExist = currentOperationService.getCurrentOperationLogBySysId(Constants.Model.Sku.toString(),sysId);
                if (!isNotExist) {
                    currentOperationService.deleteCurrentOperationBySysId(Constants.Model.Sku.toString(),sysId);
                }
            }else if("save".equals(operationType)){
                boolean isNotExist = currentOperationService.getCurrentOperationLog(Constants.Model.Sku.toString(),Constants.Function.save.toString());
                if (!isNotExist) {
                    currentOperationService.deleteCurrentOperationBySubModel(Constants.Model.Sku.toString(), Constants.Function.save.toString());
                }
            }else if ("delete".equals(operationType)){
                boolean isNotExist = currentOperationService.getCurrentOperationLog(Constants.Model.Sku.toString(),Constants.Function.delete.toString());
                if (!isNotExist) {
                    currentOperationService.deleteCurrentOperationBySubModel(Constants.Model.Sku.toString(), Constants.Function.delete.toString());
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
            log.debug("delete operation wrong :"+ex.getMessage());
            return new JsonResult(false,"","请求发生失败，请稍后重试");
        }
        return this.jsonResult(true);
    }
}
