package com.arvato.cc.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * ExpFinacial entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "exp_finacial")
public class ExpFinacial implements java.io.Serializable {

	// Fields

	private Integer id;
	private String variant;
	private String description;
	private Timestamp bldat;
	private Timestamp budat;
	private String bkpf;
	private String monat;
	private String bukrs;
	private String waers;
	private String newbs;
	private String newko;
	private Double wrbtr;
	private String zuonr;
	private String sgtxt;
	private String newbs1;
	private String newko1;
	private String name1;
	private String ort01;
	private Double wrbtr1;
	private String zuonr1;
	private String sgtxt1;
	private String newbs2;
	private String newko2;
	private Double wrbtr2;
	private String zuonr2;
	private String sgtxt2;
	private Integer version;
	private String storeId;
    private String orderStatus;
	private Timestamp alipayTime;
    private Timestamp pricingDate;//订单的创建时间
    private String tid;//订单号
    private Timestamp createTime; //支付宝到账时间
    private Double pointFee;
    private String invoiceTitle;
    private String alipayNo;
    private Timestamp endTime;

	// Constructors

	/** default constructor */
	public ExpFinacial() {
	}

	/** full constructor */
	public ExpFinacial(String variant, String description, Timestamp bldat,
                       Timestamp budat, String bkpf, String monat, String bukrs, String waers,
			String newbs, String newko, Double wrbtr, String zuonr,
			String sgtxt, String newbs1, String newko1, String name1,
			String ort01, Double wrbtr1, String zuonr1, String sgtxt1,
			String newbs2, String newko2, Double wrbtr2, String zuonr2,
			String sgtxt2, Integer version, String storeId, String orderStatus,
            Timestamp alipayTime,Timestamp pricingDate,String tid) {
		this.variant = variant;
		this.description = description;
		this.bldat = bldat;
		this.budat = budat;
		this.bkpf = bkpf;
		this.monat = monat;
		this.bukrs = bukrs;
		this.waers = waers;
		this.newbs = newbs;
		this.newko = newko;
		this.wrbtr = wrbtr;
		this.zuonr = zuonr;
		this.sgtxt = sgtxt;
		this.newbs1 = newbs1;
		this.newko1 = newko1;
		this.name1 = name1;
		this.ort01 = ort01;
		this.wrbtr1 = wrbtr1;
		this.zuonr1 = zuonr1;
		this.sgtxt1 = sgtxt1;
		this.newbs2 = newbs2;
		this.newko2 = newko2;
		this.wrbtr2 = wrbtr2;
		this.zuonr2 = zuonr2;
		this.sgtxt2 = sgtxt2;
		this.version = version;
		this.storeId = storeId;
		this.orderStatus = orderStatus;
		this.alipayTime = alipayTime;
        this.pricingDate =pricingDate;
        this.tid = tid;
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

	@Column(name = "VARIANT", length = 100)
	public String getVariant() {
		return this.variant;
	}

	public void setVariant(String variant) {
		this.variant = variant;
	}

	@Column(name = "DESCRIPTION", length = 100)
	public String getDescription() {
		return this.description;
	}

    @Column(name = "INVOICE_TITLE", length = 200)
    public String getInvoiceTitle() {
        return invoiceTitle;
    }

    public void setInvoiceTitle(String invoiceTitle) {
        this.invoiceTitle = invoiceTitle;
    }

    public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "BLDAT", length = 19)
	public Timestamp getBldat() {
		return this.bldat;
	}

	public void setBldat(Timestamp bldat) {
		this.bldat = bldat;
	}

	@Column(name = "BUDAT", length = 19)
	public Timestamp getBudat() {
		return this.budat;
	}

	public void setBudat(Timestamp budat) {
		this.budat = budat;
	}

	@Column(name = "BKPF", length = 20)
	public String getBkpf() {
		return this.bkpf;
	}

	public void setBkpf(String bkpf) {
		this.bkpf = bkpf;
	}

	@Column(name = "MONAT", length = 20)
	public String getMonat() {
		return this.monat;
	}

	public void setMonat(String monat) {
		this.monat = monat;
	}

	@Column(name = "BUKRS", length = 20)
	public String getBukrs() {
		return this.bukrs;
	}

	public void setBukrs(String bukrs) {
		this.bukrs = bukrs;
	}

	@Column(name = "WAERS", length = 20)
	public String getWaers() {
		return this.waers;
	}

	public void setWaers(String waers) {
		this.waers = waers;
	}

	@Column(name = "NEWBS", length = 20)
	public String getNewbs() {
		return this.newbs;
	}

