package com.arvato.cc.dao1;

import com.arvato.cc.model.Area;
import com.arvato.cc.model.Store;

/**
 * Created with IntelliJ IDEA.
 * User: caol005
 * Date: 15-9-8
 * Time: 下午6:33
 * To change this template use File | Settings | File Templates.
 */
public interface StoreDao {
    String findByCode(String code);
}
