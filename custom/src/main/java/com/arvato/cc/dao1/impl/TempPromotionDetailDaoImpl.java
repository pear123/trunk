package com.arvato.cc.dao1.impl;

import com.arvato.cc.constant.Constants;
import com.arvato.cc.dao1.JdbcTemplateExtend;
import com.arvato.cc.dao1.TempPromotionDetailDao;
import com.arvato.cc.model.TempPromotionDetail;
import com.arvato.jdf.dao.HibernateDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: caol005
 * Date: 15-8-28
 * Time: 上午9:15
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class TempPromotionDetailDaoImpl extends HibernateDao<TempPromotionDetail, Integer> implements TempPromotionDetailDao {

    @Autowired
    private JdbcTemplateExtend jdbcTemplateExtend;

    /**
     *根据订单号查询商品Coupon
     * @param tid
     * @return
     */
    @Override
    public List<TempPromotionDetail> getItemCouponByTid(String tid) {
        return super.find(" from TempPromotionDetail where tempTrade.tid = ? and promotionType = ?",tid, Constants.CouponType.ITEM.toString());
    }

    /**
     * 根据订单号查询店铺Coupon
     * @param tid
     * @return
     */
    @Override
    public List<TempPromotionDetail> getShopCouponByTid(String tid) {
        return super.find(" from TempPromotionDetail where tempTrade.tid = ? and promotionType = ?",tid, Constants.CouponType.SHOP.toString());
    }

    /**
     * 根据订单号，查询所有优惠券
     * @param tid
     * @return
     */
    @Override
    public List<TempPromotionDetail> getCouponByTid(String tid) {
        return super.find(" from TempPromotionDetail where tempTrade.tid = ? and ( promotionType = ? or promotionType = ? )",tid, Constants.CouponType.SHOP.toString(),Constants.CouponType.ITEM.toString());
    }

    @Override
    public Map<String, Object> getCouponAmountByTradeSysId(Integer tradeSysId) {
        String sql = "select sum(DISCOUNT_FEE) totalCoupon from temp_promotion_detail where (promotion_type = ? or promotion_type = ?) and TRADE_SYS_ID = ? ";
        Object[] objects = new Object[3];
        objects[0] = Constants.CouponType.SHOP.toString();
        objects[1] = Constants.CouponType.ITEM.toString();
        objects[2] = tradeSysId;
        List<Map<String,Object>> list = jdbcTemplateExtend.queryForList(sql,objects);
        return null == list ? null : list.get(0);
    }
}
