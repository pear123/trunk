package com.arvato.cc.dao1.impl;

import com.arvato.cc.dao1.AlipayTransDao;
import com.arvato.cc.dao1.JdbcTemplateExtend;
import com.arvato.cc.dao1.TradeHistoryDao;
import com.arvato.cc.model.AlipayTrans;
import com.arvato.cc.model.TradeHistory;
import com.arvato.cc.util.CommonHelper;
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
public class TradeHistoryDaoImpl extends HibernateDao<TradeHistory, Integer> implements TradeHistoryDao {

    @Autowired
    private JdbcTemplateExtend jdbcTemplateExtend;


    @Override
    public void saveTradeHistory(List<TradeHistory> tradeHistoryList) {
        if(Validate.isNullOrEmpty(tradeHistoryList)) {
            return;
        }
        for(TradeHistory tradeHistory : tradeHistoryList) {
            super.save(tradeHistory);
        }
    }
}
