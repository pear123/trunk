package com.arvato.cc.service1;

import com.arvato.cc.model.Alipay;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * Created with IntelliJ IDEA.
 * User: caol005
 * Date: 15-7-16
 * Time: 下午1:56
 * To change this template use File | Settings | File Templates.
 */
public interface TradeRefundErrorService {

    void saveTradeRefundError(String refundId);
}
