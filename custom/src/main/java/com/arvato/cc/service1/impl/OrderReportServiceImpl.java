package com.arvato.cc.service1.impl;

import com.arvato.cc.constant.Constants;
import com.arvato.cc.dao1.*;
import com.arvato.cc.form.AlipayTransModel;
import com.arvato.cc.form.OrderReportBean;
import com.arvato.cc.form.finance.OrderReport;
import com.arvato.cc.log.LogMessageUtil;
import com.arvato.cc.model.*;
import com.arvato.cc.service1.*;
import com.arvato.cc.util.CommonHelper;
import com.arvato.cc.util.FileUtil;
import com.arvato.cc.util.Validate;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.io.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ZHAN405
 * Date: 15-8-18
 * Time: 下午2:07
 * To change this template use File | Settings | File Templates.
 */
@Service
public class OrderReportServiceImpl implements OrderReportService {
    private static final Log log = LogFactory.getLog(OrderReportServiceImpl.class);
    private static LogMessageUtil msg = new LogMessageUtil("ORDER_REPORT");
    @Autowired
    private OrderReportDao orderReportDao;

    @Value("${tradeTemplate}")
    private String tradeTemplate;

    @Value("${tradeTempFile}")
    private String tradeTempFile;


    @Autowired
    private OperationLogService operationLogService;
    @Autowired
    private OperationLogDao operationLogDao;
    @Autowired
    private CpdDao cpdDao;
    @Autowired
    private CpdDataService cpdDataService;
    @Autowired
    private AlipayTransDao alipayTransDao;
    @Autowired
    private AlipayTransService alipayTransService;


//    @Override
//    public Workbook generateOrderReportByExample(Map<String,String> queryParams) {
//        Workbook workbook = FileUtil.getWorkBookByExample("/template/OrderReport.xlsx");
//        SXSSFWorkbook sworkBook = new SXSSFWorkbook((XSSFWorkbook)workbook);
//        Sheet sheet = sworkBook.getSheet("data");
//        List<ExpOrder> expOrderList = orderReportDao.getOrderReportList(queryParams);
//        if (Validate.isNullOrEmpty(expOrderList)) {
//            return sworkBook;
//        }
//        Integer skuStartLine = 0;
//        Integer skuEndLine = 1;
//        String tid = "";
//        int rowNum = 1;
//        Row row = null;
//        String officeName = "";
////        Map<String,Object> cpdMap =null;
////        AlipayTrans alipay = null;
//        Map<String,AlipayTransModel> alipayMap = alipayTransService.getAlipayTrans(queryParams);
//        AlipayTransModel alipay = null;
//        Map<String,String> cpdMap = cpdDataService.getCpdCode();
//        for(ExpOrder expOrder : expOrderList) {
//            row = sheet.createRow(rowNum);
////            alipay = alipayTransDao.getByServiceSerialNum(expOrder.getAlipayNo());
//            alipay = alipayMap.get(expOrder.getAlipayNo());
//            row.createCell(0).setCellValue(CommonHelper.getString(expOrder.getSalesorg()));
//            row.createCell(1).setCellValue(CommonHelper.getString(expOrder.getDistrchannel()));
//            row.createCell(2).setCellValue(CommonHelper.getString(expOrder.getSalesdoctype()));
////            cpdMap = cpdDao.getCpdCode(expOrder.getTid());
////            if(!Validate.isNullOrEmpty(cpdMap)){
////                if(!Validate.isNullOrEmpty(cpdMap.get("office_name"))){
////                    officeName = (String)cpdMap.get("office_name");
////                }
////            }
//            if(Validate.isNullOrEmpty(cpdMap)){
//                row.createCell(3).setCellValue("");
//            } else {
//                row.createCell(3).setCellValue(CommonHelper.getString(cpdMap.get(expOrder.getTid())));
//            }
//            row.createCell(4).setCellValue(CommonHelper.getString(expOrder.getPoNumber()));
//            if(!Validate.isNullOrEmpty(alipay)){
//                row.createCell(5).setCellValue(CommonHelper.getString(alipay.getCreateTime()));
//            } else {
//                row.createCell(5).setCellValue("");
//            }
//            row.createCell(6).setCellValue(CommonHelper.getString(expOrder.getPricingdate()));
//            row.createCell(7).setCellValue(CommonHelper.getString(expOrder.getPartnerfunctn()));
//            if(Validate.isNullOrEmpty(cpdMap)){
//                row.createCell(8).setCellValue("");
//            } else{
//                row.createCell(8).setCellValue(CommonHelper.getString(cpdMap.get(expOrder.getTid())));
//            }
//            row.createCell(9).setCellValue(CommonHelper.getString(expOrder.getName()));
//            row.createCell(10).setCellValue(CommonHelper.getString(expOrder.getStreet()));
//            row.createCell(11).setCellValue(CommonHelper.getString(expOrder.getCity()));
//            row.createCell(12).setCellValue(CommonHelper.getString(expOrder.getPostalCode()));
//            //留空：column no from 13 to 31 start
//            row.createCell(13).setCellValue("");
//            row.createCell(14).setCellValue("");
//            row.createCell(15).setCellValue("");
//            row.createCell(16).setCellValue("");
//            row.createCell(17).setCellValue("");
//            row.createCell(18).setCellValue("");
//            row.createCell(19).setCellValue("");
//            row.createCell(20).setCellValue("");
//            row.createCell(21).setCellValue("");
//            row.createCell(22).setCellValue("");
//            row.createCell(23).setCellValue("");
//            row.createCell(24).setCellValue("");
//            row.createCell(25).setCellValue("");
//            row.createCell(26).setCellValue("");
//            row.createCell(27).setCellValue("");
//            row.createCell(28).setCellValue("");
//            row.createCell(29).setCellValue("");
//            row.createCell(30).setCellValue("");
//            //留空：column no from 13 to 31 end
//            row.createCell(31).setCellValue(CommonHelper.getString(expOrder.getMaterial()));
//            row.createCell(32).setCellValue(CommonHelper.getString(expOrder.getQuantity()));
//            row.createCell(33).setCellValue(CommonHelper.getString(expOrder.getTmallPrice()));
//            row.createCell(34).setCellValue(CommonHelper.getString(expOrder.getConditionAmount()));
//            row.createCell(35).setCellValue(CommonHelper.getString(expOrder.getTmallPoints()));//固定
//            row.createCell(36).setCellValue(CommonHelper.getString(expOrder.getPointAmout())); //积分
//            row.createCell(37).setCellValue(CommonHelper.getString(expOrder.getTmallCoupon()));//固定KMA6
//            row.createCell(38).setCellValue(CommonHelper.getString(expOrder.getCouponAmount()));//优惠券金额
//            row.createCell(39).setCellValue(CommonHelper.getString(expOrder.getPoachiveno())); //是增票的时候填写固定:Z009
//            row.createCell(40).setCellValue(CommonHelper.getString(expOrder.getText1()));//固定Tmall 增票
//            row.createCell(41).setCellValue(CommonHelper.getString(expOrder.getInternalnote()));//固定Z004
//            row.createCell(42).setCellValue(CommonHelper.getString(expOrder.getText2()));
//            row.createCell(43).setCellValue(CommonHelper.getString(expOrder.getBillingtext()));
//            row.createCell(44).setCellValue(CommonHelper.getString(expOrder.getText3()));
//
//            //一单多行时，将整单数据框起来
////            if(!tid.equals(expOrder.getTid())){
////                //if startLine = endLine,only one record
////                skuEndLine = rowNum - 1;
////                if(skuStartLine.intValue() != skuEndLine.intValue()) {
//////                    setBorder(new Integer[]{skuStartLine,0,skuEndLine,44},workbook,sheet);
//////                    setBorder(workbook,sheet,skuStartLine,skuEndLine);
////                    setBackGreyColor(workbook,sheet,new Integer[]{skuStartLine,skuEndLine});
////                }
////                skuStartLine = rowNum;
////                tid = expOrder.getTid();
////            }
////            if(tid.equals(expOrder.getTid())){
////                //重设积分值为零
////                skuEndLine = rowNum;
////            }
//            if("1".equals(expOrder.getCountRemark())) {
//                setBackGreyColor(workbook,sheet,rowNum);
//            }
//
//
//
//            //如果订单状态非交易成功TRADE_FINISHED均红底高亮
//            if(!Constants.TradeStatus.TRADE_FINISHED.toString().equals(expOrder.getTradeStatus())) {
////                setLineBackColor(workbook,row, HSSFColor.RED.index);
//                setBackRedColor(workbook,sheet,rowNum);
//            }
//
//            //积分加样式
//            if("1".equals(expOrder.getPointFeeRemark())) {
//                setBackYellowColor(workbook,sheet,rowNum);
//            }
//
//            //coupon加样式
//            if("1".equals(expOrder.getCouponRemark())) {
//                setBackBlueColor(workbook,sheet,rowNum);
//            }
//
//            //地址加样式
//            if("1".equals(expOrder.getAddressRemark())) {
//                setBackGreenColor(workbook,sheet,rowNum);
//            }
//
//            //支付宝金额和订单实际支付金额不同，加样式
//            if(!Validate.isNullOrEmpty(alipay)) {
//                if(!expOrder.getConditionAmount().equals(alipay.getInFee())) {
//                    //加样式
////                    setBlueBorder(workbook,sheet,rowNum);
//                    setBackSkyBlueColor(workbook,sheet,rowNum);
//                }
//            } else {
//                //加样式
////                setBlueBorder(workbook,sheet,rowNum);
//                setBackSkyBlueColor(workbook,sheet,rowNum);
//            }
//
//            rowNum++;
//        }
//        sworkBook.removeSheetAt(sworkBook.getSheetIndex("style"));
//
//        OperationHistory operationHistory = operationLogService.generateOperationHistory("",Constants.OperationType.DOWNLOAD_ORDER.toString(),Constants.ModelType.DOWNLOAD.toString()) ;
//        operationLogDao.saveOperationLog(operationHistory);
//        return sworkBook;
//    }


