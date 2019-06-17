package com.arvato.cc.form.finance;

import java.util.Date;

/**
 * 退款报表
 * 
 * @author LIUA005
 * 
 */
public class RefundReport {
	// ------------------退款申请基本信息--------------------------
	// -----GL
	private Date applyReturnTime; // 申请退货时间， CS生成退货单时间
	private String hybrisOrderNo; // Hybris订单号
	private String eosNo; // EOS生成的入库单及出库单号
	private String reasonCode; // 退货原因代码
	private String userId; // Hybris的用户ID
	// -----end GL
	private String omsOrderNo; // 退货时， AOMS生成一个新的流水号

	private String omsOrderStatus; // OMS中的定单状态
	private String refundType; // 线上，线下
	private String type; // 退货，退款
	private String promotionIds; // 促销ID
	private String productSKUs; // 销售商品的SKU
	private String quantitys; // 销售商品的数量

	// -------------退款申请信息-------------------
	private String returnProductSKUs; //
	private String returnQuantitys; //
	private Double returnProductAmount; //
	private Double returnPromotion; //
	private Double returnVoucher; //
	private Double otherPromotion; //
	private Double memberIntegralAmount; // 积分抵用金额
	private String returnPostage; // 是否退第一次邮费，根据原因代码查找
	private Double postage; // 原订单中的邮费
	private Double retrievePostage; // 需收回邮费，如果有为10元
	private Double refundAmount; // 退款总金额=

	// ------------原销售交易单收款信息----------------
	private Date reallyReceiveMoneyTime; // PSP实际收款时间
	private String pspType; // 收款方式
	private Double userReallyPayAmount; // 顾客实付金额

	// ------------产生差异说明-----------------------
	private String refundAmountPayAmountDifferent; // =refundAmount - userReallyPayAmount
	private String differentReason; // 差异原因

	// ------------退款申请物流信息--------------------------
	// -------GL
	private String returnInTime; // EOS退货入库时间
	private String returnExpTrackingNum; // 退货快递单号
	private String refundReason; // CS在下退货申请时填写的原因描述
	private Double returnWeight; // 重量
	private String receiveAddress; // 收货地
	private Double payPostage; // 赔付邮费
	private String postageAccepter; // 赔付邮费承担方
	private String userAccount; // 退款账户
	// ------end GL
	private String returnInStatus; // EOS系统反馈商品入库情况

	// ------------实际退款信息----------------------------
	private Double reallyRefundAmount; // 订单实际退款总金额
	// -------GL
	private String refundStatus; // PSP处得到订单退款状态
	private String refundFailedReason; // PSP处得到订单退款失败原因

	private Date refundTime; // PSP付款时间
	// -------end GL

	public Date getApplyReturnTime() {
		return applyReturnTime;
	}

