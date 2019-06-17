package com.arvato.cc.util.finance;

import com.arvato.cc.form.finance.RefundReport;
import org.apache.poi.ss.usermodel.Row;

import java.util.List;

public class GLRefundReportExcel extends BaseReportExcel<RefundReport> {

	public GLRefundReportExcel(List<RefundReport> valueList) {
		super(valueList);
	}

	@Override
	protected void setTitle() {
		Row title = sheet.createRow(rowNum++);
		title.createCell(0).setCellValue("退货时间");
		title.createCell(1).setCellValue("订单号(Hybris)");
		title.createCell(2).setCellValue("发货/退货单号(Watsons)");
		title.createCell(3).setCellValue("退款原因代码");
		title.createCell(4).setCellValue("客户ID");
		title.createCell(5).setCellValue("入库时间");
		title.createCell(6).setCellValue("退货商品包裹快递单号");
		title.createCell(7).setCellValue("退款原因描述");
		title.createCell(8).setCellValue("重量");
		title.createCell(9).setCellValue("收货地");
		title.createCell(10).setCellValue("赔付邮费");
		title.createCell(11).setCellValue("赔付邮费承担方");
		title.createCell(12).setCellValue("退款账户");
		title.createCell(13).setCellValue("退款处理状态");
		title.createCell(14).setCellValue("退款失败原因");
		title.createCell(15).setCellValue("退款时间");
	}

	@Override
	protected void setValue() {
		for (RefundReport value : valueList) {
			Row row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(value.getApplyReturnTime());
			row.createCell(1).setCellValue(value.getHybrisOrderNo());
			row.createCell(2).setCellValue(value.getEosNo());
			row.createCell(3).setCellValue(value.getReasonCode());
			row.createCell(4).setCellValue(value.getUserId()); 
			row.createCell(5).setCellValue(value.getReturnInTime());
			row.createCell(6).setCellValue(value.getReturnExpTrackingNum());
			row.createCell(7).setCellValue(value.getRefundReason());
			row.createCell(8).setCellValue(formatDouble(value.getReturnWeight()));
			row.createCell(9).setCellValue(value.getReceiveAddress());
			row.createCell(10).setCellValue(formatDouble(value.getPayPostage()));
			row.createCell(11).setCellValue(value.getPostageAccepter());
			row.createCell(12).setCellValue(value.getUserAccount());
			row.createCell(13).setCellValue(value.getRefundStatus());
			row.createCell(14).setCellValue(value.getRefundFailedReason());
			row.createCell(15).setCellValue(formatTime(value.getRefundTime()));
			
		}
	}
}