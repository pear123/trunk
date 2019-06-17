package com.arvato.cc.service1.impl;

import com.arvato.cc.dao1.SkuDao;
import com.arvato.cc.form.ComboStore;
import com.arvato.cc.model.Sku;
import com.arvato.cc.service1.SkuService;
import com.arvato.cc.util.Validate;
import com.arvato.jdf.dao.Page;
import com.arvato.jdf.dao.PropertyFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class SkuServiceImpl implements SkuService {

    @Autowired
    private SkuDao skuDao;

    public Page<Sku> findPropertyFilter(Page page, PropertyFilter propertyFilter) {
        return skuDao.findPropertyFilter(page, propertyFilter);
    }

    /**
     * save or update sku information
     * @param sku
     * @return
     */
    public Sku save(Sku sku) {
        return skuDao.save(sku);
    }

    /**
     * delete sku by id
     * @param id
     */
    public void delete(Integer id) {
        skuDao.delete(id);
    }

    /**
     * sku页面删除
     * @param id
     */
    public void deleteSku(String id) {
        String[] ids=id.split(";");
        skuDao.deleteSku(ids);
    }

    public List<ComboStore> findCategory() {
        return getComboStore(skuDao.findCategory());
    }

    public List<ComboStore> findBrand() {
        return getComboStore(skuDao.findBrand());
    }

    public Sku findById(Integer id) {
        return skuDao.findById(id);
    }

    public Sku findByMatnr(String matnr) {
        return skuDao.findByMatnr(matnr);
    }

    private List<ComboStore> getComboStore(List<String> list){
        List<ComboStore> comboStores = new ArrayList<ComboStore>();
        if(Validate.isNullOrEmpty(list)){
            return comboStores;
        }
        ComboStore comboStore = null;
        for(String str : list){
            comboStore = new ComboStore();
            comboStore.setLabel(str);
            comboStore.setValue(str);
            comboStores.add(comboStore);
        }
        return comboStores;

    }



}
