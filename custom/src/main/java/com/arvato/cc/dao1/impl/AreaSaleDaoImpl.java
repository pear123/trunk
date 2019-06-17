package com.arvato.cc.dao1.impl;

import com.arvato.cc.constant.Constants;
import com.arvato.cc.dao1.AreaSaleDao;
import com.arvato.cc.dao1.JdbcTemplateExtend;
import com.arvato.cc.dao1.SaleStructureDao;
import com.arvato.cc.util.Validate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
public class AreaSaleDaoImpl implements AreaSaleDao
{


    private static final Log log = LogFactory.getLog(AreaSaleDaoImpl.class);
    @Autowired
    private JdbcTemplateExtend jdbcTemplateExtend;

    /**
     * sku,quantity,area
     * @return
     */
    public List<Map<String, Object>> findAreaSale(Map<String, String> queryParams) {
//        StringBuffer sql = new StringBuffer("select s.cid cid,oh.matrn sku_id,a.code code,sum(num) num,s.SAPPRICE sapprice from order_history oh left join trade_history th on oh.trade_sys_id = th.trade_sys_id inner join area a on oh.store_code = a.oname left join sku s on s.matnr = oh.matrn where s.status = 'ACTIVE' and a.parent_id != 0  ");
        StringBuffer sql = new StringBuffer("select s.cid cid,oh.matnr sku_id,a.code code,sum(oh.num) num,s.SAPPRICE sapprice from jst_order oh left join jst_trade th on oh.trade_sys_id = th.trade_sys_id left join store on store.ORDER_STORE = oh.store_code inner join area a on oh.store_code = a.oname left join area b on a.parent_id = b.id left join sku s on s.matnr = oh.matnr where s.status = 'ACTIVE' and a.parent_id != 0 ");
        sql.append(" and th.status in ('" + Constants.TradeStatus.WAIT_SELLER_SEND_GOODS.toString() + "','" + Constants.TradeStatus.WAIT_BUYER_CONFIRM_GOODS.toString() + "','" + Constants.TradeStatus.TRADE_FINISHED.toString() + "','" + Constants.TradeStatus.TRADE_CLOSED.toString() + "')");
        sql.append(" and th.shipping_type != '"+Constants.ShippingType.virtual.toString()+"'");
        if(!Validate.isNullOrEmpty(queryParams)){
            String value = queryParams.get("storeId");
            if(!Validate.isNullOrEmpty(value)){
                sql.append(" and th.store_id =  '" + value + "'");
            }
            value = queryParams.get("category");
            if(!Validate.isNullOrEmpty(value)){
                sql.append(" and s.cid =  '" + value + "'");
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
                sql.append(" and s.matnr like  '%" + value + "%'");
            }
        }
        sql.append(" group by s.cid,oh.matnr,a.code order by s.cid,oh.matnr");
        System.out.println("method : findAreaSale");
        System.out.println("sql:"+sql.toString());
        return jdbcTemplateExtend.query(sql.toString());
    }

    /**
     *
     * @return
     */
    public List<Map<String, Object>> findArea() {
        String sql = "select * from (( " +
                " select parent.id id,parent.code code,parent.name name,parent.oname oname,child.code pcode,child.name pname,child.oname poname from area child " +
                " left join area parent on child.id = parent.parent_id where parent.parent_id !=0 ) union all( " +
                " select parent.id,parent.code code,parent.name name,parent.oname oname,parent.code pcode,parent.name pname,parent.oname poname from area parent " +
                " where parent.parent_id =0)) as unionT order by id ";
//        return jdbcTemplateExtend.query("select b.code code,b.name name,b.oname oname,a.code pcode,a.name pname,a.oname poname from area a left outer join area b on a.id = b.parent_id where b.code is not null order by b.id");

        System.out.println("method : findArea");
        System.out.println("sql:"+sql);
        return jdbcTemplateExtend.query(sql);
    }

