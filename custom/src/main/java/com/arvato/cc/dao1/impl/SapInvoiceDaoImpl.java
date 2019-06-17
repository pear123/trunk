package com.arvato.cc.dao1.impl;

import com.arvato.cc.dao1.JdbcTemplateExtend;
import com.arvato.cc.dao1.SapInvoiceDao;
import com.arvato.cc.model.UpdInvoice;
import com.arvato.cc.util.Validate;
import com.arvato.jdf.dao.HibernateDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public class SapInvoiceDaoImpl extends HibernateDao<UpdInvoice, String> implements SapInvoiceDao {

    @Autowired
    private JdbcTemplateExtend jdbcTemplateExtend;

    public void saveOrUpdate(UpdInvoice updInvoice) {
        getSession().saveOrUpdate(updInvoice);
    }

    public List<UpdInvoice> findGitNoByTid(String tid){
        return find("from UpdInvoice where poNumber=?", tid);
    }

    public UpdInvoice findGitNoByTidAndMater(String tid,String mater){
        return (UpdInvoice)find("from UpdInvoice where poNumber=? and material=? ",tid,mater).get(0);
    }

    public List<Map<String,Object>> getBillAllGitNo(){
        return jdbcTemplateExtend.query("select gitno from upd_invoice");
    }

    public List<Map<String,Object>> getBillAllGitNoAndId(){
        return jdbcTemplateExtend.query("select gitno,id from upd_invoice");
    }

    public UpdInvoice findById(Integer id){
        List<UpdInvoice> list = find("from UpdInvoice where id=?", id);
        if(list!=null&&list.size()>0){
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<UpdInvoice> getByParams(Map<String, String> params){
        StringBuffer hql = new StringBuffer(" from UpdInvoice where 1=1 ");
        if(!Validate.isNullOrEmpty(params)){
            String value = params.get("startTime");
            if(!Validate.isNullOrEmpty(value)){
                hql.append(" and poDate >=  '" + value + "'");
            }
            value = params.get("endTime");
            if(!Validate.isNullOrEmpty(value)){
                hql.append(" and poDate <=  '" + value + "'");
            }
            value = params.get("invoiceNo");
            if(!Validate.isNullOrEmpty(value)){
                hql.append(" and poNumber =  '" + value + "天猫'");
            }
        }
        List<UpdInvoice> invoices = super.find(hql.toString());
        return ( null == invoices || invoices.isEmpty() ) ? null : invoices;
    }
}
