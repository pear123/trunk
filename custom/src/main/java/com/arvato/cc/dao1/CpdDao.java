package com.arvato.cc.dao1;

import com.arvato.cc.model.UpdCpd;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: XUSO002
 * Date: 15-8-8
 * Time: 下午3:09
 * To change this template use File | Settings | File Templates.
 */
public interface CpdDao {
    List<UpdCpd> getAllCpdIds();

    UpdCpd get(UpdCpd updCpd);

    void  saveOrUpdate(UpdCpd updCpd);

    void updateCpdStatus(int cpdId);

    void updateAllCpdStatus();

    void deleteCpdByOfficeName(List<String> officeNameList);
    /**
     * add by tracy
     * @return
     */
    List<UpdCpd> findAllUpdCpd();

    Map<String,Object> getCpdCode(String tid);

    List<Map<String,Object>> getCpdCode();

}
