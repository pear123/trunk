package com.arvato.cc.model;

import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * Alipay entity.
 *
 * @author MyEclipse Persistence Tools
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Table(name = "alipay")
public class Alipay implements java.io.Serializable {

    // Fields

    private Integer id;
    private Integer version;
    private String financialSerialNum;
    private String serviceSerialNum;
    private String orderNum;
    private String goodsName;
    private Timestamp createTime;
    private String account;
    private Double inFee;
    private Double outFee;
    private Double balance;
    private String mode;
    private String type;
    private String memo;
    private Integer tradeType;
    private String storeId;

    // Constructors

    /** default constructor */
    public Alipay() {
    }

    /** minimal constructor */
    public Alipay(String serviceSerialNum) {
        this.serviceSerialNum = serviceSerialNum;
    }

    /** full constructor */
    public Alipay(String financialSerialNum, String serviceSerialNum,
                  String orderNum, String goodsName, Timestamp createTime, String account,
                  Double inFee, Double outFee, Double balance, String mode,
                  String type, String memo, Integer tradeType, String storeId) {
        this.financialSerialNum = financialSerialNum;
        this.serviceSerialNum = serviceSerialNum;
        this.orderNum = orderNum;
        this.goodsName = goodsName;
        this.createTime = createTime;
        this.account = account;
        this.inFee = inFee;
        this.outFee = outFee;
        this.balance = balance;
        this.mode = mode;
        this.type = type;
        this.memo = memo;
        this.tradeType = tradeType;
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

    @Column(name = "FINANCIAL_SERIAL_NUM", length = 100)
    public String getFinancialSerialNum() {
        return this.financialSerialNum;
    }

    public void setFinancialSerialNum(String financialSerialNum) {
        this.financialSerialNum = financialSerialNum;
    }

    @Column(name = "SERVICE_SERIAL_NUM", nullable = false, length = 100)
    public String getServiceSerialNum() {
        return this.serviceSerialNum;
    }

    public void setServiceSerialNum(String serviceSerialNum) {
        this.serviceSerialNum = serviceSerialNum;
    }

    @Column(name = "ORDER_NUM", length = 100)
    public String getOrderNum() {
        return this.orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    @Column(name = "GOODS_NAME", length = 500)
    public String getGoodsName() {
        return this.goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    @Column(name = "CREATE_TIME", length = 19)
    public Timestamp getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Column(name = "ACCOUNT", length = 100)
    public String getAccount() {
        return this.account;
    }

    public void setAccount(String account) {
        this.account = account;
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

    @Column(name = "MODE", length = 100)
    public String getMode() {
        return this.mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    @Column(name = "TYPE", length = 100)
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

    @Column(name = "TRADE_TYPE")
    public Integer getTradeType() {
        return this.tradeType;
    }

    public void setTradeType(Integer tradeType) {
        this.tradeType = tradeType;
    }

    @Column(name = "STORE_ID", length = 100)
    public String getStoreId() {
        return this.storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

}