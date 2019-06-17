package com.arvato.cc.dao1;

import com.arvato.cc.model.CurrentOperation;
import com.arvato.cc.model.OperationHistory;

import java.util.List;

public interface CurrentOperationDao {


    /**
     * get current operation by model name
     * @param modelName
     * @return
     */
     List<CurrentOperation> getByModel(String modelName);

    List<CurrentOperation> getByModel2(String modelName);

    /**
     * current operation by model name and sysId
     * @param modelName
     * @param sysId
     * @return
     */
    List<CurrentOperation> getByModelBySysId(String modelName,String sysId);

    List<CurrentOperation> getByModel(String modelName,String subModel);

    List<CurrentOperation> getByModelByUser(String modelName,String userName);
    /**
     * delete current operation by model name
     * @param modelName
     */
     void delete(String modelName);

    void deleteBySysId(String modelName,String sysId);

    void delete(String modelName,String userName);

    void deleteBySubModel(String modelName,String subModel);

    void saveCurrentOperation(CurrentOperation currentOperation);


    List<CurrentOperation> findAllByN();

    CurrentOperation save(CurrentOperation cp);

}