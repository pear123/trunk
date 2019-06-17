package com.arvato.cc.util.finance;

import com.arvato.cc.form.finance.SaleItemReport;
import org.apache.poi.ss.usermodel.Row;

import java.util.List;

public class SaleItemReportExcel extends BaseReportExcel<SaleItemReport> {

	public SaleItemReportExcel(List<SaleItemReport> list) {
		super(list);
	}

	@Override
	protected void setTitle() {
		Row title = sheet.createRow(rowNum++);
		title.createCell(0).setCellValue("发货时间");
        title.createCell(1).setCellValue("支付时间");
		title.createCell(2).setCellValue("Hybirs订单号");
		title.createCell(3).setCellValue("屈臣氏订单号");
        title.createCell(4).setCellValue("登陆名称");
        title.createCell(5).setCellValue("会员卡号");
        title.createCell(6).setCellValue("商品编码");
        title.createCell(7).setCellValue("商品名称");
        title.createCell(8).setCellValue("销售单价");
        title.createCell(9).setCellValue("销售数量");
        title.createCell(10).setCellValue("销售总金额");
        title.createCell(11).setCellValue("退货单价");
        title.createCell(12).setCellValue("退货数量");
        title.createCell(13).setCellValue("退货总金额");
        title.createCell(14).setCellValue("实际数量");
        title.createCell(15).setCellValue("实际金额");
	}

	@Override
	protected void setValue() {
        for (SaleItemReport value : valueList) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(formatTime(value.getShippingTime()));
            row.createCell(1).setCellValue(formatTime(value.getPaymentTime()));
            row.createCell(2).setCellValue(value.getSourceOrderNo());
            row.createCell(3).setCellValue(value.getOmsOrderNo());
            row.createCell(4).setCellValue(value.getLoginName());
            row.createCell(5).setCellValue(value.getCardNo());
            row.createCell(6).setCellValue(value.getItemSku());
            row.createCell(7).setCellValue(value.getItemName());
            row.createCell(8).setCellValue(formatDouble(value.getItemPrice()));
            row.createCell(9).setCellValue(formatInteger(value.getItemQuantity()));
            row.createCell(10).setCellValue(formatDouble(value.getItemTotal()));
            row.createCell(11).setCellValue(formatDouble(value.getItemReturnPrice()));
            row.createCell(12).setCellValue(formatInteger(value.getItemReturnQuantity()));
            row.createCell(13).setCellValue(formatDouble(value.getItemReturnTotal()));
            row.createCell(14).setCellValue(formatInteger(value.getRemaindQuantity()));
            row.createCell(15).setCellValue(formatDouble(value.getRemaindTotal()));
        }
	}

}
