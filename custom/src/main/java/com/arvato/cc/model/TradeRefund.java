package com.arvato.cc.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * TradeRefund entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "trade_refund")
public class TradeRefund implements java.io.Serializable {

	// Fields

	private Integer id;
	private String shippingType;
	private String csStatus;
	private String advanceStatus;
	private Double splitTaobaoFee;
	private Double splitSellerFee;
	private String refundId;
	private String tid;
	private String oid;
	private String alipayNo;
	private Double totalFee;
	private String sellerNick;
	private String buyerNick;
	private Timestamp created;
	private Timestamp modified;
	private String orderStatus;
	private String status;
	private String goodStatus;
	private String hasGoodReturn;
	private Double refundFee;
	private Double payment;
	private String reason;
	private String description;
	private String title;
	private Double price;
	private Integer num;
	private Timestamp goodReturnTime;
	private String companyName;
	private String sid;
	private String address;
	private String numIid;
	private String refundPhase;
	private String refundVersion;
	private String sku;
	private String attribute;
	private String outerId;
	private String operationContraint;
	private String storeId;
	private Timestamp recordCreate;
	private Integer batchNo;

	// Constructors

	/** default constructor */
	public TradeRefund() {
	}

	/** minimal constructor */
	public TradeRefund(String shippingType, String csStatus,
			String advanceStatus, Double splitTaobaoFee, Double splitSellerFee,
			String refundId, String tid, String oid, String alipayNo,
			Double totalFee, String orderStatus, String status,
			String goodStatus, String hasGoodReturn, Double refundFee,
			Double payment, String reason, String description, String title,
			Double price, Integer num, Timestamp goodReturnTime, String companyName,
			String sid, String address, String numIid, String refundPhase,
			String refundVersion, String sku, String attribute, String outerId,
			String operationContraint, String storeId) {
		this.shippingType = shippingType;
		this.csStatus = csStatus;
		this.advanceStatus = advanceStatus;
		this.splitTaobaoFee = splitTaobaoFee;
		this.splitSellerFee = splitSellerFee;
		this.refundId = refundId;
		this.tid = tid;
		this.oid = oid;
		this.alipayNo = alipayNo;
		this.totalFee = totalFee;
		this.orderStatus = orderStatus;
		this.status = status;
		this.goodStatus = goodStatus;
		this.hasGoodReturn = hasGoodReturn;
		this.refundFee = refundFee;
		this.payment = payment;
		this.reason = reason;
		this.description = description;
		this.title = title;
		this.price = price;
		this.num = num;
		this.goodReturnTime = goodReturnTime;
		this.companyName = companyName;
		this.sid = sid;
		this.address = address;
		this.numIid = numIid;
		this.refundPhase = refundPhase;
		this.refundVersion = refundVersion;
		this.sku = sku;
		this.attribute = attribute;
		this.outerId = outerId;
		this.operationContraint = operationContraint;
		this.storeId = storeId;
	}

	/** full constructor */
	public TradeRefund(String shippingType, String csStatus,
			String advanceStatus, Double splitTaobaoFee, Double splitSellerFee,
			String refundId, String tid, String oid, String alipayNo,
			Double totalFee, String sellerNick, String buyerNick, Timestamp created,
            Timestamp modified, String orderStatus, String status,
			String goodStatus, String hasGoodReturn, Double refundFee,
			Double payment, String reason, String description, String title,
			Double price, Integer num, Timestamp goodReturnTime, String companyName,
			String sid, String address, String numIid, String refundPhase,
			String refundVersion, String sku, String attribute, String outerId,
			String operationContraint, String storeId, Timestamp recordCreate,
			Integer batchNo) {
		this.shippingType = shippingType;
		this.csStatus = csStatus;
		this.advanceStatus = advanceStatus;
		this.splitTaobaoFee = splitTaobaoFee;
		this.splitSellerFee = splitSellerFee;
		this.refundId = refundId;
		this.tid = tid;
		this.oid = oid;
		this.alipayNo = alipayNo;
		this.totalFee = totalFee;
		this.sellerNick = sellerNick;
		this.buyerNick = buyerNick;
		this.created = created;
		this.modified = modified;
		this.orderStatus = orderStatus;
		this.status = status;
		this.goodStatus = goodStatus;
		this.hasGoodReturn = hasGoodReturn;
		this.refundFee = refundFee;
		this.payment = payment;
		this.reason = reason;
		this.description = description;
		this.title = title;
		this.price = price;
		this.num = num;
		this.goodReturnTime = goodReturnTime;
		this.companyName = companyName;
		this.sid = sid;
		this.address = address;
		this.numIid = numIid;
		this.refundPhase = refundPhase;
		this.refundVersion = refundVersion;
		this.sku = sku;
		this.attribute = attribute;
		this.outerId = outerId;
		this.operationContraint = operationContraint;
		this.storeId = storeId;
		this.recordCreate = recordCreate;
		this.batchNo = batchNo;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "shipping_type",  length = 100)
	public String getShippingType() {
		return this.shippingType;
	}

	public void setShippingType(String shippingType) {
		this.shippingType = shippingType;
	}

	@Column(name = "cs_status",  length = 10)
	public String getCsStatus() {
		return this.csStatus;
	}

	public void setCsStatus(String csStatus) {
		this.csStatus = csStatus;
	}

	@Column(name = "advance_status",  length = 10)
	public String getAdvanceStatus() {
		return this.advanceStatus;
	}

	public void setAdvanceStatus(String advanceStatus) {
		this.advanceStatus = advanceStatus;
	}

