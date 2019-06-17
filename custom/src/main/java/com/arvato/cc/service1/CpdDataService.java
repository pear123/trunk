package com.arvato.cc.service1;

import com.arvato.cc.model.UpdCpd;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: XUSO002
 * Date: 15-8-8
 * Time: 下午3:05
 * To change this template use File | Settings | File Templates.
 */
public interface CpdDataService {

    String uploadFile(CommonsMultipartFile uploadFile, String fileType, String folder);

    UpdCpd findCpdDataByCityCode(String province,String city);

    Map<String,String> getCpdCode();
}
