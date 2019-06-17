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
public class JdpRefundRequest {
    private Boolean flag;
    private List<String> refundIdList;

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public List<String> getRefundIdList() {
        if(Validate.isNullOrEmpty(refundIdList)){
            return new ArrayList<String>();
        } else {
            return refundIdList;
        }

    }

    public void setRefundIdList(List<String> tidList) {
        this.refundIdList = tidList;
    }

    @Override
    public String toString() {
        return "JdpTradeReturn{" +
                "flag='" + flag + '\'' +
                ", errorTid=" + refundIdList +
                '}';
    }
}
