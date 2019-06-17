package com.arvato.platform.jobs;

/**
 * Created with IntelliJ IDEA.
 * User: caol005
 * Date: 15-8-11
 * Time: 下午1:32
 * To change this template use File | Settings | File Templates.
 */

import com.arvato.cc.constant.Constants;
import com.arvato.cc.log.LogMessageUtil;
import com.arvato.cc.model.JobExec;
import com.arvato.cc.service1.JdpTradeService;
import com.arvato.cc.service1.JobExecService;
import com.arvato.cc.service1.JobService;
import com.arvato.cc.util.Validate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 生成订单数据
 */
public class TradeDataJob {

    private static final Log log = LogFactory.getLog(TradeDataJob.class);
    private static LogMessageUtil msg = new LogMessageUtil("JOB");
    @Autowired
    private JobExecService jobExecService;

    @Autowired
    private JobService jobService;

    @Autowired
    private JdpTradeService jdpTradeService;


    public void execute() throws JobExecutionException {
        log.info(msg.getStartMessage("TradeDataJob"));
         try{
             //查询上一次执行的batch，若第一次执行，则创建Job Exec bean,不执行保存操作
             JobExec jobExec = jobService.getCurrentJobExec(Constants.JobName.orderDataJob.toString());

             //若为null，表示状态为process，
             if(Validate.isNullOrEmpty(jobExec)){
                 log.info(msg.getEndMessage("TradeDataJob getCurrentJobExec jobExec is null"));
                 return;
             }
             log.info(msg.getProcessMessage("TradeDataJob getCurrentJobExec jobExec:"+jobExec.toString()));
             //查询batch+1后，是否有数据，如果有数据，保存batch记录，并执行job
             //mod by kalend
             jobExec = jdpTradeService.getResultByBatch(jobExec);
             if(Validate.isNullOrEmpty(jobExec)){
                 log.info(msg.getProcessMessage("TradeDataJob getResultByBatch jobExec is null"));
                 return;
             }
             log.info(msg.getProcessMessage("TradeDataJob getResultByBatch jobExec:"+jobExec.toString()));
             String result = jobService.generateTradeData(jobExec.getBatchNo());
             log.info(msg.getProcessMessage("TradeDataJob result:"+result));
             if("success".equals(result)){
                 //update job execute record
                 jobExec.setStatus(Constants.JobStatus.done.toString());
                 log.info(msg.getProcessMessage("TradeDataJob success jobExec:" + jobExec.toString()));
                 jobExecService.updateJobExec(jobExec);
             } else {
                 jobExec.setBatchNo(jobExec.getBatchNo()-1);
                 jobExec.setStatus(Constants.JobStatus.done.toString());
                 log.info(msg.getProcessMessage("TradeDataJob failure jobExec:" + jobExec.toString()));
                 jobExecService.updateJobExec(jobExec);
             }
         } catch (Exception ex){
             log.error(msg.getErrorMessage("TradeDataJob ex:" + ex.getMessage()), ex);
         }
        log.info(msg.getEndMessage("TradeDataJob"));
    }

}
