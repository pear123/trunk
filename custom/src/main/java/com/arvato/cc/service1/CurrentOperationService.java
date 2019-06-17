package com.arvato.cc.service1;

import com.arvato.cc.model.CurrentOperation;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: caol005
 * Date: 15-8-10
 * Time: 下午1:05
 * To change this template use File | Settings | File Templates.
 */
public interface CurrentOperationService {

     boolean getCurrentOperationLog(String modelName);

     CurrentOperation getCurrentOperation(String modelName);

     List<CurrentOperation> getCurrentOperationByModel(String modelName);

     boolean getCurrentOperationLogBySysId(String modelName,String sysId);

     CurrentOperation getCurrentOperationBySysId(String modelName,String sysId);

     boolean getCurrentOperationLog(String modelName,String subModel);

     CurrentOperation getCurrentOperation(String modelName,String subModel);

     boolean getCurrentOperationLogByUser(String modelName,String userName);

     void deleteCurrentOperation(String modelName);

     void deleteCurrentOperationBySysId(String modelName,String sysId);

     void deleteCurrentOperation(String modelName,String userName);

     void deleteCurrentOperationBySubModel(String modelName,String subModelName);

     void saveCurrentOperation(String modelName,String subModelName);

     void saveCurrentOperation(String modelName,String subModelName,String sysId);

     Map<String,CurrentOperation> findAllForMap();

     List<String> findAllByN();

     CurrentOperation save(CurrentOperation cp);
}
