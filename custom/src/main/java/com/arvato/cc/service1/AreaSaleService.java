package com.arvato.cc.service1;

import com.arvato.cc.form.AreaSaleModel;
import com.arvato.cc.form.ComboStore;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: caol005
 * Date: 15-8-17
 * Time: 下午2:45
 * To change this template use File | Settings | File Templates.
 */
public interface AreaSaleService {

    XSSFWorkbook buildAreaSale(Map<String, String> queryParams);

    String generateAreaSaleReport(Map<String, String> queryParams);

    List<ComboStore> findGridTitle();

    List<AreaSaleModel> getAreaSaleModel(Map<String, String> queryParams);

    List<AreaSaleModel> getAreaSaleModelSummary(Map<String, String> queryParams);
}
