package com.arvato.cc.service1.impl;

import com.arvato.cc.constant.Constants;
import com.arvato.cc.dao1.OperationLogDao;
import com.arvato.cc.dao1.SaleStructureDao;
import com.arvato.cc.dao1.SkuDao;
import com.arvato.cc.form.SaleStructureBean;
import com.arvato.cc.form.SaleStructureSummaryBean;
import com.arvato.cc.log.LogMessageUtil;
import com.arvato.cc.model.OperationHistory;
import com.arvato.cc.service1.OperationLogService;
import com.arvato.cc.service1.SaleStructureService;
import com.arvato.cc.util.CommonHelper;
import com.arvato.cc.util.Validate;
import com.arvato.cc.util.finance.SaleStructureReportExcel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;
import sun.reflect.generics.reflectiveObjects.LazyReflectiveObjectGenerator;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: caol005
 * Date: 15-8-17
 * Time: 下午2:45
 * To change this template use File | Settings | File Templates.
 */
@Service
public class SaleStructureServiceImpl  implements SaleStructureService {
    private static final Log log = LogFactory.getLog(DeliveryReportServiceImpl.class);
    private static LogMessageUtil msg = new LogMessageUtil("SALESTRUCTURE_REPORT");
    @Autowired
    private SaleStructureDao saleStructureDao;

    @Autowired
    private SkuDao skuDao;

    @Autowired
    private OperationLogService operationLogService;
    @Autowired
    private OperationLogDao operationLogDao;

    @Value("${saleStructureTempFile}")
    private String saleStructureTempFile;


