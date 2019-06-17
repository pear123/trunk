package com.arvato.cc.service1;

import com.arvato.cc.form.ComboStore;
import com.arvato.cc.model.Sku;
import com.arvato.jdf.dao.Page;
import com.arvato.jdf.dao.PropertyFilter;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: caol005
 * Date: 15-8-5
 * Time: 下午6:34
 * To change this template use File | Settings | File Templates.
 */
public interface SkuService {

    Page<Sku> findPropertyFilter(Page page, PropertyFilter propertyFilter);

    Sku save(Sku sku);

    void delete(Integer id);

    void deleteSku(String id);

    List<ComboStore> findCategory();

    List<ComboStore> findBrand();

    Sku findById(Integer id);

    Sku findByMatnr(String matnr);

}
