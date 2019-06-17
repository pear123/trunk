package com.arvato.cc.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * ExpAlipay entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "exp_alipay")
public class ExpAlipay implements java.io.Serializable {

	// Fields

	private Integer id;
	private String accountSerialId;
	private String oderId;
	private String goodsName;
	private Date createTime;
	private String accountName;
	private Double inFee;
	private Double outFee;
	private Double balance;
	private String channel;
	private String type;
	private String memo;
	private Integer version;
	private String storeId;

	// Constructors

	/** default constructor */
	public ExpAlipay() {
	}

	/** full constructor */
	public ExpAlipay(String accountSerialId, String oderId, String goodsName,
			Date createTime, String accountName, Double inFee, Double outFee,
			Double balance, String channel, String type, String memo,
			Integer version, String storeId) {
		this.accountSerialId = accountSerialId;
		this.oderId = oderId;
		this.goodsName = goodsName;
		this.createTime = createTime;
		this.accountName = accountName;
		this.inFee = inFee;
		this.outFee = outFee;
		this.balance = balance;
		this.channel = channel;
		this.type = type;
		this.memo = memo;
		this.version = version;
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

	@Column(name = "ACCOUNT_SERIAL_ID", length = 200)
	public String getAccountSerialId() {
		return this.accountSerialId;
	}

	public void setAccountSerialId(String accountSerialId) {
		this.accountSerialId = accountSerialId;
	}

	@Column(name = "ODER_ID", length = 200)
	public String getOderId() {
		return this.oderId;
	}

	public void setOderId(String oderId) {
		this.oderId = oderId;
	}

	@Column(name = "GOODS_NAME", length = 100)
	public String getGoodsName() {
		return this.goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "CREATE_TIME", length = 19)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "ACCOUNT_NAME", length = 100)
	public String getAccountName() {
		return this.accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	@Column(name = "IN_FEE", precision = 22, scale = 0)
	public Double getInFee() {
		return this.inFee;
	}

	public void setInFee(Double inFee) {
		this.inFee = inFee;
	}

	@Column(name = "OUT_FEE", precision = 22, scale = 0)
	public Double getOutFee() {
		return this.outFee;
	}

	public void setOutFee(Double outFee) {
		this.outFee = outFee;
	}

	@Column(name = "BALANCE", precision = 22, scale = 0)
	public Double getBalance() {
		return this.balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	@Column(name = "CHANNEL", length = 100)
	public String getChannel() {
		return this.channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	@Column(name = "TYPE", length = 50)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "MEMO", length = 500)
	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
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

}