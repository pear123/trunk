package com.arvato.cc.jst.jobs;

import com.arvato.cc.constant.Constants;
import com.arvato.cc.form.JdpRefundRequest;
import com.arvato.cc.form.JdpTradeRequest;
import com.arvato.cc.log.LogMessageUtil;
import com.arvato.cc.model.CurrentOperation;
import com.arvato.cc.model.JobExec;
import com.arvato.cc.model.RefundSyncBatch;
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
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: caol005
 * Date: 15-8-13
 * Time: 下午6:37
 * To change this template use File | Settings | File Templates.
 */
public class JstRefundClientJob {
    private static final Log log = LogFactory.getLog(JstRefundClientJob.class);
    private static LogMessageUtil msg = new LogMessageUtil("JST_ORDER");
    @Autowired
    private JobExecService jobExecService;
    @Autowired
    private JdpRefundService jdpRefundService;
    @Autowired
    private JobService jobService;
    @Autowired
    private RefundSyncBatchService syncBatchService;
    @Autowired
    private TradeRefundErrorService tradeRefundErrorService;

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Value("${sync.limit}")
    private Integer limitCount;  //一次同步的条数

    public void execute() throws JobExecutionException {
        log.info(msg.getStartMessage("JstRefundClientJob"));
        //查询当前job的执行记录，状态为processing时，等待下次执行
        JobExec jobExec = jobService.getCurrentJobExec(Constants.JobName.syncJstRefundJob.toString());
        if (Validate.isNullOrEmpty(jobExec)) {
            log.info(msg.getProcessMessage("jobExec is null"));
            return;
        } else {
            jobExec = jobExecService.save(jobExec);
        }
        //查询最大的执行批次及时间
        RefundSyncBatch syncBatch = syncBatchService.getMaxBatch();
        Integer batchNo = syncBatch.getBatchNo();

        batchNo++;
        log.info(msg.getProcessMessage("jobExec Start:" + jobExec.toString()));
        Calendar startTime = null;
        Date endTime = null;
        try {
            //查询上次同步的时间，第一次则为空，根据限制的条数查询
            Timestamp lastSyncTime = null;
            if (!Validate.isNullOrEmpty(syncBatch)) {
                log.info(msg.getProcessMessage("findJdpRefundByBatch syncBatch:" + syncBatch.toString()));
                lastSyncTime = syncBatch.getLastSyncTime();
                log.info(msg.getProcessMessage("findJdpRefundByBatch lastSyncTime:" + CommonHelper.DateFormat(lastSyncTime, "yyyy-MM-dd HH:mm:ss")));
            }

            //获取开始时间 = 上次拉单时间  如果第一次拉单直接获取当前时间
            long hour = 0;
            if (!Validate.isNullOrEmpty(lastSyncTime)) {
                Date start = lastSyncTime;
                //初始化Calender对象 计算开始拉单见时间 往前推5分钟
                startTime = Calendar.getInstance();
                startTime.setTime(start);
                startTime.add(Calendar.MINUTE, -5);
                endTime = new Date();
                //获取当前时间
                long dq = System.currentTimeMillis();
                //计算当前时间和开始时间相差多少小时
                hour = (dq - startTime.getTime().getTime()) / (3600 * 1000);
                //结束时间大于当前时间。跳过拉单
                if (endTime.getTime() > dq) {
                    log.info(msg.getProcessMessage("pull order over"));
                } else {
                    //如果开始时间和结束时间差距超过12小时，将结束时间往后推12小时
                    if (hour > 12) {
                        Calendar calendarEndTime = Calendar.getInstance();
                        calendarEndTime.setTime(startTime.getTime());
                        calendarEndTime.add(Calendar.HOUR, +12);
                        endTime = calendarEndTime.getTime();
                    }
                }
            }

            if(Validate.isNullOrEmpty(endTime)){
                endTime = new Date();
            }

            List<Map<String, Object>> resultList = jdpRefundService.findJdpRefundByBatch(startTime, endTime);
            if (Validate.isNullOrEmpty(resultList)) {
                log.info(msg.getProcessMessage("resultList is null"));
                return;
            }
            log.info(msg.getProcessMessage("resultList:" + resultList.size()));

            //切割需要推送的订单，每次推送规定条数
            List<List<Map<String, Object>>> splitJdpRefundLists = splitList(resultList,limitCount);

            log.info(msg.getProcessMessage("fetchJstRefund splitJdpRefundLists Size:" + splitJdpRefundLists.size()));

            for (int i = 0; i < splitJdpRefundLists.size(); i++) {
                List<Map<String, Object>> resultsplirtList = splitJdpRefundLists.get(i);
                log.info(msg.getProcessMessage("fetchJstRefund resultsplirtList size:" + resultsplirtList.size()));
                String response = jdpRefundService.fetchJstRefund(resultsplirtList, batchNo);
                log.info(msg.getProcessMessage("fetchJstRefund response:" + response));
                JSONObject jsonObject = JSONObject.fromObject(response);
                JdpRefundRequest request = (JdpRefundRequest) JSONObject.toBean(jsonObject, JdpRefundRequest.class);
                if (!Validate.isNullOrEmpty(request)) {
                    if (!Validate.isNullOrEmpty(request.getRefundIdList())) {
//                        Map<String,CurrentOperation> cpMap = tradeRefundErrorService.findAllForMap();
                        for (String refundId : request.getRefundIdList()) {
//                            CurrentOperation cp = cpMap.get(cpMap);
//                            if(Validate.isNullOrEmpty(cp)){
                                tradeRefundErrorService.saveTradeRefundError(refundId);
//                            }
                        }
                    }
                }
            }

        } catch (Exception ex) {
            log.error(msg.getErrorMessage(ex.getMessage()), ex);
        } finally {
            RefundSyncBatch syncBatch1 = new RefundSyncBatch();
            syncBatch1.setBatchNo(batchNo);
            syncBatch1.setLastSyncTime(Timestamp.valueOf(sdf.format(endTime)));
            syncBatchService.saveRefundSyncBatch(syncBatch1);
            //update job execute record
            jobExec.setStatus(Constants.JobStatus.done.toString());
            log.info(msg.getProcessMessage("jobExec Update:" + jobExec.toString()));
            jobExecService.updateJobExec(jobExec);
        }
        log.info(msg.getEndMessage("JstRefundClientJob"));
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
