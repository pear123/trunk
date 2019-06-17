package com.arvato.cc.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * TempTrade entity.
 *
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "temp_trade")
public class TempTrade implements java.io.Serializable {

    // Fields

    private Integer tradeSysId;
    private String tid;
    private String alipayId;
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
    private Double pointFee;
    private Integer batchNo;
    private String cpdCode;
    private String alipayCreateTime;
    private String shippingType;
    private String alipayNo;


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
    private Double receivedPayment;
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
    private Timestamp consignTime;
    private Timestamp estConTime;
    private String invoiceKind;
    private String receiverCountry;
    private String receiverTown;
    private Double orderTaxFee;
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

    private String isDaixiao;
    private Double stepPaidFee;
    private String stepTradeStatus;
    private String shopCode;
    private Timestamp timeoutActionTime;
    private String promotion;
    private Timestamp etSerTime;
    private String etShopName;
    private String etVerifiedShopName;
    private String etPlateNumber;
    private Double paidCouponFee;
    private String sid;
    private String buyerIp;
    private String hasYfx;
    private Double yfxFee;
    private String yfxId;
    private String yfxType;
    private String eticketExt;
    private Timestamp sendTime;
    private Double expressAgencyFee;
    private String canRate;
    private String acookieId;
    private String tradeMemo;
    private String tradeSource;
    private Integer arriveInterval;
    private String arriveCutTime;
    private Integer consignInterval;
    private String o2o;
    private String o2oGuideId;
    private String o2oShopId;
    private String o2oGuideName;
    private String o2oShopName;
    private String o2oDelivery;
    private String zeroPurchase;
    private String o2oOutTradeId;
    private String hkEnName;
    private String hkFlightNo;
    private String hkChinaName;
    private String hkCardCode;
    private String hkCardType;
    private String hkFlightDate;
    private String hkGender;
    private String hkBirthday;
    private String hkPickup;
    private String hkPickupId;
    private String eticketServiceAddr;
    private String isShShip;
    private String o2oSnatchStatus;
    private String market;
    private String etType;
    private String etShopId;
    private String obs;
    private Set<TempPromotionDetail> tempPromotionDetails = new HashSet<TempPromotionDetail>(
            0);
    private Set<TempOrder> tempOrders = new HashSet<TempOrder>(0);

    // Constructors

    /** default constructor */
    public TempTrade() {
    }

    /** minimal constructor */
    public TempTrade(String tid, String storeId) {
        this.tid = tid;
        this.storeId = storeId;
    }

