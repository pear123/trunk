package com.arvato.cc.service1.impl;

import com.arvato.cc.constant.Constants;
import com.arvato.cc.dao1.JdpTradeDao;
import com.arvato.cc.dao1.JstTradeDao;
import com.arvato.cc.dao1.OperationLogDao;
import com.arvato.cc.dao1.RestFinanceReportDao;
import com.arvato.cc.form.finance.RestFinanceReport;
import com.arvato.cc.log.LogMessageUtil;
import com.arvato.cc.model.Alipay;
import com.arvato.cc.model.OperationHistory;
import com.arvato.cc.service1.OperationLogService;
import com.arvato.cc.service1.RestFinanceReportService;
import com.arvato.cc.util.CommonHelper;
import com.arvato.cc.util.FileUtil;
import com.arvato.cc.util.Validate;
import com.arvato.jdf.dao.Page;
import org.apache.commons.collections.CollectionUtils;
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
import java.util.*;

@Service
public class RestFinanceReportServiceImpl implements RestFinanceReportService {

    private static final Log log = LogFactory.getLog(RestFinanceReportServiceImpl.class);
    private static LogMessageUtil msg = new LogMessageUtil("RESTFINANCE_REPORT");
    @Autowired
    private RestFinanceReportDao restFinanceReportDao;

    @Value("${restFinanceTemplate}")
    private String restFinanceTemplate;

    @Value("${restFinanceTempFile}")
    private String restFinanceTempFile;

    @Autowired
    private JdpTradeDao jdpTradeDao;

    @Autowired
    private JstTradeDao jstTradeDao;

    @Autowired
    private OperationLogService operationLogService;

    @Autowired
    private OperationLogDao operationLogDao;

    public List<RestFinanceReport> getAlipayReportList(Map<String, String> queryParams) {
        StringBuffer sb = new StringBuffer(" from Alipay where tradeType=2");
        if (StringUtils.isNotBlank((String) queryParams.get("brand"))) {
            sb.append(" and storeId =  '" + (String) queryParams.get("brand") + "'");
        }
        if (StringUtils.isNotBlank((String) queryParams.get("startTime"))) {
            sb.append(" and createTime > '" + (String) queryParams.get("startTime") + "'");
        }
        if (StringUtils.isNotBlank((String) queryParams.get("endTime"))) {
            sb.append(" and createTime < '" + (String) queryParams.get("endTime") + "'");
        }
        List<Alipay> alipayList = restFinanceReportDao.getAlipayReportList(
                sb.toString());
        if (Validate.isNullOrEmpty(alipayList)) {
            return null;
        }
        //判断alipayList中的订单号是否存在，标红。

        List<RestFinanceReport> restFinanceReportList = new ArrayList<RestFinanceReport>();

        for (Alipay alipay : alipayList) {
            RestFinanceReport restFinanceReport = new RestFinanceReport();
            restFinanceReport.setFinancialSerialNum(alipay.getFinancialSerialNum());
            restFinanceReport.setServiceSerialNum(CommonHelper.getString(alipay.getServiceSerialNum()));
            String orderNum = alipay.getOrderNum();
            restFinanceReport.setOrderNum(orderNum);
            restFinanceReport.setGoodsName(alipay.getGoodsName());
            restFinanceReport.setCreateTime(alipay.getCreateTime());
            restFinanceReport.setAccount(alipay.getAccount());
            restFinanceReport.setInFee(alipay.getInFee());
            restFinanceReport.setOutFee(alipay.getOutFee());
            restFinanceReport.setBalance(alipay.getBalance());
            restFinanceReport.setMode(alipay.getMode());
            restFinanceReport.setType(alipay.getType());
            restFinanceReport.setMemo(alipay.getMemo());
            restFinanceReportList.add(restFinanceReport);
        }
        return restFinanceReportList;
    }

