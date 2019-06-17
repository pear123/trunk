package com.arvato.cc.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * UpdCpd entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "upd_cpd")
public class UpdCpd implements java.io.Serializable {

	// Fields

	private Integer cpdId;
	private String officeName;
	private String code;
	private Integer version;
	private String status;

	// Constructors

	/** default constructor */
	public UpdCpd() {
	}

	/** full constructor */
	public UpdCpd(String officeName, String code, Integer version, String status) {
		this.officeName = officeName;
		this.code = code;
		this.version = version;
		this.status = status;
	}

	// Property accessors
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CPD_ID", unique = true, nullable = false)
	public Integer getCpdId() {
		return this.cpdId;
	}

	public void setCpdId(Integer cpdId) {
		this.cpdId = cpdId;
	}

	@Column(name = "OFFICE_NAME", length = 50)
	public String getOfficeName() {
		return this.officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	@Column(name = "CODE", length = 50)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "VERSION")
    @Version
	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@Column(name = "STATUS", length = 100)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}