    /** full constructor */
    public TempTrade(String tid, String alipayId, String alipayNo,
                    String sellerNick, Double payment, String receiverName,
                    String receiverState, String receiverAddress, String receiverZip,
                    String receiverMobile, String receiverCity,
                    String receiverDistrict, String status, Double discountFee,
                    Double totalFee, Timestamp created, Timestamp payTime, Timestamp modified,
                    Timestamp endTime, String sellerMemo, String invoiceName,
                    String buyerNick, String storeId, Double pointFee,
                    String shippingType,
                    Set<TempPromotionDetail> tempPromotionDetails,
                    Set<TempOrder> tempOrders) {
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
        this.tempPromotionDetails = tempPromotionDetails;
        this.tempOrders = tempOrders;
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

    @Column(name = "point_fee")
    public Double getPointFee() {
        return this.pointFee;
    }

    public void setPointFee(Double pointFee) {
        this.pointFee = pointFee;
    }

    @Column(name = "BATCH_NO", nullable = false)
    public Integer getBatchNo() {
        return this.batchNo;
    }

    public void setBatchNo(Integer batchNo) {
        this.batchNo = batchNo;
    }

    @Column(name = "SHIPPING_TYPE", length = 200)
    public String getShippingType() {
        return this.shippingType;
    }

    public void setShippingType(String shippingType) {
        this.shippingType = shippingType;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "tempTrade")
    public Set<TempPromotionDetail> getTempPromotionDetails() {
        return this.tempPromotionDetails;
    }

    public void setTempPromotionDetails(
            Set<TempPromotionDetail> tempPromotionDetails) {
        this.tempPromotionDetails = tempPromotionDetails;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "tempTrade")
    public Set<TempOrder> getTempOrders() {
        return this.tempOrders;
    }

    public void setTempOrders(Set<TempOrder> tempOrders) {
        this.tempOrders = tempOrders;
    }

    @Column(name = "cpd_code")
    public String getCpdCode() {
        return this.cpdCode;
    }

    public void setCpdCode(String cpdCode) {
        this.cpdCode = cpdCode;
    }

    @Column(name = "ALIPAY_TIME")
    public String getAlipayCreateTime() {
        return alipayCreateTime;
    }

    public void setAlipayCreateTime(String alipayCreateTime) {
        this.alipayCreateTime = alipayCreateTime;
    }



    @Column(name = "RECEIVER_PHONE", length = 200)
    public String getReceiverPhone() {
        return this.receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
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
    public Double getReceivedPayment() {
        return this.receivedPayment;
    }

    public void setReceivedPayment(Double receivedPayment) {
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

    @Column(name = "CONSIGN_TIME", length = 19)
    public Timestamp getConsignTime() {
        return this.consignTime;
    }

    public void setConsignTime(Timestamp consignTime) {
        this.consignTime = consignTime;
    }

    @Column(name = "EST_CON_TIME", length = 19)
    public Timestamp getEstConTime() {
        return this.estConTime;
    }

    public void setEstConTime(Timestamp estConTime) {
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

    @Column(name = "ORDER_TAX_FEE")
    public Double getOrderTaxFee() {
        return this.orderTaxFee;
    }

    public void setOrderTaxFee(Double orderTaxFee) {
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

    @Column(name = "IS_DAIXIAO", length = 10)
    public String getIsDaixiao() {
        return this.isDaixiao;
    }

    public void setIsDaixiao(String isDaixiao) {
        this.isDaixiao = isDaixiao;
    }

    @Column(name = "STEP_PAID_FEE", precision = 10)
    public Double getStepPaidFee() {
        return this.stepPaidFee;
    }

    public void setStepPaidFee(Double stepPaidFee) {
        this.stepPaidFee = stepPaidFee;
    }

    @Column(name = "STEP_TRADE_STATUS", length = 200)
    public String getStepTradeStatus() {
        return this.stepTradeStatus;
    }

    public void setStepTradeStatus(String stepTradeStatus) {
        this.stepTradeStatus = stepTradeStatus;
    }

    @Column(name = "TID", length = 200)
    public String getTid() {
        return this.tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }



    @Column(name = "SHOP_CODE", length = 200)
    public String getShopCode() {
        return this.shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    @Column(name = "TIMEOUT_ACTION_TIME", length = 19)
    public Timestamp getTimeoutActionTime() {
        return this.timeoutActionTime;
    }

    public void setTimeoutActionTime(Timestamp timeoutActionTime) {
        this.timeoutActionTime = timeoutActionTime;
    }

    @Column(name = "PROMOTION", length = 200)
    public String getPromotion() {
        return this.promotion;
    }

    public void setPromotion(String promotion) {
        this.promotion = promotion;
    }

    @Column(name = "ET_SER_TIME", length = 19)
    public Timestamp getEtSerTime() {
        return this.etSerTime;
    }

    public void setEtSerTime(Timestamp etSerTime) {
        this.etSerTime = etSerTime;
    }

    @Column(name = "ET_SHOP_NAME", length = 200)
    public String getEtShopName() {
        return this.etShopName;
    }

    public void setEtShopName(String etShopName) {
        this.etShopName = etShopName;
    }

    @Column(name = "ET_VERIFIED_SHOP_NAME", length = 200)
    public String getEtVerifiedShopName() {
        return this.etVerifiedShopName;
    }

    public void setEtVerifiedShopName(String etVerifiedShopName) {
        this.etVerifiedShopName = etVerifiedShopName;
    }

    @Column(name = "ET_PLATE_NUMBER", length = 200)
    public String getEtPlateNumber() {
        return this.etPlateNumber;
    }

    public void setEtPlateNumber(String etPlateNumber) {
        this.etPlateNumber = etPlateNumber;
    }

    @Column(name = "PAID_COUPON_FEE", precision = 10)
    public Double getPaidCouponFee() {
        return this.paidCouponFee;
    }

    public void setPaidCouponFee(Double paidCouponFee) {
        this.paidCouponFee = paidCouponFee;
    }

    @Column(name = "SID", length = 200)
    public String getSid() {
        return this.sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    @Column(name = "BUYER_IP", length = 200)
    public String getBuyerIp() {
        return this.buyerIp;
    }

    public void setBuyerIp(String buyerIp) {
        this.buyerIp = buyerIp;
    }

    @Column(name = "HAS_YFX", length = 200)
    public String getHasYfx() {
        return this.hasYfx;
    }

    public void setHasYfx(String hasYfx) {
        this.hasYfx = hasYfx;
    }

    @Column(name = "YFX_FEE", precision = 10)
    public Double getYfxFee() {
        return this.yfxFee;
    }

    public void setYfxFee(Double yfxFee) {
        this.yfxFee = yfxFee;
    }

    @Column(name = "YFX_ID", length = 200)
    public String getYfxId() {
        return this.yfxId;
    }

    public void setYfxId(String yfxId) {
        this.yfxId = yfxId;
    }

    @Column(name = "YFX_TYPE", length = 200)
    public String getYfxType() {
        return this.yfxType;
    }

    public void setYfxType(String yfxType) {
        this.yfxType = yfxType;
    }

    @Column(name = "ETICKET_EXT", length = 200)
    public String getEticketExt() {
        return this.eticketExt;
    }

    public void setEticketExt(String eticketExt) {
        this.eticketExt = eticketExt;
    }

    @Column(name = "SEND_TIME", length = 19)
    public Timestamp getSendTime() {
        return this.sendTime;
    }

    public void setSendTime(Timestamp sendTime) {
        this.sendTime = sendTime;
    }

    @Column(name = "EXPRESS_AGENCY_FEE", precision = 10)
    public Double getExpressAgencyFee() {
        return this.expressAgencyFee;
    }

    public void setExpressAgencyFee(Double expressAgencyFee) {
        this.expressAgencyFee = expressAgencyFee;
    }

    @Column(name = "CAN_RATE", length = 10)
    public String getCanRate() {
        return this.canRate;
    }

    public void setCanRate(String canRate) {
        this.canRate = canRate;
    }

    @Column(name = "ACOOKIE_ID", length = 200)
    public String getAcookieId() {
        return this.acookieId;
    }

    public void setAcookieId(String acookieId) {
        this.acookieId = acookieId;
    }

    @Column(name = "TRADE_MEMO", length = 200)
    public String getTradeMemo() {
        return this.tradeMemo;
    }

    public void setTradeMemo(String tradeMemo) {
        this.tradeMemo = tradeMemo;
    }

    @Column(name = "TRADE_SOURCE", length = 200)
    public String getTradeSource() {
        return this.tradeSource;
    }

    public void setTradeSource(String tradeSource) {
        this.tradeSource = tradeSource;
    }

    @Column(name = "ARRIVE_INTERVAL")
    public Integer getArriveInterval() {
        return this.arriveInterval;
    }

    public void setArriveInterval(Integer arriveInterval) {
        this.arriveInterval = arriveInterval;
    }

    @Column(name = "ARRIVE_CUT_TIME", length = 200)
    public String getArriveCutTime() {
        return this.arriveCutTime;
    }

    public void setArriveCutTime(String arriveCutTime) {
        this.arriveCutTime = arriveCutTime;
    }

    @Column(name = "CONSIGN_INTERVAL")
    public Integer getConsignInterval() {
        return this.consignInterval;
    }

    public void setConsignInterval(Integer consignInterval) {
        this.consignInterval = consignInterval;
    }

    @Column(name = "O2O", length = 200)
    public String getO2o() {
        return this.o2o;
    }

    public void setO2o(String o2o) {
        this.o2o = o2o;
    }

    @Column(name = "O2O_GUIDE_ID", length = 200)
    public String getO2oGuideId() {
        return this.o2oGuideId;
    }

    public void setO2oGuideId(String o2oGuideId) {
        this.o2oGuideId = o2oGuideId;
    }

    @Column(name = "O2O_SHOP_ID", length = 200)
    public String getO2oShopId() {
        return this.o2oShopId;
    }

    public void setO2oShopId(String o2oShopId) {
        this.o2oShopId = o2oShopId;
    }

    @Column(name = "O2O_GUIDE_NAME", length = 200)
    public String getO2oGuideName() {
        return this.o2oGuideName;
    }

    public void setO2oGuideName(String o2oGuideName) {
        this.o2oGuideName = o2oGuideName;
    }

    @Column(name = "O2O_SHOP_NAME", length = 200)
    public String getO2oShopName() {
        return this.o2oShopName;
    }

    public void setO2oShopName(String o2oShopName) {
        this.o2oShopName = o2oShopName;
    }

    @Column(name = "O2O_DELIVERY", length = 200)
    public String getO2oDelivery() {
        return this.o2oDelivery;
    }

    public void setO2oDelivery(String o2oDelivery) {
        this.o2oDelivery = o2oDelivery;
    }

    @Column(name = "ZERO_PURCHASE", length = 10)
    public String getZeroPurchase() {
        return this.zeroPurchase;
    }

    public void setZeroPurchase(String zeroPurchase) {
        this.zeroPurchase = zeroPurchase;
    }

    @Column(name = "O2O_OUT_TRADE_ID", length = 200)
    public String getO2oOutTradeId() {
        return this.o2oOutTradeId;
    }

    public void setO2oOutTradeId(String o2oOutTradeId) {
        this.o2oOutTradeId = o2oOutTradeId;
    }

    @Column(name = "HK_EN_NAME", length = 200)
    public String getHkEnName() {
        return this.hkEnName;
    }

    public void setHkEnName(String hkEnName) {
        this.hkEnName = hkEnName;
    }

    @Column(name = "HK_FLIGHT_NO", length = 200)
    public String getHkFlightNo() {
        return this.hkFlightNo;
    }

    public void setHkFlightNo(String hkFlightNo) {
        this.hkFlightNo = hkFlightNo;
    }

    @Column(name = "HK_CHINA_NAME", length = 200)
    public String getHkChinaName() {
        return this.hkChinaName;
    }

    public void setHkChinaName(String hkChinaName) {
        this.hkChinaName = hkChinaName;
    }

    @Column(name = "HK_CARD_CODE", length = 200)
    public String getHkCardCode() {
        return this.hkCardCode;
    }

    public void setHkCardCode(String hkCardCode) {
        this.hkCardCode = hkCardCode;
    }

    @Column(name = "HK_CARD_TYPE", length = 200)
    public String getHkCardType() {
        return this.hkCardType;
    }

    public void setHkCardType(String hkCardType) {
        this.hkCardType = hkCardType;
    }

    @Column(name = "HK_FLIGHT_DATE", length = 200)
    public String getHkFlightDate() {
        return this.hkFlightDate;
    }

    public void setHkFlightDate(String hkFlightDate) {
        this.hkFlightDate = hkFlightDate;
    }

    @Column(name = "HK_GENDER", length = 200)
    public String getHkGender() {
        return this.hkGender;
    }

    public void setHkGender(String hkGender) {
        this.hkGender = hkGender;
    }

    @Column(name = "HK_BIRTHDAY", length = 200)
    public String getHkBirthday() {
        return this.hkBirthday;
    }

    public void setHkBirthday(String hkBirthday) {
        this.hkBirthday = hkBirthday;
    }

    @Column(name = "HK_PICKUP", length = 200)
    public String getHkPickup() {
        return this.hkPickup;
    }

    public void setHkPickup(String hkPickup) {
        this.hkPickup = hkPickup;
    }

    @Column(name = "HK_PICKUP_ID", length = 200)
    public String getHkPickupId() {
        return this.hkPickupId;
    }

    public void setHkPickupId(String hkPickupId) {
        this.hkPickupId = hkPickupId;
    }

    @Column(name = "ETICKET_SERVICE_ADDR", length = 200)
    public String getEticketServiceAddr() {
        return this.eticketServiceAddr;
    }

    public void setEticketServiceAddr(String eticketServiceAddr) {
        this.eticketServiceAddr = eticketServiceAddr;
    }

    @Column(name = "IS_SH_SHIP", length = 10)
    public String getIsShShip() {
        return this.isShShip;
    }

    public void setIsShShip(String isShShip) {
        this.isShShip = isShShip;
    }

    @Column(name = "O2O_SNATCH_STATUS", length = 10)
    public String getO2oSnatchStatus() {
        return this.o2oSnatchStatus;
    }

    public void setO2oSnatchStatus(String o2oSnatchStatus) {
        this.o2oSnatchStatus = o2oSnatchStatus;
    }

    @Column(name = "MARKET", length = 200)
    public String getMarket() {
        return this.market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    @Column(name = "ET_TYPE", length = 200)
    public String getEtType() {
        return this.etType;
    }

    public void setEtType(String etType) {
        this.etType = etType;
    }

    @Column(name = "ET_SHOP_ID", length = 200)
    public String getEtShopId() {
        return this.etShopId;
    }

    public void setEtShopId(String etShopId) {
        this.etShopId = etShopId;
    }

    @Column(name = "OBS", length = 200)
    public String getObs() {
        return this.obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

}