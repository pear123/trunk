package com.arvato.cc.form;

/**
 * Created with IntelliJ IDEA.
 * User: caol005
 * Date: 15-8-17
 * Time: 下午2:47
 * To change this template use File | Settings | File Templates.
 */
public class SaleStructureSummaryBean {
    private int saleQuantitySummary;
    private double saleAmountSummary;
    private double saleAvgSummary;

    public int getSaleQuantitySummary() {
        return saleQuantitySummary;
    }

    public void setSaleQuantitySummary(int saleQuantitySummary) {
        this.saleQuantitySummary = saleQuantitySummary;
    }

    public double getSaleAmountSummary() {
        return saleAmountSummary;
    }

    public void setSaleAmountSummary(double saleAmountSummary) {
        this.saleAmountSummary = saleAmountSummary;
    }

    public double getSaleAvgSummary() {
        return this.saleAmountSummary / this.saleQuantitySummary ;
    }

    public void setSaleAvgSummary(double saleAvgSummary) {
        this.saleAvgSummary = saleAvgSummary;
    }
}
