package com.arvato.cc.service1.impl;

import com.arvato.cc.dao1.CurrentOperationDao;
import com.arvato.cc.model.CurrentOperation;
import com.arvato.cc.service1.CurrentOperationService;
import com.arvato.cc.util.CommonHelper;
import com.arvato.cc.util.UserSecurityUtil;
import com.arvato.cc.util.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: caol005
 * Date: 15-8-10
 * Time: 下午1:05
 * To change this template use File | Settings | File Templates.
 */
@Service
public class CurrentOperationServiceImpl implements CurrentOperationService{
    @Autowired
    private CurrentOperationDao currentOperationDao;

    public boolean getCurrentOperationLog(String modelName) {
        List<CurrentOperation> currentOperationList = currentOperationDao.getByModel(modelName);
        if(Validate.isNullOrEmpty(currentOperationList)){
            return true;
        }
        return false;
    }

    @Override
    public CurrentOperation getCurrentOperation(String modelName) {
        List<CurrentOperation> currentOperationList = currentOperationDao.getByModel(modelName);
        if(Validate.isNullOrEmpty(currentOperationList)){
            return null;
        }
        return currentOperationList.get(0);
    }

    @Override
    public List<CurrentOperation> getCurrentOperationByModel(String modelName) {
        List<CurrentOperation> currentOperationList = currentOperationDao.getByModel2(modelName);
        if(Validate.isNullOrEmpty(currentOperationList)){
            return null;
        }
        return currentOperationList;
    }


    @Override
    public boolean getCurrentOperationLogBySysId(String modelName, String sysId) {
        List<CurrentOperation> currentOperationList = currentOperationDao.getByModelBySysId(modelName, sysId);
        if(Validate.isNullOrEmpty(currentOperationList)){
            return true;
        }
        return false;
    }


    @Override
    public CurrentOperation getCurrentOperationBySysId(String modelName, String sysId) {
        List<CurrentOperation> currentOperationList = currentOperationDao.getByModelBySysId(modelName, sysId);
        if(Validate.isNullOrEmpty(currentOperationList)){
            return null;
        }
        return currentOperationList.get(0);
    }

    @Override
    public boolean getCurrentOperationLog(String modelName, String subModel) {
        List<CurrentOperation> currentOperationList = currentOperationDao.getByModel(modelName,subModel);
        if(Validate.isNullOrEmpty(currentOperationList)){
            return true;
        }
        return false;
    }

    @Override
    public CurrentOperation getCurrentOperation(String modelName, String subModel) {
        List<CurrentOperation> currentOperationList = currentOperationDao.getByModel(modelName,subModel);
        if(Validate.isNullOrEmpty(currentOperationList)){
            return null;
        }
        return currentOperationList.get(0);
    }

    @Override
    public boolean getCurrentOperationLogByUser(String modelName, String userName) {
        List<CurrentOperation> currentOperationList = currentOperationDao.getByModelByUser(modelName, userName);
        if(Validate.isNullOrEmpty(currentOperationList)){
            return true;
        }
        return false;
    }

    public void deleteCurrentOperation(String modelName) {
        currentOperationDao.delete(modelName);
    }

    @Override
    public void deleteCurrentOperationBySysId(String modelName, String sysId) {
        currentOperationDao.deleteBySysId(modelName,sysId);
    }

    @Override
    public void deleteCurrentOperation(String modelName, String userName) {
        currentOperationDao.delete(modelName,userName);
    }

    @Override
    public void deleteCurrentOperationBySubModel(String modelName, String subModelName) {
        currentOperationDao.deleteBySubModel(modelName, subModelName);
    }

    /**
     * save currentOpeation
     * @param modelName
     * @param subModelName
     */
    public void saveCurrentOperation(String modelName,String subModelName) {
        CurrentOperation currentOperation = new CurrentOperation();
        currentOperation.setModel(modelName);
        currentOperation.setOperateOp(UserSecurityUtil.getCurrentUsername());
        currentOperation.setOperateTime(CommonHelper.getThisTimestamp());
        currentOperation.setSubModel(subModelName);
        currentOperationDao.saveCurrentOperation(currentOperation);

    }

    @Override
    public void saveCurrentOperation(String modelName, String subModelName, String sysId) {
        CurrentOperation currentOperation = new CurrentOperation();
        currentOperation.setModel(modelName);
        currentOperation.setOperateOp(UserSecurityUtil.getCurrentUsername());
        currentOperation.setOperateTime(CommonHelper.getThisTimestamp());
        currentOperation.setSubModel(subModelName);
        currentOperation.setSysId(sysId);
        currentOperationDao.saveCurrentOperation(currentOperation);
    }


    @Override
    public Map<String, CurrentOperation> findAllForMap() {
        List<CurrentOperation> cplist =  currentOperationDao.findAllByN();
        Map<String,CurrentOperation> cpMap = new HashMap<String, CurrentOperation>();
        if(!Validate.isNullOrEmpty(cplist)){
            for(CurrentOperation cp:cplist){
                cpMap.put(cp.getModel(),cp);
            }
        }
        return cpMap;
    }

    /**
     * 查询所有失败聚石塔订单
     * @return tid集合
     */
    @Override
    public List<String> findAllByN(){
        List<String> tids = new ArrayList<String>();
        List<CurrentOperation> cplist =  currentOperationDao.findAllByN();
        if(!Validate.isNullOrEmpty(cplist)){
            for(CurrentOperation cp:cplist){
                tids.add(cp.getModel());
            }
        }
        return tids;
    }

    @Override
    public CurrentOperation save(CurrentOperation cp) {
       return currentOperationDao.save(cp);
    }
}
