package com.arvato.cc.dao1.impl;

import com.arvato.cc.dao1.JdbcTemplateExtend;
import com.arvato.cc.dao1.JstTradeDao;
import com.arvato.cc.model.JstTrade;
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
 * Date: 15-8-11
 * Time: 下午6:47
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class JstTradeDaoImpl extends HibernateDao<JstTrade, String> implements JstTradeDao {

    @Autowired
    private JdbcTemplateExtend jdbcTemplateExtend;

    public void saveJstTrade(JstTrade jstTrade) {
        super.save(jstTrade);
    }

    public void saveJstTrade(List<JstTrade> jstTradeList) {
        saveOrUpdateAll(jstTradeList);
    }

    @Override
    public List<Map<String, Object>>  findAllAliayNos(){
        return jdbcTemplateExtend.query("select alipay_no from jst_trade");
    }

    @Override
    public JstTrade getByServiceSerialNum(String serviceSerialNum) {
        List<JstTrade> list = super.find(" from JstTrade where alipayNo = ? ",serviceSerialNum);
        return (null == list || list.isEmpty()) ? null :list.get(0);
    }

    @Override
    public void deleteByTid(String tid) {
        List<JstTrade> list = super.find(" from JstTrade where tid = ?",tid);
        if(!Validate.isNullOrEmpty(list)){
            for(JstTrade jstTrade : list){
                super.delete(jstTrade);
            }
        }
    }

    @Override
    public String findByServiceSerialNum(String serviceSerialNum) {
        List<Map<String,Object>> list = jdbcTemplateExtend.query("select tid from jst_trade where ALIPAY_NO = '"+serviceSerialNum+"'");
        if(!Validate.isNullOrEmpty(list)){
            return CommonHelper.getString(list.get(0).get("tid"));
        }
        return "";
    }
}
