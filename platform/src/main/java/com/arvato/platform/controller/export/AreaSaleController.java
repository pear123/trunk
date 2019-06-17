package com.arvato.platform.controller.export;

import com.arvato.cc.form.AreaSaleModel;
import com.arvato.cc.form.ComboStore;
import com.arvato.cc.form.SaleStructureBean;
import com.arvato.cc.form.finance.DeliveryReport;
import com.arvato.cc.form.finance.FinanceReport;
import com.arvato.cc.form.finance.OrderReport;
import com.arvato.cc.form.finance.RestFinanceReport;
import com.arvato.cc.service1.*;
import com.arvato.cc.util.CommonHelper;
import com.arvato.cc.util.Validate;
import com.arvato.cc.util.finance.*;
import com.arvato.jdf.dao.Page;
import com.arvato.jdf.util.PageHelper;
import com.arvato.jdf.web.json.JsonResult;
import com.arvato.platform.controller.sys.CommonController;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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

@Controller
public class AreaSaleController extends CommonController {

    private static final Log log = LogFactory.getLog(AreaSaleController.class);

    @Autowired
    private AreaSaleService areaSaleService;

    /**
     *销售结构报表
     */
    @RequestMapping(value = "download/areaSale")
    public String downloadAreaSaleIndex() {
        return "download/areaSale";
    }

    /**
     *销售结构报表数据展示页
     */
    @RequestMapping(value = "sale/areaSale")
    public String downloadAreaSalePage() {
        return "sale/areaSalePage";
    }

    @RequestMapping(value = "/download/generateAreaSale.json", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult generateAreaSale(HttpServletRequest request, HttpServletResponse response) throws Exception {
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
            filePath = areaSaleService.generateAreaSaleReport(queryParams);
        } catch (Exception e) {
            e.printStackTrace();
            log.info(e.getMessage());
        }
        return this.jsonResult(filePath);
    }

    @RequestMapping(value = "/download/downAreaSale.json")
    public void downAreaSale(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("Entry method downLoadList start!");
        String filePath = request.getParameter("filePath");
        if(Validate.isNullOrEmpty(filePath)){
            return;
        }
        String fileName = filePath.substring(filePath.lastIndexOf("/") + 1,filePath.length());
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


//    @RequestMapping(value = "/download/downAreaSale.json", method = RequestMethod.POST)
//    public void downAreaSale(HttpServletRequest request, HttpServletResponse response) throws Exception {
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
//        String fileName = "RegionSale" + CommonHelper.DateFormat(CommonHelper.getThisTimestamp(), "yyyy-MM-dd HH:mm:ss") + ".xlsx";
//        ByteArrayOutputStream bos = null;
//        OutputStream out = null;
//        try {
//            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
//            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
//
//            bos = new ByteArrayOutputStream();
//            XSSFWorkbook excel = areaSaleService.buildAreaSale(queryParams);
//            excel.write(bos);
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





    @RequestMapping(value = "/sale/showAreaSale.json")
    @ResponseBody
    public JsonResult showAreaSale(HttpServletRequest request, HttpServletResponse response) throws Exception {
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
            queryParams.put("category", new String(category.getBytes("ISO-8859-1"),"UTF-8"));  //new String(category.getBytes(“ISO-8859-1”),”GBK”);
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
        List<AreaSaleModel> list = areaSaleService.getAreaSaleModel(queryParams);
        return  jsonResult(list);
    }

    @RequestMapping(value = "/sale/showAreaSaleSummary.json")
    @ResponseBody
    public JsonResult showAreaSaleSummary(HttpServletRequest request, HttpServletResponse response) throws Exception {
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
            queryParams.put("category", new String(category.getBytes("ISO-8859-1"),"UTF-8"));  //new String(category.getBytes(“ISO-8859-1”),”GBK”);
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
        List<AreaSaleModel> list = areaSaleService.getAreaSaleModelSummary(queryParams);
        return  jsonResult(list);
    }

}
