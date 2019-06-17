package com.arvato.cc.form.finance;

import java.util.Date;

/**
 * 应付应收报表
 * 
 * @author LIUA005
 * 
 */
public class PayAndReceiveReport {
	private Date orderPayTime; // 顾客交易付款时间
	private String pspType; // 顾客付款方式
	private String deliverOrReturnInTime; // 发货/退货时间
	private String inSameWeekDeliver; // 是否交易当周发货（Y/N）
	private String hybrisOrderNo; // Hybris订单号
	private String eosNo; // EOS生成的入库单及出库单号
	private String omsOrderNo; // 退货时， AOMS生成一个新的流水号
	private String rowType; //
	private String orderStatus; //
	private String tradeType; //
	private String refundType; // 线上，线下
	private String remark; //
	private String promotionIds; //
	private String reasonCode;
	private String couponType; // 礼券类型
	private String couponNo; // 优惠券号
	private Double originalOrderAmount; // 商品原含税销售金额
	private Double promotion; // 普通折扣
	private Double voucher; // 其他礼券
	private Double otherPromotion; // 其他折扣
	private Double memberIntegralAmount; // 积分抵用金额
	private Double postage; // 原订单中的邮费
	private Double tradeAmount; // 应收顾客商品交易金额
	private Double noTaxProductsAmount; // 免税商品销售促销折后含税销售金额
	private Double noTaxProductsOtherVoucher; // 免税商品其他礼券
	private Double productsOtherVoucher; // 非免税商品其它礼券
	private Double noTaxProductsOtherPromotion; // 免税商品其他折扣
	private Double productsOtherPromotion; // 非免税商品其他折扣
	private Double noTaxProductsUseIntegral; // 免税商品会员积分换购
	private Double productsUseIntegral; // 非免税商品会员积分换购
	private Double retrievePostage; // 需收回邮费，如果有为10元
	private Double userPayAmount; // 顾客应付金额
	private Date receiveMoneyTime; // 应收款时间
	private Date reallyReceiveMoneyTime; // 实际收款时间
	private Date bankReceiveMoneyTime; // 银行实际到帐时间
	private int receiveOvertime; // 超期收款天数
	private Double reallyReceiveAmount; // 实收交易金额
	private Double receiveAmountDiff; // 应收差异金额
    private String interfacePayMethod;//银联接口支付方式 暂为空
	private Double pspBrokerage; // 预提手续费=userPayAmount*psp费率
	private Double reallyPpspBrokerage; // 实付手续费
	private Double pspBrokerageDiff; // 手续费差异=

    public String getInterfacePayMethod() {
        return interfacePayMethod;
    }

    public void setInterfacePayMethod(String interfacePayMethod) {
        this.interfacePayMethod = interfacePayMethod;
    }

    public Date getOrderPayTime() {
		return orderPayTime;
	}

	public void setOrderPayTime(Date orderPayTime) {
		this.orderPayTime = orderPayTime;
	}

	public String getPspType() {
		return pspType;
	}

	public void setPspType(String pspType) {
		this.pspType = pspType;
	}

	public String getDeliverOrReturnInTime() {
		return deliverOrReturnInTime;
	}

	public void setDeliverOrReturnInTime(String deliverOrReturnInTime) {
		this.deliverOrReturnInTime = deliverOrReturnInTime;
	}

	public String getInSameWeekDeliver() {
		return inSameWeekDeliver;
	}

	public void setInSameWeekDeliver(String inSameWeekDeliver) {
		this.inSameWeekDeliver = inSameWeekDeliver;
	}

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

	public String getRowType() {
		return rowType;
	}

