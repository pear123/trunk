package com.arvato.cc.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Sku entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "sku")
public class Sku implements java.io.Serializable {

	// Fields

	private Integer id;
	private String brand;
	private String cid;
	private String matnr;
	private Double tmallPrice;
	private Double sapprice;
	private Integer version;
	private String status;

	// Constructors

	/** default constructor */
	public Sku() {
	}

	/** full constructor */
	public Sku(String brand, String cid, String matnr, Double tmallPrice,
			Double sapprice, Integer version, String status) {
		this.brand = brand;
		this.cid = cid;
		this.matnr = matnr;
		this.tmallPrice = tmallPrice;
		this.sapprice = sapprice;
		this.version = version;
		this.status = status;
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

	@Column(name = "BRAND", length = 20)
	public String getBrand() {
		return this.brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	@Column(name = "CID", length = 50)
	public String getCid() {
		return this.cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	@Column(name = "MATNR", length = 50)
	public String getMatnr() {
		return this.matnr;
	}

	public void setMatnr(String matnr) {
		this.matnr = matnr;
	}

	@Column(name = "TMALL_PRICE", precision = 22, scale = 0)
	public Double getTmallPrice() {
		return this.tmallPrice;
	}

	public void setTmallPrice(Double tmallPrice) {
		this.tmallPrice = tmallPrice;
	}

	@Column(name = "SAPPRICE", precision = 22, scale = 0)
	public Double getSapprice() {
		return this.sapprice;
	}

	public void setSapprice(Double sapprice) {
		this.sapprice = sapprice;
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