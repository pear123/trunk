package com.arvato.cc.dao1.impl;

import com.arvato.cc.dao1.CpdDao;
import com.arvato.cc.dao1.ExpOrderDao;
import com.arvato.cc.model.ExpOrder;
import com.arvato.cc.model.UpdCpd;
import com.arvato.jdf.dao.HibernateDao;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: caol005
 * Date: 15-8-13
 * Time: 下午4:49
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class ExpOrderDaoImpl extends HibernateDao<ExpOrder, Integer> implements ExpOrderDao {

    public void saveExpOrder(List<ExpOrder> expOrderList) {
        for(ExpOrder expOrder : expOrderList) {
            super.save(expOrder);
        }
    }

    @Override
    public void deleteExeOrder(String tid) {
        SQLQuery sqlQuery = this.getSession().createSQLQuery("delete from exp_order where tid=?");
        sqlQuery.setParameter(0, tid);
        sqlQuery.executeUpdate();
    }
}
