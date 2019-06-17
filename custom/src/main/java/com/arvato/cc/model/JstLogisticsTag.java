package com.arvato.cc.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

/**
 * JstLogisticsTag entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "jst_logistics_tag")
public class JstLogisticsTag implements java.io.Serializable {

	// Fields

	private Integer logisticsTagId;
	private JstTrade jstTrade;
	private String orderId;
	private Set<JstLogisticsServiceTag> jstLogisticsServiceTags = new HashSet<JstLogisticsServiceTag>(
			0);

	// Constructors

	/** default constructor */
	public JstLogisticsTag() {
	}

	/** minimal constructor */
	public JstLogisticsTag(JstTrade jstTrade) {
		this.jstTrade = jstTrade;
	}

	/** full constructor */
	public JstLogisticsTag(JstTrade jstTrade, String orderId,
			Set<JstLogisticsServiceTag> jstLogisticsServiceTags) {
		this.jstTrade = jstTrade;
		this.orderId = orderId;
		this.jstLogisticsServiceTags = jstLogisticsServiceTags;
	}

	// Property accessors
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "LOGISTICS_TAG_ID", unique = true, nullable = false)
	public Integer getLogisticsTagId() {
		return this.logisticsTagId;
	}

	public void setLogisticsTagId(Integer logisticsTagId) {
		this.logisticsTagId = logisticsTagId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TRADE_SYS_ID", nullable = false)
	public JstTrade getJstTrade() {
		return this.jstTrade;
	}

	public void setJstTrade(JstTrade jstTrade) {
		this.jstTrade = jstTrade;
	}

	@Column(name = "ORDER_ID", length = 100)
	public String getOrderId() {
		return this.orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "jstLogisticsTag")
	public Set<JstLogisticsServiceTag> getJstLogisticsServiceTags() {
		return this.jstLogisticsServiceTags;
	}

	public void setJstLogisticsServiceTags(
			Set<JstLogisticsServiceTag> jstLogisticsServiceTags) {
		this.jstLogisticsServiceTags = jstLogisticsServiceTags;
	}

}