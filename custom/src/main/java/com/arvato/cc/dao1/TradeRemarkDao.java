package com.arvato.cc.dao1;

import com.arvato.cc.model.TradeRemark;

/**
 * Created with IntelliJ IDEA.
 * User: caol005
 * Date: 15-8-13
 * Time: 下午3:59
 * To change this template use File | Settings | File Templates.
 */
public interface TradeRemarkDao {

    void saveTradeRemark(TradeRemark tradeRemark);

    TradeRemark findInvoiceTitleById(Integer tradeSysId);

    Integer getIdByTid(String tid);

    TradeRemark getByTid(String tid);

    void deleteTradeRemark(String tid);
}
