package com.arvato.cc.dao1;

import com.arvato.cc.form.finance.RestFinanceReport;
import com.arvato.cc.model.Alipay;
import com.arvato.cc.model.OperationHistory;
import com.arvato.jdf.dao.Page;

import java.util.List;
import java.util.Map;

public interface RestFinanceReportDao {

    List<Alipay> getAlipayReportList(String hql);

    List<Alipay> getAlipayRerportListBySql(String sql);


    Page<Alipay> findByPage(Page page,String hql);



    Boolean isExits(String orderNum);

}