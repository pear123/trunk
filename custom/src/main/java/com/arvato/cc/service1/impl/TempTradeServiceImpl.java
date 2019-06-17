package com.arvato.cc.service1.impl;

import com.arvato.cc.dao1.SkuDao;
import com.arvato.cc.dao1.TempTradeDao;
import com.arvato.cc.dao1.TradeRemarkDao;
import com.arvato.cc.model.*;
import com.arvato.cc.model.JdpTrade;
import com.arvato.cc.model.TempTrade;
import com.arvato.cc.model.TradeRemark;
import com.arvato.cc.model.UpdCpd;
import com.arvato.cc.service1.AlipayService;
import com.arvato.cc.service1.CpdDataService;
import com.arvato.cc.service1.TempTradeService;
import com.arvato.cc.util.TaobaoJsonUtil;
import com.arvato.cc.util.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: caol005
 * Date: 15-8-27
 * Time: 下午3:17
 * To change this template use File | Settings | File Templates.
 */
@Service
public class TempTradeServiceImpl implements TempTradeService{

    @Autowired
    private TempTradeDao tempTradeDao;

    @Autowired
    private TradeRemarkDao tradeRemarkDao;

    @Autowired
    private CpdDataService cpdDataService;

    @Autowired
    private AlipayService alipayService;

    @Autowired
    private SkuDao skuDao;

    @Override
    public String saveTempTrade(TempTrade tempTrade, int batchNo) {
        String result = "success";
        try {
            //save jdp trade
            TradeRemark tradeRemark = null;
            tempTrade.setBatchNo(batchNo);
            //get remark start
            try {
                tradeRemark = TaobaoJsonUtil.getTradeRemarkBySellerMemo(tempTrade.getSellerMemo());
                if(!Validate.isNullOrEmpty(tradeRemark)){
                    tradeRemark.setTid(tempTrade.getTid());
                    tradeRemarkDao.saveTradeRemark(tradeRemark);
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }

            tempTradeDao.saveTempTrade(tempTrade);
        } catch (Exception ex) {
            result = "failure";
            ex.printStackTrace();
        }
        return result;
    }
}
