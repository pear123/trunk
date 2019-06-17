package com.arvato.platform.jobs;

import com.arvato.cc.form.CheckJstTrade;
import com.arvato.cc.log.LogMessageUtil;
import com.arvato.cc.service1.JdpTradeService;
import com.arvato.cc.util.Validate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Project bsh.
 * Created by zhan005 on 2015-10-23 11:00.
 * <p/>
 * Sie sind das Essen und wir sind die Jaeger.
 */
public class CheckTradeJob {
    private static final Log log = LogFactory.getLog(CheckTradeJob.class);
    private static LogMessageUtil msg = new LogMessageUtil("JOB");

    @Autowired
    private JdpTradeService jdpTradeService;
    public void execute() throws JobExecutionException {
        CheckJstTrade checkJstTrade = jdpTradeService.checkJstTrade();
        if(!Validate.isNullOrEmpty(checkJstTrade)){
            String findTime = checkJstTrade.getFindTime();
            String jstTradeNum = checkJstTrade.getJstTradeNum();
            String ccTradeNum  = checkJstTrade.getCcTradeNum();
            String jdpTrades = "无";
            if(!Validate.isNullOrEmpty(checkJstTrade.getJdpTrades())){
                jdpTrades = checkJstTrade.getJdpTrades().toString();
            }

            log.error(msg.getErrorMessage("\r\n这不是错误是监控漏单的数据：\r\n数据检查时间：" + findTime + "\r\n 聚石塔Trade数量：" + jstTradeNum + "\r\n Channel Connector数量:" + ccTradeNum + "\r\n 漏单数据:" + jdpTrades + "\r\n"));
        }

    }



}
