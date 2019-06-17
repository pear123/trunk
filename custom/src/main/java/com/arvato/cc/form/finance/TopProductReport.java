package com.arvato.cc.form.finance;

import java.text.DecimalFormat;

/**
 * 热销商品报表
 * @author tracy
 * 
 */
public class TopProductReport {
	protected DecimalFormat df = new DecimalFormat("##.00");

	private String sku;
    private String name;
    private Integer quantity;
    private Double price;


    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
