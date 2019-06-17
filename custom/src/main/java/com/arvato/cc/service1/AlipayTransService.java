package com.arvato.cc.service1;

import com.arvato.cc.form.AlipayTransModel;
import com.arvato.cc.model.Alipay;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: caol005
 * Date: 15-7-16
 * Time: 下午1:56
 * To change this template use File | Settings | File Templates.
 */
public interface AlipayTransService {
    Map<String,AlipayTransModel> getAlipayTrans(Map<String,String> params);

}
