package com.arvato.cc.dao1;

import com.arvato.cc.model.UpdDelivery;

import java.util.List;
import java.util.Map;

public interface BillDataDao {


    void saveOrUpdate(UpdDelivery updDelivery);

    List<Map<String,Object>> getBillUniqueNum();

}
