package com.arvato.cc.util.finance;

import com.arvato.cc.form.finance.FinanceReport;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ZHAN405
 * Date: 15-8-20
 * Time: 上午10:28
 * To change this template use File | Settings | File Templates.
 */
public class FinanceReportExcel extends BaseReportExcel<FinanceReport> {

    public FinanceReportExcel(List<FinanceReport> financeList) {
        super(financeList);
    }

    @Override
    protected void setTitle() {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFCellStyle style = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 10);
        style.setFont(font);
        Row title = sheet.createRow(rowNum++);
        title.createCell(0).setCellStyle(style);
        title.createCell(0).setCellValue("[VARIANT]");
        title.createCell(1).setCellValue("[DESCRIPTION]");
        title.createCell(2).setCellValue("BLDAT");
        title.createCell(3).setCellValue("BUDAT");
        title.createCell(4).setCellValue("BKPF");
        title.createCell(5).setCellValue("MONAT");
        title.createCell(6).setCellValue("BUKRS");
        title.createCell(7).setCellValue("WAERS");
        title.createCell(8).setCellValue("NEWBS");
        title.createCell(9).setCellValue("NEWKO");
        title.createCell(10).setCellValue("WRBTR");
        title.createCell(11).setCellValue("ZUONR");
        title.createCell(12).setCellValue("SGTXT");
        title.createCell(13).setCellValue("NEWBS1");
        title.createCell(14).setCellValue("NEWKO1");
        title.createCell(15).setCellValue("NAME1");
        title.createCell(16).setCellValue("ORT01");
        title.createCell(17).setCellValue("WRBTR1");
        title.createCell(18).setCellValue("ZUONR1");
        title.createCell(19).setCellValue("SGTXT1");
        title.createCell(20).setCellValue("NEWBS2");
        title.createCell(21).setCellValue("NEWKO2");
        title.createCell(22).setCellValue("WRBTR2");
        title.createCell(23).setCellValue("ZUONR2");
        title.createCell(24).setCellValue("SGTXT2");
        title = sheet.createRow(rowNum++);
        title.setRowStyle(style);
        title.createCell(0).setCellValue("*");
        rowNum++;
        title = sheet.createRow(rowNum++);
        title.setRowStyle(style);
        title.createCell(0).setCellValue("*ECATTDEFAULT");
    }


    @Override
    protected void setValue() {
        if (!CollectionUtils.isEmpty(valueList)) {
            for (FinanceReport value : valueList) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(2).setCellValue(value.getBldat());
                row.createCell(3).setCellValue(value.getBudat());
                row.createCell(4).setCellValue(value.getBkpf());
                row.createCell(5).setCellValue(value.getMonat());
                row.createCell(6).setCellValue(value.getBukrs());
                row.createCell(7).setCellValue(value.getWaers());
                row.createCell(8).setCellValue(value.getNewbs());
                row.createCell(9).setCellValue(value.getNewko());
                row.createCell(10).setCellValue(value.getWrbtr());
                row.createCell(11).setCellValue(value.getZuonr());
                row.createCell(12).setCellValue(value.getSgtxt());
                row.createCell(13).setCellValue(value.getNewbs1());
                row.createCell(14).setCellValue(value.getNewko1());
                row.createCell(15).setCellValue(value.getName1());
                row.createCell(16).setCellValue(value.getOrt01());
                row.createCell(17).setCellValue(value.getWrbtr1());
                row.createCell(18).setCellValue(value.getZuonr1());
                row.createCell(19).setCellValue(value.getSgtxt1());
                row.createCell(20).setCellValue(value.getNewbs2());
                row.createCell(21).setCellValue(value.getNewko2());
                row.createCell(22).setCellValue(value.getWrbtr2());
                row.createCell(23).setCellValue(value.getZuonr2());
                row.createCell(24).setCellValue(value.getSgtxt2());
            }
        }
    }
}