	public void setApplyReturnTime(Date applyReturnTime) {
		this.applyReturnTime = applyReturnTime;
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

	public String getReasonCode() {
		return reasonCode;
	}

	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOmsOrderStatus() {
		return omsOrderStatus;
	}

	public void setOmsOrderStatus(String omsOrderStatus) {
		this.omsOrderStatus = omsOrderStatus;
	}

	public String getRefundType() {
		return refundType;
	}

	public void setRefundType(String refundType) {
		this.refundType = refundType;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPromotionIds() {
		return promotionIds;
	}

	public void setPromotionIds(String promotionIds) {
		this.promotionIds = promotionIds;
	}

	public String getProductSKUs() {
		return productSKUs;
	}

	public void setProductSKUs(String productSKUs) {
		this.productSKUs = productSKUs;
	}

	public String getQuantitys() {
		return quantitys;
	}

	public void setQuantitys(String quantitys) {
		this.quantitys = quantitys;
	}

	public String getReturnProductSKUs() {
		return returnProductSKUs;
	}

	public void setReturnProductSKUs(String returnProductSKUs) {
		this.returnProductSKUs = returnProductSKUs;
	}

	public String getReturnQuantitys() {
		return returnQuantitys;
	}

	public void setReturnQuantitys(String returnQuantitys) {
		this.returnQuantitys = returnQuantitys;
	}

	public Double getReturnProductAmount() {
		return returnProductAmount;
	}

	public void setReturnProductAmount(Double returnProductAmount) {
		this.returnProductAmount = returnProductAmount;
	}

	public Double getReturnPromotion() {
		return returnPromotion;
	}

	public void setReturnPromotion(Double returnPromotion) {
		this.returnPromotion = returnPromotion;
	}

	public Double getReturnVoucher() {
		return returnVoucher;
	}

	public void setReturnVoucher(Double returnVoucher) {
		this.returnVoucher = returnVoucher;
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

	public String getReturnPostage() {
		return returnPostage;
	}

	public void setReturnPostage(String returnPostage) {
		this.returnPostage = returnPostage;
	}

	public Double getPostage() {
		return postage;
	}

	public void setPostage(Double postage) {
		this.postage = postage;
	}

	public Double getRetrievePostage() {
		return retrievePostage;
	}

	public void setRetrievePostage(Double retrievePostage) {
		this.retrievePostage = retrievePostage;
	}

	public Double getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(Double refundAmount) {
		this.refundAmount = refundAmount;
	}

	public Date getReallyReceiveMoneyTime() {
		return reallyReceiveMoneyTime;
	}

	public void setReallyReceiveMoneyTime(Date reallyReceiveMoneyTime) {
		this.reallyReceiveMoneyTime = reallyReceiveMoneyTime;
	}

	public String getPspType() {
		return pspType;
	}

	public void setPspType(String pspType) {
		this.pspType = pspType;
	}

	public Double getUserReallyPayAmount() {
		return userReallyPayAmount;
	}

	public void setUserReallyPayAmount(Double userReallyPayAmount) {
		this.userReallyPayAmount = userReallyPayAmount;
	}

	public String getRefundAmountPayAmountDifferent() {
		return refundAmountPayAmountDifferent;
	}

	public void setRefundAmountPayAmountDifferent(
			String refundAmountPayAmountDifferent) {
		this.refundAmountPayAmountDifferent = refundAmountPayAmountDifferent;
	}

	public String getDifferentReason() {
		return differentReason;
	}

	public void setDifferentReason(String differentReason) {
		this.differentReason = differentReason;
	}

	public String getReturnInTime() {
		return returnInTime;
	}

	public void setReturnInTime(String returnInTime) {
		this.returnInTime = returnInTime;
	}

	public String getReturnExpTrackingNum() {
		return returnExpTrackingNum;
	}

	public void setReturnExpTrackingNum(String returnExpTrackingNum) {
		this.returnExpTrackingNum = returnExpTrackingNum;
	}

	public String getReturnInStatus() {
		return returnInStatus;
	}

	public void setReturnInStatus(String returnInStatus) {
		this.returnInStatus = returnInStatus;
	}

	public String getRefundReason() {
		return refundReason;
	}

	public void setRefundReason(String refundReason) {
		this.refundReason = refundReason;
	}

	public Double getReturnWeight() {
		return returnWeight;
	}

	public void setReturnWeight(Double returnWeight) {
		this.returnWeight = returnWeight;
	}

	public String getReceiveAddress() {
		return receiveAddress;
	}

	public void setReceiveAddress(String receiveAddress) {
		this.receiveAddress = receiveAddress;
	}

	public Double getPayPostage() {
		return payPostage;
	}

	public void setPayPostage(Double payPostage) {
		this.payPostage = payPostage;
	}

	public String getPostageAccepter() {
		return postageAccepter;
	}

	public void setPostageAccepter(String postageAccepter) {
		this.postageAccepter = postageAccepter;
	}

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public Double getReallyRefundAmount() {
		return reallyRefundAmount;
	}

	public void setReallyRefundAmount(Double reallyRefundAmount) {
		this.reallyRefundAmount = reallyRefundAmount;
	}

	public String getRefundStatus() {
		return refundStatus;
	}

	public void setRefundStatus(String refundStatus) {
		this.refundStatus = refundStatus;
	}

	public String getRefundFailedReason() {
		return refundFailedReason;
	}

	public void setRefundFailedReason(String refundFailedReason) {
		this.refundFailedReason = refundFailedReason;
	}

	public Date getRefundTime() {
		return refundTime;
	}

	public void setRefundTime(Date refundTime) {
		this.refundTime = refundTime;
	}

}
