package com.arvato.cc.service1.impl;

import com.arvato.cc.constant.Constants;
import com.arvato.cc.dao1.AlipayDao;
import com.arvato.cc.dao1.OperationLogDao;
import com.arvato.cc.dao1.SapInvoiceDao;
import com.arvato.cc.exceptions.DataException;
import com.arvato.cc.model.OperationHistory;
import com.arvato.cc.model.UpdInvoice;
import com.arvato.cc.service1.UploadInvoiceService;
import com.arvato.cc.util.CommonHelper;
import com.arvato.cc.util.FileUtil;
import com.arvato.cc.util.UserSecurityUtil;
import com.arvato.cc.util.Validate;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: caol005
 * Date: 15-7-16
 * Time: 下午1:58
 * To change this template use File | Settings | File Templates.
 */
@Service
public class UploadInvoiceServiceImpl implements UploadInvoiceService {
    private static final Logger log = LoggerFactory.getLogger(UploadInvoiceServiceImpl.class);

    @Autowired
    private AlipayDao uploadDao;
    @Value("${upload.process}")
    private String processPath;  //正在操作的文件
    @Value("${upload.arch}")
    private String archPath;   //成功读取本地文件备份
    @Value("${upload.error}")
    private String errorPath;   //读取失败本地文件备份

    @Autowired
    private SapInvoiceDao sapInvoiceDao;

    @Autowired
    private OperationLogDao operationLogDao;

    private String dateFormat = "yyyyMMddHHmmss"; //file suffix

