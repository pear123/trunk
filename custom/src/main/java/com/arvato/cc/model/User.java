package com.arvato.cc.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * User entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "user")
public class User implements java.io.Serializable {

	// Fields

	private Integer userSysId;
	private String userId;
	private String userEmail;
	private String userPassword;
	private Integer version;
	private String userStatus;
	private Date createTime;
	private Date updateTime;
	private String createOp;
	private String updateOp;
	private String userName;
	private String userRemark;

	// Constructors

	/** default constructor */
	public User() {
	}

	/** minimal constructor */
	public User(String userId, String userEmail, String userPassword,
			Integer version) {
		this.userId = userId;
		this.userEmail = userEmail;
		this.userPassword = userPassword;
		this.version = version;
	}

	/** full constructor */
	public User(String userId, String userEmail, String userPassword,
			Integer version, String userStatus, Date createTime,
			Date updateTime, String createOp, String updateOp, String userName,
			String userRemark) {
		this.userId = userId;
		this.userEmail = userEmail;
		this.userPassword = userPassword;
		this.version = version;
		this.userStatus = userStatus;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.createOp = createOp;
		this.updateOp = updateOp;
		this.userName = userName;
		this.userRemark = userRemark;
	}

	// Property accessors
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USER_SYS_ID", unique = true, nullable = false)
	public Integer getUserSysId() {
		return this.userSysId;
	}

	public void setUserSysId(Integer userSysId) {
		this.userSysId = userSysId;
	}

	@Column(name = "USER_ID", nullable = false, length = 200)
	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "USER_EMAIL", nullable = false, length = 200)
	public String getUserEmail() {
		return this.userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	@Column(name = "USER_PASSWORD", nullable = false, length = 100)
	public String getUserPassword() {
		return this.userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	@Column(name = "VERSION", nullable = false)
	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@Column(name = "USER_STATUS")
	public String getUserStatus() {
		return this.userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	@Column(name = "CREATE_TIME", length = 19)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "UPDATE_TIME", length = 19)
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "CREATE_OP", length = 100)
	public String getCreateOp() {
		return this.createOp;
	}

	public void setCreateOp(String createOp) {
		this.createOp = createOp;
	}

	@Column(name = "UPDATE_OP", length = 100)
	public String getUpdateOp() {
		return this.updateOp;
	}

	public void setUpdateOp(String updateOp) {
		this.updateOp = updateOp;
	}

	@Column(name = "USER_NAME", length = 100)
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

    @Column(name = "USER_REMARK", length = 500)
	public String getUserRemark() {
		return this.userRemark;
	}

	public void setUserRemark(String userRemark) {
		this.userRemark = userRemark;
	}

}