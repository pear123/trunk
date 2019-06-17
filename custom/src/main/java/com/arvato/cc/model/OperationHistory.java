package com.arvato.cc.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

/**
 * OperationHistory entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "operation_history")
public class OperationHistory implements java.io.Serializable {

	// Fields

	private Integer id;
	private String fileName;
	private String fileType;
	private Timestamp operateTime;
	private String operateOp;
	private String model;

	// Constructors

	/** default constructor */
	public OperationHistory() {
	}

	/** full constructor */
	public OperationHistory(String fileName, String fileType, Timestamp operateTime,
			String operateOp, String model) {
		this.fileName = fileName;
		this.fileType = fileType;
		this.operateTime = operateTime;
		this.operateOp = operateOp;
		this.model = model;
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

	@Column(name = "file_name", length = 200)
	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Column(name = "file_type", length = 100)
	public String getFileType() {
		return this.fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
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

	@Column(name = "model", length = 200)
	public String getModel() {
		return this.model;
	}

	public void setModel(String model) {
		this.model = model;
	}

}