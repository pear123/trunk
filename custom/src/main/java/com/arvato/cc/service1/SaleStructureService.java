package com.arvato.cc.service1;

import com.arvato.cc.form.SaleStructureBean;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: caol005
 * Date: 15-8-17
 * Time: 下午2:45
 * To change this template use File | Settings | File Templates.
 */
public interface SaleStructureService {

    List<SaleStructureBean> findSaleStructure(Map<String, String> queryParams);

    String generateSaleStructureReport(Map<String, String> queryParams);
}
