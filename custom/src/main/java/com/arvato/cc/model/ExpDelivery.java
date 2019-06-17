package com.arvato.cc.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

/**
 * ExpDelivery entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "exp_delivery")
public class ExpDelivery implements java.io.Serializable {

	// Fields

	private Integer id;
	private String orderId;
	private String expressNo;
	private String subOrderNo;
	private String receiverAddress;
	private String receiverMobile;
	private String receiverName;
	private String recevierCompany;
	private String recevierPhone;
	private Integer printCount;
	private String sendContent;
	private Integer sendNum;
	private String sendCompany;
	private String sendAddress;
	private String sendMobile;
	private String senderName;
	private String sendStart;
	private String sendTo;
	private Double weightFee;
	private Double weightActual;
	private String closeToId;
	private Date shipDate;
	private String memo;
	private String serviceType;
	private String urgent;
	private String payMode;
	private String receiveLoan;
	private String loanId;
	private Double loanFee;
	private String permissionOrder;
	private Double otherFee;
	private Double amount;
	private String protectPrice;
	private Double protectFee;
	private String isAskfor;
	private String isSignReceipt;
	private String isSendOnTime;
	private Date sendDate;
	private Date sendTime;
	private Double shippigFee;
	private Double length;
	private Double width;
	private Double height;
	private String extendField1;
	private String extendField2;
	private String extendField3;
	private Date shipTime;
	private String receiveState;
	private String username;
	private String customerId;
	private String invoiceTitle;
	private Double invoiceFee;
	private String giftSku;
	private Integer giftNum;
	private Double total;
	private Integer version;
	private String storeId;
    private String orderStatus;
    private String presentNames;
    private Double pointFee;
    private String buyerNick;
    private String alipayNo;
    private String storeCode;
    private String receiverState;
    private String receiverCity;
    private String receiverDistrict;

    @Column(name = "RECEIVER_STATE", length = 500)
    public String getReceiverState() {
        return this.receiverState;
    }

    public void setReceiverState(String receiverState) {
        this.receiverState = receiverState;
    }

    @Column(name = "RECEIVER_CITY", length = 500)
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

    @Column(name = "PRICINGDATE", length = 19)
    public Timestamp getPricingDate() {
        return pricingDate;
    }

    public void setPricingDate(Timestamp pricingDate) {
        this.pricingDate = pricingDate;
    }

    @Column(name = "INVOICE_N0", length = 200)
    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    private Timestamp pricingDate;//订单的创建时间
    private String invoiceNo;//发票号
    private Timestamp endTime;

    private String sellerMemo;
    private String tradeAddress;
    private String tradeMobile;
    private String tradeName;


	// Constructors

	/** default constructor */
	public ExpDelivery() {
	}

	/** full constructor */
	public ExpDelivery(String orderId, String expressNo, String subOrderNo,
			String receiverAddress, String receiverMobile, String receiverName,
			String recevierCompany, String recevierPhone, Integer printCount,
			String sendContent, Integer sendNum, String sendCompany,
			String sendAddress, String sendMobile, String senderName,
			String sendStart, String sendTo, Double weightFee,
			Double weightActual, String closeToId, Date shipDate, String memo,
			String serviceType, String urgent, String payMode,
			String receiveLoan, String loanId, Double loanFee,
			String permissionOrder, Double otherFee, Double amount,
			String protectPrice, Double protectFee, String isAskfor,
			String isSignReceipt, String isSendOnTime, Date sendDate,
			Date sendTime, Double shippigFee, Double length, Double width,
			Double height, String extendField1, String extendField2,
			String extendField3, Date shipTime, String receiveState,
			String username, String customerId, String invoiceTitle,
			Double invoiceFee, String giftSku, Integer giftNum, Double total,
			Integer version, String storeId, String orderStatus) {
		this.orderId = orderId;
		this.expressNo = expressNo;
		this.subOrderNo = subOrderNo;
		this.receiverAddress = receiverAddress;
		this.receiverMobile = receiverMobile;
		this.receiverName = receiverName;
		this.recevierCompany = recevierCompany;
		this.recevierPhone = recevierPhone;
		this.printCount = printCount;
		this.sendContent = sendContent;
		this.sendNum = sendNum;
		this.sendCompany = sendCompany;
		this.sendAddress = sendAddress;
		this.sendMobile = sendMobile;
		this.senderName = senderName;
		this.sendStart = sendStart;
		this.sendTo = sendTo;
		this.weightFee = weightFee;
		this.weightActual = weightActual;
		this.closeToId = closeToId;
		this.shipDate = shipDate;
		this.memo = memo;
		this.serviceType = serviceType;
		this.urgent = urgent;
		this.payMode = payMode;
		this.receiveLoan = receiveLoan;
		this.loanId = loanId;
		this.loanFee = loanFee;
		this.permissionOrder = permissionOrder;
		this.otherFee = otherFee;
		this.amount = amount;
		this.protectPrice = protectPrice;
		this.protectFee = protectFee;
		this.isAskfor = isAskfor;
		this.isSignReceipt = isSignReceipt;
		this.isSendOnTime = isSendOnTime;
		this.sendDate = sendDate;
		this.sendTime = sendTime;
		this.shippigFee = shippigFee;
		this.length = length;
		this.width = width;
		this.height = height;
		this.extendField1 = extendField1;
		this.extendField2 = extendField2;
		this.extendField3 = extendField3;
		this.shipTime = shipTime;
		this.receiveState = receiveState;
		this.username = username;
		this.customerId = customerId;
		this.invoiceTitle = invoiceTitle;
		this.invoiceFee = invoiceFee;
		this.giftSku = giftSku;
		this.giftNum = giftNum;
		this.total = total;
		this.version = version;
		this.storeId = storeId;
		this.orderStatus = orderStatus;
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

	@Column(name = "ORDER_ID", length = 200)
	public String getOrderId() {
		return this.orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	@Column(name = "EXPRESS_NO", length = 200)
	public String getExpressNo() {
		return this.expressNo;
	}

	public void setExpressNo(String expressNo) {
		this.expressNo = expressNo;
	}

	@Column(name = "SUB_ORDER_NO", length = 200)
	public String getSubOrderNo() {
		return this.subOrderNo;
	}

	public void setSubOrderNo(String subOrderNo) {
		this.subOrderNo = subOrderNo;
	}

	@Column(name = "RECEIVER_ADDRESS", length = 200)
	public String getReceiverAddress() {
		return this.receiverAddress;
	}

	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}

	@Column(name = "RECEIVER_MOBILE", length = 20)
	public String getReceiverMobile() {
		return this.receiverMobile;
	}

	public void setReceiverMobile(String receiverMobile) {
		this.receiverMobile = receiverMobile;
	}

	@Column(name = "RECEIVER_NAME", length = 100)
	public String getReceiverName() {
		return this.receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	@Column(name = "RECEVIER_COMPANY", length = 100)
	public String getRecevierCompany() {
		return this.recevierCompany;
	}

	public void setRecevierCompany(String recevierCompany) {
		this.recevierCompany = recevierCompany;
	}

	@Column(name = "RECEVIER_PHONE", length = 20)
	public String getRecevierPhone() {
		return this.recevierPhone;
	}

	public void setRecevierPhone(String recevierPhone) {
		this.recevierPhone = recevierPhone;
	}

	@Column(name = "PRINT_COUNT")
	public Integer getPrintCount() {
		return this.printCount;
	}

	public void setPrintCount(Integer printCount) {
		this.printCount = printCount;
	}

	@Column(name = "SEND_CONTENT", length = 200)
	public String getSendContent() {
		return this.sendContent;
	}

	public void setSendContent(String sendContent) {
		this.sendContent = sendContent;
	}

	@Column(name = "SEND_NUM")
	public Integer getSendNum() {
		return this.sendNum;
	}

	public void setSendNum(Integer sendNum) {
		this.sendNum = sendNum;
	}

	@Column(name = "SEND_COMPANY", length = 200)
	public String getSendCompany() {
		return this.sendCompany;
	}

	public void setSendCompany(String sendCompany) {
		this.sendCompany = sendCompany;
	}

	@Column(name = "SEND_ADDRESS", length = 200)
	public String getSendAddress() {
		return this.sendAddress;
	}

	public void setSendAddress(String sendAddress) {
		this.sendAddress = sendAddress;
	}

	@Column(name = "SEND_MOBILE", length = 200)
	public String getSendMobile() {
		return this.sendMobile;
	}

	public void setSendMobile(String sendMobile) {
		this.sendMobile = sendMobile;
	}

	@Column(name = "SENDER_NAME", length = 200)
	public String getSenderName() {
		return this.senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	@Column(name = "SEND_START", length = 200)
	public String getSendStart() {
		return this.sendStart;
	}

	public void setSendStart(String sendStart) {
		this.sendStart = sendStart;
	}

	@Column(name = "SEND_TO", length = 200)
	public String getSendTo() {
		return this.sendTo;
	}

	public void setSendTo(String sendTo) {
		this.sendTo = sendTo;
	}

	@Column(name = "WEIGHT_FEE", precision = 22, scale = 0)
	public Double getWeightFee() {
		return this.weightFee;
	}

	public void setWeightFee(Double weightFee) {
		this.weightFee = weightFee;
	}

	@Column(name = "WEIGHT_ACTUAL", precision = 22, scale = 0)
	public Double getWeightActual() {
		return this.weightActual;
	}

	public void setWeightActual(Double weightActual) {
		this.weightActual = weightActual;
	}

	@Column(name = "CLOSE_TO_ID", length = 200)
	public String getCloseToId() {
		return this.closeToId;
	}

	public void setCloseToId(String closeToId) {
		this.closeToId = closeToId;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "SHIP_DATE", length = 19)
	public Date getShipDate() {
		return this.shipDate;
	}

	public void setShipDate(Date shipDate) {
		this.shipDate = shipDate;
	}

	@Column(name = "MEMO", length = 500)
	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Column(name = "SERVICE_TYPE", length = 200)
	public String getServiceType() {
		return this.serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	@Column(name = "URGENT", length = 200)
	public String getUrgent() {
		return this.urgent;
	}

	public void setUrgent(String urgent) {
		this.urgent = urgent;
	}

	@Column(name = "PAY_MODE", length = 200)
	public String getPayMode() {
		return this.payMode;
	}

	public void setPayMode(String payMode) {
		this.payMode = payMode;
	}

	@Column(name = "RECEIVE_LOAN", length = 200)
	public String getReceiveLoan() {
		return this.receiveLoan;
	}

	public void setReceiveLoan(String receiveLoan) {
		this.receiveLoan = receiveLoan;
	}

	@Column(name = "LOAN_ID", length = 200)
	public String getLoanId() {
		return this.loanId;
	}

	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}

	@Column(name = "LOAN_FEE", precision = 22, scale = 0)
	public Double getLoanFee() {
		return this.loanFee;
	}

	public void setLoanFee(Double loanFee) {
		this.loanFee = loanFee;
	}

	@Column(name = "PERMISSION_ORDER", length = 200)
	public String getPermissionOrder() {
		return this.permissionOrder;
	}

	public void setPermissionOrder(String permissionOrder) {
		this.permissionOrder = permissionOrder;
	}

	@Column(name = "OTHER_FEE", precision = 22, scale = 0)
	public Double getOtherFee() {
		return this.otherFee;
	}

	public void setOtherFee(Double otherFee) {
		this.otherFee = otherFee;
	}

	@Column(name = "AMOUNT", precision = 22, scale = 0)
	public Double getAmount() {
		return this.amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@Column(name = "PROTECT_PRICE", length = 10)
	public String getProtectPrice() {
		return this.protectPrice;
	}

	public void setProtectPrice(String protectPrice) {
		this.protectPrice = protectPrice;
	}

	@Column(name = "PROTECT_FEE", precision = 22, scale = 0)
	public Double getProtectFee() {
		return this.protectFee;
	}

	public void setProtectFee(Double protectFee) {
		this.protectFee = protectFee;
	}

	@Column(name = "IS_ASKFOR", length = 10)
	public String getIsAskfor() {
		return this.isAskfor;
	}

	public void setIsAskfor(String isAskfor) {
		this.isAskfor = isAskfor;
	}

	@Column(name = "IS_SIGN_RECEIPT", length = 10)
	public String getIsSignReceipt() {
		return this.isSignReceipt;
	}

	public void setIsSignReceipt(String isSignReceipt) {
		this.isSignReceipt = isSignReceipt;
	}

	@Column(name = "IS_SEND_ON_TIME", length = 10)
	public String getIsSendOnTime() {
		return this.isSendOnTime;
	}

	public void setIsSendOnTime(String isSendOnTime) {
		this.isSendOnTime = isSendOnTime;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "SEND_DATE", length = 19)
	public Date getSendDate() {
		return this.sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "SEND_TIME", length = 19)
	public Date getSendTime() {
		return this.sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	@Column(name = "SHIPPIG_FEE", precision = 22, scale = 0)
	public Double getShippigFee() {
		return this.shippigFee;
	}

	public void setShippigFee(Double shippigFee) {
		this.shippigFee = shippigFee;
	}

	@Column(name = "LENGTH", precision = 22, scale = 0)
	public Double getLength() {
		return this.length;
	}

	public void setLength(Double length) {
		this.length = length;
	}

	@Column(name = "WIDTH", precision = 22, scale = 0)
	public Double getWidth() {
		return this.width;
	}

	public void setWidth(Double width) {
		this.width = width;
	}

	@Column(name = "HEIGHT", precision = 22, scale = 0)
	public Double getHeight() {
		return this.height;
	}

	public void setHeight(Double height) {
		this.height = height;
	}

	@Column(name = "EXTEND_FIELD1", length = 200)
	public String getExtendField1() {
		return this.extendField1;
	}

	public void setExtendField1(String extendField1) {
		this.extendField1 = extendField1;
	}

	@Column(name = "EXTEND_FIELD2", length = 200)
	public String getExtendField2() {
		return this.extendField2;
	}

	public void setExtendField2(String extendField2) {
		this.extendField2 = extendField2;
	}

	@Column(name = "EXTEND_FIELD3", length = 200)
	public String getExtendField3() {
		return this.extendField3;
	}

	public void setExtendField3(String extendField3) {
		this.extendField3 = extendField3;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "SHIP_TIME", length = 19)
	public Date getShipTime() {
		return this.shipTime;
	}

	public void setShipTime(Date shipTime) {
		this.shipTime = shipTime;
	}

	@Column(name = "RECEIVE_STATE", length = 200)
	public String getReceiveState() {
		return this.receiveState;
	}

	public void setReceiveState(String receiveState) {
		this.receiveState = receiveState;
	}

	@Column(name = "USERNAME", length = 200)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "CUSTOMER_ID", length = 200)
	public String getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	@Column(name = "INVOICE_TITLE", length = 200)
	public String getInvoiceTitle() {
		return this.invoiceTitle;
	}

	public void setInvoiceTitle(String invoiceTitle) {
		this.invoiceTitle = invoiceTitle;
	}

	@Column(name = "INVOICE_FEE", precision = 22, scale = 0)
	public Double getInvoiceFee() {
		return this.invoiceFee;
	}

	public void setInvoiceFee(Double invoiceFee) {
		this.invoiceFee = invoiceFee;
	}

	@Column(name = "GIFT_SKU", length = 200)
	public String getGiftSku() {
		return this.giftSku;
	}

	public void setGiftSku(String giftSku) {
		this.giftSku = giftSku;
	}

	@Column(name = "GIFT_NUM", precision = 22, scale = 0)
	public Integer getGiftNum() {
		return this.giftNum;
	}

	public void setGiftNum(Integer giftNum) {
		this.giftNum = giftNum;
	}

	@Column(name = "TOTAL", precision = 22, scale = 0)
	public Double getTotal() {
		return this.total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	@Column(name = "VERSION")
	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@Column(name = "STORE_ID", length = 100)
	public String getStoreId() {
		return this.storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	@Column(name = "ORDER_STATUS", length = 100)
	public String getOrderStatus() {
		return this.orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

    @Column(name = "PRESENT_NAME", length = 500)
    public String getPresentNames() {
        return presentNames;
    }

    public void setPresentNames(String presentNames) {
        this.presentNames = presentNames;
    }


    @Column(name = "point_fee", nullable = false, precision = 22, scale = 0)
    public Double getPointFee() {
        return this.pointFee;
    }

    public void setPointFee(Double pointFee) {
        this.pointFee = pointFee;
    }


    @Column(name = "BUYER_NICK", length = 200)
    public String getBuyerNick() {
        return this.buyerNick;
    }

    public void setBuyerNick(String buyerNick) {
        this.buyerNick = buyerNick;
    }

    @Column(name = "end_time", length = 19)
    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    @Column(name = "seller_memo", length = 1000)
    public String getSellerMemo() {
        return sellerMemo;
    }

    public void setSellerMemo(String sellerMemo) {
        this.sellerMemo = sellerMemo;
    }
    @Column(name = "trade_address", length = 200)
    public String getTradeAddress() {
        return tradeAddress;
    }

    public void setTradeAddress(String tradeAddress) {
        this.tradeAddress = tradeAddress;
    }
    @Column(name = "trade_mobile", length = 200)
    public String getTradeMobile() {
        return tradeMobile;
    }

    public void setTradeMobile(String tradeMobile) {
        this.tradeMobile = tradeMobile;
    }
    @Column(name = "trade_name", length = 200)
    public String getTradeName() {
        return tradeName;
    }

    public void setTradeName(String tradeName) {
        this.tradeName = tradeName;
    }

    @Column(name = "ALIPAY_NO", length = 100)
    public String getAlipayNo() {
        return this.alipayNo;
    }

    public void setAlipayNo(String alipayNo) {
        this.alipayNo = alipayNo;
    }

    @Column(name = "store_code", length = 100)
    public String getStoreCode() {
        return this.storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

}