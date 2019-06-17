package com.arvato.cc.model;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * SyncBatch entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "REFUND_SYNC_BATCH")
public class RefundSyncBatch implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer batchNo;
	private Timestamp lastSyncTime;

	// Constructors

	/** default constructor */
	public RefundSyncBatch() {
	}

	/** full constructor */
	public RefundSyncBatch(Integer batchNo, Timestamp lastSyncTime) {
		this.batchNo = batchNo;
		this.lastSyncTime = lastSyncTime;
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

	@Column(name = "BATCH_NO", length = 200)
	public Integer getBatchNo() {
		return this.batchNo;
	}

	public void setBatchNo(Integer batchNo) {
		this.batchNo = batchNo;
	}

	@Column(name = "LAST_SYNC_TIME")
	public Timestamp getLastSyncTime() {
		return this.lastSyncTime;
	}

	public void setLastSyncTime(Timestamp lastSyncTime) {
		this.lastSyncTime = lastSyncTime;
	}

}