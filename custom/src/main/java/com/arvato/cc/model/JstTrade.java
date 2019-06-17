package com.arvato.cc.model;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

/**
 * JstTrade entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "jst_trade")
public class JstTrade implements java.io.Serializable {

	// Fields

	private Integer tradeSysId;
	private String tid;
	private String alipayId;
	private String alipayNo;
	private String sellerNick;
	private Double payment;
	private String receiverName;
	private String receiverState;
	private String receiverAddress;
	private String receiverZip;
	private String receiverMobile;
    private String receiverPhone;
	private String receiverCity;
	private String receiverDistrict;
	private String status;
	private Double discountFee;
	private Double totalFee;
	private Timestamp created;
	private Timestamp payTime;
	private Timestamp modified;
	private Timestamp endTime;
	private String sellerMemo;
	private String invoiceName;
	private String buyerNick;
	private String storeId;
	private String pointFee;
	private String shippingType;
	private JstTradeExtend jstTradeExt;
	private Set<JstPromotionDetail> jstPromotionDetails = new HashSet<JstPromotionDetail>(
			0);
	private Set<JstServiceOrder> jstServiceOrders = new HashSet<JstServiceOrder>(
			0);
	private Set<JstLogisticsTag> jstLogisticsTags = new HashSet<JstLogisticsTag>(
			0);
	private Set<JstOrder> jstOrders = new HashSet<JstOrder>(0);

	// Constructors

	/** default constructor */
	public JstTrade() {
	}

	/** minimal constructor */
	public JstTrade(String tid, String storeId) {
		this.tid = tid;
		this.storeId = storeId;
	}

	/** full constructor */
	public JstTrade(String tid, String alipayId, String alipayNo,
			String sellerNick, Double payment, String receiverName,
			String receiverState, String receiverAddress, String receiverZip,
			String receiverMobile, String receiverCity,
			String receiverDistrict, String status, Double discountFee,
			Double totalFee, Timestamp created, Timestamp payTime, Timestamp modified,
			Timestamp endTime, String sellerMemo, String invoiceName,
			String buyerNick, String storeId, String pointFee,
			String shippingType, JstTradeExtend jstTradeExt,
			Set<JstPromotionDetail> jstPromotionDetails,
			Set<JstServiceOrder> jstServiceOrders,
			Set<JstLogisticsTag> jstLogisticsTags, Set<JstOrder> jstOrders) {
		this.tid = tid;
		this.alipayId = alipayId;
		this.alipayNo = alipayNo;
		this.sellerNick = sellerNick;
		this.payment = payment;
		this.receiverName = receiverName;
		this.receiverState = receiverState;
		this.receiverAddress = receiverAddress;
		this.receiverZip = receiverZip;
		this.receiverMobile = receiverMobile;
		this.receiverCity = receiverCity;
		this.receiverDistrict = receiverDistrict;
		this.status = status;
		this.discountFee = discountFee;
		this.totalFee = totalFee;
		this.created = created;
		this.payTime = payTime;
		this.modified = modified;
		this.endTime = endTime;
		this.sellerMemo = sellerMemo;
		this.invoiceName = invoiceName;
		this.buyerNick = buyerNick;
		this.storeId = storeId;
		this.pointFee = pointFee;
		this.shippingType = shippingType;
		this.jstTradeExt = jstTradeExt;
		this.jstPromotionDetails = jstPromotionDetails;
		this.jstServiceOrders = jstServiceOrders;
		this.jstLogisticsTags = jstLogisticsTags;
		this.jstOrders = jstOrders;
	}

	// Property accessors
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TRADE_SYS_ID", unique = true, nullable = false)
	public Integer getTradeSysId() {
		return this.tradeSysId;
	}

	public void setTradeSysId(Integer tradeSysId) {
		this.tradeSysId = tradeSysId;
	}

	@Column(name = "TID", nullable = false, length = 200)
	public String getTid() {
		return this.tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	@Column(name = "ALIPAY_ID", length = 200)
	public String getAlipayId() {
		return this.alipayId;
	}

	public void setAlipayId(String alipayId) {
		this.alipayId = alipayId;
	}

	@Column(name = "ALIPAY_NO", length = 200)
	public String getAlipayNo() {
		return this.alipayNo;
	}

	public void setAlipayNo(String alipayNo) {
		this.alipayNo = alipayNo;
	}

	@Column(name = "SELLER_NICK", length = 200)
	public String getSellerNick() {
		return this.sellerNick;
	}

	public void setSellerNick(String sellerNick) {
		this.sellerNick = sellerNick;
	}

	@Column(name = "PAYMENT", precision = 22, scale = 0)
	public Double getPayment() {
		return this.payment;
	}

	public void setPayment(Double payment) {
		this.payment = payment;
	}

	@Column(name = "RECEIVER_NAME", length = 200)
	public String getReceiverName() {
		return this.receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	@Column(name = "RECEIVER_STATE", length = 200)
	public String getReceiverState() {
		return this.receiverState;
	}

	public void setReceiverState(String receiverState) {
		this.receiverState = receiverState;
	}

	@Column(name = "RECEIVER_ADDRESS", length = 200)
	public String getReceiverAddress() {
		return this.receiverAddress;
	}

	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}

	@Column(name = "RECEIVER_ZIP", length = 200)
	public String getReceiverZip() {
		return this.receiverZip;
	}

	public void setReceiverZip(String receiverZip) {
		this.receiverZip = receiverZip;
	}

	@Column(name = "RECEIVER_MOBILE", length = 200)
	public String getReceiverMobile() {
		return this.receiverMobile;
	}

	public void setReceiverMobile(String receiverMobile) {
		this.receiverMobile = receiverMobile;
	}

	@Column(name = "RECEIVER_CITY", length = 200)
	public String getReceiverCity() {
		return this.receiverCity;
	}

	public void setReceiverCity(String receiverCity) {
		this.receiverCity = receiverCity;
	}

	@Column(name = "RECEIVER_DISTRICT", length = 200)
	public String getReceiverDistrict() {
		return this.receiverDistrict;
	}

	public void setReceiverDistrict(String receiverDistrict) {
		this.receiverDistrict = receiverDistrict;
	}

	@Column(name = "STATUS", length = 200)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "DISCOUNT_FEE", precision = 22, scale = 0)
	public Double getDiscountFee() {
		return this.discountFee;
	}

	public void setDiscountFee(Double discountFee) {
		this.discountFee = discountFee;
	}

	@Column(name = "TOTAL_FEE", precision = 22, scale = 0)
	public Double getTotalFee() {
		return this.totalFee;
	}

	public void setTotalFee(Double totalFee) {
		this.totalFee = totalFee;
	}

	@Column(name = "CREATED", length = 19)
	public Timestamp getCreated() {
		return this.created;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}


	@Column(name = "PAY_TIME", length = 19)
	public Timestamp getPayTime() {
		return this.payTime;
	}

	public void setPayTime(Timestamp payTime) {
		this.payTime = payTime;
	}

	@Column(name = "MODIFIED", length = 19)
	public Timestamp getModified() {
		return this.modified;
	}

	public void setModified(Timestamp modified) {
		this.modified = modified;
	}

	@Column(name = "END_TIME", length = 19)
	public Timestamp getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	@Column(name = "SELLER_MEMO", length = 1000)
	public String getSellerMemo() {
		return this.sellerMemo;
	}

	public void setSellerMemo(String sellerMemo) {
		this.sellerMemo = sellerMemo;
	}

	@Column(name = "INVOICE_NAME", length = 200)
	public String getInvoiceName() {
		return this.invoiceName;
	}

	public void setInvoiceName(String invoiceName) {
		this.invoiceName = invoiceName;
	}

	@Column(name = "BUYER_NICK", length = 200)
	public String getBuyerNick() {
		return this.buyerNick;
	}

	public void setBuyerNick(String buyerNick) {
		this.buyerNick = buyerNick;
	}

	@Column(name = "STORE_ID", nullable = false, length = 100)
	public String getStoreId() {
		return this.storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	@Column(name = "point_fee", length = 200)
	public String getPointFee() {
		return this.pointFee;
	}

	public void setPointFee(String pointFee) {
		this.pointFee = pointFee;
	}

	@Column(name = "SHIPPING_TYPE", length = 200)
	public String getShippingType() {
		return this.shippingType;
	}

	public void setShippingType(String shippingType) {
		this.shippingType = shippingType;
	}

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "jstTrade")
	public JstTradeExtend getJstTradeExt() {
		return this.jstTradeExt;
	}

	public void setJstTradeExt(JstTradeExtend jstTradeExt) {
		this.jstTradeExt = jstTradeExt;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "jstTrade")
	public Set<JstPromotionDetail> getJstPromotionDetails() {
		return this.jstPromotionDetails;
	}

	public void setJstPromotionDetails(
			Set<JstPromotionDetail> jstPromotionDetails) {
		this.jstPromotionDetails = jstPromotionDetails;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "jstTrade")
	public Set<JstServiceOrder> getJstServiceOrders() {
		return this.jstServiceOrders;
	}

	public void setJstServiceOrders(Set<JstServiceOrder> jstServiceOrders) {
		this.jstServiceOrders = jstServiceOrders;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "jstTrade")
	public Set<JstLogisticsTag> getJstLogisticsTags() {
		return this.jstLogisticsTags;
	}

	public void setJstLogisticsTags(Set<JstLogisticsTag> jstLogisticsTags) {
		this.jstLogisticsTags = jstLogisticsTags;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "jstTrade")
	public Set<JstOrder> getJstOrders() {
		return this.jstOrders;
	}

	public void setJstOrders(Set<JstOrder> jstOrders) {
		this.jstOrders = jstOrders;
	}

    @Column(name = "RECEIVER_PHONE", length = 200)
    public String getReceiverPhone() {
        return this.receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }
}