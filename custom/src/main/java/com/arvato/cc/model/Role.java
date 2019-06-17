package com.arvato.cc.model;

import com.arvato.cc.util.ExtTreeNode;
import com.arvato.cc.util.ExtTreeNodeSingle;
import com.arvato.cc.util.RoleTreeNode;
import com.arvato.cc.util.RoleTreeNodeSingle;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * CRole entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "role")
public class Role implements java.io.Serializable {

	// Fields

	private Integer roleSysId;
	private String roleId;
	private String roleName;
	private String roleDescription;
	private String roleStatus;
	private Date createTime;
	private Date updateTime;
	private String createOp;
	private String updateOp;
	private Integer version;
	private Set<RoleResource> roleResources = new HashSet<RoleResource>(0);
    private Set<UserRole> userRoles = new HashSet<UserRole>(0);

	// Constructors

	/** default constructor */
	public Role() {
	}

	/** minimal constructor */
	public Role(Integer version) {
		this.version = version;
	}

	/** full constructor */
	public Role(String roleId, String roleName, String roleDescription,
                String roleStatus, Date createTime, Date updateTime,
			String createOp, String updateOp, Integer version,
			Set<RoleResource> roleResources) {
		this.roleId = roleId;
		this.roleName = roleName;
		this.roleDescription = roleDescription;
		this.roleStatus = roleStatus;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.createOp = createOp;
		this.updateOp = updateOp;
		this.version = version;
		this.roleResources = roleResources;
	}

	// Property accessors
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ROLE_SYS_ID", unique = true, nullable = false)
	public Integer getRoleSysId() {
		return this.roleSysId;
	}

	public void setRoleSysId(Integer roleSysId) {
		this.roleSysId = roleSysId;
	}

	@Column(name = "ROLE_ID")
	public String getRoleId() {
		return this.roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	@Column(name = "ROLE_NAME", length = 100)
	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Column(name = "ROLE_DESCRIPTION", length = 300)
	public String getRoleDescription() {
		return this.roleDescription;
	}

	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}

	@Column(name = "ROLE_STATUS")
	public String getRoleStatus() {
		return this.roleStatus;
	}

	public void setRoleStatus(String roleStatus) {
		this.roleStatus = roleStatus;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "CREATE_TIME", length = 19)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Temporal(TemporalType.DATE)
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

	@Column(name = "VERSION", nullable = false)
    @Version
	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "role")
	public Set<RoleResource> getRoleResources() {
		return this.roleResources;
	}

	public void setRoleResources(Set<RoleResource> roleResources) {
		this.roleResources = roleResources;
	}

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "role")
    public Set<UserRole> getUserRoles() {
        return this.userRoles;
    }

    public void setUserRoles(Set<UserRole> omsUserRoles) {
        this.userRoles = omsUserRoles;
    }

    public ExtTreeNodeSingle convertTreeNodeSingle() {
        RoleTreeNodeSingle node = new RoleTreeNodeSingle();
        node.setCode(roleId.toString());
        node.setId(roleSysId.toString());
        node.setText(roleName);
        node.setQtip(roleDescription);
        node.setQtitle(roleName);
//      node.setChecked(true);
        node.setDescription(roleDescription);
        Set<RoleResource> roleResourceSet = this.getRoleResources();
        if (null != roleResourceSet && roleResourceSet.size() > 0) {
            String ids = "";
            for (RoleResource roleResource : roleResourceSet) {
                ids = ids.concat(roleResource.getResource().getResourceSysId().toString()).concat(",");
            }
            node.setResourceIds(ids);
        }
        return node;
    }

    public ExtTreeNode convertTreeNode() {
        RoleTreeNode node = new RoleTreeNode();
        node.setCode(roleId.toString());
        node.setId(roleSysId.toString());
        node.setText(roleName);
        node.setQtip(roleDescription);
        node.setQtitle(roleName);
        node.setChecked(true);
        node.setDescription(roleDescription);
        Set<RoleResource> roleResourceSet = this.getRoleResources();
        if (null != roleResourceSet && roleResourceSet.size() > 0) {
            String ids = "";
            for (RoleResource roleResource : roleResourceSet) {
                ids = ids.concat(roleResource.getResource().getResourceSysId().toString()).concat(",");
            }
            node.setResourceIds(ids);
        }
        return node;
    }

}