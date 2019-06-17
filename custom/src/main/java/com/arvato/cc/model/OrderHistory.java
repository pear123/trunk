package com.arvato.cc.model;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

/**
 * OrderHistory entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "order_history")
public class OrderHistory implements java.io.Serializable {

	// Fields

	private Integer jstOrderSysId;
	private TradeHistory tradeHistory;
	private Double price;
	private Integer num;
	private Double totalFee;
	private Double payment;
	private Double discountFee;
	private Double divideOrderFee;
	private Double partMjzDiscount;
	private String storeCode;
	private String matrn;

	// Constructors

	/** default constructor */
	public OrderHistory() {
	}

	/** minimal constructor */
	public OrderHistory(TradeHistory tradeHistory) {
		this.tradeHistory = tradeHistory;
	}

	/** full constructor */
	public OrderHistory(TradeHistory tradeHistory, Double price, Integer num,
			Double totalFee, Double payment, Double discountFee,
			Double divideOrderFee, Double partMjzDiscount, String storeCode,
			String matrn) {
		this.tradeHistory = tradeHistory;
		this.price = price;
		this.num = num;
		this.totalFee = totalFee;
		this.payment = payment;
		this.discountFee = discountFee;
		this.divideOrderFee = divideOrderFee;
		this.partMjzDiscount = partMjzDiscount;
		this.storeCode = storeCode;
		this.matrn = matrn;
	}

	// Property accessors
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "JST_ORDER_SYS_ID", unique = true, nullable = false)
	public Integer getJstOrderSysId() {
		return this.jstOrderSysId;
	}

	public void setJstOrderSysId(Integer jstOrderSysId) {
		this.jstOrderSysId = jstOrderSysId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TRADE_SYS_ID", nullable = false)
	public TradeHistory getTradeHistory() {
		return this.tradeHistory;
	}

	public void setTradeHistory(TradeHistory tradeHistory) {
		this.tradeHistory = tradeHistory;
	}

	@Column(name = "PRICE", precision = 22, scale = 0)
	public Double getPrice() {
		return this.price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Column(name = "NUM")
	public Integer getNum() {
		return this.num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	@Column(name = "TOTAL_FEE", precision = 22, scale = 0)
	public Double getTotalFee() {
		return this.totalFee;
	}

	public void setTotalFee(Double totalFee) {
		this.totalFee = totalFee;
	}

	@Column(name = "PAYMENT", precision = 22, scale = 0)
	public Double getPayment() {
		return this.payment;
	}

	public void setPayment(Double payment) {
		this.payment = payment;
	}

	@Column(name = "DISCOUNT_FEE", precision = 22, scale = 0)
	public Double getDiscountFee() {
		return this.discountFee;
	}

	public void setDiscountFee(Double discountFee) {
		this.discountFee = discountFee;
	}

	@Column(name = "DIVIDE_ORDER_FEE", precision = 22, scale = 0)
	public Double getDivideOrderFee() {
		return this.divideOrderFee;
	}

	public void setDivideOrderFee(Double divideOrderFee) {
		this.divideOrderFee = divideOrderFee;
	}

	@Column(name = "PART_MJZ_DISCOUNT", precision = 22, scale = 0)
	public Double getPartMjzDiscount() {
		return this.partMjzDiscount;
	}

	public void setPartMjzDiscount(Double partMjzDiscount) {
		this.partMjzDiscount = partMjzDiscount;
	}

	@Column(name = "STORE_CODE", length = 100)
	public String getStoreCode() {
		return this.storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	@Column(name = "matrn", length = 100)
	public String getMatrn() {
		return this.matrn;
	}

	public void setMatrn(String matrn) {
		this.matrn = matrn;
	}

}