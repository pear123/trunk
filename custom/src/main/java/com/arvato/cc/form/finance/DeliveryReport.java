package com.arvato.cc.form.finance;

import java.security.PrivateKey;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

/**
 * Created with IntelliJ IDEA.
 * User: ZHAN405
 * Date: 15-8-19
 * Time: 下午12:44
 * To change this template use File | Settings | File Templates.
 */
public class DeliveryReport {

    private static Double CSRate = 0.007;

    protected SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    protected DecimalFormat df = new DecimalFormat("##.00");

    private String orderId; //订单号
    private String receiverAddress;//收方地址
    private String receiverMobile;//收方联系电话
    private String receiverName;//收方联系人
    private String receiverCompany; //收方公司
    private String receiverPhone; //收方手机号码
    private String presentNames;
    private String buyerNick;
    private String sellerMemo;
    private String tradeAddress;
    private String tradeMobile;
    private String tradeName;
    private String storeCode;

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    private String storeId;

    public String getBuyerNick() {
        return buyerNick;
    }

    public void setBuyerNick(String buyerNick) {
        this.buyerNick = buyerNick;
    }

    public String getPresentNames() {
        return presentNames;
    }

    public void setPresentNames(String presentNames) {
        this.presentNames = presentNames;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public String getReceiverMobile() {
        return receiverMobile;
    }

    public void setReceiverMobile(String receiverMobile) {
        this.receiverMobile = receiverMobile;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverCompany() {
        return receiverCompany;
    }

    public void setReceiverCompany(String receiverCompany) {
        this.receiverCompany = receiverCompany;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    public String getSendContent() {
        return sendContent;
    }

    public void setSendContent(String sendContent) {
        this.sendContent = sendContent;
    }

    public String getSendNum() {
        return sendNum;
    }

    public void setSendNum(String sendNum) {
        this.sendNum = sendNum;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getInvoiceTitle() {
        return invoiceTitle;
    }

    public void setInvoiceTitle(String invoiceTitle) {
        this.invoiceTitle = invoiceTitle;
    }

    public String getInvoiceFee() {
        return invoiceFee;
    }

    public void setInvoiceFee(String invoiceFee) {
        this.invoiceFee = invoiceFee;
    }

    public String getSkuNos() {
        return skuNos;
    }

    public void setSkuNos(String skuNos) {
        this.skuNos = skuNos;
    }

    public String getGiftNum() {
        return giftNum;
    }

    public void setGiftNum(String giftNum) {
        this.giftNum = giftNum;
    }


    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    private String sendContent;//托寄物内容
    private String sendNum; //托寄物数量
    private String customerID;//客户ID
    private String invoiceTitle;//发票抬头
    private String invoiceFee;//开票金额
    private String skuNos;//礼品物料号
    private String giftNum;//礼品数量
    private String invoiceNo;//发票号
    private String originalSendContent;//托寄物内容

    public String getOriginalSendContent() {
        return originalSendContent;
    }

    public void setOriginalSendContent(String originalSendContent) {
        this.originalSendContent = originalSendContent;
    }

    public String getSellerMemo() {
        return sellerMemo;
    }

    public void setSellerMemo(String sellerMemo) {
        this.sellerMemo = sellerMemo;
    }

    public String getTradeAddress() {
        return tradeAddress;
    }

    public void setTradeAddress(String tradeAddress) {
        this.tradeAddress = tradeAddress;
    }

    public String getTradeMobile() {
        return tradeMobile;
    }

    public void setTradeMobile(String tradeMobile) {
        this.tradeMobile = tradeMobile;
    }

    public String getTradeName() {
        return tradeName;
    }

    public void setTradeName(String tradeName) {
        this.tradeName = tradeName;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }
}
