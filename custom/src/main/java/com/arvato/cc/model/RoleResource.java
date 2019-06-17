package com.arvato.cc.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * RoleResource entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "role_resource")
public class RoleResource implements java.io.Serializable {

	// Fields

	private Integer roleResourceSysId;
	private Role role;
	private Resource resource;
	private Integer version;

	// Constructors

	/** default constructor */
	public RoleResource() {
	}

	/** full constructor */
	public RoleResource(Role role, Resource resource, Integer version) {
		this.role = role;
		this.resource = resource;
		this.version = version;
	}

	// Property accessors
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ROLE_RESOURCE_SYS_ID", unique = true, nullable = false)
	public Integer getRoleResourceSysId() {
		return this.roleResourceSysId;
	}

	public void setRoleResourceSysId(Integer roleResourceSysId) {
		this.roleResourceSysId = roleResourceSysId;
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
	@JoinColumn(name = "RESOURCE_SYS_ID", nullable = false)
	public Resource getResource() {
		return this.resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	@Column(name = "VERSION", nullable = false)
	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

}