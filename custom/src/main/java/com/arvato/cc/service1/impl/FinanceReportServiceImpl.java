package com.arvato.cc.service1.impl;

import com.arvato.cc.constant.Constants;
import com.arvato.cc.dao1.*;
import com.arvato.cc.form.AlipayTransModel;
import com.arvato.cc.form.finance.FinanceReport;
import com.arvato.cc.log.LogMessageUtil;
import com.arvato.cc.model.ExpFinacial;
import com.arvato.cc.model.OperationHistory;
import com.arvato.cc.service1.*;
import com.arvato.cc.util.CommonHelper;
import com.arvato.cc.util.FileUtil;
import com.arvato.cc.util.Validate;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: ZHAN405
 * Date: 15-8-20
 * Time: 上午11:27
 * To change this template use File | Settings | File Templates.
 */
@Service
public class FinanceReportServiceImpl implements FinanceReportService {
    private static final Log log = LogFactory.getLog(FinanceReportServiceImpl.class);
    private static LogMessageUtil msg = new LogMessageUtil("FIANANCE_REPORT");
    @Autowired
    private FinanceReportDao financeReportDao;

    @Value("${financeTemplate}")
    private String financeTemplate;

    @Value("${financeReportTempFile}")
    private String financeReportTempFile;


    @Autowired
    private AlipayService alipayService;

    @Autowired
    private TempTradeDao tempTradeDao;

    @Autowired
    private TradeRemarkDao tradeRemarkDao;

    @Autowired
    private OperationLogService operationLogService;

    @Autowired
    private CpdDataService cpdDataService;

    @Autowired
    private OperationLogDao operationLogDao;

    @Autowired
    private CpdDao cpdDao;

    @Autowired
    private AlipayTransService alipayTransService;

    public List<FinanceReport> getFinanceReportList(Map<String, String> queryParams) throws ParseException {
        log.info(msg.getStartMessage("getFinanceReportList"));
//        StringBuffer sb = new StringBuffer(" from ExpFinacial where 1=1");
//        if (StringUtils.isNotBlank( queryParams.get("brand"))) {
//            sb.append(" and storeId =  '" + queryParams.get("brand") + "'");
//        }
//        if (StringUtils.isNotBlank(queryParams.get("startTime"))) {
//            sb.append(" and pricingDate >= '" + queryParams.get("startTime") + "'");
//        }
//        if (StringUtils.isNotBlank( queryParams.get("endTime"))) {
//            sb.append(" and pricingDate <= '" +  queryParams.get("endTime") + "'");
//        }
//        if (StringUtils.isNotBlank(queryParams.get("orderNum"))) {
//            sb.append(" and tid = '" + queryParams.get("orderNum") + "'");
//        }
//        List<ExpFinacial> expFinancialList = financeReportDao.getFinanceReportList(sb.toString());
//        if (Validate.isNullOrEmpty(expFinancialList)) {
//            return null;
//        }
//        //判断alipayList中的订单号是否存在，标红。
//
//        List<FinanceReport> financeReportList = new ArrayList<FinanceReport>();
//        String sgtxt = "";
//        String sgtxt2 = "";
//        String invoiceTitle = "";
//        String createTime = "";
//        String customerId = "";
//        fetch.start("getAlipayTrans");
//        Map<String, AlipayTransModel> alipayMap = alipayTransService.getAlipayTrans(queryParams);
//        fetch.stop();
//
//        AlipayTransModel alipay = null;

//        StringBuffer sb = new StringBuffer("select distinct ef.bldat bldat,ef.budat budat,ef.bkpf bkpf,ef.monat monat,ef.bukrs bukrs,ef.waers waers,ef.newbs newbs,ef.newko newko,ali.SERVICE_SERIAL_NUM serviceSerialNum,ef.zuonr zuonr,ef.invoice_Title invoiceTitle,ali.IN_FEE inFee,ali.CREATE_TIME createTime,ef.ort01 ort01,ef.newbs1 newbs1,ef.wrbtr1 wrbtr1,ef.Zuonr1 Zuonr1,ef.newbs2 newbs2,ef.point_Fee pointFee,ef.zuonr2 zuonr2,ef.store_Id storeId,ef.order_Status orderStatus   from exp_Finacial ef inner join alipay_trans ali on ef.alipay_no = ali.service_serial_num where 1=1 ");
//        if (StringUtils.isNotBlank( queryParams.get("brand"))) {
//            sb.append(" and ef.store_Id =  '" + queryParams.get("brand") + "'");
//        }
//        if (StringUtils.isNotBlank(queryParams.get("startTime"))) {
//            sb.append(" and ef.pricingDate >= '" + queryParams.get("startTime") + "'");
//        }
//        if (StringUtils.isNotBlank( queryParams.get("endTime"))) {
//            sb.append(" and ef.pricingDate <= '" +  queryParams.get("endTime") + "'");
//        }
//        if (StringUtils.isNotBlank(queryParams.get("alipayStartTime"))) {
//            sb.append(" and ali.create_time >= '" + queryParams.get("alipayStartTime") + "'");
//        }
//        if (StringUtils.isNotBlank( queryParams.get("alipayEndTime"))) {
//            sb.append(" and ali.create_time <= '" +  queryParams.get("alipayEndTime") + "'");
//        }
//        if (StringUtils.isNotBlank(queryParams.get("orderNum"))) {
//            sb.append(" and ef.tid = '" + queryParams.get("orderNum") + "'");
//        }
        List<Map<String,Object>> expFinancialMapList = financeReportDao.getFinanceReportListBySQL(queryParams);
        if (Validate.isNullOrEmpty(expFinancialMapList)) {
            return null;
        }
        List<FinanceReport> expFinacialList = convertExpFinancialMapList(expFinancialMapList);
        return expFinacialList;
    }