    /**
     * 上传Sap发票数据
     *
     * @param uploadFile
     * @param fileType
     * @param folder
     * @return
     */
    public String uploadFile(CommonsMultipartFile uploadFile, String fileType, String folder) {
        log.debug("method uploadFile start");
        String result = "failure";
        Date nowTime=new Date();
        String originalFileName = uploadFile.getOriginalFilename();
        String prefix = originalFileName.substring(0, originalFileName.lastIndexOf("."));
        String fileName = prefix + CommonHelper.DateFormat(CommonHelper.getThisTimestamp(), "yyyMMddhhmmss") + "." + fileType;
        File processFilePath = new File(processPath + folder);
        if (!processFilePath.exists()) {
            processFilePath.mkdirs();
        }
        File updFile = new File(processFilePath, fileName);
        try {
            FileCopyUtils.copy(uploadFile.getBytes(), updFile);
            //sap invoice template
            Map<Integer, Map<Integer, List<String>>> map = FileUtil.readExcel(new FileInputStream(updFile), 81, 1, 1, fileType);
            int rowNum = 3;
            //取出所有的GTI-No的所有数据
            List<Map<String, Object>> gitNoList = sapInvoiceDao.getBillAllGitNoAndId();
            Set<String> gitNoSet = new HashSet<String>();
            if (!CollectionUtils.isEmpty(gitNoList)) {
                for (Map<String, Object> valueMap : gitNoList) {
                    String gitno = valueMap.get("gitno").toString();
                    gitNoSet.add(gitno);
                }
            }
            Set<Map.Entry<Integer, List<String>>> resultSet = map.get(0).entrySet();
            Map<String,Integer> columnIndexMap = new HashMap<String,Integer>();
            if (!CollectionUtils.isEmpty(resultSet)) {
                Boolean IsNeed = true;//对表头进行校验
                List<UpdInvoice> updInvoiceList=new ArrayList<UpdInvoice>();
                for (Map.Entry<Integer, List<String>> ent : resultSet) {
                    List<String> contents = ent.getValue();
                    //格式校验不通过，则返回NULL
                    try {
                        if (IsNeed) {
                            if (!CollectionUtils.isEmpty(contents)) {
                                if (validateColumn(contents)) {
                                    columnIndexMap = columnIndexMap(contents);
                                    IsNeed = false;
                                    continue;
                                } else {
                                    return Constants.ExceptionMsg.FORMAT_ERROR.getValue().toString();
                                }
                            }
                        }
                        if (!CollectionUtils.isEmpty(contents)) {
                            //if exist then update database data
                            Integer invoiceId=null;
//                            if (!CollectionUtils.isEmpty(gitNoSet) && gitNoSet.contains(contents.get(columnIndexMap.get("GTI-No.")))) {
//                                //rowNum++;
//                                //continue;
//                                if (!CollectionUtils.isEmpty(gitNoList)) {
//                                    for (Map<String, Object> valueMap : gitNoList) {
//                                        if(contents.get(columnIndexMap.get("GTI-No.")).equals(valueMap.get("gitno").toString())){
//                                            invoiceId=(Integer)valueMap.get("id");
//                                            break;
//                                        }
//                                    }
//                                }
//                            }
                            //if(invoiceId==null){
                                UpdInvoice updInvoice = getUpdInvoiceData(contents,columnIndexMap);
                                if (updInvoice != null) {
                                    updInvoice.setLastTime(nowTime);
                                    //this.sapInvoiceDao.saveOrUpdate(updInvoice);
                                    updInvoiceList.add(updInvoice);
                                    gitNoSet.add(updInvoice.getGitno());
                                    rowNum++;
                                } else {
                                    return String.format(Constants.upload_error_msg, rowNum,"GIT-NO不能为空!");
                                }
//                            }else{
//                                UpdInvoice updInvoice =sapInvoiceDao.findById(invoiceId);
//                                updInvoice =updateUpdInvoiceData(updInvoice,contents,columnIndexMap);
//                                updInvoice.setLastTime(nowTime);
//                                this.sapInvoiceDao.saveOrUpdate(updInvoice);
//                                gitNoSet.add(updInvoice.getGitno());
//                                rowNum++;
//                            }

                        } else {
                            return Constants.ExceptionMsg.DATA_IS_NULL.getValue().toString();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        return String.format(Constants.upload_error_msg, rowNum, "save data exception!");
                    }
                }
                if (resultSet.size() == 1) {
                    return Constants.ExceptionMsg.DATA_IS_NULL.getValue().toString();
                }
                if(updInvoiceList!=null&&updInvoiceList.size()>0){
                    for(UpdInvoice updInvoice : updInvoiceList){
                        this.sapInvoiceDao.saveOrUpdate(updInvoice);
                    }
                }
                //将上传的操作记录到历史操作表中
                OperationHistory OperationHistory = new OperationHistory();
                OperationHistory.setFileName(fileName);
                OperationHistory.setOperateTime(CommonHelper.getThisTimestamp());
                OperationHistory.setOperateOp(UserSecurityUtil.getCurrentUsername());
                OperationHistory.setFileType(Constants.OperationType.UPLOAD_INVOICE.toString());
                OperationHistory.setModel(Constants.ModelType.UPLOAD.toString());
                this.operationLogDao.saveOperationLog(OperationHistory);

                File archFilePath = new File(archPath + folder);
                if (!archFilePath.exists()) {
                    archFilePath.mkdirs();
                }
                File updSuccessFile = new File(archFilePath, fileName);
                FileCopyUtils.copy(uploadFile.getBytes(), updSuccessFile);
                updFile.delete();
                result = "success";
            } else {
                return Constants.ExceptionMsg.FORMAT_ERROR.getValue().toString();
            }
        } catch (Exception e) {
            try {
                File errorFilePath = new File(errorPath + folder);
                if (!errorFilePath.exists()) {
                    errorFilePath.mkdirs();
                }
                File updErrorFile = new File(errorFilePath, fileName);
                FileCopyUtils.copy(uploadFile.getBytes(), updErrorFile);
                updFile.delete();
            } catch (IOException el) {
                el.printStackTrace();
            }
            e.printStackTrace();
        }
        log.debug("method upload sap Invoice File end");
        return result;
    }

    private Map<String,Integer> columnIndexMap(List<String> content){
        Map<String,Integer> columnIndexMap = new HashMap<String,Integer>();
        for(int i = 0;i<content.size();i++){
            columnIndexMap.put(content.get(i),i);
        }
        return columnIndexMap;
    }


    public Boolean validateColumn(List<String> contents) {
        List<String> list = new ArrayList<String>();
//        list.add("SOrg.");
//        list.add("DChl");
        list.add("Bill.Doc.");
//        list.add("Item");
//        list.add("CnTy");
        list.add("BillT");
        list.add("Bill. Date");
        list.add("GTI-No.");
//        list.add("Outb.Cr.");
        list.add("GTI-STA");
//        list.add("Col.date");
//        list.add("GTI-TEXT");
//        list.add("PO ar.no");
//        list.add("SOff");
//        list.add("Descript.");
//        list.add("SOff.");
//        list.add("SOff.Stat");
//        list.add("Description");
//        list.add("Payer");
//        list.add("Sold-to pt");
//        list.add("Name1");
//        list.add("Bill to");
        list.add("Name1");
        list.add("PO number");
        list.add("PO date");
//        list.add("Created on");
//        list.add("Time");
//        list.add("NETWR");
//        list.add("PayT");
//        list.add("FixValDate");
//        list.add("AValD");
//        list.add("Curr.");
//        list.add("Head-Txt");
//        list.add("RIN-TEXT");
//        list.add("Proj.Id");
//        list.add("PriPr.");
//        list.add("DocCa");
        list.add("Material");
//        list.add("Descr.");
//        list.add("B.-Field");
//        list.add("SLoc");
//        list.add("Sa-Rep I");
//        list.add("Sa-Unit  I");
//        list.add("Sa-Man.  I");
//        list.add("Sa-Man.2 I");
//        list.add("Plnt");
//        list.add("NETWR");
//        list.add("Turnover");
//        list.add("Unit-Pr.");
        list.add("CMPRE");
//        list.add("Bill.qty");
//        list.add("Tax amount");
//        list.add("Am.eligibl");
//        list.add("cost");
//        list.add("Item-Txt");
//        list.add("KBETR");
//        list.add("Unit");
//        list.add("KWERT");
//        list.add("Unit");
//        list.add("Ref.doc.");
//        list.add("RefItm");
//        list.add("Sales Doc.");
//        list.add("SaTy");
//        list.add("OrdRs");
//        list.add("ItCa");
//        list.add("Ship-to");
//        list.add("Name1");
//        list.add("Name2");
//        list.add("Acc.");
//        list.add("CC/Or");
//        list.add("Profit Ctr");
//        list.add("MPG");
//        list.add("Brand");
//        list.add("Reference");
//        list.add("Can");
//        list.add("Canc.Inv.");
//        list.add("DocumentNo");
//        list.add("Gross");
//        list.add("Net");
//        list.add("Bonus ID");
//        list.add("Campaign");
        boolean isSame = true;
        for(String str : list) {
            if(!contents.contains(str)){
                isSame = false;
                break;
            }
        }
        return isSame;
//        Boolean rs = false;
//        String[] columns = {"SOrg.", "DChl", "Bill.Doc.", "Item", "CnTy",
//                "BillT", "Bill. Date", "GTI-No.", "Outb.Cr.", "GTI-STA",
//                "Col.date", "GTI-TEXT", "PO ar.no", "SOff.", "Descript.", "SOff.Stat",
//                "Description", "Payer", "Sold-to pt",
//                "Name1", "Bill to", "Name1", "PO number", "PO date", "Created on", "Time",
//                "NETWR", "PayT", "FixValDate", "AValD", "Curr.", "Head-Txt", "RIN-TEXT", "Proj.Id",
//                "PriPr.", "DocCa", "Material", "Descr.", "B.-Field", "SLoc", "Sa-Rep I",
//                "Sa-Unit  I", "Sa-Man.  I", "Sa-Man.2 I", "Plnt", "NETWR", "Turnover",
//                "Unit-Pr.", "CMPRE", "Bill.qty", "Tax amount", "Am.eligibl", "cost", "Item-Txt",
//                "KBETR", "Unit", "KWERT", "Unit", "Ref.doc.", "RefItm", "Sales Doc.", "SaTy",
//                "OrdRs", "ItCa", "Ship-to", "Name1", "Name2", "Acc.", "CC/Or", "Profit Ctr",
//                "MPG", "Brand", "Reference", "Can", "Canc.Inv.", "DocumentNo", "Gross", "Net",
//                "Bonus ID", "Campaign"};
//        if (contents.size() == columns.length) {
//            for (int i = 0; i < columns.length; i++) {
//                if (!columns[i].trim().equals(contents.get(i))) {
//                    break;
//                }
//                if (i == columns.length - 1) {
//                    rs = true;
//                }
//            }
//        }
//        return rs;
    }


    public UpdInvoice getUpdInvoiceData(List<String> Contents,Map<String,Integer> columnIndexMap) throws DataException {

        UpdInvoice updInvoice = new UpdInvoice();

        if (Validate.isNullOrEmpty(Contents) || Contents.size() == 0) {
            throw new DataException(Constants.ExceptionMsg.DATA_IS_NULL.getValue().toString());
        }
        //模板中的所有字段,共80个字段
        String gitNo = Contents.get(columnIndexMap.get("GTI-No.")).trim();
        String b_field = Contents.get(columnIndexMap.get("B.-Field")).trim();
        String netwr2 = Contents.get(columnIndexMap.get("NETWR")).trim();
        String kwert = Contents.get(columnIndexMap.get("KWERT")).trim();
        String unit2 = Contents.get(columnIndexMap.get("Unit")).trim();
        String cc_Or = Contents.get(columnIndexMap.get("CC/Or")).trim();

        //校验gitNo是否为空

        if (StringUtils.isEmpty(gitNo)) {
            return null;
        }
        updInvoice.setGitno(gitNo);
        updInvoice.setSorg(Contents.get(columnIndexMap.get("SOrg.")).trim());
        updInvoice.setDchl(Contents.get(columnIndexMap.get("DChl")).trim());
        updInvoice.setBilldoc(Contents.get(columnIndexMap.get("Bill.Doc.")).trim());
        updInvoice.setItem(Contents.get(columnIndexMap.get("Item")).trim());
        updInvoice.setCnty(Contents.get(columnIndexMap.get("CnTy")).trim());
        updInvoice.setBillt(Contents.get(columnIndexMap.get("BillT")).trim());
        updInvoice.setBilldate(Contents.get(columnIndexMap.get("Bill. Date")).trim());
        updInvoice.setOutbcr(Contents.get(columnIndexMap.get("Outb.Cr.")).trim());
        updInvoice.setGitsta(Contents.get(columnIndexMap.get("GTI-STA")).trim());
        updInvoice.setColdate(Contents.get(columnIndexMap.get("Col.date")).trim());
        updInvoice.setGitText(Contents.get(columnIndexMap.get("GTI-TEXT")).trim());
        updInvoice.setPoArno(Contents.get(columnIndexMap.get("PO ar.no")).trim());
        updInvoice.setSoff(Contents.get(columnIndexMap.get("SOff.")).trim());
        updInvoice.setDescript(Contents.get(columnIndexMap.get("Descript.")).trim());
        updInvoice.setSoffstat(Contents.get(columnIndexMap.get("SOff.Stat")).trim());
        updInvoice.setDescription(Contents.get(columnIndexMap.get("Description")).trim());
        updInvoice.setPayer(Contents.get(columnIndexMap.get("Payer")).trim());
        updInvoice.setSoldToPt(Contents.get(columnIndexMap.get("Sold-to pt")).trim());
        updInvoice.setName1(Contents.get(columnIndexMap.get("Name1")).trim());
        updInvoice.setBillTo(Contents.get(columnIndexMap.get("Bill to")).trim());
        updInvoice.setName2(Contents.get(columnIndexMap.get("Name1")).trim());
        updInvoice.setPoNumber(Contents.get(columnIndexMap.get("PO number")).trim());
        updInvoice.setPoDate(Contents.get(columnIndexMap.get("PO date")).trim());
        updInvoice.setCreatedOn(Contents.get(columnIndexMap.get("Created on")).trim());
        updInvoice.setTime(Contents.get(columnIndexMap.get("Time")).trim());
        updInvoice.setNetwr(Contents.get(columnIndexMap.get("NETWR")).trim());
        updInvoice.setPayt(Contents.get(columnIndexMap.get("PayT")).trim());
        updInvoice.setFixvaldate(Contents.get(columnIndexMap.get("FixValDate")).trim());
        updInvoice.setAvald(Contents.get(columnIndexMap.get("AValD")).trim());
        updInvoice.setCurr(Contents.get(columnIndexMap.get("Curr.")).trim());
        updInvoice.setHeadTxt(Contents.get(columnIndexMap.get("Head-Txt")).trim());
        updInvoice.setRinText(Contents.get(columnIndexMap.get("RIN-TEXT")).trim());
        updInvoice.setProjId(Contents.get(columnIndexMap.get("Proj.Id")).trim());
        updInvoice.setPripr(Contents.get(columnIndexMap.get("PriPr.")).trim());
        updInvoice.setDocca(Contents.get(columnIndexMap.get("DocCa")).trim());
        updInvoice.setMaterial(Contents.get(columnIndexMap.get("Material")).trim());
        updInvoice.setDescry(Contents.get(columnIndexMap.get("Descr.")).trim());
//      updInvoice.setbField(b_field);
        updInvoice.setSloc(Contents.get(columnIndexMap.get("SLoc")).trim());
        updInvoice.setSaRepI(Contents.get(columnIndexMap.get("Sa-Rep I")).trim());
        updInvoice.setSaUnitI(Contents.get(columnIndexMap.get("Sa-Unit  I")).trim());
        updInvoice.setSaManI(Contents.get(columnIndexMap.get("Sa-Man.  I")).trim());
        updInvoice.setSaMan2(Contents.get(columnIndexMap.get("Sa-Man.2 I")).trim());
        updInvoice.setPlnt(Contents.get(columnIndexMap.get("Plnt")).trim());
//      updInvoice.setNetwr2(netwr2);
        updInvoice.setTurnover(Contents.get(columnIndexMap.get("Turnover")).trim());
        updInvoice.setUnitPr(Contents.get(columnIndexMap.get("Unit-Pr.")).trim());
        updInvoice.setCmpre(Contents.get(columnIndexMap.get("CMPRE")).trim());
        updInvoice.setBillQty(Contents.get(columnIndexMap.get("Bill.qty")).trim());
        updInvoice.setTaxAmount(Contents.get(columnIndexMap.get("Tax amount")).trim());
        updInvoice.setAmEligibl(Contents.get(columnIndexMap.get("Am.eligibl")).trim());
        updInvoice.setCost(Contents.get(columnIndexMap.get("cost")).trim());
        updInvoice.setItemTxt(Contents.get(columnIndexMap.get("Item-Txt")).trim());
        updInvoice.setKbetr(Contents.get(columnIndexMap.get("KBETR")).trim());
        updInvoice.setUnit(Contents.get(columnIndexMap.get("Unit")).trim());
//      updInvoice.setKwert(kwert);
//      updInvoice.setUnit2(unit2);
        updInvoice.setRefdoc(Contents.get(columnIndexMap.get("Ref.doc.")).trim());
        updInvoice.setRefitm(Contents.get(columnIndexMap.get("RefItm")).trim());
        updInvoice.setSalesdoc(Contents.get(columnIndexMap.get("Sales Doc.")).trim());
        updInvoice.setSaty(Contents.get(columnIndexMap.get("SaTy")).trim());
        updInvoice.setOrdrs(Contents.get(columnIndexMap.get("OrdRs")).trim());
        updInvoice.setItca(Contents.get(columnIndexMap.get("ItCa")).trim());
        updInvoice.setShipTo(Contents.get(columnIndexMap.get("Ship-to")).trim());
        updInvoice.setName1(Contents.get(columnIndexMap.get("Name1")).trim());
        updInvoice.setName2(Contents.get(columnIndexMap.get("Name2")).trim());
        updInvoice.setAcc(Contents.get(columnIndexMap.get("Acc.")).trim());
//      updInvoice.setCcor(cc_Or);
        updInvoice.setProfitCtr(Contents.get(columnIndexMap.get("Profit Ctr")).trim());
        updInvoice.setMpg(Contents.get(columnIndexMap.get("MPG")).trim());
        updInvoice.setBrand(Contents.get(columnIndexMap.get("Brand")).trim());
        updInvoice.setReference(Contents.get(columnIndexMap.get("Reference")).trim());
        updInvoice.setCan(Contents.get(columnIndexMap.get("Can")).trim());
        updInvoice.setCancInv(Contents.get(columnIndexMap.get("Canc.Inv.")).trim());
        updInvoice.setDocumentno(Contents.get(columnIndexMap.get("DocumentNo")).trim());
        updInvoice.setGross(Contents.get(columnIndexMap.get("Gross")).trim());
        updInvoice.setNet(Contents.get(columnIndexMap.get("Net")).trim());
        updInvoice.setBonusId(Contents.get(columnIndexMap.get("Bonus ID")).trim());
        updInvoice.setCampaign(Contents.get(columnIndexMap.get("Campaign")).trim());
        return updInvoice;
    }

    public UpdInvoice updateUpdInvoiceData(UpdInvoice updInvoice,List<String> Contents,Map<String,Integer> columnIndexMap) throws DataException {

        if (Validate.isNullOrEmpty(Contents) || Contents.size() == 0) {
            throw new DataException(Constants.ExceptionMsg.DATA_IS_NULL.getValue().toString());
        }
        //模板中的所有字段,共80个字段
        String gitNo = Contents.get(columnIndexMap.get("GTI-No.")).trim();
        String b_field = Contents.get(columnIndexMap.get("B.-Field")).trim();
        String netwr2 = Contents.get(columnIndexMap.get("NETWR")).trim();
        String kwert = Contents.get(columnIndexMap.get("KWERT")).trim();
        String unit2 = Contents.get(columnIndexMap.get("Unit")).trim();
        String cc_Or = Contents.get(columnIndexMap.get("CC/Or")).trim();

        //校验gitNo是否为空

        if (StringUtils.isEmpty(gitNo)) {
            return null;
        }
        updInvoice.setGitno(gitNo);
        updInvoice.setSorg(Contents.get(columnIndexMap.get("SOrg.")).trim());
        updInvoice.setDchl(Contents.get(columnIndexMap.get("DChl")).trim());
        updInvoice.setBilldoc(Contents.get(columnIndexMap.get("Bill.Doc.")).trim());
        updInvoice.setItem(Contents.get(columnIndexMap.get("Item")).trim());
        updInvoice.setCnty(Contents.get(columnIndexMap.get("CnTy")).trim());
        updInvoice.setBillt(Contents.get(columnIndexMap.get("BillT")).trim());
        updInvoice.setBilldate(Contents.get(columnIndexMap.get("Bill. Date")).trim());
        updInvoice.setOutbcr(Contents.get(columnIndexMap.get("Outb.Cr.")).trim());
        updInvoice.setGitsta(Contents.get(columnIndexMap.get("GTI-STA")).trim());
        updInvoice.setColdate(Contents.get(columnIndexMap.get("Col.date")).trim());
        updInvoice.setGitText(Contents.get(columnIndexMap.get("GTI-TEXT")).trim());
        updInvoice.setPoArno(Contents.get(columnIndexMap.get("PO ar.no")).trim());
        updInvoice.setSoff(Contents.get(columnIndexMap.get("SOff.")).trim());
        updInvoice.setDescript(Contents.get(columnIndexMap.get("Descript.")).trim());
        updInvoice.setSoffstat(Contents.get(columnIndexMap.get("SOff.Stat")).trim());
        updInvoice.setDescription(Contents.get(columnIndexMap.get("Description")).trim());
        updInvoice.setPayer(Contents.get(columnIndexMap.get("Payer")).trim());
        updInvoice.setSoldToPt(Contents.get(columnIndexMap.get("Sold-to pt")).trim());
        updInvoice.setName1(Contents.get(columnIndexMap.get("Name1")).trim());
        updInvoice.setBillTo(Contents.get(columnIndexMap.get("Bill to")).trim());
        updInvoice.setName2(Contents.get(columnIndexMap.get("Name1")).trim());
        updInvoice.setPoNumber(Contents.get(columnIndexMap.get("PO number")).trim());
        updInvoice.setPoDate(Contents.get(columnIndexMap.get("PO date")).trim());
        updInvoice.setCreatedOn(Contents.get(columnIndexMap.get("Created on")).trim());
        updInvoice.setTime(Contents.get(columnIndexMap.get("Time")).trim());
        updInvoice.setNetwr(Contents.get(columnIndexMap.get("NETWR")).trim());
        updInvoice.setPayt(Contents.get(columnIndexMap.get("PayT")).trim());
        updInvoice.setFixvaldate(Contents.get(columnIndexMap.get("FixValDate")).trim());
        updInvoice.setAvald(Contents.get(columnIndexMap.get("AValD")).trim());
        updInvoice.setCurr(Contents.get(columnIndexMap.get("Curr.")).trim());
        updInvoice.setHeadTxt(Contents.get(columnIndexMap.get("Head-Txt")).trim());
        updInvoice.setRinText(Contents.get(columnIndexMap.get("RIN-TEXT")).trim());
        updInvoice.setProjId(Contents.get(columnIndexMap.get("Proj.Id")).trim());
        updInvoice.setPripr(Contents.get(columnIndexMap.get("PriPr.")).trim());
        updInvoice.setDocca(Contents.get(columnIndexMap.get("DocCa")).trim());
        updInvoice.setMaterial(Contents.get(columnIndexMap.get("Material")).trim());
        updInvoice.setDescry(Contents.get(columnIndexMap.get("Descr.")).trim());
        //      updInvoice.setbField(b_field);
        updInvoice.setSloc(Contents.get(columnIndexMap.get("SLoc")).trim());
        updInvoice.setSaRepI(Contents.get(columnIndexMap.get("Sa-Rep I")).trim());
        updInvoice.setSaUnitI(Contents.get(columnIndexMap.get("Sa-Unit  I")).trim());
        updInvoice.setSaManI(Contents.get(columnIndexMap.get("Sa-Man.  I")).trim());
        updInvoice.setSaMan2(Contents.get(columnIndexMap.get("Sa-Man.2 I")).trim());
        updInvoice.setPlnt(Contents.get(columnIndexMap.get("Plnt")).trim());
        //      updInvoice.setNetwr2(netwr2);
        updInvoice.setTurnover(Contents.get(columnIndexMap.get("Turnover")).trim());
        updInvoice.setUnitPr(Contents.get(columnIndexMap.get("Unit-Pr.")).trim());
        updInvoice.setCmpre(Contents.get(columnIndexMap.get("CMPRE")).trim());
        updInvoice.setBillQty(Contents.get(columnIndexMap.get("Bill.qty")).trim());
        updInvoice.setTaxAmount(Contents.get(columnIndexMap.get("Tax amount")).trim());
        updInvoice.setAmEligibl(Contents.get(columnIndexMap.get("Am.eligibl")).trim());
        updInvoice.setCost(Contents.get(columnIndexMap.get("cost")).trim());
        updInvoice.setItemTxt(Contents.get(columnIndexMap.get("Item-Txt")).trim());
        updInvoice.setKbetr(Contents.get(columnIndexMap.get("KBETR")).trim());
        updInvoice.setUnit(Contents.get(columnIndexMap.get("Unit")).trim());
        //      updInvoice.setKwert(kwert);
        //      updInvoice.setUnit2(unit2);
        updInvoice.setRefdoc(Contents.get(columnIndexMap.get("Ref.doc.")).trim());
        updInvoice.setRefitm(Contents.get(columnIndexMap.get("RefItm")).trim());
        updInvoice.setSalesdoc(Contents.get(columnIndexMap.get("Sales Doc.")).trim());
        updInvoice.setSaty(Contents.get(columnIndexMap.get("SaTy")).trim());
        updInvoice.setOrdrs(Contents.get(columnIndexMap.get("OrdRs")).trim());
        updInvoice.setItca(Contents.get(columnIndexMap.get("ItCa")).trim());
        updInvoice.setShipTo(Contents.get(columnIndexMap.get("Ship-to")).trim());
        updInvoice.setName1(Contents.get(columnIndexMap.get("Name1")).trim());
        updInvoice.setName2(Contents.get(columnIndexMap.get("Name2")).trim());
        updInvoice.setAcc(Contents.get(columnIndexMap.get("Acc.")).trim());
        //      updInvoice.setCcor(cc_Or);
        updInvoice.setProfitCtr(Contents.get(columnIndexMap.get("Profit Ctr")).trim());
        updInvoice.setMpg(Contents.get(columnIndexMap.get("MPG")).trim());
        updInvoice.setBrand(Contents.get(columnIndexMap.get("Brand")).trim());
        updInvoice.setReference(Contents.get(columnIndexMap.get("Reference")).trim());
        updInvoice.setCan(Contents.get(columnIndexMap.get("Can")).trim());
        updInvoice.setCancInv(Contents.get(columnIndexMap.get("Canc.Inv.")).trim());
        updInvoice.setDocumentno(Contents.get(columnIndexMap.get("DocumentNo")).trim());
        updInvoice.setGross(Contents.get(columnIndexMap.get("Gross")).trim());
        updInvoice.setNet(Contents.get(columnIndexMap.get("Net")).trim());
        updInvoice.setBonusId(Contents.get(columnIndexMap.get("Bonus ID")).trim());
        updInvoice.setCampaign(Contents.get(columnIndexMap.get("Campaign")).trim());
        return updInvoice;
    }

    @Override
    public Map<String, String> getInvoiceByParams(Map<String, String> params) {
        String poNumber = "";
        String poNumberStr = "";
        String gitNo = "";
        Map<String, String> map = new HashMap<String, String>();
        List<UpdInvoice> list = sapInvoiceDao.getByParams(params);
        if (Validate.isNullOrEmpty(list)) {
            return map;
        }
        for (UpdInvoice updInvoiceObj : list) {
            poNumberStr = updInvoiceObj.getPoNumber();
            if(!Validate.isNullOrEmpty(poNumberStr)){
                poNumber = poNumberStr.substring(0, poNumberStr.lastIndexOf("天猫"));
                if (!map.containsKey(poNumber)) {
                    gitNo = updInvoiceObj.getGitno();
                } else {
                    gitNo += "," + updInvoiceObj.getGitno();
                }
                map.put(poNumber, gitNo);
            }
        }
        return map;
    }
}
