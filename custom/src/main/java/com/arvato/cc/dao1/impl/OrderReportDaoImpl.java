package com.arvato.cc.dao1.impl;

import com.arvato.cc.dao1.JdbcTemplateExtend;
import com.arvato.cc.dao1.OrderReportDao;
import com.arvato.cc.model.ExpOrder;
import com.arvato.cc.util.Validate;
import com.arvato.jdf.dao.HibernateDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: ZHAN405
 * Date: 15-8-18
 * Time: 下午2:46
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class OrderReportDaoImpl extends HibernateDao<ExpOrder, String> implements OrderReportDao {

    @Autowired
    private JdbcTemplateExtend jdbcTemplateExtend;

    public List<ExpOrder> getOrderReportList(Map<String,String> params) {
        StringBuffer hql = new StringBuffer(" from ExpOrder where tradeStatus in ('TRADE_CLOSED','TRADE_FINISHED') ");
        if(!Validate.isNullOrEmpty(params)){
            String value = params.get("storeId");
            if(!Validate.isNullOrEmpty(value)){
                hql.append(" and storeId =  '" + value + "'");
            }
            value = params.get("poNumber");
            if(!Validate.isNullOrEmpty(value)){
                hql.append(" and tid =  '" + value + "'");
            }
            value = params.get("startTime");
            if(!Validate.isNullOrEmpty(value)){
                hql.append(" and pricingdate >=  '" + value + "'");
            }
            value = params.get("endTime");
            if(!Validate.isNullOrEmpty(value)){
                hql.append(" and pricingdate <=  '" + value + "'");
            }
        }
        List<ExpOrder> queryExpOrder = super.find(hql.toString());
        return ( null == queryExpOrder || queryExpOrder.isEmpty() ) ? null : queryExpOrder;
    }

    public List<Map<String,Object>> getOrderReportListBySql(Map<String,String> params) {
//        StringBuffer hql = new StringBuffer("select * from view_order_report where TRADE_STATUS in ('TRADE_CLOSED','TRADE_FINISHED')");
//        if(!Validate.isNullOrEmpty(params)){
//            String value = params.get("storeId");
//            if(!Validate.isNullOrEmpty(value)){
//                hql.append(" and STORE_ID =  '" + value + "'");
//            }
//            value = params.get("poNumber");
//            if(!Validate.isNullOrEmpty(value)){
//                hql.append(" and TID =  '" + value + "'");
//            }
//            value = params.get("startTime");
//            if(!Validate.isNullOrEmpty(value)){
//                hql.append(" and PRICINGDATE >=  '" + value + "'");
//            }
//            value = params.get("endTime");
//            if(!Validate.isNullOrEmpty(value)){
//                hql.append(" and PRICINGDATE <=  '" + value + "'");
//            }
//        }

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

        String prod = "{call prod_oreport("+store_id+","+tid+","+startTime+","+endTime+","+alipayStartTime+","+alipayEndTime+")}";


        return jdbcTemplateExtend.query(prod);

    }

}
