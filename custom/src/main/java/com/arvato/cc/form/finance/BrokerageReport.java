package com.arvato.cc.form.finance;

import org.apache.commons.lang.StringUtils;

import java.text.DecimalFormat;
import java.util.Date;

/**
 * 佣金报表
 *
 * @author LIUA005
 */
public class BrokerageReport {
    private static Double CSRate = 0.007;

    protected DecimalFormat df = new DecimalFormat("##.00");

    private String hybrisOrderNo; // Hybris订单号
    private String eosNo; // EOS生成的入库单及出库单号
    private String omsOrderNo; // 退货时， AOMS生成一个新的流水号
    private Date deliverTime; // EOS发货时间
    private Date hybrisOrderTradeTime; // 顾客下单付款的日期
    private Date userConfirmReceiveTime; // 顾客确认收货时间
    private Date reallyReceiveMoneyTime; // PSP实际收款时间
    private Date bankReceiveMoneyTime; // 银行实际到帐时间
    private String pspType; // 收款方式
    private Double payAmount; // 实收款
    private Double reallyPspBrokerage; // PSP实扣款
    private Double pspRate; // PSP扣款费率

    private Double pspBrokerage; // =payAmount * pspRate   应扣款

    private Double pspBrokerageDifferent; // =pspBrokerage - reallyPspBrokerage  实扣/应扣差异
    private String pspProductType; // Payment Getway产品渠道

    public String getHybrisOrderNo() {
        return hybrisOrderNo;
    }

    public void setHybrisOrderNo(String hybrisOrderNo) {
        this.hybrisOrderNo = hybrisOrderNo;
    }

    public String getEosNo() {
        return eosNo;
    }

    public void setEosNo(String eosNo) {
        this.eosNo = eosNo;
    }

    public String getOmsOrderNo() {
        return omsOrderNo;
    }

    public void setOmsOrderNo(String omsOrderNo) {
        this.omsOrderNo = omsOrderNo;
    }

    public Date getDeliverTime() {
        return deliverTime;
    }

    public void setDeliverTime(Date deliverTime) {
        this.deliverTime = deliverTime;
    }

    public Date getHybrisOrderTradeTime() {
        return hybrisOrderTradeTime;
    }

    public void setHybrisOrderTradeTime(Date hybrisOrderTradeTime) {
        this.hybrisOrderTradeTime = hybrisOrderTradeTime;
    }

    public Date getUserConfirmReceiveTime() {
        return userConfirmReceiveTime;
    }

    public void setUserConfirmReceiveTime(Date userConfirmReceiveTime) {
        this.userConfirmReceiveTime = userConfirmReceiveTime;
    }

    public Date getReallyReceiveMoneyTime() {
        return reallyReceiveMoneyTime;
    }

    public void setReallyReceiveMoneyTime(Date reallyReceiveMoneyTime) {
        this.reallyReceiveMoneyTime = reallyReceiveMoneyTime;
    }

    public Date getBankReceiveMoneyTime() {
        return bankReceiveMoneyTime;
    }

    public void setBankReceiveMoneyTime(Date bankReceiveMoneyTime) {
        this.bankReceiveMoneyTime = bankReceiveMoneyTime;
    }

    public String getPspType() {
        return pspType;
    }

    public void setPspType(String pspType) {
        this.pspType = pspType;
    }

    public Double getPayAmount() {
        if (null == payAmount) {
            return 0.00;
        }
        return payAmount;
    }

    public void setPayAmount(Double payAmount) {
        this.payAmount = payAmount;
    }

    public Double getReallyPspBrokerage() {
        if (null == reallyPspBrokerage) {
            return 0.00;
        }
        return reallyPspBrokerage;
    }

    public void setReallyPspBrokerage(Double reallyPspBrokerage) {

        this.reallyPspBrokerage = reallyPspBrokerage;
    }

    public Double getPspRate() {
        if (null == pspRate) {
            return 0.00;
        }
        return pspRate;
    }

    public void setPspRate(Double pspRate) {

        this.pspRate = pspRate;
    }

    public Double getPspBrokerage() {
        if (null == pspBrokerage) {
            return 0.00;
        }
        return pspBrokerage;
    }

    public Double getPspBrokerageDifferent() {
        if (null == this.pspBrokerageDifferent) {
            return 0.00;
        }
        return this.pspBrokerageDifferent;
    }

    public String getPspProductType() {
        if (StringUtils.isBlank(pspProductType)) {
            return "";
        }

        return pspProductType;
    }

    public void setPspProductType(String pspProductType) {
        this.pspProductType = pspProductType;
    }

    public void setPspBrokerage(Double pspBrokerage) {
        this.pspBrokerage = pspBrokerage;
    }

    public void setPspBrokerageDifferent(Double pspBrokerageDifferent) {
        this.pspBrokerageDifferent = pspBrokerageDifferent;
    }
}
