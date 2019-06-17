package com.arvato.cc.dao1.impl;

import com.arvato.cc.constant.Constants;
import com.arvato.cc.dao1.JdbcTemplateExtend;
import com.arvato.cc.dao1.SkuDataDao;
import com.arvato.cc.dao1.TempTradeDao;
import com.arvato.cc.model.Sku;
import com.arvato.cc.model.TempTrade;
import com.arvato.cc.util.Validate;
import com.arvato.jdf.dao.HibernateDao;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: caol005
 * Date: 15-8-12
 * Time: 下午3:10
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class TempTradeDaoImpl  extends HibernateDao<TempTrade, Integer> implements TempTradeDao {

    @Autowired
    private JdbcTemplateExtend jdbcTemplateExtend;

    public void saveTempTradeList(List<TempTrade> tempTradeList) {
        super.saveOrUpdateAll(tempTradeList);
    }

    public void saveTempTrade(TempTrade tempTrade) {
        super.save(tempTrade);
    }

    /**
     * 根据批次号查询临时表，不包含虚拟发货订单
     * @param batchNo
     * @return
     */
    public List<TempTrade> findByBatchNo(int batchNo) {
        return super.find(" from TempTrade tt where tt.batchNo = ? and shippingType != ? ",batchNo, Constants.ShippingType.virtual.toString());
    }

    public Integer findMinBatchNo() {
        List<Map<String,Object>> list = jdbcTemplateExtend.query("select min(BATCH_NO) min_batchNO from temp_trade");
        if(Validate.isNullOrEmpty(list) || Validate.isNullOrEmpty(list.get(0).get("min_batchNO"))) {
            return 0;
        } else {
            return Integer.parseInt(list.get(0).get("min_batchNO").toString());
        }
    }

    public List<TempTrade> findByMaxBatchNo(int batchNo) {
        return super.find(" from TempTrade tt where tt.batchNo <= ? ",batchNo);
    }

    public int deleteByMaxBatchNo(int batchNo) {
        return getSession().createSQLQuery("delete from temp_trade where batch_no <= ?").setParameter(0,batchNo).executeUpdate();
    }

    @Override
    public TempTrade findByTid(String tid) {
        List<TempTrade> tempTradeList = super.find(" from TempTrade tt where tt.tid = ? ",tid);
        return (null == tempTradeList || tempTradeList.isEmpty()) ? null : tempTradeList.get(0);
    }

    @Override
    public void deleteTempTrade(TempTrade tempTrade) {
        super.delete(tempTrade);
    }

    @Override
    public void deleteByTid(String tidStr) {
        jdbcTemplateExtend.execute("delete from temp_trade where tid in ("+tidStr+")");
    }

    @Override
    public List<TempTrade> findByBatchNoNotVirtual(int batchNo){
        return super.find(" from TempTrade tt where tt.batchNo = ? ",batchNo);
    }
}
