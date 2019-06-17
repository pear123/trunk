package com.arvato.cc.form.finance;

import java.util.Date;

/**
 * 索赔报表
 * 
 * @author LIUA005
 * 
 */
public class IndemnityReport {
	private Date deliverTime; // EOS发货时间
	private Date returnTime; // CS创建入库单的日期
	private String expTrackingNum; // 快递单号
	private String hybrisOrderNo; // Hybris订单号
	private String eosNo; // EOS生成的入库单及出库单号
	private String omsOrderNo; // 退货时， AOMS生成一个新的流水号
	private String returnInTime; // EOS退货入库时间
	private String returnInStatus; // EOS系统反馈商品入库情况
	private String reasonCode; // 退货原因代码
	private String productCostAccepter; // 商品价值承担方
	private String productSku; // 退货商品SKU
	private String productName; // 退货商品名称
	private Double productCost; // 商品折后价
	private Integer quantity; // 数量
	private Double amount; // =productCost * quantity
	private Double postage; // 邮费，与快递公司结算的邮费，非收取用户的邮费
	@SuppressWarnings("unused")
	private Double indemnityTotalAmount; // =amount + postage
	private String remark;

	public Date getDeliverTime() {
		return deliverTime;
	}

	public void setDeliverTime(Date deliverTime) {
		this.deliverTime = deliverTime;
	}

	public Date getReturnTime() {
		return returnTime;
	}

	public void setReturnTime(Date returnTime) {
		this.returnTime = returnTime;
	}

	public String getExpTrackingNum() {
		return expTrackingNum;
	}

	public void setExpTrackingNum(String expTrackingNum) {
		this.expTrackingNum = expTrackingNum;
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

	public String getReturnInTime() {
		return returnInTime;
	}

	public void setReturnInTime(String returnInTime) {
		this.returnInTime = returnInTime;
	}

	public String getReturnInStatus() {
		return returnInStatus;
	}

	public void setReturnInStatus(String returnInStatus) {
		this.returnInStatus = returnInStatus;
	}

	public String getReasonCode() {
		return reasonCode;
	}

	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}

	public String getProductCostAccepter() {
		return productCostAccepter;
	}

	public void setProductCostAccepter(String productCostAccepter) {
		this.productCostAccepter = productCostAccepter;
	}

	public String getProductSku() {
		return productSku;
	}

	public void setProductSku(String productSku) {
		this.productSku = productSku;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Double getProductCost() {
		return productCost;
	}

	public void setProductCost(Double productCost) {
		this.productCost = productCost;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Double getAmount() {
		return amount;
	}

    public void setAmount(Double Amount) {
        this.amount = Amount;
    }

	public Double getPostage() {
		return postage;
	}

	public void setPostage(Double postage) {
		this.postage = postage;
	}

	public Double getIndemnityTotalAmount() {
		return indemnityTotalAmount;
	}

    public void setIndemnityTotalAmount(Double indemnityTotalAmount) {
        this.indemnityTotalAmount = indemnityTotalAmount;
    }
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