    @Override
    public Workbook generateOrderReportByExample(Map<String,String> queryParams) {
        log.info(msg.getStartMessage("generateOrderReportByExample"));
        StopWatch fetch = new StopWatch("generateOrderReportByExample");
        fetch.start("getWorkBookByExample");
        Workbook workbook = FileUtil.getWorkBookByExample(tradeTemplate);
        SXSSFWorkbook sworkBook = new SXSSFWorkbook((XSSFWorkbook)workbook);
        Sheet sheet = sworkBook.getSheet("data");
        fetch.stop();
//        List<ExpOrder> expOrderList = orderReportDao.getOrderReportList(queryParams);
        log.info(msg.getProcessMessage("getOrderReportListBySql"));
        fetch.start("getOrderReportListBySql");
        List<Map<String,Object>> expOrderList = orderReportDao.getOrderReportListBySql(queryParams);
        fetch.stop();
        log.info(msg.getProcessMessage("getOrderReportListBySql End"));
        if (Validate.isNullOrEmpty(expOrderList)) {
            return sworkBook;
        }
//        Integer skuStartLine = 0;
//        Integer skuEndLine = 1;
//        String tid = "";
        int rowNum = 1;
        Row row = null;
        String officeName = "";
//        Map<String,Object> cpdMap =null;
//        AlipayTrans alipay = null;
//        Map<String,AlipayTransModel> alipayMap = alipayTransService.getAlipayTrans(queryParams);
//        AlipayTransModel alipay = null;
//        Map<String,String> cpdMap = cpdDataService.getCpdCode();
        log.info(msg.getProcessMessage("for expOrderList"));
        fetch.start("for expOrderList");
        int i = 10;
        for(Map<String,Object> expOrder : expOrderList) {
            row = sheet.createRow(rowNum);
//            alipay = alipayTransDao.getByServiceSerialNum(expOrder.getAlipayNo());
//            alipay = alipayMap.get(CommonHelper.getString(expOrder.get("ALIPAY_NO")));  //expOrder.getAlipayNo()
            //CR:20150929***************************************************************
            row.createCell(0).setCellValue(CommonHelper.getString(expOrder.get("item_type")));
            row.createCell(1).setCellValue(CommonHelper.getString(expOrder.get("TIDi")));
            row.createCell(2).setCellValue(CommonHelper.getString(expOrder.get("order_payment")));
            row.createCell(3).setCellValue(CommonHelper.getString(expOrder.get("IN_FEE")));
            row.createCell(4).setCellValue(CommonHelper.getString(expOrder.get("order_pay")));
            row.createCell(5).setCellValue(CommonHelper.getString(expOrder.get("item_title")));
            row.createCell(6).setCellValue(CommonHelper.getString(expOrder.get("item_payment")));
            row.createCell(7).setCellValue(CommonHelper.getString(expOrder.get("seller_memo")));
            row.createCell(8).setCellValue(CommonHelper.getString(expOrder.get("invoice_name")));
            row.createCell(9).setCellValue(CommonHelper.getString(expOrder.get("receiver_name")));
            row.createCell(10).setCellValue(CommonHelper.getString(expOrder.get("original_point_fee")));
            row.createCell(11).setCellValue(CommonHelper.getString(expOrder.get("original_coupon")));

            row.createCell(0+i).setCellValue(CommonHelper.getString(expOrder.get("SALESORG")));//expOrder.getSalesorg()
            row.createCell(1+i).setCellValue(CommonHelper.getString(expOrder.get("DISTRCHANNEL")));//expOrder.getDistrchannel()
            row.createCell(2+i).setCellValue(CommonHelper.getString(expOrder.get("SALESDOCTYPE")));//expOrder.getSalesdoctype()
//            cpdMap = cpdDao.getCpdCode(expOrder.getTid());
//            if(!Validate.isNullOrEmpty(cpdMap)){
//                if(!Validate.isNullOrEmpty(cpdMap.get("office_name"))){
//                    officeName = (String)cpdMap.get("office_name");
//                }
//            }
//            if(Validate.isNullOrEmpty(cpdMap)){
//                row.createCell(3).setCellValue("");
//            } else {
                row.createCell(3+i).setCellValue(CommonHelper.getString(expOrder.get("OFFICE_NAME")));//cpdMap.get(expOrder.getTid()
//            }
            row.createCell(4+i).setCellValue(CommonHelper.getString(expOrder.get("PO_NUMBER")));//expOrder.getPoNumber()
//            if(!Validate.isNullOrEmpty(alipay)){
                row.createCell(5+i).setCellValue(CommonHelper.objectToTime(expOrder.get("CREATE_TIME"),"yyyy.MM.dd"));//alipay.getCreateTime()
//            } else {
//                row.createCell(5).setCellValue("");
//            }
            row.createCell(6+i).setCellValue(CommonHelper.objectToTime(expOrder.get("PRICINGDATE"),"yyyy.MM.dd"));//expOrder.getPricingdate()
            row.createCell(7+i).setCellValue(CommonHelper.getString(expOrder.get("PARTNERFUNCTN")));//expOrder.getPartnerfunctn()
//            if(Validate.isNullOrEmpty(cpdMap)){
//                row.createCell(8).setCellValue("");
//            } else{
                row.createCell(8+i).setCellValue(CommonHelper.getString(expOrder.get("OFFICE_NAME")));
//            }
            row.createCell(9+i).setCellValue(CommonHelper.getString(expOrder.get("NAME")));//expOrder.getName()
            row.createCell(10+i).setCellValue(CommonHelper.getString(expOrder.get("STREET")));//expOrder.getStreet()
            row.createCell(11+i).setCellValue(CommonHelper.getString(expOrder.get("CITY"))); //expOrder.getCity()
            row.createCell(12+i).setCellValue(CommonHelper.getString(expOrder.get("POSTAL_CODE"))); //expOrder.getPostalCode()
            //留空：column no from 13 to 31 start
            row.createCell(13+i).setCellValue("");
            row.createCell(14+i).setCellValue("");
            row.createCell(15+i).setCellValue("");
            row.createCell(16+i).setCellValue("");
            row.createCell(17+i).setCellValue("");
            row.createCell(18+i).setCellValue("");
            row.createCell(19+i).setCellValue("");
            row.createCell(20+i).setCellValue("");
            row.createCell(21+i).setCellValue("");
            row.createCell(22+i).setCellValue("");
            row.createCell(23+i).setCellValue("");
            row.createCell(24+i).setCellValue("");
            row.createCell(25+i).setCellValue("");
            row.createCell(26+i).setCellValue("");
            row.createCell(27+i).setCellValue("");
            row.createCell(28+i).setCellValue("");
            row.createCell(29+i).setCellValue("");
            row.createCell(30+i).setCellValue("");
            //留空：column no from 13 to 31 end
            row.createCell(31+i).setCellValue(CommonHelper.getString(expOrder.get("MATERIAL")));//expOrder.getMaterial()
            row.createCell(32+i).setCellValue(CommonHelper.getString(expOrder.get("QUANTITY")));//expOrder.getQuantity()
            row.createCell(33+i).setCellValue(CommonHelper.getString(expOrder.get("TMALL_PRICE")));//expOrder.getTmallPrice()
            row.createCell(34+i).setCellValue(CommonHelper.getString(expOrder.get("CONDITION_AMOUNT")));//expOrder.getConditionAmount()
            row.createCell(35+i).setCellValue(CommonHelper.getString(expOrder.get("TMALL_POINTS")));//固定expOrder.getTmallPoints()
            row.createCell(36+i).setCellValue(CommonHelper.getString(expOrder.get("POINT_AMOUT"))); //积分expOrder.getPointAmout()
            row.createCell(37+i).setCellValue(CommonHelper.getString(expOrder.get("TMALL_COUPON")));//固定KMA6expOrder.getTmallCoupon()
            row.createCell(38+i).setCellValue(CommonHelper.getString(expOrder.get("COUPON_AMOUNT")));//优惠券金额expOrder.getCouponAmount()
            row.createCell(39+i).setCellValue(CommonHelper.getString(expOrder.get("POACHIVENO"))); //是增票的时候填写固定:Z009expOrder.getPoachiveno()
            row.createCell(40+i).setCellValue(CommonHelper.getString(expOrder.get("TEXT1")));//固定Tmall 增票 expOrder.getText1()
            row.createCell(41+i).setCellValue(CommonHelper.getString(expOrder.get("INTERNALNOTE")));//固定Z004 expOrder.getInternalnote()
            row.createCell(42+i).setCellValue(CommonHelper.getString(expOrder.get("TEXT2"))); // expOrder.getText2()
            row.createCell(43+i).setCellValue(CommonHelper.getString(expOrder.get("BILLINGTEXT"))); //expOrder.getBillingtext()
            row.createCell(44+i).setCellValue(CommonHelper.getString(expOrder.get("TEXT3"))); //expOrder.getText3()

            //一单多行时，将整单数据框起来
//            if(!tid.equals(expOrder.getTid())){
//                //if startLine = endLine,only one record
//                skuEndLine = rowNum - 1;
//                if(skuStartLine.intValue() != skuEndLine.intValue()) {
////                    setBorder(new Integer[]{skuStartLine,0,skuEndLine,44},workbook,sheet);
////                    setBorder(workbook,sheet,skuStartLine,skuEndLine);
//                    setBackGreyColor(workbook,sheet,new Integer[]{skuStartLine,skuEndLine});
//                }
//                skuStartLine = rowNum;
//                tid = expOrder.getTid();
//            }
//            if(tid.equals(expOrder.getTid())){
//                //重设积分值为零
//                skuEndLine = rowNum;
//            }
            if("1".equals(CommonHelper.getString(expOrder.get("COUNT_REMARK")))) { //expOrder.getCountRemark()
                setBackGreyColor(workbook,sheet,rowNum,i);
            }



            //如果订单状态非交易成功TRADE_FINISHED均红底高亮
            if(!Constants.TradeStatus.TRADE_FINISHED.toString().equals(CommonHelper.getString(expOrder.get("TRADE_STATUS")))) { //expOrder.getTradeStatus()
//                setLineBackColor(workbook,row, HSSFColor.RED.index);
                setBackRedColor(workbook,sheet,rowNum,i);
            }

            //积分加样式
            if("1".equals(CommonHelper.getString(expOrder.get("TRADE_STATUS")))) { //expOrder.getPointFeeRemark()
               setBackYellowColor(workbook,sheet,rowNum,i);
            }

            //coupon加样式
            if("1".equals(CommonHelper.getString(expOrder.get("TRADE_STATUS")))) { //expOrder.getCouponRemark()
                setBackBlueColor(workbook,sheet,rowNum,i);
            }

            //地址加样式
//            if("1".equals(CommonHelper.getString(expOrder.get("TRADE_STATUS")))) { //expOrder.getAddressRemark()
//                setBackGreenColor(workbook,sheet,rowNum);
//            }

            //支付宝金额和订单实际支付金额不同，加样式
//            if(!Validate.isNullOrEmpty(alipay)) {
//                if(!expOrder.getConditionAmount().equals(alipay.getInFee())) {
                if(!CommonHelper.objectToDouble(expOrder.get("PAYMENT")).equals(CommonHelper.objectToDouble(expOrder.get("IN_FEE")))) { //expOrder.getConditionAmount()  alipay.getInFee()
                    //加样式
//                    setBlueBorder(workbook,sheet,rowNum);
                    setBackSkyBlueColor(workbook,sheet,rowNum,i);
                }
//            } else {
                //加样式
//                setBlueBorder(workbook,sheet,rowNum);
//                setBackSkyBlueColor(workbook,sheet,rowNum);
//            }

            rowNum++;
        }

//        sworkBook.removeSheetAt(sworkBook.getSheetIndex("style"));
        fetch.stop();
        log.info(msg.getProcessMessage("generateOperationHistory"));
        fetch.start("generateOperationHistory");
        OperationHistory operationHistory = operationLogService.generateOperationHistory("",Constants.OperationType.DOWNLOAD_ORDER.toString(),Constants.ModelType.DOWNLOAD.toString()) ;
        operationLogDao.saveOperationLog(operationHistory);
        fetch.stop();
        log.info(msg.getEndMessage("generateOrderReportByExample\r\n"+fetch.prettyPrint()));
        return sworkBook;
    }

