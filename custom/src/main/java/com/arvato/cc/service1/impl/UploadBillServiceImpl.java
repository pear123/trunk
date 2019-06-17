package com.arvato.cc.service1.impl;

import com.arvato.cc.constant.Constants;
import com.arvato.cc.dao1.AlipayDao;
import com.arvato.cc.dao1.BillDataDao;
import com.arvato.cc.dao1.OperationLogDao;
import com.arvato.cc.exceptions.DataException;
import com.arvato.cc.model.OperationHistory;
import com.arvato.cc.model.UpdDelivery;
import com.arvato.cc.service1.UploadBillService;
import com.arvato.cc.util.CommonHelper;
import com.arvato.cc.util.FileUtil;
import com.arvato.cc.util.UserSecurityUtil;
import com.arvato.cc.util.Validate;
import org.apache.commons.collections.CollectionUtils;
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
import java.text.SimpleDateFormat;
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
public class UploadBillServiceImpl implements UploadBillService {
    private static final Logger log = LoggerFactory.getLogger(UploadBillServiceImpl.class);

    @Autowired
    private AlipayDao uploadDao;
    @Value("${upload.process}")
    private String processPath;  //正在操作的文件
    @Value("${upload.arch}")
    private String archPath;   //成功读取本地文件备份
    @Value("${upload.error}")
    private String errorPath;   //读取失败本地文件备份

    @Autowired
    private BillDataDao billDataDao;

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
            Map<Integer, Map<Integer, List<String>>> map = FileUtil.readExcel(new FileInputStream(updFile), 63, 0, 0, fileType);
            int rowNum = 2;
            List<Map<String,Object>> uniqueNumList = billDataDao.getBillUniqueNum();
            Set<String> deliveryNoSet = new HashSet<String>();
            if(!CollectionUtils.isEmpty(uniqueNumList)){
                for(Map<String,Object> valueMap:uniqueNumList) {
                    String deliveryNum = valueMap.get("deliveryno").toString();
                    deliveryNoSet.add(deliveryNum);
                }
            }
            Set<Map.Entry<Integer, List<String>>> resultSet = map.get(0).entrySet();
            if (!CollectionUtils.isEmpty(resultSet)) {
                //对表头进行校验
                Boolean IsNeed = true;
                List<UpdDelivery> updDeliveryList=new ArrayList<UpdDelivery>();
                for (Map.Entry<Integer, List<String>> ent : resultSet) {
                    List<String> Contents = ent.getValue();
                    try {
                        if (!CollectionUtils.isEmpty(Contents)) {
                            if (IsNeed) {
                                if (validateColumn(Contents)) {
                                    IsNeed = false;
                                    continue;
                                } else {
                                    return Constants.ExceptionMsg.FORMAT_ERROR.getValue().toString();
                                }
                            }
                            if(!CollectionUtils.isEmpty(Contents)){
                                if(!CollectionUtils.isEmpty(deliveryNoSet) && deliveryNoSet.contains(Contents.get(0))){
                                    rowNum++;
                                    continue;
                                }
                                UpdDelivery updDelivery = getBillData(Contents);
                                if (updDelivery != null) {
                                    //this.billDataDao.saveOrUpdate(updDelivery);
                                    updDeliveryList.add(updDelivery);
                                    deliveryNoSet.add(updDelivery.getDeliveryno());
                                }
                                rowNum++;
                            }else{
                                return  Constants.ExceptionMsg.DATA_IS_NULL.getValue().toString();
                            }
                        }
                    } catch (DataException ex) {
                        return String.format(Constants.upload_error_msg, rowNum, ex.getMessage());
                    }
                }
                if(resultSet.size() == 1){
                    return  Constants.ExceptionMsg.DATA_IS_NULL.getValue().toString();
                }
                if(updDeliveryList!=null&&updDeliveryList.size()>0){
                    for(UpdDelivery updDelivery : updDeliveryList){
                        this.billDataDao.saveOrUpdate(updDelivery);
                    }
                }
                //将上传的操作记录到历史操作表中
                OperationHistory OperationHistory = new OperationHistory();
                OperationHistory.setFileName(fileName);
                OperationHistory.setOperateTime(CommonHelper.getThisTimestamp());
                OperationHistory.setOperateOp(UserSecurityUtil.getCurrentUsername());
                OperationHistory.setFileType(Constants.OperationType.UPLOAD_BILL.toString());
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
        log.debug("method upload bill File end");
        return result;
    }

