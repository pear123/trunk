package com.arvato.cc.service1.impl;

import com.arvato.cc.constant.Constants;
import com.arvato.cc.dao1.*;
import com.arvato.cc.form.finance.DeliveryReport;
import com.arvato.cc.log.LogMessageUtil;
import com.arvato.cc.model.ExpDelivery;
import com.arvato.cc.model.GiftData;
import com.arvato.cc.model.OperationHistory;
import com.arvato.cc.model.TaobaoStore;
import com.arvato.cc.service1.*;
import com.arvato.cc.util.CommonHelper;
import com.arvato.cc.util.FileUtil;
import com.arvato.cc.util.Validate;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ZHAN405
 * Date: 15-8-19
 * Time: 下午1:37
 * To change this template use File | Settings | File Templates.
 */
@Service
public class DeliveryReportServiceImpl implements DeliveryReportService {
    private static final Log log = LogFactory.getLog(DeliveryReportServiceImpl.class);
    private static LogMessageUtil msg = new LogMessageUtil("DELIVERY_REPORT");
    @Autowired
    private DeliveryReportDao deliveryReportDao;

    @Value("${deliveryTemplate}")
    private String deliveryTemplate;

    @Value("${deliveryReportTempFile}")
    private String deliveryReportTempFile;

    @Autowired
    private OperationLogService operationLogService;

    @Autowired
    private CpdDataService cpdDataService;

    @Autowired
    private OperationLogDao operationLogDao;

    @Autowired
    private SapInvoiceDao sapInvoiceDao;

    @Autowired
    private TradeRemarkPresentDao tradeRemarkPresentDao;

    @Autowired
    private TradeRemarkDao tradeRemarkDao;

    @Autowired
    private TempTradeDao tempTradeDao;

    @Autowired
    private GiftDataDao giftDataDao;

    @Autowired
    private CpdDao cpdDao;

    @Autowired
    private UploadInvoiceService uploadInvoiceService;

    @Autowired
    private GiftDataService giftDataService;

    @Autowired
    private TaobaoStoreDao taobaoStoreDao;


