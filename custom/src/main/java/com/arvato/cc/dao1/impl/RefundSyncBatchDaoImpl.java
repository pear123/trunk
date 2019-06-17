package com.arvato.cc.dao1.impl;

import com.arvato.cc.dao1.RefundSyncBatchDao;
import com.arvato.cc.dao1.SyncBatchDao;
import com.arvato.cc.model.RefundSyncBatch;
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
public class RefundSyncBatchDaoImpl extends HibernateDao<RefundSyncBatch, Integer> implements RefundSyncBatchDao {

    @Override
    public RefundSyncBatch getLastBatch(int batchNo) {
        List<RefundSyncBatch> syncBatchList = super.find(" from RefundSyncBatch sb where sb.batchNo = ?",batchNo);
        return ( null == syncBatchList || syncBatchList.isEmpty() ) ?  null :syncBatchList.get(0);
    }

    @Override
    public void saveSyncBatch(RefundSyncBatch syncBatch) {
        super.save(syncBatch);
    }

    @Override
    public RefundSyncBatch getMaxBatch() {
        return (RefundSyncBatch)super.createQuery("from RefundSyncBatch where batchNo = (select max(batchNo) from RefundSyncBatch as sb)").uniqueResult();
    }
}
