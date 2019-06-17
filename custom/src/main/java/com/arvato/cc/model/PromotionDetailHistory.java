package com.arvato.cc.model;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

/**
 * PromotionDetailHistory entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "promotion_detail_history")
public class PromotionDetailHistory implements java.io.Serializable {

	// Fields

	private Integer promotionDetailId;
	private TradeHistory tradeHistory;
	private String id;
	private String promotionName;
	private Double discountFee;
	private String promotionDesc;
	private String promotionId;
	private String promotionType;

	// Constructors

	/** default constructor */
	public PromotionDetailHistory() {
	}

	/** minimal constructor */
	public PromotionDetailHistory(TradeHistory tradeHistory) {
		this.tradeHistory = tradeHistory;
	}

	/** full constructor */
	public PromotionDetailHistory(TradeHistory tradeHistory, String id,
			String promotionName, Double discountFee, String promotionDesc,
			String promotionId, String promotionType) {
		this.tradeHistory = tradeHistory;
		this.id = id;
		this.promotionName = promotionName;
		this.discountFee = discountFee;
		this.promotionDesc = promotionDesc;
		this.promotionId = promotionId;
		this.promotionType = promotionType;
	}

	// Property accessors
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PROMOTION_DETAIL_ID", unique = true, nullable = false)
	public Integer getPromotionDetailId() {
		return this.promotionDetailId;
	}

	public void setPromotionDetailId(Integer promotionDetailId) {
		this.promotionDetailId = promotionDetailId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TRADE_SYS_ID", nullable = false)
	public TradeHistory getTradeHistory() {
		return this.tradeHistory;
	}

	public void setTradeHistory(TradeHistory tradeHistory) {
		this.tradeHistory = tradeHistory;
	}

	@Column(name = "ID", length = 200)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "PROMOTION_NAME", length = 100)
	public String getPromotionName() {
		return this.promotionName;
	}

	public void setPromotionName(String promotionName) {
		this.promotionName = promotionName;
	}

	@Column(name = "DISCOUNT_FEE", precision = 22, scale = 0)
	public Double getDiscountFee() {
		return this.discountFee;
	}

	public void setDiscountFee(Double discountFee) {
		this.discountFee = discountFee;
	}

	@Column(name = "PROMOTION_DESC", length = 100)
	public String getPromotionDesc() {
		return this.promotionDesc;
	}

	public void setPromotionDesc(String promotionDesc) {
		this.promotionDesc = promotionDesc;
	}

	@Column(name = "PROMOTION_ID", length = 100)
	public String getPromotionId() {
		return this.promotionId;
	}

	public void setPromotionId(String promotionId) {
		this.promotionId = promotionId;
	}

	@Column(name = "promotion_type", length = 100)
	public String getPromotionType() {
		return this.promotionType;
	}

	public void setPromotionType(String promotionType) {
		this.promotionType = promotionType;
	}

}