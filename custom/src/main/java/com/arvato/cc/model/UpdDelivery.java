package com.arvato.cc.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * UpdDelivery entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "upd_delivery")
public class UpdDelivery implements java.io.Serializable {

	// Fields

	private Integer deliveryid;
	private String deliveryno;
	private String tid;
	private Date createTime;
	private String buyerNick;
	private String sellerNick;
	private String shipperNick;
	private String logisticCompany;
	private String orderid;
	private String orderStauts;
	private String receiverName;
	private String state;
	private String city;
	private String district;
	private String address;
	private String zip;
	private String mobile;
	private String phone;
	private String invoiceKind;
	private String invoiceName;
	private Double invoiceFee;
	private String invoiceContent;
	private String store;
	private Date storeOutTime;
	private String title1;
	private Double price1;
	private String code1;
	private Integer num1;
	private String title2;
	private Double price2;
	private String code2;
	private Integer num2;
	private String title3;
	private Double price3;
	private String code3;
	private String title4;
	private Double price4;
	private String code4;
	private Integer num4;
	private String title5;
	private Double price5;
	private String code5;
	private Integer num5;
	private String title6;
	private Double price6;
	private String code6;
	private Integer num6;
	private String title7;
	private Double price7;
	private String code7;
	private Integer num7;
	private String title8;
	private Double price8;
	private String code8;
	private Integer num8;
	private String title9;
	private Double price9;
	private String code9;
	private Integer num9;
	private String title10;
	private Double price10;
	private String code10;
	private Integer num10;
	private Integer version;

	// Constructors

	/** default constructor */
	public UpdDelivery() {
	}

	/** full constructor */
	public UpdDelivery(String deliveryno, String tid, Date createTime,
			String buyerNick, String sellerNick, String shipperNick,
			String logisticCompany, String orderid, String orderStauts,
			String receiverName, String state, String city, String district,
			String address, String zip, String mobile, String phone,
			String invoiceKind, String invoiceName, Double invoiceFee,
			String invoiceContent, String store, Date storeOutTime,
			String title1, Double price1, String code1, Integer num1,
			String title2, Double price2, String code2, Integer num2,
			String title3, Double price3, String code3, String title4,
			Double price4, String code4, Integer num4, String title5,
			Double price5, String code5, Integer num5, String title6,
			Double price6, String code6, Integer num6, String title7,
			Double price7, String code7, Integer num7, String title8,
			Double price8, String code8, Integer num8, String title9,
			Double price9, String code9, Integer num9, String title10,
			Double price10, String code10, Integer num10, Integer version) {
		this.deliveryno = deliveryno;
		this.tid = tid;
		this.createTime = createTime;
		this.buyerNick = buyerNick;
		this.sellerNick = sellerNick;
		this.shipperNick = shipperNick;
		this.logisticCompany = logisticCompany;
		this.orderid = orderid;
		this.orderStauts = orderStauts;
		this.receiverName = receiverName;
		this.state = state;
		this.city = city;
		this.district = district;
		this.address = address;
		this.zip = zip;
		this.mobile = mobile;
		this.phone = phone;
		this.invoiceKind = invoiceKind;
		this.invoiceName = invoiceName;
		this.invoiceFee = invoiceFee;
		this.invoiceContent = invoiceContent;
		this.store = store;
		this.storeOutTime = storeOutTime;
		this.title1 = title1;
		this.price1 = price1;
		this.code1 = code1;
		this.num1 = num1;
		this.title2 = title2;
		this.price2 = price2;
		this.code2 = code2;
		this.num2 = num2;
		this.title3 = title3;
		this.price3 = price3;
		this.code3 = code3;
		this.title4 = title4;
		this.price4 = price4;
		this.code4 = code4;
		this.num4 = num4;
		this.title5 = title5;
		this.price5 = price5;
		this.code5 = code5;
		this.num5 = num5;
		this.title6 = title6;
		this.price6 = price6;
		this.code6 = code6;
		this.num6 = num6;
		this.title7 = title7;
		this.price7 = price7;
		this.code7 = code7;
		this.num7 = num7;
		this.title8 = title8;
		this.price8 = price8;
		this.code8 = code8;
		this.num8 = num8;
		this.title9 = title9;
		this.price9 = price9;
		this.code9 = code9;
		this.num9 = num9;
		this.title10 = title10;
		this.price10 = price10;
		this.code10 = code10;
		this.num10 = num10;
		this.version = version;
	}