    public List<Map<String,Object>> findByCategory(Map<String, String> queryParams){
        StringBuffer sql = new StringBuffer("select cid , sum(num * SAPPRICE) total,sum(num) num from (  select s.cid cid,oh.matnr sku_id,sum(oh.num) num,s.SAPPRICE sapprice from jst_order oh left join jst_trade th on oh.trade_sys_id = th.trade_sys_id left join store on store.ORDER_STORE = oh.store_code left join area a on oh.store_code = a.oname left join area b on a.parent_id = b.id  left join sku s on s.matnr = oh.matnr where s.status = 'ACTIVE' and a.parent_id !=0  ");

        StringBuffer ohStr = new StringBuffer(" and th.status in ('"+ Constants.TradeStatus.WAIT_SELLER_SEND_GOODS.toString()+"','"+ Constants.TradeStatus.WAIT_BUYER_CONFIRM_GOODS.toString()+"','"+ Constants.TradeStatus.TRADE_FINISHED.toString()+"','"+ Constants.TradeStatus.TRADE_CLOSED.toString()+"')");
        ohStr.append(" and th.shipping_type != '"+Constants.ShippingType.virtual.toString()+"'");

        if(!Validate.isNullOrEmpty(queryParams)){
            String value = queryParams.get("storeId");
            if(!Validate.isNullOrEmpty(value)){
                ohStr.append(" and th.store_id =  '" + value + "'");
            }
            value = queryParams.get("category");
            if(!Validate.isNullOrEmpty(value)){
                ohStr.append(" and s.cid =  '" + value + "'");
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
                ohStr.append(" and s.matnr like  '%" + value + "%'");
            }
        }
        sql.append(ohStr.toString());
        sql.append(" group by s.cid,oh.matnr ) a group by cid");
        System.out.println("method : findByCategory");
        System.out.println("sql:"+sql.toString());
        return jdbcTemplateExtend.query(sql.toString());

    }

    public List<Map<String, Object>> findByOtherCategory(Map<String, String> queryParams) {
//        String sql = " select sku.cid as cid,b.code,sum(jo.num) as num " +
//                "from jst_order jo " +
//                "inner join area a on jo.store_code = a.oname " +
//                "inner join area b on a.parent_id = b.id " +
//                "left join Sku sku on jo.matnr = sku.matnr " +
//                "where sku.status = 'ACTIVE' and sku.cid in ('洗衣机','冰箱','厨卫') group by sku.cid,a.parent_id";

        StringBuffer sql = new StringBuffer("select sku.cid as cid,b.code,sum(oh.num) as num from jst_order oh left join jst_trade th on oh.trade_sys_id = th.trade_sys_id left join store on store.ORDER_STORE = oh.store_code " +
                " left join area a on oh.store_code = a.oname left join area b on a.parent_id = b.id left join Sku sku on oh.matnr = sku.matnr " +
                " where sku.status = 'ACTIVE' and sku.cid in ('洗衣机','冰箱','厨卫') and a.parent_id !=0 ");
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
        sql.append(" group by sku.cid,a.parent_id");
        System.out.println("method : findByOtherCategory");
        System.out.println("sql:" + sql.toString());
        return jdbcTemplateExtend.query(sql.toString());
    }


    public List<Map<String, Object>> findBySummaryBottom(Map<String, String> queryParams) {
//        String sql = " select sku.cid as cid,b.code,sum(jo.num) as num " +
//                "from jst_order jo " +
//                "inner join area a on jo.store_code = a.oname " +
//                "inner join area b on a.parent_id = b.id " +
//                "left join Sku sku on jo.matnr = sku.matnr " +
//                "where sku.status = 'ACTIVE' and sku.cid in ('洗衣机','冰箱','厨卫') group by sku.cid,a.parent_id";

        StringBuffer sql = new StringBuffer("select sku.cid as cid,b.code,sum(oh.num) as num from jst_order oh left join jst_trade th on oh.trade_sys_id = th.trade_sys_id left join store on store.ORDER_STORE = oh.store_code " +
                " left join area a on oh.store_code = a.oname left join area b on a.parent_id = b.id left join Sku sku on oh.matnr = sku.matnr " +
                " where sku.status = 'ACTIVE' and a.parent_id !=0  ");
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
        sql.append(" group by sku.cid,a.parent_id");
        System.out.println("method : findBySummaryBottom");
        System.out.println("sql:" + sql.toString());
        return jdbcTemplateExtend.query(sql.toString());
    }


}
