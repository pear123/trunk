package com.arvato.cc.form.finance;

/**
 * 订单销售报表
 * @author tracy
 * 
 */
public class SaleReport {
//    private String day;
//    private String[] index = new String[]{"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25"};
//    private String[] hours = new String[]{"00:00-00:59","01:00-01:59","02:00-02:59","03:00-03:59","04:00-04:59","05:00-05:59","06:00-06:59",
//            "07:00-07:59","08:00-08:59","09:00-09:59","10:00-10:59","11:00-11:59","12:00-12:59","13:00-13:59","14:00-14:59","15:00-15:59",
//            "16:00-16:59","17:00-17:59","18:00-18:59","19:00-19:59","20:00-20:59","21:00-21:59","22:00-22:59","23:00-23:59","00:00-23:59"};
//    private Integer[] counts = new Integer[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
//    private Double[] amounts = new Double[]{0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00};
//
//    private String hourPoint;
//    private Integer countTotal;
//    private Double amountTotal;
//
//    public String getDay() {
//        return day;
//    }
//
//    public void setDay(String day) {
//        this.day = day;
//    }
//
//    public String[] getHours() {
//        return hours;
//    }
//
//    public void setHours(String[] hours) {
//        this.hours = hours;
//    }
//
//    public Integer[] getCounts() {
//        return counts;
//    }
//
//    public void setCounts(Integer[] counts) {
//        this.counts = counts;
//    }
//
//    public Double[] getAmounts() {
//        return amounts;
//    }
//
//    public void setAmounts(Double[] amounts) {
//        this.amounts = amounts;
//    }
//
//    public String getHourPoint() {
//        return hourPoint;
//    }
//
//    public void setHourPoint(String hourPoint) {
//        this.hourPoint = hourPoint;
//    }
//
//    public Integer getCountTotal() {
//        return countTotal;
//    }
//
//    public void setCountTotal(Integer countTotal) {
//        this.countTotal = countTotal;
//    }
//
//    public Double getAmountTotal() {
//        return amountTotal;
//    }
//
//    public void setAmountTotal(Double amountTotal) {
//        this.amountTotal = amountTotal;
//    }

    private String[] hours = new String[]{"00:00-00:59","01:00-01:59","02:00-02:59","03:00-03:59","04:00-04:59","05:00-05:59","06:00-06:59",
            "07:00-07:59","08:00-08:59","09:00-09:59","10:00-10:59","11:00-11:59","12:00-12:59","13:00-13:59","14:00-14:59","15:00-15:59",
            "16:00-16:59","17:00-17:59","18:00-18:59","19:00-19:59","20:00-20:59","21:00-21:59","22:00-22:59","23:00-23:59","00:00-23:59"};

    private String todayDay;
    private String todayHour;



    private String todayH;
    private Double todaySaleAmount;
    private Integer todaySaleTransaction;

    private String yesterdayDay;
    private String yesterdayHour;
    private Double yesterdaySaleAmount;
    private Integer yesterdaySaleTransaction;

    public void setTodayH(String todayH) {
        this.todayH = todayH;
    }

    public String getTodayH() {
        return todayH;
    }
    public String[] getHours() {
        return hours;
    }

    public String getTodayDay() {
        return todayDay;
    }

    public void setTodayDay(String todayDay) {
        this.todayDay = todayDay;
    }

    public String getTodayHour() {
        return todayHour;
    }

    public void setTodayHour(String todayHour) {
        this.todayHour = todayHour;
    }

    public Double getTodaySaleAmount() {
        return todaySaleAmount;
    }

    public void setTodaySaleAmount(Double todaySaleAmount) {
        this.todaySaleAmount = todaySaleAmount;
    }

    public Integer getTodaySaleTransaction() {
        return todaySaleTransaction;
    }

    public void setTodaySaleTransaction(Integer todaySaleTransaction) {
        this.todaySaleTransaction = todaySaleTransaction;
    }

    public String getYesterdayHour() {
        return yesterdayHour;
    }

    public void setYesterdayHour(String yesterdayHour) {
        this.yesterdayHour = yesterdayHour;
    }

    public Double getYesterdaySaleAmount() {
        return yesterdaySaleAmount;
    }

    public void setYesterdaySaleAmount(Double yesterdaySaleAmount) {
        this.yesterdaySaleAmount = yesterdaySaleAmount;
    }

    public Integer getYesterdaySaleTransaction() {
        return yesterdaySaleTransaction;
    }

    public void setYesterdaySaleTransaction(Integer yesterdaySaleTransaction) {
        this.yesterdaySaleTransaction = yesterdaySaleTransaction;
    }

    public String getYesterdayDay() {
        return yesterdayDay;
    }

    public void setYesterdayDay(String yesterdayDay) {
        this.yesterdayDay = yesterdayDay;
    }
}