    public List<RestFinanceReport> getNonClosedAndFinishedFinanceList(Map<String, String> queryParams) {
        StringBuffer sb = new StringBuffer("SELECT DISTINCT {a.*} FROM alipay a,exp_order o WHERE a.trade_Type=1 AND a.service_serial_num=o.alipay_no AND a.in_fee >0 AND o.trade_status NOT IN('TRADE_CLOSED','TRADE_FINISHED')");
        if (StringUtils.isNotBlank((String) queryParams.get("brand"))) {
            sb.append(" and a.store_Id =  '" + (String) queryParams.get("brand") + "'");
        }
        if (StringUtils.isNotBlank((String) queryParams.get("startTime"))) {
            sb.append(" and a.create_Time > '" + (String) queryParams.get("startTime") + "'");
        }
        if (StringUtils.isNotBlank((String) queryParams.get("endTime"))) {
            sb.append(" and a.create_Time < '" + (String) queryParams.get("endTime") + "'");
        }
        List<Alipay> alipayList = restFinanceReportDao.getAlipayRerportListBySql(
                sb.toString());
        if (Validate.isNullOrEmpty(alipayList)) {
            return null;
        }

        List<RestFinanceReport> restFinanceReportList = new ArrayList<RestFinanceReport>();

        for (Alipay alipay : alipayList) {
            RestFinanceReport restFinanceReport = new RestFinanceReport();
            restFinanceReport.setFinancialSerialNum(alipay.getFinancialSerialNum());
            restFinanceReport.setServiceSerialNum(CommonHelper.getString(alipay.getServiceSerialNum()));
            String orderNum = alipay.getOrderNum();
            restFinanceReport.setOrderNum(orderNum);
            restFinanceReport.setGoodsName(alipay.getGoodsName());
            restFinanceReport.setCreateTime(alipay.getCreateTime());
            restFinanceReport.setAccount(alipay.getAccount());
            restFinanceReport.setInFee(alipay.getInFee());
            restFinanceReport.setOutFee(alipay.getOutFee());
            restFinanceReport.setBalance(alipay.getBalance());
            restFinanceReport.setMode(alipay.getMode());
            restFinanceReport.setType(alipay.getType());
            restFinanceReport.setMemo(alipay.getMemo());
            restFinanceReportList.add(restFinanceReport);
        }
        return restFinanceReportList;
    }


    public List<RestFinanceReport> getVirtualFinanceList(Map<String, String> queryParams) {
        StringBuffer sb = new StringBuffer("SELECT DISTINCT {a.*} FROM alipay a,jst_trade o WHERE a.trade_Type=1 AND a.service_serial_num=o.alipay_no AND a.in_fee >0  and o.shipping_type='").append(
                Constants.ShippingType.virtual).append("'");
        if (StringUtils.isNotBlank((String) queryParams.get("brand"))) {
            sb.append(" and a.store_Id =  '" + (String) queryParams.get("brand") + "'");
        }
        if (StringUtils.isNotBlank((String) queryParams.get("startTime"))) {
            sb.append(" and a.create_Time > '" + (String) queryParams.get("startTime") + "'");
        }
        if (StringUtils.isNotBlank((String) queryParams.get("endTime"))) {
            sb.append(" and a.create_Time < '" + (String) queryParams.get("endTime") + "'");
        }
        List<Alipay> alipayList = restFinanceReportDao.getAlipayRerportListBySql(
                sb.toString());
        if (Validate.isNullOrEmpty(alipayList)) {
            return null;
        }

        List<RestFinanceReport> restFinanceReportList = new ArrayList<RestFinanceReport>();

        for (Alipay alipay : alipayList) {
            RestFinanceReport restFinanceReport = new RestFinanceReport();
            restFinanceReport.setFinancialSerialNum(alipay.getFinancialSerialNum());
            restFinanceReport.setServiceSerialNum(CommonHelper.getString(alipay.getServiceSerialNum()));
            String orderNum = alipay.getOrderNum();
            restFinanceReport.setOrderNum(orderNum);
            restFinanceReport.setGoodsName(alipay.getGoodsName());
            restFinanceReport.setCreateTime(alipay.getCreateTime());
            restFinanceReport.setAccount(alipay.getAccount());
            restFinanceReport.setInFee(alipay.getInFee());
            restFinanceReport.setOutFee(alipay.getOutFee());
            restFinanceReport.setBalance(alipay.getBalance());
            restFinanceReport.setMode(alipay.getMode());
            restFinanceReport.setType(alipay.getType());
            restFinanceReport.setMemo(alipay.getMemo());
            restFinanceReportList.add(restFinanceReport);
        }
        return restFinanceReportList;
    }

