package com.arvato.cc.dao1.impl;

import com.arvato.cc.dao1.TradeRemarkPresentDao;
import com.arvato.cc.model.TradeRemarkPresent;
import com.arvato.jdf.dao.HibernateDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ZHAN405
 * Date: 15-8-13
 * Time: 下午6:29
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class TradeRemarkPresentDaoImpl extends HibernateDao<TradeRemarkPresent, Integer> implements TradeRemarkPresentDao {

    public List<TradeRemarkPresent> getPresentById(String id){
        return  this.find("from TradeRemarkPresent where tradeRemark.tid = ?",id);
    }


    public List<TradeRemarkPresent> getAllTradeRemarkPresents(){
        return this.getAll();
    }
}
