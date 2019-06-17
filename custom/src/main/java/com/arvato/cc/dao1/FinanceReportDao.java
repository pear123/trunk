package com.arvato.cc.dao1;

import com.arvato.cc.model.ExpFinacial;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ZHAN405
 * Date: 15-8-20
 * Time: 上午11:44
 * To change this template use File | Settings | File Templates.
 */
public interface FinanceReportDao {

    List<ExpFinacial> getFinanceReportList(String hql);

    List<Map<String,Object>> getFinanceReportListBySQL(String sql);

    List<Map<String,Object>> getFinanceReportListBySQL(Map<String,String> params);

}
