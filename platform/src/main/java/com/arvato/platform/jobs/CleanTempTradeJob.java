package com.arvato.platform.jobs;

import com.arvato.cc.constant.Constants;
import com.arvato.cc.dao1.JobExecDao;
import com.arvato.cc.dao1.TempTradeDao;
import com.arvato.cc.model.JobExec;
import com.arvato.cc.service1.JobExecService;
import com.arvato.cc.service1.JobService;
import com.arvato.cc.service1.TradeReportService;
import com.arvato.cc.util.Validate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: caol005
 * Date: 15-8-12
 * Time: 下午3:16
 * To change this template use File | Settings | File Templates.
 */
public class CleanTempTradeJob {

    private static final Log log = LogFactory.getLog(CleanTempTradeJob.class);

    @Autowired
    private JobExecDao jobExecDao;

    @Autowired
    private TempTradeDao tempTradeDao;

    @Autowired
    private JobService jobService;

    @Autowired
    private TradeReportService tradeReportService;

    public void execute() throws JobExecutionException {
        try{
            //查询上一次执行的batch，若第一次执行，则创建Job Exec bean,不执行保存操作
            JobExec jobExec = jobService.getCurrentJobExec(Constants.JobName.cleanTempDataJob.toString());
            //若为null，表示状态为process，
            if(Validate.isNullOrEmpty(jobExec)){
                return;
            }
            //查询batch+1后，是否有数据，如果有数据，保存batch记录，并执行job
            jobExec = tradeReportService.getResultByBatch(jobExec);
            if(Validate.isNullOrEmpty(jobExec)){
                return;
            }
            //select min batchNo in job execute table
            Integer minBatchNo = jobExecDao.findMinBatchNo();

            //select min batchNo in TempTrade table
            Integer minTempBatchNo = tempTradeDao.findMinBatchNo();

            if(minTempBatchNo >= minBatchNo) {
                log.debug("min batch no in temp is greater than or equals job execute,so return");
                return;
            }

            Integer deleteBeforeBatchNo = minBatchNo - 1;

            jobService.moveTempTrade(deleteBeforeBatchNo);

        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