	public void setRowType(String rowType) {
		this.rowType = rowType;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public String getRefundType() {
		return refundType;
	}

	public void setRefundType(String refundType) {
		this.refundType = refundType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPromotionIds() {
		return promotionIds;
	}

	public void setPromotionIds(String promotionIds) {
		this.promotionIds = promotionIds;
	}

	public String getReasonCode() {
		return reasonCode;
	}

	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}

	public String getCouponType() {
		return couponType;
	}

	public void setCouponType(String couponType) {
		this.couponType = couponType;
	}

	public String getCouponNo() {
		return couponNo;
	}

	public void setCouponNo(String couponNo) {
		this.couponNo = couponNo;
	}

	public Double getOriginalOrderAmount() {
		return originalOrderAmount;
	}

	public void setOriginalOrderAmount(Double originalOrderAmount) {
		this.originalOrderAmount = originalOrderAmount;
	}

	public Double getPromotion() {
		return promotion;
	}

	public void setPromotion(Double promotion) {
		this.promotion = promotion;
	}

	public Double getVoucher() {
		return voucher;
	}

	public void setVoucher(Double voucher) {
		this.voucher = voucher;
	}

	public Double getOtherPromotion() {
		return otherPromotion;
	}

	public void setOtherPromotion(Double otherPromotion) {
		this.otherPromotion = otherPromotion;
	}

	public Double getMemberIntegralAmount() {
		return memberIntegralAmount;
	}

	public void setMemberIntegralAmount(Double memberIntegralAmount) {
		this.memberIntegralAmount = memberIntegralAmount;
	}

	public Double getPostage() {
		return postage;
	}

	public void setPostage(Double postage) {
		this.postage = postage;
	}

	public Double getTradeAmount() {
		return tradeAmount;
	}

	public void setTradeAmount(Double tradeAmount) {
		this.tradeAmount = tradeAmount;
	}

	public Double getNoTaxProductsAmount() {
		return noTaxProductsAmount;
	}

	public void setNoTaxProductsAmount(Double noTaxProductsAmount) {
		this.noTaxProductsAmount = noTaxProductsAmount;
	}

	public Double getNoTaxProductsOtherVoucher() {
		return noTaxProductsOtherVoucher;
	}

	public void setNoTaxProductsOtherVoucher(Double noTaxProductsOtherVoucher) {
		this.noTaxProductsOtherVoucher = noTaxProductsOtherVoucher;
	}

	public Double getProductsOtherVoucher() {
		return productsOtherVoucher;
	}

	public void setProductsOtherVoucher(Double productsOtherVoucher) {
		this.productsOtherVoucher = productsOtherVoucher;
	}

	public Double getNoTaxProductsOtherPromotion() {
		return noTaxProductsOtherPromotion;
	}

	public void setNoTaxProductsOtherPromotion(
			Double noTaxProductsOtherPromotion) {
		this.noTaxProductsOtherPromotion = noTaxProductsOtherPromotion;
	}

	public Double getProductsOtherPromotion() {
		return productsOtherPromotion;
	}

	public void setProductsOtherPromotion(Double productsOtherPromotion) {
		this.productsOtherPromotion = productsOtherPromotion;
	}

	public Double getNoTaxProductsUseIntegral() {
		return noTaxProductsUseIntegral;
	}

	public void setNoTaxProductsUseIntegral(Double noTaxProductsUseIntegral) {
		this.noTaxProductsUseIntegral = noTaxProductsUseIntegral;
	}

	public Double getProductsUseIntegral() {
		return productsUseIntegral;
	}

	public void setProductsUseIntegral(Double productsUseIntegral) {
		this.productsUseIntegral = productsUseIntegral;
	}

	public Double getRetrievePostage() {
		return retrievePostage;
	}

	public void setRetrievePostage(Double retrievePostage) {
		this.retrievePostage = retrievePostage;
	}

	public Double getUserPayAmount() {
		return userPayAmount;
	}

	public void setUserPayAmount(Double userPayAmount) {
		this.userPayAmount = userPayAmount;
	}

	public Date getReceiveMoneyTime() {
		return receiveMoneyTime;
	}

	public void setReceiveMoneyTime(Date receiveMoneyTime) {
		this.receiveMoneyTime = receiveMoneyTime;
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

	public int getReceiveOvertime() {
		return receiveOvertime;
	}

	public void setReceiveOvertime(int receiveOvertime) {
		this.receiveOvertime = receiveOvertime;
	}

	public Double getReallyReceiveAmount() {
		return reallyReceiveAmount;
	}

	public void setReallyReceiveAmount(Double reallyReceiveAmount) {
		this.reallyReceiveAmount = reallyReceiveAmount;
	}

	public Double getReceiveAmountDiff() {
		return receiveAmountDiff;
	}

	public void setReceiveAmountDiff(Double receiveAmountDiff) {
		this.receiveAmountDiff = receiveAmountDiff;
	}

	public Double getPspBrokerage() {
		return pspBrokerage;
	}

	public void setPspBrokerage(Double pspBrokerage) {
		this.pspBrokerage = pspBrokerage;
	}

	public Double getReallyPpspBrokerage() {
		return reallyPpspBrokerage;
	}

	public void setReallyPpspBrokerage(Double reallyPpspBrokerage) {
		this.reallyPpspBrokerage = reallyPpspBrokerage;
	}

	public Double getPspBrokerageDiff() {
		return pspBrokerageDiff;
	}

	public void setPspBrokerageDiff(Double pspBrokerageDiff) {
		this.pspBrokerageDiff = pspBrokerageDiff;
	}
}
