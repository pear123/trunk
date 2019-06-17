package com.arvato.cc.dao1.impl;

import com.arvato.cc.dao1.BillDataDao;
import com.arvato.cc.dao1.JdbcTemplateExtend;
import com.arvato.cc.model.UpdDelivery;
import com.arvato.jdf.dao.HibernateDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class BillDaoImpl extends HibernateDao<UpdDelivery, String> implements BillDataDao {

    @Autowired
    private JdbcTemplateExtend jdbcTemplateExtend;

    public void saveOrUpdate(UpdDelivery updDelivery) {
        getSession().saveOrUpdate(updDelivery);
    }
    public List<Map<String,Object>> getBillUniqueNum(){
        return jdbcTemplateExtend.query("select deliveryno from upd_delivery");
    }
}
