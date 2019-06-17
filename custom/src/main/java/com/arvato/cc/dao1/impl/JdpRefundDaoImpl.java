package com.arvato.cc.dao1.impl;

import com.arvato.cc.dao1.HibernateDao;
import com.arvato.cc.dao1.JdbcTemplateExtend;
import com.arvato.cc.dao1.JdpRefundDao;
import com.arvato.cc.dao1.JdpTradeDao;
import com.arvato.cc.model.JdpRefund;
import com.arvato.cc.model.JdpTrade;
import com.arvato.cc.util.JdbcUtil;
import com.arvato.cc.util.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by HUPE002 on 2015/8/11.
 */
@Repository
public class JdpRefundDaoImpl extends HibernateDao<JdpRefund,Integer> implements JdpRefundDao {

    @Autowired
    private JdbcTemplateExtend jdbcTemplateExtend;

//    public void saveAllJdpTrade(List<JdpTrade> list) {
//        for(JdpTrade jdpTrade : list){
//            super.save(jdpTrade);
//        }
//    }

        @Override
        public JdpRefund saveJdpRefund(JdpRefund jdpRefund){
           return super.save(jdpRefund);
        }

// @Override
//    public List<JdpTrade> findByBatchNo(int batchNo) {
//        return super.find(" from JdpTrade dt where dt.batchNo = ?",batchNo);  //To change body of implemented methods use File | Settings | File Templates.
//    }

 @Override
    public Integer findMaxBatchNo() {
        List<Map<String,Object>> list = jdbcTemplateExtend.query("select max(batch_no) max_batchNO from jdp_refund");
        if(Validate.isNullOrEmpty(list) || Validate.isNullOrEmpty(list.get(0).get("max_batchNO"))) {
            return 0;
        } else {
            return Integer.parseInt(list.get(0).get("max_batchNO").toString());
        }
    }

    @Override
    public List<Map<String, Object>> fetchJstRefund(StringBuffer sql)
             throws SQLException {
      List<Map<String, Object>> list = JdbcUtil.getJdbcUtilSingle().findModeResult(sql.toString(), null);
      return list;
    }

// @Override
// public JdpTrade findByTid(String tid) {
//        List<JdpTrade> jdpTradeList = super.find(" from JdpTrade where tid = ?",tid);
//        return (null == jdpTradeList || jdpTradeList.isEmpty()) ? null:jdpTradeList.get(0);
//    }

//    @Override
//    public void deleteByTid(JdpTrade jdpTrade) {
//        super.delete(jdpTrade);
//    }
//
//    @Override
//    public void deleteByTid(String tidStr) {
//        jdbcTemplateExtend.execute("delete from jdp_trade where tid in ("+tidStr+")");
//    }
//    @Override
//    public List<Map<String, Object>> findAllTid(){
//        return jdbcTemplateExtend.query("select tid from jdp_trade");
//    }
//
//    @Override
//    public List<JdpTrade> findAll() {
//     return super.getAll();
//    }
}