    public Boolean validateColumn(List<String> contents) {
        Boolean rs = false;
        String[] columns = {"发货单号", "淘宝交易号", "提交时间", "买家昵称", "卖家昵称",
                "发货商昵称", "物流公司", "运单号", "订单状态", "收件人姓名", "省", "市", "区", "详细地址", "邮编", "手机",
                "电话", "发票类型", "发票抬头", "发票金额（元）", "发票内容", "仓库名称", "实际出库时间", "商品1名称", "商品1价格",
                "商品1商品编码", "商品1数量", "商品2名称", "商品2价格", "商品2商品编码", "商品2数量", "商品3名称", "商品3价格",
                "商品3商品编码", "商品3数量", "商品4名称", "商品4价格", "商品4商品编码", "商品4数量", "商品5名称", "商品5价格",
                "商品5商品编码", "商品5数量", "商品6名称", "商品6价格", "商品6商品编码", "商品6数量", "商品7名称", "商品7价格",
                "商品7商品编码", "商品7数量", "商品8名称", "商品8价格", "商品8商品编码", "商品8数量", "商品9名称", "商品9价格",
                "商品9商品编码", "商品9数量", "商品10名称", "商品10价格", "商品10商品编码", "商品10数量"};
        if (contents.size() == columns.length) {
            for (int i = 0; i < columns.length; i++) {
                if (!columns[i].trim().equals(contents.get(i))) {
                    break;
                }
                if (i == columns.length - 1) {
                    rs = true;
                }
            }
        }
        return rs;
    }


