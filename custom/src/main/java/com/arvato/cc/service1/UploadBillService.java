package com.arvato.cc.service1;

import com.arvato.jdf.dao.Page;
import com.arvato.jdf.dao.PropertyFilter;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * Created with IntelliJ IDEA.
 * User: zhan405
 * Date: 15-8-4
 * Time: 下午1:56
 * To change this template use File | Settings | File Templates.
 */
public interface UploadBillService {

    /**
     * upload file
     * @param uploadFile
     * @param fileType
     * @param folder
     * @return
     */
    String uploadFile(CommonsMultipartFile uploadFile, String fileType, String folder);

}
