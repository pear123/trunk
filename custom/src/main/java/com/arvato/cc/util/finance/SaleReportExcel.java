package com.arvato.cc.util.finance;

import com.arvato.cc.form.finance.SaleReport;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Row;

import java.util.List;

public class SaleReportExcel extends BaseReportExcel<SaleReport> {

    public SaleReportExcel(List<SaleReport> saleReportList) {
        super(saleReportList);
    }

    @Override
    protected void setTitle() {
        Row title = sheet.createRow(rowNum++);
        title.createCell(0).setCellValue("Today Time");
        title.createCell(1).setCellValue("Today Sale Value");
        title.createCell(2).setCellValue("Today Transaction");
        title.createCell(3).setCellValue("Yesterday Time");
        title.createCell(4).setCellValue("Yesterday Sale Value");
        title.createCell(5).setCellValue("Yesterday Transaction");
    }

    @Override
    protected void setValue() {
        for (SaleReport value : valueList) {
            Row row = sheet.createRow(rowNum++);
            if (StringUtils.isNotBlank(value.getTodayHour())) {
                row.createCell(0).setCellValue(value.getTodayHour());
            }
            if (null != value.getTodaySaleAmount()) {
                row.createCell(1).setCellValue(value.getTodaySaleAmount());
            }
            if (null != value.getTodaySaleTransaction()) {
                row.createCell(2).setCellValue(value.getTodaySaleTransaction());
            }
            if (StringUtils.isNotBlank(value.getYesterdayHour())) {
                row.createCell(3).setCellValue(value.getYesterdayHour());
            }
            if (null != value.getYesterdaySaleAmount()) {
                row.createCell(4).setCellValue(value.getYesterdaySaleAmount());
            }
            if (null != value.getYesterdaySaleTransaction()) {
                row.createCell(5).setCellValue(value.getYesterdaySaleTransaction());
            }
        }
    }

}
