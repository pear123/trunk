package com.arvato.cc.dao1;

import com.arvato.cc.model.JstTrade;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: caol005
 * Date: 15-8-11
 * Time: 下午6:45
 * To change this template use File | Settings | File Templates.
 */
public interface JstTradeDao {

    void saveJstTrade(JstTrade jstTrade);

    void saveJstTrade(List<JstTrade> jstTradeList);

    List<Map<String, Object>>  findAllAliayNos();

    JstTrade getByServiceSerialNum(String serviceSerialNum);

    void deleteByTid(String tid);

    String findByServiceSerialNum(String serviceSerialNum);
}
