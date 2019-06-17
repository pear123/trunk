package com.arvato.cc.service1.impl;

import com.arvato.cc.constant.Constants;
import com.arvato.cc.dao1.GiftDataDao;
import com.arvato.cc.dao1.OperationLogDao;
import com.arvato.cc.exceptions.DataException;
import com.arvato.cc.form.ComboStore;
import com.arvato.cc.model.GiftData;
import com.arvato.cc.model.OperationHistory;
import com.arvato.cc.service1.GiftDataService;
import com.arvato.cc.service1.OperationLogService;
import com.arvato.cc.util.CommonHelper;
import com.arvato.cc.util.FileUtil;
import com.arvato.cc.util.Validate;
import com.arvato.jdf.dao.Page;
import com.arvato.jdf.dao.PropertyFilter;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: XUSO002
 * Date: 15-8-8
 * Time: 下午12:38
 * To change this template use File | Settings | File Templates.
 */
@Service
public class GiftDataServiceImpl implements GiftDataService {
    private static final Logger log = LoggerFactory.getLogger(GiftDataServiceImpl.class);

    @Value("${upload.process}")
    private String processPath;  //正在操作的文件
    @Value("${upload.arch}")
    private String archPath;   //成功读取本地文件备份
    @Value("${upload.error}")
    private String errorPath;   //读取失败本地文件备份


    @Autowired
    private OperationLogDao operationLogDao;
    @Autowired
    private GiftDataDao giftDataDao;
    @Autowired
    private OperationLogService operationLogService;
    private String dateFormat = "yyyyMMddHHmmss"; //file suffix

    public List<ComboStore> findCategory() {
        return getComboStore(giftDataDao.findCategory());
    }

    public List<ComboStore> findBrand() {
        return getComboStore(giftDataDao.findBrand());
    }

    private List<ComboStore> getComboStore(List<String> list){
        List<ComboStore> comboStores = new ArrayList<ComboStore>();
        if(Validate.isNullOrEmpty(list)){
            return comboStores;
        }
        ComboStore comboStore = null;
        for(String str : list){
            comboStore = new ComboStore();
            comboStore.setLabel(str);
            comboStore.setValue(str);
            comboStores.add(comboStore);
        }
        return comboStores;

    }

    public void delete(Integer id) {
        giftDataDao.delete(id);
    }

    public void deleteGift(String id){
        System.out.println("id="+id);
        String[] ids=id.split(";");
        giftDataDao.deleteGift(ids);
    }

    public GiftData save(GiftData giftData) {

        OperationHistory operationHistory = operationLogService.generateOperationHistory(null,Constants.OperationType.UPLOAD_GIFT.toString(),Constants.Model.Gift.toString()) ;
        operationLogDao.saveOperationLog(operationHistory);
        return giftDataDao.save(giftData);
    }

    public GiftData findById(Integer id) {
        return giftDataDao.findById(id);
    }

    public Page<GiftData> findPropertyFilter(Page page, PropertyFilter propertyFilter) {
        return giftDataDao.findPropertyFilter(page, propertyFilter);
    }

    /**
     * 上传物料数据
     *
     * @param uploadFile
     * @param fileType
     * @param folder
     * @return
     */

