package com.arvato.cc.service1.impl;

import com.arvato.cc.dao1.SyncBatchDao;
import com.arvato.cc.model.SyncBatch;
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
public class SyncBatchServiceImpl implements SyncBatchService {
 @Autowired
 private SyncBatchDao syncBatchDao;

 @Override
 public SyncBatch getLastBatch(int batchNo) {
  return syncBatchDao.getLastBatch(batchNo);
 }

 @Override
 public void saveSyncBatch(SyncBatch syncBatch) {
  syncBatchDao.saveSyncBatch(syncBatch);
 }

 @Override
 public SyncBatch getMaxBatch() {
  return syncBatchDao.getMaxBatch();
 }
}
