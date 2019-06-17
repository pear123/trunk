package com.arvato.platform.jobs;

import com.arvato.cc.constant.Constants;
import com.arvato.cc.log.LogMessageUtil;
import com.arvato.cc.model.JobExec;
import com.arvato.cc.model.SyncBatch;
import com.arvato.cc.service1.JdpTradeService;
import com.arvato.cc.service1.JobExecService;
import com.arvato.cc.service1.JobService;
import com.arvato.cc.service1.SyncBatchService;
import com.arvato.cc.util.CommonHelper;
import com.arvato.cc.util.Validate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: caol005
 * Date: 15-8-13
 * Time: 下午6:37
 * To change this template use File | Settings | File Templates.
 */
public class JstClientJob {
 private static final Log log = LogFactory.getLog(JstClientJob.class);
 private static LogMessageUtil msg = new LogMessageUtil("JST_ORDER");
 @Autowired
 private JobExecService jobExecService;
 @Autowired
 private JdpTradeService jdpTradeService;
 @Autowired
 private JobService jobService;
 @Autowired
 private SyncBatchService syncBatchService;

 @Value("${sync.limit}")
 private Integer limitCount;  //一次同步的条数

 public void execute() throws JobExecutionException {
//  log.info(msg.getStartMessage("JstClientJob"));
//
//  JobExec jobExec = jobService.getCurrentJobExec(
//          Constants.JobName.syncJstTradeJob.toString());
//  if (Validate.isNullOrEmpty(jobExec)) {
//   log.info(msg.getProcessMessage("jobExec is null"));
//   return;
//  }
//  SyncBatch syncBatch = syncBatchService.getMaxBatch();
//  Integer batchNo = 0;
//  if (!Validate.isNullOrEmpty(syncBatch)) {
//   log.info(msg.getProcessMessage(
//           "fetchJstTrade syncBatch:" + syncBatch.toString()));
//   batchNo = syncBatch.getBatchNo();
//  }
//  log.info(msg.getProcessMessage("jobExec Start:" + jobExec.toString()));
//  try {
//   List<Map<String, Object>> resultList = jdpTradeService
//           .findJdpTradeByBatch(syncBatch);
//   if (Validate.isNullOrEmpty(resultList)) {
//    log.info(msg.getProcessMessage("resultList is null"));
//    return;
//   }
//   log.info(msg.getProcessMessage("resultList:" + resultList.size()));
//
//   List<List<Map<String, Object>>> splitJdpTradeLists = splitList(resultList,
//           limitCount);
//   log.info(msg.getProcessMessage(
//           "fetchJstTrade splitJdpTradeLists Size:" + splitJdpTradeLists
//                   .size()));
//   for (int i = 0; i < splitJdpTradeLists.size(); i++) {
//    batchNo++;
//    Map<String, Object> lastRecord = splitJdpTradeLists.get(i).get(
//            splitJdpTradeLists.get(i).size() - 1);//获取最后一条数据，并取得时间最为最后一次更新时间
//    Timestamp lastRecordTime = CommonHelper.parseFromString(
//            lastRecord.get("jdp_modified").toString(),
//            "yyyy-MM-dd HH:mm:ss");
//    String response = jdpTradeService.fetchJstTrade(resultList, batchNo);
//    if ("success".equals(response)) {
//
//     log.info(
//             msg.getProcessMessage("fetchJstTrade success batchNo:" + batchNo));
//     log.info(msg.getProcessMessage(
//             "fetchJstTrade success lastRecordTime:" + CommonHelper
//                     .DateFormat(lastRecordTime, "yyyy-MM-dd HH:mm:ss")));
//     SyncBatch syncBatch1 = new SyncBatch();
//     syncBatch1.setBatchNo(batchNo);
//     syncBatch1.setLastSyncTime(lastRecordTime);
//     syncBatchService.saveSyncBatch(syncBatch1);
//    }
//   }
//
//  } catch (Exception ex) {
//   log.error(msg.getErrorMessage(ex.getMessage()), ex);
//  } finally {
//   //update job execute record
//   jobExec.setStatus(Constants.JobStatus.done.toString());
//   log.info(msg.getProcessMessage("jobExec Update:" + jobExec.toString()));
//   jobExecService.updateJobExec(jobExec);
//  }
//  log.info(msg.getEndMessage("JstClientJob"));
 }

 /**
  * 分割List
  *
  * @param list     待分割的list
  * @param pageSize 每段list的大小
  *
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
