package com.arvato.cc.service1;

import com.arvato.cc.dao1.JobExecDao;
import com.arvato.cc.model.JobExec;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: caol005
 * Date: 15-8-11
 * Time: 下午2:13
 * To change this template use File | Settings | File Templates.
 */
public interface JobExecService {

    JobExec saveJobExec(int batchNo,String jobName,String status);

    void updateJobExec(JobExec jobExec);

    List<JobExec> findByJobName(String jobName);

    JobExec save(JobExec jobExec);
}