    private List<FinanceReport> convertExpFinancialMapList(List<Map<String,Object>> expFinancialMapList) throws ParseException {
        List<FinanceReport> financeReportList = new ArrayList<FinanceReport>();
        Map<String, String> cpdMap = cpdDataService.getCpdCode();
        FinanceReport financeReport = null;
        String customerId = "";
        for(Map<String,Object> map : expFinancialMapList) {
            financeReport = new FinanceReport();
            financeReport.setBldat(CommonHelper.TimeParse(map.get("bldat"), "yyyy-MM-dd HH:mm:ss"));//expFinacial.getBldat()
            financeReport.setBudat(CommonHelper.TimeParse(map.get("budat"), "yyyy-MM-dd HH:mm:ss"));//expFinacial.getBudat()
            financeReport.setBkpf(CommonHelper.getString(map.get("bkpf")));//expFinacial.getBkpf()
            financeReport.setMonat(CommonHelper.getString(map.get("monat")));//expFinacial.getMonat()
            financeReport.setBukrs(CommonHelper.getString(map.get("bukrs")));//expFinacial.getBukrs()
            financeReport.setWaers(CommonHelper.getString(map.get("waers")));//expFinacial.getWaers()
            financeReport.setNewbs(CommonHelper.getString(map.get("newbs")));//expFinacial.getNewbs()
            financeReport.setNewko(CommonHelper.getString(map.get("newko")));//expFinacial.getNewko()
            financeReport.setZuonr(CommonHelper.getString(map.get("zuonr")));//expFinacial.getZuonr()
            financeReport.setWrbtr(CommonHelper.getString(map.get("inFee")));//alipay.getInFee()
            financeReport.setCreateTime(CommonHelper.getString(map.get("createTime")));
            financeReport.setSgtxt(CommonHelper.getString(map.get("sgtxt"))); //sgtxt + subInvoiceTitle
            financeReport.setNewbs1(CommonHelper.getString(map.get("newbs1")));//expFinacial.getNewbs1()
            if (!Validate.isNullOrEmpty(cpdMap)) {
                customerId = CommonHelper.getString(cpdMap.get(map.get("ort01")));//expFinacial.getOrt01()
            }
            financeReport.setNewko1(customerId);
            financeReport.setName1(CommonHelper.getString(map.get("name1")));
            financeReport.setOrt01(CommonHelper.getString(map.get("ort01"))); //expFinacial.getOrt01()
            financeReport.setWrbtr1(CommonHelper.getString(map.get("wrbtr1")));//expFinacial.getWrbtr1()
            financeReport.setZuonr1(CommonHelper.getString(map.get("zuonr1")));//expFinacial.getZuonr1()
            financeReport.setSgtxt1(CommonHelper.getString(map.get("sgtxt1")));
            financeReport.setNewbs2(CommonHelper.getString(map.get("newbs2")));//expFinacial.getNewbs2()
            financeReport.setNewko2("6200709245");
            financeReport.setWrbtr2(CommonHelper.getString(CommonHelper.DoubleParse(map.get("pointFee")) / 100));//expFinacial.getPointFee()
            financeReport.setZuonr2(CommonHelper.getString(map.get("zuonr2")));//expFinacial.getZuonr2()
            financeReport.setSgtxt2(CommonHelper.getString(map.get("sgtxt2")));
            financeReport.setOrderStatus(CommonHelper.getString(map.get("orderStatus")));//expFinacial.getOrderStatus()
            financeReport.setHasRefundRemark(CommonHelper.getString(map.get("hasRefundRemark")));
            financeReportList.add(financeReport);
        }
        return financeReportList;
    }

//    private List<FinanceReport> convertExpFinancialMapList(List<Map<String,Object>> expFinancialMapList) throws ParseException {
//        List<FinanceReport> financeReportList = new ArrayList<FinanceReport>();
//        Map<String, String> cpdMap = cpdDataService.getCpdCode();
//        FinanceReport financeReport = null;
//        String invoiceTitle = "";
//        String createTime = "";
//        String sgtxt = "";
//        String sgtxt2 = "";
//        String customerId = "";
//        for(Map<String,Object> map : expFinancialMapList) {
//            financeReport = new FinanceReport();
//            financeReport.setBldat(CommonHelper.TimeParse(map.get("bldat"), "yyyy-MM-dd HH:mm:ss"));//expFinacial.getBldat()
//            financeReport.setBudat(CommonHelper.TimeParse(map.get("budat"), "yyyy-MM-dd HH:mm:ss"));//expFinacial.getBudat()
//            financeReport.setBkpf(CommonHelper.getString(map.get("bkpf")));//expFinacial.getBkpf()
//            financeReport.setMonat(CommonHelper.getString(map.get("monat")));//expFinacial.getMonat()
//            financeReport.setBukrs(CommonHelper.getString(map.get("bukrs")));//expFinacial.getBukrs()
//            financeReport.setWaers(CommonHelper.getString(map.get("waers")));//expFinacial.getWaers()
//            financeReport.setNewbs(CommonHelper.getString(map.get("newbs")));//expFinacial.getNewbs()
//            financeReport.setNewko(CommonHelper.getString(map.get("newko")));//expFinacial.getNewko()
//
//            financeReport.setZuonr(CommonHelper.getString(map.get("zuonr")));//expFinacial.getZuonr()
//            invoiceTitle = CommonHelper.getString(map.get("invoiceTitle"));//expFinacial.getInvoiceTitle()
//            financeReport.setWrbtr(CommonHelper.getString(map.get("inFee")));//alipay.getInFee()
//            createTime = CommonHelper.getString(map.get("createTime"));//alipay.getCreateTime()
//            financeReport.setCreateTime(createTime);
//            if (!"".equals(createTime)) {
//                createTime = CommonHelper.TimeParseToStr(map.get("createTime"), "yyyy-MM-dd").replace("-","");
//            }
//
//            sgtxt = createTime + "收tmall销售款" + CommonHelper.getString(map.get("ort01"));//expFinacial.getOrt01()
//            String subInvoiceTitle = invoiceTitle;
//            if (invoiceTitle.length() > 4) {
//                subInvoiceTitle = invoiceTitle.substring(0, 4);
//            }
//
//
//            financeReport.setSgtxt(sgtxt + subInvoiceTitle);
//            financeReport.setNewbs1(CommonHelper.getString(map.get("newbs1")));//expFinacial.getNewbs1()
//            if (!Validate.isNullOrEmpty(cpdMap)) {
//                customerId = CommonHelper.getString(cpdMap.get(map.get("ort01")));//expFinacial.getOrt01()
//            }
//            financeReport.setNewko1(customerId);
////            if (invoiceTitle.length() > 4) {
////                financeReport.setName1(invoiceTitle.substring(0, 4));
////            } else {
////                financeReport.setName1(invoiceTitle);
////            }
//            financeReport.setName1(subInvoiceTitle);
//            financeReport.setOrt01(CommonHelper.getString(map.get("ort01"))); //expFinacial.getOrt01()
//            financeReport.setWrbtr1(CommonHelper.getString(map.get("wrbtr1")));//expFinacial.getWrbtr1()
//            financeReport.setZuonr1(CommonHelper.getString(map.get("Zuonr1")));//expFinacial.getZuonr1()
//            financeReport.setSgtxt1(sgtxt + invoiceTitle);
//            financeReport.setNewbs2(CommonHelper.getString(map.get("newbs2")));//expFinacial.getNewbs2()
//            financeReport.setNewko2("6200709245");
//            financeReport.setWrbtr2(CommonHelper.getString(CommonHelper.DoubleParse(map.get("pointFee")) / 100));//expFinacial.getPointFee()
//            financeReport.setZuonr2(CommonHelper.getString(map.get("zuonr2")));//expFinacial.getZuonr2()
//            sgtxt2 = sgtxt + invoiceTitle + "/消费积分";//+ "/" + CommonHelper.getDouble(expFinacial.getPointFee()) / 100;
//            if ("22511920".equals(CommonHelper.getString(map.get("storeId")))) { //expFinacial.getStoreId()
////                sgtxt2 = sgtxt + invoiceTitle + "/消费积分/博世";//+ "/" + CommonHelper.getDouble(expFinacial.getPointFee()) / 100 + "/博世";
//                //Y列：20151008收tmall销售款1303186995113156丁水平/消费积分/博世 改成：20151008收tmall销售款1303186995113156丁水平/消费积分博世 2015-10-20
//                sgtxt2 = sgtxt + invoiceTitle + "/消费积分博世";
//            }
//            financeReport.setSgtxt2(sgtxt2);
//            financeReport.setOrderStatus(CommonHelper.getString(map.get("orderStatus")));//expFinacial.getOrderStatus()
//            financeReportList.add(financeReport);
//        }
//        return financeReportList;
//    }

