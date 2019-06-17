package com.arvato.cc.dao1;

import com.arvato.cc.model.ReportConfig;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: caol005
 * Date: 15-7-16
 * Time: 下午2:02
 * To change this template use File | Settings | File Templates.
 */
public interface ReportConfigDao {
    List<ReportConfig> getReportConfigByName(String reportName);
}
