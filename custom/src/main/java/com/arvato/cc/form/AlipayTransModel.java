package com.arvato.cc.form;

import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: caol005
 * Date: 15-9-10
 * Time: 上午10:20
 * To change this template use File | Settings | File Templates.
 */
public class AlipayTransModel {

    private Timestamp createTime;

    private Double inFee;

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Double getInFee() {
        return inFee;
    }

    public void setInFee(Double inFee) {
        this.inFee = inFee;
    }


    public AlipayTransModel(Timestamp createTime, Double inFee) {
        this.createTime = createTime;
        this.inFee = inFee;
    }
}
