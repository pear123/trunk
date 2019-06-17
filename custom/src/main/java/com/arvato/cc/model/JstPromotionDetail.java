package com.arvato.cc.model;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

/**
 * JstPromotionDetail entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "jst_promotion_detail")
public class JstPromotionDetail implements java.io.Serializable {

	// Fields

	private Integer promotionDetailId;
	private JstTrade jstTrade;
	private String id;
	private String promotionName;
	private Double discountFee;
	private String promotionDesc;
	private String promotionId;

	// Constructors

	/** default constructor */
	public JstPromotionDetail() {
	}

	/** minimal constructor */
	public JstPromotionDetail(JstTrade jstTrade, String id) {
		this.jstTrade = jstTrade;
		this.id = id;
	}

	/** full constructor */
	public JstPromotionDetail(JstTrade jstTrade, String id,
			String promotionName, Double discountFee, String promotionDesc,
			String promotionId) {
		this.jstTrade = jstTrade;
		this.id = id;
		this.promotionName = promotionName;
		this.discountFee = discountFee;
		this.promotionDesc = promotionDesc;
		this.promotionId = promotionId;
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
	public JstTrade getJstTrade() {
		return this.jstTrade;
	}

	public void setJstTrade(JstTrade jstTrade) {
		this.jstTrade = jstTrade;
	}

	@Column(name = "ID", nullable = false, length = 200)
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

}