    public String generateSaleStructureReport(Map<String, String> queryParams) {
        log.info(msg.getStartMessage("findSaleStructure"));
        StopWatch fetch = new StopWatch("findSaleStructure");
        fetch.start("findSaleStructureSum");
        List<Map<String,Object>> listSummary = saleStructureDao.findSaleStructureSum(queryParams);
        if(Validate.isNullOrEmpty(listSummary)) {
            return "none";
        }
        fetch.stop();
        int saleQuantitySummary = 0;
        double saleAmountSummary = 0.0;
        if(!Validate.isNullOrEmpty(listSummary)){
            Map<String,Object> objects = listSummary.get(0);
            if(!Validate.isNullOrEmpty(objects.get("saleQuantitySum")))
                saleQuantitySummary = Integer.parseInt(objects.get("saleQuantitySum").toString());
            if(!Validate.isNullOrEmpty(objects.get("saleAmountSum")))
                saleAmountSummary = CommonHelper.getDouble(Double.parseDouble(objects.get("saleAmountSum").toString()));
        }

        List<SaleStructureBean> saleStructureBeans = new ArrayList<SaleStructureBean>();
        fetch.start("findSaleStructure");
        List<Map<String,Object>> list = saleStructureDao.findSaleStructure(queryParams);
        if(Validate.isNullOrEmpty(list)) {
            return "none";
        }
        fetch.stop();
        SaleStructureBean saleStructureBean = null;
        int i = 0;
        double sapAmountSummary = 0.0;
        Map<String,Double> map = new HashMap<String, Double>();
        for(Map<String,Object> obj:list) {
            saleStructureBean = new SaleStructureBean();
            i++;
            saleStructureBean.setNum(i+"");
            String skuId = obj.get("matnr").toString();
            String cid = obj.get("cid").toString();
            int saleQuantity = 0;
            if(!Validate.isNullOrEmpty(obj.get("saleQuantity")))
                saleQuantity = Integer.parseInt(obj.get("saleQuantity").toString());
            double saleAmount = 0.0;
            if(!Validate.isNullOrEmpty(obj.get("saleAmount")))
                saleAmount = CommonHelper.DoubleParse(Double.parseDouble(obj.get("saleAmount").toString()));
            double sapPrice = 0.0;
            if(!Validate.isNullOrEmpty(obj.get("sapPrice")))
                sapPrice = Double.parseDouble(obj.get("sapPrice").toString());
            saleStructureBean.setMatnr(skuId);
            saleStructureBean.setSaleQuantity(saleQuantity);
            saleStructureBean.setSaleAmount(
                    CommonHelper.DoubleParse(saleAmount));
            saleStructureBean.setSapPrice(CommonHelper.saveByScale(sapPrice, 0));
            saleStructureBean.setSapAmount(CommonHelper.getMul(sapPrice,saleQuantity));
            //todo data format
            saleStructureBean.setSaleQuantityPercentage(CommonHelper.DoubleParseOnePercent(Double.parseDouble(obj.get("saleQuantity").toString())/saleQuantitySummary*100)+"%");
            saleStructureBean.setSaleAmountPercentage(CommonHelper.DoubleParseOnePercent(saleAmount/saleAmountSummary*100)+"%");
            saleStructureBean.setSapDiscount(CommonHelper.DoubleParseOnePercent((CommonHelper.DoubleParse(saleAmount) / CommonHelper.DoubleParse(
                    saleStructureBean.getSapAmount()) - 1) * 100)+"%");
            saleStructureBeans.add(saleStructureBean);

            log.info(msg.getProcessMessage("sapAmountSummary" + sapAmountSummary ));
            sapAmountSummary = CommonHelper.getAdd(sapAmountSummary,CommonHelper.getMul(sapPrice,saleQuantity)) ;
            log.info(msg.getProcessMessage("sapPrice" + sapPrice ));
            log.info(msg.getProcessMessage("saleQuantity" + saleQuantity ));
            log.info(msg.getProcessMessage("sapAmountSummary" + sapAmountSummary ));
            log.info(msg.getProcessMessage("--------------------------") );
            if(Validate.isNullOrEmpty(map.get(cid))) {
                map.put(cid,CommonHelper.getMul(sapPrice,saleQuantity));
            } else {
                Double oldSaleAmount = map.get(cid);
                map.put(cid,CommonHelper.getAdd(oldSaleAmount,CommonHelper.getMul(sapPrice,saleQuantity)));
            }
        }

        //summary row
        saleStructureBean = new SaleStructureBean();
//        saleStructureBean.setNum(i++);
        saleStructureBean.setMatnr("合计");
        saleStructureBean.setSaleQuantity(saleQuantitySummary);
        saleStructureBean.setSaleAmount(CommonHelper.DoubleParse(saleAmountSummary));
        saleStructureBean.setSaleQuantityPercentage("100%");
        saleStructureBean.setSaleAmountPercentage("100%");

        log.info("sapAmountSummary in summary line:" + sapAmountSummary );
        log.info("saleQuantitySummary in summary line:" + saleQuantitySummary );
        saleStructureBean.setSapPrice(CommonHelper.saveByScale(sapAmountSummary / saleQuantitySummary,0));
        saleStructureBean.setSapAmount(CommonHelper.saveByScale(sapAmountSummary));
        saleStructureBean.setSapDiscount(CommonHelper.DoubleParseOnePercent((CommonHelper.DoubleParse(
                saleStructureBean.getSaleAmount()) / CommonHelper.DoubleParse(sapAmountSummary) - 1) * 100)+"%");
        saleStructureBeans.add(saleStructureBean);

        //summary 洗衣机 冰箱 厨卫
        fetch.start("findSaleStructureByCategory");
        List<Map<String,Object>> categoryList = saleStructureDao.findSaleStructureByCategory(queryParams);
        fetch.stop();
        fetch.start("for categoryList");
        for (Map<String,Object> obj:categoryList){
            saleStructureBean = new SaleStructureBean();
//            saleStructureBean.setNum(i++);
            saleStructureBean.setMatnr(obj.get("cid").toString());
            int saleQuantitySum = 0;
            if(!Validate.isNullOrEmpty(obj.get("saleQuantitySum")))
                saleQuantitySum = Integer.parseInt(obj.get("saleQuantitySum").toString());
            double saleAmountSum = 0.0;
            if(!Validate.isNullOrEmpty(obj.get("saleAmountSum")))
                saleAmountSum = CommonHelper.DoubleParse(Double.parseDouble(obj.get("saleAmountSum").toString()));

            saleStructureBean.setSaleQuantity(saleQuantitySum);
            saleStructureBean.setSaleAmount(CommonHelper.DoubleParse(saleAmountSum));
            saleStructureBean.setSaleQuantityPercentage(CommonHelper.DoubleParseOnePercent(CommonHelper.DoubleParse(saleQuantitySum) / saleQuantitySummary *100 )+ "%");
            saleStructureBean.setSaleAmountPercentage(CommonHelper.DoubleParseOnePercent(saleAmountSum / saleAmountSummary * 100) + "%");
            saleStructureBean.setSapPrice(CommonHelper.saveByScale(map.get(obj.get("cid")) / saleQuantitySum,0));
            saleStructureBean.setSapAmount(CommonHelper.DoubleParse( map.get(obj.get("cid")) ) );
            saleStructureBean.setSapDiscount(CommonHelper.DoubleParseOnePercent((CommonHelper.DoubleParse(saleAmountSum) / CommonHelper.DoubleParse(
                    map.get(obj.get("cid").toString())) - 1) * 100) +"%");
            saleStructureBeans.add(saleStructureBean);
        }
        fetch.stop();
        fetch.start("generateOperationHistory");
        OperationHistory operationHistory = operationLogService.generateOperationHistory("", Constants.OperationType.DOWNLOAD_SALE_STRUCTURE.toString(),Constants.ModelType.DOWNLOAD.toString()) ;
        operationLogDao.saveOperationLog(operationHistory);
        fetch.stop();

        FileOutputStream outputStream = null;
        ByteArrayOutputStream bos = null;
        String fileName = saleStructureTempFile + "Sale Structure-"+ CommonHelper.DateFormat(CommonHelper.getThisTimestamp(),"yyyy-MM-dd HH-mm-ss")+".xlsx";
        try{
            outputStream = new FileOutputStream(fileName);
            SaleStructureReportExcel excel = new SaleStructureReportExcel(saleStructureBeans);
            bos = (ByteArrayOutputStream) excel.getOutputStream();
            outputStream.write(bos.toByteArray());
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

            try {
                if(bos!=null){
                    bos.close();
                }
            }catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }

        log.info(msg.getEndMessage("findSaleStructure\r\n"+fetch.prettyPrint()));
        return "";
    }

