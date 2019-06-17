package com.arvato.cc.service1.impl;

import com.arvato.cc.constant.Constants;
import com.arvato.cc.dao1.AlipayDao;
import com.arvato.cc.dao1.OperationLogDao;
import com.arvato.cc.dao1.SkuDataDao;
import com.arvato.cc.exceptions.DataException;
import com.arvato.cc.model.OperationHistory;
import com.arvato.cc.model.Sku;
import com.arvato.cc.service1.UploadSkuService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: caol005
 * Date: 15-7-16
 * Time: 下午1:58
 * To change this template use File | Settings | File Templates.
 */
@Service
public class UploadSkuServiceImpl implements UploadSkuService {
    private static final Logger log = LoggerFactory.getLogger(UploadSkuServiceImpl.class);

    @Autowired
    private AlipayDao uploadDao;
    @Value("${upload.process}")
    private String processPath;  //正在操作的文件
    @Value("${upload.arch}")
    private String archPath;   //成功读取本地文件备份
    @Value("${upload.error}")
    private String errorPath;   //读取失败本地文件备份

    @Autowired
    private SkuDataDao skuDataDao;

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
            Map<Integer, Map<Integer, List<String>>> resultMap = FileUtil.readExcel(new FileInputStream(updFile), 5, 0, 0, fileType);
            List<String> ids = new ArrayList<String>();
            List<Sku> rs = this.skuDataDao.getAllIds();
            if (!CollectionUtils.isEmpty(rs)) {
                for (Sku s : rs) {
                    ids.add(s.getId().toString());
                }
            }
            String storeId=getStore(resultMap);
            int rowNum = 2;
            //对模版格式及非空进行校验
            Set<Map.Entry<Integer, List<String>>> resultSet = resultMap.get(0).entrySet();
            if (!CollectionUtils.isEmpty(resultSet)) {
                //对表头进行校验
                Boolean IsNeed = true;
                List<Sku> skuInsertList=new ArrayList<Sku>();
                List<Sku> skuUpdateList=new ArrayList<Sku>();
                for (Map.Entry<Integer, List<String>> ent : resultSet) {
                    List<String> Contents = ent.getValue();
                    try {
                        if (IsNeed) {
                            if (!CollectionUtils.isEmpty(Contents)) {
                                String msg = validateColumn(Contents);
                                if (Validate.isNullOrEmpty(msg)){
                                    IsNeed = false;
                                    continue;
                                }else {
                                    return Constants.ExceptionMsg.FORMAT_ERROR.getValue().toString() + msg;
                                }
                            }
                        }
                        if (!CollectionUtils.isEmpty(Contents)) {
                            //检索出所有id放入list集合。excel中如果在数据表中存在记录，则更新数据并记录id，
                            //从list集合中移除，将list集合中剩余的id的数据状态置为inactive,如果不存在记录，则保存入库，并记录状态active
                            //根据参数信息查询数据库,查看数据是否存在
                            Sku sku = getSkuData(Contents);
                            Sku skuObject = skuDataDao.get(sku);
                            if (skuObject != null) {
                                //根据ID更新数据,并将list集合中id移除
                                skuObject.setSapprice(sku.getSapprice());
                                skuObject.setTmallPrice(sku.getTmallPrice());
                                skuObject.setMatnr(sku.getMatnr());
                                skuObject.setBrand(sku.getBrand());
                                skuObject.setCid(sku.getCid());
                                skuObject.setStatus(
                                        Constants.UploadStatus.ACTIVE
                                                .toString());
                                //this.skuDataDao.updateSkuData(skuObject.getId(),
                                //        skuObject);
                                skuUpdateList.add(skuObject);
                                ids.remove(skuObject.getId().toString());
                            } else {
                                skuObject = sku;
                                skuObject.setStatus(
                                        Constants.UploadStatus.ACTIVE
                                                .toString());
                                //skuDataDao.saveOrUpdate(skuObject);
                                skuInsertList.add(skuObject);
                            }
                            rowNum++;
                        }else{
                            return  Constants.ExceptionMsg.DATA_IS_NULL.getValue().toString();
                        }
                    } catch (DataException ex) {
                        return String.format(Constants.upload_error_msg, rowNum, ex.getMessage());
                    }
                }
                if(resultSet.size() == 1){
                    return  Constants.ExceptionMsg.DATA_IS_NULL.getValue().toString();
                }

                if(skuInsertList!=null&&skuInsertList.size()>0){
                    for(Sku sku : skuInsertList){
                        skuDataDao.saveOrUpdate(sku);
                    }
                }
                if(skuUpdateList!=null&&skuUpdateList.size()>0){
                    for(Sku sku : skuUpdateList){
                        this.skuDataDao.updateSkuData(sku.getId(), sku);
                    }
                }
                //将list中的剩余id的记录，更新状态inactive
                Sku sku=null;
                for (String r : ids) {
                // this.skuDataDao.updateSkuStatus(Integer.parseInt(r));
                    //update by songsong start
                    if (!Validate.isNullOrEmpty(r)){
                        sku=skuDataDao.getSkuById(Integer.valueOf(r));
                        if (storeId.equalsIgnoreCase(sku.getBrand())){
                            sku.setStatus(Constants.UploadStatus.INACTIVE.toString());
                            skuDataDao.saveOrUpdate(sku);
                        }
                    }
                    //update by songsong end

                }

                //将上传的操作记录到历史操作表中
                OperationHistory OperationHistory = new OperationHistory();
                OperationHistory.setFileName(fileName);
                OperationHistory.setOperateTime(CommonHelper.getThisTimestamp());
                OperationHistory.setOperateOp(UserSecurityUtil.getCurrentUsername());
                OperationHistory.setFileType(Constants.OperationType.UPLOAD_SKU.toString());
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
        log.debug("method upload Sku File end");
        return result;
    }

//获取本次上传文件的门店
    public static String getStore(Map<Integer, Map<Integer, List<String>>> resultMap){
        String storeId=resultMap.get(0).get(1).get(0).trim();
        System.out.println("storeId="+storeId);
        if ("Bosch".equalsIgnoreCase(storeId)){
            return "Bosch";
        }else if ("Siemens".equalsIgnoreCase(storeId)){
            return "Siemens";
        }else{
            return "未找到门店";
        }
    }