    @Override
    public String generateOrderReport(Map<String,String> queryParams) {
        log.info(msg.getStartMessage("generateOrderReport"));
        StopWatch fetch = new StopWatch("generateOrderReport");
        log.info(msg.getProcessMessage("getWorkBookByExample"));
        fetch.start("getWorkBookByExample");
        Workbook workbook = FileUtil.getWorkBookByExample(tradeTemplate);
        SXSSFWorkbook sworkBook = new SXSSFWorkbook((XSSFWorkbook)workbook);
        Sheet sheet = sworkBook.getSheet("data");
        fetch.stop();
        CellStyle greyColorCellStyle = FileUtil.getCellStyle(workbook,0,0);
        CellStyle redColorCellStyle = FileUtil.getCellStyle(workbook, 2, 0);
        CellStyle yellowColorCellStyle = FileUtil.getCellStyle(workbook, 4, 0);
        CellStyle blueColorCellStyle = FileUtil.getCellStyle(workbook, 8, 0);
        CellStyle greenColorCellStyle = FileUtil.getCellStyle(workbook, 10, 0);
        CellStyle skyBlueColorCellStyle = FileUtil.getCellStyle(workbook, 12, 0);
        CellStyle littleGreenColorCellStyle = FileUtil.getCellStyle(workbook, 14, 0);

        log.info(msg.getProcessMessage("getOrderReportListBySql"));
        fetch.start("getOrderReportListBySql");
        List<Map<String,Object>> expOrderList = orderReportDao.getOrderReportListBySql(queryParams);
        fetch.stop();
        if (Validate.isNullOrEmpty(expOrderList)) {
            return "none";
        }
        Map<String,OrderReportBean> orderReportBeanMap=new HashMap<String,OrderReportBean>();
        for(Map<String,Object> expOrder : expOrderList) {
            String itemType=CommonHelper.getString(expOrder.get("item_type"));
            expOrder.put("original_point_fee",covertNumber(expOrder.get("original_point_fee")));
            if("order".equals(itemType)){
                orderReportBeanMap.put(CommonHelper.getString(expOrder.get("TIDi")),new OrderReportBean(CommonHelper.getString(expOrder.get("original_point_fee")),CommonHelper.getString(expOrder.get("original_coupon"))));
            }
        }
        int rowNum = 1;
        Row row = null;
        int i = 14;
        log.info(msg.getProcessMessage("for expOrderList"));
        fetch.start("for expOrderList");
        for(Map<String,Object> expOrder : expOrderList) {
            row = sheet.createRow(rowNum);
            row.createCell(0).setCellValue(CommonHelper.getString(expOrder.get("item_type")));//行类型
            String tid=CommonHelper.getString(expOrder.get("TIDi"));
            row.createCell(1).setCellValue(tid);//订单号
            row.createCell(2).setCellValue(CommonHelper.getString(expOrder.get("refund_fee")));//退款金额
            row.createCell(3).setCellValue(CommonHelper.DoubleParse(expOrder.get("order_payment")));//买家应付款
            row.createCell(4).setCellValue(CommonHelper.DoubleParse(expOrder.get("IN_FEE")));//支付宝到账
            Object original_point_fee = expOrder.get("original_point_fee");
            if(null==original_point_fee||"0".equals(original_point_fee)){
                row.createCell(5).setCellValue(orderReportBeanMap.get(tid)==null ? CommonHelper.DoubleParse(0.0):CommonHelper.DoubleParse(orderReportBeanMap.get(tid).getOriginalPointFee()));//积分总额
            }else{
                row.createCell(5).setCellValue(CommonHelper.DoubleParse(original_point_fee));
            }
            row.createCell(6).setCellValue(CommonHelper.DoubleParse(expOrder.get("order_pay")));//开票金额
            row.createCell(7).setCellValue(CommonHelper.getString(expOrder.get("item_title")));//宝贝标题
            if(Validate.isNullOrEmpty(expOrder.get("item_payment"))) {
                row.createCell(8).setCellValue("");
            } else {
                row.createCell(8).setCellValue(CommonHelper.DoubleParse(expOrder.get("item_payment")));//子商品金额
            }
            Object original_coupon = expOrder.get("original_coupon");
            if(null==original_coupon){
                row.createCell(9).setCellValue(orderReportBeanMap.get(tid)==null?CommonHelper.DoubleParse(0.0):CommonHelper.DoubleParse(orderReportBeanMap.get(tid).getOriginalCoupon()));//优惠券
            }else{
                row.createCell(9).setCellValue(CommonHelper.DoubleParse(original_coupon));
            }
            //row.createCell(9).setCellValue(CommonHelper.getString(expOrder.get("original_coupon")));//优惠券
            row.createCell(10).setCellValue(CommonHelper.getString(expOrder.get("seller_memo")));//商家备注
            row.createCell(11).setCellValue(CommonHelper.getString(expOrder.get("invoice_name")));//发票抬头
            row.createCell(12).setCellValue(CommonHelper.getString(expOrder.get("receiver_name")));//收货人姓名
            row.createCell(13).setCellValue(CommonHelper.getString(expOrder.get("trade_status")));//订单状态

            row.createCell(0+i).setCellValue(CommonHelper.getString(expOrder.get("SALESORG")));//expOrder.getSalesorg()
            row.createCell(1+i).setCellValue(CommonHelper.getString(expOrder.get("DISTRCHANNEL")));//expOrder.getDistrchannel()
            row.createCell(2+i).setCellValue(CommonHelper.getString(expOrder.get("SALESDOCTYPE")));//expOrder.getSalesdoctype()
            row.createCell(3+i).setCellValue(CommonHelper.getString(
                    expOrder.get("OFFICE_NAME")));//cpdMap.get(expOrder.getTid()
            row.createCell(4+i).setCellValue(CommonHelper.getString(
                    expOrder.get("PO_NUMBER")));//expOrder.getPoNumber()
            row.createCell(5+i).setCellValue(CommonHelper.objectToTime(
                    expOrder.get("CREATE_TIME"), "yyyy.MM.dd"));//alipay.getCreateTime()
            row.createCell(6+i).setCellValue(CommonHelper.objectToTime(
                    expOrder.get("PRICINGDATE"), "yyyy.MM.dd"));//expOrder.getPricingdate()
            row.createCell(7+i).setCellValue(CommonHelper.getString(expOrder.get("PARTNERFUNCTN")));//expOrder.getPartnerfunctn()
            row.createCell(8+i).setCellValue(CommonHelper.getString(expOrder.get("OFFICE_NAME")));
            row.createCell(9+i).setCellValue(CommonHelper.getString(expOrder.get("NAME")));//expOrder.getName()
            row.createCell(10+i).setCellValue(CommonHelper.getString(expOrder.get("STREET")));//expOrder.getStreet()
            row.createCell(11+i).setCellValue(CommonHelper.getString(expOrder.get("CITY"))); //expOrder.getCity()
            row.createCell(12+i).setCellValue(CommonHelper.getString(expOrder.get("POSTAL_CODE"))); //expOrder.getPostalCode()
            //留空：column no from 13 to 31 start
            row.createCell(13+i).setCellValue("");
            row.createCell(14+i).setCellValue("");
            row.createCell(15+i).setCellValue("");
            row.createCell(16+i).setCellValue("");
            row.createCell(17+i).setCellValue("");
            row.createCell(18+i).setCellValue("");
            row.createCell(19+i).setCellValue("");
            row.createCell(20+i).setCellValue("");
            row.createCell(21+i).setCellValue("");
            row.createCell(22+i).setCellValue("");
            row.createCell(23+i).setCellValue("");
            row.createCell(24+i).setCellValue("");
            row.createCell(25+i).setCellValue("");
            row.createCell(26+i).setCellValue("");
            row.createCell(27+i).setCellValue("");
            row.createCell(28+i).setCellValue("");
            row.createCell(29+i).setCellValue("");
            row.createCell(30+i).setCellValue("");
            //留空：column no from 13 to 31 end
            row.createCell(31+i).setCellValue(CommonHelper.getString(expOrder.get("MATERIAL")));//[Material(MATNR)]expOrder.getMaterial()
            row.createCell(32+i).setCellValue(CommonHelper.IntegerParse(expOrder.get("QUANTITY")));//[(QUANTITY)]expOrder.getQuantity()
            row.createCell(33+i).setCellValue(CommonHelper.getString(expOrder.get("TMALL_PRICE")));//[T-Mall price(KSCHL)]expOrder.getTmallPrice()
            row.createCell(34+i).setCellValue(CommonHelper.DoubleParse(expOrder.get("CONDITION_AMOUNT")));//[Condition amount(KBETR)]expOrder.getConditionAmount()
            String pointAmout=CommonHelper.getString(expOrder.get("POINT_AMOUT"));
            row.createCell(35+i).setCellValue(pointAmout.equals("0")||pointAmout.equals("0.0")||pointAmout.equals("")?"":CommonHelper.getString(expOrder.get("TMALL_POINTS")));//[KMA1 T-Mall points(KSCHL)]固定expOrder.getTmallPoints()
            if(pointAmout.equals("0")||pointAmout.equals("0.0")||pointAmout.equals("")){
                row.createCell(36+i).setCellValue("");
            } else {
                row.createCell(36+i).setCellValue(CommonHelper.DoubleParse(pointAmout)); //[Condition amount(KBETR)]积分expOrder.getPointAmout()
            }
            String couponAmout=CommonHelper.getString(expOrder.get("COUPON_AMOUNT"));
            row.createCell(37+i).setCellValue(couponAmout.equals("0")||couponAmout.equals("0.0")||couponAmout.equals("")?"":CommonHelper.getString(expOrder.get("TMALL_COUPON")));//[KMA6 T-Mall coupon(KSCHL)]固定KMA6expOrder.getTmallCoupon()
            if (couponAmout.equals("0")||couponAmout.equals("0.0")||couponAmout.equals("")) {
                row.createCell(38+i).setCellValue("");
            } else {
                row.createCell(38+i).setCellValue(CommonHelper.DoubleParse(couponAmout));//[Condition amount(KBETR)]优惠券金额expOrder.getCouponAmount()
            }
            row.createCell(39+i).setCellValue(CommonHelper.getString(expOrder.get("POACHIVENO"))); //[PO archive No(TEXTID)]是增票的时候填写固定:Z009expOrder.getPoachiveno()
            row.createCell(40+i).setCellValue(CommonHelper.getString(expOrder.get("TEXT1")));//[Text(TEXT)]固定Tmall 增票 expOrder.getText1()
            row.createCell(41+i).setCellValue(CommonHelper.getString(expOrder.get("INTERNALNOTE")));//固定Z004 expOrder.getInternalnote()
            row.createCell(42+i).setCellValue(CommonHelper.getString(expOrder.get("TEXT2"))); // expOrder.getText2()
            row.createCell(43+i).setCellValue(CommonHelper.getString(expOrder.get("BILLINGTEXT"))); //expOrder.getBillingtext()
            row.createCell(44+i).setCellValue(CommonHelper.getString(expOrder.get("TEXT3"))); //expOrder.getText3()

            if("1".equals(CommonHelper.getString(expOrder.get("COUNT_REMARK")))) { //expOrder.getCountRemark()
                setBackGreyColor(workbook,sheet,rowNum,i);
                if(!Validate.isNullOrEmpty(expOrder.get("POINT_AMOUT")) && CommonHelper.objectToDouble(expOrder.get("POINT_AMOUT")) > 0) {
                    //setBackYellowColor(workbook,sheet,rowNum,i);
                    FileUtil.setCellStyle(sheet,rowNum,36+i,yellowColorCellStyle);
                }
            }

            //如果订单状态非交易成功TRADE_FINISHED均红底高亮
            if(!Constants.TradeStatus.TRADE_FINISHED.toString().equals(CommonHelper.getString(expOrder.get("TRADE_STATUS")))) { //expOrder.getTradeStatus()
                //setBackRedColor(workbook, sheet, rowNum, i);
                FileUtil.setCellStyle(sheet, rowNum, 4 + i, redColorCellStyle);
            }

            //积分加样式
            if("1".equals(CommonHelper.getString(expOrder.get("POINT_FEE_REMARK")))) { //expOrder.getPointFeeRemark()
                //setBackYellowColor(workbook, sheet, rowNum, i);
                FileUtil.setCellStyle(sheet, rowNum, 36 + i, yellowColorCellStyle);
            }

            //coupon加样式
            if("1".equals(CommonHelper.getString(expOrder.get("COUPON_REMARK")))) { //expOrder.getCouponRemark()
                //setBackBlueColor(workbook, sheet, rowNum, i);
                FileUtil.setCellStyle(sheet, rowNum, 38 + i, blueColorCellStyle);
            }

            //sku加样式
            if("1".equals(CommonHelper.getString(expOrder.get("ADDRESS_REMARK")))) { //expOrder.getAddressRemark()
                //setBackGreenColor(workbook,sheet,rowNum,i);
                FileUtil.setCellStyle(sheet, rowNum, 31 + i, greenColorCellStyle);
            }

            if(!CommonHelper.objectToDouble(expOrder.get("PAYMENT")).equals(CommonHelper.objectToDouble(expOrder.get("IN_FEE")))) { //expOrder.getConditionAmount()  alipay.getInFee()
                //加样式
                //setBackSkyBlueColor(workbook, sheet, rowNum, i);
                FileUtil.setCellStyle(sheet, rowNum, 34 + i, skyBlueColorCellStyle);
            }

            //商家备注1判断
            String sellerMemo=CommonHelper.getString(
                    expOrder.get("seller_memo"));
            boolean flag=true;
            if(!Validate.isNullOrEmpty(sellerMemo)){
                String[] split=sellerMemo.split("[；;]");
                if(split!=null && split.length==5){
                    if(split[0].startsWith("1、")){
                        if(!Validate.isNullOrEmpty(split[0].replaceFirst("1、",""))){
                            flag=false;
                        }
                    }
                }
            }
            if(flag){
                //setBackLittleGreenColor(workbook, sheet, rowNum, i);
                FileUtil.setCellStyle(sheet, rowNum, 10, littleGreenColorCellStyle);
            }
            rowNum++;
        }

//        sworkBook.removeSheetAt(sworkBook.getSheetIndex("style"));
        fetch.stop();
        log.info(msg.getProcessMessage("generateOperationHistory"));
        fetch.start("generateOperationHistory");
        OperationHistory operationHistory = operationLogService.generateOperationHistory("",Constants.OperationType.DOWNLOAD_ORDER.toString(),Constants.ModelType.DOWNLOAD.toString()) ;
        operationLogDao.saveOperationLog(operationHistory);
        fetch.stop();
        FileOutputStream outputStream = null;
        String fileName = tradeTempFile + "OrderReport-"+ CommonHelper.DateFormat(CommonHelper.getThisTimestamp(),"yyyy-MM-dd HH-mm-ss")+".xlsx";
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

        log.info(msg.getEndMessage("generateOrderReport\r\n"+fetch.prettyPrint()));
        return "";
    }

