package com.arvato.cc.form;

/**
 * Created by Chen380 on 2015/10/21 0021.
 */
public class OrderReportBean {
 private String originalPointFee;
 private String originalCoupon;

 public OrderReportBean() {
 }

 public OrderReportBean(String originalPointFee, String originalCoupon) {
  this.originalPointFee = originalPointFee;
  this.originalCoupon = originalCoupon;
 }

 public String getOriginalPointFee() {
  return originalPointFee;
 }

 public void setOriginalPointFee(String originalPointFee) {
  this.originalPointFee = originalPointFee;
 }

 public String getOriginalCoupon() {
  return originalCoupon;
 }

 public void setOriginalCoupon(String originalCoupon) {
  this.originalCoupon = originalCoupon;
 }
}
