package com.arvato.cc.dao1;

import com.arvato.cc.model.JobExec;
import com.arvato.cc.model.TempTrade;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: caol005
 * Date: 15-8-11
 * Time: 下午2:04
 * To change this template use File | Settings | File Templates.
 */
public interface JobExecDao {

    /**
     * find by job name
     * @param jobName
     * @return
     */
    List<JobExec> findByJobName(String jobName);

    JobExec saveJobExec(JobExec jobExec);

    Integer  findMinBatchNo();

    List<TempTrade> findByBatchNo(int batchNo);

    List<TempTrade> findByBatchNoNotVirtual(int batchNo);
}