	@Column(name = "split_taobao_fee", precision = 22, scale = 0)
	public Double getSplitTaobaoFee() {
		return this.splitTaobaoFee;
	}

	public void setSplitTaobaoFee(Double splitTaobaoFee) {
		this.splitTaobaoFee = splitTaobaoFee;
	}

	@Column(name = "split_seller_fee", precision = 22, scale = 0)
	public Double getSplitSellerFee() {
		return this.splitSellerFee;
	}

	public void setSplitSellerFee(Double splitSellerFee) {
		this.splitSellerFee = splitSellerFee;
	}

	@Column(name = "refund_id",  length = 100)
	public String getRefundId() {
		return this.refundId;
	}

	public void setRefundId(String refundId) {
		this.refundId = refundId;
	}

	@Column(name = "tid",  length = 100)
	public String getTid() {
		return this.tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	@Column(name = "oid",  length = 100)
	public String getOid() {
		return this.oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	@Column(name = "alipay_no",  length = 100)
	public String getAlipayNo() {
		return this.alipayNo;
	}

	public void setAlipayNo(String alipayNo) {
		this.alipayNo = alipayNo;
	}

	@Column(name = "total_fee",  precision = 22, scale = 0)
	public Double getTotalFee() {
		return this.totalFee;
	}

	public void setTotalFee(Double totalFee) {
		this.totalFee = totalFee;
	}

	@Column(name = "seller_nick", length = 100)
	public String getSellerNick() {
		return this.sellerNick;
	}

	public void setSellerNick(String sellerNick) {
		this.sellerNick = sellerNick;
	}

	@Column(name = "buyer_nick", length = 100)
	public String getBuyerNick() {
		return this.buyerNick;
	}

	public void setBuyerNick(String buyerNick) {
		this.buyerNick = buyerNick;
	}

	@Column(name = "created", length = 19)
	public Timestamp getCreated() {
		return this.created;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}

	@Column(name = "modified", length = 19)
	public Timestamp getModified() {
		return this.modified;
	}

	public void setModified(Timestamp modified) {
		this.modified = modified;
	}

	@Column(name = "order_status",  length = 100)
	public String getOrderStatus() {
		return this.orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	@Column(name = "status",  length = 100)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "good_status",  length = 100)
	public String getGoodStatus() {
		return this.goodStatus;
	}

	public void setGoodStatus(String goodStatus) {
		this.goodStatus = goodStatus;
	}

	@Column(name = "has_good_return",  length = 100)
	public String getHasGoodReturn() {
		return this.hasGoodReturn;
	}

	public void setHasGoodReturn(String hasGoodReturn) {
		this.hasGoodReturn = hasGoodReturn;
	}

	@Column(name = "refund_fee",  precision = 22, scale = 0)
	public Double getRefundFee() {
		return this.refundFee;
	}

	public void setRefundFee(Double refundFee) {
		this.refundFee = refundFee;
	}

	@Column(name = "payment",  precision = 22, scale = 0)
	public Double getPayment() {
		return this.payment;
	}

	public void setPayment(Double payment) {
		this.payment = payment;
	}

	@Column(name = "reason",  length = 200)
	public String getReason() {
		return this.reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@Column(name = "description",  length = 1000)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "title",  length = 100)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "price",  precision = 22, scale = 0)
	public Double getPrice() {
		return this.price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Column(name = "num", nullable = false)
	public Integer getNum() {
		return this.num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	@Column(name = "good_return_time",  length = 19)
	public Timestamp getGoodReturnTime() {
		return this.goodReturnTime;
	}

	public void setGoodReturnTime(Timestamp goodReturnTime) {
		this.goodReturnTime = goodReturnTime;
	}

	@Column(name = "company_name",  length = 100)
	public String getCompanyName() {
		return this.companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	@Column(name = "sid",  length = 100)
	public String getSid() {
		return this.sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	@Column(name = "address",  length = 200)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "num_iid",  length = 100)
	public String getNumIid() {
		return this.numIid;
	}

	public void setNumIid(String numIid) {
		this.numIid = numIid;
	}

	@Column(name = "refund_phase",  length = 100)
	public String getRefundPhase() {
		return this.refundPhase;
	}

	public void setRefundPhase(String refundPhase) {
		this.refundPhase = refundPhase;
	}

	@Column(name = "refund_version",  length = 100)
	public String getRefundVersion() {
		return this.refundVersion;
	}

	public void setRefundVersion(String refundVersion) {
		this.refundVersion = refundVersion;
	}

	@Column(name = "sku",  length = 100)
	public String getSku() {
		return this.sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	@Column(name = "attribute",  length = 1000)
	public String getAttribute() {
		return this.attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	@Column(name = "outer_id",  length = 100)
	public String getOuterId() {
		return this.outerId;
	}

	public void setOuterId(String outerId) {
		this.outerId = outerId;
	}

	@Column(name = "operation_contraint",  length = 100)
	public String getOperationContraint() {
		return this.operationContraint;
	}

	public void setOperationContraint(String operationContraint) {
		this.operationContraint = operationContraint;
	}

	@Column(name = "store_id",  length = 100)
	public String getStoreId() {
		return this.storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	@Column(name = "record_create", length = 19)
	public Timestamp getRecordCreate() {
		return this.recordCreate;
	}

	public void setRecordCreate(Timestamp recordCreate) {
		this.recordCreate = recordCreate;
	}

	@Column(name = "batch_no")
	public Integer getBatchNo() {
		return this.batchNo;
	}

	public void setBatchNo(Integer batchNo) {
		this.batchNo = batchNo;
	}

}