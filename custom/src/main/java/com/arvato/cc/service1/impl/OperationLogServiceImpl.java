package com.arvato.cc.service1.impl;

import com.arvato.cc.dao1.OperationLogDao;
import com.arvato.cc.log.LogMessageUtil;
import com.arvato.cc.model.OperationHistory;
import com.arvato.cc.service1.OperationLogService;
import com.arvato.cc.util.CommonHelper;
import com.arvato.cc.util.UserSecurityUtil;
import com.arvato.cc.util.Validate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: caol005
 * Date: 15-8-10
 * Time: 上午9:18
 * To change this template use File | Settings | File Templates.
 */
@Service
public class OperationLogServiceImpl implements OperationLogService{
    private static final Log log = LogFactory.getLog(OperationLogServiceImpl.class);
    private static LogMessageUtil msg = new LogMessageUtil("OPERATION_LOG");
    @Autowired
    private OperationLogDao operationLogDao;

    public OperationHistory findLastedOperationHistory(String operationType) {
        log.info(msg.getStartMessage("findLastedOperationHistory"));
        StopWatch fetch = new StopWatch("findLastedOperationHistory");
        fetch.start("findLastedOperationHistory");
        List<OperationHistory> operationHistoryList = operationLogDao.findLastedOperationHistory(operationType);
        fetch.stop();
        if(Validate.isNullOrEmpty(operationHistoryList)){
            return null;
        }
        log.info(msg.getEndMessage("findLastedOperationHistory"));
        return operationHistoryList.get(0);
    }

    /**
     * generate operation history
     * @param fileName
     * @param fileType
     * @param model
     * @return
     */
    public OperationHistory generateOperationHistory(String fileName, String fileType, String model) {
        OperationHistory operationHistory = new OperationHistory();
        operationHistory.setFileName(fileName);
        operationHistory.setFileType(fileType);
        operationHistory.setModel(model);
        operationHistory.setOperateOp(UserSecurityUtil.getCurrentUsername());
        operationHistory.setOperateTime(CommonHelper.getThisTimestamp());
        return  operationHistory;
    }

    @Override
    public void saveOrUpdate(OperationHistory operationHistory) {
         operationLogDao.saveOperationLog(operationHistory);
    }
}
