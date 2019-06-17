package com.arvato.cc.util.finance;

import com.arvato.cc.form.finance.TopProductReport;
import org.apache.poi.ss.usermodel.Row;

import java.util.List;

public class TopProductReportExcel extends BaseReportExcel<TopProductReport> {

	public TopProductReportExcel(List<TopProductReport> list) {
		super(list);
	}

	@Override
	protected void setTitle() {
		Row title = sheet.createRow(rowNum++);
		title.createCell(0).setCellValue("item Code");
		title.createCell(1).setCellValue("item Description");
		title.createCell(2).setCellValue("sale Qty");
        title.createCell(3).setCellValue("sale Value");
	}

	@Override
	protected void setValue() {
        for (TopProductReport value : valueList) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(value.getSku());
            row.createCell(1).setCellValue(value.getName());
            row.createCell(2).setCellValue(value.getQuantity());
            row.createCell(3).setCellValue(value.getPrice());
        }
	}

}
