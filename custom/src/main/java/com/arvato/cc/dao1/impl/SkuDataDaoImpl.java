package com.arvato.cc.dao1.impl;

import com.arvato.cc.constant.Constants;
import com.arvato.cc.dao1.SkuDataDao;
import com.arvato.cc.model.*;
import com.arvato.cc.util.CommonHelper;
import com.arvato.cc.util.UserSecurityUtil;
import com.arvato.jdf.dao.HibernateDao;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Repository
public class SkuDataDaoImpl extends HibernateDao<Sku, String> implements SkuDataDao {


    public void updateSkuStatus(int id) {
        SQLQuery sqlQuery = this.getSession().createSQLQuery("update sku set STATUS = '"+ Constants.UploadStatus.INACTIVE.toString()+"' where id = ?");
        sqlQuery.setParameter(0,id);
        sqlQuery.executeUpdate();
    }

    public Sku get(Sku skuData){
        String HQL = "from Sku where brand='" + skuData.getBrand() + "'"
                + " and cid='" + skuData.getCid() + "'"
                + " and matnr='" + skuData.getMatnr() + "'" ;
        List<Sku> skuList = super.find(HQL, null);
        Sku sku = null;
        if(!CollectionUtils.isEmpty(skuList)){
          sku = (Sku)super.find(HQL, null).get(0);
        }
        return sku;
    }

    public Sku getSkuById(Integer id){
        String HQL = "from Sku where id=?";
        List<Sku> skuList = super.find(HQL, id);
        Sku sku = null;
        if(!CollectionUtils.isEmpty(skuList)){
            sku = (Sku)super.find(HQL, id).get(0);
        }
        return sku;
    }

    public void saveOrUpdate(Sku skuData) {
        getSession().saveOrUpdate(skuData);
    }


    public void delete(int id){
        SQLQuery sqlQuery = this.getSession().createSQLQuery("delete from sku where ID=?");
        sqlQuery.setParameter(0, id);
        sqlQuery.executeUpdate();
    }

    public List<Sku> getAllIds(){
        return super.getAll();
    }

    public void updateSkuData(int id,Sku skuData){
        SQLQuery sqlQuery = this.getSession().createSQLQuery("update sku set brand=?,cid=?,matnr=?,tmall_price=?,sapprice=?,status=? where id=?");
        sqlQuery.setParameter(0,skuData.getBrand());
        sqlQuery.setParameter(1,skuData.getCid());
        sqlQuery.setParameter(2,skuData.getMatnr());
        sqlQuery.setParameter(3,skuData.getTmallPrice());
        sqlQuery.setParameter(4,skuData.getSapprice());
        sqlQuery.setParameter(5,skuData.getStatus());
        sqlQuery.setParameter(6,skuData.getId());
        sqlQuery.executeUpdate();
    }
}