    public Workbook generateFinanceReport(Map<String, String> queryParams) throws ParseException {
        log.info(msg.getStartMessage("generateFinanceReport"));
        StopWatch fetch = new StopWatch("generateFinanceReport");
        fetch.start("getWorkBookByExample");
        Workbook workbook = FileUtil.getWorkBookByExample(financeTemplate);
        SXSSFWorkbook sworkBook = new SXSSFWorkbook((XSSFWorkbook) workbook);
        Sheet sheet = workbook.getSheet("data");
        fetch.stop();
        //获取订单数据
        fetch.start("getFinanceReportList");
        List<FinanceReport> financeList = getFinanceReportList(queryParams);
        fetch.stop();
        if (Validate.isNullOrEmpty(financeList)) {
            return sworkBook;
        }
        //TRADE_FINISHED的数据
        List<FinanceReport> tradeFinishedList = new ArrayList<FinanceReport>();
        //非TRADE_FINISHED的数据
        List<FinanceReport> nonTradeFinishedList = new ArrayList<FinanceReport>();
        for (FinanceReport financeReport : financeList) {
            if (!"TRADE_FINISHED".equals(financeReport.getOrderStatus())) {
                nonTradeFinishedList.add(financeReport);
            } else {
                tradeFinishedList.add(financeReport);
            }
        }
        List<FinanceReport> tradeFinishedSortedList = new ArrayList<FinanceReport>();
        if (!Validate.isNullOrEmpty(tradeFinishedList)) {
            Iterator<FinanceReport> iterator = tradeFinishedList.iterator();
            FinanceReport financeReport = null;
            while (iterator.hasNext()) {
                financeReport = iterator.next();
                if (!"".equals(CommonHelper.getString(financeReport.getCreateTime()))) {
                    tradeFinishedSortedList.add(financeReport);
                    iterator.remove();
                }
            }
   /*         for (FinanceReport financeReportObj : tradeFinishedList) {
                if (!"".equals(CommonHelper.getString(financeReportObj.getCreateTime()))) {
                    tradeFinishedSortedList.add(financeReportObj);
                    tradeFinishedList.remove(financeReportObj);
                }
            }*/
        }
        fetch.start("Collections.sort");
        Collections.sort(tradeFinishedSortedList, new Comparator<FinanceReport>() {
            public int compare(FinanceReport arg0, FinanceReport arg1) {
                String createTime0Str = arg0.getCreateTime();
                String createTime1Str = arg1.getCreateTime();

                Timestamp createTime0 = Timestamp.valueOf(createTime0Str);
                Timestamp createTime1 = Timestamp.valueOf(createTime1Str);
                if (createTime0.after(createTime1)) {
                    return -1;
                }
                return 1;
            }
        });
        if (!Validate.isNullOrEmpty(tradeFinishedList)) {
            tradeFinishedSortedList.addAll(tradeFinishedList);
        }
        fetch.stop();
        fetch.start("createWorkBookSheet1");
        int startIndex = 3;
        createWorkBookSheet(workbook, sheet, startIndex, tradeFinishedSortedList, 0);
        fetch.stop();
        fetch.start("createWorkBookSheet2");
        int otherStartIndex = startIndex + tradeFinishedSortedList.size();
        createWorkBookSheet(workbook, sheet, otherStartIndex, nonTradeFinishedList, tradeFinishedSortedList.size());
//        sworkBook.removeSheetAt(sworkBook.getSheetIndex("style"));
        fetch.stop();
        fetch.start("generateOperationHistory");
        OperationHistory operationHistory = this.operationLogService.generateOperationHistory("", Constants.OperationType.DOWNLOAD_FINANCE.toString(), Constants.ModelType.DOWNLOAD.toString());
        this.operationLogDao.saveOperationLog(operationHistory);
        fetch.stop();
        log.info(msg.getEndMessage("getFinanceReportList\r\n" + fetch.prettyPrint()));
        return sworkBook;
    }

