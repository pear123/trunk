package com.arvato.cc.model;

import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

/**
 * ExpOrder entity.
 *
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "exp_order")
public class ExpOrder implements java.io.Serializable {

    // Fields

    private Integer id;
    private Integer version;
    private String salesorg;
    private String distrchannel;
    private String salesdoctype;
    private String customer;
    private String poNumber;
    private String podate;
    private Timestamp pricingdate;
    private String partnerfunctn;
    private String shipToParty;
    private String name;
    private String street;
    private String city;
    private String postalCode;
    private String material;
    private Integer quantity;
    private String tmallPrice;
    private Double conditionAmount;
    private String tmallPoints;
    private Double pointAmout;
    private String tmallCoupon;
    private Double couponAmount;
    private String poachiveno;
    private String text1;
    private String internalnote;
    private String text2;
    private String billingtext;
    private String text3;
    private String storeId;
    private String tradeStatus;
    private String tid;
    private String pointFeeRemark;
    private String couponRemark;
    private String addressRemark;
    private String alipayNo;

    private String receiverCity;
    private String receiverState;
    private String countRemark;
    private Double payment;
    private String refundStatus; //退款状态
    // Constructors

    private String conditionAmountRemark;
    private String sellerMemo;
    private String invoiceName;
    private String receiverName;
    private String itemTitle;
    private String itemType;
    private Double orderPay;
    private Double orderPayment;
    private Double itemPayment;
    private Timestamp endTime;
    private Double receivedPayment;
    private Double originalPointFee;
    private Double originalCoupon;
    private String refundId;


    /**
     * default constructor
     */
    public ExpOrder() {
    }

    /**
     * full constructor
     */
    public ExpOrder(String salesorg, String distrchannel, String salesdoctype,
                    String customer, String poNumber, String podate, Timestamp pricingdate,
                    String partnerfunctn, String shipToParty, String name,
                    String street, String city, String postalCode, String material,
                    Integer quantity, String tmallPrice, Double conditionAmount,
                    String tmallPoints, Double pointAmout, String tmallCoupon,
                    Double couponAmount, String poachiveno, String text1, String text2,
                    String text3, String storeId) {
        this.salesorg = salesorg;
        this.distrchannel = distrchannel;
        this.salesdoctype = salesdoctype;
        this.customer = customer;
        this.poNumber = poNumber;
        this.podate = podate;
        this.pricingdate = pricingdate;
        this.partnerfunctn = partnerfunctn;
        this.shipToParty = shipToParty;
        this.name = name;
        this.street = street;
        this.city = city;
        this.postalCode = postalCode;
        this.material = material;
        this.quantity = quantity;
        this.tmallPrice = tmallPrice;
        this.conditionAmount = conditionAmount;
        this.tmallPoints = tmallPoints;
        this.pointAmout = pointAmout;
        this.tmallCoupon = tmallCoupon;
        this.couponAmount = couponAmount;
        this.poachiveno = poachiveno;
        this.internalnote = internalnote;
        this.billingtext = billingtext;
        this.text1 = text1;
        this.text2 = text2;
        this.text3 = text3;
        this.storeId = storeId;
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

    @Version
    @Column(name = "VERSION")
    public Integer getVersion() {
        return this.version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Column(name = "SALESORG", length = 20)
    public String getSalesorg() {
        return this.salesorg;
    }

    public void setSalesorg(String salesorg) {
        this.salesorg = salesorg;
    }

    @Column(name = "INTERNALNOTE", length = 20)
    public String getInternalnote() {
        return internalnote;
    }

    public void setInternalnote(String internalnote) {
        this.internalnote = internalnote;
    }

    @Column(name = "BILLINGTEXT", length = 20)
    public String getBillingtext() {
        return billingtext;
    }

    public void setBillingtext(String billingtext) {
        this.billingtext = billingtext;
    }

    @Column(name = "DISTRCHANNEL", length = 20)
    public String getDistrchannel() {
        return this.distrchannel;
    }

    public void setDistrchannel(String distrchannel) {
        this.distrchannel = distrchannel;
    }

    @Column(name = "SALESDOCTYPE", length = 20)
    public String getSalesdoctype() {
        return this.salesdoctype;
    }

    public void setSalesdoctype(String salesdoctype) {
        this.salesdoctype = salesdoctype;
    }

    @Column(name = "CUSTOMER", length = 200)
    public String getCustomer() {
        return this.customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    @Column(name = "PO_NUMBER", length = 200)
    public String getPoNumber() {
        return this.poNumber;
    }

    public void setPoNumber(String poNumber) {
        this.poNumber = poNumber;
    }

    @Column(name = "PODATE", length = 30)
    public String getPodate() {
        return this.podate;
    }

    public void setPodate(String podate) {
        this.podate = podate;
    }

    @Column(name = "PRICINGDATE", length = 19)
    public Timestamp getPricingdate() {
        return this.pricingdate;
    }

    public void setPricingdate(Timestamp pricingdate) {
        this.pricingdate = pricingdate;
    }

    @Column(name = "PARTNERFUNCTN", length = 30)
    public String getPartnerfunctn() {
        return this.partnerfunctn;
    }

    public void setPartnerfunctn(String partnerfunctn) {
        this.partnerfunctn = partnerfunctn;
    }

    @Column(name = "SHIP_TO_PARTY", length = 30)
    public String getShipToParty() {
        return this.shipToParty;
    }

    public void setShipToParty(String shipToParty) {
        this.shipToParty = shipToParty;
    }

    @Column(name = "NAME", length = 200)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "STREET", length = 200)
    public String getStreet() {
        return this.street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Column(name = "CITY", length = 200)
    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Column(name = "POSTAL_CODE", length = 200)
    public String getPostalCode() {
        return this.postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Column(name = "MATERIAL", length = 200)
    public String getMaterial() {
        return this.material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    @Column(name = "QUANTITY")
    public Integer getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Column(name = "TMALL_PRICE", length = 200)
    public String getTmallPrice() {
        return this.tmallPrice;
    }

    public void setTmallPrice(String tmallPrice) {
        this.tmallPrice = tmallPrice;
    }

    @Column(name = "CONDITION_AMOUNT", precision = 22, scale = 0)
    public Double getConditionAmount() {
        return this.conditionAmount;
    }

    public void setConditionAmount(Double conditionAmount) {
        this.conditionAmount = conditionAmount;
    }

    @Column(name = "TMALL_POINTS", length = 200)
    public String getTmallPoints() {
        return this.tmallPoints;
    }

    public void setTmallPoints(String tmallPoints) {
        this.tmallPoints = tmallPoints;
    }

    @Column(name = "POINT_AMOUT")
    public Double getPointAmout() {
        return this.pointAmout;
    }

    public void setPointAmout(Double pointAmout) {
        this.pointAmout = pointAmout;
    }

    @Column(name = "tmall_coupon", length = 50)
    public String getTmallCoupon() {
        return this.tmallCoupon;
    }

    public void setTmallCoupon(String tmallCoupon) {
        this.tmallCoupon = tmallCoupon;
    }

    @Column(name = "coupon_amount", precision = 22, scale = 0)
    public Double getCouponAmount() {
        return this.couponAmount;
    }

    public void setCouponAmount(Double couponAmount) {
        this.couponAmount = couponAmount;
    }

    @Column(name = "POACHIVENO", length = 200)
    public String getPoachiveno() {
        return this.poachiveno;
    }

    public void setPoachiveno(String poachiveno) {
        this.poachiveno = poachiveno;
    }
    //mod by kalend
    @Column(name = "TEXT1", length = 1000)
    public String getText1() {
        return this.text1;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }
    //mod by kalend
    @Column(name = "TEXT2", length = 1000)
    public String getText2() {
        return this.text2;
    }

    public void setText2(String text2) {
        this.text2 = text2;
    }
    //mod by kalend
    @Column(name = "TEXT3", length = 1000)
    public String getText3() {
        return this.text3;
    }

    public void setText3(String text3) {
        this.text3 = text3;
    }

    @Column(name = "STORE_ID", length = 100)
    public String getStoreId() {
        return this.storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    @Column(name = "trade_status", length = 100)
    public String getTradeStatus() {
        return this.tradeStatus;
    }

    public void setTradeStatus(String tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    @Column(name = "tid", length = 100)
    public String getTid() {
        return this.tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    @Column(name = "point_fee_remark", length = 45)
    public String getPointFeeRemark() {
        return this.pointFeeRemark;
    }

    public void setPointFeeRemark(String pointFeeRemark) {
        this.pointFeeRemark = pointFeeRemark;
    }

    @Column(name = "coupon_remark", length = 45)
    public String getCouponRemark() {
        return this.couponRemark;
    }

    public void setCouponRemark(String couponRemark) {
        this.couponRemark = couponRemark;
    }

    @Column(name = "address_remark", length = 45)
    public String getAddressRemark() {
        return this.addressRemark;
    }

    public void setAddressRemark(String addressRemark) {
        this.addressRemark = addressRemark;
    }

    @Column(name = "ALIPAY_NO", length = 100)
    public String getAlipayNo() {
        return this.alipayNo;
    }

    public void setAlipayNo(String alipayNo) {
        this.alipayNo = alipayNo;
    }

    @Column(name = "RECEIVER_CITY", length = 200)
    public String getReceiverCity() {
        return this.receiverCity;
    }

    public void setReceiverCity(String receiverCity) {
        this.receiverCity = receiverCity;
    }

    @Column(name = "RECEIVER_STATE", length = 200)
    public String getReceiverState() {
        return this.receiverState;
    }

    public void setReceiverState(String receiverState) {
        this.receiverState = receiverState;
    }

    @Column(name = "count_remark", length = 45)
    public String getCountRemark() {
        return this.countRemark;
    }

    public void setCountRemark(String countRemark) {
        this.countRemark = countRemark;
    }

    @Column(name = "PAYMENT", precision = 22, scale = 0)
    public Double getPayment() {
        return this.payment;
    }

    public void setPayment(Double payment) {
        this.payment = payment;
    }

    @Column(name = "REFUND_STATUS", length = 100)
    public String getRefundStatus() {
        return this.refundStatus;
    }

    public void setRefundStatus(String refundStatus) {
        this.refundStatus = refundStatus;
    }

    @Column(name = "condition_amount_remark", length = 45)
    public String getConditionAmountRemark() {
        return this.conditionAmountRemark;
    }

    public void setConditionAmountRemark(String conditionAmountRemark) {
        this.conditionAmountRemark = conditionAmountRemark;
    }

    @Column(name = "seller_memo", length = 1000)
    public String getSellerMemo() {
        return sellerMemo;
    }

    public void setSellerMemo(String sellerMemo) {
        this.sellerMemo = sellerMemo;
    }


    @Column(name = "invoice_name", length = 200)
    public String getInvoiceName() {
        return invoiceName;
    }

    public void setInvoiceName(String invoiceName) {
        this.invoiceName = invoiceName;
    }

    @Column(name = "receiver_name", length = 200)
    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    @Column(name = "item_title", length = 200)
    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    @Column(name = "item_type", length = 200)
    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    @Column(name = "order_pay", precision = 22, scale = 0)
    public Double getOrderPay() {
        return orderPay;
    }

    public void setOrderPay(Double orderPay) {
        this.orderPay = orderPay;
    }

    @Column(name = "order_payment", precision = 22, scale = 0)
    public Double getOrderPayment() {
        return orderPayment;
    }

    public void setOrderPayment(Double orderPayment) {
        this.orderPayment = orderPayment;
    }

    @Column(name = "item_payment", precision = 22, scale = 0)
    public Double getItemPayment() {
        return itemPayment;
    }

    public void setItemPayment(Double itemPayment) {
        this.itemPayment = itemPayment;
    }

    @Column(name = "end_time", length = 19)
    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    @Column(name = "received_payment", precision = 22, scale = 0)
    public Double getReceivedPayment() {
        return receivedPayment;
    }

    public void setReceivedPayment(Double receivedPayment) {
        this.receivedPayment = receivedPayment;
    }
    @Column(name = "original_point_fee", precision = 22, scale = 0)
    public Double getOriginalPointFee() {
        return originalPointFee;
    }

    public void setOriginalPointFee(Double originalPointFee) {
        this.originalPointFee = originalPointFee;
    }
    @Column(name = "original_coupon", precision = 22, scale = 0)
    public Double getOriginalCoupon() {
        return originalCoupon;
    }

    public void setOriginalCoupon(Double originalCoupon) {
        this.originalCoupon = originalCoupon;
    }

    @Column(name = "REFUND_ID", length = 100)
    public String getRefundId() {
        return this.refundId;
    }

    public void setRefundId(String refundId) {
        this.refundId = refundId;
    }
}