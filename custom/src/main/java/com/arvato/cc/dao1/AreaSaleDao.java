package com.arvato.cc.dao1;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: caol005
 * Date: 15-8-17
 * Time: 下午3:02
 * To change this template use File | Settings | File Templates.
 */
public interface AreaSaleDao {

    /**
     * sku,quantity,area
     * @return
     */
    List<Map<String,Object>> findAreaSale(Map<String, String> queryParams);

    /**
     * all area
     * @return
     */
    List<Map<String, Object>> findArea();


    /**
     *
     * @return
     */
    public List<Map<String,Object>> findByCategory(Map<String, String> queryParams);


    public List<Map<String,Object>> findByOtherCategory(Map<String, String> queryParams);

    /**
     *底部汇总
     * @param queryParams
     * @return
     */
    List<Map<String, Object>> findBySummaryBottom(Map<String, String> queryParams);
}