    public List<DeliveryReport> getDeliveryReportList(Map<String, String> queryParams) {
        StringBuffer sb = new StringBuffer(" from ExpDelivery where 1=1");
        if (StringUtils.isNotBlank((String) queryParams.get("brand"))) {
            sb.append(" and storeId =  '" + (String) queryParams.get("brand") + "'");
        }
        if (StringUtils.isNotBlank((String) queryParams.get("startTime"))) {
            sb.append(" and pricingDate >= '" + (String) queryParams.get("startTime") + "'");
        }
        if (StringUtils.isNotBlank((String) queryParams.get("endTime"))) {
            sb.append(" and pricingDate <= '" + (String) queryParams.get("endTime") + "'");
        }
        if (StringUtils.isNotBlank((String) queryParams.get("orderNum"))) {
            sb.append(" and orderId =  '" + (String) queryParams.get("orderNum") + "'");
        }
        if (StringUtils.isNotBlank((String) queryParams.get("invoiceNo"))) {
            sb.append(" and invoiceNo =  '" + (String) queryParams.get("invoiceNo") + "'");
        }
        List<ExpDelivery> deliveryList = deliveryReportDao.getDeliveryReportList(
                sb.toString());
        if (Validate.isNullOrEmpty(deliveryList)) {
            return null;
        }
        List<DeliveryReport> deliveryReportList = new ArrayList<DeliveryReport>();
        String invoiceNo = "";
        Map<String, String> invoiceMap = uploadInvoiceService.getInvoiceByParams(queryParams);
        for (ExpDelivery expDelivery : deliveryList) {
            DeliveryReport deliveryReport = new DeliveryReport();
            List<String> invoiceNoList = new ArrayList<String>();
            if (null != invoiceMap) {
                invoiceNo = invoiceMap.get(expDelivery.getOrderId());
                if (!Validate.isNullOrEmpty(invoiceNo)) {
                    String[] invoiceArray = invoiceNo.split(",");
                    if (!Validate.isNullOrEmpty(invoiceArray)) {
                        for (int i = 0; i < invoiceArray.length; i++) {
                            invoiceNoList.add(invoiceArray[i]);
                        }
                    }
                }
            }
            deliveryReport.setStoreId(
                    CommonHelper.getString(expDelivery.getStoreId()));
            deliveryReport.setOrderId(
                    CommonHelper.getString(expDelivery.getOrderId()));
            deliveryReport.setReceiverAddress(
                    CommonHelper.getString(expDelivery.getReceiverAddress()));
            deliveryReport.setReceiverMobile(
                    CommonHelper.getString(expDelivery.getReceiverMobile()));
            deliveryReport.setReceiverName(
                    CommonHelper.getString(expDelivery.getReceiverName()));
            deliveryReport.setReceiverCompany(CommonHelper.getString(expDelivery.getRecevierCompany()));
            deliveryReport.setReceiverPhone(
                    CommonHelper.getString(expDelivery.getRecevierPhone()));

            if (!Validate.isNullOrEmpty(invoiceNo)) {
                deliveryReport.setSendContent("发票" + invoiceNo + CommonHelper.getString(expDelivery.getSendContent()));
                if (!"".equals(CommonHelper.getString(expDelivery.getSendNum())) && invoiceNoList.size() > 0) {
                    deliveryReport.setSendNum(CommonHelper.getString(Integer.valueOf(expDelivery.getSendNum()) + Integer.valueOf(invoiceNoList.size())));
                } else if (!"".equals(CommonHelper.getString(expDelivery.getSendNum()))) {
                    deliveryReport.setSendNum(CommonHelper.getString(expDelivery.getSendNum()));
                }else if (Validate.isNullOrEmpty(CommonHelper.getString(expDelivery.getSendNum()))&&invoiceNoList.size()>0){
                    deliveryReport.setSendNum(invoiceNoList.size()+"");
                }
            } else {
                deliveryReport.setSendContent(CommonHelper.getString(expDelivery.getSendContent()));
                deliveryReport.setSendNum(CommonHelper.getString(expDelivery.getSendNum()));
            }
            deliveryReport.setGiftNum(CommonHelper.getString(
                    expDelivery.getSendNum()));
            deliveryReport.setCustomerID(CommonHelper.getString(expDelivery.getBuyerNick()));
            deliveryReport.setInvoiceTitle(CommonHelper.getString(expDelivery.getInvoiceTitle()));
            deliveryReport.setInvoiceFee(CommonHelper.getString(expDelivery.getInvoiceFee()));
            deliveryReport.setPresentNames(CommonHelper.getString(expDelivery.getPresentNames()));
            deliveryReport.setInvoiceNo(invoiceNo);
            deliveryReportList.add(deliveryReport);
        }
        return deliveryReportList;
    }

