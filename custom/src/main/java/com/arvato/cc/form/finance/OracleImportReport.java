package com.arvato.cc.form.finance;

/**
 * oracle报表
 *
 * @author LIUA005
 */
public class OracleImportReport {
    private String loadText = ""; //加载
    private String reportType = "1ADJUSTMENT"; // 类别
    private String resource = "电子表格"; // 来源
    private String cny = "CNY"; // 币种
    private String saleDate; // 会计日期
    private String company = "7258"; // Company
    private String naturalAccount; // Natural Account
    private String costCenter = "238S"; // Cost Center
    private String productService = "000"; // Product/Service
    private String salesChannel = "00"; // Sales Channel
    private String country = "CN0"; // Country
    private String interCompany = "0000"; // Inter-Company
    private String project = "00"; // Project
    private String spare = "0000"; // Spare
    private String debitItem; // 借项
    private String creditItem; // 贷项
    private String batchName; // 批名
    private String batchDesc; // 批说明
    private String accountName; // 日记帐名称
    private String rowDesc; // 行说明
    private String info = ""; // 信息

    public String getLoadText() {
        return loadText;
    }

    public void setLoadText(String loadText) {
        this.loadText = loadText;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getCny() {
        return cny;
    }

    public void setCny(String cny) {
        this.cny = cny;
    }

    public String getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(String saleDate) {
        this.saleDate = saleDate;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getNaturalAccount() {
        return naturalAccount;
    }

    public void setNaturalAccount(String naturalAccount) {
        this.naturalAccount = naturalAccount;
    }

    public String getCostCenter() {
        return costCenter;
    }

    public void setCostCenter(String costCenter) {
        this.costCenter = costCenter;
    }

    public String getProductService() {
        return productService;
    }

    public void setProductService(String productService) {
        this.productService = productService;
    }

    public String getSalesChannel() {
        return salesChannel;
    }

    public void setSalesChannel(String salesChannel) {
        this.salesChannel = salesChannel;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getInterCompany() {
        return interCompany;
    }

    public void setInterCompany(String interCompany) {
        this.interCompany = interCompany;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getSpare() {
        return spare;
    }

    public void setSpare(String spare) {
        this.spare = spare;
    }

    public String getDebitItem() {
        return debitItem;
    }

    public void setDebitItem(String debitItem) {
        this.debitItem = debitItem;
    }

    public String getCreditItem() {
        return creditItem;
    }

    public void setCreditItem(String creditItem) {
        this.creditItem = creditItem;
    }

    public String getBatchName() {
        return batchName;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }

    public String getBatchDesc() {
        return batchDesc;
    }

    public void setBatchDesc(String batchDesc) {
        this.batchDesc = batchDesc;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getRowDesc() {
        return rowDesc;
    }

    public void setRowDesc(String rowDesc) {
        this.rowDesc = rowDesc;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
