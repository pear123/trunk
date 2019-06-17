package com.arvato.cc.dao1.impl;

import com.arvato.cc.dao1.ReportConfigDao;
import com.arvato.cc.model.ReportConfig;
import com.arvato.jdf.dao.HibernateDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: caol005
 * Date: 15-7-16
 * Time: 下午2:03
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class ReportConfigDaoImpl extends HibernateDao<ReportConfig, Integer> implements ReportConfigDao {

    @Override
    public List<ReportConfig> getReportConfigByName(String reportName) {
        return this.find("from ReportConfig where reportName = ? order by orderIndex",reportName);
    }
}
