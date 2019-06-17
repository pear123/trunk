package com.arvato.cc.form.finance;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

/**
 * Created with IntelliJ IDEA.
 * User: ZHAN405
 * Date: 15-8-20
 * Time: 上午10:24
 * To change this template use File | Settings | File Templates.
 */
public class FinanceReport {

    private static Double CSRate = 0.007;
    protected SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    protected DecimalFormat df = new DecimalFormat("##.00");
    private Timestamp bldat;
    private Timestamp budat;
    private String bkpf;
    private String monat;
    private String bukrs;
    private String orderStatus;

    private String waers;
    private String newbs;
    private String newko;
    private String wrbtr;
    private String zuonr;
    private String sgtxt;
    private String newbs1;
    private String newko1;
    private String name1;
    private String ort01;
    private String wrbtr1;
    private String zuonr1;
    private String sgtxt1;
    private String newbs2;
    private String newko2;
    private String wrbtr2;
    private String zuonr2;
    private String hasRefundRemark;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    private String sgtxt2;

    private String createTime;

    public Timestamp getBldat() {
        return bldat;
    }

    public void setBldat(Timestamp bldat) {
        this.bldat = bldat;
    }

    public String getSgtxt2() {
        return sgtxt2;
    }

    public void setSgtxt2(String sgtxt2) {
        this.sgtxt2 = sgtxt2;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Timestamp getBudat() {
        return budat;
    }

    public void setBudat(Timestamp budat) {
        this.budat = budat;
    }

    public String getBkpf() {
        return bkpf;
    }

    public void setBkpf(String bkpf) {
        this.bkpf = bkpf;
    }

    public String getMonat() {
        return monat;
    }

    public void setMonat(String monat) {
        this.monat = monat;
    }

    public String getBukrs() {
        return bukrs;
    }

    public void setBukrs(String bukrs) {
        this.bukrs = bukrs;
    }

    public String getWaers() {
        return waers;
    }

    public void setWaers(String waers) {
        this.waers = waers;
    }

    public String getNewbs() {
        return newbs;
    }

    public void setNewbs(String newbs) {
        this.newbs = newbs;
    }

    public String getNewko() {
        return newko;
    }

    public void setNewko(String newko) {
        this.newko = newko;
    }

    public String getWrbtr() {
        return wrbtr;
    }

    public void setWrbtr(String wrbtr) {
        this.wrbtr = wrbtr;
    }

    public String getZuonr() {
        return zuonr;
    }

    public void setZuonr(String zuonr) {
        this.zuonr = zuonr;
    }

    public String getSgtxt() {
        return sgtxt;
    }

    public void setSgtxt(String sgtxt) {
        this.sgtxt = sgtxt;
    }

    public String getNewbs1() {
        return newbs1;
    }

    public void setNewbs1(String newbs1) {
        this.newbs1 = newbs1;
    }

    public String getNewko1() {
        return newko1;
    }

    public void setNewko1(String newko1) {
        this.newko1 = newko1;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getOrt01() {
        return ort01;
    }

    public void setOrt01(String ort01) {
        this.ort01 = ort01;
    }

    public String getWrbtr1() {
        return wrbtr1;
    }

    public void setWrbtr1(String wrbtr1) {
        this.wrbtr1 = wrbtr1;
    }

    public String getZuonr1() {
        return zuonr1;
    }

    public void setZuonr1(String zuonr1) {
        this.zuonr1 = zuonr1;
    }

    public String getSgtxt1() {
        return sgtxt1;
    }

    public void setSgtxt1(String sgtxt1) {
        this.sgtxt1 = sgtxt1;
    }

    public String getNewbs2() {
        return newbs2;
    }

    public void setNewbs2(String newbs2) {
        this.newbs2 = newbs2;
    }

    public String getNewko2() {
        return newko2;
    }

    public void setNewko2(String newko2) {
        this.newko2 = newko2;
    }

    public String getWrbtr2() {
        return wrbtr2;
    }

    public void setWrbtr2(String wrbtr2) {
        this.wrbtr2 = wrbtr2;
    }

    public String getZuonr2() {
        return zuonr2;
    }

    public void setZuonr2(String zuonr2) {
        this.zuonr2 = zuonr2;
    }

    public String getHasRefundRemark() {
        return hasRefundRemark;
    }

    public void setHasRefundRemark(String hasRefundRemark) {
        this.hasRefundRemark = hasRefundRemark;
    }
}