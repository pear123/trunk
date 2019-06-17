package com.arvato.cc.dao1.impl;

import com.arvato.cc.dao1.DeliveryReportDao;
import com.arvato.cc.dao1.JdbcTemplateExtend;
import com.arvato.cc.model.ExpDelivery;
import com.arvato.jdf.dao.HibernateDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ZHAN405
 * Date: 15-8-19
 * Time: 下午2:04
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class DeliveryReportDaoImpl extends HibernateDao<ExpDelivery, String> implements DeliveryReportDao {
    @Autowired
    private JdbcTemplateExtend jdbcTemplateExtend;

    public List<ExpDelivery> getDeliveryReportList(String hql){
        List<ExpDelivery> queryExpDelivery = super.find(hql);
        return ( null == queryExpDelivery || queryExpDelivery.isEmpty() ) ? null : queryExpDelivery;
    }

    @Override
    public List<Map<String, Object>> getDeliveryReportListByView(Map<String, String> queryParams) {
        StringBuffer sql = new StringBuffer("select distinct delivery.* , invoiceName,(ali.in_fee - delivery.point_fee / 100) inFee  from ( " +
                " select po_number,group_concat(DISTINCT gitNo) invoiceName from (SELECT * FROM upd_invoice AS b WHERE b.last_time>=(SELECT MAX(last_time) FROM upd_invoice)) invoice group by po_number " +
                " ) invoice , exp_delivery delivery ,alipay_trans ali" +
                " where invoice.po_number = concat(delivery.order_id, '天猫') and ali.service_serial_num = delivery.alipay_no ");
        if (StringUtils.isNotBlank(queryParams.get("brand"))) {
            sql.append(" and delivery.store_id =  '" +  queryParams.get("brand") + "'");
        }
        if (StringUtils.isNotBlank( queryParams.get("startTime"))) {
            sql.append(" and delivery.pricingDate >= '" +  queryParams.get("startTime") + "'");
        }
        if (StringUtils.isNotBlank( queryParams.get("endTime"))) {
            sql.append(" and delivery.pricingDate <= '" +  queryParams.get("endTime") + "'");
        }
        if (StringUtils.isNotBlank( queryParams.get("orderNum"))) {
            sql.append(" and delivery.order_id =  '" +  queryParams.get("orderNum") + "'");
        }
        if (StringUtils.isNotBlank(queryParams.get("invoiceNo"))) {
            sql.append(" and invoiceName like  '%" + queryParams.get("invoiceNo") + "%'");
        }
        return jdbcTemplateExtend.query(sql.toString());
    }
}
