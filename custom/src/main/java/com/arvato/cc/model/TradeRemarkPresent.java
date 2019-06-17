package com.arvato.cc.model;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

/**
 * TradeRemarkPresent entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "trade_remark_present")
public class TradeRemarkPresent implements java.io.Serializable {

	// Fields

	private Integer id;
	private TradeRemark tradeRemark;
	private String presentReason;
	private String presentName;
	private Integer presentQuantity;

	// Constructors

	/** default constructor */
	public TradeRemarkPresent() {
	}

	/** minimal constructor */
	public TradeRemarkPresent(TradeRemark tradeRemark) {
		this.tradeRemark = tradeRemark;
	}

	/** full constructor */
	public TradeRemarkPresent(TradeRemark tradeRemark, String presentReason,
			String presentName, Integer presentQuantity) {
		this.tradeRemark = tradeRemark;
		this.presentReason = presentReason;
		this.presentName = presentName;
		this.presentQuantity = presentQuantity;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TRADE_REMARK_sys_id", nullable = false)
	public TradeRemark getTradeRemark() {
		return this.tradeRemark;
	}

	public void setTradeRemark(TradeRemark tradeRemark) {
		this.tradeRemark = tradeRemark;
	}

	@Column(name = "PRESENT_REASON", length = 200)
	public String getPresentReason() {
		return this.presentReason;
	}

	public void setPresentReason(String presentReason) {
		this.presentReason = presentReason;
	}

	@Column(name = "PRESENT_NAME", length = 100)
	public String getPresentName() {
		return this.presentName;
	}

	public void setPresentName(String presentName) {
		this.presentName = presentName;
	}

	@Column(name = "PRESENT_QUANTITY")
	public Integer getPresentQuantity() {
		return this.presentQuantity;
	}

	public void setPresentQuantity(Integer presentQuantity) {
		this.presentQuantity = presentQuantity;
	}

}