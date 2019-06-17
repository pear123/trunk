package com.arvato.cc.form.finance;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-6-4
 * Time: 下午1:31
 * To change this template use File | Settings | File Templates.
 */
public class PeriodShowBean implements Comparable {

    private String omsPeriodSysId;
    private String staicMonth;//
    private String accountType;//
    private String startDate;  //
    private String endDate;//

    public String getOmsPeriodSysId() {
        return omsPeriodSysId;
    }

    public void setOmsPeriodSysId(String omsPeriodSysId) {
        this.omsPeriodSysId = omsPeriodSysId;
    }

    public String getStaicMonth() {
        return staicMonth;
    }

    public void setStaicMonth(String staicMonth) {
        this.staicMonth = staicMonth;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int compareTo(Object o) {
        if(this.staicMonth==null)
        {
            return 0;
        }else if(o==null)
        {
            return 0;
        }else if(o instanceof  PeriodShowBean)
        {
            PeriodShowBean period =(PeriodShowBean) o;
            if(period.getStaicMonth() ==null)
            {
                return 1;
            }else
            {
                return this.staicMonth.compareTo(period.getStaicMonth())   ;
            }
        }else
        {
            return 0;
        }
    }
}
