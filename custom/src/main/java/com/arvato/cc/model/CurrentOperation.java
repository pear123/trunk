package com.arvato.cc.model;

import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

/**
 * CurrentOperation entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "current_operation")
public class CurrentOperation implements java.io.Serializable {

	// Fields

	private Integer id;
	private String model;
	private String subModel;
	private Timestamp operateTime;
	private String operateOp;
	private String sysId;

	// Constructors

	/** default constructor */
	public CurrentOperation() {
	}

	/** full constructor */
	public CurrentOperation(String model, String subModel, Timestamp operateTime,
			String operateOp) {
		this.model = model;
		this.subModel = subModel;
		this.operateTime = operateTime;
		this.operateOp = operateOp;
	}

	public CurrentOperation(String model, String subModel, Timestamp operateTime, String operateOp, String sysId) {
		this.model = model;
		this.subModel = subModel;
		this.operateTime = operateTime;
		this.operateOp = operateOp;
		this.sysId = sysId;
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

	@Column(name = "model", length = 200)
	public String getModel() {
		return this.model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	@Column(name = "sub_model", length = 100)
	public String getSubModel() {
		return this.subModel;
	}

	public void setSubModel(String subModel) {
		this.subModel = subModel;
	}

	@Column(name = "operate_time", length = 19)
	public Timestamp getOperateTime() {
		return this.operateTime;
	}

	public void setOperateTime(Timestamp operateTime) {
		this.operateTime = operateTime;
	}

	@Column(name = "operate_op", length = 100)
	public String getOperateOp() {
		return this.operateOp;
	}

	public void setOperateOp(String operateOp) {
		this.operateOp = operateOp;
	}

	@Column(name = "sys_id")
	public String getSysId() {
		return sysId;
	}

	public void setSysId(String sysId) {
		this.sysId = sysId;
	}
}