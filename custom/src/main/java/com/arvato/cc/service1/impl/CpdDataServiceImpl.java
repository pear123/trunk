package com.arvato.cc.service1.impl;

import com.arvato.cc.constant.Constants;
import com.arvato.cc.dao1.CpdDao;
import com.arvato.cc.dao1.OperationLogDao;
import com.arvato.cc.exceptions.DataException;
import com.arvato.cc.model.OperationHistory;
import com.arvato.cc.model.UpdCpd;
import com.arvato.cc.service1.CpdDataService;
import com.arvato.cc.service1.OperationLogService;
import com.arvato.cc.util.CommonHelper;
import com.arvato.cc.util.FileUtil;
import com.arvato.cc.util.UserSecurityUtil;
import com.arvato.cc.util.Validate;
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
import java.io.InputStream;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: XUSO002
 * Date: 15-8-8
 * Time: 下午3:05
 * To change this template use File | Settings | File Templates.
 */
@Service
public class CpdDataServiceImpl  implements CpdDataService{

    private static final Logger log = LoggerFactory.getLogger(CpdDataService.class);

    @Value("${upload.process}")
    private String processPath;  //正在操作的文件
    @Value("${upload.arch}")
    private String archPath;   //成功读取本地文件备份
    @Value("${upload.error}")
    private String errorPath;   //读取失败本地文件备份

    @Autowired
    private OperationLogDao operationLogDao;
    @Autowired
    private CpdDao cpdDao;
    @Autowired
    private OperationLogService operationLogService;
    private String dateFormat = "yyyyMMddHHmmss"; //file suffix