    public Page<RestFinanceReport> getFinanceQueryListByPage(Page page, Map<String, Object> queryParams) {
        StringBuffer sb = new StringBuffer(" from Alipay where 1=1 and tradeType = 1");
        if (StringUtils.isNotBlank((String) queryParams.get("store"))) {
            sb.append(" and storeId = '" + (String) queryParams.get("store") + "'");
        }
        if (StringUtils.isNotBlank((String) queryParams.get("startTime"))) {
            sb.append(" and createTime >'" + (String) queryParams.get("startTime") + "'");
        }
        if (StringUtils.isNotBlank((String) queryParams.get("endTime"))) {
            sb.append(" and createTime <'" + (String) queryParams.get("endTime") + "'");
        }
        Page<Alipay> accountPage = restFinanceReportDao.findByPage(page, sb.toString());
        page.setRecordCount(accountPage.getRecordCount());

        List<Alipay> queryAlipays = accountPage.getResult();
        Page<RestFinanceReport> resultPage = page;
        List<RestFinanceReport> vlist = new ArrayList<RestFinanceReport>();
        if (null != queryAlipays && queryAlipays.size() > 0) {
            for (Alipay alipay : queryAlipays) {
                RestFinanceReport restFinanceReport = new RestFinanceReport();
                restFinanceReport.setFinancialSerialNum(alipay.getFinancialSerialNum());
                restFinanceReport.setOrderNum(alipay.getOrderNum());
                restFinanceReport.setGoodsName(alipay.getGoodsName());
                restFinanceReport.setCreateTime(alipay.getCreateTime());
                restFinanceReport.setAccount(alipay.getAccount());
                restFinanceReport.setInFee(alipay.getInFee());
                restFinanceReport.setOutFee(alipay.getOutFee());
                restFinanceReport.setBalance(alipay.getBalance());
                restFinanceReport.setMode(alipay.getMode());
                restFinanceReport.setType(alipay.getType());
                restFinanceReport.setMemo(alipay.getMemo());
                vlist.add(restFinanceReport);
            }
        }
        resultPage.setResult(vlist);
        return resultPage;
    }