    public UpdDelivery getBillData(List<String> Contents) throws DataException {

        UpdDelivery updDelivery = new UpdDelivery();

        if (Validate.isNullOrEmpty(Contents) || Contents.size() == 0) {
            throw new DataException(Constants.ExceptionMsg.DATA_IS_NULL.getValue().toString());
        }
        //模板中的所有字段,共63个字段
        updDelivery.setDeliveryno(Contents.get(0).trim());
        updDelivery.setTid(Contents.get(1).trim());
        try {
            if (!"".equals(Contents.get(2).trim())) {
                updDelivery.setCreateTime(new SimpleDateFormat(dateFormat).parse(Contents.get(2).trim()));
            }
            if (!"".equals(Contents.get(22).trim())) {
                updDelivery.setStoreOutTime(new SimpleDateFormat(dateFormat).parse(Contents.get(22).trim()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        updDelivery.setBuyerNick(Contents.get(3).trim());
        updDelivery.setSellerNick(Contents.get(4).trim());
        updDelivery.setShipperNick(Contents.get(5).trim());
        updDelivery.setLogisticCompany(Contents.get(6).trim());
        updDelivery.setOrderid(Contents.get(7).trim());
        updDelivery.setOrderStauts(Contents.get(8).trim());
        updDelivery.setReceiverName(Contents.get(9).trim());
        updDelivery.setState(Contents.get(10).trim());
        updDelivery.setCity(Contents.get(11).trim());
        updDelivery.setDistrict(Contents.get(12).trim());
        updDelivery.setAddress(Contents.get(13).trim());
        updDelivery.setZip(Contents.get(14).trim());
        updDelivery.setMobile(Contents.get(15).trim());
        updDelivery.setPhone(Contents.get(16).trim());
        updDelivery.setInvoiceKind(Contents.get(17).trim());
        updDelivery.setInvoiceName(Contents.get(18).trim());
        if (!"".equals(Contents.get(19).trim())) {
            updDelivery.setInvoiceFee(Double.parseDouble(Contents.get(19).trim()));
        }
        updDelivery.setInvoiceContent(Contents.get(20).trim());
        updDelivery.setStore(Contents.get(21).trim());

        updDelivery.setTitle1(Contents.get(23).trim());
        if (!"".equals(Contents.get(24).trim())) {
            if(!Pattern.matches("^[+-]?\\d+(\\.\\d+)?$", Contents.get(24).trim())) {
                throw new DataException(Constants.ExceptionMsg.PRICE_FORMAT_ERROR.getValue().toString());
            }
            updDelivery.setPrice1(Double.parseDouble(Contents.get(24).trim()));
        }
        updDelivery.setCode1(Contents.get(25).trim());
        if (!"".equals(Contents.get(26).trim())) {
            updDelivery.setNum1(Integer.parseInt(Contents.get(26).trim()));
        }
        updDelivery.setTitle2(Contents.get(27).trim());
        if (!"".equals(Contents.get(28).trim())) {
            updDelivery.setPrice2(Double.parseDouble(Contents.get(28).trim()));
        }
        updDelivery.setCode2(Contents.get(29).trim());
        if (!"".equals(Contents.get(30).trim())) {
            updDelivery.setNum2(Integer.parseInt(Contents.get(30).trim()));
        }
        updDelivery.setTitle3(Contents.get(31).trim());
        if (!"".equals(Contents.get(32).trim())) {
            updDelivery.setPrice3(Double.parseDouble(Contents.get(32).trim()));
        }
        updDelivery.setCode3(Contents.get(33).trim());
        //updDelivery.setNum3(Integer.parseInt(Contents.get(34).trim()));
        updDelivery.setTitle4(Contents.get(35).trim());
        if (!"".equals(Contents.get(36).trim())) {
            updDelivery.setPrice4(Double.parseDouble(Contents.get(36).trim()));
        }
        updDelivery.setCode4(Contents.get(37).trim());
        if (!"".equals(Contents.get(38).trim())) {
            updDelivery.setNum4(Integer.parseInt(Contents.get(38).trim()));
        }
        updDelivery.setTitle5(Contents.get(39).trim());
        if (!"".equals(Contents.get(40).trim())) {
            updDelivery.setPrice5(Double.parseDouble(Contents.get(40).trim()));
        }
        updDelivery.setCode5(Contents.get(41).trim());
        if (!"".equals(Contents.get(42).trim())) {
            updDelivery.setNum5(Integer.parseInt(Contents.get(42).trim()));
        }

        updDelivery.setTitle6(Contents.get(43).trim());
        if (!"".equals(Contents.get(44).trim())) {
            updDelivery.setPrice6(Double.parseDouble(Contents.get(44).trim()));
        }
        updDelivery.setCode6(Contents.get(45).trim());
        if (!"".equals(Contents.get(46).trim())) {
            updDelivery.setNum6(Integer.parseInt(Contents.get(46).trim()));
        }
        updDelivery.setTitle7(Contents.get(47).trim());
        if (!"".equals(Contents.get(48).trim())) {
            updDelivery.setPrice7(Double.parseDouble(Contents.get(48).trim()));
        }

        updDelivery.setCode7(Contents.get(49).trim());
        if (!"".equals(Contents.get(50).trim())) {
            updDelivery.setNum7(Integer.parseInt(Contents.get(50).trim()));
        }

        updDelivery.setTitle8(Contents.get(51).trim());
        if (!"".equals(Contents.get(52).trim())) {
            updDelivery.setPrice8(Double.parseDouble(Contents.get(52).trim()));
        }

        updDelivery.setCode8(Contents.get(53).trim());
        if (!"".equals(Contents.get(54).trim())) {
            updDelivery.setNum8(Integer.parseInt(Contents.get(54).trim()));
        }

        updDelivery.setTitle9(Contents.get(55).trim());
        if (!"".equals(Contents.get(56).trim())) {
            updDelivery.setPrice9(Double.parseDouble(Contents.get(56).trim()));
        }

        updDelivery.setCode9(Contents.get(57).trim());
        if (!"".equals(Contents.get(58).trim())) {
            updDelivery.setNum9(Integer.parseInt(Contents.get(58).trim()));
        }

        updDelivery.setTitle10(Contents.get(59).trim());
        if (!"".equals(Contents.get(60).trim())) {
            updDelivery.setPrice10(Double.parseDouble(Contents.get(60).trim()));
        }

        updDelivery.setCode10(Contents.get(61).trim());
        if (!"".equals(Contents.get(62).trim())) {
            updDelivery.setNum10(Integer.parseInt(Contents.get(62).trim()));
        }

        return updDelivery;
    }
}
