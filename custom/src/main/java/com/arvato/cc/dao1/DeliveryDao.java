package com.arvato.cc.dao1;

import com.arvato.cc.model.ExpDelivery;

/**
 * Created with IntelliJ IDEA.
 * User: ZHAN405
 * Date: 15-8-13
 * Time: 下午7:59
 * To change this template use File | Settings | File Templates.
 */
public interface DeliveryDao {
    void saveExpDeliveryData(ExpDelivery expDelivery);

    void deleteExpDeliveryData(String tid);
}