    public List<SaleStructureBean> findSaleStructure(Map<String, String> queryParams) {
        log.info(msg.getStartMessage("findSaleStructure"));
        StopWatch fetch = new StopWatch("findSaleStructure");
        fetch.start("findSaleStructureSum");
        //查询汇总行start
        List<Map<String,Object>> listSummary = saleStructureDao.findSaleStructureSum(queryParams);
        if(Validate.isNullOrEmpty(listSummary)){
            return null;
        }
        fetch.stop();
        int saleQuantitySummary = 0;
        double saleAmountSummary = 0.0;
        if(!Validate.isNullOrEmpty(listSummary)){
            Map<String,Object> objects = listSummary.get(0);
            if(!Validate.isNullOrEmpty(objects.get("saleQuantitySum")))
                saleQuantitySummary = Integer.parseInt(objects.get("saleQuantitySum").toString());
            if(!Validate.isNullOrEmpty(objects.get("saleAmountSum")))
                saleAmountSummary = CommonHelper.getDouble(Double.parseDouble(objects.get("saleAmountSum").toString()));
        }
        //查询汇总行end
        List<SaleStructureBean> saleStructureBeans = new ArrayList<SaleStructureBean>();
        fetch.start("findSaleStructure");
        List<Map<String,Object>> list = saleStructureDao.findSaleStructure(queryParams);
        if(Validate.isNullOrEmpty(list)) {
            return null;
        }
        fetch.stop();
        SaleStructureBean saleStructureBean = null;
        int i = 0;
        double sapAmountSummary = 0.0;
        Map<String,Double> map = new HashMap<String, Double>();
        for(Map<String,Object> obj:list) {
            saleStructureBean = new SaleStructureBean();
            i++;
            saleStructureBean.setNum(i+"");
            String skuId = obj.get("matnr").toString();
            String cid = obj.get("cid").toString();
            int saleQuantity = 0;
            if(!Validate.isNullOrEmpty(obj.get("saleQuantity")))
                saleQuantity = Integer.parseInt(obj.get("saleQuantity").toString());
            double saleAmount = 0.0;
            if(!Validate.isNullOrEmpty(obj.get("saleAmount")))
                saleAmount = CommonHelper.DoubleParse(Double.parseDouble(obj.get("saleAmount").toString()));
            double sapPrice = 0.0;
            if(!Validate.isNullOrEmpty(obj.get("sapPrice")))
                sapPrice = Double.parseDouble(obj.get("sapPrice").toString());
            saleStructureBean.setMatnr(skuId);
            saleStructureBean.setSaleQuantity(saleQuantity);
            saleStructureBean.setSaleAmount(CommonHelper.DoubleParse(saleAmount));
            saleStructureBean.setSapPrice(CommonHelper.saveByScale(sapPrice, 0));
            saleStructureBean.setSapAmount(CommonHelper.getMul(sapPrice,saleQuantity));
            saleStructureBean.setSaleQuantityPercentage(CommonHelper.DoubleParseOnePercent(Double.parseDouble(obj.get("saleQuantity").toString())/saleQuantitySummary*100)+"%");
            saleStructureBean.setSaleAmountPercentage(CommonHelper.DoubleParseOnePercent(saleAmount/saleAmountSummary*100)+"%");
            saleStructureBean.setSapDiscount(CommonHelper.DoubleParseOnePercent((CommonHelper.DoubleParse(saleAmount) / CommonHelper.DoubleParse(saleStructureBean.getSapAmount()) - 1) * 100 ) +"%");
            saleStructureBeans.add(saleStructureBean);

            log.info(msg.getProcessMessage("sapAmountSummary" + sapAmountSummary ));
            sapAmountSummary = CommonHelper.getAdd(sapAmountSummary,CommonHelper.getMul(sapPrice,saleQuantity)) ;
            log.info(msg.getProcessMessage("sapPrice" + sapPrice ));
            log.info(msg.getProcessMessage("saleQuantity" + saleQuantity ));
            log.info(msg.getProcessMessage("sapAmountSummary" + sapAmountSummary ));
            log.info(msg.getProcessMessage("--------------------------") );
            if(Validate.isNullOrEmpty(map.get(cid))) {
                map.put(cid,CommonHelper.getMul(sapPrice,saleQuantity));
            } else {
                Double oldSaleAmount = map.get(cid);
                map.put(cid,CommonHelper.getAdd(oldSaleAmount,CommonHelper.getMul(sapPrice,saleQuantity)));
            }
        }

        //summary row
        saleStructureBean = new SaleStructureBean();
//        saleStructureBean.setNum(i++);
        saleStructureBean.setMatnr("合计");
        saleStructureBean.setSaleQuantity(saleQuantitySummary);
        saleStructureBean.setSaleAmount(CommonHelper.DoubleParse(saleAmountSummary));
        saleStructureBean.setSaleQuantityPercentage("100%");
        saleStructureBean.setSaleAmountPercentage("100%");
        log.info(msg.getProcessMessage("sapAmountSummary in summary line:" + sapAmountSummary ));
        log.info(msg.getProcessMessage("saleQuantitySummary in summary line:" + saleQuantitySummary) );
        saleStructureBean.setSapPrice(CommonHelper.saveByScale(sapAmountSummary / saleQuantitySummary,0));
        saleStructureBean.setSapAmount(CommonHelper.saveByScale(sapAmountSummary));
        saleStructureBean.setSapDiscount(CommonHelper.DoubleParseOnePercent((CommonHelper.DoubleParse(saleStructureBean.getSaleAmount()) / CommonHelper.DoubleParse(sapAmountSummary) - 1) * 100) +"%");
        saleStructureBeans.add(saleStructureBean);

        //summary 洗衣机 冰箱 厨卫
        fetch.start("findSaleStructureByCategory");
        List<Map<String,Object>> categoryList = saleStructureDao.findSaleStructureByCategory(queryParams);
        if(Validate.isNullOrEmpty(categoryList)) {
            return null;
        }
        fetch.stop();
        fetch.start("for categoryList");
        for (Map<String,Object> obj:categoryList){
            saleStructureBean = new SaleStructureBean();
//            saleStructureBean.setNum(i++);
            saleStructureBean.setMatnr(obj.get("cid").toString());
            int saleQuantitySum = 0;
            if(!Validate.isNullOrEmpty(obj.get("saleQuantitySum")))
                saleQuantitySum = Integer.parseInt(obj.get("saleQuantitySum").toString());
            double saleAmountSum = 0.0;
            if(!Validate.isNullOrEmpty(obj.get("saleAmountSum")))
                saleAmountSum = CommonHelper.DoubleParse(Double.parseDouble(obj.get("saleAmountSum").toString()));

            saleStructureBean.setSaleQuantity(saleQuantitySum);
            saleStructureBean.setSaleAmount(
                    CommonHelper.DoubleParse(saleAmountSum));
            saleStructureBean.setSaleQuantityPercentage(CommonHelper
                    .DoubleParseOnePercent(
                            CommonHelper.DoubleParse(saleQuantitySum)
                                    / saleQuantitySummary * 100) + "%");
            saleStructureBean.setSaleAmountPercentage(CommonHelper
                    .DoubleParseOnePercent(
                            saleAmountSum / saleAmountSummary * 100) + "%");
            saleStructureBean.setSapPrice(CommonHelper.saveByScale(
                    map.get(obj.get("cid")) / saleQuantitySum, 0));
          //  saleStructureBean.setSapAmount(CommonHelper.DoubleParse(map.get(obj.get("cid"))));
            saleStructureBean.setSapAmount(CommonHelper.saveByScale(CommonHelper.DoubleParse(map.get(obj.get("cid")))));
            saleStructureBean.setSapDiscount(CommonHelper.DoubleParseOnePercent((CommonHelper.DoubleParse(saleAmountSum) / CommonHelper.DoubleParse(map.get(obj.get("cid").toString())) - 1) * 100) +"%");
            saleStructureBeans.add(saleStructureBean);
        }
        fetch.stop();
        fetch.start("generateOperationHistory");
        OperationHistory operationHistory = operationLogService.generateOperationHistory("", Constants.OperationType.DOWNLOAD_SALE_STRUCTURE.toString(),Constants.ModelType.DOWNLOAD.toString()) ;
        operationLogDao.saveOperationLog(operationHistory);
        fetch.stop();
        log.info(msg.getEndMessage("findSaleStructure\r\n"+fetch.prettyPrint()));
        return saleStructureBeans;
    }


//    public List<SaleStructureBean> findSaleStructure(Map<String, String> queryParams) {
//        log.info(msg.getStartMessage("findSaleStructure"));
//        StopWatch fetch = new StopWatch("findSaleStructure");
//        fetch.start("findSaleStructureSum");
//        List<Map<String,Object>> listSummary = saleStructureDao.findSaleStructureSum(queryParams);
//        fetch.stop();
//        SaleStructureSummaryBean saleStructureSummaryBean = null;
//        if(!Validate.isNullOrEmpty(listSummary)){
//            Map<String,Object> objects = listSummary.get(0);
//            saleStructureSummaryBean = new SaleStructureSummaryBean();
//            int saleQuantitySummary = 0;
//            if(!Validate.isNullOrEmpty(objects.get("saleQuantitySum")))
//                saleQuantitySummary = Integer.parseInt(objects.get("saleQuantitySum").toString());
//            double saleAmountSummary = 0.0;
//            if(!Validate.isNullOrEmpty(objects.get("saleAmountSum")))
//                saleAmountSummary = CommonHelper.getDouble(Double.parseDouble(objects.get("saleAmountSum").toString()));
//            saleStructureSummaryBean.setSaleQuantitySummary(saleQuantitySummary);
//            saleStructureSummaryBean.setSaleAmountSummary(CommonHelper.DoubleParse(saleAmountSummary));
//        }
//
//        List<SaleStructureBean> saleStructureBeans = new ArrayList<SaleStructureBean>();
//        fetch.start("findSaleStructure");
//        List<Map<String,Object>> list = saleStructureDao.findSaleStructure(queryParams);
//        fetch.stop();
//        SaleStructureBean saleStructureBean = null;
//        int i = 1;
//        double sapAmountSummary = 0.0;
//        for(Map<String,Object> obj:list) {
//            saleStructureBean = new SaleStructureBean();
//            saleStructureBean.setNum(i++);
//            String skuId = obj.get("matnr").toString();
//            int saleQuantity = 0;
//            if(!Validate.isNullOrEmpty(obj.get("saleQuantity")))
//                saleQuantity = Integer.parseInt(obj.get("saleQuantity").toString());
//            double saleAmount = 0.0;
//            if(!Validate.isNullOrEmpty(obj.get("saleAmount")))
//                saleAmount = CommonHelper.DoubleParse(Double.parseDouble(obj.get("saleAmount").toString()));
//            double sapPrice = 0.0;
//            if(!Validate.isNullOrEmpty(obj.get("sapPrice")))
//                sapPrice = Double.parseDouble(obj.get("sapPrice").toString());
//            saleStructureBean.setMatnr(skuId);
//            saleStructureBean.setSaleQuantity(saleQuantity);
//            saleStructureBean.setSaleAmount(CommonHelper.DoubleParse(saleAmount));
//            saleStructureBean.setSapPrice(CommonHelper.DoubleParse(sapPrice));
//            //todo data format
//            saleStructureBean.setSaleQuantityPercentage(CommonHelper.DoubleParseOnePercent(Double.parseDouble(obj.get("saleQuantity").toString())/saleStructureSummaryBean.getSaleQuantitySummary()*100)+"%");
//            saleStructureBean.setSaleAmountPercentage(CommonHelper.DoubleParseOnePercent(saleAmount/saleStructureSummaryBean.getSaleAmountSummary()*100)+"%");
//            saleStructureBean.setSapDiscount(CommonHelper.DoubleParse(saleAmount / saleStructureBean.getSapAmount() - 1));
//            saleStructureBeans.add(saleStructureBean);
//            sapAmountSummary += sapPrice * saleQuantity;
//        }
//
//        //summary row
//        saleStructureBean = new SaleStructureBean();
//        saleStructureBean.setNum(i++);
//        saleStructureBean.setMatnr("");
//        saleStructureBean.setSaleQuantity(saleStructureSummaryBean.getSaleQuantitySummary());
//        saleStructureBean.setSaleAmount(CommonHelper.DoubleParse(saleStructureSummaryBean.getSaleAmountSummary()));
//        saleStructureBean.setSaleQuantityPercentage("100%");
//        saleStructureBean.setSaleAmountPercentage("100%");
//        saleStructureBean.setSapPrice(CommonHelper.DoubleParse(sapAmountSummary / saleStructureSummaryBean.getSaleQuantitySummary()));
//        saleStructureBean.setSapAmount(CommonHelper.DoubleParse(sapAmountSummary));
//        saleStructureBean.setSapDiscount(CommonHelper.DoubleParse(saleStructureBean.getSaleAmount() / sapAmountSummary - 1));
//        saleStructureBeans.add(saleStructureBean);
//
//        //summary 洗衣机 冰箱 厨卫
//        int totalQuantity = 0;
//        double totalAmount = 0.0;
//        fetch.start("findSaleStructureByCategorySum");
//        List<Map<String,Object>> categoryListSum = saleStructureDao.findSaleStructureByCategorySum(queryParams);
//        fetch.stop();
//        if(!Validate.isNullOrEmpty(categoryListSum)){
//            Map<String,Object> objects = categoryListSum.get(0);
//            saleStructureSummaryBean = new SaleStructureSummaryBean();
//            int saleQuantitySummary = 0;
//            if(!Validate.isNullOrEmpty(objects.get("saleQuantitySum")))
//                saleQuantitySummary = Integer.parseInt(objects.get("saleQuantitySum").toString());
//            double saleAmountSummary = 0.0;
//            if(!Validate.isNullOrEmpty(objects.get("saleAmountSum")))
//                saleAmountSummary = CommonHelper.DoubleParse(Double.parseDouble(objects.get("saleAmountSum").toString()));
//            saleStructureSummaryBean.setSaleQuantitySummary(saleQuantitySummary);
//            saleStructureSummaryBean.setSaleAmountSummary(CommonHelper.DoubleParse(saleAmountSummary));
//            totalQuantity += saleQuantitySummary;
//            totalAmount += saleAmountSummary;
//        }
//        fetch.start("findSaleStructureByCategory");
//        List<Map<String,Object>> categoryList = saleStructureDao.findSaleStructureByCategory(queryParams);
//        fetch.stop();
//        fetch.start("for categoryList");
//        for (Map<String,Object> obj:categoryList){
//            saleStructureBean = new SaleStructureBean();
//            saleStructureBean.setNum(i++);
//            saleStructureBean.setMatnr(obj.get("cid").toString());
//            int saleQuantitySum = 0;
//            if(!Validate.isNullOrEmpty(obj.get("saleQuantitySum")))
//                saleQuantitySum = Integer.parseInt(obj.get("saleQuantitySum").toString());
//            double saleAmountSum = 0.0;
//            if(!Validate.isNullOrEmpty(obj.get("saleAmountSum")))
//                saleAmountSum = CommonHelper.DoubleParse(Double.parseDouble(obj.get("saleAmountSum").toString()));
//
//            saleStructureBean.setSaleQuantity(saleQuantitySum);
//            saleStructureBean.setSaleAmount(CommonHelper.DoubleParse(saleAmountSum));
//            saleStructureBean.setSaleQuantityPercentage(CommonHelper.DoubleParseOnePercent(Double.parseDouble(obj.get("saleQuantitySum").toString()) / totalQuantity *100 )+ "%");
//            saleStructureBean.setSaleAmountPercentage(CommonHelper.DoubleParseOnePercent(saleAmountSum / totalAmount * 100) + "%");
//            saleStructureBean.setSapPrice(CommonHelper.DoubleParse(saleStructureSummaryBean.getSaleAmountSummary() / saleQuantitySum));
//            saleStructureBean.setSapAmount(CommonHelper.DoubleParse(saleStructureSummaryBean.getSaleAmountSummary()));
//            saleStructureBean.setSapDiscount(CommonHelper.DoubleParse(saleStructureBean.getSaleAmount() / sapAmountSummary - 1) );
//            saleStructureBeans.add(saleStructureBean);
//        }
//        fetch.stop();
//        fetch.start("generateOperationHistory");
//        OperationHistory operationHistory = operationLogService.generateOperationHistory("", Constants.OperationType.DOWNLOAD_SALE_STRUCTURE.toString(),Constants.ModelType.DOWNLOAD.toString()) ;
//        operationLogDao.saveOperationLog(operationHistory);
//        fetch.stop();
//        log.info(msg.getEndMessage("findSaleStructure\r\n"+fetch.prettyPrint()));
//        return saleStructureBeans;
//    }
}