    public List<DeliveryReport> getDeliveryReportListByView(Map<String, String> queryParams) {
        List<Map<String,Object>> deliveryList = deliveryReportDao.getDeliveryReportListByView(queryParams);
        if (Validate.isNullOrEmpty(deliveryList)) {
            return null;
        }
        List<DeliveryReport> deliveryReportList = new ArrayList<DeliveryReport>();
        String invoiceNo = "";
        for (Map<String,Object> expDelivery : deliveryList) {
            DeliveryReport deliveryReport = new DeliveryReport();
            deliveryReport.setSellerMemo(
                    CommonHelper.getString(expDelivery.get("seller_memo")));
            deliveryReport.setTradeAddress(
                    CommonHelper.getString(expDelivery.get("RECEIVER_STATE")) +
                    CommonHelper.getString(expDelivery.get("RECEIVER_CITY"))+
                    CommonHelper.getString(expDelivery.get("RECEIVER_DISTRICT"))+
                    CommonHelper.getString(expDelivery.get("trade_address")));
            deliveryReport.setTradeMobile(
                    CommonHelper.getString(expDelivery.get("trade_mobile")));
            deliveryReport.setTradeName(
                    CommonHelper.getString(expDelivery.get("trade_name")));
            deliveryReport.setStoreId(
                    CommonHelper.getString(expDelivery.get("store_id")));
            deliveryReport.setStoreCode(
                    CommonHelper.getString(expDelivery.get("store_code")));
            deliveryReport.setOrderId(
                    CommonHelper.getString(expDelivery.get("ORDER_ID")));
            deliveryReport.setReceiverAddress(
                    CommonHelper.getString(expDelivery.get("RECEIVER_ADDRESS")));
            deliveryReport.setReceiverMobile(
                    CommonHelper.getString(expDelivery.get("RECEIVER_MOBILE")));
            deliveryReport.setReceiverName(
                    CommonHelper.getString(expDelivery.get("RECEIVER_NAME")));
            deliveryReport.setReceiverCompany(
                    CommonHelper.getString(expDelivery.get("RECEVIER_COMPANY")));
            deliveryReport.setReceiverPhone(CommonHelper.getString(expDelivery.get("RECEVIER_PHONE")));
            invoiceNo = CommonHelper.getString(expDelivery.get("invoiceName"));
            if (!Validate.isNullOrEmpty(invoiceNo)) {
                deliveryReport.setSendContent("发票" + invoiceNo + CommonHelper.getString(expDelivery.get("SEND_CONTENT")));
                if (invoiceNo.split(",").length > 0) {
                    deliveryReport.setSendNum(String.valueOf(CommonHelper.IntegerParse(expDelivery.get("SEND_NUM")) + invoiceNo.split(",").length));
                } else if (!"".equals(CommonHelper.getString(expDelivery.get("SEND_NUM")))) {
                    deliveryReport.setSendNum(String.valueOf(CommonHelper.IntegerParse(expDelivery.get("SEND_NUM"))));
                }
            } else {
                deliveryReport.setSendContent(CommonHelper.getString(expDelivery.get("SEND_CONTENT")));
                deliveryReport.setSendNum(CommonHelper.getString(expDelivery.get("SEND_NUM")));
            }
            deliveryReport.setOriginalSendContent(CommonHelper.getString(expDelivery.get("SEND_CONTENT")));
            deliveryReport.setGiftNum(String.valueOf(CommonHelper.IntegerParse(expDelivery.get("SEND_NUM"))));
            deliveryReport.setCustomerID(CommonHelper.getString(expDelivery.get("BUYER_NICK")));
            deliveryReport.setInvoiceTitle(CommonHelper.getString(expDelivery.get("INVOICE_TITLE")));
            deliveryReport.setInvoiceFee(CommonHelper.getString(expDelivery.get("inFee")));
            deliveryReport.setPresentNames(CommonHelper.getString(expDelivery.get("PRESENT_NAME")));
            deliveryReport.setInvoiceNo(invoiceNo);
            deliveryReportList.add(deliveryReport);
        }
        return deliveryReportList;
    }

