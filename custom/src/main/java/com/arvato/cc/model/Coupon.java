package com.arvato.cc.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Coupon entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "coupon")
public class Coupon implements java.io.Serializable {

	// Fields

	private Integer id;
	private String couponCode;
	private String couponName;
	private Double couponPrice;

	// Constructors

	/** default constructor */
	public Coupon() {
	}

	/** full constructor */
	public Coupon(String couponCode, String couponName, Double couponPrice) {
		this.couponCode = couponCode;
		this.couponName = couponName;
		this.couponPrice = couponPrice;
	}

	// Property accessors
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "coupon_code", length = 200)
	public String getCouponCode() {
		return this.couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	@Column(name = "coupon_name", length = 100)
	public String getCouponName() {
		return this.couponName;
	}

	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}

	@Column(name = "coupon_price", precision = 22, scale = 0)
	public Double getCouponPrice() {
		return this.couponPrice;
	}

	public void setCouponPrice(Double couponPrice) {
		this.couponPrice = couponPrice;
	}

}