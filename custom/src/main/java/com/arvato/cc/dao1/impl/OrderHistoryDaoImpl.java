package com.arvato.cc.dao1.impl;

import com.arvato.cc.dao1.OrderHistoryDao;
import com.arvato.cc.model.OrderHistory;
import com.arvato.jdf.dao.HibernateDao;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 * User: ZHAN405
 * Date: 15-8-27
 * Time: 下午1:01
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class OrderHistoryDaoImpl extends HibernateDao<OrderHistory, String> implements OrderHistoryDao {



    public String getMaterByTid(String tid){
//        String HQL = "from OrderHistory where brand='" +tid + "'";
//        List<Sku> skuList = super.find(HQL, null);
//        Sku sku = null;
//        if(!CollectionUtils.isEmpty(skuList)){
//            sku = (Sku)super.find(HQL, null).get(0);
//        }
//        return sku;
           return "";
    }
}
