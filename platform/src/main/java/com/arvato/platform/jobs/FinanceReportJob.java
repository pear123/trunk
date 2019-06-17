package com.arvato.platform.jobs;

/**
 * Created with IntelliJ IDEA.
 * User: caol005
 * Date: 15-8-11
 * Time: 下午1:31
 * To change this template use File | Settings | File Templates.
 */

import com.arvato.cc.constant.Constants;
import com.arvato.cc.log.LogMessageUtil;
import com.arvato.cc.model.JobExec;
import com.arvato.cc.service1.JobExecService;
import com.arvato.cc.service1.JobService;
import com.arvato.cc.util.Validate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 生成财务报表数据
 */
public class FinanceReportJob {

    private static final Log log = LogFactory.getLog(FinanceReportJob.class);
    private static LogMessageUtil msg = new LogMessageUtil("JOB");

    @Autowired
    private JobExecService jobExecService;

    @Autowired
    private JobService jobService;

    public void execute() throws JobExecutionException {
        log.info(msg.getStartMessage("FinanceReportJob"));
        try {
            //查询上一次执行的batch，若第一次执行，则创建Job Exec bean,不执行保存操作
            JobExec jobExec = jobService.getCurrentJobExec(Constants.JobName.financeReportJob.toString());
            if(Validate.isNullOrEmpty(jobExec)){
                log.info(msg.getEndMessage("FinanceReportJob getCurrentJobExec jobExec is null"));
                return;
            }
            log.info(msg.getProcessMessage("FinanceReportJob getCurrentJobExec jobExec:"+jobExec.toString()));
            //查询batch+1后，是否有数据，如果有数据，保存batch记录，并执行job
            //mod by kalend
            jobExec = jobService.getResultByBatch(jobExec);
            if(Validate.isNullOrEmpty(jobExec)){
                log.info(msg.getEndMessage("FinanceReportJob getFinanceResultByBatch jobExec is null"));
                return;
            }
            log.info(msg.getProcessMessage("FinanceReportJob getFinanceResultByBatch jobExec:"+jobExec.toString()));
            String result = jobService.generateFinanceReport(jobExec.getBatchNo());
            log.info(msg.getProcessMessage("FinanceReportJob result:"+result));
            if ("success".equals(result)) {
                jobExec.setStatus(Constants.JobStatus.done.toString());
                log.info(msg.getProcessMessage("FinanceReportJob success jobExec:" + jobExec.toString()));
                jobExecService.updateJobExec(jobExec);
            } else {
                jobExec.setBatchNo(jobExec.getBatchNo()-1);
                jobExec.setStatus(Constants.JobStatus.done.toString());
                log.info(msg.getProcessMessage("FinanceReportJob failure jobExec:" + jobExec.toString()));
                jobExecService.updateJobExec(jobExec);
            }

        } catch (Exception ex) {
            log.error(msg.getErrorMessage("FinanceReportJob "+ ex.getMessage()),ex);
        }
        log.info(msg.getEndMessage("FinanceReportJob"));
    }
}
