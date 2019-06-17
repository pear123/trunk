package com.arvato.cc.form.finance;

import java.util.Date;

/**
 *  alipay model
 * 
 *  @author tracy
 * 
 */
public class QueryAccountReport {
	private String sellerAccount; // 支付宝账号
	private String iwAccountLogId; // 账务流水号
	private String tradeNo; // 业务流水号
	private String merchantOutOrderNo; // 商户订单号
	private Date transDate; // 发生时间
	private Double income; // 收入金额
	private Double outcome; // 支出金额
	private String transAccount; // 交易渠道
	private String transCodeMsg; // 业务类型，
	private String memo; // 备注 ，
    private String balance; // 账户余额
	private String goodsTitle;//商品信息
	private String buyerAccount;//对方账号

    //Steven 2013年3月13日 添加
    private String contractProduct;//签约产品
    private Double deductionRate;//费率
    private String proxyUploadFileName;//代上传文件名
    private String proxyDeductionType;//代扣款类型

	public String getBuyerAccount() {
		return buyerAccount;
	}

	public void setBuyerAccount(String buyerAccount) {
		this.buyerAccount = buyerAccount;
	}

	public String getGoodsTitle() {
		return goodsTitle;
	}

	public void setGoodsTitle(String goodsTitle) {
		this.goodsTitle = goodsTitle;
	}

	public String getSellerAccount() {
        return sellerAccount;
    }

    public void setSellerAccount(String sellerAccount) {
        this.sellerAccount = sellerAccount;
    }

    public String getIwAccountLogId() {
        return iwAccountLogId;
    }

    public void setIwAccountLogId(String iwAccountLogId) {
        this.iwAccountLogId = iwAccountLogId;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getMerchantOutOrderNo() {
        return merchantOutOrderNo;
    }

    public void setMerchantOutOrderNo(String merchantOutOrderNo) {
        this.merchantOutOrderNo = merchantOutOrderNo;
    }

    public Date getTransDate() {
        return transDate;
    }

    public void setTransDate(Date transDate) {
        this.transDate = transDate;
    }

    public Double getIncome() {
        return income;
    }

    public void setIncome(Double income) {
        this.income = income;
    }

    public Double getOutcome() {
        return outcome;
    }

    public void setOutcome(Double outcome) {
        this.outcome = outcome;
    }

    public String getTransAccount() {
        return transAccount;
    }

    public void setTransAccount(String transAccount) {
        this.transAccount = transAccount;
    }

    public String getTransCodeMsg() {
        return transCodeMsg;
    }

    public void setTransCodeMsg(String transCodeMsg) {
        this.transCodeMsg = transCodeMsg;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getContractProduct() {
        return contractProduct;
    }

    public void setContractProduct(String contractProduct) {
        this.contractProduct = contractProduct;
    }

    public Double getDeductionRate() {
        return deductionRate;
    }

    public void setDeductionRate(Double deductionRate) {
        this.deductionRate = deductionRate;
    }

    public String getProxyUploadFileName() {
        return proxyUploadFileName;
    }

    public void setProxyUploadFileName(String proxyUploadFileName) {
        this.proxyUploadFileName = proxyUploadFileName;
    }

    public String getProxyDeductionType() {
        return proxyDeductionType;
    }

    public void setProxyDeductionType(String proxyDeductionType) {
        this.proxyDeductionType = proxyDeductionType;
    }
}
