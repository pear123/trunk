package com.arvato.cc.form;

import com.arvato.cc.util.Validate;

import java.util.ArrayList;
import java.util.List;

/**
 * Project bsh.
 * Created by zhan005 on 2015-09-14 16:50.
 * <p/>
 * Sie sind das Essen und wir sind die Jaeger.
 */
public class JdpTradeRequest {
    private Boolean flag;
    private List<String> tidList;

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public List<String> getTidList() {
        if(Validate.isNullOrEmpty(tidList)){
            return new ArrayList<String>();
        } else {
            return tidList;
        }

    }

    public void setTidList(List<String> tidList) {
        this.tidList = tidList;
    }

    @Override
    public String toString() {
        return "JdpTradeReturn{" +
                "flag='" + flag + '\'' +
                ", errorTid=" + tidList +
                '}';
    }
}