    public String validateColumn(List<String> contents) {
        String msg = "";
        String[] columns = {"Brand", "Category", "VIB", "SAP Price", "Tmall Price"};
        if (contents.size() == columns.length) {
            for (int i = 0; i < columns.length; i++) {
                  if(!columns[i].trim().equals(contents.get(i))){
                      msg = "{列："+contents.get(i)+"，应为："+columns[i].trim()+"}";
                      return msg;
                  }
            }
        } else {
            msg = "{列数不准确}";
        }
        return msg;
    }

    public Sku getSkuData(List<String> Contents) throws DataException {

        Sku sku = new Sku();
        //模板中共5个字段，分别是Brand，品类,MATNR,Tmall Price,Sap Price，
        //每列不能为空，金额格式校验

        if (Validate.isNullOrEmpty(Contents) || Contents.size() == 0) {
            throw new DataException(Constants.ExceptionMsg.DATA_IS_NULL.getValue().toString());
        }

        String brand = Contents.get(0).trim();
        if (StringUtils.isEmpty(brand)) {
            throw new DataException("Brand" + Constants.ExceptionMsg.DATA_IS_NULL.getValue().toString());
        }
        sku.setBrand(brand);

        String catagory = Contents.get(1).trim();
        if (StringUtils.isEmpty(catagory)) {
            throw new DataException("Category" + Constants.ExceptionMsg.DATA_IS_NULL.getValue().toString());
        }
        sku.setCid(catagory);

        String matnr = Contents.get(2).trim();
        if (StringUtils.isEmpty(matnr)) {
            throw new DataException("VIB" + Constants.ExceptionMsg.DATA_IS_NULL.getValue().toString());
        }
        sku.setMatnr(matnr);

        //校验sap price
        String sapPrice_str = Contents.get(3).trim();
        if (StringUtils.isEmpty(sapPrice_str)) {
            throw new DataException("SAP Price" + Constants.ExceptionMsg.DATA_IS_NULL.getValue().toString());
        }

        Double sap_price = null;
        if (!Pattern.matches("^[+-]?\\d+(\\.\\d+)?$", sapPrice_str)) {
            throw new DataException("SAP Price" + Constants.ExceptionMsg.PRICE_FORMAT_ERROR.getValue().toString());
        } else {
            sap_price = new Double(sapPrice_str);
            sku.setSapprice(sap_price);
        }

        //校验tmall price
        String tmallPrice_str = Contents.get(4).trim();
        if (StringUtils.isEmpty(tmallPrice_str)) {
            throw new DataException("Tmall Price" + Constants.ExceptionMsg.DATA_IS_NULL.getValue().toString());
        }
        Double tmall_price = null;
        if (!Pattern.matches("^[+-]?\\d+(\\.\\d+)?$", tmallPrice_str)) {
            throw new DataException("Tmall Price" +Constants.ExceptionMsg.PRICE_FORMAT_ERROR.getValue().toString());
        } else {
            tmall_price = new Double(tmallPrice_str);
            sku.setTmallPrice(tmall_price);
        }
        return sku;
    }
}
