package com.arvato.cc.dao1.impl;

import com.arvato.cc.dao1.SkuDao;
import com.arvato.cc.form.ComboStore;
import com.arvato.cc.model.Sku;
import com.arvato.jdf.dao.HibernateDao;
import com.arvato.jdf.dao.Page;
import com.arvato.jdf.dao.PropertyFilter;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class SkuDaoImpl extends HibernateDao<Sku, Integer> implements SkuDao {

    public Page<Sku> findPropertyFilter(Page page, PropertyFilter propertyFilter) {
        return super.findByPropertyFilter(page, propertyFilter);
    }

    /**
     * save sku information
     * @param sku
     * @return
     */
    public Sku save(Sku sku){
        return super.save(sku);
    }

    /**
     * delete by id
     * @param id
     */
    public void delete(Integer id) {
        super.delete(id);
    }

    public void deleteSku(String[] id) {
        String hql="";
        for (int i=0;i<id.length;i++){
            if(i==0){
                hql="id="+id[i];
            }else{
                hql=hql+" or id="+id[i];
            }
        }
        Query query=this.getSession().createSQLQuery("delete from Sku where "+hql);
        query.executeUpdate();
        // super.delete(id);
    }

    /**
     * get all categories
     * @return
     */
    public List<String> findCategory() {
        return this.find("select cid from Sku group by cid");
    }

    /**
     * get All brands
     * @return
     */
    public List<String> findBrand() {
        return this.find("select brand from Sku group by brand");

    }

    /**
     * get sku by id
     * @param id
     * @return
     */
    public Sku findById(Integer id) {
        List<Sku> skuList = this.find(" from Sku where id = ?",id);
        return (null == skuList || skuList.size() ==0) ? null : skuList.get(0);
    }

     public Sku findByMatnr(String matnr) {
        List<Sku> skuList = this.find(" from Sku where matnr = ?",matnr);
        return (null == skuList || skuList.size() ==0) ? null : skuList.get(0);
    }

    public List<Sku> findAllSku() {
        return super.find(" from Sku");
    }

}