    public Workbook generateRestFinanceReport(Map<String, String> queryParams) {
        log.info(msg.getStartMessage("generateRestFinanceReport"));
        StopWatch fetch = new StopWatch("generateRestFinanceReport");
        fetch.start("getWorkBookByExample");
        Workbook workbook = FileUtil.getWorkBookByExample(restFinanceTemplate);
        SXSSFWorkbook sworkBook = new SXSSFWorkbook((XSSFWorkbook) workbook);
        Sheet sheet = sworkBook.getSheet("data");
        List<RestFinanceReport> restFinanceList = getAlipayReportList(
                queryParams);
        if (Validate.isNullOrEmpty(restFinanceList)) {
            return sworkBook;
        }
        fetch.stop();
        int startIndex = 1;
        Row deliveryRow = null;
        //取出订单表中所有的订单号
        fetch.start("findAllAliayNos");
        List<Map<String, Object>> alipayNoList = jstTradeDao.findAllAliayNos();
        fetch.stop();
        Set<String> alipayNoSet = new HashSet<String>();
        fetch.start("restFinanceList");
        if (!CollectionUtils.isEmpty(alipayNoList)) {
            for (Map<String, Object> valueMap : alipayNoList) {
                String alipayNoStr = valueMap.get("alipay_no").toString();
                alipayNoSet.add(alipayNoStr);
            }
        }
        for (int i = 0; i < restFinanceList.size(); i++) {
            deliveryRow = sheet.createRow(startIndex);
            RestFinanceReport restFinanceReportObj = restFinanceList.get(i);
            if (null != restFinanceReportObj) {
                deliveryRow.createCell(0).setCellValue(CommonHelper.getString(restFinanceReportObj.getFinancialSerialNum()));
                deliveryRow.createCell(1).setCellValue(CommonHelper.getString(restFinanceReportObj.getOrderNum()));
                deliveryRow.createCell(2).setCellValue(CommonHelper.getString(restFinanceReportObj.getGoodsName()));
                deliveryRow.createCell(3).setCellValue(CommonHelper.DateFormat(restFinanceReportObj.getCreateTime(), "yyyy/MM/dd HH:mm:ss"));
                deliveryRow.createCell(4).setCellValue(CommonHelper.getString(restFinanceReportObj.getAccount()));
                deliveryRow.createCell(5).setCellValue(CommonHelper.getString(restFinanceReportObj.getInFee()));
                deliveryRow.createCell(6).setCellValue(CommonHelper.getString(restFinanceReportObj.getOutFee()));
                deliveryRow.createCell(7).setCellValue(CommonHelper.getString(restFinanceReportObj.getBalance()));
                deliveryRow.createCell(8).setCellValue(CommonHelper.getString(restFinanceReportObj.getMode()));
                deliveryRow.createCell(9).setCellValue(CommonHelper.getString(restFinanceReportObj.getType()));
                deliveryRow.createCell(10).setCellValue(CommonHelper.getString(restFinanceReportObj.getMemo()));

                //在订单表中不存改商户订单号的记录，则红色底高亮
                String serviceSerialNum = CommonHelper.getString(restFinanceReportObj.getServiceSerialNum());
               if (!Validate.isNullOrEmpty(serviceSerialNum) && !alipayNoSet.contains(serviceSerialNum)) {
                    setBackRedColor(workbook, sheet, startIndex);
                }
                startIndex++;
            }
        }
//        sworkBook.removeSheetAt(sworkBook.getSheetIndex("style"));
        fetch.stop();
        fetch.start("generateOperationHistory");
        OperationHistory operationHistory = this.operationLogService.generateOperationHistory("", Constants.OperationType.DOWNLOAD_REST.toString(), Constants.ModelType.DOWNLOAD.toString());
        this.operationLogDao.saveOperationLog(operationHistory);
        fetch.stop();
        log.info(msg.getEndMessage("generateRestFinanceReport\r\n"+fetch.prettyPrint()));
        return sworkBook;
    }

