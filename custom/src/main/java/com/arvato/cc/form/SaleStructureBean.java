package com.arvato.cc.form;

import com.arvato.cc.util.CommonHelper;

/**
 * Created with IntelliJ IDEA.
 * User: caol005
 * Date: 15-8-17
 * Time: 下午2:47
 * To change this template use File | Settings | File Templates.
 */
public class SaleStructureBean {
    private String num;
    private String matnr;
    private int saleQuantity;
    private double saleAmount;
    private double saleAvg;
    private String saleQuantityPercentage;
    private String saleAmountPercentage;
    private double sapPrice;
    private double sapAmount;
    private String sapDiscount;

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getMatnr() {
        return matnr;
    }

    public void setMatnr(String matnr) {
        this.matnr = matnr;
    }

    public int getSaleQuantity() {
        return saleQuantity;
    }

    public void setSaleQuantity(int saleQuantity) {
        this.saleQuantity = saleQuantity;
    }

    public double getSaleAmount() {
        return saleAmount;
    }

    public void setSaleAmount(double saleAmount) {
        this.saleAmount = saleAmount;
    }

    public double getSaleAvg() {
        return CommonHelper.saveByScale(this.saleAmount / this.saleQuantity,0);
    }

    public void setSaleAvg(double saleAvg) {
        this.saleAvg = saleAvg;
    }

    public String getSaleQuantityPercentage() {
        return saleQuantityPercentage;
    }

    public void setSaleQuantityPercentage(String saleQuantityPercentage) {
        this.saleQuantityPercentage = saleQuantityPercentage;
    }

    public String getSaleAmountPercentage() {
        return saleAmountPercentage;
    }

    public void setSaleAmountPercentage(String saleAmountPercentage) {
        this.saleAmountPercentage = saleAmountPercentage;
    }

    public double getSapPrice() {
        return sapPrice;
    }

    public void setSapPrice(double sapPrice) {
        this.sapPrice = sapPrice;
    }

    public double getSapAmount() {
        return this.sapAmount;
    }

    public void setSapAmount(double sapAmount) {
        this.sapAmount = sapAmount;
    }

    public String getSapDiscount() {
        return sapDiscount;
    }

    public void setSapDiscount(String sapDiscount) {
        this.sapDiscount = sapDiscount;
    }
}
