package com.arvato.cc.dao1.impl;

import com.arvato.cc.dao1.AlipayDao;
import com.arvato.cc.dao1.JdbcTemplateExtend;
import com.arvato.cc.model.Alipay;
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
public class AlipayDaoImpl extends HibernateDao<Alipay, Integer> implements AlipayDao {

    @Autowired
    private JdbcTemplateExtend jdbcTemplateExtend;

    public List<Alipay> getByFinanceSerialAndServiceSerial(String financialSerialNum, String serviceSerialNum) {
        return super.find("from Alipay where financialSerialNum = ? and serviceSerialNum = ?",new Object[]{financialSerialNum,serviceSerialNum});
    }

    public List<Map<String,Object>> getAlipayUniqueKeys() {
        return jdbcTemplateExtend.query("select financial_Serial_Num,service_Serial_Num from alipay");
    }

    public void saveAlipay(Alipay alipay) {
        super.save(alipay);
    }

    public List<Alipay> findByTid(String tid) {
        return super.find(" from Alipay ali where ali.orderNum = ? order by createTime desc" ,tid);
    }

    @Override
    public List<Alipay> findByAlipayNo(String alipayNo) {
        return super.find(" from Alipay ali where ali.serviceSerialNum = ? order by createTime" ,alipayNo);
    }

    @Override
    public List<Alipay> getFinanceAlipay() {
        return super.find(" from Alipay ali where ali.tradeType = '2' ");
    }


}
