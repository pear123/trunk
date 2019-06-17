package com.arvato.cc.service1;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: zhan405
 * Date: 15-8-4
 * Time: 下午1:56
 * To change this template use File | Settings | File Templates.
 */
public interface UploadInvoiceService {

    /**
     * upload file
     * @param uploadFile
     * @param fileType
     * @param folder
     * @return
     */
    String uploadFile(CommonsMultipartFile uploadFile, String fileType, String folder);

    Map<String,String> getInvoiceByParams(Map<String,String> params);

}
