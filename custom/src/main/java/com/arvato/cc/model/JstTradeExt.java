package com.arvato.cc.model;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

/**
 * JstTradeExt entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "jst_trade_ext")
public class JstTradeExt implements java.io.Serializable {

	// Fields

	private Integer tradeExtId;
	private JstTrade jstTrade;
	private String beforeEnableFlag;
	private String beforeCloseFlag;
	private String beforePayFlag;
	private String beforeShipFlag;
	private String beforeConfirmFlag;
	private String beforeRateFlag;
	private String beforeRefundFlag;
	private String beforeModifyFlag;
	private String thirdPartyStatus;
	private String extraData;
	private String extAttributes;

	// Constructors

	/** default constructor */
	public JstTradeExt() {
	}

	/** minimal constructor */
	public JstTradeExt(JstTrade jstTrade) {
		this.jstTrade = jstTrade;
	}

	/** full constructor */
	public JstTradeExt(JstTrade jstTrade, String beforeEnableFlag,
			String beforeCloseFlag, String beforePayFlag,
			String beforeShipFlag, String beforeConfirmFlag,
			String beforeRateFlag, String beforeRefundFlag,
			String beforeModifyFlag, String thirdPartyStatus, String extraData,
			String extAttributes) {
		this.jstTrade = jstTrade;
		this.beforeEnableFlag = beforeEnableFlag;
		this.beforeCloseFlag = beforeCloseFlag;
		this.beforePayFlag = beforePayFlag;
		this.beforeShipFlag = beforeShipFlag;
		this.beforeConfirmFlag = beforeConfirmFlag;
		this.beforeRateFlag = beforeRateFlag;
		this.beforeRefundFlag = beforeRefundFlag;
		this.beforeModifyFlag = beforeModifyFlag;
		this.thirdPartyStatus = thirdPartyStatus;
		this.extraData = extraData;
		this.extAttributes = extAttributes;
	}

	// Property accessors
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TRADE_EXT_ID", unique = true, nullable = false)
	public Integer getTradeExtId() {
		return this.tradeExtId;
	}

	public void setTradeExtId(Integer tradeExtId) {
		this.tradeExtId = tradeExtId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TRADE_SYS_ID", nullable = false)
	public JstTrade getJstTrade() {
		return this.jstTrade;
	}

	public void setJstTrade(JstTrade jstTrade) {
		this.jstTrade = jstTrade;
	}

	@Column(name = "BEFORE_ENABLE_FLAG", length = 50)
	public String getBeforeEnableFlag() {
		return this.beforeEnableFlag;
	}

	public void setBeforeEnableFlag(String beforeEnableFlag) {
		this.beforeEnableFlag = beforeEnableFlag;
	}

	@Column(name = "BEFORE_CLOSE_FLAG", length = 50)
	public String getBeforeCloseFlag() {
		return this.beforeCloseFlag;
	}

	public void setBeforeCloseFlag(String beforeCloseFlag) {
		this.beforeCloseFlag = beforeCloseFlag;
	}

	@Column(name = "BEFORE_PAY_FLAG", length = 50)
	public String getBeforePayFlag() {
		return this.beforePayFlag;
	}

	public void setBeforePayFlag(String beforePayFlag) {
		this.beforePayFlag = beforePayFlag;
	}

	@Column(name = "BEFORE_SHIP_FLAG", length = 50)
	public String getBeforeShipFlag() {
		return this.beforeShipFlag;
	}

	public void setBeforeShipFlag(String beforeShipFlag) {
		this.beforeShipFlag = beforeShipFlag;
	}

	@Column(name = "BEFORE_CONFIRM_FLAG", length = 50)
	public String getBeforeConfirmFlag() {
		return this.beforeConfirmFlag;
	}

	public void setBeforeConfirmFlag(String beforeConfirmFlag) {
		this.beforeConfirmFlag = beforeConfirmFlag;
	}

	@Column(name = "BEFORE_RATE_FLAG", length = 50)
	public String getBeforeRateFlag() {
		return this.beforeRateFlag;
	}

	public void setBeforeRateFlag(String beforeRateFlag) {
		this.beforeRateFlag = beforeRateFlag;
	}

	@Column(name = "BEFORE_REFUND_FLAG", length = 50)
	public String getBeforeRefundFlag() {
		return this.beforeRefundFlag;
	}

	public void setBeforeRefundFlag(String beforeRefundFlag) {
		this.beforeRefundFlag = beforeRefundFlag;
	}

	@Column(name = "BEFORE_MODIFY_FLAG", length = 50)
	public String getBeforeModifyFlag() {
		return this.beforeModifyFlag;
	}

	public void setBeforeModifyFlag(String beforeModifyFlag) {
		this.beforeModifyFlag = beforeModifyFlag;
	}

	@Column(name = "THIRD_PARTY_STATUS", length = 50)
	public String getThirdPartyStatus() {
		return this.thirdPartyStatus;
	}

	public void setThirdPartyStatus(String thirdPartyStatus) {
		this.thirdPartyStatus = thirdPartyStatus;
	}

	@Column(name = "EXTRA_DATA", length = 50)
	public String getExtraData() {
		return this.extraData;
	}

	public void setExtraData(String extraData) {
		this.extraData = extraData;
	}

	@Column(name = "EXT_ATTRIBUTES", length = 50)
	public String getExtAttributes() {
		return this.extAttributes;
	}

	public void setExtAttributes(String extAttributes) {
		this.extAttributes = extAttributes;
	}

}