package com.arvato.cc.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: XUSO002
 * Date: 15-8-6
 * Time: 下午5:08
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "gift_data")
public class GiftData {
    private Integer skuId;
    private String  brand;
    private String  skuNo;
    private String  name;
    private String  memo;
    private Integer version;
    private String  status;

    public GiftData() {
    }

    public GiftData(String brand, String skuNo, String name, String memo, Integer version, String status) {
        this.brand = brand;
        this.skuNo = skuNo;
        this.name = name;
        this.memo = memo;
        this.version = version;
        this.status = status;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "skuid", unique = true, nullable = false)
    public Integer getSkuId() {
        return skuId;
    }

    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }
    @Column(name = "brand",length = 200)
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
    @Column(name = "skuno",length = 200)
    public String getSkuNo() {
        return skuNo;
    }

    public void setSkuNo(String skuNo) {
        this.skuNo = skuNo;
    }
    @Column(name="name",length = 200)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
     @Column(name="memo",length = 500)
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
    @Column(name = "VERSION")
    @Version
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
     @Column(name="STATUS",length = 100)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}