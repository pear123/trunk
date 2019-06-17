package com.arvato.cc.service1;

import com.arvato.cc.form.CheckJstTrade;
import com.arvato.cc.form.JdpTradeRequest;
import com.arvato.cc.model.JdpTrade;
import com.arvato.cc.model.JobExec;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface JdpTradeService {

    String fetchJstTrade(List<Map<String, Object>> resultList,Integer batchNo) throws Exception;

    List<Map<String, Object>> findJdpTradeByBatch(Calendar startTime,Date endTime) throws SQLException;

    List<JdpTrade> findByBatchNo(int batchNo);

    Map<String,JdpTrade> findAllByMap();

    List<Map<String,Object>> findJdpTradeByError(String tids) throws SQLException;

    JdpTradeRequest saveSyncJdpTrade(List<JdpTrade> jdpTradeList,Integer batchNo);

    List<Map<String, Object>> findJdpTradeBySeller(Date startTime,Date endTime,String seller,String tid) throws SQLException;

    CheckJstTrade checkJstTrade();

    JobExec getResultByBatch(JobExec jobExec);
}
