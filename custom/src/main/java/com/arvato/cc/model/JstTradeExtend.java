package com.arvato.cc.model;

import java.util.Date;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

/**
 * JstTradeExtend entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "jst_trade_extend")
public class JstTradeExtend implements java.io.Serializable {

	// Fields

	private Integer tradeExtSysId;
	private JstTrade jstTrade;
	private String alipayPoint;
	private String availableConfirmFee;
	private String buyerAlipayNo;
	private String buyerArea;
	private String buyerEmail;
	private String buyerObtainPointFee;
	private String codFee;
	private String codStatus;
	private String commissionFee;
	private String is3d;
	private String isBrandSale;
	private String isForceWlb;
	private String isLgtype;
	private String isPartConsign;
	private String isWt;
	private String pccAf;
	private String realPointFee;
	private String receivedPayment;
	private String sellerAlipayNo;
	private String sellerCanRate;
	private String sellerCodFee;
	private String sellerEmail;
	private String sellerMobile;
	private String sellerName;
	private String sellerPhone;
	private String snapshotUrl;
	private String picPath;
	private String sellerRate;
	private Double postFee;
	private Date consignTime;
	private String estConTime;
	private String invoiceKind;
	private String receiverCountry;
	private String receiverTown;
	private String orderTaxFee;
	private Integer num;
	private String numIid;
	private String title;
	private String type;
	private Double price;
	private String hasPostFee;
	private String buyerMessage;
	private String buyerMemo;
	private Integer buyerFlag;
	private String sellerFlag;
	private String invoiceType;
	private Double creditCardFee;
	private String markDesc;
	private Double buyerCodFee;
	private Double adjustFee;
	private String tradeFrom;
	private String buyerRate;

	// Constructors

	/** default constructor */
	public JstTradeExtend() {
	}

	/** minimal constructor */
	public JstTradeExtend(JstTrade jstTrade) {
		this.jstTrade = jstTrade;
	}

	/** full constructor */
	public JstTradeExtend(JstTrade jstTrade, String alipayPoint,
			String availableConfirmFee, String buyerAlipayNo, String buyerArea,
			String buyerEmail, String buyerObtainPointFee, String codFee,
			String codStatus, String commissionFee, String is3d,
			String isBrandSale, String isForceWlb, String isLgtype,
			String isPartConsign, String isWt, String pccAf,
			String realPointFee, String receivedPayment, String sellerAlipayNo,
			String sellerCanRate, String sellerCodFee, String sellerEmail,
			String sellerMobile, String sellerName, String sellerPhone,
			String snapshotUrl, String picPath, String sellerRate,
			Double postFee, Date consignTime, String estConTime,
			String invoiceKind, String receiverCountry, String receiverTown,
			String orderTaxFee, Integer num, String numIid, String title,
			String type, Double price, String hasPostFee, String buyerMessage,
			String buyerMemo, Integer buyerFlag, String sellerFlag,
			String invoiceType, Double creditCardFee, String markDesc,
			Double buyerCodFee, Double adjustFee, String tradeFrom,
			String buyerRate) {
		this.jstTrade = jstTrade;
		this.alipayPoint = alipayPoint;
		this.availableConfirmFee = availableConfirmFee;
		this.buyerAlipayNo = buyerAlipayNo;
		this.buyerArea = buyerArea;
		this.buyerEmail = buyerEmail;
		this.buyerObtainPointFee = buyerObtainPointFee;
		this.codFee = codFee;
		this.codStatus = codStatus;
		this.commissionFee = commissionFee;
		this.is3d = is3d;
		this.isBrandSale = isBrandSale;
		this.isForceWlb = isForceWlb;
		this.isLgtype = isLgtype;
		this.isPartConsign = isPartConsign;
		this.isWt = isWt;
		this.pccAf = pccAf;
		this.realPointFee = realPointFee;
		this.receivedPayment = receivedPayment;
		this.sellerAlipayNo = sellerAlipayNo;
		this.sellerCanRate = sellerCanRate;
		this.sellerCodFee = sellerCodFee;
		this.sellerEmail = sellerEmail;
		this.sellerMobile = sellerMobile;
		this.sellerName = sellerName;
		this.sellerPhone = sellerPhone;
		this.snapshotUrl = snapshotUrl;
		this.picPath = picPath;
		this.sellerRate = sellerRate;
		this.postFee = postFee;
		this.consignTime = consignTime;
		this.estConTime = estConTime;
		this.invoiceKind = invoiceKind;
		this.receiverCountry = receiverCountry;
		this.receiverTown = receiverTown;
		this.orderTaxFee = orderTaxFee;
		this.num = num;
		this.numIid = numIid;
		this.title = title;
		this.type = type;
		this.price = price;
		this.hasPostFee = hasPostFee;
		this.buyerMessage = buyerMessage;
		this.buyerMemo = buyerMemo;
		this.buyerFlag = buyerFlag;
		this.sellerFlag = sellerFlag;
		this.invoiceType = invoiceType;
		this.creditCardFee = creditCardFee;
		this.markDesc = markDesc;
		this.buyerCodFee = buyerCodFee;
		this.adjustFee = adjustFee;
		this.tradeFrom = tradeFrom;
		this.buyerRate = buyerRate;
	}

	// Property accessors
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TRADE_EXT_SYS_ID", unique = true, nullable = false)
	public Integer getTradeExtSysId() {
		return this.tradeExtSysId;
	}

	public void setTradeExtSysId(Integer tradeExtSysId) {
		this.tradeExtSysId = tradeExtSysId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TRADE_SYS_ID", nullable = false)
	public JstTrade getJstTrade() {
		return this.jstTrade;
	}

	public void setJstTrade(JstTrade jstTrade) {
		this.jstTrade = jstTrade;
	}

	@Column(name = "ALIPAY_POINT", length = 200)
	public String getAlipayPoint() {
		return this.alipayPoint;
	}

	public void setAlipayPoint(String alipayPoint) {
		this.alipayPoint = alipayPoint;
	}

	@Column(name = "available_confirm_fee", length = 200)
	public String getAvailableConfirmFee() {
		return this.availableConfirmFee;
	}

	public void setAvailableConfirmFee(String availableConfirmFee) {
		this.availableConfirmFee = availableConfirmFee;
	}

	@Column(name = "buyer_alipay_no", length = 200)
	public String getBuyerAlipayNo() {
		return this.buyerAlipayNo;
	}

	public void setBuyerAlipayNo(String buyerAlipayNo) {
		this.buyerAlipayNo = buyerAlipayNo;
	}

	@Column(name = "buyer_area", length = 200)
	public String getBuyerArea() {
		return this.buyerArea;
	}

	public void setBuyerArea(String buyerArea) {
		this.buyerArea = buyerArea;
	}

	@Column(name = "buyer_email", length = 200)
	public String getBuyerEmail() {
		return this.buyerEmail;
	}

	public void setBuyerEmail(String buyerEmail) {
		this.buyerEmail = buyerEmail;
	}

	@Column(name = "buyer_obtain_point_fee", length = 200)
	public String getBuyerObtainPointFee() {
		return this.buyerObtainPointFee;
	}

	public void setBuyerObtainPointFee(String buyerObtainPointFee) {
		this.buyerObtainPointFee = buyerObtainPointFee;
	}

	@Column(name = "cod_fee", length = 200)
	public String getCodFee() {
		return this.codFee;
	}

	public void setCodFee(String codFee) {
		this.codFee = codFee;
	}

	@Column(name = "cod_status", length = 200)
	public String getCodStatus() {
		return this.codStatus;
	}

	public void setCodStatus(String codStatus) {
		this.codStatus = codStatus;
	}

	@Column(name = "commission_fee", length = 200)
	public String getCommissionFee() {
		return this.commissionFee;
	}

	public void setCommissionFee(String commissionFee) {
		this.commissionFee = commissionFee;
	}

	@Column(name = "is_3D", length = 200)
	public String getIs3d() {
		return this.is3d;
	}

	public void setIs3d(String is3d) {
		this.is3d = is3d;
	}

	@Column(name = "is_brand_sale", length = 200)
	public String getIsBrandSale() {
		return this.isBrandSale;
	}

	public void setIsBrandSale(String isBrandSale) {
		this.isBrandSale = isBrandSale;
	}

	@Column(name = "is_force_wlb", length = 200)
	public String getIsForceWlb() {
		return this.isForceWlb;
	}

	public void setIsForceWlb(String isForceWlb) {
		this.isForceWlb = isForceWlb;
	}

	@Column(name = "is_lgtype", length = 200)
	public String getIsLgtype() {
		return this.isLgtype;
	}

	public void setIsLgtype(String isLgtype) {
		this.isLgtype = isLgtype;
	}

	@Column(name = "is_part_consign", length = 200)
	public String getIsPartConsign() {
		return this.isPartConsign;
	}

	public void setIsPartConsign(String isPartConsign) {
		this.isPartConsign = isPartConsign;
	}

	@Column(name = "is_wt", length = 200)
	public String getIsWt() {
		return this.isWt;
	}

	public void setIsWt(String isWt) {
		this.isWt = isWt;
	}

	@Column(name = "pcc_af", length = 200)
	public String getPccAf() {
		return this.pccAf;
	}

	public void setPccAf(String pccAf) {
		this.pccAf = pccAf;
	}

	@Column(name = "real_point_fee", length = 200)
	public String getRealPointFee() {
		return this.realPointFee;
	}

	public void setRealPointFee(String realPointFee) {
		this.realPointFee = realPointFee;
	}

	@Column(name = "received_payment", length = 200)
	public String getReceivedPayment() {
		return this.receivedPayment;
	}

	public void setReceivedPayment(String receivedPayment) {
		this.receivedPayment = receivedPayment;
	}

	@Column(name = "seller_alipay_no", length = 200)
	public String getSellerAlipayNo() {
		return this.sellerAlipayNo;
	}

	public void setSellerAlipayNo(String sellerAlipayNo) {
		this.sellerAlipayNo = sellerAlipayNo;
	}

	@Column(name = "seller_can_rate", length = 200)
	public String getSellerCanRate() {
		return this.sellerCanRate;
	}

	public void setSellerCanRate(String sellerCanRate) {
		this.sellerCanRate = sellerCanRate;
	}

	@Column(name = "seller_cod_fee", length = 200)
	public String getSellerCodFee() {
		return this.sellerCodFee;
	}

	public void setSellerCodFee(String sellerCodFee) {
		this.sellerCodFee = sellerCodFee;
	}

	@Column(name = "seller_email", length = 200)
	public String getSellerEmail() {
		return this.sellerEmail;
	}

	public void setSellerEmail(String sellerEmail) {
		this.sellerEmail = sellerEmail;
	}

	@Column(name = "seller_mobile", length = 200)
	public String getSellerMobile() {
		return this.sellerMobile;
	}

	public void setSellerMobile(String sellerMobile) {
		this.sellerMobile = sellerMobile;
	}

	@Column(name = "seller_name", length = 200)
	public String getSellerName() {
		return this.sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	@Column(name = "seller_phone", length = 200)
	public String getSellerPhone() {
		return this.sellerPhone;
	}

	public void setSellerPhone(String sellerPhone) {
		this.sellerPhone = sellerPhone;
	}

	@Column(name = "snapshot_url", length = 200)
	public String getSnapshotUrl() {
		return this.snapshotUrl;
	}

	public void setSnapshotUrl(String snapshotUrl) {
		this.snapshotUrl = snapshotUrl;
	}

	@Column(name = "PIC_PATH", length = 200)
	public String getPicPath() {
		return this.picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

	@Column(name = "SELLER_RATE", length = 200)
	public String getSellerRate() {
		return this.sellerRate;
	}

	public void setSellerRate(String sellerRate) {
		this.sellerRate = sellerRate;
	}

	@Column(name = "POST_FEE", precision = 22, scale = 0)
	public Double getPostFee() {
		return this.postFee;
	}

	public void setPostFee(Double postFee) {
		this.postFee = postFee;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "CONSIGN_TIME", length = 19)
	public Date getConsignTime() {
		return this.consignTime;
	}

	public void setConsignTime(Date consignTime) {
		this.consignTime = consignTime;
	}

	@Column(name = "EST_CON_TIME", length = 100)
	public String getEstConTime() {
		return this.estConTime;
	}

	public void setEstConTime(String estConTime) {
		this.estConTime = estConTime;
	}

	@Column(name = "INVOICE_KIND", length = 10)
	public String getInvoiceKind() {
		return this.invoiceKind;
	}

	public void setInvoiceKind(String invoiceKind) {
		this.invoiceKind = invoiceKind;
	}

	@Column(name = "RECEIVER_COUNTRY", length = 200)
	public String getReceiverCountry() {
		return this.receiverCountry;
	}

	public void setReceiverCountry(String receiverCountry) {
		this.receiverCountry = receiverCountry;
	}

	@Column(name = "RECEIVER_TOWN", length = 200)
	public String getReceiverTown() {
		return this.receiverTown;
	}

	public void setReceiverTown(String receiverTown) {
		this.receiverTown = receiverTown;
	}

	@Column(name = "ORDER_TAX_FEE", length = 200)
	public String getOrderTaxFee() {
		return this.orderTaxFee;
	}

	public void setOrderTaxFee(String orderTaxFee) {
		this.orderTaxFee = orderTaxFee;
	}

	@Column(name = "NUM")
	public Integer getNum() {
		return this.num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	@Column(name = "NUM_IID", length = 200)
	public String getNumIid() {
		return this.numIid;
	}

	public void setNumIid(String numIid) {
		this.numIid = numIid;
	}

	@Column(name = "TITLE", length = 200)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "TYPE", length = 200)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "PRICE", precision = 22, scale = 0)
	public Double getPrice() {
		return this.price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Column(name = "HAS_POST_FEE", length = 50)
	public String getHasPostFee() {
		return this.hasPostFee;
	}

	public void setHasPostFee(String hasPostFee) {
		this.hasPostFee = hasPostFee;
	}

	@Column(name = "BUYER_MESSAGE", length = 500)
	public String getBuyerMessage() {
		return this.buyerMessage;
	}

	public void setBuyerMessage(String buyerMessage) {
		this.buyerMessage = buyerMessage;
	}

	@Column(name = "BUYER_MEMO", length = 500)
	public String getBuyerMemo() {
		return this.buyerMemo;
	}

	public void setBuyerMemo(String buyerMemo) {
		this.buyerMemo = buyerMemo;
	}

	@Column(name = "BUYER_FLAG")
	public Integer getBuyerFlag() {
		return this.buyerFlag;
	}

	public void setBuyerFlag(Integer buyerFlag) {
		this.buyerFlag = buyerFlag;
	}

	@Column(name = "SELLER_FLAG", length = 200)
	public String getSellerFlag() {
		return this.sellerFlag;
	}

	public void setSellerFlag(String sellerFlag) {
		this.sellerFlag = sellerFlag;
	}

	@Column(name = "INVOICE_TYPE", length = 200)
	public String getInvoiceType() {
		return this.invoiceType;
	}

	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}

	@Column(name = "CREDIT_CARD_FEE", precision = 22, scale = 0)
	public Double getCreditCardFee() {
		return this.creditCardFee;
	}

	public void setCreditCardFee(Double creditCardFee) {
		this.creditCardFee = creditCardFee;
	}

	@Column(name = "MARK_DESC", length = 200)
	public String getMarkDesc() {
		return this.markDesc;
	}

	public void setMarkDesc(String markDesc) {
		this.markDesc = markDesc;
	}

	@Column(name = "BUYER_COD_FEE", precision = 22, scale = 0)
	public Double getBuyerCodFee() {
		return this.buyerCodFee;
	}

	public void setBuyerCodFee(Double buyerCodFee) {
		this.buyerCodFee = buyerCodFee;
	}

	@Column(name = "ADJUST_FEE", precision = 22, scale = 0)
	public Double getAdjustFee() {
		return this.adjustFee;
	}

	public void setAdjustFee(Double adjustFee) {
		this.adjustFee = adjustFee;
	}

	@Column(name = "TRADE_FROM", length = 200)
	public String getTradeFrom() {
		return this.tradeFrom;
	}

	public void setTradeFrom(String tradeFrom) {
		this.tradeFrom = tradeFrom;
	}

	@Column(name = "BUYER_RATE", length = 200)
	public String getBuyerRate() {
		return this.buyerRate;
	}

	public void setBuyerRate(String buyerRate) {
		this.buyerRate = buyerRate;
	}

}