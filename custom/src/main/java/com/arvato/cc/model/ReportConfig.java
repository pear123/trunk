package com.arvato.cc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * ReportConfig entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "report_config")
public class ReportConfig implements java.io.Serializable {

	// Fields

	private Integer id;
	private String reportName;
	private String reportColumn;
	private String taobaoColumn;
	private Integer orderIndex;
	private String tradeStatus;

	// Constructors

	/** default constructor */
	public ReportConfig() {
	}

	/** full constructor */
	public ReportConfig(String reportName, String reportColumn,
			String taobaoColumn, Integer orderIndex, String tradeStatus) {
		this.reportName = reportName;
		this.reportColumn = reportColumn;
		this.taobaoColumn = taobaoColumn;
		this.orderIndex = orderIndex;
		this.tradeStatus = tradeStatus;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "report_name", length = 45)
	public String getReportName() {
		return this.reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	@Column(name = "report_column", length = 45)
	public String getReportColumn() {
		return this.reportColumn;
	}

	public void setReportColumn(String reportColumn) {
		this.reportColumn = reportColumn;
	}

	@Column(name = "taobao_column", length = 45)
	public String getTaobaoColumn() {
		return this.taobaoColumn;
	}

	public void setTaobaoColumn(String taobaoColumn) {
		this.taobaoColumn = taobaoColumn;
	}

	@Column(name = "order_index")
	public Integer getOrderIndex() {
		return this.orderIndex;
	}

	public void setOrderIndex(Integer orderIndex) {
		this.orderIndex = orderIndex;
	}

	@Column(name = "trade_status", length = 45)
	public String getTradeStatus() {
		return this.tradeStatus;
	}

	public void setTradeStatus(String tradeStatus) {
		this.tradeStatus = tradeStatus;
	}

}