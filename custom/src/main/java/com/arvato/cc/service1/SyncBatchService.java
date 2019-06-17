package com.arvato.cc.service1;

import com.arvato.cc.model.SyncBatch;

/**
 * Project bsh.
 * Created by zhan005 on 2015-09-09 14:31.
 *
 * Sie sind das Essen und wir sind die Jaeger.
 */
public interface SyncBatchService {

 SyncBatch getLastBatch(int batchNo);

 void saveSyncBatch(SyncBatch syncBatch);

 /**
  * 聚石塔拉单获取最大Batch
  * @return batchNo
  */
 SyncBatch getMaxBatch();
}
