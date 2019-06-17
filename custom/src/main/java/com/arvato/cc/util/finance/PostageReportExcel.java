package com.arvato.cc.util.finance;

import com.arvato.cc.form.finance.PostageReport;
import org.apache.poi.ss.usermodel.Row;

import java.util.List;

public class PostageReportExcel extends BaseReportExcel<PostageReport> {

	public PostageReportExcel(List<PostageReport> valueList) {
		super(valueList);
	}

	@Override
	protected void setTitle() {
		Row title = sheet.createRow(rowNum++);
		title.createCell(0).setCellValue("快递单号");
		title.createCell(1).setCellValue("订单号(Hybris)");
		title.createCell(2).setCellValue("发货/退货单号(Watsons)");
		title.createCell(3).setCellValue("流水号(AOMS)");
		title.createCell(4).setCellValue("发货日期");
		title.createCell(5).setCellValue("快递商");
		title.createCell(6).setCellValue("发货地");
		title.createCell(7).setCellValue("收货地");
		title.createCell(8).setCellValue("包裹重量");
		title.createCell(9).setCellValue("首重邮费");
		title.createCell(10).setCellValue("续重邮费");
		title.createCell(11).setCellValue("包裹单费用");
		title.createCell(12).setCellValue("应付包裹总邮费");
		title.createCell(13).setCellValue("备注");
	}

	@Override
	protected void setValue() {
		for (PostageReport value : valueList) {
			Row row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(value.getExpTrackingNum());
			row.createCell(1).setCellValue(value.getHybrisOrderNo());
			row.createCell(2).setCellValue(value.getEosNo());
			row.createCell(3).setCellValue(value.getOmsOrderNo());
			row.createCell(4).setCellValue(formatTime(value.getDeliverTime()));
			row.createCell(5).setCellValue(value.getDeliveryVendor());
			row.createCell(6).setCellValue(value.getWarehouseAddress());
			row.createCell(7).setCellValue(value.getUserAddress());
			row.createCell(8).setCellValue(formatDouble(value.getDeliveryWeight()));
			row.createCell(9).setCellValue(formatDouble(value.getFirstAmount()));
			row.createCell(10).setCellValue(formatDouble(value.getContinueAmount()));
			row.createCell(11).setCellValue(formatDouble(value.getPackageAmount()));
			row.createCell(12).setCellValue(formatDouble(value.getTotalAmount()));
			row.createCell(13).setCellValue(value.getRemark());
		}
	}

}