package com.arvato.cc.model;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * JdpTrade entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "JDP_REFUND")
public class JdpRefund implements java.io.Serializable {

	// Fields

	private Integer id;
	private String refundId;
	private String oid;
	private String tid;
	private String status;
	private String sellerNick;
	private String buyerNick;
	private Timestamp created;
	private Timestamp modified;
	private Timestamp jdpCreated;
	private Timestamp jdpModified;
	private String jdpHashcode;
	private String jdpResponse;
    private Integer batchNo;

	// Constructors

	/** default constructor */
	public JdpRefund() {
	}

	/** minimal constructor */
	public JdpRefund(String tid, String status) {
		this.tid = tid;
		this.status = status;
	}

	/** full constructor */
	public JdpRefund(String tid, String status, String sellerNick,
                     String buyerNick, Timestamp created, Timestamp modified, Timestamp jdpCreated,
                     Timestamp jdpModified, String jdpHashcode, String jdpResponse) {
		this.tid = tid;
		this.status = status;
		this.sellerNick = sellerNick;
		this.buyerNick = buyerNick;
		this.created = created;
		this.modified = modified;
		this.jdpCreated = jdpCreated;
		this.jdpModified = jdpModified;
		this.jdpHashcode = jdpHashcode;
		this.jdpResponse = jdpResponse;
	}

	// Property accessors
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "TID", nullable = false, length = 100)
	public String getTid() {
		return this.tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	@Column(name = "STATUS", nullable = false, length = 100)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "oid", length = 100)
	public String getOid() {
		return this.oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	@Column(name = "SELLER_NICK", length = 100)
	public String getSellerNick() {
		return this.sellerNick;
	}

	public void setSellerNick(String sellerNick) {
		this.sellerNick = sellerNick;
	}

	@Column(name = "BUYER_NICK", length = 100)
	public String getBuyerNick() {
		return this.buyerNick;
	}

	public void setBuyerNick(String buyerNick) {
		this.buyerNick = buyerNick;
	}

	@Column(name = "CREATED", length = 19)
	public Timestamp getCreated() {
		return this.created;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}

	@Column(name = "MODIFIED", length = 19)
	public Timestamp getModified() {
		return this.modified;
	}

	public void setModified(Timestamp modified) {
		this.modified = modified;
	}

	@Column(name = "JDP_CREATED", length = 19)
	public Timestamp getJdpCreated() {
		return this.jdpCreated;
	}

	public void setJdpCreated(Timestamp jdpCreated) {
		this.jdpCreated = jdpCreated;
	}

	@Column(name = "JDP_MODIFIED", length = 19)
	public Timestamp getJdpModified() {
		return this.jdpModified;
	}

	public void setJdpModified(Timestamp jdpModified) {
		this.jdpModified = jdpModified;
	}

	@Column(name = "JDP_HASHCODE", length = 300)
	public String getJdpHashcode() {
		return this.jdpHashcode;
	}

	public void setJdpHashcode(String jdpHashcode) {
		this.jdpHashcode = jdpHashcode;
	}

	@Column(name = "JDP_RESPONSE", length = 65535)
	public String getJdpResponse() {
		return this.jdpResponse;
	}

	public void setJdpResponse(String jdpResponse) {
		this.jdpResponse = jdpResponse;
	}

    @Column(name = "batch_no")
    public Integer getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(Integer batchNo) {
        this.batchNo = batchNo;
    }

    @Column(name = "REFUND_ID", length = 100)
    public String getRefundId() {
        return this.sellerNick;
    }

    public void setRefundId(String refundId) {
        this.refundId = refundId;
    }
}