    public static String covertNumber(Object obj){
        if (Validate.isNullOrEmpty(obj) || "null".equals(obj)) {
            return "0";
        }
        String tmp=obj.toString();
        if(tmp.endsWith(".0")){
            return tmp.substring(0,tmp.length()-2);
        }
        return "0";
    }
    /**
     *从模版获取样式
     */
    public static void setBorder(Workbook workbook,Sheet sheet,Integer startLine,Integer endLine){

        CellStyle oldCellStyle = sheet.getRow(startLine).getCell(4).getCellStyle();
        CellStyle startCellStyle = workbook.createCellStyle();
        startCellStyle.cloneStyleFrom(oldCellStyle);
        startCellStyle.setTopBorderColor(IndexedColors.GREY_80_PERCENT.getIndex());
        startCellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);

        startCellStyle.setLeftBorderColor(IndexedColors.GREY_80_PERCENT.getIndex());
        startCellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);

        startCellStyle.setRightBorderColor(IndexedColors.GREY_80_PERCENT.getIndex());
        startCellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);

        sheet.getRow(startLine).getCell(4).setCellStyle(startCellStyle);


        CellStyle endCellStyle = workbook.createCellStyle();
        endCellStyle.cloneStyleFrom(oldCellStyle);
        endCellStyle.setBottomBorderColor(IndexedColors.GREY_80_PERCENT.getIndex());
        endCellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);

        endCellStyle.setLeftBorderColor(IndexedColors.GREY_80_PERCENT.getIndex());
        endCellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);

        endCellStyle.setRightBorderColor(IndexedColors.GREY_80_PERCENT.getIndex());
        endCellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);

        sheet.getRow(endLine).getCell(4).setCellStyle(endCellStyle);

        if(startLine+1 < endLine){
            for(int i = startLine+1;i<endLine;i++) {
                CellStyle middleCellStyle = workbook.createCellStyle();
                middleCellStyle.cloneStyleFrom(oldCellStyle);
                middleCellStyle.setLeftBorderColor(IndexedColors.GREY_80_PERCENT.getIndex());
                middleCellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);

                middleCellStyle.setRightBorderColor(IndexedColors.GREY_80_PERCENT.getIndex());
                middleCellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);

                sheet.getRow(endLine).getCell(4).setCellStyle(middleCellStyle);
            }
        }



