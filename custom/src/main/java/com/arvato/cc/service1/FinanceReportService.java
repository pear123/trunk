package com.arvato.cc.service1;


import com.arvato.cc.form.finance.FinanceReport;
import org.apache.poi.ss.usermodel.Workbook;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ZHAN405
 * Date: 15-8-19
 * Time: 下午3:48
 * To change this template use File | Settings | File Templates.
 */
public interface FinanceReportService {

    List<FinanceReport> getFinanceReportList(Map<String, String> queryParams) throws ParseException;

    Workbook generateFinanceReport(Map<String,String> queryParams) throws ParseException;

    String generateFinance(Map<String,String> queryParams) throws ParseException;
}
