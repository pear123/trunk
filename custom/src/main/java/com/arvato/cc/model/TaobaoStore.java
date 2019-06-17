package com.arvato.cc.model;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

/**
 * TaobaoStore entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "taobao_store")
public class TaobaoStore implements java.io.Serializable {

	// Fields

	private Integer id;
	private String code;
	private String name;
    private String sname;
	private String alipayNo;

	// Constructors

	/** default constructor */
	public TaobaoStore() {
	}

	/** minimal constructor */
	public TaobaoStore(String name) {
		this.name = name;
	}

	/** full constructor */
	public TaobaoStore(String code, String name, String alipayNo) {
		this.code = code;
		this.name = name;
		this.alipayNo = alipayNo;
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

	@Column(name = "CODE", length = 100)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "NAME", nullable = false, length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

    @Column(name = "SNAME", nullable = false, length = 100)
    public String getSname() {
        return this.sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

	@Column(name = "ALIPAY_NO", length = 100)
	public String getAlipayNo() {
		return this.alipayNo;
	}

	public void setAlipayNo(String alipayNo) {
		this.alipayNo = alipayNo;
	}

}