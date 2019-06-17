package com.arvato.cc.dao1.impl;

import com.arvato.cc.dao1.TempTradeDao;
import com.arvato.cc.dao1.TradeRemarkDao;
import com.arvato.cc.model.Sku;
import com.arvato.cc.model.TempTrade;
import com.arvato.cc.model.TradeRemark;
import com.arvato.cc.util.Validate;
import com.arvato.jdf.dao.HibernateDao;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: caol005
 * Date: 15-8-13
 * Time: 下午4:01
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class TradeRemarkDaoImpl extends HibernateDao<TradeRemark, Integer> implements TradeRemarkDao {


    public void saveTradeRemark(TradeRemark tradeRemark) {
        super.save(tradeRemark);
    }

    public  TradeRemark findInvoiceTitleById(Integer tradeSysId){
        return super.get(tradeSysId);
    }

    public Integer getIdByTid(String tid){
        String HQL = " from TradeRemark tt where tt.tid = ? ";
        List<TradeRemark> trList = super.find(HQL, tid);
        Integer id = null;
        if(!CollectionUtils.isEmpty(trList)){
            TradeRemark tr = (TradeRemark)trList.get(0);
            id = tr.getId();
        }
        return id;
    }

    public TradeRemark getByTid(String tid) {
        List<TradeRemark> tradeRemarkList = super.find(" from TradeRemark tr where tr.tid = ?",tid);
        return (null == tradeRemarkList || tradeRemarkList.isEmpty() ) ? null : tradeRemarkList.get(0);
    }

    @Override
    public void deleteTradeRemark(String tid) {
        List<TradeRemark> tradeRemarkList = super.find(" from TradeRemark tr where tr.tid = ?",tid);
        if(Validate.isNullOrEmpty(tradeRemarkList)){
            return ;
        }
        for(TradeRemark tradeRemark : tradeRemarkList) {
            super.delete(tradeRemark);
        }
    }
}