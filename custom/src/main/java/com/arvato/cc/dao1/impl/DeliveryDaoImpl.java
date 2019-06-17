package com.arvato.cc.dao1.impl;

import com.arvato.cc.dao1.DeliveryDao;
import com.arvato.cc.model.ExpDelivery;
import com.arvato.jdf.dao.HibernateDao;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 * User: ZHAN405
 * Date: 15-8-13
 * Time: 下午8:01
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class DeliveryDaoImpl  extends HibernateDao<ExpDelivery, Integer> implements DeliveryDao {

    @Override
    public void saveExpDeliveryData(ExpDelivery expDelivery){
        super.save(expDelivery);
    }

    @Override
    public void deleteExpDeliveryData(String tid){
        SQLQuery sqlQuery = this.getSession().createSQLQuery("delete from exp_delivery where order_id=?");
        sqlQuery.setParameter(0, tid);
        sqlQuery.executeUpdate();
    }
}
