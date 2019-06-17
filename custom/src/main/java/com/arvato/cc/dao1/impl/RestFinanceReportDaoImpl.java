package com.arvato.cc.dao1.impl;

import com.arvato.cc.dao1.JdbcTemplateExtend;
import com.arvato.cc.dao1.OperationLogDao;
import com.arvato.cc.dao1.RestFinanceReportDao;
import com.arvato.cc.form.finance.RestFinanceReport;
import com.arvato.cc.model.Alipay;
import com.arvato.cc.model.GiftData;
import com.arvato.cc.model.JstTrade;
import com.arvato.cc.model.OperationHistory;
import com.arvato.jdf.dao.HibernateDao;
import com.arvato.jdf.dao.Page;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.SQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SimpleTriggerBean;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public class RestFinanceReportDaoImpl extends HibernateDao<Alipay, String> implements RestFinanceReportDao {

    @Autowired
    private JdbcTemplateExtend jdbcTemplateExtend;

    public List<Alipay> getAlipayReportList(String hql) {

        List<Alipay> queryAlipays = super.find(hql);
        return ( null == queryAlipays || queryAlipays.isEmpty() ) ? null : queryAlipays;
    }

    public List<Alipay> getAlipayRerportListBySql(String sql){
        SQLQuery sqlQuery =this.getSession().createSQLQuery(sql);
        sqlQuery.addEntity("a",Alipay.class);
        return sqlQuery.list();
    }


    public Page<Alipay> findByPage(Page page, String hql) {
        return super.find(page,hql);
    }

    public Boolean isExits(String orderNum){
        Boolean rs = false;
        String HQL = "from JstTrade where tid='" +orderNum + "'";
        List<JstTrade> jstTradeList = super.find(HQL, null);
        if(CollectionUtils.isEmpty(jstTradeList) && jstTradeList.size()==0){
            rs = true;
        }
        return rs;
    }
}
