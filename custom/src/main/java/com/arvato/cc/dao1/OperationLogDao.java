package com.arvato.cc.dao1;

import com.arvato.cc.model.OperationHistory;

import java.util.List;

public interface OperationLogDao {


    void saveOperationLog(OperationHistory operationHistory);

    List<OperationHistory> findLastedOperationHistory(String operationType);

}