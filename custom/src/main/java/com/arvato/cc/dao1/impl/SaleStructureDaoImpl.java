package com.arvato.cc.dao1.impl;

import com.arvato.cc.constant.Constants;
import com.arvato.cc.dao1.JdbcTemplateExtend;
import com.arvato.cc.dao1.RoleResourceDao;
import com.arvato.cc.dao1.SaleStructureDao;
import com.arvato.cc.model.ExpOrder;
import com.arvato.cc.model.JdpTrade;
import com.arvato.cc.model.RoleResource;
import com.arvato.cc.util.Validate;
import com.arvato.jdf.dao.HibernateDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: caol005
 * Date: 15-8-17
 * Time: 下午3:03
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class SaleStructureDaoImpl implements SaleStructureDao
{
    @Autowired
    private JdbcTemplateExtend jdbcTemplateExtend;

    /**
     *根据条件查询出订单商品信息，及对应的sap价格
     * @return
     */
    public List<Map<String,Object>> findSaleStructure(Map<String, String> queryParams) {
//        String SQL = " select a.*,b.SAPPRICE as sapPrice from ( " +
//            " select oh.sku_Id as matnr,sum(oh.num) as saleQuantity ,sum(oh.TOTAL_FEE) as saleAmount from jst_order oh group by oh.SKU_ID" +
//            " ) a left join sku b on a.matnr = b.matnr where b.status = 'ACTIVE'";
        StringBuffer sql = new StringBuffer("select a.*,sku.SAPPRICE as sapPrice,sku.cid cid from (select oh.matnr as matnr,sum(oh.num) as saleQuantity ,sum(oh.TOTAL_FEE - oh.part_mjz_discount) as saleAmount from jst_order oh left join jst_trade th on oh.trade_sys_id = th.trade_sys_id ");

        StringBuffer ohStr = new StringBuffer(" where th.status in ('"+ Constants.TradeStatus.WAIT_SELLER_SEND_GOODS.toString()+"','"+ Constants.TradeStatus.WAIT_BUYER_CONFIRM_GOODS.toString()+"','"+ Constants.TradeStatus.TRADE_FINISHED.toString()+"','"+ Constants.TradeStatus.TRADE_CLOSED.toString()+"')");
        ohStr.append(" and th.shipping_type != '"+Constants.ShippingType.virtual.toString()+"'");

        StringBuffer endStr = new StringBuffer();
        if(!Validate.isNullOrEmpty(queryParams)){
            String value = queryParams.get("storeId");
            if(!Validate.isNullOrEmpty(value)){
                ohStr.append(" and th.store_id =  '" + value + "'");
            }
            value = queryParams.get("category");
            if(!Validate.isNullOrEmpty(value)){
                endStr.append(" and sku.cid =  '" + value + "'");
            }
            value = queryParams.get("startTime");
            if(!Validate.isNullOrEmpty(value)){
                ohStr.append(" and th.created >=  '" + value + "'");
            }
            value = queryParams.get("endTime");
            if(!Validate.isNullOrEmpty(value)){
                ohStr.append(" and th.created <=  '" + value + "'");
            }
            value = queryParams.get("is_trade_close");
            if(!Validate.isNullOrEmpty(value) && "true".equals(value)){
                ohStr.append(" and th.status <>  '" + Constants.TradeStatus.TRADE_CLOSED.toString() + "'");
            }
            value = queryParams.get("sku");
            if(!Validate.isNullOrEmpty(value)){
                endStr.append(" and sku.matnr like  '%" + value + "%'");
            }
        }
        sql.append(ohStr.toString());
        sql.append(" group by oh.matnr ) a left join sku on a.matnr = sku.matnr where sku.status = 'ACTIVE'");
        sql.append(endStr.toString());
//        System.out.println("method : findSaleStructure");
//        System.out.println("sql:" + sql.toString());
//        System.out.println("sql:" + String.format(sql.toString(),ohStr.toString()));
//        return jdbcTemplateExtend.query(String.format(sql.toString(),ohStr.toString()));
        sql.append(" order by salequantity desc");
        return jdbcTemplateExtend.query(sql.toString());
    }

    /**
     *根据条件查询出总数量及总价格，用于计算百分比
     * @return
     */
    public List<Map<String,Object>> findSaleStructureSum(Map<String, String> queryParams){
        StringBuffer sql = new StringBuffer("select sum(oh.num) as saleQuantitySum ,sum(oh.TOTAL_FEE - oh.part_mjz_discount) as saleAmountSum from jst_order oh ,jst_trade th,sku where oh.trade_sys_id = th.trade_sys_id and sku.matnr = oh.matnr and sku.status = 'ACTIVE' ");
        sql.append(" and th.status in ('"+ Constants.TradeStatus.WAIT_SELLER_SEND_GOODS.toString()+"','"+ Constants.TradeStatus.WAIT_BUYER_CONFIRM_GOODS.toString()+"','"+ Constants.TradeStatus.TRADE_FINISHED.toString()+"','"+ Constants.TradeStatus.TRADE_CLOSED.toString()+"')");
        sql.append(" and th.shipping_type != '"+Constants.ShippingType.virtual.toString()+"'");

        if(!Validate.isNullOrEmpty(queryParams)){
            String value = queryParams.get("storeId");
            if(!Validate.isNullOrEmpty(value)){
                sql.append(" and th.store_id =  '" + value + "'");
            }
            value = queryParams.get("category");
            if(!Validate.isNullOrEmpty(value)){
                sql.append(" and sku.cid =  '" + value + "'");
            }
            value = queryParams.get("startTime");
            if(!Validate.isNullOrEmpty(value)){
                sql.append(" and th.created >=  '" + value + "'");
            }
            value = queryParams.get("endTime");
            if(!Validate.isNullOrEmpty(value)){
                sql.append(" and th.created <=  '" + value + "'");
            }
            value = queryParams.get("is_trade_close");
            if(!Validate.isNullOrEmpty(value) && "true".equals(value)){
                sql.append(" and th.status <>  '" + Constants.TradeStatus.TRADE_CLOSED.toString() + "'");
            }
            value = queryParams.get("sku");
            if(!Validate.isNullOrEmpty(value)){
                sql.append(" and sku.matnr like  '%" + value + "%'");
            }
        }
//        return jdbcTemplateExtend.query("select sum(oh.num) as saleQuantitySum ,sum(oh.TOTAL_FEE) as saleAmountSum from jst_order oh");

//        System.out.println("method : findSaleStructureSum");
//        System.out.println("sql:" + sql.toString());
        return jdbcTemplateExtend.query(sql.toString());
    }

    /**
     * 对洗衣机,冰箱,厨卫进行分类汇总
     * 品牌，数量，金额
     * @return
     */
    public List<Map<String,Object>> findSaleStructureByCategory(Map<String, String> queryParams){
//        return jdbcTemplateExtend.query("select sku.cid as cid,sum(oh.num) as saleQuantitySum ,sum(oh.TOTAL_FEE) as saleAmountSum " +
//            "from jst_order oh left join Sku sku on oh.sku_Id = sku.matnr " +
//            " where sku.cid in ('洗衣机','冰箱','厨卫') and sku.status = 'ACTIVE' group by sku.cid");

        StringBuffer sql = new StringBuffer("select sku.cid as cid,sum(oh.num) as saleQuantitySum ,sum(oh.TOTAL_FEE - oh.part_mjz_discount) as saleAmountSum from jst_order oh left join Sku sku on oh.matnr = sku.matnr left join jst_trade th on oh.trade_sys_id = th.trade_sys_id where  sku.status = 'ACTIVE' ");  //sku.cid in ('洗衣机','冰箱','厨卫') and
        sql.append(" and th.status in ('"+ Constants.TradeStatus.WAIT_SELLER_SEND_GOODS.toString()+"','"+ Constants.TradeStatus.WAIT_BUYER_CONFIRM_GOODS.toString()+"','"+ Constants.TradeStatus.TRADE_FINISHED.toString()+"','"+ Constants.TradeStatus.TRADE_CLOSED.toString()+"')");
        sql.append(" and th.shipping_type != '"+Constants.ShippingType.virtual.toString()+"'");

        if(!Validate.isNullOrEmpty(queryParams)){
            String value = queryParams.get("storeId");
            if(!Validate.isNullOrEmpty(value)){
                sql.append(" and th.store_id =  '" + value + "'");
            }
            value = queryParams.get("category");
            if(!Validate.isNullOrEmpty(value)){
                sql.append(" and sku.cid =  '" + value + "'");
            }
            value = queryParams.get("startTime");
            if(!Validate.isNullOrEmpty(value)){
                sql.append(" and th.created >=  '" + value + "'");
            }
            value = queryParams.get("endTime");
            if(!Validate.isNullOrEmpty(value)){
                sql.append(" and th.created <=  '" + value + "'");
            }
            value = queryParams.get("is_trade_close");
            if(!Validate.isNullOrEmpty(value) && "true".equals(value)){
                sql.append(" and th.status <>  '" + Constants.TradeStatus.TRADE_CLOSED.toString() + "'");
            }
            value = queryParams.get("sku");
            if(!Validate.isNullOrEmpty(value)){
                sql.append(" and sku.matnr like  '%" + value + "%'");
            }
        }
        sql.append(" group by sku.cid");
//        System.out.println("method : findSaleStructureByCategory");
//        System.out.println("sql:" + sql.toString());
        return jdbcTemplateExtend.query(sql.toString());
    }

    /**
     *根据条件查询出对应的cid，sku，数量，及sap单价
     *根据cid对数据汇总，cid，数量汇总，sap金额汇总
     * @return
     */
    public List<Map<String,Object>> findSaleStructureByCategorySum(Map<String, String> queryParams){
//        return jdbcTemplateExtend.query("select sum(saleQuantity) as saleQuantitySum,sum(saleQuantity * sapPrice) as saleAmountSum " +
//            "from ( " +
//            " select a.matnr matnr,a.saleQuantity saleQuantity,a.saleAmount saleAmount,b.SAPPRICE sapPrice,b.cid cid from (  " +
//            " select oh.sku_Id as matnr,sum(oh.num) as saleQuantity ,sum(oh.TOTAL_FEE) as saleAmount from jst_order oh group by oh.SKU_ID " +
//            " ) a left join sku b on a.matnr = b.matnr where b.cid in ('洗衣机','冰箱','厨卫') and b.status = 'ACTIVE'" +
//            " ) c");

        StringBuffer sql = new StringBuffer("select oh.matnr as matnr,sum(oh.num) as saleQuantity ,sum(oh.TOTAL_FEE - oh.part_mjz_discount) as saleAmount,sku.SAPPRICE sapPrice,sku.cid cid from jst_order oh,jst_trade th,sku where oh.trade_sys_id = th.trade_sys_id and sku.matnr = oh.matnr and sku.status = 'ACTIVE'  ");  //and sku.cid in ('洗衣机','冰箱','厨卫')

        StringBuffer ohStr = new StringBuffer(" and th.status in ('"+ Constants.TradeStatus.WAIT_SELLER_SEND_GOODS.toString()+"','"+ Constants.TradeStatus.WAIT_BUYER_CONFIRM_GOODS.toString()+"','"+ Constants.TradeStatus.TRADE_FINISHED.toString()+"','"+ Constants.TradeStatus.TRADE_CLOSED.toString()+"')");
        ohStr.append(" and th.shipping_type != '"+Constants.ShippingType.virtual.toString()+"'");

        if(!Validate.isNullOrEmpty(queryParams)){
            String value = queryParams.get("storeId");
            if(!Validate.isNullOrEmpty(value)){
                ohStr.append(" and th.store_id =  '" + value + "'");
            }
            value = queryParams.get("category");
            if(!Validate.isNullOrEmpty(value)){
                ohStr.append(" and sku.cid =  '" + value + "'");
            }
            value = queryParams.get("startTime");
            if(!Validate.isNullOrEmpty(value)){
                ohStr.append(" and th.created >=  '" + value + "'");
            }
            value = queryParams.get("endTime");
            if(!Validate.isNullOrEmpty(value)){
                ohStr.append(" and th.created <=  '" + value + "'");
            }
            value = queryParams.get("is_trade_close");
            if(!Validate.isNullOrEmpty(value) && "true".equals(value)){
                ohStr.append(" and th.status <>  '" + Constants.TradeStatus.TRADE_CLOSED.toString() + "'");
            }
            value = queryParams.get("sku");
            if(!Validate.isNullOrEmpty(value)){
                ohStr.append(" and sku.matnr like  '%" + value + "%'");
            }
        }
        sql.append(ohStr.toString());
        sql.append(" group by oh.matnr");
//        System.out.println("method : findSaleStructureByCategorySum");
//        System.out.println("sql:" + sql.toString());
//        System.out.println("sql:" + String.format(sql.toString(),ohStr.toString()));
//        return jdbcTemplateExtend.query(String.format(sql.toString(),ohStr.toString()));
        return jdbcTemplateExtend.query(sql.toString());
    }
}
