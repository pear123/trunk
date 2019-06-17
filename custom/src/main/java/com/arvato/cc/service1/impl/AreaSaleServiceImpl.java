package com.arvato.cc.service1.impl;

import com.arvato.cc.constant.Constants;
import com.arvato.cc.dao1.AreaSaleDao;
import com.arvato.cc.dao1.OperationLogDao;
import com.arvato.cc.form.AreaSaleModel;
import com.arvato.cc.form.ComboStore;
import com.arvato.cc.form.SaleStructureBean;
import com.arvato.cc.log.LogMessageUtil;
import com.arvato.cc.model.OperationHistory;
import com.arvato.cc.service1.AreaSaleService;
import com.arvato.cc.service1.OperationLogService;
import com.arvato.cc.util.CommonHelper;
import com.arvato.cc.util.FileUtil;
import com.arvato.cc.util.Validate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: caol005
 * Date: 15-8-17
 * Time: 下午2:45
 * To change this template use File | Settings | File Templates.
 */
@Service
public class AreaSaleServiceImpl implements AreaSaleService {
    private static final Log log = LogFactory.getLog(AreaSaleServiceImpl.class);
    private static LogMessageUtil msg = new LogMessageUtil("AREA_SALE_REPORT");

    @Autowired
    private AreaSaleDao areaSaleDao;

    @Autowired
    private OperationLogService operationLogService;
    @Autowired
    private OperationLogDao operationLogDao;

    @Value("${areaSaleTempFile}")
    private String areaSaleTempFile;

