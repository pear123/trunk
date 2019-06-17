package com.arvato.cc.dao1;

import com.arvato.cc.model.TempPromotionDetail;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: caol005
 * Date: 15-8-28
 * Time: 上午9:15
 * To change this template use File | Settings | File Templates.
 */
public interface TempPromotionDetailDao {

    List<TempPromotionDetail> getItemCouponByTid(String tid);

    List<TempPromotionDetail> getShopCouponByTid(String tid);

    List<TempPromotionDetail> getCouponByTid(String tid);

    Map<String,Object> getCouponAmountByTradeSysId(Integer tradeSysId);
}
