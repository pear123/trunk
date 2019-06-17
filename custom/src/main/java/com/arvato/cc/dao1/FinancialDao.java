package com.arvato.cc.dao1;

import com.arvato.cc.model.ExpFinacial;


public interface FinancialDao {

    void saveExpFinancialData(ExpFinacial expFinacial);

    void deleteExpFinancialData(String tid);
}
