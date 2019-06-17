package com.arvato.cc.service1;

import com.arvato.cc.form.finance.DeliveryReport;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ZHAN405
 * Date: 15-8-19
 * Time: 下午12:05
 * To change this template use File | Settings | File Templates.
 */
public interface DeliveryReportService {

    List<DeliveryReport> getDeliveryReportList(Map<String, String> queryParams);


    Workbook generateDeliveryReport(Map<String,String> queryParams);

    String generateDelivery(Map<String,String> queryParams);
}
