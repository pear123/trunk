package com.arvato.platform.jobs.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: caol005
 * Date: 15-8-17
 * Time: 上午11:39
 * To change this template use File | Settings | File Templates.
 */
@XmlRootElement(name = "jdpTradeBean")
@XmlAccessorType(XmlAccessType.FIELD)
public class JdpTradeBean {

    private Integer id;
    private String tid;
    private String status;
    private String type;
    private String sellerNick;
    private String buyerNick;
    private String created;
    private String modified;
    private String jdpCreated;
    private String jdpModified;
    private String jdpHashcode;
    private String jdpResponse;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSellerNick() {
        return sellerNick;
    }

    public void setSellerNick(String sellerNick) {
        this.sellerNick = sellerNick;
    }

    public String getBuyerNick() {
        return buyerNick;
    }

    public void setBuyerNick(String buyerNick) {
        this.buyerNick = buyerNick;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public String getJdpCreated() {
        return jdpCreated;
    }

    public void setJdpCreated(String jdpCreated) {
        this.jdpCreated = jdpCreated;
    }

    public String getJdpModified() {
        return jdpModified;
    }

    public void setJdpModified(String jdpModified) {
        this.jdpModified = jdpModified;
    }

    public String getJdpHashcode() {
        return jdpHashcode;
    }

    public void setJdpHashcode(String jdpHashcode) {
        this.jdpHashcode = jdpHashcode;
    }

    public String getJdpResponse() {
        return jdpResponse;
    }

    public void setJdpResponse(String jdpResponse) {
        this.jdpResponse = jdpResponse;
    }

}
