package com.arvato.cc.form;

import java.sql.Timestamp;

public class Crontab {

	private String id;
	private String omsStoreId;
	private String jobname;
	private Timestamp starttime;
	private Timestamp endtime;
	private String delaytime;
	
	private String mins;
	private String hours;
	private String secs;
	
	private Timestamp createtime;
	private Timestamp updatetime;
	private String flag;
	private Timestamp nextstarttime;
	private Timestamp lastendtime;
	private String stauts;
	private String remark;
	
	private String startTime;
	private String endTime;
	private String createTime;
	private String updateTime;
	private String nextstartTime;
	private String lastendTime;
	
	private String jobcode;
	
	
	public String getJobcode() {
		return jobcode;
	}
	public void setJobcode(String jobcode) {
		this.jobcode = jobcode;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getNextstartTime() {
		return nextstartTime;
	}
	public void setNextstartTime(String nextstartTime) {
		this.nextstartTime = nextstartTime;
	}
	public String getLastendTime() {
		return lastendTime;
	}
	public void setLastendTime(String lastendTime) {
		this.lastendTime = lastendTime;
	}
	public String getStauts() {
		return stauts;
	}
	public void setStauts(String stauts) {
		this.stauts = stauts;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getJobname() {
		return jobname;
	}
	public void setJobname(String jobname) {
		this.jobname = jobname;
	}
	public Timestamp getStarttime() {
		return starttime;
	}
	public void setStarttime(Timestamp starttime) {
		this.starttime = starttime;
	}
	public Timestamp getEndtime() {
		return endtime;
	}
	public void setEndtime(Timestamp endtime) {
		this.endtime = endtime;
	}
	public String getDelaytime() {
		return delaytime;
	}
	public void setDelaytime(String delaytime) {
		this.delaytime = delaytime;
	}
	public Timestamp getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	public Timestamp getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Timestamp updatetime) {
		this.updatetime = updatetime;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public Timestamp getNextstarttime() {
		return nextstarttime;
	}
	public void setNextstarttime(Timestamp nextstarttime) {
		this.nextstarttime = nextstarttime;
	}
	public Timestamp getLastendtime() {
		return lastendtime;
	}
	public void setLastendtime(Timestamp lastendtime) {
		this.lastendtime = lastendtime;
	}
	public String getOmsStoreId() {
		return omsStoreId;
	}
	public void setOmsStoreId(String omsStoreId) {
		this.omsStoreId = omsStoreId;
	}
	public String getMins() {
		return mins;
	}
	public void setMins(String mins) {
		this.mins = mins;
	}
	public String getHours() {
		return hours;
	}
	public void setHours(String hours) {
		this.hours = hours;
	}
	public String getSecs() {
		return secs;
	}
	public void setSecs(String secs) {
		this.secs = secs;
	}
	
}