    /**
     * 上传CPD数据
     *
     * @param uploadFile
     * @param fileType
     * @param folder
     * @return
     */
    public String uploadFile(CommonsMultipartFile uploadFile, String fileType, String folder) {
        log.debug("method uploadFile start");
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
            List<List<String>>  cpdList=fileUtil.getExcelLineStringList(
                    updFile.toString(), 2, 0, 0, fileType);
            if (cpdList.size()==0||cpdList.size()==1||Validate.isNullOrEmpty(cpdList)){
                result=Constants.ExceptionMsg.DATA_IS_NULL.getValue().toString();
                return result;
            }
            //验证模版
            if (!validateTemplate(cpdList)){
                return Constants.ExceptionMsg.FORMAT_ERROR.getValue().toString();
            }
            HashMap<String,List<String>> cpdMap=listToMap(cpdList);

            int rowNum = 2;
            List<UpdCpd> updCpdList=new ArrayList<UpdCpd>();
            for (int i=0;i<cpdList.size();i++) {
                   try {
                       List<String> list = cpdList.get(i);
                       if ("Office name Chinese".equals(list.get(0))){
                             continue;
                         }
                       //if(Validate.isNullOrEmpty(cpdMap.get(key))){
                       //    continue;
                       //}
                       UpdCpd updCpd = getCpdData(list);
                       //cpdDao.saveOrUpdate(updCpd);
                       updCpdList.add(updCpd);
                       rowNum++;
                   } catch (Exception ex){
                       result = String.format(Constants.upload_error_msg,rowNum,ex.getMessage());
                       break;
                   }
            }
            if(result.equals(Constants.upload_success_msg)&&updCpdList!=null&&updCpdList.size()>0){
                //删除本次上传可能已经存在数据库中的数据
                List<String> officeNameList=getCpdOfficeName(cpdMap);
                if (!Validate.isNullOrEmpty(officeNameList)&&officeNameList.size()!=0){
                    cpdDao.deleteCpdByOfficeName(officeNameList);
                }
                //更新全部的状态
                cpdDao.updateAllCpdStatus();

                for(UpdCpd updCpd : updCpdList){
                    cpdDao.saveOrUpdate(updCpd);
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
            OperationHistory operationHistory = operationLogService.generateOperationHistory(fileName,Constants.OperationType.UPLOAD_CPD.toString(),Constants.ModelType.UPLOAD.toString()) ;
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
        log.debug("method upload File end");
        return result;
    }

    public UpdCpd findCpdDataByCityCode(String receiverStatus, String receiverCity) {
        try{
            UpdCpd updCpd = null;
            List<UpdCpd> cpdList = cpdDao.findAllUpdCpd();
            if(Validate.isNullOrEmpty(cpdList)){
                return null;
            }
            for(UpdCpd cpd : cpdList) {
                if(!Validate.isNullOrEmpty(receiverCity)){
                    if(receiverCity.indexOf(cpd.getOfficeName()) >= 0) {
                        updCpd = cpd;
                        break;
                    }
                }
                if(!Validate.isNullOrEmpty(receiverStatus)) {
                    if(receiverStatus.indexOf(cpd.getOfficeName()) >= 0) {
                        updCpd = cpd;
                        break;
                    }
                }
            }
            return updCpd;
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public Map<String, String> getCpdCode() {
        List<Map<String,Object>> list = cpdDao.getCpdCode();
        Map<String,String> map = new HashMap<String,String>();
        for(Map<String,Object> li : list) {
            map.put(li.get("tid").toString(),li.get("office_name").toString());
        }
        return map;
    }

    /**
     * 数据验证
     * @param Contents
     * @return
     * @throws DataException
     */
    public UpdCpd getCpdData(List<String> Contents) throws  DataException{
        if(Validate.isNullOrEmpty(Contents) || Contents.size() == 0) {
            throw new DataException(Constants.ExceptionMsg.DATA_IS_NULL.getValue().toString());
        }
        UpdCpd updCpd = new UpdCpd();
        //模板中的所有字段,共6个字段
        String officeName = Contents.get(0).trim();
        if (StringUtils.isEmpty(officeName)){
            throw new DataException("OfficeName:" + Constants.ExceptionMsg.DATA_IS_NULL.getValue().toString());
        }
        updCpd.setOfficeName(officeName);
        String code=Contents.get(1).trim();
        if (StringUtils.isEmpty(code)){
            throw new DataException("Code:" + Constants.ExceptionMsg.DATA_IS_NULL.getValue().toString());
        }
        updCpd.setCode(code);
        updCpd.setStatus(Constants.UploadStatus.ACTIVE.toString());
        return updCpd;
    }

    /**
     * 将List转换为Map
     * 去除本次上传文件中本身存在的重复项
     * @return
     */
    public HashMap<String,List<String>> listToMap(List<List<String>> cpdList){
        HashMap<String,List<String>> cpdMap=new HashMap<String, List<String>>();
        if (!Validate.isNullOrEmpty(cpdList)){
            for (int i=0;i<cpdList.size();i++) {
                if (!Validate.isNullOrEmpty(cpdList.get(i).get(0))){
                     cpdMap.put(cpdList.get(i).get(0),cpdList.get(i));
                }
            }
        }
        return cpdMap;
    }

    /**
     * 获取到全部的officeName
     * @param officeNameMap
     * @return
     */
    public List<String> getCpdOfficeName(Map<String,List<String>> officeNameMap){
        String  officeName="";
        List<String> officeNameList=new ArrayList<String>();
        for (Map.Entry<String,List<String>> ent : officeNameMap.entrySet()) {
            if ("Office name Chinese".equals(ent.getKey())){
                 continue;
            }
            officeNameList.add(ent.getKey());
        }
        return officeNameList;
    }

    //验证模版格式
    public boolean validateTemplate(List<List<String>> cpdList){
        List<String> contents=cpdList.get(0);
        if(contents.size()!=2){
            return  false;
        }
        if (("Office name Chinese".trim()).equalsIgnoreCase(contents.get(0))&&("Code".trim()).equalsIgnoreCase(contents.get(1))) {
            return true;
        }  else{
            return  false;
        }

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
}
