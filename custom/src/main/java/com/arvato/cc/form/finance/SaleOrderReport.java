package com.arvato.cc.form.finance;

import java.text.DecimalFormat;
import java.util.Date;

/**
 * 订单详细报表
 * @author tracy
 * 
 */
public class SaleOrderReport {
	protected DecimalFormat df = new DecimalFormat("##.00");

    private Date shippingTime;
    private String sourceOrderNo;
    private String omsOrderNo;
    private String loginName;
    private String cardNo;
    private String alipayNo;
    private String internalStatus;
    private Double productTotal;
    private Double shippingTotal;
    private String createTimeDay;
    private String createTimeHour;
    private String paymentTimeDay ;
    private String paymentTimeHour ;
    private String consumer ;
    private String address ;
    private String phone ;
    private String mobile;
    private String shippingMethod ;

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

    public String getAlipayNo() {
        return alipayNo;
    }

    public void setAlipayNo(String alipayNo) {
        this.alipayNo = alipayNo;
    }

    public String getInternalStatus() {
        return internalStatus;
    }

    public void setInternalStatus(String internalStatus) {
        this.internalStatus = internalStatus;
    }

    public Double getProductTotal() {
        return productTotal;
    }

    public void setProductTotal(Double productTotal) {
        this.productTotal = productTotal;
    }

    public Double getShippingTotal() {
        return shippingTotal;
    }

    public void setShippingTotal(Double shippingTotal) {
        this.shippingTotal = shippingTotal;
    }

    public String getCreateTimeDay() {
        return createTimeDay;
    }

    public void setCreateTimeDay(String createTimeDay) {
        this.createTimeDay = createTimeDay;
    }

    public String getCreateTimeHour() {
        return createTimeHour;
    }

    public void setCreateTimeHour(String createTimeHour) {
        this.createTimeHour = createTimeHour;
    }

    public String getPaymentTimeDay() {
        return paymentTimeDay;
    }

    public void setPaymentTimeDay(String paymentTimeDay) {
        this.paymentTimeDay = paymentTimeDay;
    }

    public String getPaymentTimeHour() {
        return paymentTimeHour;
    }

    public void setPaymentTimeHour(String paymentTimeHour) {
        this.paymentTimeHour = paymentTimeHour;
    }

    public String getConsumer() {
        return consumer;
    }

    public void setConsumer(String consumer) {
        this.consumer = consumer;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }
}
