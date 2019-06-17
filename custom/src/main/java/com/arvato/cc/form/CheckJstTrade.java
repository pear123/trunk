package com.arvato.cc.form;

import com.arvato.cc.model.JdpTrade;

import java.util.List;

/**
 * Project bsh.
 * Created by zhan005 on 2015-10-23 11:04.
 * <p/>
 * Sie sind das Essen und wir sind die Jaeger.
 */
public class CheckJstTrade {
    public String jstTradeNum;
    public String ccTradeNum;

    public String findTime;

    public List<JdpTradeBean> jdpTrades;

    public String getJstTradeNum() {
        return jstTradeNum;
    }

    public void setJstTradeNum(String jstTradeNum) {
        this.jstTradeNum = jstTradeNum;
    }

    public String getCcTradeNum() {
        return ccTradeNum;
    }

    public void setCcTradeNum(String ccTradeNum) {
        this.ccTradeNum = ccTradeNum;
    }

    public String getFindTime() {
        return findTime;
    }

    public void setFindTime(String findTime) {
        this.findTime = findTime;
    }

    public List<JdpTradeBean> getJdpTrades() {
        return jdpTrades;
    }

    public void setJdpTrades(List<JdpTradeBean> jdpTrades) {
        this.jdpTrades = jdpTrades;
    }

    @Override
    public String toString() {
        return "CheckJstTrade{" +
                "jstTradeNum='" + jstTradeNum + '\'' +
                ", ccTradeNum='" + ccTradeNum + '\'' +
                ", findTime='" + findTime + '\'' +
                ", jdpTrades=" + jdpTrades +
                '}';
    }
}
