package com.arvato.cc.service1;

import com.arvato.cc.model.OperationHistory;
import com.arvato.cc.model.Sku;
import com.arvato.jdf.dao.Page;
import com.arvato.jdf.dao.PropertyFilter;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: caol005
 * Date: 15-8-10
 * Time: 上午9:17
 * To change this template use File | Settings | File Templates.
 */
public interface OperationLogService {

    OperationHistory findLastedOperationHistory(String operationType);

    OperationHistory generateOperationHistory(String fileName,String fileType,String model);

    void saveOrUpdate(OperationHistory operationHistory);

}
