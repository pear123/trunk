package com.arvato.cc.dao1.impl;

import com.arvato.cc.dao1.AlipayDao;
import com.arvato.cc.dao1.JdbcTemplateExtend;
import com.arvato.cc.dao1.TaobaoStoreDao;
import com.arvato.cc.model.Alipay;
import com.arvato.cc.model.TaobaoStore;
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
public class TaobaoStoreDaoImpl extends HibernateDao<TaobaoStore, Integer> implements TaobaoStoreDao {

    @Autowired
    private JdbcTemplateExtend jdbcTemplateExtend;

    public TaobaoStore getStoreCodeByAlipayNo(String alipayNo) {
        List<TaobaoStore> list = super.find("from TaobaoStore where alipayNo = ? ",new Object[]{alipayNo});
        return (null == list || list.isEmpty()) ? null : list.get(0);
    }

    @Override
    public TaobaoStore getStoreCodeByName(String name) {
        List<TaobaoStore> list = super.find("from TaobaoStore where name = ? ",new Object[]{name});
        return (null == list || list.isEmpty()) ? null : list.get(0);
    }

    @Override
    public TaobaoStore getStoreNameByCode(String code) {
        List<TaobaoStore> list = super.find("from TaobaoStore where code = ? ",new Object[]{code});
        return (null == list || list.isEmpty()) ? null : list.get(0);
    }

}