	// Property accessors
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "DELIVERYID", unique = true, nullable = false)
	public Integer getDeliveryid() {
		return this.deliveryid;
	}

	public void setDeliveryid(Integer deliveryid) {
		this.deliveryid = deliveryid;
	}

	@Column(name = "DELIVERYNO", length = 200)
	public String getDeliveryno() {
		return this.deliveryno;
	}

	public void setDeliveryno(String deliveryno) {
		this.deliveryno = deliveryno;
	}

	@Column(name = "TID", length = 200)
	public String getTid() {
		return this.tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "CREATE_TIME", length = 19)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "BUYER_NICK", length = 200)
	public String getBuyerNick() {
		return this.buyerNick;
	}

	public void setBuyerNick(String buyerNick) {
		this.buyerNick = buyerNick;
	}

	@Column(name = "SELLER_NICK", length = 200)
	public String getSellerNick() {
		return this.sellerNick;
	}

	public void setSellerNick(String sellerNick) {
		this.sellerNick = sellerNick;
	}

	@Column(name = "SHIPPER_NICK", length = 200)
	public String getShipperNick() {
		return this.shipperNick;
	}

	public void setShipperNick(String shipperNick) {
		this.shipperNick = shipperNick;
	}

	@Column(name = "LOGISTIC_COMPANY", length = 200)
	public String getLogisticCompany() {
		return this.logisticCompany;
	}

	public void setLogisticCompany(String logisticCompany) {
		this.logisticCompany = logisticCompany;
	}

	@Column(name = "ORDERID", length = 200)
	public String getOrderid() {
		return this.orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	@Column(name = "ORDER_STAUTS", length = 200)
	public String getOrderStauts() {
		return this.orderStauts;
	}

	public void setOrderStauts(String orderStauts) {
		this.orderStauts = orderStauts;
	}

	@Column(name = "RECEIVER_NAME", length = 200)
	public String getReceiverName() {
		return this.receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	@Column(name = "STATE", length = 200)
	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column(name = "CITY", length = 200)
	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "DISTRICT", length = 200)
	public String getDistrict() {
		return this.district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	@Column(name = "ADDRESS", length = 200)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "ZIP", length = 50)
	public String getZip() {
		return this.zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	@Column(name = "MOBILE", length = 50)
	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "PHONE", length = 50)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "INVOICE_KIND", length = 100)
	public String getInvoiceKind() {
		return this.invoiceKind;
	}

	public void setInvoiceKind(String invoiceKind) {
		this.invoiceKind = invoiceKind;
	}

	@Column(name = "INVOICE_NAME", length = 100)
	public String getInvoiceName() {
		return this.invoiceName;
	}

	public void setInvoiceName(String invoiceName) {
		this.invoiceName = invoiceName;
	}

	@Column(name = "INVOICE_FEE", precision = 22, scale = 0)
	public Double getInvoiceFee() {
		return this.invoiceFee;
	}

	public void setInvoiceFee(Double invoiceFee) {
		this.invoiceFee = invoiceFee;
	}

	@Column(name = "INVOICE_CONTENT", length = 200)
	public String getInvoiceContent() {
		return this.invoiceContent;
	}

	public void setInvoiceContent(String invoiceContent) {
		this.invoiceContent = invoiceContent;
	}

	@Column(name = "STORE", length = 100)
	public String getStore() {
		return this.store;
	}

	public void setStore(String store) {
		this.store = store;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "STORE_OUT_TIME", length = 19)
	public Date getStoreOutTime() {
		return this.storeOutTime;
	}

	public void setStoreOutTime(Date storeOutTime) {
		this.storeOutTime = storeOutTime;
	}

	@Column(name = "title1", length = 200)
	public String getTitle1() {
		return this.title1;
	}

	public void setTitle1(String title1) {
		this.title1 = title1;
	}

	@Column(name = "price1", precision = 22, scale = 0)
	public Double getPrice1() {
		return this.price1;
	}

	public void setPrice1(Double price1) {
		this.price1 = price1;
	}

	@Column(name = "code1", length = 200)
	public String getCode1() {
		return this.code1;
	}

	public void setCode1(String code1) {
		this.code1 = code1;
	}

	@Column(name = "num1")
	public Integer getNum1() {
		return this.num1;
	}

	public void setNum1(Integer num1) {
		this.num1 = num1;
	}

	@Column(name = "title2", length = 200)
	public String getTitle2() {
		return this.title2;
	}

	public void setTitle2(String title2) {
		this.title2 = title2;
	}

	@Column(name = "price2", precision = 22, scale = 0)
	public Double getPrice2() {
		return this.price2;
	}

	public void setPrice2(Double price2) {
		this.price2 = price2;
	}

	@Column(name = "code2", length = 200)
	public String getCode2() {
		return this.code2;
	}

	public void setCode2(String code2) {
		this.code2 = code2;
	}

	@Column(name = "num2")
	public Integer getNum2() {
		return this.num2;
	}

	public void setNum2(Integer num2) {
		this.num2 = num2;
	}

	@Column(name = "title3", length = 200)
	public String getTitle3() {
		return this.title3;
	}

	public void setTitle3(String title3) {
		this.title3 = title3;
	}

	@Column(name = "price3", precision = 22, scale = 0)
	public Double getPrice3() {
		return this.price3;
	}

	public void setPrice3(Double price3) {
		this.price3 = price3;
	}

	@Column(name = "code3", length = 200)
	public String getCode3() {
		return this.code3;
	}

	public void setCode3(String code3) {
		this.code3 = code3;
	}

	@Column(name = "title4", length = 200)
	public String getTitle4() {
		return this.title4;
	}

	public void setTitle4(String title4) {
		this.title4 = title4;
	}

	@Column(name = "price4", precision = 22, scale = 0)
	public Double getPrice4() {
		return this.price4;
	}

	public void setPrice4(Double price4) {
		this.price4 = price4;
	}

	@Column(name = "code4", length = 200)
	public String getCode4() {
		return this.code4;
	}

	public void setCode4(String code4) {
		this.code4 = code4;
	}

	@Column(name = "num4")
	public Integer getNum4() {
		return this.num4;
	}

	public void setNum4(Integer num4) {
		this.num4 = num4;
	}

	@Column(name = "title5", length = 200)
	public String getTitle5() {
		return this.title5;
	}

	public void setTitle5(String title5) {
		this.title5 = title5;
	}

	@Column(name = "price5", precision = 22, scale = 0)
	public Double getPrice5() {
		return this.price5;
	}

	public void setPrice5(Double price5) {
		this.price5 = price5;
	}

	@Column(name = "code5", length = 200)
	public String getCode5() {
		return this.code5;
	}

	public void setCode5(String code5) {
		this.code5 = code5;
	}

	@Column(name = "num5")
	public Integer getNum5() {
		return this.num5;
	}

	public void setNum5(Integer num5) {
		this.num5 = num5;
	}

	@Column(name = "title6", length = 200)
	public String getTitle6() {
		return this.title6;
	}

	public void setTitle6(String title6) {
		this.title6 = title6;
	}

	@Column(name = "price6", precision = 22, scale = 0)
	public Double getPrice6() {
		return this.price6;
	}

	public void setPrice6(Double price6) {
		this.price6 = price6;
	}

	@Column(name = "code6", length = 200)
	public String getCode6() {
		return this.code6;
	}

	public void setCode6(String code6) {
		this.code6 = code6;
	}

	@Column(name = "num6")
	public Integer getNum6() {
		return this.num6;
	}

	public void setNum6(Integer num6) {
		this.num6 = num6;
	}

	@Column(name = "title7", length = 200)
	public String getTitle7() {
		return this.title7;
	}

	public void setTitle7(String title7) {
		this.title7 = title7;
	}

	@Column(name = "price7", precision = 22, scale = 0)
	public Double getPrice7() {
		return this.price7;
	}

	public void setPrice7(Double price7) {
		this.price7 = price7;
	}

	@Column(name = "code7", length = 200)
	public String getCode7() {
		return this.code7;
	}

	public void setCode7(String code7) {
		this.code7 = code7;
	}

	@Column(name = "num7")
	public Integer getNum7() {
		return this.num7;
	}

	public void setNum7(Integer num7) {
		this.num7 = num7;
	}

	@Column(name = "title8", length = 200)
	public String getTitle8() {
		return this.title8;
	}

	public void setTitle8(String title8) {
		this.title8 = title8;
	}

	@Column(name = "price8", precision = 22, scale = 0)
	public Double getPrice8() {
		return this.price8;
	}

	public void setPrice8(Double price8) {
		this.price8 = price8;
	}

	@Column(name = "code8", length = 200)
	public String getCode8() {
		return this.code8;
	}

	public void setCode8(String code8) {
		this.code8 = code8;
	}

	@Column(name = "num8")
	public Integer getNum8() {
		return this.num8;
	}

	public void setNum8(Integer num8) {
		this.num8 = num8;
	}

	@Column(name = "title9", length = 200)
	public String getTitle9() {
		return this.title9;
	}

	public void setTitle9(String title9) {
		this.title9 = title9;
	}

	@Column(name = "price9", precision = 22, scale = 0)
	public Double getPrice9() {
		return this.price9;
	}

	public void setPrice9(Double price9) {
		this.price9 = price9;
	}

	@Column(name = "code9", length = 200)
	public String getCode9() {
		return this.code9;
	}

	public void setCode9(String code9) {
		this.code9 = code9;
	}

	@Column(name = "num9")
	public Integer getNum9() {
		return this.num9;
	}

	public void setNum9(Integer num9) {
		this.num9 = num9;
	}

	@Column(name = "title10", length = 200)
	public String getTitle10() {
		return this.title10;
	}

	public void setTitle10(String title10) {
		this.title10 = title10;
	}

	@Column(name = "price10", precision = 22, scale = 0)
	public Double getPrice10() {
		return this.price10;
	}

	public void setPrice10(Double price10) {
		this.price10 = price10;
	}

	@Column(name = "code10", length = 200)
	public String getCode10() {
		return this.code10;
	}

	public void setCode10(String code10) {
		this.code10 = code10;
	}

	@Column(name = "num10")
	public Integer getNum10() {
		return this.num10;
	}

	public void setNum10(Integer num10) {
		this.num10 = num10;
	}

	@Column(name = "version")
    @Version
	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

}