    public String uploadFile(CommonsMultipartFile uploadFile, String fileType, String folder) {
        log.debug("method uploadFile start");
        // String result = "failure";
        String result = Constants.upload_success_msg;
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
            FileUtil fileUtil=new FileUtil();
            List<List<String>>  rowList=fileUtil.getExcelLineStringList(processPath + folder + fileName, 4, 0, 0, fileType);
            if (rowList.get(0).size()==1||Validate.isNullOrEmpty(rowList)){
                result=Constants.ExceptionMsg.DATA_IS_NULL.getValue().toString();
                return result;
            }
            String storeId=getStore(rowList);
            //验证模版
            if(!FileUtil.validateTemplate(getGiftTitle(), rowList.get(0))){
                result = Constants.ExceptionMsg.FORMAT_ERROR.getValue().toString();
                return result;
            }
            int rowNum = 2;
            List<String> giftlist = new ArrayList<String>();
            List<GiftData> allSkuIds = this.giftDataDao.getAllSkuIds();
            for (GiftData gift: allSkuIds) {
                giftlist.add(gift.getSkuId().toString());
            }
            List<GiftData> giftDataList=new ArrayList<GiftData>();
            for (int i=1;i<rowList.size();i++) {
                try{
                    if(Validate.isNullOrEmpty(rowList.get(i))){
                        continue;
                    }
                    GiftData giftData = getGiftData(rowList.get(i));
                    if (!Validate.isNullOrEmpty(giftData)) {
                       // GiftData giftObject = giftDataDao.getGiftBySkuNo(giftData.getSkuNo());
                        GiftData giftObject = giftDataDao.getGiftBySkuNoAndBrand(giftData.getSkuNo(),giftData.getBrand());
                        if (!Validate.isNullOrEmpty(giftObject)) {
                            //根据ID更新数据,并将list集合中id移除
                            giftObject.setBrand(giftData.getBrand());
                            giftObject.setName(giftData.getName());
                            giftObject.setMemo(giftData.getMemo());
                            giftObject.setStatus(
                                    Constants.UploadStatus.ACTIVE.toString());
                            //this.giftDataDao.saveOrUpdate(giftObject);
                            giftDataList.add(giftObject);
                            rowNum++;
                            giftlist.remove(giftObject.getSkuId().toString());
                        } else {
                            //giftDataDao.saveOrUpdate(giftData);
                            giftDataList.add(giftData);
                            rowNum++;
                        }
                    }
                }catch (DataException ex){
                    result = String.format(Constants.upload_error_msg,rowNum,ex.getMessage());
                    break;
                }
            }
            if(result.equals(Constants.upload_success_msg)&&giftDataList!=null&&giftDataList.size()>0){
                for(GiftData giftData : giftDataList){
                    giftDataDao.saveOrUpdate(giftData);
                }
            }
            //将list中的剩余id的记录，更新状态inactive
            GiftData giftData=null;
            for (String r : giftlist) {
                if (!Validate.isNullOrEmpty(r)){
                    giftData=giftDataDao.getGiftBySkuId(Integer.valueOf(r));
                    if (storeId.equalsIgnoreCase(giftData.getBrand())){
                        giftData.setStatus(Constants.UploadStatus.INACTIVE.toString());
                        giftDataDao.saveOrUpdate(giftData);
                    }
                }
            }

            File archFilePath = new File(archPath + folder);
            if (!archFilePath.exists()) {
                archFilePath.mkdirs();
            }
            File updSuccessFile = new File(archFilePath, fileName);
            FileCopyUtils.copy(uploadFile.getBytes(), updSuccessFile);
            updFile.delete();
            //将上传的操作记录到历史操作表中
            OperationHistory operationHistory = operationLogService.generateOperationHistory(fileName,Constants.OperationType.UPLOAD_GIFT.toString(),Constants.ModelType.UPLOAD.toString()) ;
            operationLogDao.saveOperationLog(operationHistory);
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
        log.debug("method upload  File end");
        return result;
    }

    //获取本次上传文件的门店
    public static String getStore(List<List<String>> rowList){
       String storeId=rowList.get(1).get(0).trim();
        System.out.println("storeId="+storeId);
        if ("Bosch".equalsIgnoreCase(storeId)){
              return "Bosch";
        }else if ("Siemens".equalsIgnoreCase(storeId)){
            return "Siemens";
        }else{
            return "未找到门店";
        }
    }


    /**
     * 得到文件的title
     * @return
     */
    private static List<String> getGiftTitle(){
        List<String> title = new ArrayList<String>();
        title.add("Brand");
        title.add("SAP物料号");
        title.add("Goods Name");
        title.add("客服备注简写");
        return title;
    }

    public GiftData findSkuNo(String skuNo) {
        return giftDataDao.findSkuNo(skuNo);
    }

    public GiftData getGiftData(List<String> Contents) throws DataException{
        if(Validate.isNullOrEmpty(Contents) ) {
            throw new DataException(Constants.ExceptionMsg.DATA_IS_NULL.getValue().toString());
        }
        GiftData giftData = new GiftData();
        //模板中的所有字段,共6个字段
        String brand=Contents.get(0).trim();
        if (StringUtils.isEmpty(brand)){
            throw new DataException("品牌:" + Constants.ExceptionMsg.DATA_IS_NULL.getValue().toString());
        }
        giftData.setBrand(brand);
        String skuNo = Contents.get(1).trim();
        if (StringUtils.isEmpty(skuNo)){
            throw new DataException("物料号:" + Constants.ExceptionMsg.DATA_IS_NULL.getValue().toString());
         }
        giftData.setSkuNo(skuNo);
        String name = Contents.get(2).trim();
        if (StringUtils.isEmpty(name)){
            throw new DataException("商品名称:" + Constants.ExceptionMsg.DATA_IS_NULL.getValue().toString());
        }
        giftData.setName(name);
        String memo = Contents.get(3).trim();
        if (StringUtils.isEmpty(memo)){
            throw new DataException("客服备注简写:" + Constants.ExceptionMsg.DATA_IS_NULL.getValue().toString());
        }
        giftData.setMemo(memo);
        giftData.setStatus(Constants.UploadStatus.ACTIVE.toString());
        return giftData;
    }

    public Properties readProperties() {
        String fileName = "config/sftpConfig.properties";
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);

        Properties p = new Properties();
        try {
            p.load(inputStream);
        } catch (IOException e1) {
        }
        return p;
    }

    @Override
    public List<GiftData> findAllGiftSku(){
       return giftDataDao.findAllGift();
    }
}
