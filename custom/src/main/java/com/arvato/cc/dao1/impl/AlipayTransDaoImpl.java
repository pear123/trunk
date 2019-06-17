package com.arvato.cc.dao1.impl;

import com.arvato.cc.dao1.AlipayDao;
import com.arvato.cc.dao1.AlipayTransDao;
import com.arvato.cc.dao1.JdbcTemplateExtend;
import com.arvato.cc.model.Alipay;
import com.arvato.cc.model.AlipayTrans;
import com.arvato.cc.util.CommonHelper;
import com.arvato.cc.util.Validate;
import com.arvato.jdf.dao.HibernateDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: caol005
 * Date: 15-7-16
 * Time: 下午2:03
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class AlipayTransDaoImpl extends HibernateDao<AlipayTrans, Integer> implements AlipayTransDao {

    @Autowired
    private JdbcTemplateExtend jdbcTemplateExtend;

    public List<Map<String,Object>> getAlipayTransUniqueSet() {
        return jdbcTemplateExtend.query("select financial_Serial_Num from ALIPAY_TRANS");
    }

    public List<Map<String,Object>> getAlipayTransServiceUniqueSet() {
        return jdbcTemplateExtend.query("select service_Serial_Num,tid from ALIPAY_TRANS");
    }

    @Override
    public List<Map<String, Object>> getAlipayTransSum() {
        return jdbcTemplateExtend.query("select distinct service_Serial_Num,IN_FEE from ALIPAY_TRANS");
    }

    @Override
    public void saveAlipayTrans(AlipayTrans alipayTrans) {
        super.save(alipayTrans);
    }

    public AlipayTrans getByServiceSerialNum(String serviceSerialNum){
        List<AlipayTrans> list = super.find("from AlipayTrans where serviceSerialNum = ? order by createTime desc",serviceSerialNum);
        return (null == list || list.isEmpty()) ? null : list.get(0);
    }

    @Override
    public List<AlipayTrans> getByParams(Map<String, String> params) {
        StringBuffer hql = new StringBuffer(" from AlipayTrans where 1=1 ");
        if(!Validate.isNullOrEmpty(params)){
            String value = params.get("startTime");
            if(!Validate.isNullOrEmpty(value)){
                hql.append(" and createTime >=  '" + CommonHelper.getDateBeforeMonth(value,2) + "'");
            }
            value = params.get("endTime");
            if(!Validate.isNullOrEmpty(value)){
                hql.append(" and createTime <=  '" + CommonHelper.getDateAfterMonth(value,2) + "'");
            }
        }
        List<AlipayTrans> alipayTranses = super.find(hql.toString());
        return ( null == alipayTranses || alipayTranses.isEmpty() ) ? null : alipayTranses;
    }

    @Override
    public void deleteByServiceSerialNum(String serviceSerialNum) {
        jdbcTemplateExtend.execute("delete from alipay_trans where service_Serial_Num =  '"+serviceSerialNum+"'");

    }
}