    @Override
    public String  generateRestFinance(Map<String, String> queryParams) {
        log.info(msg.getStartMessage("generateRestFinanceReport"));
        StopWatch fetch = new StopWatch("generateRestFinanceReport");
        fetch.start("getWorkBookByExample");
        Workbook workbook = FileUtil.getWorkBookByExample(restFinanceTemplate);
        SXSSFWorkbook sworkBook = new SXSSFWorkbook((XSSFWorkbook) workbook);
        Sheet sheet = sworkBook.getSheet("data");

        List<RestFinanceReport> restFinanceList =new ArrayList<RestFinanceReport>();
        List<RestFinanceReport> alipayReportList = getAlipayReportList(queryParams);//包含不是订单是收入的虚拟订单

        //除订单开票报表以外的收入金额，均需体现：除去交易状态为（TRADE_CLOSED，TRADE_FINISHED）所有收入（包含满足条件的虚拟订单）
        List<RestFinanceReport> nonClosedAndFinishedFinanceList=getNonClosedAndFinishedFinanceList(queryParams);

        //虚拟订单，99元特权，1元特权这种虚拟产品的订单（查询订单状态为‘TRADE_CLOSED，TRADE_FINISHED’的所有虚拟订单）
        List<RestFinanceReport> virtualFinanceList= getVirtualFinanceList(queryParams);

        if(!Validate.isNullOrEmpty(alipayReportList)){
            restFinanceList.addAll(alipayReportList);
        }

        if(!Validate.isNullOrEmpty(nonClosedAndFinishedFinanceList)){
            restFinanceList.addAll(nonClosedAndFinishedFinanceList);
        }

        if(!Validate.isNullOrEmpty(virtualFinanceList)){
            restFinanceList.addAll(virtualFinanceList);
        }

        if (Validate.isNullOrEmpty(restFinanceList)) {
            return "none";
        }
        fetch.stop();
        int startIndex = 1;
        Row deliveryRow = null;
        //取出订单表中所有的订单号
        fetch.start("findAllAliayNos");
//        List<Map<String, Object>> alipayNoList = jstTradeDao.findAllAliayNos();
//        fetch.stop();
//        Set<String> alipayNoSet = new HashSet<String>();
//        fetch.start("restFinanceList");
//        if (!CollectionUtils.isEmpty(alipayNoList)) {
//            for (Map<String, Object> valueMap : alipayNoList) {
//                String alipayNoStr = valueMap.get("alipay_no").toString();
//                if(Validate.isNullOrEmpty(alipayNoStr)){
//                    alipayNoSet.add(alipayNoStr);
//                }
//            }
//        }
        for (int i = 0; i < restFinanceList.size(); i++) {
            deliveryRow = sheet.createRow(startIndex);
            RestFinanceReport restFinanceReportObj = restFinanceList.get(i);
            if (null != restFinanceReportObj) {
                deliveryRow.createCell(0).setCellValue(CommonHelper.getString(restFinanceReportObj.getFinancialSerialNum()));
                deliveryRow.createCell(1).setCellValue(CommonHelper.getString(restFinanceReportObj.getOrderNum()));
                deliveryRow.createCell(2).setCellValue(CommonHelper.getString(restFinanceReportObj.getGoodsName()));
                deliveryRow.createCell(3).setCellValue(CommonHelper.DateFormat(restFinanceReportObj.getCreateTime(), "yyyy/MM/dd HH:mm:ss"));
                deliveryRow.createCell(4).setCellValue(CommonHelper.getString(restFinanceReportObj.getAccount()));
                deliveryRow.createCell(5).setCellValue(CommonHelper.getString(restFinanceReportObj.getInFee()));
                deliveryRow.createCell(6).setCellValue(CommonHelper.getString(restFinanceReportObj.getOutFee()));
                deliveryRow.createCell(7).setCellValue(CommonHelper.getString(restFinanceReportObj.getBalance()));
                deliveryRow.createCell(8).setCellValue(CommonHelper.getString(restFinanceReportObj.getMode()));
                deliveryRow.createCell(9).setCellValue(CommonHelper.getString(restFinanceReportObj.getType()));
                deliveryRow.createCell(10).setCellValue(CommonHelper.getString(restFinanceReportObj.getMemo()));

                //在订单表中不存改商户订单号的记录，则红色底高亮
                String serviceSerialNum = CommonHelper.getString(restFinanceReportObj.getServiceSerialNum());
                //if (!Validate.isNullOrEmpty(serviceSerialNum) && !alipayNoSet.contains(serviceSerialNum)&&"T".equals(restFinanceReportObj.getOrderNum().substring(0,1))) {
                if (!Validate.isNullOrEmpty(restFinanceReportObj.getOrderNum()) && "T".equals(restFinanceReportObj.getOrderNum().substring(0,1))) {
                    setBackRedColor(workbook, sheet, startIndex);
                }
                startIndex++;
            }
        }
//        sworkBook.removeSheetAt(sworkBook.getSheetIndex("style"));
        fetch.stop();
        fetch.start("generateOperationHistory");
        OperationHistory operationHistory = this.operationLogService.generateOperationHistory("", Constants.OperationType.DOWNLOAD_REST.toString(), Constants.ModelType.DOWNLOAD.toString());
        this.operationLogDao.saveOperationLog(operationHistory);
        fetch.stop();
        FileOutputStream outputStream = null;
        String fileName = restFinanceTempFile + "RestFinanceReport_"+ CommonHelper.DateFormat(CommonHelper.getThisTimestamp(),"yyyy-MM-dd HH-mm-ss")+".xlsx";
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
        log.info(msg.getEndMessage("generateRestFinanceReport\r\n"+fetch.prettyPrint()));
        return "";
    }

    public static void setBackRedColor(Workbook workbook, Sheet sheet, Integer lineNo) {
        CellStyle oldCellStyle = sheet.getRow(lineNo).getCell(4).getCellStyle();
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.cloneStyleFrom(oldCellStyle);
        cellStyle.cloneStyleFrom(workbook.getSheet("style").getRow(0).getCell(0).getCellStyle());
        Row row = sheet.getRow(lineNo);
        int cellNum = row.getPhysicalNumberOfCells();
        for (int i = 0; i < cellNum; i++) {
            row.getCell(i).setCellStyle(cellStyle);
        }
    }
}
