package com.arvato.cc.form.finance;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

/**
 * Created with IntelliJ IDEA.
 * User: ZHAN405
 * Date: 15-8-18
 * Time: 上午10:35
 * To change this template use File | Settings | File Templates.
 */
public class OrderReport {


    private static Double CSRate = 0.007;

    protected SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    protected DecimalFormat df = new DecimalFormat("##.00");

    private String saleSorg;//固定 CN00
    private String distrchan; //固定  30
    private String ordertype;  //固定SKKE
    private String customer;
    private String custOrdNum;
    private String orderDate;//支付宝到账时间
    private String priceDate;//订单创建时间
    private String parvw;//固定AG
    private String kunnr;//同customer
    private String name;//开票抬头
    private String street;//收货地址
    private String city;
    private String  postalCode;//固定999999
    private String  parvw1;
    private String kunnr1;
    private String name1;
    private String street1;//收货地址
    private String city1;
    private String  postalCode1;
    private String parvw2;
    private String kunnr2;
    private String name2;//开票抬头
    private String street2;//收货地址
    private String city2;
    private String  postalCode2;
    private String parvw3;
    private String kunnr3;
    private String name3;//开票抬头
    private String street3;//收货地址
    private String city3;
    private String  postalCode3;
    private String  matnr;
    private String quantity;
    private String kschl;
    private String kbetr;
    private String kschl1;
    private String kbetr1;
    private String kschl2;
    private String kbetr2;
    private String textID;
    private String text;
    private String textID1;
    private String text1;
    private String textID2;
    private String text2;

    public String getSaleSorg() {
        return saleSorg;
    }

    public void setSaleSorg(String saleSorg) {
        this.saleSorg = saleSorg;
    }

    public String getDistrchan() {
        return distrchan;
    }

    public void setDistrchan(String distrchan) {
        this.distrchan = distrchan;
    }

    public String getOrdertype() {
        return ordertype;
    }

    public void setOrdertype(String ordertype) {
        this.ordertype = ordertype;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getCustOrdNum() {
        return custOrdNum;
    }

    public void setCustOrdNum(String custOrdNum) {
        this.custOrdNum = custOrdNum;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getPriceDate() {
        return priceDate;
    }

    public void setPriceDate(String priceDate) {
        this.priceDate = priceDate;
    }

    public String getParvw() {
        return parvw;
    }

    public void setParvw(String parvw) {
        this.parvw = parvw;
    }

    public String getKunnr() {
        return kunnr;
    }

    public void setKunnr(String kunnr) {
        this.kunnr = kunnr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getParvw1() {
        return parvw1;
    }

    public void setParvw1(String parvw1) {
        this.parvw1 = parvw1;
    }

    public String getKunnr1() {
        return kunnr1;
    }

    public void setKunnr1(String kunnr1) {
        this.kunnr1 = kunnr1;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getStreet1() {
        return street1;
    }

    public void setStreet1(String street1) {
        this.street1 = street1;
    }

    public String getCity1() {
        return city1;
    }

    public void setCity1(String city1) {
        this.city1 = city1;
    }

    public String getPostalCode1() {
        return postalCode1;
    }

    public void setPostalCode1(String postalCode1) {
        this.postalCode1 = postalCode1;
    }

    public String getParvw2() {
        return parvw2;
    }

    public void setParvw2(String parvw2) {
        this.parvw2 = parvw2;
    }

    public String getKunnr2() {
        return kunnr2;
    }

    public void setKunnr2(String kunnr2) {
        this.kunnr2 = kunnr2;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public String getStreet2() {
        return street2;
    }

    public void setStreet2(String street2) {
        this.street2 = street2;
    }

    public String getCity2() {
        return city2;
    }

    public void setCity2(String city2) {
        this.city2 = city2;
    }

    public String getPostalCode2() {
        return postalCode2;
    }

    public void setPostalCode2(String postalCode2) {
        this.postalCode2 = postalCode2;
    }

    public String getParvw3() {
        return parvw3;
    }

    public void setParvw3(String parvw3) {
        this.parvw3 = parvw3;
    }

    public String getKunnr3() {
        return kunnr3;
    }

    public void setKunnr3(String kunnr3) {
        this.kunnr3 = kunnr3;
    }

    public String getName3() {
        return name3;
    }

    public void setName3(String name3) {
        this.name3 = name3;
    }

    public String getStreet3() {
        return street3;
    }

    public void setStreet3(String street3) {
        this.street3 = street3;
    }

    public String getCity3() {
        return city3;
    }

    public void setCity3(String city3) {
        this.city3 = city3;
    }

    public String getPostalCode3() {
        return postalCode3;
    }

    public void setPostalCode3(String postalCode3) {
        this.postalCode3 = postalCode3;
    }

    public String getMatnr() {
        return matnr;
    }

    public void setMatnr(String matnr) {
        this.matnr = matnr;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getKschl() {
        return kschl;
    }

    public void setKschl(String kschl) {
        this.kschl = kschl;
    }

    public String getKbetr() {
        return kbetr;
    }

    public void setKbetr(String kbetr) {
        this.kbetr = kbetr;
    }

    public String getKschl1() {
        return kschl1;
    }

    public void setKschl1(String kschl1) {
        this.kschl1 = kschl1;
    }

    public String getKbetr1() {
        return kbetr1;
    }

    public void setKbetr1(String kbetr1) {
        this.kbetr1 = kbetr1;
    }

    public String getKschl2() {
        return kschl2;
    }

    public void setKschl2(String kschl2) {
        this.kschl2 = kschl2;
    }

    public String getKbetr2() {
        return kbetr2;
    }

    public void setKbetr2(String kbetr2) {
        this.kbetr2 = kbetr2;
    }

    public String getTextID() {
        return textID;
    }

    public void setTextID(String textID) {
        this.textID = textID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTextID1() {
        return textID1;
    }

    public void setTextID1(String textID1) {
        this.textID1 = textID1;
    }

    public String getText1() {
        return text1;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }

    public String getTextID2() {
        return textID2;
    }

    public void setTextID2(String textID2) {
        this.textID2 = textID2;
    }

    public String getText2() {
        return text2;
    }

    public void setText2(String text2) {
        this.text2 = text2;
    }



}