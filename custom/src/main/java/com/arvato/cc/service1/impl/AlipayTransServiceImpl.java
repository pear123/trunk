package com.arvato.cc.service1.impl;

import com.arvato.cc.constant.Constants;
import com.arvato.cc.dao1.AlipayDao;
import com.arvato.cc.dao1.AlipayTransDao;
import com.arvato.cc.dao1.OperationLogDao;
import com.arvato.cc.exceptions.DataException;
import com.arvato.cc.form.AlipayTransModel;
import com.arvato.cc.model.Alipay;
import com.arvato.cc.model.AlipayTrans;
import com.arvato.cc.model.OperationHistory;
import com.arvato.cc.model.TradeHistory;
import com.arvato.cc.service1.AlipayService;
import com.arvato.cc.service1.AlipayTransService;
import com.arvato.cc.service1.OperationLogService;
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
public class AlipayTransServiceImpl implements AlipayTransService {
    private static final Logger log = LoggerFactory.getLogger(AlipayTransServiceImpl.class);

    @Autowired
    private AlipayTransDao alipayTransDao;


    @Override
    public Map<String, AlipayTransModel> getAlipayTrans(Map<String,String> params) {
        List<AlipayTrans> list = alipayTransDao.getByParams(params);
        Map<String,AlipayTransModel> map = new HashMap<String,AlipayTransModel>();
        if(Validate.isNullOrEmpty(list)) {
           return map;
        }
        for(AlipayTrans alipayTrans : list) {
            if(!map.containsKey(alipayTrans.getServiceSerialNum())) {
                map.put(alipayTrans.getServiceSerialNum(),new AlipayTransModel(alipayTrans.getCreateTime(),alipayTrans.getInFee()));
            }
        }
        return map;
    }
}