    @Override
    public String generateFinance(Map<String, String> queryParams) throws ParseException {
        log.info(msg.getStartMessage("generateFinanceReport"));
        StopWatch fetch = new StopWatch("generateFinanceReport");
        fetch.start("getWorkBookByExample");
        Workbook workbook = FileUtil.getWorkBookByExample(financeTemplate);
        SXSSFWorkbook sworkBook = new SXSSFWorkbook((XSSFWorkbook) workbook);
        Sheet sheet = workbook.getSheet("data");
        fetch.stop();
        //获取订单数据
        fetch.start("getFinanceReportList");
        List<FinanceReport> financeList = getFinanceReportList(queryParams);
        fetch.stop();
        if (Validate.isNullOrEmpty(financeList)) {
            return "none";
        }
        //TRADE_FINISHED的数据
        List<FinanceReport> tradeFinishedList = new ArrayList<FinanceReport>();
        //非TRADE_FINISHED的数据
        List<FinanceReport> nonTradeFinishedList = new ArrayList<FinanceReport>();
        for (FinanceReport financeReport : financeList) {
            if (!"TRADE_FINISHED".equals(financeReport.getOrderStatus())) {
                nonTradeFinishedList.add(financeReport);
            } else {
                tradeFinishedList.add(financeReport);
            }
        }
        List<FinanceReport> tradeFinishedSortedList = new ArrayList<FinanceReport>();
        if (!Validate.isNullOrEmpty(tradeFinishedList)) {
            Iterator<FinanceReport> iterator = tradeFinishedList.iterator();
            FinanceReport financeReport = null;
            while (iterator.hasNext()) {
                financeReport = iterator.next();
                if (!"".equals(CommonHelper.getString(financeReport.getCreateTime()))) {
                    tradeFinishedSortedList.add(financeReport);
                    iterator.remove();
                }
            }
   /*         for (FinanceReport financeReportObj : tradeFinishedList) {
                if (!"".equals(CommonHelper.getString(financeReportObj.getCreateTime()))) {
                    tradeFinishedSortedList.add(financeReportObj);
                    tradeFinishedList.remove(financeReportObj);
                }
            }*/
        }
        fetch.start("Collections.sort");
        Collections.sort(tradeFinishedSortedList, new Comparator<FinanceReport>() {
            public int compare(FinanceReport arg0, FinanceReport arg1) {
                String createTime0Str = arg0.getCreateTime();
                String createTime1Str = arg1.getCreateTime();

                Timestamp createTime0 = Timestamp.valueOf(createTime0Str);
                Timestamp createTime1 = Timestamp.valueOf(createTime1Str);
                if (createTime0.after(createTime1)) {
                    return -1;
                }
                return 1;
            }
        });
        if (!Validate.isNullOrEmpty(tradeFinishedList)) {
            tradeFinishedSortedList.addAll(tradeFinishedList);
        }
        fetch.stop();
        fetch.start("createWorkBookSheet1");
        int startIndex = 3;
        createWorkBookSheet(workbook, sheet, startIndex, tradeFinishedSortedList, 0);
        fetch.stop();
        fetch.start("createWorkBookSheet2");
        int otherStartIndex = startIndex + tradeFinishedSortedList.size();
        createWorkBookSheet(workbook, sheet, otherStartIndex, nonTradeFinishedList, tradeFinishedSortedList.size());
//        sworkBook.removeSheetAt(sworkBook.getSheetIndex("style"));
        fetch.stop();
        fetch.start("generateOperationHistory");
        OperationHistory operationHistory = this.operationLogService.generateOperationHistory("", Constants.OperationType.DOWNLOAD_FINANCE.toString(), Constants.ModelType.DOWNLOAD.toString());
        this.operationLogDao.saveOperationLog(operationHistory);
        fetch.stop();
        FileOutputStream outputStream = null;
        String fileName = financeReportTempFile + "FinanceReport_"+ CommonHelper.DateFormat(CommonHelper.getThisTimestamp(),"yyyy-MM-dd HH-mm-ss")+".xlsx";
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
        log.info(msg.getEndMessage("getFinanceReportList\r\n" + fetch.prettyPrint()));
        return "";
    }

