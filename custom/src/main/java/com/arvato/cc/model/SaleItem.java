package com.arvato.cc.model;

import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

/**
 * SaleItem entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "sale_item")
public class SaleItem implements java.io.Serializable {

	// Fields

	private Integer saleItemSysId;
	private String tid;
	private String storeId;
	private String status;
	private String cid;
	private String sku1;
	private String skus;
	private Timestamp createTime;
	private Double payment;
	private Double partMjzDiscount;
	private Double sapPrice;
	private String storeCode;

	// Constructors

	/** default constructor */
	public SaleItem() {
	}

	/** minimal constructor */
	public SaleItem(String tid) {
		this.tid = tid;
	}

	/** full constructor */
	public SaleItem(String tid, String storeId, String status, String cid,
			String sku1, String skus, Timestamp createTime, Double payment,
			Double partMjzDiscount, Double sapPrice, String storeCode) {
		this.tid = tid;
		this.storeId = storeId;
		this.status = status;
		this.cid = cid;
		this.sku1 = sku1;
		this.skus = skus;
		this.createTime = createTime;
		this.payment = payment;
		this.partMjzDiscount = partMjzDiscount;
		this.sapPrice = sapPrice;
		this.storeCode = storeCode;
	}

	// Property accessors
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SALE_ITEM_SYS_ID", unique = true, nullable = false)
	public Integer getSaleItemSysId() {
		return this.saleItemSysId;
	}

	public void setSaleItemSysId(Integer saleItemSysId) {
		this.saleItemSysId = saleItemSysId;
	}

	@Column(name = "TID", nullable = false, length = 100)
	public String getTid() {
		return this.tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	@Column(name = "STORE_ID", length = 100)
	public String getStoreId() {
		return this.storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	@Column(name = "STATUS", length = 100)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "CID", length = 100)
	public String getCid() {
		return this.cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	@Column(name = "SKU1", length = 100)
	public String getSku1() {
		return this.sku1;
	}

	public void setSku1(String sku1) {
		this.sku1 = sku1;
	}

	@Column(name = "SKUS", length = 100)
	public String getSkus() {
		return this.skus;
	}

	public void setSkus(String skus) {
		this.skus = skus;
	}

	@Column(name = "CREATE_TIME", length = 19)
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name = "PAYMENT", precision = 22, scale = 0)
	public Double getPayment() {
		return this.payment;
	}

	public void setPayment(Double payment) {
		this.payment = payment;
	}

	@Column(name = "PART_MJZ_DISCOUNT", precision = 22, scale = 0)
	public Double getPartMjzDiscount() {
		return this.partMjzDiscount;
	}

	public void setPartMjzDiscount(Double partMjzDiscount) {
		this.partMjzDiscount = partMjzDiscount;
	}

	@Column(name = "SAP_PRICE", precision = 22, scale = 0)
	public Double getSapPrice() {
		return this.sapPrice;
	}

	public void setSapPrice(Double sapPrice) {
		this.sapPrice = sapPrice;
	}

	@Column(name = "STORE_CODE", length = 100)
	public String getStoreCode() {
		return this.storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

}