package com.arvato.cc.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * UpdInvoice entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "upd_invoice")
public class UpdInvoice implements java.io.Serializable {

	// Fields

	private Integer id;
	private String sorg;
	private String dchl;
	private String billdoc;
	private String item;
	private String cnty;
	private String billt;
	private String billdate;
	private String gitno;
	private String outbcr;
	private String gitsta;
	private String coldate;
	private String gitText;
	private String poArno;
	private String soff;
	private String descript;
	private String soffstat;
	private String description;
	private String payer;
	private String soldToPt;
	private String name1;
	private String billTo;
	private String poNumber;
	private String poDate;
	private String createdOn;
	private String time;
	private String payt;
	private String fixvaldate;
	private String avald;
	private String curr;
	private String headTxt;
	private String rinText;
	private String projId;
	private String pripr;
	private String docca;
	private String material;
	private String descry;
	private String BField;
	private String sloc;
	private String saRepI;
	private String saUnitI;
	private String saManI;
	private String saMan2;
	private String plnt;
	private String netwr;
	private String turnover;
	private String unitPr;
	private String cmpre;
	private String billQty;
	private String taxAmount;
	private String amEligibl;
	private String cost;
	private String itemTxt;
	private String kbetr;
	private String unit;
	private String refdoc;
	private String refitm;
	private String salesdoc;
	private String saty;
	private String ordrs;
	private String itca;
	private String shipTo;
	private String name2;
	private String acc;
	private String or;
	private String profitCtr;
	private String mpg;
	private String brand;
	private String reference;
	private String can;
	private String cancInv;
	private String documentno;
	private String gross;
	private String net;
	private String bonusId;
	private String campaign;
	private Integer version;
 	private Date lastTime;

	// Constructors

	/** default constructor */
	public UpdInvoice() {
	}

	/** minimal constructor */
	public UpdInvoice(Integer version) {
		this.version = version;
	}

