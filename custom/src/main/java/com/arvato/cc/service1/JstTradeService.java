package com.arvato.cc.service1;

import com.arvato.cc.model.JstTrade;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: caol005
 * Date: 15-8-12
 * Time: 上午10:35
 * To change this template use File | Settings | File Templates.
 */
public interface JstTradeService {
    void saveJstTrade(List<JstTrade> jstTradeList);

    void saveJstTrade(JstTrade jstTrade);

}
