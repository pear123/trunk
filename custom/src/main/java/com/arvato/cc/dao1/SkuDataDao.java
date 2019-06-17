package com.arvato.cc.dao1;

import com.arvato.cc.model.Sku;

import java.util.List;

public interface SkuDataDao {

    void saveOrUpdate(Sku skuData);

    void updateSkuStatus(int id);

    Sku get(Sku skuData);

    Sku getSkuById(Integer id);
    void delete(int id);

    List<Sku> getAllIds();

    void updateSkuData(int id,Sku skuObject);


}