//        sheet.getRow(startLine).getCell(4).setCellStyle(workbook.getSheet("style").getRow(6).getCell(0).getCellStyle());
//        sheet.getRow(endLine).getCell(4).setCellStyle(workbook.getSheet("style").getRow(7).getCell(0).getCellStyle());
//
//        if(startLine+1 < endLine){
//            for(int i = startLine+1;i<endLine;i++) {
//                sheet.getRow(i).getCell(4).setCellStyle(workbook.getSheet("style").getRow(9).getCell(0).getCellStyle());
//            }
//        }
////        sheet.getRow(startLine).setRowStyle(workbook.getSheet("style").getRow(1).getRowStyle());
////        sheet.getRow(endLine).setRowStyle(workbook.getSheet("style").getRow(2).getRowStyle());
    }

    /**
     *单号相同
     * @param workbook
     * @param sheet
     * @param lines
     */
//    public static void setBackGreyColor(Workbook workbook,Sheet sheet,Integer[] lines) {
//        for(int i = lines[0];i<=lines[1];i++){
//            CellStyle oldCellStyle = sheet.getRow(i).getCell(0).getCellStyle();
//            CellStyle cellStyle = workbook.createCellStyle();
//            cellStyle.cloneStyleFrom(oldCellStyle);
//            cellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());//cloneStyleFrom(workbook.getSheet("style").getRow(0).getCell(0).getCellStyle());
//            cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
//            sheet.getRow(i).getCell(0).setCellStyle(cellStyle);
//        }
//    }

    /**
     *单号相同
     * @param workbook
     * @param sheet
     * @param lineNo
     */
    public static void setBackGreyColor(Workbook workbook,Sheet sheet,Integer lineNo,Integer i) {
//        CellStyle oldCellStyle = sheet.getRow(lineNo).getCell(0).getCellStyle();
//        CellStyle cellStyle = workbook.createCellStyle();
//        cellStyle.cloneStyleFrom(oldCellStyle);
//        cellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());//cloneStyleFrom(workbook.getSheet("style").getRow(0).getCell(0).getCellStyle());
//        cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
//        sheet.getRow(lineNo).getCell(0).setCellStyle(cellStyle);

        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.cloneStyleFrom(workbook.getSheet("style").getRow(0).getCell(0).getCellStyle());
        sheet.getRow(lineNo).getCell(0+i).setCellStyle(cellStyle);
    }

    /**
     *金额不一致
     * @param workbook
     * @param sheet
     * @param lineNo
     */
    public static void setBackSkyBlueColor(Workbook workbook,Sheet sheet,Integer lineNo,Integer i) {
//            CellStyle oldCellStyle = sheet.getRow(lineNo).getCell(0).getCellStyle();
//            CellStyle cellStyle = workbook.createCellStyle();
//            cellStyle.cloneStyleFrom(oldCellStyle);
//            cellStyle.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());//cloneStyleFrom(workbook.getSheet("style").getRow(0).getCell(0).getCellStyle());
//            cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
//            sheet.getRow(lineNo).getCell(0).setCellStyle(cellStyle);
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.cloneStyleFrom(workbook.getSheet("style").getRow(12).getCell(0).getCellStyle());
        sheet.getRow(lineNo).getCell(34+i).setCellStyle(cellStyle);
    }



    /**
     *状态非TRADE_FINISH显示红色，用此样式
     * @param workbook
     * @param sheet
     * @param lineNo
     */
    public static void setBackRedColor(Workbook workbook,Sheet sheet,Integer lineNo,Integer i) {
//        CellStyle oldCellStyle = sheet.getRow(lineNo).getCell(4).getCellStyle();
//        CellStyle cellStyle = workbook.createCellStyle();
//        cellStyle.cloneStyleFrom(oldCellStyle);
//        cellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());//cloneStyleFrom(workbook.getSheet("style").getRow(0).getCell(0).getCellStyle());
//        cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
//        sheet.getRow(lineNo).getCell(4).setCellStyle(cellStyle);

        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.cloneStyleFrom(workbook.getSheet("style").getRow(2).getCell(0).getCellStyle());
        sheet.getRow(lineNo).getCell(4+i).setCellStyle(cellStyle);

    }

    /**
     *当积分除不尽时，用此样式黄底
     * @param workbook
     * @param sheet
     * @param lineNo
     */
    public static void setBackYellowColor(Workbook workbook,Sheet sheet,Integer lineNo,Integer i) {
//        CellStyle oldCellStyle = sheet.getRow(lineNo).getCell(36).getCellStyle();
//        CellStyle cellStyle = workbook.createCellStyle();
//        cellStyle.cloneStyleFrom(oldCellStyle);
//        cellStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
//        cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
//        sheet.getRow(lineNo).getCell(36).setCellStyle(cellStyle);
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.cloneStyleFrom(workbook.getSheet("style").getRow(4).getCell(0).getCellStyle());
        sheet.getRow(lineNo).getCell(36+i).setCellStyle(cellStyle);
    }

    /**
     *当优惠券除不尽时，用此样式蓝底
     * @param workbook
     * @param sheet
     * @param lineNo
     */
    public static void setBackBlueColor(Workbook workbook,Sheet sheet,Integer lineNo,Integer i) {
//        CellStyle oldCellStyle = sheet.getRow(lineNo).getCell(36).getCellStyle();
//        CellStyle cellStyle = workbook.createCellStyle();
//        cellStyle.cloneStyleFrom(oldCellStyle);
//        cellStyle.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());
//        cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
//        sheet.getRow(lineNo).getCell(38).setCellStyle(cellStyle);


        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.cloneStyleFrom(workbook.getSheet("style").getRow(8).getCell(0).getCellStyle());
        sheet.getRow(lineNo).getCell(38+i).setCellStyle(cellStyle);

    }

    /**
     *当地址不相同时，用此样式绿底
     * @param workbook
     * @param sheet
     * @param lineNo
     */
    public static void setBackGreenColor(Workbook workbook,Sheet sheet,Integer lineNo,Integer i) {
//        CellStyle oldCellStyle = sheet.getRow(lineNo).getCell(36).getCellStyle();
//        CellStyle cellStyle = workbook.createCellStyle();
//        cellStyle.cloneStyleFrom(oldCellStyle);
//        cellStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
//        cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
//        sheet.getRow(lineNo).getCell(10).setCellStyle(cellStyle);


        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.cloneStyleFrom(workbook.getSheet("style").getRow(10).getCell(0).getCellStyle());
        sheet.getRow(lineNo).getCell(31+i).setCellStyle(cellStyle);

    }

    /**
     *发票抬头，如果为空，标记浅绿色
     * @param workbook
     * @param sheet
     * @param lineNo
     */
    public static void setBackLittleGreenColor(Workbook workbook,Sheet sheet,Integer lineNo,Integer i) {
        //        CellStyle oldCellStyle = sheet.getRow(lineNo).getCell(36).getCellStyle();
        //        CellStyle cellStyle = workbook.createCellStyle();
        //        cellStyle.cloneStyleFrom(oldCellStyle);
        //        cellStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        //        cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        //        sheet.getRow(lineNo).getCell(10).setCellStyle(cellStyle);


        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.cloneStyleFrom(workbook.getSheet("style").getRow(14).getCell(0).getCellStyle());
        sheet.getRow(lineNo).getCell(10).setCellStyle(cellStyle);

    }


    /**
     *当支付宝和实际支付金额不一致，篮框
     * @param workbook
     * @param sheet
     * @param lineNo
     */
    public static void setBlueBorder(Workbook workbook,Sheet sheet,Integer lineNo){

        CellStyle oldCellStyle = sheet.getRow(lineNo).getCell(34).getCellStyle();
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.cloneStyleFrom(oldCellStyle);
        cellStyle.setTopBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);

        cellStyle.setLeftBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);

        cellStyle.setRightBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);

        cellStyle.setBottomBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);

        sheet.getRow(lineNo).getCell(34).setCellStyle(cellStyle);
    }







    public static void setLineBackColor(Workbook workbook,Row row,short fg){
        int cellNum = row.getPhysicalNumberOfCells();
        Cell cell = null;
        for(int i=0;i<cellNum;i++){
            cell = row.getCell(i);
            CellStyle cellStyle = getNewCellStyle(workbook,cell);
            cellStyle.setFillForegroundColor(fg);
            cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            cell.setCellStyle(cellStyle);
        }
    }

    private static CellStyle getNewCellStyle(Workbook workbook,Cell cell){
        CellStyle oldCellStyle = cell.getCellStyle();
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.cloneStyleFrom(oldCellStyle);
        return cellStyle;
    }

    private static void setBorder(Integer[] position,Workbook workbook,Sheet sheet) {
        Cell cell = null;
        Row row = null;
        CellStyle cellStyle = null;
        Row startLine = sheet.getRow(position[0]);
        int columns = startLine.getPhysicalNumberOfCells();
        //设置起始行top
        for(int i=0;i<columns;i++){
            cell = startLine.getCell(i);
            cellStyle = getNewCellStyle(workbook,cell);
            cellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
            cellStyle.setBorderTop(HSSFCellStyle.BORDER_MEDIUM_DASHED);
            cell.setCellStyle(cellStyle);
        }
        Row endLine = sheet.getRow(position[2]);
        for(int i=0;i<columns;i++){
            cell = endLine.getCell(i);
            cellStyle = getNewCellStyle(workbook,cell);
            cellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
            cellStyle.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM_DASHED);
            cell.setCellStyle(cellStyle);
        }
        for(int i = position[0];i<=position[2];i++){
            row = sheet.getRow(i);

            cell = row.getCell(position[1]);
            cellStyle = getNewCellStyle(workbook,cell);
            cellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
            cellStyle.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM_DASHED);
            cell.setCellStyle(cellStyle);

            cell = row.getCell(position[3]);
            cellStyle = getNewCellStyle(workbook,cell);
            cellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
            cellStyle.setBorderRight(HSSFCellStyle.BORDER_MEDIUM_DASHED);
            cell.setCellStyle(cellStyle);
        }
    }

    private static void resetPoints(Workbook workbook,Cell cell,Integer points,Integer num) {
        if(Validate.isNullOrEmpty(points)){
            return ;
        }

        if(points%num == 0) {
            cell.setCellValue((double) points / num);
        } else {
            cell.setCellValue(points);
            CellStyle cellStyle = getNewCellStyle(workbook,cell);
            cellStyle.setFillForegroundColor(HSSFColor.YELLOW.index);
            cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            cell.setCellStyle(cellStyle);
        }

    }
}

