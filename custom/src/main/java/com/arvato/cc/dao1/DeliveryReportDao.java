package com.arvato.cc.dao1;

import com.arvato.cc.model.ExpDelivery;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ZHAN405
 * Date: 15-8-19
 * Time: 下午2:03
 * To change this template use File | Settings | File Templates.
 */
public interface DeliveryReportDao {

    List<ExpDelivery> getDeliveryReportList(String hql);

    List<Map<String,Object>> getDeliveryReportListByView(Map<String, String> queryParams);
}