    public Workbook generateDeliveryReport(Map<String, String> queryParams) {
        log.info(msg.getStartMessage("generateDeliveryReport"));
        StopWatch fetch = new StopWatch("generateDeliveryReport");
        fetch.start("getWorkBookByExample");
        Workbook workbook = FileUtil.getWorkBookByExample(deliveryTemplate);
        SXSSFWorkbook sworkBook = new SXSSFWorkbook((XSSFWorkbook) workbook);
        Sheet sheet = workbook.getSheet("data");
        fetch.stop();
        //获取订单数据
        fetch.start("getDeliveryReportList");
        List<DeliveryReport> deliveryList = getDeliveryReportListByView(queryParams);
        fetch.stop();
        if (Validate.isNullOrEmpty(deliveryList)) {
            return sworkBook;
        }
        int startIndex = 4;
        Row deliveryRow = null;
        deliveryRow = sheet.getRow(0);
        deliveryRow.createCell(0).setCellValue("天猫旗舰店发票和赠品寄送清单");
        deliveryRow = sheet.getRow(1);
        deliveryRow.createCell(0).setCellValue("日期:" + CommonHelper.DateFormat(CommonHelper.getThisTimestamp(), "yyyy年MM月dd日"));

        fetch.start("findAllGiftSku");
        List<GiftData> giftDatas = giftDataService.findAllGiftSku();
        Map<String, String> skuNoMap = new HashMap<String, String>();
        if (!Validate.isNullOrEmpty(giftDatas)) {
            String skuNoKey = "";
            for (GiftData giftDataObj : giftDatas) {
                skuNoKey = CommonHelper.getString(giftDataObj.getMemo()) + CommonHelper.getString(giftDataObj.getBrand().toUpperCase());
                if (!Validate.isNullOrEmpty(skuNoKey)) {
                    skuNoMap.put(skuNoKey, CommonHelper.getString(giftDataObj.getSkuNo()));
                }
            }
        }
        fetch.stop();
        Map<String, Integer> skuCountMap = new HashMap<String, Integer>();
        String presentNameStr;
        String storeId = "";
        fetch.start("for deliveryList");
        for (int i = 0, h = deliveryList.size(); i < h; i++) {
            deliveryRow = sheet.createRow(startIndex);
            DeliveryReport deliveryReportObj = deliveryList.get(i);
            if (null != deliveryReportObj) {
                deliveryRow.createCell(0).setCellValue(CommonHelper.getString(deliveryReportObj.getOrderId()));
                deliveryRow.createCell(1).setCellValue("");
                deliveryRow.createCell(2).setCellValue("");
                storeId = CommonHelper.getString(deliveryReportObj.getStoreCode());
                StringBuffer skuNos = new StringBuffer();
                presentNameStr = CommonHelper.getString(deliveryReportObj.getPresentNames());
                if (!"".equals(presentNameStr)) {
                    String[] names = presentNameStr.split(",");
                    GiftData giftDataObj = null;
                    if (!Validate.isNullOrEmpty(skuNoMap)) {
                        for (int j = 0; j < names.length; j++) {
                            StringBuffer mySkuKey = new StringBuffer();
                            mySkuKey.append(names[j]);
                            mySkuKey.append(storeId);
                            if (skuNoMap.containsKey(mySkuKey.toString())) {
                                skuNos.append(CommonHelper.getString(skuNoMap.get(mySkuKey.toString())));
                                if (skuCountMap.containsKey(skuNoMap.get(mySkuKey.toString()))) {
                                    skuCountMap.put(skuNoMap.get(mySkuKey.toString()), skuCountMap.get(skuNoMap.get(mySkuKey.toString())) + 1);
                                } else {
                                    skuCountMap.put(skuNoMap.get(mySkuKey.toString()), 1);
                                }
                                skuNos.append(" ");
                            }
                        }
                    }
                }
                  /*    if (!CollectionUtils.isEmpty(giftDatas)) {
                        for (int j = 0; j < names.length; j++) {
                            for (int k = 0; k < giftDatas.size(); k++) {
                                giftDataObj = giftDatas.get(k);
                                if (giftDataObj.getMemo().equals(names[j]) && CommonHelper.getString(giftDataObj.getBrand()).equals(storeId)) {
                                    skuNos.append(CommonHelper.getString(giftDataObj.getSkuNo()));
                                    if (skuCountMap.containsKey(giftDataObj.getSkuNo())) {
                                        skuCountMap.put(giftDataObj.getSkuNo(), skuCountMap.get(giftDataObj.getSkuNo()) + 1);
                                    } else {
                                        skuCountMap.put(giftDataObj.getSkuNo(), 1);
                                    }
                                    skuNos.append(" ");
                                }
                            }
                        }
                    }*/
                deliveryRow.createCell(3).setCellValue(CommonHelper.getString(deliveryReportObj.getReceiverAddress()));
                deliveryRow.createCell(4).setCellValue(CommonHelper.getString(deliveryReportObj.getReceiverPhone()));
                deliveryRow.createCell(5).setCellValue(CommonHelper.getString(deliveryReportObj.getReceiverName()));
                deliveryRow.createCell(6).setCellValue(CommonHelper.getString(deliveryReportObj.getReceiverCompany()));
                deliveryRow.createCell(7).setCellValue(CommonHelper.getString(deliveryReportObj.getReceiverMobile()));
               /* deliveryRow.createCell(8).setCellValue("");*/
                deliveryRow.createCell(9).setCellValue(CommonHelper.getString(deliveryReportObj.getSendContent()));
                deliveryRow.createCell(10).setCellValue(CommonHelper.getString(deliveryReportObj.getSendNum()));
     /*           for (int j = 11; j < 48; j++) {
                    deliveryRow.createCell(j).setCellValue("");
                }*/
                deliveryRow.createCell(48).setCellValue(CommonHelper.getString(deliveryReportObj.getCustomerID()));
                deliveryRow.createCell(49).setCellValue(CommonHelper.getString(deliveryReportObj.getInvoiceTitle()));
                deliveryRow.createCell(50).setCellValue(CommonHelper.getString(deliveryReportObj.getInvoiceFee()));
                deliveryRow.createCell(51).setCellValue(skuNos.toString());
                deliveryRow.createCell(52).setCellValue(CommonHelper.getString(deliveryReportObj.getGiftNum()));
                deliveryRow.createCell(53).setCellValue(CommonHelper.getString(deliveryReportObj.getSellerMemo()));
                deliveryRow.createCell(54).setCellValue(CommonHelper.getString(deliveryReportObj.getTradeAddress()));
                deliveryRow.createCell(55).setCellValue(CommonHelper.getString(deliveryReportObj.getTradeMobile()));
                deliveryRow.createCell(56).setCellValue(CommonHelper.getString(deliveryReportObj.getTradeName()));

                //如果匹配不到发票号或寄送地址或电话或礼品，则红底高亮显示
                String invoiceNoStr = CommonHelper.getString(deliveryReportObj.getInvoiceNo());
                String receiverAddress = CommonHelper.getString(deliveryReportObj.getReceiverAddress());
                String mobile = CommonHelper.getString(deliveryReportObj.getReceiverMobile());
                String giftNum = CommonHelper.getString(deliveryReportObj.getGiftNum());

                int cellNum = deliveryRow.getPhysicalNumberOfCells();
                Cell cell = null;
                CellStyle cellStyle = null;
                if ("".equals(invoiceNoStr) || "".equals(receiverAddress) || "".equals(mobile) || "".equals(giftNum)) {
                    //取模板中的样式
                    setBackRedColor(workbook, sheet, startIndex);
                }
                startIndex++;
            }
        }

        startIndex++;
        deliveryRow = sheet.createRow(startIndex);
        deliveryRow.createCell(51).setCellValue("礼品物料号");
        deliveryRow.createCell(52).setCellValue("合计");
        startIndex++;
        if (!Validate.isNullOrEmpty(skuCountMap)) {
            for (String key : skuCountMap.keySet()) {
                deliveryRow = sheet.createRow(startIndex);
                deliveryRow.createCell(51).setCellValue(key);
                deliveryRow.createCell(52).setCellValue(CommonHelper.getString(skuCountMap.get(key)));
                startIndex++;
            }
        }
//        sworkBook.removeSheetAt(sworkBook.getSheetIndex("style"));
        fetch.stop();
        fetch.start("generateOperationHistory");
        OperationHistory operationHistory = this.operationLogService.generateOperationHistory("", Constants.OperationType.DOWNLOAD_BILL.toString(), Constants.ModelType.DOWNLOAD.toString());
        this.operationLogDao.saveOperationLog(operationHistory);
        fetch.stop();
        log.info(msg.getEndMessage("generateDeliveryReport\r\n" + fetch.prettyPrint()));
        return sworkBook;
    }

