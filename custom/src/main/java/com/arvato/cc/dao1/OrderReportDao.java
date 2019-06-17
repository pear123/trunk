package com.arvato.cc.dao1;

import com.arvato.cc.model.ExpOrder;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ZHAN405
 * Date: 15-8-18
 * Time: 下午2:44
 * To change this template use File | Settings | File Templates.
 */
public interface OrderReportDao {

    List<ExpOrder> getOrderReportList(Map<String,String> params);

    List<Map<String,Object>> getOrderReportListBySql(Map<String,String> params);

}
