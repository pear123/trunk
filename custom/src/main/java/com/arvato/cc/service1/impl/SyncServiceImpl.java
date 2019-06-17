package com.arvato.cc.service1.impl;

import com.arvato.cc.constant.Constants;
import com.arvato.cc.dao1.UserDao;
import com.arvato.cc.log.LogMessageUtil;
import com.arvato.cc.model.CurrentOperation;
import com.arvato.cc.model.User;
import com.arvato.cc.service1.CurrentOperationService;
import com.arvato.cc.service1.SyncService;
import com.arvato.cc.util.Validate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.impl.Log4JLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
* Created by XUSO002 on 2015/9/18 0018.
*/
@Service
public class SyncServiceImpl implements SyncService {
    private static final Log log = LogFactory.getLog(SyncServiceImpl.class);
    private static LogMessageUtil log_msg = new LogMessageUtil("ALIPAY");
    @Autowired
    private CurrentOperationService currentOperationService;

    @Autowired
    private UserDao userDao;

    @Override
    public  String syncAlipay() {
        log.info(log_msg.getStartMessage("doSyncService"));
        synchronized (this){
            String msg=null;
            try {
                CurrentOperation currentOperation = currentOperationService.getCurrentOperation(Constants.Model.Alipay.toString());
                if (!Validate.isNullOrEmpty(currentOperation)) {
                    //UPLOAD FAILURE:FUNCTION IS BUSY NOW,PLEASE TRY AGAIN LATER!
                    String operationOp=currentOperation.getOperateOp();
                    User user=userDao.findUserByUserId(operationOp);
                    msg=operationOp+"正在操作，请稍后操作，如有紧急，请联系"+operationOp+"，邮箱是："+user.getUserEmail();
                    return msg;
                }
                currentOperationService.saveCurrentOperation(Constants.Model.Alipay.toString(), Constants.Function.upload.toString());
            }catch (Exception ex){
                ex.printStackTrace();
                log.info(log_msg.getStartMessage("Exception:"+ex.getMessage()));
            }
            return  msg;
        }
    }
    @Override
    public  String syncSku() {
        synchronized (this){
            String msg=null;
            try {
                CurrentOperation currentOperation = currentOperationService.getCurrentOperation(Constants.Model.Sku.toString());
                if (!Validate.isNullOrEmpty(currentOperation)) {
                    //UPLOAD FAILURE:FUNCTION IS BUSY NOW,PLEASE TRY AGAIN LATER!
                    String operationOp=currentOperation.getOperateOp();
                    User user=userDao.findUserByUserId(operationOp);
                    msg=operationOp+"正在操作，请稍后操作，如有紧急，请联系"+operationOp+"，邮箱是："+user.getUserEmail();
                    return msg;
                }
                currentOperationService.saveCurrentOperation(Constants.Model.Sku.toString(), Constants.Function.upload.toString());
            }catch (Exception ex){
                ex.printStackTrace();
                log.debug("query sku upload current operation wrong :"+ex.getMessage());
            }
            return  msg;
        }

    }

    @Override
    public  String syncGift() {
        synchronized (this){
            String msg=null;
            try {
                CurrentOperation currentOperation = currentOperationService.getCurrentOperation(Constants.Model.Gift.toString());
                if (!Validate.isNullOrEmpty(currentOperation)) {
                    //UPLOAD FAILURE:FUNCTION IS BUSY NOW,PLEASE TRY AGAIN LATER!
                    String operationOp=currentOperation.getOperateOp();
                    User user=userDao.findUserByUserId(operationOp);
                    msg=operationOp+"正在操作，请稍后操作，如有紧急，请联系"+operationOp+"，邮箱是："+user.getUserEmail();
                    return  msg;
                }
                currentOperationService.saveCurrentOperation(Constants.Model.Gift.toString(), Constants.Function.upload.toString());
            }catch (Exception ex){
                ex.printStackTrace();
                log.debug("query gift upload current operation wrong :"+ex.getMessage());
            }
            return msg;
        }
    }



    @Override
    public  String syncCpd() {
        synchronized (this){
            String msg=null;
            try {
                CurrentOperation currentOperation = currentOperationService.getCurrentOperation(Constants.Model.CPD.toString());
                if (!Validate.isNullOrEmpty(currentOperation)) {
                    //UPLOAD FAILURE:FUNCTION IS BUSY NOW,PLEASE TRY AGAIN LATER!
                    String operationOp=currentOperation.getOperateOp();
                    User user=userDao.findUserByUserId(operationOp);
                    msg=operationOp+"正在操作，请稍后操作，如有紧急，请联系"+operationOp+"，邮箱是："+user.getUserEmail();
                    return msg;
                }
                currentOperationService.saveCurrentOperation(Constants.Model.CPD.toString(), Constants.Function.upload.toString());
            }catch (Exception ex){
                ex.printStackTrace();
                log.debug("query cpd upload current operation wrong :"+ex.getMessage());
            }
            return msg;
        }
    }

    @Override
    public  String syncInvoice() {
        synchronized (this){
            String msg=null;
            try {
                CurrentOperation currentOperation = currentOperationService.getCurrentOperation(Constants.Model.Sap.toString());
                if (!Validate.isNullOrEmpty(currentOperation)) {
                    //UPLOAD FAILURE:FUNCTION IS BUSY NOW,PLEASE TRY AGAIN LATER!
                    String operationOp=currentOperation.getOperateOp();
                    User user=userDao.findUserByUserId(operationOp);
                    msg=operationOp+"正在操作，请稍后操作，如有紧急，请联系"+operationOp+"，邮箱是："+user.getUserEmail();
                    return msg;
                }
                currentOperationService.saveCurrentOperation(Constants.Model.Sap.toString(), Constants.Function.upload.toString());
            }catch (Exception ex){
                ex.printStackTrace();
                log.debug("query sap upload current operation wrong :"+ex.getMessage());

            }
            return msg;
        }

    }

    @Override
    public   String  syncBill() {
        synchronized (this){
            String msg=null;
            try {
                CurrentOperation currentOperation = currentOperationService.getCurrentOperation(Constants.Model.Bill.toString());
                if (!Validate.isNullOrEmpty(currentOperation)) {
                    String operationOp=currentOperation.getOperateOp();
                    User user=userDao.findUserByUserId(operationOp);
                    msg=operationOp+"正在操作，请稍后操作，如有紧急，请联系"+operationOp+"，邮箱是："+user.getUserEmail();
                    return msg;
                }
                currentOperationService.saveCurrentOperation(Constants.Model.Bill.toString(), Constants.Function.upload.toString());
            }catch (Exception ex){
                ex.printStackTrace();
                log.debug("query bill upload current operation wrong :"+ex.getMessage());
            }
            return msg;
        }
    }
}
