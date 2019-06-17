package com.arvato.cc.service1.impl;

import com.arvato.cc.constant.Constants;
import com.arvato.cc.dao1.SequenceDao;
import com.arvato.cc.service1.SequenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

/**
 * generate sequence no for new order item.
 * User: pang008
 * Date: 11/10/12
 * Time: 21:28
 * To change this template use File | Settings | File Templates.
 */
@Service
public class SequenceServiceImpl implements SequenceService {
    private static final Logger logger = LoggerFactory.getLogger(SequenceServiceImpl.class);

    private static final int INIT_CAPACITY = 200;

    private static final int Threshold = 20;

    private static Vector<Integer> noPool = new Vector<Integer>();

    private static Vector<Integer> alipayBatchNoPool = new Vector<Integer>();

    @Autowired
    private SequenceDao sequenceDao;

    public Integer generate() {
        logger.debug("generate ORDER_ITEM_SEQ no start.");
        int size = noPool.size();
        logger.debug("current ORDER_ITEM_SEQ pool size[" + size + "]");
        if (size < Threshold) {
            logger.debug("current ORDER_ITEM_SEQ pool size[" + size + "] is less than Threshold[" + Threshold + "], so generate additional size[" + (INIT_CAPACITY - size)+"]");
            noPool.addAll(sequenceDao.generate(INIT_CAPACITY - size, Constants.Sequence.ORDER_ITEM_SEQ.name()));
        }

        int no = noPool.remove(0);
        logger.debug("generate ORDER_ITEM_SEQ no end with[NO：" + no + "]");
        return no;
    }

    public String generateAlipayReturnBatchNo() {
        logger.debug("generate ALIPAY_RETURN_BATCH_NO no start.");
        int size = alipayBatchNoPool.size();
        logger.debug("current ALIPAY_RETURN_BATCH_NO pool size[" + size + "]");
        if (size < Threshold) {
            logger.debug("current ALIPAY_RETURN_BATCH_NO pool size[" + size + "] is less than Threshold[" + Threshold + "], so generate additional size[" + (INIT_CAPACITY - size)+"]");
            alipayBatchNoPool.addAll(sequenceDao.generate(INIT_CAPACITY - size, Constants.Sequence.ALIPAY_RETURN_BATCH_NO.name()));
        }

        int no = alipayBatchNoPool.remove(0);
        String batchNumber = new SimpleDateFormat("yyyyMMdd").format(new Date())+no;
        logger.debug("batchNumber："+batchNumber);
        return batchNumber;
    }
}
