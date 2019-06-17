package com.arvato.cc.model;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

/**
 * JstServiceOrder entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "jst_service_order")
public class JstServiceOrder implements java.io.Serializable {

	// Fields

	private Integer serviceOrderId;
	private JstTrade jstTrade;
	private String itemOid;
	private String serviceId;
	private String serviceDetailUrl;
	private Integer num;
	private Double price;
	private Double payment;
	private String title;
	private Double totalFee;
	private String buyerNick;
	private String sellerNick;
	private String picPath;

	// Constructors

	/** default constructor */
	public JstServiceOrder() {
	}

	/** minimal constructor */
	public JstServiceOrder(JstTrade jstTrade) {
		this.jstTrade = jstTrade;
	}

	/** full constructor */
	public JstServiceOrder(JstTrade jstTrade, String itemOid, String serviceId,
			String serviceDetailUrl, Integer num, Double price, Double payment,
			String title, Double totalFee, String buyerNick, String sellerNick,
			String picPath) {
		this.jstTrade = jstTrade;
		this.itemOid = itemOid;
		this.serviceId = serviceId;
		this.serviceDetailUrl = serviceDetailUrl;
		this.num = num;
		this.price = price;
		this.payment = payment;
		this.title = title;
		this.totalFee = totalFee;
		this.buyerNick = buyerNick;
		this.sellerNick = sellerNick;
		this.picPath = picPath;
	}

	// Property accessors
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SERVICE_ORDER_ID", unique = true, nullable = false)
	public Integer getServiceOrderId() {
		return this.serviceOrderId;
	}

	public void setServiceOrderId(Integer serviceOrderId) {
		this.serviceOrderId = serviceOrderId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TRADE_SYS_ID", nullable = false)
	public JstTrade getJstTrade() {
		return this.jstTrade;
	}

	public void setJstTrade(JstTrade jstTrade) {
		this.jstTrade = jstTrade;
	}

	@Column(name = "ITEM_OID", length = 100)
	public String getItemOid() {
		return this.itemOid;
	}

	public void setItemOid(String itemOid) {
		this.itemOid = itemOid;
	}

	@Column(name = "SERVICE_ID", length = 100)
	public String getServiceId() {
		return this.serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	@Column(name = "SERVICE_DETAIL_URL", length = 100)
	public String getServiceDetailUrl() {
		return this.serviceDetailUrl;
	}

	public void setServiceDetailUrl(String serviceDetailUrl) {
		this.serviceDetailUrl = serviceDetailUrl;
	}

	@Column(name = "NUM")
	public Integer getNum() {
		return this.num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	@Column(name = "PRICE", precision = 22, scale = 0)
	public Double getPrice() {
		return this.price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Column(name = "PAYMENT", precision = 22, scale = 0)
	public Double getPayment() {
		return this.payment;
	}

	public void setPayment(Double payment) {
		this.payment = payment;
	}

	@Column(name = "TITLE", length = 100)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "TOTAL_FEE", precision = 22, scale = 0)
	public Double getTotalFee() {
		return this.totalFee;
	}

	public void setTotalFee(Double totalFee) {
		this.totalFee = totalFee;
	}

	@Column(name = "BUYER_NICK", length = 100)
	public String getBuyerNick() {
		return this.buyerNick;
	}

	public void setBuyerNick(String buyerNick) {
		this.buyerNick = buyerNick;
	}

	@Column(name = "SELLER_NICK", length = 100)
	public String getSellerNick() {
		return this.sellerNick;
	}

	public void setSellerNick(String sellerNick) {
		this.sellerNick = sellerNick;
	}

	@Column(name = "PIC_PATH", length = 100)
	public String getPicPath() {
		return this.picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

}