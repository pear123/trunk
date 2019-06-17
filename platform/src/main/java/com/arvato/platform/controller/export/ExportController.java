package com.arvato.platform.controller.export;

import com.arvato.cc.form.finance.RestFinanceReport;
import com.arvato.cc.service1.DeliveryReportService;
import com.arvato.cc.service1.FinanceReportService;
import com.arvato.cc.service1.RestFinanceReportService;
import com.arvato.cc.util.CommonHelper;
import com.arvato.cc.util.Validate;
import com.arvato.jdf.dao.Page;
import com.arvato.jdf.util.PageHelper;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;


@Controller
public class ExportController extends CommonController {

    private static final Log log = LogFactory.getLog(ExportController.class);

    @Autowired
    private RestFinanceReportService restFinanceReportService;

    @Autowired
    private DeliveryReportService deliveryReportService;

    @Autowired
    private FinanceReportService financeReportService;

    /**
     * 跳转到Alipay导出报表页面
     */
    @RequestMapping(value = "/download/restFinance")
    public String downloadRestFinanceIndex() {
        return "download/restFinance";
    }


    /**
     * 跳转到发货单报表导出页面
     *
     * @return
     */
    @RequestMapping(value = "/download/stkout")
    public String downloadStkOutIndex() {
        return "download/delivery";
    }

    /**
     * 跳转到财务报表导出页面
     *
     * @return
     */
    @RequestMapping(value = "/download/finance")
    public String downloadFinanceIndex() {
        return "download/finance";
    }

