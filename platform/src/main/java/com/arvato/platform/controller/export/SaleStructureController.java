package com.arvato.platform.controller.export;

import com.arvato.cc.form.SaleStructureBean;
import com.arvato.cc.service1.SaleStructureService;
import com.arvato.cc.util.CommonHelper;
import com.arvato.cc.util.Validate;
import com.arvato.cc.util.finance.SaleStructureReportExcel;
import com.arvato.jdf.web.json.JsonResult;
import com.arvato.platform.controller.sys.CommonController;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: caol005
 * Date: 15-9-7
 * Time: 上午9:49
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class SaleStructureController extends CommonController {

    private static final Log log = LogFactory.getLog(SaleStructureController.class);

    @Autowired
    private SaleStructureService saleStructureService;

    /**
     * 销售结构报表
     */
    @RequestMapping(value = "/download/saleStructure")
    public String downloadSaleStructureIndex() {
        return "download/saleStructure";
    }

    /**
     *销售结构报表数据展示页
     */
    @RequestMapping(value = "/sale/saleStructure")
    public String downloadAreaSalePage() {
        return "sale/saleStructure";
    }

    @RequestMapping(value = "/sale/generateSaleStructure.json", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult generateSaleStructure(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("Entry method downLoadList start!");
        Map<String, String> queryParams = new HashMap<String, String>();
        String category = request.getParameter("category1");
        String storeId = request.getParameter("storeId1");
        String startTime = request.getParameter("startTime1");
        String endTime = request.getParameter("endTime1");
        String is_trade_close = request.getParameter("is_trade_close1");
        String sku = request.getParameter("sku1");
        if (null != startTime && !"".equals(startTime)) {
            queryParams.put("startTime", startTime);
        }
        if (null != endTime && !"".equals(endTime)) {
            queryParams.put("endTime", endTime);
        }
        if (null != category && !"".equals(category)) {
            queryParams.put("category", category);
        }
        if (null != storeId && !"".equals(storeId)) {
            queryParams.put("storeId", storeId);
        }
        if (null != is_trade_close && !"".equals(is_trade_close)) {
            queryParams.put("is_trade_close", is_trade_close);
        }
        if (null != sku && !"".equals(sku)) {
            queryParams.put("sku", sku);
        }
        String filePath = "";
        try {
            filePath = saleStructureService.generateSaleStructureReport(queryParams);
        } catch (Exception e) {
            e.printStackTrace();
            log.info(e.getMessage());
        }
        return this.jsonResult(filePath);
    }

    @RequestMapping(value = "/sale/downSaleStructure.json")
    public void downSaleStructure(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("Entry method downLoadList start!");
        String filePath = request.getParameter("filePath");
        if(Validate.isNullOrEmpty(filePath)){
            return;
        }
        String fileName = filePath.substring(filePath.lastIndexOf("/") + 1, filePath.length());
        FileInputStream fileInputStream = null;
        OutputStream out = null;
        BufferedInputStream fin = null;
        try {
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

            fileInputStream = new FileInputStream(filePath);
            fin = new BufferedInputStream(fileInputStream);
            out = response.getOutputStream();
            byte[] content = new byte[1024];
            int length;
            while ((length = fin.read(content, 0, content.length)) != -1) {
                out.write(content, 0, length);
            }
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
            log.info(e.getMessage());
        } finally {
            try {
                if (null != fileInputStream) {
                    fileInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (null != fin) {
                    fin.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                if (null != out) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

//    @RequestMapping(value = "/sale/downSaleStructure.json", method = RequestMethod.POST)
//    public void downSaleStructure(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        log.info("Entry method downLoadList start!");
//        Map<String, String> queryParams = new HashMap<String, String>();
//        String category = request.getParameter("category1");
//        String storeId = request.getParameter("storeId1");
//        String startTime = request.getParameter("startTime1");
//        String endTime = request.getParameter("endTime1");
//        String is_trade_close = request.getParameter("is_trade_close1");
//        String sku = request.getParameter("sku1");
//        if (null != startTime && !"".equals(startTime)) {
//            queryParams.put("startTime", startTime);
//        }
//        if (null != endTime && !"".equals(endTime)) {
//            queryParams.put("endTime", endTime);
//        }
//        if (null != category && !"".equals(category)) {
//            queryParams.put("category", category);
//        }
//        if (null != storeId && !"".equals(storeId)) {
//            queryParams.put("storeId", storeId);
//        }
//        if (null != is_trade_close && !"".equals(is_trade_close)) {
//            queryParams.put("is_trade_close", is_trade_close);
//        }
//        if (null != sku && !"".equals(sku)) {
//            queryParams.put("sku", sku);
//        }
//        String fileName = "SaleStructure" + CommonHelper.DateFormat(CommonHelper.getThisTimestamp(),"yyyy-MM-dd HH:mm:ss") + ".xlsx";
//        ByteArrayOutputStream bos = null;
//        OutputStream out = null;
//        try {
//            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
//            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
//
//            List<SaleStructureBean> valueList = saleStructureService.findSaleStructure(queryParams);
//
//            SaleStructureReportExcel excel = new SaleStructureReportExcel(valueList);
//
//            bos = (ByteArrayOutputStream) excel.getOutputStream();
//            out = response.getOutputStream();
//            out.write(bos.toByteArray());
//            out.flush();
//        } catch (Exception e) {
//            e.printStackTrace();
//            log.info(e.getMessage());
//        } finally {
//            try {
//                if (null != bos) {
//                    bos.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            try{
//                if(null != out){
//                    out.close();
//                }
//            } catch (IOException ex){
//                ex.printStackTrace();
//            }
//        }
//    }


    @RequestMapping(value = "/sale/showSaleStructure.json")
    @ResponseBody
    public JsonResult showSaleStructure(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, String> queryParams = new HashMap<String, String>();
        String category = request.getParameter("category1");
        String storeId = request.getParameter("storeId1");
        String startTime = request.getParameter("startTime1");
        String endTime = request.getParameter("endTime1");
        String is_trade_close = request.getParameter("is_trade_close1");
        String sku = request.getParameter("sku1");
        if (null != startTime && !"".equals(startTime)) {
            queryParams.put("startTime", startTime);
        }
        if (null != endTime && !"".equals(endTime)) {
            queryParams.put("endTime", endTime);
        }
        if (null != category && !"".equals(category)) {
            queryParams.put("category", new String(category.getBytes("ISO-8859-1"),"UTF-8"));
        }
        if (null != storeId && !"".equals(storeId)) {
            queryParams.put("storeId", storeId);
        }
        if (null != is_trade_close && !"".equals(is_trade_close)) {
            queryParams.put("is_trade_close", is_trade_close);
        }
        if (null != sku && !"".equals(sku)) {
            queryParams.put("sku", new String(sku.getBytes("ISO-8859-1"),"UTF-8"));
        }
        List<SaleStructureBean> list = saleStructureService.findSaleStructure(queryParams);
        return  jsonResult(list);
    }


}
