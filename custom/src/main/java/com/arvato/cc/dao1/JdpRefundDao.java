package com.arvato.cc.dao1;

import com.arvato.cc.model.JdpRefund;
import com.arvato.cc.model.JdpTrade;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by HUPE002 on 2015/8/11.
 */
public interface JdpRefundDao {
//   void saveAllJdpTrade(List<JdpTrade> list);
    JdpRefund saveJdpRefund(JdpRefund jdpRefund);

    /**
     * find list by batchNo
     * add by tracy
//     * @param batchNo
     * @return
     */
//    List<JdpTrade> findByBatchNo(int batchNo);

    Integer findMaxBatchNo();

    List<Map<String, Object>> fetchJstRefund(StringBuffer sql) throws SQLException;

//    JdpTrade findByTid(String tid);

//    void deleteByTid(JdpTrade jdpTrade);

//    void deleteByTid(String tidStr);

//    List<Map<String, Object>> findAllTid();

//    List<JdpTrade> findAll();
}
