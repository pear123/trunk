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
public interface SaleStructureDao {

    List<Map<String,Object>> findSaleStructure(Map<String, String> queryParams);

    List<Map<String,Object>> findSaleStructureSum(Map<String, String> queryParams);

    List<Map<String,Object>> findSaleStructureByCategory(Map<String, String> queryParams);

    List<Map<String,Object>> findSaleStructureByCategorySum(Map<String, String> queryParams);
}