    public void createWorkBookSheet(Workbook workbook, Sheet sheet, int startIndex, List<FinanceReport> valueList, int columnIndex) {
        CellStyle redColorCellStyle = FileUtil.getCellStyle(workbook, 0, 0);
        CellStyle yellowColorCellStyle = FileUtil.getCellStyle(workbook, 1, 0);
        CellStyle greenColorCellStyle = FileUtil.getCellStyle(workbook, 2, 0);
        for (int i = 0, h = valueList.size(); i < h; i++) {
            Row deliveryRow = sheet.createRow(startIndex);
            FinanceReport FinanceObj = valueList.get(i);
            deliveryRow.createCell(0).setCellValue(i + columnIndex + 1);
            deliveryRow.createCell(1).setCellValue("");
            deliveryRow.createCell(2).setCellValue(CommonHelper.DateFormat(CommonHelper.getThisTimestamp(), "yyyy.MM.dd"));
            deliveryRow.createCell(3).setCellValue(CommonHelper.DateFormat(CommonHelper.getThisTimestamp(), "yyyy.MM.dd"));
            deliveryRow.createCell(4).setCellValue(CommonHelper.getString(FinanceObj.getBkpf()));
            deliveryRow.createCell(5).setCellValue(CommonHelper.DoubleParse(FinanceObj.getMonat()));
            deliveryRow.createCell(6).setCellValue(CommonHelper.getString(FinanceObj.getBukrs()));
            deliveryRow.createCell(7).setCellValue(CommonHelper.getString(FinanceObj.getWaers()));
            deliveryRow.createCell(8).setCellValue(CommonHelper.DoubleParse(FinanceObj.getNewbs()));
            deliveryRow.createCell(9).setCellValue(CommonHelper.DoubleParse(FinanceObj.getNewko()));
            deliveryRow.createCell(10).setCellValue(CommonHelper.DoubleParse(FinanceObj.getWrbtr()));
            deliveryRow.createCell(11).setCellValue(CommonHelper.getString(FinanceObj.getZuonr()));
            deliveryRow.createCell(12).setCellValue(CommonHelper.getString(FinanceObj.getSgtxt()));
            deliveryRow.createCell(13).setCellValue(CommonHelper.DoubleParse(FinanceObj.getNewbs1()));
            deliveryRow.createCell(14).setCellValue(CommonHelper.DoubleParse(FinanceObj.getNewko1()));
            deliveryRow.createCell(15).setCellValue(CommonHelper.getString(FinanceObj.getName1()));
            deliveryRow.createCell(16).setCellValue(CommonHelper.getString(FinanceObj.getOrt01()));
            deliveryRow.createCell(17).setCellValue(CommonHelper.DoubleParse(FinanceObj.getWrbtr1()));
            deliveryRow.createCell(18).setCellValue(CommonHelper.getString(FinanceObj.getZuonr1()));
            deliveryRow.createCell(19).setCellValue(CommonHelper.getString(FinanceObj.getSgtxt1()));
            deliveryRow.createCell(20).setCellValue(CommonHelper.DoubleParse(FinanceObj.getNewbs2()));
            deliveryRow.createCell(21).setCellValue(CommonHelper.DoubleParse(FinanceObj.getNewko2()));
            deliveryRow.createCell(22).setCellValue(CommonHelper.DoubleParse(FinanceObj.getWrbtr2()));
            deliveryRow.createCell(23).setCellValue(CommonHelper.getString(FinanceObj.getZuonr2()));
            deliveryRow.createCell(24).setCellValue(CommonHelper.getString(FinanceObj.getSgtxt2()));

            if (!"TRADE_FINISHED".equals(CommonHelper.getString(FinanceObj.getOrderStatus()))) {
                //setBackRedColor(workbook, sheet, startIndex);
                FileUtil.setCellStyle(sheet, startIndex, 0, redColorCellStyle);
            }

            String wrbtr1 = CommonHelper.getString(FinanceObj.getWrbtr1());
            String wrbtr = CommonHelper.getString(FinanceObj.getWrbtr());
            String wrbtr2 = CommonHelper.getString(FinanceObj.getWrbtr2());

            if (Validate.isNullOrEmpty(wrbtr)||Validate.isNullOrEmpty(wrbtr1)||Validate.isNullOrEmpty(wrbtr2)) {
                //setBackYellowColor(workbook, sheet, startIndex);
                FileUtil.setCellStyle(sheet, startIndex, 1,yellowColorCellStyle);
            }else{
                double wrbtrd=Double.valueOf(wrbtr).doubleValue();
                double wrbtr1d=Double.valueOf(wrbtr1).doubleValue();
                double wrbtr2d=Double.valueOf(wrbtr2).doubleValue();
                if(wrbtr1d+wrbtr2d!=wrbtrd){
                    //setBackYellowColor(workbook, sheet, startIndex);
                    FileUtil.setCellStyle(sheet, startIndex, 1, yellowColorCellStyle);
                }
            }
            if(!Validate.isNullOrEmpty(FinanceObj.getHasRefundRemark())){
                if("1".equals(FinanceObj.getHasRefundRemark())){
                    FileUtil.setCellStyle(sheet, startIndex, 2, greenColorCellStyle);
                }
            }
            startIndex++;
        }
    }

