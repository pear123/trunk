package com.arvato.cc.util.finance;

import com.arvato.cc.form.finance.BrokerageReport;
import com.arvato.cc.form.finance.RestFinanceReport;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.List;

public class RestFinanceReportExcel extends BaseReportExcel<RestFinanceReport> {

    public RestFinanceReportExcel(List<RestFinanceReport> restFinanceList) {
        super(restFinanceList);
    }

    @Override
    protected void setTitle() {
        Row title = sheet.createRow(rowNum++);
        title.createCell(0).setCellValue("账务流水号");
        title.createCell(1).setCellValue("商户订单号");
        title.createCell(2).setCellValue("商品名称");
        title.createCell(3).setCellValue("发生时间");
        title.createCell(4).setCellValue("对方账号");
        title.createCell(5).setCellValue("收入金额（+元）");
        title.createCell(6).setCellValue("支出金额（-元）");
        title.createCell(7).setCellValue("账户余额（元）");
        title.createCell(8).setCellValue("交易渠道");
        title.createCell(9).setCellValue("业务类型");
        title.createCell(10).setCellValue("备注");
    }

    @Override
    protected void setValue() {
        if(!CollectionUtils.isEmpty(valueList)){
            for (RestFinanceReport value : valueList) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(value.getFinancialSerialNum());
                row.createCell(1).setCellValue(value.getOrderNum());
                row.createCell(2).setCellValue(value.getGoodsName());
                row.createCell(3).setCellValue(formatTime(value.getCreateTime()));
                row.createCell(4).setCellValue(value.getAccount());
                row.createCell(5).setCellValue(value.getInFee());
                row.createCell(6).setCellValue(value.getOutFee());
                row.createCell(7).setCellValue(value.getBalance());
                row.createCell(8).setCellValue(value.getMode());
                row.createCell(9).setCellValue(value.getType());
                row.createCell(10).setCellValue(value.getMemo());
            }
        }
    }
}
