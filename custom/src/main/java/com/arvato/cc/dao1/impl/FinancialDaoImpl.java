package com.arvato.cc.dao1.impl;

import com.arvato.cc.dao1.FinancialDao;
import com.arvato.cc.model.ExpFinacial;
import com.arvato.jdf.dao.HibernateDao;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 * User: caol005
 * Date: 15-7-16
 * Time: 下午2:03
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class FinancialDaoImpl extends HibernateDao<ExpFinacial, Integer> implements FinancialDao {

    @Override
    public void saveExpFinancialData(ExpFinacial expFinacial) {
        super.save(expFinacial);
    }

    @Override
    public void deleteExpFinancialData(String tid){
        SQLQuery sqlQuery = this.getSession().createSQLQuery("delete from exp_finacial where ort01=?");
        sqlQuery.setParameter(0, tid);
        sqlQuery.executeUpdate();
    }

}
