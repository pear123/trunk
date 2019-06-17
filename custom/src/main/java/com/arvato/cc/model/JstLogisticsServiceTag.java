package com.arvato.cc.model;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

/**
 * JstLogisticsServiceTag entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "jst_logistics_service_tag")
public class JstLogisticsServiceTag implements java.io.Serializable {

	// Fields

	private Integer logisticsServiceTagId;
	private JstLogisticsTag jstLogisticsTag;
	private String serviceTag;
	private String serviceType;

	// Constructors

	/** default constructor */
	public JstLogisticsServiceTag() {
	}

	/** minimal constructor */
	public JstLogisticsServiceTag(JstLogisticsTag jstLogisticsTag) {
		this.jstLogisticsTag = jstLogisticsTag;
	}

	/** full constructor */
	public JstLogisticsServiceTag(JstLogisticsTag jstLogisticsTag,
			String serviceTag, String serviceType) {
		this.jstLogisticsTag = jstLogisticsTag;
		this.serviceTag = serviceTag;
		this.serviceType = serviceType;
	}

	// Property accessors
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "LOGISTICS_SERVICE_TAG_ID", unique = true, nullable = false)
	public Integer getLogisticsServiceTagId() {
		return this.logisticsServiceTagId;
	}

	public void setLogisticsServiceTagId(Integer logisticsServiceTagId) {
		this.logisticsServiceTagId = logisticsServiceTagId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LOGISTICS_TAG_ID", nullable = false)
	public JstLogisticsTag getJstLogisticsTag() {
		return this.jstLogisticsTag;
	}

	public void setJstLogisticsTag(JstLogisticsTag jstLogisticsTag) {
		this.jstLogisticsTag = jstLogisticsTag;
	}

	@Column(name = "SERVICE_TAG", length = 50)
	public String getServiceTag() {
		return this.serviceTag;
	}

	public void setServiceTag(String serviceTag) {
		this.serviceTag = serviceTag;
	}

	@Column(name = "SERVICE_TYPE", length = 50)
	public String getServiceType() {
		return this.serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

}