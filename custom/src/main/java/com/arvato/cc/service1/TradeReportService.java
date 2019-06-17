package com.arvato.cc.service1;

import com.arvato.cc.model.JobExec;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;

/**
 * Created with IntelliJ IDEA.
 * User: caol005
 * Date: 15-8-27
 * Time: 下午9:26
 * To change this template use File | Settings | File Templates.
 */
public interface TradeReportService {
    JobExec getResultByBatch(JobExec jobExec);
    String generateTradeReport(int batchNo) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException, ParseException;
}
