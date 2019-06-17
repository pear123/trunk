package com.arvato.cc.dao1;

import com.arvato.cc.model.GiftData;
import com.arvato.jdf.dao.Page;
import com.arvato.jdf.dao.PropertyFilter;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: XUSO002
 * Date: 15-8-8
 * Time: 下午12:42
 * To change this template use File | Settings | File Templates.
 */
public interface GiftDataDao {

    List<String> findCategory();

    List<String> findBrand();

    void delete(Integer id);

    void deleteGiftBySkuNo(List<String> skuNoList);

    void updataAllGiftStatus();

    void deleteGift(String[] id);

    GiftData save(GiftData sku);

    GiftData findById(Integer id);

    Page<GiftData> findPropertyFilter(Page page, PropertyFilter propertyFilter);

    List<GiftData> getAllSkuIds();

    GiftData getGiftBySkuNo(String skuNo);

    GiftData getGiftBySkuNoAndBrand(String skuNo,String brand);

    GiftData getGiftBySkuId(Integer skuId);

    void  saveOrUpdate(GiftData giftData);

    GiftData findSkuByname(String presentName);

    GiftData findSkuNo(String skuNo);

    List<GiftData>  findAllGift();
}
