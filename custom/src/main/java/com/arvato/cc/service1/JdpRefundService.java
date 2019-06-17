package com.arvato.cc.service1;

import com.arvato.cc.form.JdpRefundRequest;
import com.arvato.cc.form.JdpTradeRequest;
import com.arvato.cc.model.JdpRefund;
import com.arvato.cc.model.JdpTrade;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface JdpRefundService {

    String fetchJstRefund(List<Map<String, Object>> resultList, Integer batchNo) throws Exception;

    List<Map<String, Object>> findJdpRefundByBatch(Calendar startTime, Date endTime) throws SQLException;

//    List<JdpTrade> findByBatchNo(int batchNo);
//
//    Map<String,JdpTrade> findAllByMap();
//
//    List<Map<String,Object>> findJdpTradeByError(String tids) throws SQLException;
//
    JdpRefundRequest saveSyncJdpRefund(List<JdpRefund> jdpRefundList, Integer batchNo);

}
