package com.arvato.cc.service1;


import com.arvato.cc.form.ComboStore;
import com.arvato.cc.model.GiftData;
import com.arvato.jdf.dao.Page;
import com.arvato.jdf.dao.PropertyFilter;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: XUSO002
 * Date: 15-8-8
 * Time: 下午12:37
 * To change this template use File | Settings | File Templates.
 */
public interface GiftDataService {

    List<ComboStore> findCategory();

    List<ComboStore> findBrand();

    void delete(Integer id);

    void deleteGift(String id);

    GiftData save(GiftData giftData);

    GiftData findById(Integer id);

    Page<GiftData> findPropertyFilter(Page page, PropertyFilter propertyFilter);

    String uploadFile(CommonsMultipartFile uploadFile, String fileType, String folder);

    GiftData findSkuNo(String skuNo);

    List<GiftData> findAllGiftSku();
}
