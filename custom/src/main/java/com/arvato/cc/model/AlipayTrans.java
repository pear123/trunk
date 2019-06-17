package com.arvato.cc.model;

import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.*;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

/**
 * AlipayTrans entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Table(name = "alipay_trans")
public class AlipayTrans implements java.io.Serializable {

	// Fields

	private Integer id;
	private String financialSerialNum;
	private String serviceSerialNum;
    private Timestamp createTime;
	private Double inFee;
    private String tid;

	// Constructors

	/** default constructor */
	public AlipayTrans() {
	}

	/** minimal constructor */
	public AlipayTrans(String serviceSerialNum) {
		this.serviceSerialNum = serviceSerialNum;
	}

	/** full constructor */
	public AlipayTrans(String financialSerialNum, String serviceSerialNum,
                       Timestamp createTime, Double inFee) {
		this.financialSerialNum = financialSerialNum;
		this.serviceSerialNum = serviceSerialNum;
		this.createTime = createTime;
		this.inFee = inFee;
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

	@Column(name = "FINANCIAL_SERIAL_NUM", length = 100)
	public String getFinancialSerialNum() {
		return this.financialSerialNum;
	}

	public void setFinancialSerialNum(String financialSerialNum) {
		this.financialSerialNum = financialSerialNum;
	}

	@Column(name = "SERVICE_SERIAL_NUM", nullable = false, length = 100)
	public String getServiceSerialNum() {
		return this.serviceSerialNum;
	}

	public void setServiceSerialNum(String serviceSerialNum) {
		this.serviceSerialNum = serviceSerialNum;
	}

	@Column(name = "CREATE_TIME", length = 19)
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name = "IN_FEE", precision = 22, scale = 0)
	public Double getInFee() {
		return this.inFee;
	}

	public void setInFee(Double inFee) {
		this.inFee = inFee;
	}

    @Column(name = "TID", length = 100)
    public String getTid() {
        return this.tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

}