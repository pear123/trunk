package com.arvato.cc.dao1.impl;

import com.arvato.cc.dao1.FinanceReportDao;
import com.arvato.cc.dao1.JdbcTemplateExtend;
import com.arvato.cc.model.ExpFinacial;
import com.arvato.cc.util.Validate;
import com.arvato.jdf.dao.HibernateDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ZHAN405
 * Date: 15-8-20
 * Time: 上午11:45
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class FinanceReportDaoImpl extends HibernateDao<ExpFinacial, Integer> implements FinanceReportDao {

    @Autowired
    private JdbcTemplateExtend jdbcTemplateExtend;

    public List<ExpFinacial> getFinanceReportList(String hql) {
        List<ExpFinacial> queryExpFinancial = super.find(hql);
        return (null == queryExpFinancial || queryExpFinancial.isEmpty()) ? null : queryExpFinancial;
    }

    @Override
    public List<Map<String, Object>> getFinanceReportListBySQL(String sql) {
        return jdbcTemplateExtend.query(sql);
    }

    @Override
    public List<Map<String, Object>> getFinanceReportListBySQL(Map<String, String> params) {
        String store_id = "''";
        String tid = "''";
        String startTime = "''";
        String endTime = "''";
        String alipayStartTime = "''";
        String alipayEndTime = "''";
        if(!Validate.isNullOrEmpty(params)){
            String value = params.get("storeId");
            if(!Validate.isNullOrEmpty(value)){
                store_id = "'" + value + "'";
            }
            value = params.get("poNumber");
            if(!Validate.isNullOrEmpty(value)){
                tid = "'" + value + "'";
            }
            value = params.get("startTime");
            if(!Validate.isNullOrEmpty(value)){
                startTime = "'" + value + "'";
            }
            value = params.get("endTime");
            if(!Validate.isNullOrEmpty(value)){
                endTime = "'" + value + "'";
            }
            value = params.get("alipayStartTime");
            if(!Validate.isNullOrEmpty(value)){
                alipayStartTime = "'" + value + "'";
            }
            value = params.get("alipayEndTime");
            if(!Validate.isNullOrEmpty(value)){
                alipayEndTime = "'" + value + "'";
            }
        }

        String prod = "{call prod_finreport("+store_id+","+tid+","+startTime+","+endTime+","+alipayStartTime+","+alipayEndTime+")}";


        return jdbcTemplateExtend.query(prod);



    }
}
