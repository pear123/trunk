package com.arvato.cc.dao1.impl;

import com.arvato.cc.constant.Constants;
import com.arvato.cc.dao1.CpdDao;
import com.arvato.cc.dao1.JdbcTemplateExtend;
import com.arvato.cc.model.UpdCpd;
import com.arvato.cc.util.Validate;
import com.arvato.jdf.dao.HibernateDao;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.SQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: XUSO002
 * Date: 15-8-8
 * Time: 下午3:11
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class CpdDaoImpl  extends HibernateDao<UpdCpd, Integer> implements CpdDao{

    @Autowired
    private JdbcTemplateExtend jdbcTemplateExtend;

    public List<UpdCpd> getAllCpdIds() {
        return super.getAll();
    }

    public UpdCpd get(UpdCpd updCpd) {
        String HQL = "from UpdCpd where officeName='" + updCpd.getOfficeName() + "'"
                + " and code='" + updCpd.getCode() + "'" ;
        List<UpdCpd> cpdList = super.find(HQL, null);
        UpdCpd updCpd1 = null;
        if(!CollectionUtils.isEmpty(cpdList)&&cpdList.size()!=0){
            updCpd1 = (UpdCpd)super.find(HQL, null).get(0);
        }
        return updCpd1;
    }

    public void saveOrUpdate(UpdCpd updCpd) {
       getSession().saveOrUpdate(updCpd);
    }

    public void updateCpdStatus(int cpdId) {
        SQLQuery sqlQuery = this.getSession().createSQLQuery("update  upd_cpd set STATUS = '"+ Constants.UploadStatus.INACTIVE+"' where cpd_id = ?");
        sqlQuery.setParameter(0,cpdId);
        sqlQuery.executeUpdate();
    }

    public void updateAllCpdStatus() {
          String hql="update upd_cpd set STATUS=?";
          SQLQuery sqlQuery=this.getSession().createSQLQuery(hql);
          sqlQuery.setString(0,Constants.UploadStatus.INACTIVE.toString());
          sqlQuery.executeUpdate();
    }

    public void deleteCpdByOfficeName(List<String> officeNameList) {
        String constr="";
        for (int i=0;i<officeNameList.size();i++){
            if (i==officeNameList.size()-1){
                constr=constr+"'"+officeNameList.get(i)+"'";
            }  else {
                constr=constr+"'"+officeNameList.get(i)+"',";
            }
        }
        String hql="delete from upd_cpd where office_Name in("+constr+")";
        SQLQuery sqlQuery=this.getSession().createSQLQuery(hql) ;
        sqlQuery.executeUpdate();
    }



    public List<UpdCpd> findAllUpdCpd() {
        List<UpdCpd> updCpdList = super.find(" from UpdCpd ");
        if(Validate.isNullOrEmpty(updCpdList)) {
            return null;
        } else {
            return updCpdList;
        }
    }

    public Map<String,Object> getCpdCode(String tid){
        String sql = "select office_name from view_cpd where 1=1";
        if(!Validate.isNullOrEmpty(tid)){
            sql += " and tid = '"+tid+"'";
        }
        List<Map<String,Object>> list = jdbcTemplateExtend.query(sql);
        return (null == list || list.isEmpty()) ? null :list.get(0) ;

    }

    public List<Map<String,Object>> getCpdCode(){
        String sql = "select tid,office_name from view_cpd";
        List<Map<String,Object>> list = jdbcTemplateExtend.query(sql);
        return list;
    }


}
