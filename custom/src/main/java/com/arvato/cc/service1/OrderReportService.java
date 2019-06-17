package com.arvato.cc.service1;

import com.arvato.cc.form.finance.OrderReport;
import com.arvato.cc.form.finance.RestFinanceReport;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ZHAN405
 * Date: 15-8-18
 * Time: 下午2:04
 * To change this template use File | Settings | File Templates.
 */
public interface OrderReportService {


    Workbook generateOrderReportByExample(Map<String,String> queryParams);

    String generateOrderReport(Map<String,String> queryParams);
}