    @Override
    public String generateDelivery(Map<String, String> queryParams) {
        log.info(msg.getStartMessage("generateDeliveryReport"));
        StopWatch fetch = new StopWatch("generateDeliveryReport");
        fetch.start("getWorkBookByExample");
        Workbook workbook = FileUtil.getWorkBookByExample(deliveryTemplate);
        SXSSFWorkbook sworkBook = new SXSSFWorkbook((XSSFWorkbook) workbook);
        Sheet sheet = workbook.getSheet("data");
        fetch.stop();
        //获取订单数据
        fetch.start("getDeliveryReportList");
        List<DeliveryReport> deliveryList = getDeliveryReportListByView(queryParams);
        fetch.stop();
        if (Validate.isNullOrEmpty(deliveryList)) {
            return "none";
        }
        int startIndex = 4;
        Row deliveryRow = null;
        deliveryRow = sheet.getRow(0);
        deliveryRow.createCell(0).setCellValue("天猫旗舰店发票和赠品寄送清单");
        deliveryRow = sheet.getRow(1);
        //mod by songsong start
        String storeIdTitle=(String) queryParams.get("brand");
        if (Validate.isNullOrEmpty(storeIdTitle)){
            deliveryRow.createCell(0).setCellValue("日期:" + CommonHelper.DateFormat(CommonHelper.getThisTimestamp(), "yyyy年MM月dd日"));
        }else{
            if(!Validate.isNullOrEmpty(storeIdTitle)) {
                TaobaoStore taobaoStore = taobaoStoreDao.getStoreNameByCode(storeIdTitle);
                storeIdTitle = taobaoStore.getName() ;
            }
            deliveryRow.createCell(0).setCellValue("日期:" + CommonHelper.DateFormat(CommonHelper.getThisTimestamp(), "yyyy年MM月dd日")+"("+storeIdTitle+")");
        }
        //mod by songsong end
        fetch.start("findAllGiftSku");
        List<GiftData> giftDatas = giftDataService.findAllGiftSku();
        Map<String, String> skuNoMap = new HashMap<String, String>();
        if (!Validate.isNullOrEmpty(giftDatas)) {
            String skuNoKey = "";
            for (GiftData giftDataObj : giftDatas) {
                skuNoKey = CommonHelper.getString(giftDataObj.getMemo()) + CommonHelper.getString(giftDataObj.getBrand().toUpperCase());
                if (!Validate.isNullOrEmpty(skuNoKey)) {
                    skuNoMap.put(skuNoKey, CommonHelper.getString(giftDataObj.getSkuNo()));
                }
            }
        }
        fetch.stop();
        Map<String, Integer> skuCountMap = new HashMap<String, Integer>();
        String presentNameStr;
        String storeId = "";
        String noneGiftNo = "";
        boolean noneGift = true;
        fetch.start("for deliveryList");
        CellStyle invoiceNoCellStyle = FileUtil.getCellStyle(workbook,1,0);
        CellStyle receiverAddressCellStyle = FileUtil.getCellStyle(workbook,2,0);
        CellStyle mobileCellStyle = FileUtil.getCellStyle(workbook,2,0);
        CellStyle giftNumCellStyle = FileUtil.getCellStyle(workbook,3,0);
        CellStyle noneGiftCellStyle = FileUtil.getCellStyle(workbook,4,0);
        CellStyle formatCellStyle = FileUtil.getCellStyle(workbook,5,0);

        for (int i = 0, h = deliveryList.size(); i < h; i++) {
            noneGiftNo = "";
            noneGift = true;
            deliveryRow = sheet.createRow(startIndex);
            DeliveryReport deliveryReportObj = deliveryList.get(i);
            if (null != deliveryReportObj) {
                deliveryRow.createCell(0).setCellValue(CommonHelper.getString(deliveryReportObj.getOrderId()));
                deliveryRow.createCell(1).setCellValue("");
                deliveryRow.createCell(2).setCellValue("");
                storeId = CommonHelper.getString(deliveryReportObj.getStoreCode());
                StringBuffer skuNos = new StringBuffer();
                presentNameStr = CommonHelper.getString(deliveryReportObj.getPresentNames());
                if (!"".equals(presentNameStr)) {
                    noneGift = false;
                    String[] names = presentNameStr.split(",");
                    GiftData giftDataObj = null;
                    if (!Validate.isNullOrEmpty(skuNoMap)) {
                        for (int j = 0; j < names.length; j++) {
                            StringBuffer mySkuKey = new StringBuffer();
                            mySkuKey.append(names[j]);
                            mySkuKey.append(storeId);
                            if (skuNoMap.containsKey(mySkuKey.toString())) {
                                String giftNo = CommonHelper.getString(skuNoMap.get(mySkuKey.toString()));
                                if(!Validate.isNullOrEmpty(giftNo)){
                                    skuNos.append(giftNo);
                                    if(noneGiftNo.equals("0")){ //有不存在的，则
                                        noneGiftNo = "0";
                                    }else {
                                        noneGiftNo = "1";
                                    }
                                }else {
                                    noneGiftNo = "0";
                                }
                                String sendContent = deliveryReportObj.getSendContent();
                                int index = sendContent.indexOf(names[j]) + names[j].length();
                                int quantity = CommonHelper.IntegerParse(sendContent.substring(index, index + 1));
                                if (skuCountMap.containsKey(skuNoMap.get(mySkuKey.toString()))) {
                                    skuCountMap.put(skuNoMap.get(mySkuKey.toString()), skuCountMap.get(skuNoMap.get(mySkuKey.toString())) + quantity);
                                } else {
                                    skuCountMap.put(skuNoMap.get(mySkuKey.toString()), quantity);
                                }
                                skuNos.append(" ");
                            }else {
                                noneGiftNo = "0";
                            }
                        }
                    }
                }
                  /*    if (!CollectionUtils.isEmpty(giftDatas)) {
                        for (int j = 0; j < names.length; j++) {
                            for (int k = 0; k < giftDatas.size(); k++) {
                                giftDataObj = giftDatas.get(k);
                                if (giftDataObj.getMemo().equals(names[j]) && CommonHelper.getString(giftDataObj.getBrand()).equals(storeId)) {
                                    skuNos.append(CommonHelper.getString(giftDataObj.getSkuNo()));
                                    if (skuCountMap.containsKey(giftDataObj.getSkuNo())) {
                                        skuCountMap.put(giftDataObj.getSkuNo(), skuCountMap.get(giftDataObj.getSkuNo()) + 1);
                                    } else {
                                        skuCountMap.put(giftDataObj.getSkuNo(), 1);
                                    }
                                    skuNos.append(" ");
                                }
                            }
                        }
                    }*/
                deliveryRow.createCell(3).setCellValue(CommonHelper.getString(deliveryReportObj.getReceiverAddress()));
                deliveryRow.createCell(4).setCellValue(CommonHelper.getString(deliveryReportObj.getReceiverMobile()));
                deliveryRow.createCell(5).setCellValue(CommonHelper.getString(deliveryReportObj.getReceiverName()));
                deliveryRow.createCell(6).setCellValue(CommonHelper.getString(deliveryReportObj.getReceiverCompany()));
                deliveryRow.createCell(7).setCellValue(CommonHelper.getString(deliveryReportObj.getReceiverPhone()));
               /* deliveryRow.createCell(8).setCellValue("");*/
                deliveryRow.createCell(9).setCellValue(CommonHelper.getString(deliveryReportObj.getSendContent()));
                deliveryRow.createCell(10).setCellValue(CommonHelper.IntegerParse(deliveryReportObj.getSendNum()));
     /*           for (int j = 11; j < 48; j++) {
                    deliveryRow.createCell(j).setCellValue("");
                }*/
                deliveryRow.createCell(48).setCellValue(CommonHelper.getString(deliveryReportObj.getCustomerID()));
                deliveryRow.createCell(49).setCellValue(CommonHelper.getString(deliveryReportObj.getInvoiceNo()));//发票号列
                deliveryRow.createCell(50).setCellValue(CommonHelper.getString(
                        deliveryReportObj.getInvoiceTitle()));
                deliveryRow.createCell(51).setCellValue(CommonHelper.DoubleParse(
                        deliveryReportObj.getInvoiceFee()));
                deliveryRow.createCell(52).setCellValue(skuNos.toString());
                String giftNums=deliveryReportObj.getGiftNum();
                String pname="";
                if(!Validate.isNullOrEmpty(giftNums)&&Integer.valueOf(giftNums)>0){
                    pname=deliveryReportObj.getOriginalSendContent();
                }
                deliveryRow.createCell(53).setCellValue(pname);//礼品名称列
                deliveryRow.createCell(54).setCellValue(CommonHelper.IntegerParse(deliveryReportObj.getGiftNum()));
                deliveryRow.createCell(55).setCellValue(CommonHelper.getString(deliveryReportObj.getSellerMemo()));
                deliveryRow.createCell(56).setCellValue(CommonHelper.getString(deliveryReportObj.getTradeAddress()));
                deliveryRow.createCell(57).setCellValue(CommonHelper.getString(deliveryReportObj.getTradeMobile()));
                deliveryRow.createCell(58).setCellValue(CommonHelper.getString(deliveryReportObj.getTradeName()));
                //如果匹配不到发票号或寄送地址或电话或礼品，则红底高亮显示
                String invoiceNoStr = CommonHelper.getString(deliveryReportObj.getInvoiceNo());
                String receiverAddress = CommonHelper.getString(deliveryReportObj.getReceiverAddress());
                String mobile = CommonHelper.getString(deliveryReportObj.getReceiverMobile());
                String giftNum = CommonHelper.getString(deliveryReportObj.getGiftNum());
                String sellerMemo = CommonHelper.getString(deliveryReportObj.getSellerMemo());

                int cellNum = deliveryRow.getPhysicalNumberOfCells();
                Cell cell = null;
                CellStyle cellStyle = null;
                //mod by songsong start
                if (Validate.isNullOrEmpty(invoiceNoStr)){
//                    setBackColor(workbook, sheet, startIndex,1,0,9);
                    FileUtil.setCellStyle(sheet, startIndex, 9, invoiceNoCellStyle);

                }
                if (Validate.isNullOrEmpty(receiverAddress)){
//                    setBackColor(workbook, sheet, startIndex,2,0,3);
                    FileUtil.setCellStyle(sheet, startIndex, 3, receiverAddressCellStyle);
                }
                if (Validate.isNullOrEmpty(mobile)){
//                    setBackColor(workbook, sheet, startIndex,2,0,4);
                    FileUtil.setCellStyle(sheet, startIndex, 4, mobileCellStyle);
                }
                if (giftNum.equals("0")){
//                    setBackColor(workbook, sheet, startIndex,3,0,54);
                    FileUtil.setCellStyle(sheet, startIndex, 54, giftNumCellStyle);
                }
                //mod by songsong end
//                if ("".equals(invoiceNoStr) || "".equals(receiverAddress) || "".equals(mobile) || "".equals(giftNum)) {
//                    //取模板中的样式
//                    setBackRedColor(workbook, sheet, startIndex);
//                }
                if(!noneGift && noneGiftNo.equals("0")){
//                    setBackColor(workbook, sheet, startIndex,4,0,52);
                    FileUtil.setCellStyle(sheet, startIndex, 52, noneGiftCellStyle);
                }

                //商家备注2判断
                boolean flag=true;
                if(!Validate.isNullOrEmpty(sellerMemo)){
                    String[] split=sellerMemo.split("[；;]");
                    if(split!=null && split.length==5){
                        if(split[1].startsWith("2、")){
                            String invoiceSendAddr=split[1].replaceFirst("2、", "");
                            if(!Validate.isNullOrEmpty(invoiceSendAddr)){
                                String[] addrSplit=invoiceSendAddr.split("[，,]");
                                if(addrSplit!=null && addrSplit.length==3){
                                    flag=false;
                                }
                            }
                        }
                    }
                }
                if(flag){
//                    setBackColor(workbook, sheet, startIndex,5,0,55);
                    FileUtil.setCellStyle(sheet, startIndex, 55, formatCellStyle);
                }

                startIndex++;
            }
        }

        startIndex++;
        deliveryRow = sheet.createRow(startIndex);
        deliveryRow.createCell(52).setCellValue("礼品物料号");
        deliveryRow.createCell(54).setCellValue("合计");
        startIndex++;
        if (!Validate.isNullOrEmpty(skuCountMap)) {
            for (String key : skuCountMap.keySet()) {
                deliveryRow = sheet.createRow(startIndex);
                deliveryRow.createCell(52).setCellValue(key);
                deliveryRow.createCell(54).setCellValue(CommonHelper.getString(skuCountMap.get(key)));
                startIndex++;
            }
        }
//        sworkBook.removeSheetAt(sworkBook.getSheetIndex("style"));
        fetch.stop();
        fetch.start("generateOperationHistory");
        OperationHistory operationHistory = this.operationLogService.generateOperationHistory("", Constants.OperationType.DOWNLOAD_BILL.toString(), Constants.ModelType.DOWNLOAD.toString());
        this.operationLogDao.saveOperationLog(operationHistory);
        fetch.stop();
        FileOutputStream outputStream = null;
        String fileName = deliveryReportTempFile + "DeliveryReport_"+ CommonHelper.DateFormat(CommonHelper.getThisTimestamp(),"yyyy-MM-dd HH-mm-ss")+".xlsx";
        try{
            outputStream = new FileOutputStream(fileName);
            sworkBook.setActiveSheet(0);
            sworkBook.getSheetAt(0).setSelected(true);
            sworkBook.write(outputStream);
            return fileName;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if(outputStream!=null){
                    outputStream.close();
                }
            }catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        log.info(msg.getEndMessage("generateDeliveryReport\r\n" + fetch.prettyPrint()));
        return "";
    }

    public static void setBackRedColor(Workbook workbook, Sheet sheet, Integer lineNo) {
//      CellStyle oldCellStyle = sheet.getRow(lineNo).getCell(4).getCellStyle();
        CellStyle cellStyle = workbook.createCellStyle();
//      cellStyle.cloneStyleFrom(oldCellStyle);
        cellStyle.cloneStyleFrom(workbook.getSheet("style").getRow(0).getCell(0).getCellStyle());
        sheet.getRow(lineNo).getCell(0).setCellStyle(cellStyle);
    }

    public static void setBackColor(Workbook workbook, Sheet sheet, Integer lineNo,Integer rowNum,Integer colNum,Integer dataColNum) {
//      CellStyle oldCellStyle = sheet.getRow(lineNo).getCell(4).getCellStyle();
        CellStyle cellStyle = workbook.createCellStyle();
//      cellStyle.cloneStyleFrom(oldCellStyle);
        cellStyle.cloneStyleFrom(workbook.getSheet("style").getRow(rowNum).getCell(colNum).getCellStyle());
        sheet.getRow(lineNo).getCell(dataColNum).setCellStyle(cellStyle);
    }
}
