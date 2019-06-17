package com.arvato.cc.form;

/**
 * 项目名称：arvato_oms    
 * 类名称：Resource    
 * 类描述：    
 * 创建人：qin
 * 创建时间：2011-8-11下午    
 * 修改人：
 * 修改时间：2011-8-11下午    
 * 修改备注： 
 */
public class  CResource {
	private String omsResourceSysId;//系统ID
	private String omsResourceName;//资源名称
	private String omsResourcePattern;//资源定义
	private String omsResourceStatus;//资源状态
	private String omsResourceDescription;//资源描述
//	private String omsModule;//模块
//	private String omsSubModule;//子模块
//	private String omsSubSystem;//子系统
	public String getOmsResourceSysId() {
		return omsResourceSysId;
	}
//	public String getOmsModule() {
//		return omsModule;
//	}
//	public void setOmsModule(String omsModule) {
//		this.omsModule = omsModule;
//	}
//	public String getOmsSubModule() {
//		return omsSubModule;
//	}
//	public void setOmsSubModule(String omsSubModule) {
//		this.omsSubModule = omsSubModule;
//	}
//	public String getOmsSubSystem() {
//		return omsSubSystem;
//	}
//	public void setOmsSubSystem(String omsSubSystem) {
//		this.omsSubSystem = omsSubSystem;
//	}
	public void setOmsResourceSysId(String omsResourceSysId) {
		this.omsResourceSysId = omsResourceSysId;
	}
	public String getOmsResourceName() {
		return omsResourceName;
	}
	public void setOmsResourceName(String omsResourceName) {
		this.omsResourceName = omsResourceName;
	}
	public String getOmsResourcePattern() {
		return omsResourcePattern;
	}
	public void setOmsResourcePattern(String omsResourcePattern) {
		this.omsResourcePattern = omsResourcePattern;
	}
	public String getOmsResourceStatus() {
		return omsResourceStatus;
	}
	public void setOmsResourceStatus(String omsResourceStatus) {
		this.omsResourceStatus = omsResourceStatus;
	}
	public String getOmsResourceDescription() {
		return omsResourceDescription;
	}
	public void setOmsResourceDescription(String omsResourceDescription) {
		this.omsResourceDescription = omsResourceDescription;
	}
}
