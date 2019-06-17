package com.arvato.cc.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

/**
 * TradeRemark entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "trade_remark")
public class TradeRemark implements java.io.Serializable {

	// Fields

	private Integer id;
	private String tid;
	private String invoiceTitle;
	private String receiveName;
	private String mobile;
	private String phone;
	private String receiveAddress;
	private String receiveZip;
	private Integer quantity;
	private String ticketName;
	private String ticketCode;
	private String ticketAddress;
	private String ticketAccount;
	private Set<TradeRemarkPresent> tradeRemarkPresents = new HashSet<TradeRemarkPresent>(
			0);

	// Constructors

	/** default constructor */
	public TradeRemark() {
	}

	/** minimal constructor */
	public TradeRemark(String tid) {
		this.tid = tid;
	}

	/** full constructor */
	public TradeRemark(String tid, String invoiceTitle, String receiveName,
			String mobile, String phone, String receiveAddress,
			String receiveZip, Integer quantity, String ticketName,
			String ticketCode, String ticketAddress, String ticketAccount,
			Set<TradeRemarkPresent> tradeRemarkPresents) {
		this.tid = tid;
		this.invoiceTitle = invoiceTitle;
		this.receiveName = receiveName;
		this.mobile = mobile;
		this.phone = phone;
		this.receiveAddress = receiveAddress;
		this.receiveZip = receiveZip;
		this.quantity = quantity;
		this.ticketName = ticketName;
		this.ticketCode = ticketCode;
		this.ticketAddress = ticketAddress;
		this.ticketAccount = ticketAccount;
		this.tradeRemarkPresents = tradeRemarkPresents;
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

	@Column(name = "tid", nullable = false, length = 200)
	public String getTid() {
		return this.tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	@Column(name = "INVOICE_TITLE", length = 200)
	public String getInvoiceTitle() {
		return this.invoiceTitle;
	}

	public void setInvoiceTitle(String invoiceTitle) {
		this.invoiceTitle = invoiceTitle;
	}

	@Column(name = "RECEIVE_NAME", length = 100)
	public String getReceiveName() {
		return this.receiveName;
	}

	public void setReceiveName(String receiveName) {
		this.receiveName = receiveName;
	}

	@Column(name = "MOBILE", length = 100)
	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "PHONE", length = 100)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "RECEIVE_ADDRESS", length = 200)
	public String getReceiveAddress() {
		return this.receiveAddress;
	}

	public void setReceiveAddress(String receiveAddress) {
		this.receiveAddress = receiveAddress;
	}

	@Column(name = "RECEIVE_ZIP", length = 100)
	public String getReceiveZip() {
		return this.receiveZip;
	}

	public void setReceiveZip(String receiveZip) {
		this.receiveZip = receiveZip;
	}

	@Column(name = "quantity")
	public Integer getQuantity() {
		return this.quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	@Column(name = "ticket_name", length = 1000)
	public String getTicketName() {
		return this.ticketName;
	}

	public void setTicketName(String ticketName) {
		this.ticketName = ticketName;
	}

	@Column(name = "ticket_code", length = 100)
	public String getTicketCode() {
		return this.ticketCode;
	}

	public void setTicketCode(String ticketCode) {
		this.ticketCode = ticketCode;
	}

	@Column(name = "ticket_address", length = 100)
	public String getTicketAddress() {
		return this.ticketAddress;
	}

	public void setTicketAddress(String ticketAddress) {
		this.ticketAddress = ticketAddress;
	}

	@Column(name = "ticket_account", length = 100)
	public String getTicketAccount() {
		return this.ticketAccount;
	}

	public void setTicketAccount(String ticketAccount) {
		this.ticketAccount = ticketAccount;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "tradeRemark")
	public Set<TradeRemarkPresent> getTradeRemarkPresents() {
		return this.tradeRemarkPresents;
	}

	public void setTradeRemarkPresents(
			Set<TradeRemarkPresent> tradeRemarkPresents) {
		this.tradeRemarkPresents = tradeRemarkPresents;
	}

}