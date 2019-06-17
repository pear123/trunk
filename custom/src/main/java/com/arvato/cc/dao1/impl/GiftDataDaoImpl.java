package com.arvato.cc.dao1.impl;

import com.arvato.cc.constant.Constants;
import com.arvato.cc.dao1.GiftDataDao;
import com.arvato.cc.model.GiftData;
import com.arvato.jdf.dao.HibernateDao;
import com.arvato.jdf.dao.Page;
import com.arvato.jdf.dao.PropertyFilter;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: XUSO002
 * Date: 15-8-8
 * Time: 下午12:42
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class GiftDataDaoImpl extends HibernateDao<GiftData, Integer> implements GiftDataDao{

    public List<String> findCategory() {
        return this.find("select cid from GiftData group by cid");
    }

    public List<String> findBrand() {
        return this.find("select brand from GiftData group by brand");
    }

    /**
     * 根据skuNo删除物料
     * @param skuNoList
     */
//    public void deleteGiftBySkuNo(List<String> skuNoList) {
//        String constr="";
//        for (int i=0;i<skuNoList.size();i++){
//            if (i==skuNoList.size()-1){
//                constr=constr+"'"+skuNoList.get(i)+"'";
//            }  else {
//                constr=constr+"'"+skuNoList.get(i)+"',";
//            }
//        }
//        String hql="delete from gift_data where skuno in("+constr+")";
//        SQLQuery sqlQuery=this.getSession().createSQLQuery(hql) ;
//        sqlQuery.executeUpdate();
//    }

    public void deleteGiftBySkuNo(List<String> skuNoList) {
        StringBuffer constr = new StringBuffer();
        for (int i=0;i<skuNoList.size();i++){
            if (i==skuNoList.size()-1){
                constr.append("'"+skuNoList.get(i)+"'");
            }  else {
                constr.append("'"+skuNoList.get(i)+"',");
            }
        }
        String hql="delete from gift_data where skuno in("+constr+")";
        SQLQuery sqlQuery=this.getSession().createSQLQuery(hql) ;
        sqlQuery.executeUpdate();
    }

    public void updataAllGiftStatus() {
        String hql="update gift_data set STATUS=?";
        SQLQuery sqlQuery=this.getSession().createSQLQuery(hql);
        int index=0;
        sqlQuery.setString(index,Constants.UploadStatus.INACTIVE.toString());
        sqlQuery.executeUpdate();
    }

    public void deleteGift(String[] id) {
        String hql="";
        for (int i=0;i<id.length;i++){
            if(i==0){
                hql="skuId="+id[i];
            }else{
                hql=hql+" or skuId="+id[i];
            }
        }
        Query query=this.getSession().createSQLQuery("delete from gift_data where " +hql);
        query.executeUpdate();
    }

    public GiftData findById(Integer id) {
//        List<GiftData> giftList = this.find(" from GiftData where skuId = ?",id);
//        return (null == giftList || giftList.size() ==0) ? null : giftList.get(0);
       return (GiftData)this.getSession().get(GiftData.class,id);
    }

    public Page<GiftData> findPropertyFilter(Page page, PropertyFilter propertyFilter) {
        return super.findByPropertyFilter(page, propertyFilter);
    }

    public List<GiftData> getAllSkuIds() {
        return super.getAll();
    }

    public GiftData getGiftBySkuNo(String skuNo) {
        String hql="from GiftData where skuNo=?";
        List<GiftData> giftList = super.find(hql, skuNo);
        if(!CollectionUtils.isEmpty(giftList)){
             return giftList.get(0);
        }
        return null;
    }

    public GiftData getGiftBySkuNoAndBrand(String skuNo,String brand) {
        String hql="from GiftData where brand=? and skuNo=?";
        List<GiftData> giftList = super.find(hql, brand,skuNo);
        if(!CollectionUtils.isEmpty(giftList)){
            return giftList.get(0);
        }
        return null;
    }

    public GiftData getGiftBySkuId(Integer skuId) {
        String hql="from GiftData where skuId=?";
        List<GiftData> giftList = super.find(hql, skuId);
        if(!CollectionUtils.isEmpty(giftList)){
            return giftList.get(0);
        }
        return null;
    }

    public void saveOrUpdate(GiftData giftData) {
        getSession().saveOrUpdate(giftData);
    }

    public GiftData findSkuByname(String presentName){
        List<GiftData> giftDataList = find("from GiftData where memo like '%" + presentName + "%'");
        if(CollectionUtils.isNotEmpty(giftDataList)){
            return giftDataList.get(0);
        }
        return null;
    }

    public GiftData findSkuNo(String skuNo) {
        List<GiftData> giftList = this.find(" from GiftData where skuNo = ?",skuNo);
        return (null == giftList || giftList.size() ==0) ? null : giftList.get(0);
    }

    @Override
    public List<GiftData>  findAllGift(){
        return super.getAll();
    }
}
