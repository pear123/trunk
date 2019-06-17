package com.arvato.cc.model;

import com.arvato.cc.util.ExtTreeNode;
import com.arvato.cc.util.ExtTreeNodeSingle;
import com.arvato.cc.util.ResourceTreeNode;
import com.arvato.cc.util.ResourceTreeNodeSingle;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Resource entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "resource")
public class Resource implements java.io.Serializable {

	// Fields

	private Integer resourceSysId;
	private String resourceName;
	private String resourcePattern;
	private String resourceStatus;
	private String resourceDescription;
	private Date createTime;
	private Date updateTime;
	private String createOp;
	private String updateOp;
	private String resourceType;
	private String parentResourceSysId;
	private String resourceCode;
	private Integer version;
	private Set<RoleResource> roleResources = new HashSet<RoleResource>(0);

	// Constructors

	/** default constructor */
	public Resource() {
	}

	/** minimal constructor */
	public Resource(String resourceName, String resourcePattern, Integer version) {
		this.resourceName = resourceName;
		this.resourcePattern = resourcePattern;
		this.version = version;
	}

	/** full constructor */
	public Resource(String resourceName, String resourcePattern,
                    String resourceStatus, String resourceDescription,
			Date createTime, Date updateTime, String createOp, String updateOp,
			String resourceType, String parentResourceSysId,
			String resourceCode, Integer version,
			Set<RoleResource> roleResources) {
		this.resourceName = resourceName;
		this.resourcePattern = resourcePattern;
		this.resourceStatus = resourceStatus;
		this.resourceDescription = resourceDescription;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.createOp = createOp;
		this.updateOp = updateOp;
		this.resourceType = resourceType;
		this.parentResourceSysId = parentResourceSysId;
		this.resourceCode = resourceCode;
		this.version = version;
		this.roleResources = roleResources;
	}

	// Property accessors
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "RESOURCE_SYS_ID", unique = true, nullable = false)
	public Integer getResourceSysId() {
		return this.resourceSysId;
	}

	public void setResourceSysId(Integer resourceSysId) {
		this.resourceSysId = resourceSysId;
	}

	@Column(name = "RESOURCE_NAME", nullable = false, length = 100)
	public String getResourceName() {
		return this.resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	@Column(name = "RESOURCE_PATTERN", nullable = false, length = 200)
	public String getResourcePattern() {
		return this.resourcePattern;
	}

	public void setResourcePattern(String resourcePattern) {
		this.resourcePattern = resourcePattern;
	}

	@Column(name = "RESOURCE_STATUS")
	public String getResourceStatus() {
		return this.resourceStatus;
	}

	public void setResourceStatus(String resourceStatus) {
		this.resourceStatus = resourceStatus;
	}

	@Column(name = "RESOURCE_DESCRIPTION", length = 300)
	public String getResourceDescription() {
		return this.resourceDescription;
	}

	public void setResourceDescription(String resourceDescription) {
		this.resourceDescription = resourceDescription;
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

	@Column(name = "RESOURCE_TYPE", length = 100)
	public String getResourceType() {
		return this.resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	@Column(name = "PARENT_RESOURCE_SYS_ID", length = 100)
	public String getParentResourceSysId() {
		return this.parentResourceSysId;
	}

	public void setParentResourceSysId(String parentResourceSysId) {
		this.parentResourceSysId = parentResourceSysId;
	}

	@Column(name = "RESOURCE_CODE", length = 100)
	public String getResourceCode() {
		return this.resourceCode;
	}

	public void setResourceCode(String resourceCode) {
		this.resourceCode = resourceCode;
	}

	@Column(name = "VERSION", nullable = false)
    @Version
	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "resource")
	public Set<RoleResource> getRoleResources() {
		return this.roleResources;
	}

	public void setRoleResources(Set<RoleResource> roleResources) {
		this.roleResources = roleResources;
	}

    public ExtTreeNode convertTreeNode() {
        ResourceTreeNode treeNode = new ResourceTreeNode();
        treeNode.setId(getResourceSysId().toString());
        treeNode.setText(getResourceName());
        treeNode.setParentId(getParentResourceSysId());
        treeNode.setCode(getResourceCode());
        treeNode.setUrl(getResourcePattern());
        treeNode.setQtitle(getResourceName());
        treeNode.setQtip(getResourcePattern());
        treeNode.setDescription(getResourceDescription());
        treeNode.setType(getResourceType());

        return treeNode;
    }


    public ExtTreeNodeSingle convertTreeNodeSingle() {
        ResourceTreeNodeSingle treeNode = new ResourceTreeNodeSingle();
        treeNode.setId(getResourceSysId().toString());
        treeNode.setText(getResourceName());
        treeNode.setParentId(getParentResourceSysId());
        treeNode.setCode(getResourceCode());
        treeNode.setUrl(getResourcePattern());
        treeNode.setQtitle(getResourceName());
        treeNode.setQtip(getResourcePattern());
        treeNode.setDescription(getResourceDescription());
        treeNode.setType(getResourceType());

        return treeNode;
    }
}