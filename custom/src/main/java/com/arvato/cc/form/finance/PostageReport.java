package com.arvato.cc.form.finance;

import java.util.Date;

/**
 * 运费报表
 * 
 * @author LIUA005
 * 
 */
public class PostageReport {
	private String expTrackingNum; // 快递单号
	private String hybrisOrderNo; // Hybris订单号
	private String eosNo; // EOS生成的出库单号
	private String omsOrderNo; // 退货时， AOMS生成一个新的流水号
	private Date deliverTime; // EOS发货时间
	private String deliveryVendor; // 快递商
	private String warehouseAddress; // 发货地，仓库所在地
	private String userAddress; // 用户收货地
	private Double deliveryWeight; // 发货重量，
	private Double firstAmount; // 首重邮费
	private Double continueAmount; // 续重邮费
	private Double packageAmount; // 包裹单费用
	private Double totalAmount; // =firstAmount + continueAmount + packageAmount
	private String remark;

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

	public Date getDeliverTime() {
		return deliverTime;
	}

	public void setDeliverTime(Date deliverTime) {
		this.deliverTime = deliverTime;
	}

	public String getDeliveryVendor() {
		return deliveryVendor;
	}

	public void setDeliveryVendor(String deliveryVendor) {
		this.deliveryVendor = deliveryVendor;
	}

	public String getWarehouseAddress() {
		return warehouseAddress;
	}

	public void setWarehouseAddress(String warehouseAddress) {
		this.warehouseAddress = warehouseAddress;
	}

	public String getUserAddress() {
		return userAddress;
	}

	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}

	public Double getDeliveryWeight() {
		return deliveryWeight;
	}

	public void setDeliveryWeight(Double deliveryWeight) {
		this.deliveryWeight = deliveryWeight;
	}

	public Double getFirstAmount() {
		return firstAmount;
	}

	public void setFirstAmount(Double firstAmount) {
		this.firstAmount = firstAmount;
	}

	public Double getContinueAmount() {
		return continueAmount;
	}

	public void setContinueAmount(Double continueAmount) {
		this.continueAmount = continueAmount;
	}

	public Double getPackageAmount() {
		return packageAmount;
	}

	public void setPackageAmount(Double packageAmount) {
		this.packageAmount = packageAmount;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
