package com.arvato.cc.service1;

import com.arvato.cc.model.JdpTrade;
import com.arvato.cc.model.TempTrade;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: caol005
 * Date: 15-8-27
 * Time: 下午3:13
 * To change this template use File | Settings | File Templates.
 */
public interface TempTradeService {

    String saveTempTrade(TempTrade tempTrade, int batchNo);
}