	/** full constructor */
	public UpdInvoice(String sorg, String dchl, String billdoc, String item,
			String cnty, String billt, String billdate, String gitno,
			String outbcr, String gitsta, String coldate, String gitText,
			String poArno, String soff, String descript, String soffstat,
			String description, String payer, String soldToPt, String name1,
			String billTo, String poNumber, String poDate, String createdOn,
			String time, String payt, String fixvaldate, String avald,
			String curr, String headTxt, String rinText, String projId,
			String pripr, String docca, String material, String descry,
			String BField, String sloc, String saRepI, String saUnitI,
			String saManI, String saMan2, String plnt, String netwr,
			String turnover, String unitPr, String cmpre, String billQty,
			String taxAmount, String amEligibl, String cost, String itemTxt,
			String kbetr, String unit, String refdoc, String refitm,
			String salesdoc, String saty, String ordrs, String itca,
			String shipTo, String name2, String acc, String or,
			String profitCtr, String mpg, String brand, String reference,
			String can, String cancInv, String documentno, String gross,
			String net, String bonusId, String campaign, Integer version,Date lastTime) {
		this.sorg = sorg;
		this.dchl = dchl;
		this.billdoc = billdoc;
		this.item = item;
		this.cnty = cnty;
		this.billt = billt;
		this.billdate = billdate;
		this.gitno = gitno;
		this.outbcr = outbcr;
		this.gitsta = gitsta;
		this.coldate = coldate;
		this.gitText = gitText;
		this.poArno = poArno;
		this.soff = soff;
		this.descript = descript;
		this.soffstat = soffstat;
		this.description = description;
		this.payer = payer;
		this.soldToPt = soldToPt;
		this.name1 = name1;
		this.billTo = billTo;
		this.poNumber = poNumber;
		this.poDate = poDate;
		this.createdOn = createdOn;
		this.time = time;
		this.payt = payt;
		this.fixvaldate = fixvaldate;
		this.avald = avald;
		this.curr = curr;
		this.headTxt = headTxt;
		this.rinText = rinText;
		this.projId = projId;
		this.pripr = pripr;
		this.docca = docca;
		this.material = material;
		this.descry = descry;
		this.BField = BField;
		this.sloc = sloc;
		this.saRepI = saRepI;
		this.saUnitI = saUnitI;
		this.saManI = saManI;
		this.saMan2 = saMan2;
		this.plnt = plnt;
		this.netwr = netwr;
		this.turnover = turnover;
		this.unitPr = unitPr;
		this.cmpre = cmpre;
		this.billQty = billQty;
		this.taxAmount = taxAmount;
		this.amEligibl = amEligibl;
		this.cost = cost;
		this.itemTxt = itemTxt;
		this.kbetr = kbetr;
		this.unit = unit;
		this.refdoc = refdoc;
		this.refitm = refitm;
		this.salesdoc = salesdoc;
		this.saty = saty;
		this.ordrs = ordrs;
		this.itca = itca;
		this.shipTo = shipTo;
		this.name2 = name2;
		this.acc = acc;
		this.or = or;
		this.profitCtr = profitCtr;
		this.mpg = mpg;
		this.brand = brand;
		this.reference = reference;
		this.can = can;
		this.cancInv = cancInv;
		this.documentno = documentno;
		this.gross = gross;
		this.net = net;
		this.bonusId = bonusId;
		this.campaign = campaign;
		this.version = version;
	 	this.lastTime=lastTime;
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

	@Column(name = "SORG", length = 50)
	public String getSorg() {
		return this.sorg;
	}

	public void setSorg(String sorg) {
		this.sorg = sorg;
	}

	@Column(name = "DCHL", length = 10)
	public String getDchl() {
		return this.dchl;
	}

	public void setDchl(String dchl) {
		this.dchl = dchl;
	}

	@Column(name = "BILLDOC", length = 20)
	public String getBilldoc() {
		return this.billdoc;
	}

	public void setBilldoc(String billdoc) {
		this.billdoc = billdoc;
	}

	@Column(name = "ITEM", length = 200)
	public String getItem() {
		return this.item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	@Column(name = "CNTY", length = 200)
	public String getCnty() {
		return this.cnty;
	}

	public void setCnty(String cnty) {
		this.cnty = cnty;
	}

	@Column(name = "BILLT", length = 200)
	public String getBillt() {
		return this.billt;
	}

	public void setBillt(String billt) {
		this.billt = billt;
	}

	@Column(name = "BILLDATE", length = 200)
	public String getBilldate() {
		return this.billdate;
	}

	public void setBilldate(String billdate) {
		this.billdate = billdate;
	}

	@Column(name = "GITNO", length = 200)
	public String getGitno() {
		return this.gitno;
	}

	public void setGitno(String gitno) {
		this.gitno = gitno;
	}

	@Column(name = "OUTBCR", length = 200)
	public String getOutbcr() {
		return this.outbcr;
	}

	public void setOutbcr(String outbcr) {
		this.outbcr = outbcr;
	}

	@Column(name = "GITSTA", length = 200)
	public String getGitsta() {
		return this.gitsta;
	}

	public void setGitsta(String gitsta) {
		this.gitsta = gitsta;
	}

	@Column(name = "COLDATE", length = 200)
	public String getColdate() {
		return this.coldate;
	}

	public void setColdate(String coldate) {
		this.coldate = coldate;
	}

	@Column(name = "GIT_TEXT", length = 200)
	public String getGitText() {
		return this.gitText;
	}

	public void setGitText(String gitText) {
		this.gitText = gitText;
	}

	@Column(name = "PO_ARNO", length = 200)
	public String getPoArno() {
		return this.poArno;
	}

	public void setPoArno(String poArno) {
		this.poArno = poArno;
	}

	@Column(name = "SOFF", length = 200)
	public String getSoff() {
		return this.soff;
	}

	public void setSoff(String soff) {
		this.soff = soff;
	}

	@Column(name = "DESCRIPT", length = 200)
	public String getDescript() {
		return this.descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}

	@Column(name = "SOFFSTAT", length = 200)
	public String getSoffstat() {
		return this.soffstat;
	}

	public void setSoffstat(String soffstat) {
		this.soffstat = soffstat;
	}

	@Column(name = "DESCRIPTION", length = 200)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "PAYER", length = 200)
	public String getPayer() {
		return this.payer;
	}

	public void setPayer(String payer) {
		this.payer = payer;
	}

	@Column(name = "SOLD_TO_PT", length = 200)
	public String getSoldToPt() {
		return this.soldToPt;
	}

	public void setSoldToPt(String soldToPt) {
		this.soldToPt = soldToPt;
	}

	@Column(name = "NAME1", length = 200)
	public String getName1() {
		return this.name1;
	}

	public void setName1(String name1) {
		this.name1 = name1;
	}

	@Column(name = "BILL_TO", length = 200)
	public String getBillTo() {
		return this.billTo;
	}

	public void setBillTo(String billTo) {
		this.billTo = billTo;
	}

	@Column(name = "PO_NUMBER", length = 200)
	public String getPoNumber() {
		return this.poNumber;
	}

	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}

	@Column(name = "PO_DATE", length = 200)
	public String getPoDate() {
		return this.poDate;
	}

	public void setPoDate(String poDate) {
		this.poDate = poDate;
	}

