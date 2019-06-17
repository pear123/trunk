package com.arvato.cc.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * JobExec entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "job_exec")
public class JobExec implements java.io.Serializable {

	// Fields

	private Integer id;
	private String jobName;
	private Integer batchNo;
	private String status;
	private Integer num;

	// Constructors

	/** default constructor */
	public JobExec() {
	}

	/** full constructor */
	public JobExec(String jobName, Integer batchNo, String status, Integer num) {
		this.jobName = jobName;
		this.batchNo = batchNo;
		this.status = status;
		this.num = num;
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

	@Column(name = "job_name", length = 200)
	public String getJobName() {
		return this.jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	@Column(name = "batch_no")
	public Integer getBatchNo() {
		return this.batchNo;
	}

	public void setBatchNo(Integer batchNo) {
		this.batchNo = batchNo;
	}

	@Column(name = "status", length = 100)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "num")
	public Integer getNum() {
		return this.num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

 @Override
 public String toString() {
  return "JobExec{" +
		  "id=" + id +
		  ", jobName='" + jobName + '\'' +
		  ", batchNo=" + batchNo +
		  ", status='" + status + '\'' +
		  ", num=" + num +
		  '}';
 }
}