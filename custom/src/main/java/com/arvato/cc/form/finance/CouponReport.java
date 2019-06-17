package com.arvato.cc.form.finance;

import java.util.Date;

/**
 * Coupon报表
 * 
 * @author LIUA005
 * 
 */
public class CouponReport {
	private String couponType;
	private String status; // 发行，消费
	private String couponNo; // 优惠券号
	private String userId; // 优惠券发向的客户帐号，hybrais帐号
	private Date startTime; // 有效开始时间
	private Date endTime; // 有效结束时间
	private Double amount; // 金额：状态为发行时是正数， 状态为消费时为负数
    private String orderNo; // 订单号
    private Date couponTime; // 时间
	private String costAccept; // 承担方




	public String getCouponType() {
		return couponType;
	}

	public void setCouponType(String couponType) {
		this.couponType = couponType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCouponNo() {
		return couponNo;
	}

	public void setCouponNo(String couponNo) {
		this.couponNo = couponNo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getCostAccept() {
		return costAccept;
	}

	public void setCostAccept(String costAccept) {
		this.costAccept = costAccept;
	}

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Date getCouponTime() {
        return couponTime;
    }

    public void setCouponTime(Date couponTime) {
        this.couponTime = couponTime;
    }
}
