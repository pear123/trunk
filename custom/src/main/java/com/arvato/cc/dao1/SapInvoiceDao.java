package com.arvato.cc.dao1;

import com.arvato.cc.model.UpdInvoice;

import java.util.List;
import java.util.Map;

public interface SapInvoiceDao {


    void saveOrUpdate(UpdInvoice updInvoice);

    List<UpdInvoice> findGitNoByTid(String tid);

    UpdInvoice findGitNoByTidAndMater(String tid,String mater);

    List<Map<String,Object>> getBillAllGitNo();

    List<Map<String,Object>> getBillAllGitNoAndId();

    UpdInvoice findById(Integer id);

    List<UpdInvoice> getByParams(Map<String, String> params);
}
