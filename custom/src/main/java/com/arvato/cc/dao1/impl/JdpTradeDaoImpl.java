package com.arvato.cc.dao1.impl;

import com.arvato.cc.dao1.HibernateDao;
import com.arvato.cc.dao1.JdbcTemplateExtend;
import com.arvato.cc.dao1.JdpTradeDao;
import com.arvato.cc.model.JdpTrade;
import com.arvato.cc.util.JdbcUtil;
import com.arvato.cc.util.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * Created by HUPE002 on 2015/8/11.
 */
@Repository
public class JdpTradeDaoImpl extends HibernateDao<JdpTrade,Integer> implements JdpTradeDao{

    @Autowired
    private JdbcTemplateExtend jdbcTemplateExtend;

    public void saveAllJdpTrade(List<JdpTrade> list) {
        for(JdpTrade jdpTrade : list){
            super.save(jdpTrade);
        }
    }

 @Override
    public JdpTrade saveJdpTrade(JdpTrade jdpTrade){


       return super.save(jdpTrade);
    }

 @Override
    public List<JdpTrade> findByBatchNo(int batchNo) {
        return super.find(" from JdpTrade dt where dt.batchNo = ?",batchNo);  //To change body of implemented methods use File | Settings | File Templates.
    }

 @Override
    public Integer findMaxBatchNo() {
        List<Map<String,Object>> list = jdbcTemplateExtend.query("select max(batch_no) max_batchNO from jdp_trade");
        if(Validate.isNullOrEmpty(list) || Validate.isNullOrEmpty(list.get(0).get("max_batchNO"))) {
            return 0;
        } else {
            return Integer.parseInt(list.get(0).get("max_batchNO").toString());
        }
    }

 @Override
 public List<Map<String, Object>> fetchJstTrade(StringBuffer sql)
         throws SQLException {
//  StringBuffer sql = new StringBuffer(
//          "select tid,status,type,seller_nick,buyer_nick,created,modified,jdp_hashcode,jdp_response,jdp_created,jdp_modified from jdp_tb_trade where 1=1");
//  if (!Validate.isNullOrEmpty(params.get(0))) {
//   Timestamp startTime = (Timestamp) params.get(0);
//   sql.append(" and jdp_modified > '" + startTime + "'");
//  }
//  if (!Validate.isNullOrEmpty(params.get(2))) {
//   Timestamp endTime = (Timestamp) params.get(2);
//   sql.append(" and jdp_modified < '" + endTime + "'");
//  }
//  sql.append(" order by jdp_modified ");
//  if (!Validate.isNullOrEmpty(params.get(1))) {
//   Integer limitCount = (Integer) params.get(1);
//   sql.append(" limit " + limitCount);
//  }
  List<Map<String, Object>> list =  JdbcUtil.getJdbcUtilSingle()
          .findModeResult(sql.toString(), null);
  return list;
 }

 @Override
 public JdpTrade findByTid(String tid) {
        List<JdpTrade> jdpTradeList = super.find(" from JdpTrade where tid = ?",tid);
        return (null == jdpTradeList || jdpTradeList.isEmpty()) ? null:jdpTradeList.get(0);
    }

    @Override
    public void deleteByTid(JdpTrade jdpTrade) {
        super.delete(jdpTrade);
    }

    @Override
    public void deleteByTid(String tidStr) {
        jdbcTemplateExtend.execute("delete from jdp_trade where tid in ("+tidStr+")");
    }
    @Override
    public List<Map<String, Object>> findAllTid(){
        return jdbcTemplateExtend.query("select tid from jdp_trade");
    }

    @Override
    public List<JdpTrade> findAll() {
     return super.getAll();
    }

    @Override
    public String findTradeNum(String sql) throws SQLException {
        String num = super.getSession().createSQLQuery(sql).uniqueResult().toString();
        return num;
    }

    @Override
    public List<Object[]> findCompareDate(String sql) throws SQLException {
        return super.getSession().createSQLQuery(sql).list();
    }
}
