package com.arvato.cc.dao1;

import com.arvato.cc.model.ExpOrder;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: caol005
 * Date: 15-8-13
 * Time: 下午4:49
 * To change this template use File | Settings | File Templates.
 */
public interface ExpOrderDao {
    void saveExpOrder(List<ExpOrder> expOrderList);
    void deleteExeOrder(String tid);
}
