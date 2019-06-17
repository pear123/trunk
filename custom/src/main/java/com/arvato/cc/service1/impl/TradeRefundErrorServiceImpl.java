package com.arvato.cc.service1.impl;

import com.arvato.cc.constant.Constants;
import com.arvato.cc.dao1.*;
import com.arvato.cc.exceptions.DataException;
import com.arvato.cc.model.*;
import com.arvato.cc.service1.AlipayService;
import com.arvato.cc.service1.OperationLogService;
import com.arvato.cc.service1.TradeRefundErrorService;
import com.arvato.cc.util.CommonHelper;
import com.arvato.cc.util.FileUtil;
import com.arvato.cc.util.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: caol005
 * Date: 15-7-16
 * Time: 下午1:58
 * To change this template use File | Settings | File Templates.
 */
@Service
public class TradeRefundErrorServiceImpl implements TradeRefundErrorService {
    private static final Logger log = LoggerFactory.getLogger(TradeRefundErrorServiceImpl.class);

    @Autowired
    private TradeRefundErrorDao tradeRefundErrorDao;

    public void saveTradeRefundError(String refundId) {
        TradeRefundError tradeRefundError = new TradeRefundError();
        tradeRefundError.setRefundId(refundId);
        tradeRefundErrorDao.saveTradeRefundError(tradeRefundError);
    }
}
