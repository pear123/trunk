package com.arvato.cc.service1;

/**
 * Created with IntelliJ IDEA.
 * User: pang008
 * Date: 11/10/12
 * Time: 21:27
 * To change this template use File | Settings | File Templates.
 */
public interface SequenceService {
    /**
     * generate sequence no for order item
     * @return
     */
    Integer generate();

    /**
     * generate batch No for return alipay.
     * @return
     */
    String generateAlipayReturnBatchNo();
}