	public void setNewbs(String newbs) {
		this.newbs = newbs;
	}

	@Column(name = "NEWKO", length = 50)
	public String getNewko() {
		return this.newko;
	}

	public void setNewko(String newko) {
		this.newko = newko;
	}

	@Column(name = "WRBTR", precision = 22, scale = 0)
	public Double getWrbtr() {
		return this.wrbtr;
	}

	public void setWrbtr(Double wrbtr) {
		this.wrbtr = wrbtr;
	}

	@Column(name = "ZUONR", length = 100)
	public String getZuonr() {
		return this.zuonr;
	}

	public void setZuonr(String zuonr) {
		this.zuonr = zuonr;
	}

	@Column(name = "SGTXT", length = 100)
	public String getSgtxt() {
		return this.sgtxt;
	}

	public void setSgtxt(String sgtxt) {
		this.sgtxt = sgtxt;
	}

	@Column(name = "NEWBS1", length = 200)
	public String getNewbs1() {
		return this.newbs1;
	}

	public void setNewbs1(String newbs1) {
		this.newbs1 = newbs1;
	}

	@Column(name = "NEWKO1", length = 20)
	public String getNewko1() {
		return this.newko1;
	}

	public void setNewko1(String newko1) {
		this.newko1 = newko1;
	}

	@Column(name = "NAME1", length = 20)
	public String getName1() {
		return this.name1;
	}

	public void setName1(String name1) {
		this.name1 = name1;
	}

	@Column(name = "ORT01", length = 50)
	public String getOrt01() {
		return this.ort01;
	}

	public void setOrt01(String ort01) {
		this.ort01 = ort01;
	}

	@Column(name = "WRBTR1", precision = 22, scale = 0)
	public Double getWrbtr1() {
		return this.wrbtr1;
	}

	public void setWrbtr1(Double wrbtr1) {
		this.wrbtr1 = wrbtr1;
	}

	@Column(name = "ZUONR1", length = 100)
	public String getZuonr1() {
		return this.zuonr1;
	}

	public void setZuonr1(String zuonr1) {
		this.zuonr1 = zuonr1;
	}

	@Column(name = "SGTXT1", length = 100)
	public String getSgtxt1() {
		return this.sgtxt1;
	}

	public void setSgtxt1(String sgtxt1) {
		this.sgtxt1 = sgtxt1;
	}

	@Column(name = "NEWBS2", length = 200)
	public String getNewbs2() {
		return this.newbs2;
	}

	public void setNewbs2(String newbs2) {
		this.newbs2 = newbs2;
	}

	@Column(name = "NEWKO2", length = 20)
	public String getNewko2() {
		return this.newko2;
	}

	public void setNewko2(String newko2) {
		this.newko2 = newko2;
	}

	@Column(name = "WRBTR2", precision = 22, scale = 0)
	public Double getWrbtr2() {
		return this.wrbtr2;
	}

	public void setWrbtr2(Double wrbtr2) {
		this.wrbtr2 = wrbtr2;
	}

	@Column(name = "ZUONR2", length = 100)
	public String getZuonr2() {
		return this.zuonr2;
	}

	public void setZuonr2(String zuonr2) {
		this.zuonr2 = zuonr2;
	}

	@Column(name = "SGTXT2", length = 300)
	public String getSgtxt2() {
		return this.sgtxt2;
	}

	public void setSgtxt2(String sgtxt2) {
		this.sgtxt2 = sgtxt2;
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

	@Column(name = "ALIPAY_TIME", length = 19)
	public Timestamp getAlipayTime() {
		return this.alipayTime;
	}

	public void setAlipayTime(Timestamp alipayTime) {
		this.alipayTime = alipayTime;
	}


    @Column(name = "PRICINGDATE", length = 19)
    public Timestamp getPricingDate() {
        return pricingDate;
    }

    public void setPricingDate(Timestamp pricingDate) {
        this.pricingDate = pricingDate;
    }

    @Column(name = "TID", length = 100)
    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    @Column(name = "CREATE_TIME", length = 19)
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Column(name = "point_fee", nullable = false, precision = 22, scale = 0)
    public Double getPointFee() {
        return this.pointFee;
    }

    public void setPointFee(Double pointFee) {
        this.pointFee = pointFee;
    }


    @Column(name = "ALIPAY_NO", length = 100)
    public String getAlipayNo() {
        return this.alipayNo;
    }

    public void setAlipayNo(String alipayNo) {
        this.alipayNo = alipayNo;
    }

    @Column(name = "end_time", length = 19)
    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }
}