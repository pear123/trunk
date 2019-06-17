package com.arvato.cc.form.finance;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RestFinanceReport {
    private static Double CSRate = 0.007;

    protected SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    protected DecimalFormat df = new DecimalFormat("##.00");

    private String financialSerialNum; //财务流水号
    private String orderNum;//商户订单号
    private String goodsName;//商品名称
    private Timestamp createTime;//发生时间
    private String account;//对方账号
    private Double inFee; //收入金额
    private Double outFee; //支出金额
    private Double balance;//账户余额
    private String mode;//交易渠道
    private String type;//业务类型

    private String memo;//备注
    private Boolean markFlag;//高亮标识
    private String serviceSerialNum;

    public String getServiceSerialNum() {
        return serviceSerialNum;
    }

    public void setServiceSerialNum(String serviceSerialNum) {
        this.serviceSerialNum = serviceSerialNum;
    }

    public Boolean getMarkFlag() {
        return markFlag;
    }

    public void setMarkFlag(Boolean markFlag) {
        this.markFlag = markFlag;
    }

    public String getFinancialSerialNum() {
        return financialSerialNum;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public String getAccount() {
        return account;
    }

    public Double getInFee() {
        return inFee;
    }

    public Double getOutFee() {
        return outFee;
    }

    public Double getBalance() {
        return balance;
    }

    public String getMode() {
        return mode;
    }

    public String getType() {
        return type;
    }

    public String getMemo() {
        return memo;
    }

    public void setFinancialSerialNum(String financialSerialNum) {
        this.financialSerialNum = financialSerialNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setInFee(Double inFee) {
        this.inFee = inFee;
    }

    public void setOutFee(Double outFee) {
        this.outFee = outFee;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    protected String formatTime(Date time){
        if(time != null){
            return sdf.format(time);
        }else {
            return null;
        }
    }
}




