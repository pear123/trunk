package com.arvato.cc.jst.jobs;

import com.arvato.cc.constant.Constants;
import com.arvato.cc.form.JdpTradeRequest;
import com.arvato.cc.log.LogMessageUtil;
import com.arvato.cc.model.CurrentOperation;
import com.arvato.cc.model.JobExec;
import com.arvato.cc.model.SyncBatch;
import com.arvato.cc.service1.*;
import com.arvato.cc.util.CommonHelper;
import com.arvato.cc.util.Validate;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Project bsh.
 * Created by zhan005 on 2015-09-15 09:50.
 * <p/>
 * Sie sind das Essen und wir sind die Jaeger.
 */
public class JstErrorReSendJob {

    private static final Log log = LogFactory.getLog(JstErrorReSendJob.class);
    private static LogMessageUtil msg = new LogMessageUtil("JST_ORDER");
    @Autowired
    private JobExecService jobExecService;
    @Autowired
    private JdpTradeService jdpTradeService;
    @Autowired
    private JobService jobService;
    @Autowired
    private CurrentOperationService currentOperationService;

    @Value("${sync.limit}")
    private Integer limitCount;  //一次同步的条数

    public void execute() throws JobExecutionException {
        log.info(msg.getStartMessage("JstErrorReSendJob"));

        JobExec jobExec = jobService.getCurrentJobExec(
                Constants.JobName.syncJstTradeReSendJob.toString());
        if (Validate.isNullOrEmpty(jobExec)) {
            log.info(msg.getProcessMessage("jobExec is null"));
            return;
        } else {
            jobExec = jobExecService.save(jobExec);
        }
        List<String> tidList = currentOperationService.findAllByN();

        log.info(msg.getProcessMessage("jobExec Start:" + jobExec.toString()));
        try {
            if(Validate.isNullOrEmpty(tidList)){
                return;
            }

            String tids = tidList.toString().replace("[","").replace("]","");
            List<Map<String, Object>> resultList = jdpTradeService.findJdpTradeByError(tids);
            if (Validate.isNullOrEmpty(resultList)) {
                log.info(msg.getProcessMessage("resultList is null"));
                return;
            }
            log.info(msg.getProcessMessage("resultList:" + resultList.size()));

            //切割需要推送的订单，每次推送规定条数
            List<List<Map<String, Object>>> splitJdpTradeLists = splitList(resultList,
                    limitCount);

            log.info(msg.getProcessMessage(
                    "fetchJstTrade splitJdpTradeLists Size:" + splitJdpTradeLists
                            .size()));
            for (int i = 0; i < splitJdpTradeLists.size(); i++) {
                List<Map<String, Object>> resultsplirtList = splitJdpTradeLists.get(i);

                log.info(
                        msg.getProcessMessage("fetchJstTrade resultsplirtList size:" + resultsplirtList.size()));
                String response = jdpTradeService.fetchJstTrade(resultsplirtList, 0);
                log.info(
                        msg.getProcessMessage("fetchJstTrade response:" + response));
                JSONObject jsonObject = JSONObject.fromObject(response);
                JdpTradeRequest request = (JdpTradeRequest) JSONObject.toBean(jsonObject, JdpTradeRequest.class);
                if (!Validate.isNullOrEmpty(request)) {
                    Map<String,CurrentOperation> cpMap = currentOperationService.findAllForMap();

                    if (!Validate.isNullOrEmpty(request.getTidList())) {
                        for (String tid : request.getTidList()) {
                            cpMap.remove(tid);
                        }
                        Iterator iter = cpMap.entrySet().iterator();
                        while (iter.hasNext()) {
                            Map.Entry entry = (Map.Entry) iter.next();
                            CurrentOperation cpEntey = (CurrentOperation)entry.getValue();
                            cpEntey.setSubModel("O");
                            currentOperationService.save(cpEntey);
                        }
                    }

                }
            }
        } catch (Exception ex) {
            log.error(msg.getErrorMessage(ex.getMessage()), ex);
        } finally {
            //update job execute record
            jobExec.setStatus(Constants.JobStatus.done.toString());
            log.info(msg.getProcessMessage("jobExec Update:" + jobExec.toString()));
            jobExecService.updateJobExec(jobExec);
        }
        log.info(msg.getEndMessage("JstErrorReSendJob"));
    }

    /**
     * 分割List
     *
     * @param list     待分割的list
     * @param pageSize 每段list的大小
     * @return List<<List<T>>
     */
    public static <T> List<List<T>> splitList(List<T> list, int pageSize) {
        int listSize = list.size(); // list的大小
        int page = (listSize + (pageSize - 1)) / pageSize;// 页数
        List<List<T>> listArray = new ArrayList<List<T>>();// 创建list数组,用来保存分割后的list
        for (int i = 0; i < page; i++) { // 按照数组大小遍历
            List<T> subList = new ArrayList<T>(); // 数组每一位放入一个分割后的list
            for (int j = 0; j < listSize; j++) {// 遍历待分割的list
                int pageIndex = ((j + 1) + (pageSize - 1)) / pageSize;// 当前记录的页码(第几页)
                if (pageIndex == (i + 1)) {// 当前记录的页码等于要放入的页码时
                    subList.add(list.get(j)); // 放入list中的元素到分割后的list(subList)
                }
                if ((j + 1) == ((j + 1) * pageSize)) {// 当放满一页时退出当前循环
                    break;
                }
            }
            listArray.add(subList);// 将分割后的list放入对应的数组的位中
        }
        return listArray;
    }
}
