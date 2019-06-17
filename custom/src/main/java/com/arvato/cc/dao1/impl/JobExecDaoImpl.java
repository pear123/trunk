package com.arvato.cc.dao1.impl;

import com.arvato.cc.constant.Constants;
import com.arvato.cc.dao1.JdbcTemplateExtend;
import com.arvato.cc.dao1.JobExecDao;
import com.arvato.cc.model.JobExec;
import com.arvato.cc.model.TempTrade;
import com.arvato.cc.util.Validate;
import com.arvato.jdf.dao.HibernateDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: caol005
 * Date: 15-8-11
 * Time: 下午2:04
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class JobExecDaoImpl extends HibernateDao<JobExec, String> implements JobExecDao {

    @Autowired
    private JdbcTemplateExtend jdbcTemplateExtend;

    public List<JobExec> findByJobName(String jobName) {
        return super.find(" from JobExec ex where ex.jobName = ?",jobName);
    }

    public JobExec saveJobExec(JobExec jobExec) {
        return super.save(jobExec);
    }

    public Integer findMinBatchNo() {
        List<Map<String,Object>> list = jdbcTemplateExtend.query("select min(batch_no) min_batchNO from job_exec");
        if(Validate.isNullOrEmpty(list) || Validate.isNullOrEmpty(list.get(0).get("min_batchNO"))) {
            return 0;
        } else {
            return Integer.parseInt(list.get(0).get("min_batchNO").toString());
        }
    }

    //mod by kalend
    public List<TempTrade> findByBatchNo(int batchNo) {
        return super.find(" from TempTrade tt where tt.batchNo = ? ", batchNo);
    }

    //mod by kalend
    public List<TempTrade> findByBatchNoNotVirtual(int batchNo){
        return super.find(" from TempTrade tt where tt.batchNo = ? and shippingType != ? ",batchNo, Constants.ShippingType.virtual.toString());
    }

}
