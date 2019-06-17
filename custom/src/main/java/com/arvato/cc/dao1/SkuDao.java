package com.arvato.cc.dao1;

import com.arvato.cc.form.ComboStore;
import com.arvato.cc.model.Sku;
import com.arvato.jdf.dao.Page;
import com.arvato.jdf.dao.PropertyFilter;

import java.util.List;

public interface SkuDao {

    Page<Sku> findPropertyFilter(Page page, PropertyFilter propertyFilter);

    Sku save(Sku sku);

    void delete(Integer id);

    void deleteSku(String[] id);

    List<String> findCategory();

    List<String> findBrand();

    Sku findById(Integer id);

    Sku findByMatnr(String matnr);

    List<Sku> findAllSku();

}
