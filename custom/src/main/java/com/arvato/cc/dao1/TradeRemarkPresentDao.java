package com.arvato.cc.dao1;

import com.arvato.cc.model.TradeRemarkPresent;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ZHAN405
 * Date: 15-8-13
 * Time: 下午6:01
 * To change this template use File | Settings | File Templates.
 */
public interface TradeRemarkPresentDao {


    List<TradeRemarkPresent> getPresentById(String Id);


    List<TradeRemarkPresent> getAllTradeRemarkPresents();
}
