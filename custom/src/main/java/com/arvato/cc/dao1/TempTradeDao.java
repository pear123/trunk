package com.arvato.cc.dao1;

import com.arvato.cc.model.TempTrade;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: caol005
 * Date: 15-8-12
 * Time: 下午3:10
 * To change this template use File | Settings | File Templates.
 */
public interface TempTradeDao {

    void saveTempTradeList(List<TempTrade> tempTradeList);

    void saveTempTrade(TempTrade tempTrade);

    List<TempTrade> findByBatchNo(int batchNo);

    Integer  findMinBatchNo();

    List<TempTrade> findByMaxBatchNo(int batchNo);

    int deleteByMaxBatchNo(int batchNo);

    TempTrade findByTid(String tid);

    void deleteTempTrade(TempTrade tempTrade);

    void deleteByTid(String tid);

    List<TempTrade> findByBatchNoNotVirtual(int batchNo);
}
