package com.arvato.cc.form.finance;

import java.sql.Timestamp;

/**
 *  alipay model
 * 
 *  @author tracy
 * 
 */
public class PromotionReport {
	private String promotionID; // 促销ID
	private String promotionName; // 促销活动名称
	private Timestamp promotionStartTime; // 促销开始时间
	private Timestamp promotionEndTime; // 促销结束时间
	private String productCode; // 商品编码
	private String productName; // 商品名称
	private Double productPrice; // 商品原价
	private String apportionment;//分摊规则

	public String getApportionment() {
		return apportionment;
	}

	public void setApportionment(String apportionment) {
		this.apportionment = apportionment;
	}

	public String getPromotionID() {
        return promotionID;
    }

    public void setPromotionID(String promotionID) {
        this.promotionID = promotionID;
    }

    public String getPromotionName() {
        return promotionName;
    }

    public void setPromotionName(String promotionName) {
        this.promotionName = promotionName;
    }

    public Timestamp getPromotionStartTime() {
        return promotionStartTime;
    }

    public void setPromotionStartTime(Timestamp promotionStartTime) {
        this.promotionStartTime = promotionStartTime;
    }

    public Timestamp getPromotionEndTime() {
        return promotionEndTime;
    }

    public void setPromotionEndTime(Timestamp promotionEndTime) {
        this.promotionEndTime = promotionEndTime;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }
}
