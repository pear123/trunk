package com.arvato.cc.service1.impl;

import com.arvato.cc.constant.Constants;
import com.arvato.cc.dao1.*;
import com.arvato.cc.exceptions.DataException;
import com.arvato.cc.model.*;
import com.arvato.cc.service1.AlipayService;
import com.arvato.cc.service1.CurrentOperationService;
import com.arvato.cc.service1.OperationLogService;
import com.arvato.cc.util.CommonHelper;
import com.arvato.cc.util.FileUtil;
import com.arvato.cc.util.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.PrivateKey;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: caol005
 * Date: 15-7-16
 * Time: 下午1:58
 * To change this template use File | Settings | File Templates.
 */
@Service
public class AlipayServiceImpl implements AlipayService {
    private static final Logger log = LoggerFactory.getLogger(AlipayServiceImpl.class);

    @Autowired
    private AlipayDao alipayDao;

    @Autowired
    private OperationLogDao operationLogDao;

    @Autowired
    private AlipayTransDao alipayTransDao;

    @Autowired
    private OperationLogService operationLogService;

    @Autowired
    private JstTradeDao jstTradeDao;

    @Autowired
    private TaobaoStoreDao taobaoStoreDao;

    @Value("${upload.process}")
    private String processPath;  //正在操作的文件
    @Value("${upload.arch}")
    private String archPath;   //成功读取本地文件备份
    @Value("${upload.error}")
    private String errorPath;   //读取失败本地文件备份

    private static String dateFormat = "yyyyMMddHHmmss"; //file suffix