    public static void setBackRedColor(Workbook workbook, Sheet sheet, Integer lineNo) {
//        CellStyle oldCellStyle = sheet.getRow(lineNo).getCell(4).getCellStyle();
        CellStyle cellStyle = workbook.createCellStyle();
//        cellStyle.cloneStyleFrom(oldCellStyle);
        cellStyle.cloneStyleFrom(workbook.getSheet("style").getRow(0).getCell(0).getCellStyle());
        sheet.getRow(lineNo).getCell(0).setCellStyle(cellStyle);
    }


    public static void setBackYellowColor(Workbook workbook, Sheet sheet, Integer lineNo) {
//      CellStyle oldCellStyle = sheet.getRow(lineNo).getCell(4).getCellStyle();
        CellStyle cellStyle = workbook.createCellStyle();
//      cellStyle.cloneStyleFrom(oldCellStyle);
        cellStyle.cloneStyleFrom(workbook.getSheet("style").getRow(1).getCell(0).getCellStyle());
        sheet.getRow(lineNo).getCell(1).setCellStyle(cellStyle);
        /*Row row = sheet.getRow(lineNo);
        int cellNum = row.getPhysicalNumberOfCells();
        for (int i = 1; i < cellNum; i++) {
            row.getCell(i).setCellStyle(cellStyle);
        }*/
    }
}
