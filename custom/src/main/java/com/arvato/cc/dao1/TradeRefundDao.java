package com.arvato.cc.dao1;

import com.arvato.cc.model.Alipay;
import com.arvato.cc.model.TradeRefund;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: caol005
 * Date: 15-7-16
 * Time: 下午2:02
 * To change this template use File | Settings | File Templates.
 */
public interface TradeRefundDao {

    void saveTradeRefund(TradeRefund tradeRefund);

    void deleteByRefundId(String id);
}
