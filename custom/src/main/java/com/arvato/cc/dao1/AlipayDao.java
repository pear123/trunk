package com.arvato.cc.dao1;

import com.arvato.cc.model.Alipay;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: caol005
 * Date: 15-7-16
 * Time: 下午2:02
 * To change this template use File | Settings | File Templates.
 */
public interface AlipayDao {
    /**
     * get alipay by financialSerialNum and serviceSerialNum
     * @param financialSerialNum
     * @param serviceSerialNum
     * @return
     */
    List<Alipay> getByFinanceSerialAndServiceSerial(String financialSerialNum,String serviceSerialNum);

    List<Map<String,Object>> getAlipayUniqueKeys();

    void saveAlipay(Alipay alipay);

    /**
     * one order may has many alipay records
     * @param tid
     * @return
     */
    List<Alipay> findByTid(String tid);

    List<Alipay> findByAlipayNo(String alipayNo);

    List<Alipay> getFinanceAlipay();
}
