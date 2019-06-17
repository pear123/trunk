package com.arvato.cc.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * UserRole entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "user_role")
public class UserRole implements java.io.Serializable {

	// Fields

	private Integer userRoleSysId;
    private Role role;
    private User user;
	private Date createTime;
	private String createOp;
	private Date updateTime;
	private String updateOp;
	private Integer version;

	// Constructors

	/** default constructor */
	public UserRole() {
	}

	/** minimal constructor */
	public UserRole(User user, Role role, Integer version) {
		this.role = role;
		this.user = user;
		this.version = version;
	}

	/** full constructor */
	public UserRole(User user, Role role,
                    Date createTime, String createOp, Date updateTime, String updateOp,
			Integer version) {
        this.role = role;
        this.user = user;
		this.createTime = createTime;
		this.createOp = createOp;
		this.updateTime = updateTime;
		this.updateOp = updateOp;
		this.version = version;
	}

	// Property accessors
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USER_ROLE_SYS_ID", unique = true, nullable = false)
	public Integer getUserRoleSysId() {
		return this.userRoleSysId;
	}

	public void setUserRoleSysId(Integer userRoleSysId) {
		this.userRoleSysId = userRoleSysId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ROLE_SYS_ID", nullable = false)
	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_SYS_ID", nullable = false)
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "CREATE_TIME", length = 19)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "CREATE_OP", length = 100)
	public String getCreateOp() {
		return this.createOp;
	}

	public void setCreateOp(String createOp) {
		this.createOp = createOp;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "UPDATE_TIME", length = 19)
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "UPDATE_OP", length = 100)
	public String getUpdateOp() {
		return this.updateOp;
	}

	public void setUpdateOp(String updateOp) {
		this.updateOp = updateOp;
	}

	@Column(name = "VERSION", nullable = false)
	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

}