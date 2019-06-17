package com.arvato.cc.dao1.impl;

import com.arvato.cc.dao1.AlipayDao;
import com.arvato.cc.dao1.JdbcTemplateExtend;
import com.arvato.cc.dao1.TradeRefundDao;
import com.arvato.cc.model.Alipay;
import com.arvato.cc.model.TradeRefund;
import com.arvato.cc.model.TradeRemark;
import com.arvato.cc.util.Validate;
import com.arvato.jdf.dao.HibernateDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: caol005
 * Date: 15-7-16
 * Time: 下午2:03
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class TradeRefundDaoImpl extends HibernateDao<TradeRefund, Integer> implements TradeRefundDao {

    @Autowired
    private JdbcTemplateExtend jdbcTemplateExtend;

    public void saveTradeRefund(TradeRefund tradeRefund) {
        super.save(tradeRefund);
    }

    @Override
    public void deleteByRefundId(String id) {
        List<TradeRefund> tradeRefundList = super.find(" from TradeRefund tr where tr.refundId = ?",id);
        if(Validate.isNullOrEmpty(tradeRefundList)){
            return ;
        }
        for(TradeRefund tradeRemark : tradeRefundList) {
            super.delete(tradeRemark);
        }
    }

}
