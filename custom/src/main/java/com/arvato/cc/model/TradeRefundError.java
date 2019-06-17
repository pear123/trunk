package com.arvato.cc.model;

import javax.persistence.*;
import java.sql.Timestamp;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * TradeRefund entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TRADE_REFUND_ERROR")
public class TradeRefundError implements java.io.Serializable {

	// Fields

	private Integer id;
	private String refundId;

	// Constructors

	/** default constructor */
	public TradeRefundError() {
	}


	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}



	@Column(name = "refund_id", nullable = false, length = 100)
	public String getRefundId() {
		return this.refundId;
	}

	public void setRefundId(String refundId) {
		this.refundId = refundId;
	}


}