    public String uploadFile(CommonsMultipartFile uploadFile,String fileType,String folder) {
        log.debug("method upload alipay File start");
        String result = Constants.upload_success_msg;
        String originalFileName = uploadFile.getOriginalFilename();
        String prefix = originalFileName.substring(0, originalFileName.lastIndexOf("."));
        String fileName = prefix + CommonHelper.DateFormat(CommonHelper.getThisTimestamp(), "yyyMMddhhmmss") + "." + fileType;

        File processFilePath = new File(processPath+folder);
        if(!processFilePath.exists()){
            processFilePath.mkdirs();
        }
        File updFile = new File(processFilePath, fileName);
        try {
            FileCopyUtils.copy(uploadFile.getBytes(), updFile);
            //read excel by poi
            FileUtil fileUtil = new FileUtil();
            List<List<String>>  rowList = fileUtil.getExcelLineStringList(processPath + folder + fileName, 12, 0, 0, fileType);

            //验证导入数据的格式
            if(!FileUtil.validateTemplate(getAlipayTitle(),rowList.get(4))){
                result = Constants.ExceptionMsg.FORMAT_ERROR.getValue().toString();
                return result;
            }

            //确认alipay数据所属商店
            String alipayNo = "";
            String storeId = "";
            TaobaoStore taobaoStore = null;
            String title = rowList.get(1).get(0);
            if(!Validate.isNullOrEmpty(title)){
                alipayNo = title.substring(5,title.length() -1);
                if(!Validate.isNullOrEmpty(alipayNo)){
                    taobaoStore = taobaoStoreDao.getStoreCodeByAlipayNo(alipayNo);
                    if(!Validate.isNullOrEmpty(taobaoStore)){
                        storeId = taobaoStore.getCode();
                    }
                }
            }

            //数据读取从第二行开始
            String financialSerialNum = "";
            String serviceSerialNum = "";
            Set<String> keys = getUniqueSet();
            List<String> valueList = null;

            //处理alipay汇总逻辑
            Map<String,Double> alipayTransList = getAlipayTransSum();
            Set<String> transKeys = getAlipayTransUniqueSet();
            Map<String,Double> newAlipayTransList = new HashMap<String, Double>();
            List<AlipayTrans> alipayTranses = new ArrayList<AlipayTrans>();
            Map<String,Alipay> financeAlipayMap = generateFinanceAlipayMap(); //被标记为财务反查的数据
            Set<String> financeAlipayKey = financeAlipayMap.keySet();
            for(int i = 5;i<=rowList.size() - 5;i++){               //List<String> valueList : rowList
                valueList = rowList.get(i);
                try{
                    financialSerialNum = valueList.get(0);
                    serviceSerialNum = valueList.get(1);
                    if(Validate.isNullOrEmpty(financialSerialNum)) {
                        continue;
                    }

                    //被财务反查标记，对标记位做update
                    if(financeAlipayKey.contains(financialSerialNum)){
                        //update alipay and continue;
                        Alipay financeAlipay = financeAlipayMap.get(financialSerialNum);
                        int status = 1;//1:normal order ;2:finance order
                        JstTrade jstTrade = jstTradeDao.getByServiceSerialNum(serviceSerialNum);
                        if(Validate.isNullOrEmpty(jstTrade) && financeAlipay.getInFee() > 0){
                            status = 2;
                        }
                        financeAlipay.setTradeType(status);
                        alipayDao.saveAlipay(financeAlipay);
                        continue;
                    }
                    if(keys.contains(financialSerialNum)) {//之前已经上传过alipay数据
                        continue;
                    }
                    Alipay alipay = generateAlipay(valueList,storeId);
                    //处理alipay汇总逻辑
                    if(!Validate.isNullOrEmpty(alipay)){
                        if(!Validate.isNullOrEmpty(alipay.getInFee())){
                            if(!transKeys.contains(financialSerialNum)) {
                                //获取当前表中对应的alipay记录和
                                Double sum = alipayTransList.get(serviceSerialNum);
                                if(Validate.isNullOrEmpty(sum)) {
                                    Double newSum = newAlipayTransList.get(serviceSerialNum);
                                    if(Validate.isNullOrEmpty(newSum)) {
                                        newAlipayTransList.put(serviceSerialNum,alipay.getInFee());
                                    } else {
                                        newAlipayTransList.put(serviceSerialNum,alipay.getInFee()+newSum);
                                    }
                                }else{
                                    Double newSum = newAlipayTransList.get(serviceSerialNum);
                                    if(Validate.isNullOrEmpty(newSum)) {
                                        newAlipayTransList.put(serviceSerialNum,sum+alipay.getInFee());
                                    } else {
                                        newAlipayTransList.put(serviceSerialNum,sum+alipay.getInFee()+newSum);
                                    }
                                    transKeys.add(financialSerialNum);
                                }
                                alipayTranses.add(new AlipayTrans(financialSerialNum,serviceSerialNum,alipay.getCreateTime(),newAlipayTransList.get(serviceSerialNum)));
                            }
                        }
                        alipayDao.saveAlipay(alipay);
                        keys.add(financialSerialNum);
                    }
                } catch (DataException ex){
                    result = String.format(Constants.upload_error_msg,i,ex.getMessage());
                    break;
                }
            }


            //同一个流水只保留一个记录
            Set<String> serviceSerialNumSet = getAlipayTransServiceSet();
            //保存trans 的记录
            for(AlipayTrans trans : alipayTranses) {
                if(serviceSerialNumSet.contains(trans.getServiceSerialNum())) {
                    alipayTransDao.deleteByServiceSerialNum(trans.getServiceSerialNum());
                }
                trans.setInFee(newAlipayTransList.get(trans.getServiceSerialNum()));
                alipayTransDao.saveAlipayTrans(trans);
                serviceSerialNumSet.add(trans.getServiceSerialNum());
            }

            File archFilePath = new File(archPath+folder);
            if(!archFilePath.exists()){
                archFilePath.mkdirs();
            }
            File updSuccessFile = new File(archFilePath, fileName);
            FileCopyUtils.copy(uploadFile.getBytes(), updSuccessFile);
            updFile.delete();

            OperationHistory operationHistory = operationLogService.generateOperationHistory(fileName,Constants.OperationType.UPLOAD_ALIPAY.toString(),Constants.ModelType.UPLOAD.toString()) ;
            operationLogDao.saveOperationLog(operationHistory);

        } catch (Exception e) {
            //move file to success file folder
            try {
                File errorFilePath = new File(errorPath+folder);
                if(!errorFilePath.exists()){
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
        log.debug("method upload alipay File end");
        return result;
    }

    private Map<String,Alipay> generateFinanceAlipayMap(){
        List<Alipay> financeAlipayList = alipayDao.getFinanceAlipay(); //被标记为财务反查的alipay记录
        Map<String,Alipay> financeAlipayMap = new HashMap<String,Alipay>();
        for(Alipay alipay : financeAlipayList) {
            financeAlipayMap.put(alipay.getFinancialSerialNum(),alipay);
        }
        return financeAlipayMap;
    }


    public String getAlipayCreateTime(String tid){
        List<Alipay> alipayList = alipayDao.findByTid(tid);
        String poDate = "";
        if(Validate.isNullOrEmpty(alipayList) && alipayList.size() > 0){
            poDate = CommonHelper.DateFormat(alipayList.get(0).getCreateTime(), "yyyyMMdd");
        }
        return  poDate;
    }

    @Override
    public Alipay findByAlipayNo(String alipayNo) {
        List<Alipay> list = alipayDao.findByAlipayNo(alipayNo);
        if(Validate.isNullOrEmpty(list)){
            return null;
        }else{
            double sum = 0.0;
            Timestamp create = null;
            for(Alipay alipay:list){
                sum += alipay.getInFee();
                create = alipay.getCreateTime();
            }
            Alipay alipay = new Alipay();
            alipay.setCreateTime(create);
            alipay.setInFee(sum);
            return alipay;
        }

    }


    /**
     * generate alipay data by excel row
     * @param list
     * @return
     * @throws DataException
     */
    private Alipay generateAlipay(List<String> list,String storeId) throws DataException {
        if(Validate.isNullOrEmpty(list) || list.size() == 0) {
            throw new DataException(Constants.ExceptionMsg.DATA_IS_NULL.getValue().toString());
        }

        //grep start with #
        String financialSerialNum = list.get(0);
        if(Validate.isNullOrEmpty(financialSerialNum)){
            return null;
        }

        if("#".equals(financialSerialNum.substring(0, 1))){
            return null;
        }

        //get order no
        String orderNum = list.get(2);

        //get income fee
        String inFee = list.get(6);
        Double income = null;
        if(!Pattern.matches("^[+-]?\\d+(\\.\\d+)?$", inFee)) {
            throw new DataException(Constants.ExceptionMsg.PRICE_FORMAT_ERROR.getValue().toString());
        } else {
            income = new Double(inFee);
        }
        String serviceSerialNum = list.get(1);
        //get order by orderNo ,split data by condition
        int status = 1;//1:normal order ;2:finance order
        JstTrade jstTrade = jstTradeDao.getByServiceSerialNum(serviceSerialNum);
        if(Validate.isNullOrEmpty(jstTrade) && income > 0){
            status = 2;
        }



        Alipay alipay = new Alipay();
        alipay.setFinancialSerialNum(financialSerialNum);
        alipay.setServiceSerialNum(serviceSerialNum);
        alipay.setOrderNum(orderNum);
        alipay.setGoodsName(list.get(3));
        if(!Validate.isNullOrEmpty(list.get(4))){
            try {
                alipay.setCreateTime(CommonHelper.parseFromString(list.get(4), "yyyy/MM/dd HH:mm:ss"));//2015/1/18  23:53:04
            } catch (ParseException e) {
                throw new DataException(Constants.ExceptionMsg.DATE_FORMAT_ERROR.getValue().toString());
            }
        }
        alipay.setAccount(list.get(5));
        alipay.setInFee(income);
        if(Pattern.matches("^[+-]?\\d+(\\.\\d+)?$", list.get(7))){
            alipay.setOutFee(new Double(list.get(7)));
        }

        if(Pattern.matches("^[+-]?\\d+(\\.\\d+)?$", list.get(8))){
            alipay.setBalance(new Double(list.get(8)));
        }
        alipay.setMode(list.get(9));
        alipay.setType(list.get(10));
        alipay.setMemo(list.get(11));
        alipay.setStoreId(storeId);
        alipay.setTradeType(status);
        return alipay;
    }


    private Map<String,Double> getAlipayTransSum(){
        Map<String,Double> map = new HashMap<String, Double>();
        List<Map<String,Object>> transList = alipayTransDao.getAlipayTransSum();
        String service_Serial_Num = "";
        Double inFee = 0.0;
        for(Map<String,Object> valueMap:transList) {
            if(!Validate.isNullOrEmpty(valueMap.get("service_Serial_Num")))
                service_Serial_Num = valueMap.get("service_Serial_Num").toString();

            if(!Validate.isNullOrEmpty(valueMap.get("IN_FEE")))
                inFee = CommonHelper.DoubleParse(valueMap.get("IN_FEE"));
            map.put(service_Serial_Num,inFee);
        }
        return map;
    }

    private Set<String> getAlipayTransUniqueSet(){
        List<Map<String,Object>> uniqueKeysList = alipayTransDao.getAlipayTransUniqueSet();
        Set<String> keys = new HashSet<String>();
        String financialSerialNum = "";
        for(Map<String,Object> valueMap:uniqueKeysList) {
            if(!Validate.isNullOrEmpty(valueMap.get("financial_Serial_Num")))
                financialSerialNum = valueMap.get("financial_Serial_Num").toString();
            keys.add(financialSerialNum);
        }
        return keys;
    }

    private Set<String> getAlipayTransServiceSet(){
        List<Map<String,Object>> uniqueKeysList = alipayTransDao.getAlipayTransServiceUniqueSet();
        Set<String> keys = new HashSet<String>();
        String financialSerialNum = "";
        for(Map<String,Object> valueMap:uniqueKeysList) {
            if(!Validate.isNullOrEmpty(valueMap.get("service_Serial_Num")))
                financialSerialNum = valueMap.get("service_Serial_Num").toString();
            keys.add(financialSerialNum);
        }
        return keys;
    }


    private Set<String> getUniqueSet(){
        List<Map<String,Object>> uniqueKeysList = alipayDao.getAlipayUniqueKeys();
        Set<String> keys = new HashSet<String>();
        String financialSerialNum = "";
        for(Map<String,Object> valueMap:uniqueKeysList) {
            if(!Validate.isNullOrEmpty(valueMap.get("financial_Serial_Num"))) {
                financialSerialNum = valueMap.get("financial_Serial_Num").toString();
                keys.add(financialSerialNum);
            }
        }
        return keys;
    }

    private Set<String> getUniqueServiceSerialNumSet(){
        List<Map<String,Object>> uniqueKeysList = alipayTransDao.getAlipayTransServiceUniqueSet();
        Set<String> keys = new HashSet<String>();
        String serviceSerialNum = "";
        String tid = "";
        for(Map<String,Object> valueMap:uniqueKeysList) {
            serviceSerialNum = "";
            tid = "";
            if(!Validate.isNullOrEmpty(valueMap.get("SERVICE_SERIAL_NUM"))) {
                serviceSerialNum = valueMap.get("SERVICE_SERIAL_NUM").toString();
            }
            if(!Validate.isNullOrEmpty(valueMap.get("tid"))) {
                tid = valueMap.get("tid").toString();
            }
            if(!Validate.isNullOrEmpty(serviceSerialNum) && !Validate.isNullOrEmpty(tid)){
                keys.add(serviceSerialNum);
            }
        }
        return keys;
    }

    private static List<String> getAlipayTitle(){
        List<String> title = new ArrayList<String>();
        title.add("账务流水号");
        title.add("业务流水号");
        title.add("商户订单号");
        title.add("商品名称");
        title.add("发生时间");
        title.add("对方账号");
        title.add("收入金额（+元）");
        title.add("支出金额（-元）");
        title.add("账户余额（元）");
        title.add("交易渠道");
        title.add("业务类型");
        title.add("备注");
        return title;
    }




}
