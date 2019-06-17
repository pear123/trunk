package com.arvato.cc.dao1.impl;

import com.arvato.cc.dao1.HibernateDao;
import com.arvato.cc.dao1.SaleItemDao;
import com.arvato.cc.model.SaleItem;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 * User: caol005
 * Date: 15-9-10
 * Time: 下午4:23
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class SaleItemDaoImpl extends HibernateDao<SaleItem, Integer> implements SaleItemDao {


    @Override
    public void saveSaleItem(SaleItem saleItem) {
       super.save(saleItem);
    }
}
