package com.arvato.cc.service1.impl;

import com.arvato.cc.dao1.RefundSyncBatchDao;
import com.arvato.cc.dao1.SyncBatchDao;
import com.arvato.cc.model.RefundSyncBatch;
import com.arvato.cc.model.SyncBatch;
import com.arvato.cc.service1.RefundSyncBatchService;
import com.arvato.cc.service1.SyncBatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Project bsh.
 * Created by zhan005 on 2015-09-09 14:31.
 *
 * Sie sind das Essen und wir sind die Jaeger.
 */
@Service
public class RefundSyncBatchServiceImpl implements RefundSyncBatchService {
     @Autowired
     private RefundSyncBatchDao refundSyncBatchDao;

     @Override
     public RefundSyncBatch getLastBatch(int batchNo) {
      return refundSyncBatchDao.getLastBatch(batchNo);
     }

     @Override
     public void saveRefundSyncBatch(RefundSyncBatch syncBatch) {
         refundSyncBatchDao.saveSyncBatch(syncBatch);
     }

     @Override
     public RefundSyncBatch getMaxBatch() {
      return refundSyncBatchDao.getMaxBatch();
     }
}
