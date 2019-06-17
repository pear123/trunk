package com.arvato.cc.service1;

import com.arvato.cc.form.finance.RestFinanceReport;
import com.arvato.jdf.dao.Page;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;
import java.util.Map;

public interface RestFinanceReportService {

    List<RestFinanceReport> getAlipayReportList(Map<String, String> queryParams);


    Page<RestFinanceReport> getFinanceQueryListByPage( Page page,Map<String, Object> queryParams);


    Workbook generateRestFinanceReport(Map<String,String> queryParams);

    /**
     *根据条件生成文件到服务器
     * @param queryParams
     * @return
     */
    String generateRestFinance(Map<String,String> queryParams);

}
