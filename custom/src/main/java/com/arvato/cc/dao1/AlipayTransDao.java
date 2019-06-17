package com.arvato.cc.dao1;

import com.arvato.cc.model.Alipay;
import com.arvato.cc.model.AlipayTrans;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: caol005
 * Date: 15-7-16
 * Time: 下午2:02
 * To change this template use File | Settings | File Templates.
 */
public interface AlipayTransDao {
    List<Map<String,Object>> getAlipayTransUniqueSet();

    List<Map<String,Object>> getAlipayTransServiceUniqueSet();

    List<Map<String,Object>> getAlipayTransSum();

    void saveAlipayTrans(AlipayTrans alipayTrans);

    AlipayTrans getByServiceSerialNum(String serviceSerialNum);

    List<AlipayTrans> getByParams(Map<String,String> params);

    void deleteByServiceSerialNum(String serviceSerialNum);
}
