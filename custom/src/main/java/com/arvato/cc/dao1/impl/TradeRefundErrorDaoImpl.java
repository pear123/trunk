package com.arvato.cc.dao1.impl;

import com.arvato.cc.dao1.JdbcTemplateExtend;
import com.arvato.cc.dao1.TradeRefundDao;
import com.arvato.cc.dao1.TradeRefundErrorDao;
import com.arvato.cc.model.TradeRefund;
import com.arvato.cc.model.TradeRefundError;
import com.arvato.jdf.dao.HibernateDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 * User: caol005
 * Date: 15-7-16
 * Time: 下午2:03
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class TradeRefundErrorDaoImpl extends HibernateDao<TradeRefundError, Integer> implements TradeRefundErrorDao {

    @Autowired
    private JdbcTemplateExtend jdbcTemplateExtend;

    public void saveTradeRefundError(TradeRefundError tradeRefund) {
        super.save(tradeRefund);
    }

}
