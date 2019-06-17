package com.arvato.cc.model;

import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

/**
 * JstOrder entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "jst_order")
public class JstOrder implements java.io.Serializable {

	// Fields

	private Integer jstOrderSysId;
	private JstTrade jstTrade;
	private String itemMealName;
	private String picPath;
	private String refundStatus;
	private String outerIid;
	private String snapshotUrl;
	private Timestamp timeoutActionTime;
	private String buyerRate;
	private String sellerRate;
	private String cid;
	private String subOrderTaxFee;
	private String subOrderTaxRate;
	private String oid;
	private String status;
	private String title;
	private Double price;
	private String numIid;
	private String itemMealId;
	private String skuId;
	private Integer num;
	private String outerSkuId;
	private String orderFrom;
	private Double totalFee;
	private Double payment;
	private Double discountFee;
	private Double adjustFee;
	private String skuPropertiesName;
	private String refundId;
	private String isOversold;
	private String isServiceOrder;
	private Timestamp endTime;
	private Timestamp consignTime;
	private String shippingType;
	private String bindOid;
	private String logisticsCompany;
	private String invoiceNo;
	private String isDaixiao;
	private Double divideOrderFee;
	private Double partMjzDiscount;
	private String ticketOuterId;
	private String ticketExpdateKey;
	private String storeCode;
	private String isWww;
	private String tmserSpuCode;
    private String matnr;

	// Constructors

	/** default constructor */
	public JstOrder() {
	}

	/** minimal constructor */
	public JstOrder(JstTrade jstTrade) {
		this.jstTrade = jstTrade;
	}

	/** full constructor */
	public JstOrder(JstTrade jstTrade, String itemMealName, String picPath,
			String refundStatus, String outerIid, String snapshotUrl,
            Timestamp timeoutActionTime, String buyerRate, String sellerRate,
			String cid, String subOrderTaxFee, String subOrderTaxRate,
			String oid, String status, String title, Double price,
			String numIid, String itemMealId, String skuId, Integer num,
			String outerSkuId, String orderFrom, Double totalFee,
			Double payment, Double discountFee, Double adjustFee,
			String skuPropertiesName, String refundId, String isOversold,
			String isServiceOrder, Timestamp endTime, Timestamp consignTime,
			String shippingType, String bindOid, String logisticsCompany,
			String invoiceNo, String isDaixiao, Double divideOrderFee,
			Double partMjzDiscount, String ticketOuterId,
			String ticketExpdateKey, String storeCode, String isWww,
			String tmserSpuCode) {
		this.jstTrade = jstTrade;
		this.itemMealName = itemMealName;
		this.picPath = picPath;
		this.refundStatus = refundStatus;
		this.outerIid = outerIid;
		this.snapshotUrl = snapshotUrl;
		this.timeoutActionTime = timeoutActionTime;
		this.buyerRate = buyerRate;
		this.sellerRate = sellerRate;
		this.cid = cid;
		this.subOrderTaxFee = subOrderTaxFee;
		this.subOrderTaxRate = subOrderTaxRate;
		this.oid = oid;
		this.status = status;
		this.title = title;
		this.price = price;
		this.numIid = numIid;
		this.itemMealId = itemMealId;
		this.skuId = skuId;
		this.num = num;
		this.outerSkuId = outerSkuId;
		this.orderFrom = orderFrom;
		this.totalFee = totalFee;
		this.payment = payment;
		this.discountFee = discountFee;
		this.adjustFee = adjustFee;
		this.skuPropertiesName = skuPropertiesName;
		this.refundId = refundId;
		this.isOversold = isOversold;
		this.isServiceOrder = isServiceOrder;
		this.endTime = endTime;
		this.consignTime = consignTime;
		this.shippingType = shippingType;
		this.bindOid = bindOid;
		this.logisticsCompany = logisticsCompany;
		this.invoiceNo = invoiceNo;
		this.isDaixiao = isDaixiao;
		this.divideOrderFee = divideOrderFee;
		this.partMjzDiscount = partMjzDiscount;
		this.ticketOuterId = ticketOuterId;
		this.ticketExpdateKey = ticketExpdateKey;
		this.storeCode = storeCode;
		this.isWww = isWww;
		this.tmserSpuCode = tmserSpuCode;
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
	public JstTrade getJstTrade() {
		return this.jstTrade;
	}

	public void setJstTrade(JstTrade jstTrade) {
		this.jstTrade = jstTrade;
	}

	@Column(name = "ITEM_MEAL_NAME", length = 50)
	public String getItemMealName() {
		return this.itemMealName;
	}

	public void setItemMealName(String itemMealName) {
		this.itemMealName = itemMealName;
	}

	@Column(name = "PIC_PATH", length = 100)
	public String getPicPath() {
		return this.picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

	@Column(name = "REFUND_STATUS", length = 100)
	public String getRefundStatus() {
		return this.refundStatus;
	}

	public void setRefundStatus(String refundStatus) {
		this.refundStatus = refundStatus;
	}

	@Column(name = "OUTER_IID", length = 100)
	public String getOuterIid() {
		return this.outerIid;
	}

	public void setOuterIid(String outerIid) {
		this.outerIid = outerIid;
	}

	@Column(name = "SNAPSHOT_URL", length = 100)
	public String getSnapshotUrl() {
		return this.snapshotUrl;
	}

	public void setSnapshotUrl(String snapshotUrl) {
		this.snapshotUrl = snapshotUrl;
	}

	@Column(name = "TIMEOUT_ACTION_TIME", length = 19)
	public Timestamp getTimeoutActionTime() {
		return this.timeoutActionTime;
	}

	public void setTimeoutActionTime(Timestamp timeoutActionTime) {
		this.timeoutActionTime = timeoutActionTime;
	}

	@Column(name = "BUYER_RATE", length = 50)
	public String getBuyerRate() {
		return this.buyerRate;
	}

	public void setBuyerRate(String buyerRate) {
		this.buyerRate = buyerRate;
	}

	@Column(name = "SELLER_RATE", length = 50)
	public String getSellerRate() {
		return this.sellerRate;
	}

	public void setSellerRate(String sellerRate) {
		this.sellerRate = sellerRate;
	}

	@Column(name = "CID", length = 100)
	public String getCid() {
		return this.cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	@Column(name = "SUB_ORDER_TAX_FEE", length = 100)
	public String getSubOrderTaxFee() {
		return this.subOrderTaxFee;
	}

	public void setSubOrderTaxFee(String subOrderTaxFee) {
		this.subOrderTaxFee = subOrderTaxFee;
	}

	@Column(name = "SUB_ORDER_TAX_RATE", length = 100)
	public String getSubOrderTaxRate() {
		return this.subOrderTaxRate;
	}

	public void setSubOrderTaxRate(String subOrderTaxRate) {
		this.subOrderTaxRate = subOrderTaxRate;
	}

	@Column(name = "OID", length = 100)
	public String getOid() {
		return this.oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	@Column(name = "STATUS", length = 100)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "TITLE", length = 100)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "PRICE", precision = 22, scale = 0)
	public Double getPrice() {
		return this.price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Column(name = "NUM_IID", length = 100)
	public String getNumIid() {
		return this.numIid;
	}

	public void setNumIid(String numIid) {
		this.numIid = numIid;
	}

	@Column(name = "ITEM_MEAL_ID", length = 100)
	public String getItemMealId() {
		return this.itemMealId;
	}

	public void setItemMealId(String itemMealId) {
		this.itemMealId = itemMealId;
	}

	@Column(name = "SKU_ID", length = 100)
	public String getSkuId() {
		return this.skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

	@Column(name = "NUM")
	public Integer getNum() {
		return this.num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	@Column(name = "OUTER_SKU_ID", length = 100)
	public String getOuterSkuId() {
		return this.outerSkuId;
	}

	public void setOuterSkuId(String outerSkuId) {
		this.outerSkuId = outerSkuId;
	}

	@Column(name = "ORDER_FROM", length = 100)
	public String getOrderFrom() {
		return this.orderFrom;
	}

	public void setOrderFrom(String orderFrom) {
		this.orderFrom = orderFrom;
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

	@Column(name = "ADJUST_FEE", precision = 22, scale = 0)
	public Double getAdjustFee() {
		return this.adjustFee;
	}

	public void setAdjustFee(Double adjustFee) {
		this.adjustFee = adjustFee;
	}

	@Column(name = "SKU_PROPERTIES_NAME", length = 100)
	public String getSkuPropertiesName() {
		return this.skuPropertiesName;
	}

	public void setSkuPropertiesName(String skuPropertiesName) {
		this.skuPropertiesName = skuPropertiesName;
	}

	@Column(name = "REFUND_ID", length = 100)
	public String getRefundId() {
		return this.refundId;
	}

	public void setRefundId(String refundId) {
		this.refundId = refundId;
	}

	@Column(name = "IS_OVERSOLD", length = 10)
	public String getIsOversold() {
		return this.isOversold;
	}

	public void setIsOversold(String isOversold) {
		this.isOversold = isOversold;
	}

	@Column(name = "IS_SERVICE_ORDER", length = 10)
	public String getIsServiceOrder() {
		return this.isServiceOrder;
	}

	public void setIsServiceOrder(String isServiceOrder) {
		this.isServiceOrder = isServiceOrder;
	}

	@Column(name = "END_TIME", length = 19)
	public Timestamp getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	@Column(name = "CONSIGN_TIME", length = 19)
	public Timestamp getConsignTime() {
		return this.consignTime;
	}

	public void setConsignTime(Timestamp consignTime) {
		this.consignTime = consignTime;
	}

	@Column(name = "SHIPPING_TYPE", length = 100)
	public String getShippingType() {
		return this.shippingType;
	}

	public void setShippingType(String shippingType) {
		this.shippingType = shippingType;
	}

	@Column(name = "BIND_OID", length = 100)
	public String getBindOid() {
		return this.bindOid;
	}

	public void setBindOid(String bindOid) {
		this.bindOid = bindOid;
	}

	@Column(name = "LOGISTICS_COMPANY", length = 100)
	public String getLogisticsCompany() {
		return this.logisticsCompany;
	}

	public void setLogisticsCompany(String logisticsCompany) {
		this.logisticsCompany = logisticsCompany;
	}

	@Column(name = "INVOICE_NO", length = 100)
	public String getInvoiceNo() {
		return this.invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	@Column(name = "IS_DAIXIAO", length = 100)
	public String getIsDaixiao() {
		return this.isDaixiao;
	}

	public void setIsDaixiao(String isDaixiao) {
		this.isDaixiao = isDaixiao;
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

	@Column(name = "TICKET_OUTER_ID", length = 100)
	public String getTicketOuterId() {
		return this.ticketOuterId;
	}

	public void setTicketOuterId(String ticketOuterId) {
		this.ticketOuterId = ticketOuterId;
	}

	@Column(name = "TICKET_EXPDATE_KEY", length = 100)
	public String getTicketExpdateKey() {
		return this.ticketExpdateKey;
	}

	public void setTicketExpdateKey(String ticketExpdateKey) {
		this.ticketExpdateKey = ticketExpdateKey;
	}

	@Column(name = "STORE_CODE", length = 100)
	public String getStoreCode() {
		return this.storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	@Column(name = "IS_WWW", length = 100)
	public String getIsWww() {
		return this.isWww;
	}

	public void setIsWww(String isWww) {
		this.isWww = isWww;
	}

	@Column(name = "TMSER_SPU_CODE", length = 100)
	public String getTmserSpuCode() {
		return this.tmserSpuCode;
	}

	public void setTmserSpuCode(String tmserSpuCode) {
		this.tmserSpuCode = tmserSpuCode;
	}

    @Column(name = "matnr", length = 100)
    public String getMatnr() {
        return this.matnr;
    }

    public void setMatnr(String matnr) {
        this.matnr = matnr;
    }

}