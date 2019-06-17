package com.arvato.cc.dao1.impl;

import com.arvato.cc.dao1.SyncBatchDao;
import com.arvato.cc.model.SyncBatch;
import com.arvato.jdf.dao.HibernateDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: caol005
 * Date: 15-8-27
 * Time: 下午2:32
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class SyncBatchDaoImpl extends HibernateDao<SyncBatch, Integer> implements SyncBatchDao {

    @Override
    public SyncBatch getLastBatch(int batchNo) {
        List<SyncBatch> syncBatchList = super.find(" from SyncBatch sb where sb.batchNo = ?",batchNo);
        return ( null == syncBatchList || syncBatchList.isEmpty() ) ?  null :syncBatchList.get(0);
    }

    @Override
    public void saveSyncBatch(SyncBatch syncBatch) {
        super.save(syncBatch);
    }

    @Override
    public SyncBatch getMaxBatch() {
        return (SyncBatch)super.createQuery("from SyncBatch where batchNo = (select max(batchNo) from SyncBatch as sb)").uniqueResult();
    }
}