	@Column(name = "CREATED_ON", length = 200)
	public String getCreatedOn() {
		return this.createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	@Column(name = "TI_ME", length = 200)
	public String getTime() {
		return this.time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	@Column(name = "PAYT", length = 200)
	public String getPayt() {
		return this.payt;
	}

	public void setPayt(String payt) {
		this.payt = payt;
	}

	@Column(name = "FIXVALDATE", length = 200)
	public String getFixvaldate() {
		return this.fixvaldate;
	}

	public void setFixvaldate(String fixvaldate) {
		this.fixvaldate = fixvaldate;
	}

	@Column(name = "AVALD", length = 200)
	public String getAvald() {
		return this.avald;
	}

	public void setAvald(String avald) {
		this.avald = avald;
	}

	@Column(name = "CURR", length = 200)
	public String getCurr() {
		return this.curr;
	}

	public void setCurr(String curr) {
		this.curr = curr;
	}

	@Column(name = "HEAD_TXT", length = 200)
	public String getHeadTxt() {
		return this.headTxt;
	}

	public void setHeadTxt(String headTxt) {
		this.headTxt = headTxt;
	}

	@Column(name = "RIN_TEXT", length = 200)
	public String getRinText() {
		return this.rinText;
	}

	public void setRinText(String rinText) {
		this.rinText = rinText;
	}

	@Column(name = "PROJ_ID", length = 200)
	public String getProjId() {
		return this.projId;
	}

	public void setProjId(String projId) {
		this.projId = projId;
	}

	@Column(name = "PRIPR", length = 200)
	public String getPripr() {
		return this.pripr;
	}

	public void setPripr(String pripr) {
		this.pripr = pripr;
	}

	@Column(name = "DOCCA", length = 200)
	public String getDocca() {
		return this.docca;
	}

	public void setDocca(String docca) {
		this.docca = docca;
	}

	@Column(name = "MATERIAL", length = 200)
	public String getMaterial() {
		return this.material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	@Column(name = "DESCRY", length = 200)
	public String getDescry() {
		return this.descry;
	}

	public void setDescry(String descry) {
		this.descry = descry;
	}

	@Column(name = "B_FIELD", length = 200)
	public String getBField() {
		return this.BField;
	}

	public void setBField(String BField) {
		this.BField = BField;
	}

	@Column(name = "SLOC", length = 200)
	public String getSloc() {
		return this.sloc;
	}

	public void setSloc(String sloc) {
		this.sloc = sloc;
	}

	@Column(name = "SA_REP_I", length = 200)
	public String getSaRepI() {
		return this.saRepI;
	}

	public void setSaRepI(String saRepI) {
		this.saRepI = saRepI;
	}

	@Column(name = "SA_UNIT_I", length = 200)
	public String getSaUnitI() {
		return this.saUnitI;
	}

	public void setSaUnitI(String saUnitI) {
		this.saUnitI = saUnitI;
	}

	@Column(name = "SA_MAN_I", length = 200)
	public String getSaManI() {
		return this.saManI;
	}

	public void setSaManI(String saManI) {
		this.saManI = saManI;
	}

	@Column(name = "SA_MAN_2", length = 200)
	public String getSaMan2() {
		return this.saMan2;
	}

	public void setSaMan2(String saMan2) {
		this.saMan2 = saMan2;
	}

	@Column(name = "PLNT", length = 200)
	public String getPlnt() {
		return this.plnt;
	}

	public void setPlnt(String plnt) {
		this.plnt = plnt;
	}

	@Column(name = "NETWR", length = 200)
	public String getNetwr() {
		return this.netwr;
	}

	public void setNetwr(String netwr) {
		this.netwr = netwr;
	}

	@Column(name = "TURNOVER", length = 200)
	public String getTurnover() {
		return this.turnover;
	}

	public void setTurnover(String turnover) {
		this.turnover = turnover;
	}

	@Column(name = "UNIT_PR", length = 200)
	public String getUnitPr() {
		return this.unitPr;
	}

	public void setUnitPr(String unitPr) {
		this.unitPr = unitPr;
	}

	@Column(name = "CMPRE", length = 200)
	public String getCmpre() {
		return this.cmpre;
	}

	public void setCmpre(String cmpre) {
		this.cmpre = cmpre;
	}

	@Column(name = "BILL_QTY", length = 200)
	public String getBillQty() {
		return this.billQty;
	}

	public void setBillQty(String billQty) {
		this.billQty = billQty;
	}

	@Column(name = "TAX_AMOUNT", length = 200)
	public String getTaxAmount() {
		return this.taxAmount;
	}

	public void setTaxAmount(String taxAmount) {
		this.taxAmount = taxAmount;
	}

	@Column(name = "AM_ELIGIBL", length = 200)
	public String getAmEligibl() {
		return this.amEligibl;
	}

	public void setAmEligibl(String amEligibl) {
		this.amEligibl = amEligibl;
	}

	@Column(name = "COST", length = 200)
	public String getCost() {
		return this.cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}

	@Column(name = "ITEM_TXT", length = 200)
	public String getItemTxt() {
		return this.itemTxt;
	}

	public void setItemTxt(String itemTxt) {
		this.itemTxt = itemTxt;
	}

	@Column(name = "KBETR", length = 200)
	public String getKbetr() {
		return this.kbetr;
	}

	public void setKbetr(String kbetr) {
		this.kbetr = kbetr;
	}

	@Column(name = "UNIT", length = 200)
	public String getUnit() {
		return this.unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	@Column(name = "REFDOC", length = 200)
	public String getRefdoc() {
		return this.refdoc;
	}

	public void setRefdoc(String refdoc) {
		this.refdoc = refdoc;
	}

	@Column(name = "REFITM", length = 200)
	public String getRefitm() {
		return this.refitm;
	}

	public void setRefitm(String refitm) {
		this.refitm = refitm;
	}

	@Column(name = "SALESDOC", length = 200)
	public String getSalesdoc() {
		return this.salesdoc;
	}

	public void setSalesdoc(String salesdoc) {
		this.salesdoc = salesdoc;
	}

	@Column(name = "SATY", length = 200)
	public String getSaty() {
		return this.saty;
	}

	public void setSaty(String saty) {
		this.saty = saty;
	}

	@Column(name = "ORDRS", length = 200)
	public String getOrdrs() {
		return this.ordrs;
	}

	public void setOrdrs(String ordrs) {
		this.ordrs = ordrs;
	}

	@Column(name = "ITCA", length = 200)
	public String getItca() {
		return this.itca;
	}

	public void setItca(String itca) {
		this.itca = itca;
	}

	@Column(name = "SHIP_TO", length = 200)
	public String getShipTo() {
		return this.shipTo;
	}

	public void setShipTo(String shipTo) {
		this.shipTo = shipTo;
	}

	@Column(name = "NAME2", length = 200)
	public String getName2() {
		return this.name2;
	}

	public void setName2(String name2) {
		this.name2 = name2;
	}

	@Column(name = "ACC", length = 200)
	public String getAcc() {
		return this.acc;
	}

	public void setAcc(String acc) {
		this.acc = acc;
	}

	@Column(name = "O_R", length = 200)
	public String getOr() {
		return this.or;
	}

	public void setOr(String or) {
		this.or = or;
	}

	@Column(name = "PROFIT_CTR", length = 200)
	public String getProfitCtr() {
		return this.profitCtr;
	}

	public void setProfitCtr(String profitCtr) {
		this.profitCtr = profitCtr;
	}

	@Column(name = "MPG", length = 200)
	public String getMpg() {
		return this.mpg;
	}

	public void setMpg(String mpg) {
		this.mpg = mpg;
	}

	@Column(name = "BRAND", length = 200)
	public String getBrand() {
		return this.brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	@Column(name = "REFERENCE", length = 200)
	public String getReference() {
		return this.reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	@Column(name = "CAN", length = 200)
	public String getCan() {
		return this.can;
	}

	public void setCan(String can) {
		this.can = can;
	}

	@Column(name = "CANC_INV", length = 200)
	public String getCancInv() {
		return this.cancInv;
	}

	public void setCancInv(String cancInv) {
		this.cancInv = cancInv;
	}

	@Column(name = "DOCUMENTNO", length = 200)
	public String getDocumentno() {
		return this.documentno;
	}

	public void setDocumentno(String documentno) {
		this.documentno = documentno;
	}

	@Column(name = "GROSS", length = 200)
	public String getGross() {
		return this.gross;
	}

	public void setGross(String gross) {
		this.gross = gross;
	}

	@Column(name = "NET", length = 200)
	public String getNet() {
		return this.net;
	}

	public void setNet(String net) {
		this.net = net;
	}

	@Column(name = "BONUS_ID", length = 200)
	public String getBonusId() {
		return this.bonusId;
	}

	public void setBonusId(String bonusId) {
		this.bonusId = bonusId;
	}

	@Column(name = "CAMPAIGN", length = 200)
	public String getCampaign() {
		return this.campaign;
	}

	public void setCampaign(String campaign) {
		this.campaign = campaign;
	}

	@Column(name = "VERSION", nullable = false)
    @Version
	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

 	@Column(name = "LAST_TIME", length = 200)
 	public Date getLastTime() {
  		return this.lastTime;
 	}

 	public void setLastTime(Date lastTime) {
  this.lastTime = lastTime;
 }
}