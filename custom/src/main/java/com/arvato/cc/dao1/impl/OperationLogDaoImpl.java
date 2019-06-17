package com.arvato.cc.dao1.impl;

import com.arvato.cc.dao1.OperationLogDao;
import com.arvato.cc.model.OperationHistory;
import com.arvato.jdf.dao.HibernateDao;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class OperationLogDaoImpl extends HibernateDao<OperationHistory, String> implements OperationLogDao {

    public void saveOperationLog(OperationHistory operationHistory) {
        super.save(operationHistory);
    }

    public List<OperationHistory> findLastedOperationHistory(String operationType) {
        return super.find("from OperationHistory oh where oh.fileType = ? order by operateTime desc",operationType);
    }
}
