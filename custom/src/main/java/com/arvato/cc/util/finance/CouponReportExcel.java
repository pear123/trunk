package com.arvato.cc.util.finance;

import com.arvato.cc.form.finance.CouponReport;
import org.apache.poi.ss.usermodel.Row;

import java.util.List;

public class CouponReportExcel extends BaseReportExcel<CouponReport> {

	public CouponReportExcel(List<CouponReport> valueList) {
		super(valueList);
	}

	@Override
	protected void setTitle() {
		Row title = sheet.createRow(rowNum++);
		title.createCell(0).setCellValue("礼券类型");
		title.createCell(1).setCellValue("记录类型");
		title.createCell(2).setCellValue("券号");
		title.createCell(3).setCellValue("指向账户");
		title.createCell(4).setCellValue("有效期开始");
		title.createCell(5).setCellValue("有效期结束");
		title.createCell(6).setCellValue("金额");
        title.createCell(7).setCellValue("订单号");
        title.createCell(8).setCellValue("日期");
		title.createCell(9).setCellValue("费用承担方");
	}

	@Override
	protected void setValue() {
		for (CouponReport value : valueList) {
			Row row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(value.getCouponType());
			row.createCell(1).setCellValue(value.getStatus());
			row.createCell(2).setCellValue(value.getCouponNo());
			row.createCell(3).setCellValue(value.getUserId());
			row.createCell(4).setCellValue(formatTime(value.getStartTime()));
			row.createCell(5).setCellValue(formatTime(value.getEndTime()));
			row.createCell(6).setCellValue(formatDouble(value.getAmount()));
            row.createCell(7).setCellValue(value.getOrderNo());
            row.createCell(8).setCellValue(formatTime(value.getCouponTime()));
			row.createCell(9).setCellValue(value.getCostAccept());
		}
	}

}