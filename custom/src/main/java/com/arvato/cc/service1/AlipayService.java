package com.arvato.cc.service1;

import com.arvato.cc.model.Alipay;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: caol005
 * Date: 15-7-16
 * Time: 下午1:56
 * To change this template use File | Settings | File Templates.
 */
public interface AlipayService {

    /**
     * upload file by path
     * @param file
     * @return
     */
    String uploadFile(CommonsMultipartFile file,String fileType,String folder);


//    boolean getCurrentAlipayLog(String operationType);

    String getAlipayCreateTime(String tid);


    Alipay findByAlipayNo(String alipayNo);

}
