package com.arvato.platform.controller.export;

import com.arvato.cc.form.finance.OrderReport;
import com.arvato.cc.service1.OrderReportService;
import com.arvato.cc.util.CommonHelper;
import com.arvato.cc.util.Validate;
import com.arvato.cc.util.finance.OrderReportExcel;
import com.arvato.jdf.web.json.JsonResult;
import com.arvato.platform.controller.sys.CommonController;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
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
 * Date: 15-8-28
 * Time: 上午10:41
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class TradeReportController extends CommonController {
    private static final Log log = LogFactory.getLog(TradeReportController.class);

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Autowired
    private OrderReportService orderReportService;


    /**
     * 跳转到订单报表导出页面
     */
    @RequestMapping(value = "/download/order")
    public String downloadOrderReportIndex() {
        return "download/orderReport";
    }

    @RequestMapping(value = "/order/generateOrderReport.json", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult generateOrderReport(HttpServletRequest request, HttpServletResponse response) throws Exception {
        long start = System.currentTimeMillis();
        log.info("Entry method downLoadList start!");
        Map<String, String> queryParams = new HashMap<String, String>();
        String startTime = request.getParameter("startTime1");
        String endTime = request.getParameter("endTime1");
        String alipayStartTime = request.getParameter("alipayStartTime1");
        String alipayEndTime = request.getParameter("alipayEndTime1");
        String brand = request.getParameter("brand1");
        String orderNum = request.getParameter("orderNum1");
        if (!Validate.isNullOrEmpty(startTime)) {
            queryParams.put("startTime", startTime);
        }
        if (!Validate.isNullOrEmpty(endTime)) {
            queryParams.put("endTime", endTime);
        }
        if (!Validate.isNullOrEmpty(alipayStartTime)) {
            queryParams.put("alipayStartTime", alipayStartTime);
        }
        if (!Validate.isNullOrEmpty(alipayEndTime)) {
            queryParams.put("alipayEndTime", alipayEndTime);
        }
        if (!Validate.isNullOrEmpty(brand)) {
            queryParams.put("storeId", brand);
        }
        if (!Validate.isNullOrEmpty(orderNum)) {
            queryParams.put("poNumber", orderNum);
        }
        String filePath = "";
        try {
            filePath = orderReportService.generateOrderReport(queryParams);
        } catch (Exception e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println("start to end:"+(end-start));
        return this.jsonResult(filePath);
    }

    @RequestMapping(value = "/order/exportOrderReport.json")
    public void downloadOrderReport(HttpServletRequest request, HttpServletResponse response) throws Exception {
        long start = System.currentTimeMillis();
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
            log.info(e.getMessage());
            e.printStackTrace();
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
            long end = System.currentTimeMillis();
            System.out.println("start to end:"+(end-start));
        }
    }


//    @RequestMapping(value = "/order/exportOrderReport.json", method = RequestMethod.POST)
////    @ResponseBody
//    public void downloadOrderReport(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        long start = System.currentTimeMillis();
//        log.info("Entry method downLoadList start!");
//        Map<String, String> queryParams = new HashMap<String, String>();
//        String startTime = request.getParameter("startTime1");
//        String endTime = request.getParameter("endTime1");
//        String brand = request.getParameter("brand1");
//        String orderNum = request.getParameter("orderNum1");
//        if (!Validate.isNullOrEmpty(startTime)) {
//            queryParams.put("startTime", startTime);
//        }
//        if (!Validate.isNullOrEmpty(endTime)) {
//            queryParams.put("endTime", endTime);
//        }
//        if (!Validate.isNullOrEmpty(brand)) {
//            queryParams.put("storeId", brand);
//        }
//        if (!Validate.isNullOrEmpty(orderNum)) {
//            queryParams.put("poNumber", orderNum);
//        }
//        String fileName = "OrderReport-"+ CommonHelper.DateFormat(CommonHelper.getThisTimestamp(),"yyyy-MM-dd HH:mm:ss")+".xlsx";
//        ByteArrayOutputStream bos = null;
//        OutputStream out = null;
//        try {
//            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
////            response.setContentType("text/html;charset=utf-8");
////            response.setContentType("application/msexcel;charset=UTF-8");
//            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
//            Workbook workbook = orderReportService.generateOrderReportByExample(queryParams);
////            bos = new ByteArrayOutputStream();
//
//            out = response.getOutputStream();
//            workbook.write(out);
////            out.write(bos.toByteArray());
//            out.flush();
////            throw new Exception();
//        } catch (Exception e) {
//            log.info(e.getMessage());
//            e.printStackTrace();
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
//            long end = System.currentTimeMillis();
//            System.out.println("start to end:"+(end-start));
//        }
//    }
}
