package com.arvato.cc.service1;

import com.arvato.cc.model.JobExec;

/**
 * Created with IntelliJ IDEA.
 * User: caol005
 * Date: 15-8-12
 * Time: 下午2:37
 * To change this template use File | Settings | File Templates.
 */
public interface JobService
{
    String generateTradeData(int batchNo);

    String generateFinanceReport(int batchNo);

    String generateDeliveryReport(int batchNo);

    void moveTempTrade(int batchNo) throws Exception;

    JobExec getCurrentJobExec(String jobName);

    JobExec getCurrentJobBatchNo(String jobName);

    /**
     * 查询当前Batch不包含虚拟发货订单
     * @param jobExec
     * @return
     */
    JobExec getResultNotVirtualByBatch(JobExec jobExec);

    /**
     * 查询当前Batch包含虚拟发货订单
     * @param jobExec
     * @return
     */
    JobExec getResultByBatch(JobExec jobExec);

}