    @RequestMapping(value = "/delivery/generateDeliveryReport.json", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult generateDeliveryReport(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("Entry method downLoadList start!");
        Map<String, String> queryParams = new HashMap<String, String>();

        String startTime = request.getParameter("startTimeParam");
        String endTime = request.getParameter("endTimeParam");
        String brand = request.getParameter("brandParam");
        String orderNum = request.getParameter("orderNumParam");
        String invoiceNo = request.getParameter("invoiceNoParam");

        if (null != startTime && !"".equals(startTime)) {
            queryParams.put("startTime", startTime);
        }
        if (null != endTime && !"".equals(endTime)) {
            queryParams.put("endTime", endTime);
        }
        if (null != brand && !"".equals(brand)) {
            queryParams.put("brand", brand);
        }
        if (null != orderNum && !"".equals(orderNum)) {
            queryParams.put("orderNum", orderNum);
        }
        if (null != invoiceNo && !"".equals(invoiceNo)) {
            queryParams.put("invoiceNo", invoiceNo);
        }
        String filePath = "";
        try {
            filePath = deliveryReportService.generateDelivery(queryParams);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.jsonResult(filePath);
    }

    @RequestMapping(value = "/delivery/exportDeliveryReport.json")
    public void downloadDeliveryData(HttpServletRequest request, HttpServletResponse response) throws Exception {
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

//    @RequestMapping(value = "/delivery/exportDeliveryReport.json", method = RequestMethod.POST)
//    public void downloadDeliveryData(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        log.info("Entry method downLoadList start!");
//        Map<String, String> queryParams = new HashMap<String, String>();
//
//        String startTime = request.getParameter("startTimeParam");
//        String endTime = request.getParameter("endTimeParam");
//        String brand = request.getParameter("brandParam");
//        String orderNum = request.getParameter("orderNumParam");
//        String invoiceNo = request.getParameter("invoiceNoParam");
//
//        String isSuccess = "fail";
//        if (null != startTime && !"".equals(startTime)) {
//            queryParams.put("startTime", startTime);
//        }
//        if (null != endTime && !"".equals(endTime)) {
//            queryParams.put("endTime", endTime);
//        }
//        if (null != brand && !"".equals(brand)) {
//            queryParams.put("brand", brand);
//        }
//        if (null != orderNum && !"".equals(orderNum)) {
//            queryParams.put("orderNum", orderNum);
//        }
//        if (null != invoiceNo && !"".equals(invoiceNo)) {
//            queryParams.put("invoiceNo", invoiceNo);
//        }
//        String fileName = "DeliveryReport_" + CommonHelper.DateFormat(CommonHelper.getThisTimestamp(), "yyyy-MM-dd HH:mm:ss") + ".xlsx";
//        ByteArrayOutputStream bos = null;
//        OutputStream out = null;
//        try {
//            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
//            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
//            Workbook workbook = deliveryReportService.generateDeliveryReport(queryParams);
//            bos = new ByteArrayOutputStream();
//            workbook.write(bos);
//            out = response.getOutputStream();
//            out.write(bos.toByteArray());
//            out.flush();
//            isSuccess = "success";
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
//            try {
//                if (null != out) {
//                    out.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }


    @RequestMapping(value = "/order/generateFinanceReport.json", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult generateFinanceReport(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("Entry method downLoadList start!");
        Map<String, String> queryParams = new HashMap<String, String>();
        String startTime = request.getParameter("startTimeParam");
        String endTime = request.getParameter("endTimeParam");
        String alipayStartTime = request.getParameter("alipayStartTime1");
        String alipayEndTime = request.getParameter("alipayEndTime1");
        String brand = request.getParameter("brandParam");
        String orderNum = request.getParameter("orderNumParam");
        if (null != startTime && !"".equals(startTime)) {
            queryParams.put("startTime", startTime);
        }
        if (null != endTime && !"".equals(endTime)) {
            queryParams.put("endTime", endTime);
        }
        if (null != brand && !"".equals(brand)) {
            queryParams.put("storeId", brand);
        }
        if (null != orderNum && !"".equals(orderNum)) {
            queryParams.put("poNumber", orderNum);
        }
        if (!Validate.isNullOrEmpty(alipayStartTime)) {
            queryParams.put("alipayStartTime", alipayStartTime);
        }
        if (!Validate.isNullOrEmpty(alipayEndTime)) {
            queryParams.put("alipayEndTime", alipayEndTime);
        }
        String filePath = "";
        try {
            filePath = financeReportService.generateFinance(queryParams);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.jsonResult(filePath);
    }

    @RequestMapping(value = "/order/exportFinanceReport.json")
    public void downloadFinanceReport(HttpServletRequest request, HttpServletResponse response) throws Exception {
        long start = System.currentTimeMillis();

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


//    @RequestMapping(value = "/order/exportFinanceReport.json", method = RequestMethod.POST)
//    public void downloadFinanceReport(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        log.info("Entry method downLoadList start!");
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Map<String, String> queryParams = new HashMap<String, String>();
//        String startTime = request.getParameter("startTimeParam");
//        String endTime = request.getParameter("endTimeParam");
//        String brand = request.getParameter("brandParam");
//        String orderNum = request.getParameter("orderNumParam");
//        String isSuccess = "fail";
//        if (null != startTime && !"".equals(startTime)) {
//            queryParams.put("startTime", startTime);
//        }
//        if (null != endTime && !"".equals(endTime)) {
//            queryParams.put("endTime", endTime);
//        }
//        if (null != brand && !"".equals(brand)) {
//            queryParams.put("brand", brand);
//        }
//        if (null != orderNum && !"".equals(orderNum)) {
//            queryParams.put("orderNum", orderNum);
//        }
//        String fileName = "FinanceReport_" + CommonHelper.DateFormat(CommonHelper.getThisTimestamp(), "yyyy-MM-dd HH:mm:ss") + ".xlsx";
//        ByteArrayOutputStream bos = null;
//        OutputStream out = null;
//        try {
//            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
//            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
//            Workbook workbook = financeReportService.generateFinanceReport(queryParams);
//            bos = new ByteArrayOutputStream();
//            workbook.write(bos);
//            out = response.getOutputStream();
//            out.write(bos.toByteArray());
//            out.flush();
//            isSuccess = "success";
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
//            try {
//                if (null != out) {
//                    out.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }

//    @RequestMapping(value = "/order/exportOrderReport.json", method = RequestMethod.POST)
//    public void downloadOrderReport(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        try {
//            PrintWriter out = response.getWriter();
//            out.write("<table width=" + 300 + "px><tr><td>单位ID</td><td>人员代码</td><td>名称</td>");
//            for (int i = 0; i < 4; i++) {
//                out.write("<tr>");
//                out.write("<td>" + "1111" + "</td>");
//                out.write("<td>" + "2222" + "</td>");
//                out.write("<td>" + "3333" + "</td>");
//                out.write("</tr>");
//                out.write("</tr>");
//            }
//            out.write("</table>");
//            out.flush();
//            String isSuccess = "success";
//        } catch (Exception e) {
//            log.info(e.getMessage());
//        }
//    }

//    @RequestMapping(value = "/order/exportOrderReport.json", method = RequestMethod.POST)
//    public void downloadOrderReport(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        log.info("Entry method downLoadList start!");
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Map<String, Object> queryParams = new HashMap<String, Object>();
//        String startTime = request.getParameter("startTime1");
//        String endTime = request.getParameter("endTime1");
//        String brand = request.getParameter("brand1");
//        String orderNum = request.getParameter("orderNum1");
//        String isSuccess = "fail";
//        if (null != startTime && !"".equals(startTime)) {
//            queryParams.put("startTime", startTime);
//        }
//        if (null != endTime && !"".equals(endTime)) {
//            queryParams.put("endTime", endTime);
//        }
//        if (null != brand && !"".equals(brand)) {
//            queryParams.put("brand", brand);
//        }
//        if (null != orderNum && !"".equals(orderNum)) {
//            queryParams.put("orderNum", orderNum);
//        }
//        String fileName = "OrderReport" + CommonHelper.getThisTimestamp() + ".xls";
//        ByteArrayOutputStream bos = null;
//        try {
//            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
//            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
//
//            //获取订单数据
//            List<OrderReport> valueList = orderReportService.getOrderReportList(queryParams);
//
//            OrderReportExcel excel = new OrderReportExcel(valueList);
//
//            bos = (ByteArrayOutputStream) excel.getOutputStream();
//            OutputStream out = response.getOutputStream();
//            out.write(bos.toByteArray());
//            out.flush();
//            isSuccess = "success";
//        } catch (Exception e) {
//            log.info(e.getMessage());
//        } finally {
//            try {
//                if (null != bos) {
//                    bos.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }


    @RequestMapping(value = "/finance/generateRestFinance.json", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult generateRestFinance(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, String> queryParams = new HashMap<String, String>();
        String startTime = request.getParameter("startTime1");
        String endTime = request.getParameter("endTime1");
        String brand = request.getParameter("brand1");
        if (null != startTime && !"".equals(startTime)) {
            queryParams.put("startTime", startTime);
        }
        if (null != endTime && !"".equals(endTime)) {
            queryParams.put("endTime", endTime);
        }
        if (null != brand && !"".equals(brand)) {
            queryParams.put("brand", brand);
        }
        String filePath = "";
        try {
            filePath = restFinanceReportService.generateRestFinance(queryParams);
        } catch (Exception e) {
            log.info(e.getMessage());
            e.printStackTrace();
        }
        return this.jsonResult(filePath);
    }

    @RequestMapping(value = "/finance/exportRestFinance.json")
    public void downloadRestFinance(HttpServletRequest request, HttpServletResponse response) throws Exception {
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
        }
    }

//    @RequestMapping(value = "/finance/exportRestFinance.json", method = RequestMethod.POST)
//    public void downloadRestFinance(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        log.info("Entry method downLoadList start!");
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Map<String, String> queryParams = new HashMap<String, String>();
//        String startTime = request.getParameter("startTime1");
//        String endTime = request.getParameter("endTime1");
//        String brand = request.getParameter("brand1");
//        String isSuccess = "fail";
//        if (null != startTime && !"".equals(startTime)) {
//            queryParams.put("startTime", startTime);
//        }
//        if (null != endTime && !"".equals(endTime)) {
//            queryParams.put("endTime", endTime);
//        }
//        if (null != brand && !"".equals(brand)) {
//            queryParams.put("brand", brand);
//        }
//        String fileName = "RestFinanceReport_" + CommonHelper.DateFormat(CommonHelper.getThisTimestamp(), "yyyy-MM-dd HH:mm:ss") + ".xlsx";
//        ByteArrayOutputStream bos = null;
//        OutputStream out = null;
//        try {
//            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
//            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
//            Workbook workbook = restFinanceReportService.generateRestFinanceReport(queryParams);
//            bos = new ByteArrayOutputStream();
//            workbook.write(bos);
//            out = response.getOutputStream();
//            out.write(bos.toByteArray());
//            out.flush();
//            isSuccess = "success";
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
//            try {
//                if (null != out) {
//                    out.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }


    @RequestMapping(value = "/finance/exportFinanceQueryList")
    @ResponseBody
    public JsonResult exportAlipayQueryList(HttpServletRequest request, HttpServletResponse response) {
        Page<RestFinanceReport> page = PageHelper.getFromRequest(request);
        String brand = request.getParameter("brand");
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");

        Map<String, String> paymentMethodMap = new HashMap<String, String>();
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("brand", brand);
        queryParams.put("startTime", startTime);
        queryParams.put("endTime", endTime);

        Page<RestFinanceReport> valueList = restFinanceReportService.getFinanceQueryListByPage(page, queryParams);
        return extPageResult(valueList);
    }


    /**
     * 销售结构报表
     */
//    @RequestMapping(value = "download/areaSale")
//    public String downloadAreaSaleIndex() {
//        return "download/areaSale";
//    }

//    @RequestMapping(value = "/sale/downAreaSale.json", method = RequestMethod.POST)
//    public void downAreaSale(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        log.info("Entry method downLoadList start!");
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Map<String, Object> queryParams = new HashMap<String, Object>();
//        String startTime = request.getParameter("startTime1");
//        String endTime = request.getParameter("endTime1");
//        String brand = request.getParameter("brand1");
//        if (null != startTime && !"".equals(startTime)) {
//            queryParams.put("startTime", startTime);
//        }
//        if (null != endTime && !"".equals(endTime)) {
//            queryParams.put("endTime", endTime);
//        }
//        if (null != brand && !"".equals(brand)) {
//            queryParams.put("brand", brand);
//        }
//        String fileName = "ReginSale" + CommonHelper.getThisTimestamp() + ".xls";
//        ByteArrayOutputStream bos = null;
//        try {
//            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
//            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
//
//            bos = new ByteArrayOutputStream();
//            SXSSFWorkbook excel = areaSaleService.buildAreaSale();
//            excel.write(bos);
//            OutputStream out = response.getOutputStream();
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
//        }
//    }
}