    public String generateAreaSaleReport(Map<String, String> queryParams) {
        log.info(msg.getStartMessage("buildAreaSale"));
        StopWatch fetch = new StopWatch("buildAreaSale");
        //all area in map
        Map<String,Integer> columnMap = new HashMap<String, Integer>();//title中地址对应列号
        List<String> titleList = new ArrayList<String>();
        Map<String,String> codeMap = new HashMap<String, String>();
        Map<String,String> areaMapDictionary = new HashMap<String, String>();//key:地址code，value:parent_code
        fetch.start("findArea");
        List<Map<String, Object>> areas = areaSaleDao.findArea();
        fetch.stop();
        int startColumnIndex = 5;
        fetch.start("for areas map");
        for(Map<String, Object> map : areas) {
            String pcode = "";
            String pname = "";
            String code = "";
            String name = "";
            if(!Validate.isNullOrEmpty(map.get("pcode")))
                pcode = map.get("pcode").toString();
            if(!Validate.isNullOrEmpty(map.get("pname")))
                pname = map.get("pname").toString();

            if(!Validate.isNullOrEmpty(map.get("code")))
                code = map.get("code").toString();
            if(!Validate.isNullOrEmpty(map.get("name")))
                name = map.get("name").toString();

            //code --> name ,code is unique
            codeMap.put(code ,name);
            codeMap.put(pcode,pname);
            areaMapDictionary.put(code ,pcode); // code --> parent_code
            titleList.add(code);//标题有序显示
        }
        fetch.stop();
        for(String code:titleList) {
            columnMap.put(code ,startColumnIndex++);
        }
        fetch.start("create excel");
        SXSSFWorkbook excel = new SXSSFWorkbook();
        Sheet sheet = excel.createSheet();
        sheet = generateAreaTitleRow(sheet, 3, 5,titleList,codeMap);
        fetch.stop();
        //all data in map
        // sku ,area, quantity
        Map<String,Map<String,Integer>> maps = new HashMap<String,Map<String,Integer>>();
        Map<String,Map<String,Integer>> cidAreaMap = new HashMap<String,Map<String,Integer>>();
        fetch.start("findAreaSale");
        List<Map<String, Object>> areaSales = areaSaleDao.findAreaSale(queryParams);
        if(Validate.isNullOrEmpty(areaSales)) {
            return "none";
        }
        fetch.stop();
        Map<String,Double> priceMap = new HashMap<String, Double>();
        Map<String,String> cidMap = new HashMap<String, String>();
        Map<String,Integer> cidQuantityMap = new HashMap<String, Integer>();//用于计算sku的合并单元格
        List<String> skuList = new ArrayList<String>();
        fetch.start("for areaSales");
        for(Map<String, Object> map : areaSales) {
            String cid = map.get("cid").toString();
            String sku = map.get("sku_id").toString();
            String code = map.get("code").toString();//must be code
            Integer num = Integer.parseInt(map.get("num").toString());
            Double sapPrice = 0.00;
            if(!Validate.isNullOrEmpty(map.get("sapprice"))) {
                sapPrice = Double.parseDouble(map.get("sapprice").toString());
            }

            Map<String,Integer> areaMap = maps.get(sku);
            if(Validate.isNullOrEmpty(areaMap)) {
                areaMap = new HashMap<String, Integer>();
            }
            if(!skuList.contains(sku)){
                skuList.add(sku);
                if(Validate.isNullOrEmpty(cidQuantityMap.get(cid))) {
                    cidQuantityMap.put(cid,1);
                } else {
                    int quantity = cidQuantityMap.get(cid);
                    cidQuantityMap.put(cid,quantity+1);
                }
            }
            areaMap.put(code,num);
            maps.put(sku,areaMap);
            priceMap.put(sku,sapPrice);
            cidMap.put(sku,cid);

            Map<String,Integer> childMap=cidAreaMap.get(cid);
            if(null==childMap){
                childMap=new HashMap<String,Integer>();
                childMap.put(code,num);
                cidAreaMap.put(cid,childMap);
            }else{
                if(null==childMap.get(code)){
                    childMap.put(code,num);
                }else{
                    childMap.put(code,num+childMap.get(code));
                }
            }
        }
        fetch.stop();
        fetch.start("generateAreaDataRow");
        sheet = generateAreaDataRow(sheet,skuList,maps,cidQuantityMap,columnMap,areaMapDictionary,priceMap,cidMap,queryParams);
        fetch.stop();
        fetch.start("generateOtherSummary");
//        generateOtherSummary(sheet,3,columnMap,queryParams);
        //generateSummaryBottom(sheet,3,columnMap,queryParams);
        generateSummaryBottom(sheet,3,columnMap,queryParams,cidAreaMap);
        fetch.stop();
        fetch.start("generateOperationHistory");
        OperationHistory operationHistory = operationLogService.generateOperationHistory("", Constants.OperationType.DOWNLOAD_AREA_SALE.toString(),Constants.ModelType.DOWNLOAD.toString()) ;
        operationLogDao.saveOperationLog(operationHistory);
        fetch.stop();

        FileOutputStream outputStream = null;
        String fileName = areaSaleTempFile + "RegionSale-"+ CommonHelper.DateFormat(CommonHelper.getThisTimestamp(),"yyyy-MM-dd HH-mm-ss")+".xlsx";
        try{
            outputStream = new FileOutputStream(fileName);
            excel.write(outputStream);
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

        log.info(msg.getEndMessage("buildAreaSale\r\n"+fetch.prettyPrint()));
        return "";
    }


    public XSSFWorkbook buildAreaSale(Map<String, String> queryParams) {
        log.info(msg.getStartMessage("buildAreaSale"));
        StopWatch fetch = new StopWatch("buildAreaSale");
        //all area in map
        Map<String,Integer> columnMap = new HashMap<String, Integer>();//title中地址对应列号
        List<String> titleList = new ArrayList<String>();
        Map<String,String> codeMap = new HashMap<String, String>();
        Map<String,String> areaMapDictionary = new HashMap<String, String>();//key:地址code，value:parent_code
        fetch.start("findArea");
        List<Map<String, Object>> areas = areaSaleDao.findArea();
        fetch.stop();
        int startColumnIndex = 5;
        fetch.start("for areas map");
        for(Map<String, Object> map : areas) {
            String pcode = "";
            String pname = "";
            String code = "";
            String name = "";
            if(!Validate.isNullOrEmpty(map.get("pcode")))
                pcode = map.get("pcode").toString();
            if(!Validate.isNullOrEmpty(map.get("pname")))
                pname = map.get("pname").toString();

            if(!Validate.isNullOrEmpty(map.get("code")))
                code = map.get("code").toString();
            if(!Validate.isNullOrEmpty(map.get("name")))
                name = map.get("name").toString();

            //code --> name ,code is unique
            codeMap.put(code ,name);
            codeMap.put(pcode,pname);
            areaMapDictionary.put(code ,pcode); // code --> parent_code
            titleList.add(code);//标题有序显示
        }
        fetch.stop();
        for(String code:titleList) {
            columnMap.put(code ,startColumnIndex++);
        }
        fetch.start("create excel");
        XSSFWorkbook excel = new XSSFWorkbook();
        Sheet sheet = excel.createSheet();
        sheet = generateAreaTitleRow(sheet, 3, 5,titleList,codeMap);
        fetch.stop();
        //all data in map
        // sku ,area, quantity
        Map<String,Map<String,Integer>> maps = new HashMap<String,Map<String,Integer>>();
        fetch.start("findAreaSale");
        List<Map<String, Object>> areaSales = areaSaleDao.findAreaSale(queryParams);
        if(Validate.isNullOrEmpty(areaSales)){
            return null;
        }
        fetch.stop();
        Map<String,Double> priceMap = new HashMap<String, Double>();
        Map<String,String> cidMap = new HashMap<String, String>();
        Map<String,Integer> cidQuantityMap = new HashMap<String, Integer>();//用于计算sku的合并单元格
        List<String> skuList = new ArrayList<String>();
        Map<String,Map<String,Integer>> cidAreaMap = new HashMap<String,Map<String,Integer>>();
        fetch.start("for areaSales");
        for(Map<String, Object> map : areaSales) {
            String cid = map.get("cid").toString();
            String sku = map.get("sku_id").toString();
            String code = map.get("code").toString();//must be code
            Integer num = Integer.parseInt(map.get("num").toString());
            Double sapPrice = 0.00;
            if(!Validate.isNullOrEmpty(map.get("sapprice"))) {
                sapPrice = Double.parseDouble(map.get("sapprice").toString());
            }

            Map<String,Integer> areaMap = maps.get(sku);
            if(Validate.isNullOrEmpty(areaMap)) {
                areaMap = new HashMap<String, Integer>();
            }
            if(!skuList.contains(sku)){
                skuList.add(sku);
                if(Validate.isNullOrEmpty(cidQuantityMap.get(cid))) {
                    cidQuantityMap.put(cid,1);
                } else {
                    int quantity = cidQuantityMap.get(cid);
                    cidQuantityMap.put(cid,quantity+1);
                }
            }
            areaMap.put(code,num);
            maps.put(sku,areaMap);
            priceMap.put(sku,sapPrice);
            cidMap.put(sku,cid);

            Map<String,Integer> childMap=cidAreaMap.get(cid);
            if(null==childMap){
                childMap=new HashMap<String,Integer>();
                childMap.put(code,num);
                cidAreaMap.put(cid,childMap);
            }else{
                if(null==childMap.get(code)){
                    childMap.put(code,num);
                }else{
                    childMap.put(code,num+childMap.get(code));
                }
            }
        }
        fetch.stop();
        fetch.start("generateAreaDataRow");
        sheet = generateAreaDataRow(sheet,skuList,maps,cidQuantityMap,columnMap,areaMapDictionary,priceMap,cidMap,queryParams);
        fetch.stop();
        fetch.start("generateOtherSummary");
//        generateOtherSummary(sheet,3,columnMap,queryParams);
        generateSummaryBottom(sheet,3,columnMap,queryParams,cidAreaMap);
        fetch.stop();
        fetch.start("generateOperationHistory");
        OperationHistory operationHistory = operationLogService.generateOperationHistory("", Constants.OperationType.DOWNLOAD_AREA_SALE.toString(),Constants.ModelType.DOWNLOAD.toString()) ;
        operationLogDao.saveOperationLog(operationHistory);
        fetch.stop();
        log.info(msg.getEndMessage("buildAreaSale\r\n"+fetch.prettyPrint()));
        return excel;
    }

    public List<AreaSaleModel> getAreaSaleModel(Map<String, String> queryParams){
        XSSFWorkbook sxssfWorkbook = this.buildAreaSale( queryParams);
        if(Validate.isNullOrEmpty(sxssfWorkbook)){
            return null;
        }
        Sheet sheet = sxssfWorkbook.getSheetAt(0);
        List<List<String>> lineList = FileUtil.getLineStringList(sheet,42,4,1);
        return convertLineListToAreaList(lineList);
    }

    private static List<AreaSaleModel> convertLineListToAreaList(List<List<String>> lineList){
        List<AreaSaleModel> areaSaleModelList = new ArrayList<AreaSaleModel>();
        AreaSaleModel areaSaleModel = null;
        String cid = "";
        int i = 0;
        for(List<String> list : lineList) {
            i++;
            if(i%2==0){
                continue;
            }
            areaSaleModel = new AreaSaleModel();
            if(list.size()!=0){
                cid = list.get(0);
                if("TTL".equals(cid)){
                    break;
                }
                areaSaleModel.setCid(cid);
                areaSaleModel.setMatrn(list.get(1));
                areaSaleModel.setPrice(list.get(2));
                areaSaleModel.setQuantity(list.get(3));
                areaSaleModel.setPzj(list.get(4));
                areaSaleModel.setPbj(list.get(5));
                areaSaleModel.setPgd(list.get(6));
                areaSaleModel.setPhb(list.get(7));
                areaSaleModel.setPsc(list.get(8));
                areaSaleModel.setCd(list.get(9));
                areaSaleModel.setXa(list.get(10));
                areaSaleModel.setLz(list.get(11));
                areaSaleModel.setKm(list.get(12));
                areaSaleModel.setGy(list.get(13));
                areaSaleModel.setCq(list.get(14));
                areaSaleModel.setWh(list.get(15));
                areaSaleModel.setNc(list.get(16));
                areaSaleModel.setZz(list.get(17));
                areaSaleModel.setCs(list.get(18));
                areaSaleModel.setBj(list.get(19));
                areaSaleModel.setTy(list.get(20));
                areaSaleModel.setJn(list.get(21));
                areaSaleModel.setSjz(list.get(22));
                areaSaleModel.setSy(list.get(23));
                areaSaleModel.setCc(list.get(24));
                areaSaleModel.setHeb(list.get(25));
                areaSaleModel.setHz(list.get(26));
                areaSaleModel.setHf(list.get(27));
                areaSaleModel.setSh(list.get(28));
                areaSaleModel.setNj(list.get(29));
                areaSaleModel.setWx(list.get(30));
                areaSaleModel.setNb(list.get(31));
                areaSaleModel.setFs(list.get(32));
                areaSaleModel.setNn(list.get(33));
                areaSaleModel.setFz(list.get(34));
                areaSaleModel.setSz(list.get(35));
                areaSaleModel.setTtl(list.get(36));
                areaSaleModel.setPriceTotal(list.get(37));
                areaSaleModel.setCidQuantity(list.get(38));
                areaSaleModel.setCidPrice(list.get(39));
                areaSaleModel.setQuantityPercent(list.get(40));
                areaSaleModel.setPricePercent(list.get(41));
            }
            areaSaleModelList.add(areaSaleModel);
        }
        return areaSaleModelList;
    }

    public List<AreaSaleModel> getAreaSaleModelSummary(Map<String, String> queryParams){
        XSSFWorkbook sxssfWorkbook = this.buildAreaSale( queryParams);
        if(Validate.isNullOrEmpty(sxssfWorkbook)){
            return null;
        }
        Sheet sheet = sxssfWorkbook.getSheetAt(0);
        List<List<String>> lineList = FileUtil.getLineStringList(sheet,42,4,1);
        return convertLineListToAreaListSummary(lineList);
    }

    private static List<AreaSaleModel> convertLineListToAreaListSummary(List<List<String>> lineList){
        List<AreaSaleModel> areaSaleModelList = new ArrayList<AreaSaleModel>();
        AreaSaleModel areaSaleModel = null;
        int i = 0;
        int index = 0;
        for(List<String> list : lineList) {
            i++;
            if(list.size() == 0) {
                continue;
            }
            if("TTL".equals(list.get(0))) {
                index = i;
            }
            if(index==0){
                continue;
            }

            areaSaleModel = new AreaSaleModel();
            areaSaleModel.setCid(list.get(0));
            areaSaleModel.setMatrn(list.get(1));
            areaSaleModel.setPrice(list.get(2));
            if(!"TTL".equals(list.get(0))){
                areaSaleModel.setMatrn(list.get(2));
                areaSaleModel.setPrice("");
            }
            areaSaleModel.setQuantity(list.get(3));
            areaSaleModel.setPzj(list.get(4));
            areaSaleModel.setPbj(list.get(5));
            areaSaleModel.setPgd(list.get(6));
            areaSaleModel.setPhb(list.get(7));
            areaSaleModel.setPsc(list.get(8));
            areaSaleModel.setCd(list.get(9));
            areaSaleModel.setXa(list.get(10));
            areaSaleModel.setLz(list.get(11));
            areaSaleModel.setKm(list.get(12));
            areaSaleModel.setGy(list.get(13));
            areaSaleModel.setCq(list.get(14));
            areaSaleModel.setWh(list.get(15));
            areaSaleModel.setNc(list.get(16));
            areaSaleModel.setZz(list.get(17));
            areaSaleModel.setCs(list.get(18));
            areaSaleModel.setBj(list.get(19));
            areaSaleModel.setTy(list.get(20));
            areaSaleModel.setJn(list.get(21));
            areaSaleModel.setSjz(list.get(22));
            areaSaleModel.setSy(list.get(23));
            areaSaleModel.setCc(list.get(24));
            areaSaleModel.setHeb(list.get(25));
            areaSaleModel.setHz(list.get(26));
            areaSaleModel.setHf(list.get(27));
            areaSaleModel.setSh(list.get(28));
            areaSaleModel.setNj(list.get(29));
            areaSaleModel.setWx(list.get(30));
            areaSaleModel.setNb(list.get(31));
            areaSaleModel.setFs(list.get(32));
            areaSaleModel.setNn(list.get(33));
            areaSaleModel.setFz(list.get(34));
            areaSaleModel.setSz(list.get(35));
            areaSaleModel.setTtl(list.get(36));
            areaSaleModel.setPriceTotal(list.get(37));
            areaSaleModel.setCidQuantity(list.get(38));
            areaSaleModel.setCidPrice(list.get(39));
            areaSaleModel.setQuantityPercent(list.get(40));
            areaSaleModel.setPricePercent(list.get(41));
            areaSaleModelList.add(areaSaleModel);
        }
        return areaSaleModelList;
    }
    /**
     * 有序显示列头
     * @return
     */
    public List<ComboStore> findGridTitle() {
        List<Map<String, Object>> areas = areaSaleDao.findArea();
        List<ComboStore> comboStores = new ArrayList<ComboStore>();
        ComboStore comboStore = null;
        for(Map<String, Object> map : areas) {
            String name = "";
            String code = "";
            if(!Validate.isNullOrEmpty(map.get("name")))
                name = map.get("name").toString();
            if(!Validate.isNullOrEmpty(map.get("code")))
                code = map.get("code").toString();

            comboStore = new ComboStore();
            comboStore.setValue(code);
            comboStore.setLabel(name);
            comboStores.add(comboStore);
        }
        return comboStores;
    }


    /**
     * 生成报表头
     * @param sheet
     * @param rowIndex
     * @param colIndex
     * @param titleList
     * @return
     */
    private Sheet generateAreaTitleRow(Sheet sheet,Integer rowIndex,Integer colIndex,List<String> titleList,Map<String,String> codeMap){
        Row title = sheet.createRow(rowIndex);//创建报表头
        title.createCell(4).setCellValue(" ");
        title.createCell(3).setCellValue("单价");
        title.createCell(2).setCellValue("型号");
        title.createCell(1).setCellValue("品类");

        for(String key:titleList){
            title.createCell(colIndex++).setCellValue(codeMap.get(key));
        }

        title.createCell(colIndex++).setCellValue("TTL");
        title.createCell(colIndex++).setCellValue("总金额");
        title.createCell(colIndex++).setCellValue("品类总数");
        title.createCell(colIndex++).setCellValue("品类金额");
        title.createCell(colIndex++).setCellValue("数量占比");
        title.createCell(colIndex++).setCellValue("金额占比");

        return sheet;
    }

    /**
     *  生成数据行
     * @param sheet
     * @param skuList sku有序展示
     * @param maps sku:area:quantity
     * @param cidQuantityMap 品类下sku的数量
     * @param columnMap area:columnNo
     * @param areaMapDictionary area:parea
     * @param priceMap sku,sapPrice
     * @return
     */
    private Sheet generateAreaDataRow(Sheet sheet,List<String> skuList,Map<String,Map<String,Integer>> maps,Map<String,Integer> cidQuantityMap,
                                      Map<String,Integer> columnMap,Map<String,String> areaMapDictionary,Map<String,Double> priceMap, Map<String,String> cidMap,Map<String, String> queryParams){
        Integer rowIndex = sheet.getLastRowNum() + 1;
        Map<String,Integer> areaQuantitySummary = new HashMap<String, Integer>();//地址，总数量
//        Set<String> mapSet = maps.keySet();
        Integer allRowQuantity = 0;
        Double allRowPrice = 0.00;
        List<String> existCid = new ArrayList<String>();
        //查询类型汇总的数量极其金额start
        List<Map<String,Object>> list = areaSaleDao.findByCategory(queryParams);
        Map<String,Integer> cidQuantitySummaryMap = new HashMap<String, Integer>();
        Map<String,Double>  cidPriceMap = new HashMap<String, Double>();
        Integer cidQuantitySummary = 0;
        Double cidPriceSummary = 0.00;
        Integer cidQuantityExist = 0;
        Double cidPercentExist = 0.00;
        Double cidPriceExist = 0.00;
        for(Map<String,Object> map:list){
            String cid = map.get("cid").toString();
            Integer quantity = Integer.parseInt(map.get("num").toString());
            Double price = Double.parseDouble(map.get("total").toString());
            cidQuantitySummaryMap.put(cid,quantity);
            cidPriceMap.put(cid,price);
            cidQuantitySummary+=quantity;
            cidPriceSummary += price;
        }
        //查询类型汇总的数量极其金额end
        for(String mapKey : skuList){ //根据sku，获取所有地址的相关数量
            int startRow = rowIndex;
            Row dataRow = sheet.createRow(rowIndex++);//创建数据行
            Row percentRow = sheet.createRow(rowIndex++);//创建数据行
            String sku = mapKey;//对应商品sku
            //计算每cid的合并单元格
            String cid = cidMap.get(sku);
            if(!existCid.contains(cid)) {
                int quantity = cidQuantitySummaryMap.get(cid);
                double price = cidPriceMap.get(cid) ;
                /*
                 * 设定合并单元格区域范围
                 *  firstRow  0-based
                 *  lastRow   0-based
                 *  firstCol  0-based
                 *  lastCol   0-based
                 */
                CellRangeAddress cra = new CellRangeAddress(startRow, startRow + cidQuantityMap.get(cid) * 2 -1, 1, 1);
                sheet.addMergedRegion(cra);
                dataRow.createCell(1).setCellValue(cid);//品类

                cra = new CellRangeAddress(startRow, startRow + cidQuantityMap.get(cid) * 2 -1, 7+columnMap.size(), 7+columnMap.size());
                sheet.addMergedRegion(cra);
                dataRow.createCell(7+columnMap.size()).setCellValue(quantity);//数量

                cra = new CellRangeAddress(startRow, startRow + cidQuantityMap.get(cid) * 2 -1, 8+columnMap.size(), 8+columnMap.size());
                sheet.addMergedRegion(cra);
                dataRow.createCell(8+columnMap.size()).setCellValue(CommonHelper.DoubleParseOnePercent(price));//金额

                cra = new CellRangeAddress(startRow, startRow + cidQuantityMap.get(cid) * 2 -1, 9+columnMap.size(), 9+columnMap.size());
                sheet.addMergedRegion(cra);

                double percent = 0.00;
                if(cidQuantityExist + quantity == cidQuantitySummary) {
                    percent = CommonHelper.DoubleParseOnePercent(100 - cidPercentExist);
                } else{
                    percent =  CommonHelper.DoubleParseOnePercent((double)quantity / cidQuantitySummary * 100);
                }
                dataRow.createCell(9+columnMap.size()).setCellValue(CommonHelper.DoubleParseOnePercent(percent) + "%");//数量占比
                cidPercentExist += percent;

                cra = new CellRangeAddress(startRow, startRow + cidQuantityMap.get(cid) * 2 -1, 10+columnMap.size(),10+columnMap.size());
                sheet.addMergedRegion(cra);

                percent = 0.00;
                if(cidQuantityExist + quantity == cidQuantitySummary) {
                    percent = CommonHelper.DoubleParseOnePercent(100 - cidPriceExist);
                } else{
                    percent =  CommonHelper.DoubleParseOnePercent(price/ cidPriceSummary * 100);
                }
                dataRow.createCell(10+columnMap.size()).setCellValue(CommonHelper.DoubleParseOnePercent(percent)+"%");//金额占比
                cidPriceExist += percent;
                cidQuantityExist += quantity;

                existCid.add(cid);
             }
            Integer rowSumQuantity = 0;
            Map<String,Integer> summaryMap = new HashMap<String, Integer>();//父地址，数量

            //1.根据sku找到对应信息
            double sapPrice = priceMap.get(sku);
//            String cid = "";
//            if(!Validate.isNullOrEmpty(cidMap.get(sku))){
//                cid = cidMap.get(sku);
//            }
            dataRow.createCell(1).setCellValue(cid);//品类
            dataRow.createCell(2).setCellValue(sku);
            dataRow.createCell(3).setCellValue(sapPrice);//单价  -->map: sku -->cid_sapprice
            dataRow.createCell(4).setCellValue("数量");//数量
            percentRow.createCell(4).setCellValue("占比");//占比

            //2.循环value对应的map，匹配每列地址对应的数量
            Map<String,Integer> areaMap = maps.get(sku);
            Set<String> areaSet = areaMap.keySet(); // 地址（code） --> 数量

            int skuQuantitySum = 0;
            for(String areaKey : areaSet){
                //根据地址找到对应的code ,title中的列号
                int colNo = columnMap.get(areaKey);//获取当前地址对应的列号
                int quantity = areaMap.get(areaKey);//获取当前对应sku，地址的数量
                dataRow.createCell(colNo).setCellValue(quantity);//将对应的数量，放到对应的列号中
                skuQuantitySum += quantity;
                //汇总地址总数量，用于地址汇总行 area:总数量
                if(Validate.isNullOrEmpty(areaQuantitySummary.get(areaKey))) {
                    areaQuantitySummary.put(areaKey,quantity);
                } else {
                    int areaQuantitySum = areaQuantitySummary.get(areaKey);
                    areaQuantitySummary.put(areaKey,quantity + areaQuantitySum);
                }

                //3.汇总分类行数量，根据地址找到对应的parent_code ,以parent_code为key生成map
                String parentCode = "";
                if(!Validate.isNullOrEmpty(areaMapDictionary.get(areaKey))){
                    parentCode = areaMapDictionary.get(areaKey);
                } else{
                    parentCode = areaKey;
                }
                int quantitySum = 0;//获取累积父节点的商品数量
                if(!Validate.isNullOrEmpty(summaryMap.get(parentCode))) {
                    quantitySum = summaryMap.get(parentCode);
                    quantitySum += quantity;  //汇总总数量
                } else{
                    quantitySum = quantity;
                }
                summaryMap.put(parentCode, quantitySum); //重新赋值
//                skuQuantitySum += quantitySum;
                //注意区分两个集合的作用域
                quantitySum = 0;
                if(!Validate.isNullOrEmpty(areaQuantitySummary.get(parentCode))) {
                    quantitySum = areaQuantitySummary.get(parentCode);
                    quantitySum += quantity;  //汇总总数量
                } else{
                    quantitySum = quantity;
                }
                areaQuantitySummary.put(parentCode,quantitySum);
            }

            //4.给parent对应的cell赋值
            Set<String> summarySet = summaryMap.keySet();
            for(String parentCode:summarySet){
                int colNo = columnMap.get(parentCode);
                int sum = summaryMap.get(parentCode);
                dataRow.createCell(colNo).setCellValue(sum);
                rowSumQuantity += sum;//显示行总数
            }
            int columnNo = 5 + columnMap.size();//当前总列数
            dataRow.createCell(columnNo++).setCellValue(rowSumQuantity);
            dataRow.createCell(columnNo++).setCellValue(CommonHelper.DoubleParseOnePercent(sapPrice * rowSumQuantity));
            allRowQuantity += rowSumQuantity;
            allRowPrice += sapPrice * rowSumQuantity;
            //计算占比
            Map<String,Double> percentMap = new HashMap<String, Double>();//计算占比累积，用于最后一个占比减法
            Map<String,Integer> quantityMap = new HashMap<String, Integer>();//计算数量累积，用于判断当前是否为最后一个
            //5.计算完summary，才可以计算占比
            for(String areaKey : areaSet){
                //根据地址找到对应的code ,title中的列号
                int colNo = columnMap.get(areaKey);//当前地址对应的列号
                String parent_code = areaMapDictionary.get(areaKey);//地址对应的父节点
                int quantity = areaMap.get(areaKey);//获取当前对应sku，地址的数量
                int summary = summaryMap.get(parent_code);//父节点的总和，及子节点百分比计算时的分母

                int existQuantity = 0;//定义已经显示的数量，用于计算是否为某类地址的最后一个子节点
                if(!Validate.isNullOrEmpty(quantityMap.get(parent_code))){
                    existQuantity = quantityMap.get(parent_code);
                }
                Double existPercent = 0.00;//
                if(!Validate.isNullOrEmpty(percentMap.get(parent_code))){
                    existPercent = percentMap.get(parent_code);
                }
                Double percent = 0.00;
                //not last
                if(summary - existQuantity > quantity){
                    percent =  CommonHelper.DoubleParseOnePercent((double)quantity / summary * 100);
                    percentMap.put(parent_code,existPercent + percent);
                } else {
                    //is last
                    percent = CommonHelper.DoubleParseOnePercent(100 - existPercent);
                }
                //根据code获取summaryMap，如果未获取到对应的值，则为parentId，并计算占比值，最后一列值需做减法
                percentRow.createCell(colNo).setCellValue(CommonHelper.DoubleParseOnePercent(percent)+"%");
            }

//            Map<String,Double> percentMapParent = new HashMap<String, Double>();//计算占比累积，用于最后一个占比减法
//            Map<String,Integer> quantityMapParent = new HashMap<String, Integer>();//计算数量累积，用于判断当前是否为最后一个
            int existsQuantity = 0;
            double existsPercent = 0;
            for(String parentCode:summarySet){
                int colNo = columnMap.get(parentCode);
                int quantity = summaryMap.get(parentCode); //skuQuantitySum
//                int existsQuantity = 0;
//                if(!Validate.isNullOrEmpty(quantityMapParent.get(parentCode))) {
//                    existsQuantity = quantityMapParent.get(parentCode);
//                }
//                double existsPercent = 0;
//                if(!Validate.isNullOrEmpty(percentMapParent.get(parentCode))) {
//                    existsPercent = percentMapParent.get(parentCode);
//                }

                double percent =0.00;
                if(skuQuantitySum - existsQuantity > quantity) {
                    percent = CommonHelper.DoubleParseOnePercent((double) quantity / skuQuantitySum * 100);
                    existsPercent = existsPercent + percent;
                    existsQuantity = existsQuantity+quantity;
                } else {
                    percent = CommonHelper.DoubleParseOnePercent(100 - existsPercent);
                }
                percentRow.createCell(colNo).setCellValue(CommonHelper.DoubleParseOnePercent(percent)+"%");
            }

        }

        //计算汇总行数量及占比
        sheet = generateAreaSummaryRow(sheet,areaQuantitySummary,columnMap,areaMapDictionary,allRowQuantity,allRowPrice);

        return sheet;
    }

    /**
     * 生成数据汇总行
     * @param sheet
     * @param areaQuantitySummary 区域数量统计
     * @param columnMap 区域对应的列数
     * @param areaMapDictionary 区域对应的父节点
     * @param allRowQuantity 总数量
     * @param allRowPrice 总金额
     * @return
     */
    private Sheet generateAreaSummaryRow(Sheet sheet,Map<String,Integer> areaQuantitySummary,Map<String,Integer> columnMap,Map<String,String> areaMapDictionary
            ,Integer allRowQuantity,Double allRowPrice){
        int rowIndex = sheet.getLastRowNum() + 1;//获取总行数
        Row title = sheet.createRow(rowIndex);//创建报表头
        title.createCell(4).setCellValue("数量");
        title.createCell(3).setCellValue(" ");
        title.createCell(2).setCellValue(" ");
        title.createCell(1).setCellValue("TTL");
        Set<String> areaSet = areaQuantitySummary.keySet();
        for(String areaKey : areaSet){
            int colNo = columnMap.get(areaKey);
            int quantity = areaQuantitySummary.get(areaKey);
            title.createCell(colNo).setCellValue(quantity);
        }
        int columnNo = 5 + columnMap.size();//当前总列数
        title.createCell(columnNo++).setCellValue(allRowQuantity);
        title.createCell(columnNo++).setCellValue(allRowPrice);
        title.createCell(columnNo++).setCellValue(allRowQuantity);
        title.createCell(columnNo++).setCellValue(allRowPrice);
        title.createCell(columnNo++).setCellValue("100%");
        title.createCell(columnNo++).setCellValue("100%");


        rowIndex++;
        title = sheet.createRow(rowIndex);//创建报表头
        title.createCell(4).setCellValue("占比");
        title.createCell(3).setCellValue(" ");
        title.createCell(2).setCellValue(" ");
        title.createCell(1).setCellValue("TTL");
        Map<String,Double> percentMapParent = new HashMap<String, Double>();//用于父节点的计算
        Map<String,Integer> quantityMapParent = new HashMap<String, Integer>(); //用于父节点的计算
        Map<String,Double> percentMapChild = new HashMap<String, Double>();//用于父节点的计算
        Map<String,Integer> quantityMapChild = new HashMap<String, Integer>(); //用于父节点的计算
        Set<String> parentAreaSet = areaQuantitySummary.keySet();
        for(String parentArea : parentAreaSet) {
            int quantity = areaQuantitySummary.get(parentArea);
            int colNo = columnMap.get(parentArea);//当前地址对应的列号
            //查看当前地址是否为父节点地址
            String parent_code = "";//地址对应的父节点
            if(!Validate.isNullOrEmpty(areaMapDictionary.get(parentArea))){
                parent_code = areaMapDictionary.get(parentArea);//地址对应的父节点
            }
            if(parentArea.equals(parent_code)){
                int existsQuantity = 0;
                if(!Validate.isNullOrEmpty(quantityMapParent.get(parentArea))){
                    existsQuantity = quantityMapParent.get(parentArea);
                }
                double existsPercent = 0.00;
                if(!Validate.isNullOrEmpty(percentMapParent.get(parentArea))){
                    existsPercent = percentMapParent.get(parentArea);
                }
                double percent =0.00;
                if(allRowQuantity - existsQuantity > quantity) {
                    percent = CommonHelper.DoubleParseOnePercent((double) quantity / allRowQuantity * 100);
                    percentMapParent.put(parentArea,existsPercent + percent);
                    quantityMapParent.put(parentArea,existsQuantity+quantity);
                } else {
                    percent = CommonHelper.DoubleParseOnePercent(100 - existsPercent);
                }

                title.createCell(colNo).setCellValue(CommonHelper.DoubleParseOnePercent(percent)+"%");
            } else {
                int childQuantity = areaQuantitySummary.get(parent_code);
                int existsQuantity = 0;
                if(!Validate.isNullOrEmpty(quantityMapChild.get(parentArea))){
                    existsQuantity = quantityMapChild.get(parentArea);
                }
                double existsPercent = 0.00;
                if(!Validate.isNullOrEmpty(percentMapChild.get(parentArea))){
                    existsPercent = percentMapChild.get(parentArea);
                }
                double percent =0.00;
                if(childQuantity - existsQuantity > quantity) {
                    percent = CommonHelper.DoubleParseOnePercent((double) quantity / childQuantity * 100);
                    percentMapChild.put(parentArea,existsPercent + percent);
                } else {
                    percent = CommonHelper.DoubleParseOnePercent(100 - existsPercent);
                }
                title.createCell(colNo).setCellValue(CommonHelper.DoubleParseOnePercent(percent)+"%");
            }
        }

        return sheet;
    }

    /**
     *生成分类汇总行
     * @return
     */
    private Sheet generateAreaCategorySummaryRow(Map<String, String> queryParams){
        List<Map<String,Object>> list = areaSaleDao.findByCategory(queryParams);
        Map<String,Integer> cidQuantityMap = new HashMap<String, Integer>();
        Map<String,Double>  cidPriceMap = new HashMap<String, Double>();
        for(Map<String,Object> map:list){
            String cid = map.get("cid").toString();
            Integer quantity = Integer.parseInt(map.get("num").toString());
            Double price = Double.parseDouble(map.get("total").toString());
            cidQuantityMap.put(cid,quantity);
            cidPriceMap.put(cid,price);
        }

        return null;
    }

    /**
     *厨卫汇总
     */
    private Sheet generateOtherSummary(Sheet sheet,int startColIndex,Map<String,Integer> columnMap,Map<String, String> queryParams){
        int rowIndex = sheet.getLastRowNum() + 2;//获取总行数

        List<Map<String,Object>> list = areaSaleDao.findByOtherCategory(queryParams);
        Map<String,Integer> cidRowQuantityMap = new HashMap<String, Integer>();
        Map<String,Map<String,Integer>> cidCodeQuantityMap = new HashMap<String, Map<String,Integer>>();
        for(Map<String,Object> map:list){
            String cid = map.get("cid").toString();
            String code = map.get("code").toString();
            Integer num = Integer.parseInt(map.get("num").toString());
            Integer existsNum = 0;
            if(!Validate.isNullOrEmpty(cidRowQuantityMap.get(cid))){
                existsNum = cidRowQuantityMap.get(cid);
            }
            cidRowQuantityMap.put(cid,existsNum + num);

            Map<String,Integer> codeMap = cidCodeQuantityMap.get(cid);
            if(Validate.isNullOrEmpty(codeMap)){
                codeMap = new HashMap<String, Integer>();
            }
            codeMap.put(code,num);
            cidCodeQuantityMap.put(cid,codeMap);
        }

        Set<String> cidSet = cidRowQuantityMap.keySet();
        /*
         * 设定合并单元格区域范围
         *  firstRow  0-based
         *  lastRow   0-based
         *  firstCol  0-based
         *  lastCol   0-based
         */
        CellRangeAddress cra = new CellRangeAddress(rowIndex, rowIndex+1, 3, 3);
        sheet.addMergedRegion(cra);

        Row quantityPLCRow = sheet.createRow(rowIndex++);
        Row percentPLCRow = sheet.createRow(rowIndex++);
        quantityPLCRow.createCell(startColIndex).setCellValue("PLC");
        quantityPLCRow.createCell(startColIndex+1).setCellValue("数量");
        percentPLCRow.createCell(startColIndex+1).setCellValue("占比");
        Map<String,Integer> rowMap = cidCodeQuantityMap.get("洗衣机");
        if(!Validate.isNullOrEmpty(rowMap)) {
            Integer cidQuantity = cidRowQuantityMap.get("洗衣机");
            Integer existQuantity = 0;
            Double existPercent = 0.00;
            Double percent = 0.00;
            Set<String> set = rowMap.keySet();
            for(String code:set) {
                Integer colNo = columnMap.get(code);
                Integer num = rowMap.get(code);
                quantityPLCRow.createCell(colNo).setCellValue(num);
                if(existQuantity + num == cidQuantity) {
                    percent = CommonHelper.DoubleParseOnePercent(100 -  existPercent);
                    percentPLCRow.createCell(colNo).setCellValue(CommonHelper.DoubleParseOnePercent(percent) + "%");
                }else{
                    percent = CommonHelper.DoubleParseOnePercent((double)num/cidQuantity*100);
                    percentPLCRow.createCell(colNo).setCellValue(CommonHelper.DoubleParseOnePercent(percent) + "%");
                }
                existQuantity+=num;
                existPercent+=percent;
            }
        }

        cra = new CellRangeAddress(rowIndex, rowIndex+1, 3, 3);
        sheet.addMergedRegion(cra);

        Row quantityPRFRow = sheet.createRow(rowIndex++);
        Row percentPRFRow = sheet.createRow(rowIndex++);
        quantityPRFRow.createCell(startColIndex).setCellValue("PRF");
        quantityPRFRow.createCell(startColIndex+1).setCellValue("数量");
        percentPRFRow.createCell(startColIndex+1).setCellValue("占比");

        rowMap = cidCodeQuantityMap.get("冰箱");
        if(!Validate.isNullOrEmpty(rowMap)) {
            Integer cidQuantity = cidRowQuantityMap.get("冰箱");
            Integer existQuantity = 0;
            Double existPercent = 0.00;
            Double percent = 0.00;
            Set<String> set = rowMap.keySet();
            for(String code:set) {
                Integer colNo = columnMap.get(code);
                Integer num = rowMap.get(code);
                quantityPRFRow.createCell(colNo).setCellValue(num);
                if(existQuantity + num == cidQuantity) {
                    percent = CommonHelper.DoubleParseOnePercent(100 -  existPercent);
                    percentPRFRow.createCell(colNo).setCellValue(CommonHelper.DoubleParseOnePercent(percent) + "%");
                }else{
                    percent = CommonHelper.DoubleParseOnePercent((double)num/cidQuantity*100);
                    percentPRFRow.createCell(colNo).setCellValue(CommonHelper.DoubleParseOnePercent(percent) + "%");
            }
            existQuantity+=num;
            existPercent+=percent;
            }
        }

        cra = new CellRangeAddress(rowIndex, rowIndex+1, 3, 3);
        sheet.addMergedRegion(cra);

        Row quantityOtherRow = sheet.createRow(rowIndex++);
        Row percentOtherRow = sheet.createRow(rowIndex++);
        quantityOtherRow.createCell(startColIndex).setCellValue("Other");
        quantityOtherRow.createCell(startColIndex+1).setCellValue("数量");
        percentOtherRow.createCell(startColIndex+1).setCellValue("占比");

        rowMap = cidCodeQuantityMap.get("厨卫");
        if(!Validate.isNullOrEmpty(rowMap)) {
            Integer cidQuantity = cidRowQuantityMap.get("厨卫");
            Integer existQuantity = 0;
            Double existPercent = 0.00;
            Double percent = 0.00;
            Set<String> set = rowMap.keySet();
            for(String code:set) {
                Integer colNo = columnMap.get(code);
                Integer num = rowMap.get(code);
                quantityOtherRow.createCell(colNo).setCellValue(num);
                if(existQuantity + num == cidQuantity) {
                    percent = CommonHelper.DoubleParseOnePercent(100 - existPercent);
                    percentOtherRow.createCell(colNo).setCellValue(CommonHelper.DoubleParseOnePercent(percent) + "%");
                }else{
                    percent = CommonHelper.DoubleParseOnePercent((double)num/cidQuantity*100);
                    percentOtherRow.createCell(colNo).setCellValue(CommonHelper.DoubleParseOnePercent(percent) + "%");
                }
                existQuantity+=num;
                existPercent+=percent;
            }
        }
        //设置单元格样式
        return sheet;
    }

    /**
     *厨卫汇总
     */
    private Sheet generateSummaryBottom(Sheet sheet,int startColIndex,Map<String,Integer> columnMap,Map<String, String> queryParams,Map<String,Map<String,Integer>> cidAreaMap){
        int rowIndex = sheet.getLastRowNum() + 2;//获取总行数
        //底部汇总小表查询
        List<Map<String,Object>> list = areaSaleDao.findBySummaryBottom(queryParams);
        Map<String,Integer> cidRowQuantityMap = new HashMap<String, Integer>();
        Map<String,Map<String,Integer>> cidCodeQuantityMap = new HashMap<String, Map<String,Integer>>();
        for(Map<String,Object> map:list){
            String cid = map.get("cid").toString();//品牌
            String code = map.get("code").toString();//地址父节点的名称
            Integer num = Integer.parseInt(map.get("num").toString());//总数量
            Integer existsNum = 0;
            if(!Validate.isNullOrEmpty(cidRowQuantityMap.get(cid))){
                existsNum = cidRowQuantityMap.get(cid);
            }
            cidRowQuantityMap.put(cid,existsNum + num);

            Map<String,Integer> codeMap = cidCodeQuantityMap.get(cid);
            if(Validate.isNullOrEmpty(codeMap)){
                codeMap = new HashMap<String, Integer>();
            }
            codeMap.put(code,num);
            cidCodeQuantityMap.put(cid,codeMap);
        }

        Set<String> cidSet = cidRowQuantityMap.keySet();
        for (String key : cidCodeQuantityMap.keySet()) {
            /*
         * 设定合并单元格区域范围
         *  firstRow  0-based
         *  lastRow   0-based
         *  firstCol  0-based
         *  lastCol   0-based
         */
            CellRangeAddress cra = new CellRangeAddress(rowIndex, rowIndex+1, 3, 3);
            sheet.addMergedRegion(cra);

            Row quantityPLCRow = sheet.createRow(rowIndex++);
            Row percentPLCRow = sheet.createRow(rowIndex++);
            quantityPLCRow.createCell(startColIndex).setCellValue(key);
            quantityPLCRow.createCell(startColIndex+1).setCellValue("数量");
            percentPLCRow.createCell(startColIndex+1).setCellValue("占比");
            Map<String,Integer> rowMap = cidCodeQuantityMap.get(key);
            if(!Validate.isNullOrEmpty(rowMap)) {
                Integer cidQuantity = cidRowQuantityMap.get(key);
                Integer existQuantity = 0;
                Double existPercent = 0.00;
                Double percent = 0.00;
                Set<String> set = rowMap.keySet();
                for(String code:set) {
                    Integer colNo = columnMap.get(code);
                    Integer num = rowMap.get(code);
                    quantityPLCRow.createCell(colNo).setCellValue(num);
                    if(existQuantity + num == cidQuantity) {
                        percent = CommonHelper.DoubleParseOnePercent(100 -  existPercent);
                        percentPLCRow.createCell(colNo).setCellValue(CommonHelper.DoubleParseOnePercent(percent) + "%");
                    }else{
                        percent = CommonHelper.DoubleParseOnePercent((double)num/cidQuantity*100);
                        percentPLCRow.createCell(colNo).setCellValue(CommonHelper.DoubleParseOnePercent(percent) + "%");
                    }
                    existQuantity+=num;
                    existPercent+=percent;
                }
            }

            //
            Map<String,Integer> childMap =cidAreaMap.get(key);
            if(null!=childMap&&childMap.size()>0){
                Set<String> keySets=childMap.keySet();
                for (String areaCode:keySets){
                    Integer colNo = columnMap.get(areaCode);
                    quantityPLCRow.createCell(colNo).setCellValue(childMap.get(areaCode));
                }
            }

        }
//        /*
//         * 设定合并单元格区域范围
//         *  firstRow  0-based
//         *  lastRow   0-based
//         *  firstCol  0-based
//         *  lastCol   0-based
//         */
//        CellRangeAddress cra = new CellRangeAddress(rowIndex, rowIndex+1, 3, 3);
//        sheet.addMergedRegion(cra);
//
//        Row quantityPLCRow = sheet.createRow(rowIndex++);
//        Row percentPLCRow = sheet.createRow(rowIndex++);
//        quantityPLCRow.createCell(startColIndex).setCellValue("PLC");
//        quantityPLCRow.createCell(startColIndex+1).setCellValue("数量");
//        percentPLCRow.createCell(startColIndex+1).setCellValue("占比");
//        Map<String,Integer> rowMap = cidCodeQuantityMap.get("洗衣机");
//        if(!Validate.isNullOrEmpty(rowMap)) {
//            Integer cidQuantity = cidRowQuantityMap.get("洗衣机");
//            Integer existQuantity = 0;
//            Double existPercent = 0.00;
//            Double percent = 0.00;
//            Set<String> set = rowMap.keySet();
//            for(String code:set) {
//                Integer colNo = columnMap.get(code);
//                Integer num = rowMap.get(code);
//                quantityPLCRow.createCell(colNo).setCellValue(num);
//                if(existQuantity + num == cidQuantity) {
//                    percent = CommonHelper.DoubleParseOnePercent(100 -  existPercent);
//                    percentPLCRow.createCell(colNo).setCellValue(CommonHelper.DoubleParseOnePercent(percent) + "%");
//                }else{
//                    percent = CommonHelper.DoubleParseOnePercent((double)num/cidQuantity*100);
//                    percentPLCRow.createCell(colNo).setCellValue(CommonHelper.DoubleParseOnePercent(percent) + "%");
//                }
//                existQuantity+=num;
//                existPercent+=percent;
//            }
//        }
//
//        cra = new CellRangeAddress(rowIndex, rowIndex+1, 3, 3);
//        sheet.addMergedRegion(cra);
//
//        Row quantityPRFRow = sheet.createRow(rowIndex++);
//        Row percentPRFRow = sheet.createRow(rowIndex++);
//        quantityPRFRow.createCell(startColIndex).setCellValue("PRF");
//        quantityPRFRow.createCell(startColIndex+1).setCellValue("数量");
//        percentPRFRow.createCell(startColIndex+1).setCellValue("占比");
//
//        rowMap = cidCodeQuantityMap.get("冰箱");
//        if(!Validate.isNullOrEmpty(rowMap)) {
//            Integer cidQuantity = cidRowQuantityMap.get("冰箱");
//            Integer existQuantity = 0;
//            Double existPercent = 0.00;
//            Double percent = 0.00;
//            Set<String> set = rowMap.keySet();
//            for(String code:set) {
//                Integer colNo = columnMap.get(code);
//                Integer num = rowMap.get(code);
//                quantityPRFRow.createCell(colNo).setCellValue(num);
//                if(existQuantity + num == cidQuantity) {
//                    percent = CommonHelper.DoubleParseOnePercent(100 -  existPercent);
//                    percentPRFRow.createCell(colNo).setCellValue(CommonHelper.DoubleParseOnePercent(percent) + "%");
//                }else{
//                    percent = CommonHelper.DoubleParseOnePercent((double)num/cidQuantity*100);
//                    percentPRFRow.createCell(colNo).setCellValue(CommonHelper.DoubleParseOnePercent(percent) + "%");
//                }
//                existQuantity+=num;
//                existPercent+=percent;
//            }
//        }
//
//        cra = new CellRangeAddress(rowIndex, rowIndex+1, 3, 3);
//        sheet.addMergedRegion(cra);
//
//        Row quantityOtherRow = sheet.createRow(rowIndex++);
//        Row percentOtherRow = sheet.createRow(rowIndex++);
//        quantityOtherRow.createCell(startColIndex).setCellValue("Other");
//        quantityOtherRow.createCell(startColIndex+1).setCellValue("数量");
//        percentOtherRow.createCell(startColIndex+1).setCellValue("占比");
//
//        rowMap = cidCodeQuantityMap.get("厨卫");
//        if(!Validate.isNullOrEmpty(rowMap)) {
//            Integer cidQuantity = cidRowQuantityMap.get("厨卫");
//            Integer existQuantity = 0;
//            Double existPercent = 0.00;
//            Double percent = 0.00;
//            Set<String> set = rowMap.keySet();
//            for(String code:set) {
//                Integer colNo = columnMap.get(code);
//                Integer num = rowMap.get(code);
//                quantityOtherRow.createCell(colNo).setCellValue(num);
//                if(existQuantity + num == cidQuantity) {
//                    percent = CommonHelper.DoubleParseOnePercent(100 - existPercent);
//                    percentOtherRow.createCell(colNo).setCellValue(CommonHelper.DoubleParseOnePercent(percent) + "%");
//                }else{
//                    percent = CommonHelper.DoubleParseOnePercent((double)num/cidQuantity*100);
//                    percentOtherRow.createCell(colNo).setCellValue(CommonHelper.DoubleParseOnePercent(percent) + "%");
//                }
//                existQuantity+=num;
//                existPercent+=percent;
//            }
//        }
        //设置单元格样式
        return sheet;
    }

    /**
     * 生成空白行
     * @return
     */
    private Sheet generateBlankRow(){
        return null;
    }

    private Cell setBorder(Cell cell){
        CellStyle cellStyle = cell.getCellStyle();
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        return cell;
    }


    public static void main(String[] args){
        FileOutputStream fos= null;
        try {
            fos = new FileOutputStream("D:\\13.xls");
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        Workbook wb=new HSSFWorkbook();

        Sheet sheet=wb.createSheet();
        /*
         * 设定合并单元格区域范围
         *  firstRow  0-based
         *  lastRow   0-based
         *  firstCol  0-based
         *  lastCol   0-based
         */
        CellRangeAddress cra=new CellRangeAddress(0, 3, 3, 9);

        //在sheet里增加合并单元格
        sheet.addMergedRegion(cra);

        Row row = sheet.createRow(0);

        Cell cell_1 = row.createCell(3);

        cell_1.setCellValue("When you're right , no one remembers, when you're wrong ,no one forgets .");

        //cell 位置3-9被合并成一个单元格，不管你怎样创建第4个cell还是第5个cell…然后在写数据。都是无法写入的。
        Cell cell_2 = row.createCell(10);

        cell_2.setCellValue("what's up ! ");

        try {
            wb.write(fos);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        try {
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

}
