package com.arvato.cc.service1.impl;

import com.arvato.cc.constant.Constants;
import com.arvato.cc.dao1.JobExecDao;
import com.arvato.cc.model.JobExec;
import com.arvato.cc.service1.JobExecService;
import com.arvato.cc.util.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: caol005
 * Date: 15-8-11
 * Time: 下午2:13
 * To change this template use File | Settings | File Templates.
 */
@Service
public class JobExecServiceImpl implements JobExecService {

    public static final Object lock = new Object();

    @Autowired
    private JobExecDao jobExecDao;

    /**
     * save current job exec record
     * @param batchNo
     * @param jobName
     */
    public JobExec saveJobExec(int batchNo,String jobName,String status) {
        JobExec jobExec = new JobExec();
        jobExec.setBatchNo(batchNo);
        jobExec.setJobName(jobName);
        jobExec.setStatus(status);
        jobExec.setNum(0);
        jobExec = jobExecDao.saveJobExec(jobExec);
        return jobExec;
    }

    public void updateJobExec(JobExec jobExec) {
        synchronized(lock) {
            jobExecDao.saveJobExec(jobExec);
        }
    }

    @Override
    public List<JobExec> findByJobName(String jobName) {
        return jobExecDao.findByJobName(jobName);
    }

    @Override
    public JobExec save(JobExec jobExec) {
        return jobExecDao.saveJobExec(jobExec);
    }
}
