package com.arvato.cc.form.finance;

import java.util.Date;

/**
 * 商品退换货信息报表
 *
 * @author tracy
 */
public class SaleItemReport {
    private String orderSysId;
    private Date paymentTime;
    private Date shippingTime;
    private String sourceOrderNo;
    private String omsOrderNo;
    private String loginName;
    private String cardNo;
    private String itemSku;
    private String itemName;
    private Double itemPrice;
    private Integer itemQuantity;
    private Double itemTotal;
    private Double itemReturnPrice;
    private Integer itemReturnQuantity;
    private Double itemReturnTotal;
    private Integer remaindQuantity;
    private Double remaindTotal;

    public String getOrderSysId() {
        return orderSysId;
    }

    public void setOrderSysId(String orderSysId) {
        this.orderSysId = orderSysId;
    }

    public Date getShippingTime() {
        return shippingTime;
    }

    public void setShippingTime(Date shippingTime) {
        this.shippingTime = shippingTime;
    }

    public String getSourceOrderNo() {
        return sourceOrderNo;
    }

    public void setSourceOrderNo(String sourceOrderNo) {
        this.sourceOrderNo = sourceOrderNo;
    }

    public String getOmsOrderNo() {
        return omsOrderNo;
    }

    public void setOmsOrderNo(String omsOrderNo) {
        this.omsOrderNo = omsOrderNo;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getItemSku() {
        return itemSku;
    }

    public void setItemSku(String itemSku) {
        this.itemSku = itemSku;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(Double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public Integer getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(Integer itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public Double getItemTotal() {
        return itemTotal;
    }

    public void setItemTotal(Double itemTotal) {
        this.itemTotal = itemTotal;
    }

    public Double getItemReturnPrice() {
        return itemReturnPrice;
    }

    public void setItemReturnPrice(Double itemReturnPrice) {
        this.itemReturnPrice = itemReturnPrice;
    }

    public Integer getItemReturnQuantity() {
        return itemReturnQuantity;
    }

    public void setItemReturnQuantity(Integer itemReturnQuantity) {
        this.itemReturnQuantity = itemReturnQuantity;
    }

    public Double getItemReturnTotal() {
        return itemReturnTotal;
    }

    public void setItemReturnTotal(Double itemReturnTotal) {
        this.itemReturnTotal = itemReturnTotal;
    }

    public Integer getRemaindQuantity() {
        return remaindQuantity;
    }

    public void setRemaindQuantity(Integer remaindQuantity) {
        this.remaindQuantity = remaindQuantity;
    }

    public Double getRemaindTotal() {
        return remaindTotal;
    }

    public void setRemaindTotal(Double remaindTotal) {
        this.remaindTotal = remaindTotal;
    }

    public Date getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(Date paymentTime) {
        this.paymentTime = paymentTime;
    }
}
