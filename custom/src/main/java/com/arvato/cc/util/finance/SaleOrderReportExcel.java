package com.arvato.cc.util.finance;

import com.arvato.cc.form.finance.SaleOrderReport;
import com.arvato.jdf.util.MessageHelper;
import org.apache.poi.ss.usermodel.Row;

import java.util.List;

public class SaleOrderReportExcel extends BaseReportExcel<SaleOrderReport> {

	public SaleOrderReportExcel(List<SaleOrderReport> list) {
		super(list);
	}

	@Override
	protected void setTitle() {
		Row title = sheet.createRow(rowNum++);
		title.createCell(0).setCellValue("发货时间");
		title.createCell(1).setCellValue("Hybirs订单号");
		title.createCell(2).setCellValue("屈臣氏订单号");
        title.createCell(3).setCellValue("登陆名称");
        title.createCell(4).setCellValue("会员卡号");
        title.createCell(5).setCellValue("买家支付宝账号");
        title.createCell(6).setCellValue("订单状态");
        title.createCell(7).setCellValue("顾客实付商品金额");
        title.createCell(8).setCellValue("顾客实付运费金额");
        title.createCell(9).setCellValue("订单创建日期");
        title.createCell(10).setCellValue("订单创建时间");
        title.createCell(11).setCellValue("订单付款日期");
        title.createCell(12).setCellValue("订单付款时间");
        title.createCell(13).setCellValue("收件人姓名");
        title.createCell(14).setCellValue("收货人地址");
        title.createCell(15).setCellValue("联系电话");
        title.createCell(16).setCellValue("联系手机");
        title.createCell(17).setCellValue("快递供应商");
	}

	@Override
	protected void setValue() {
        MessageHelper messageHelper = null;
        for (SaleOrderReport value : valueList) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(formatTime(value.getShippingTime()));
            row.createCell(1).setCellValue(value.getSourceOrderNo());
            row.createCell(2).setCellValue(value.getOmsOrderNo());
            row.createCell(3).setCellValue(value.getLoginName());
            row.createCell(4).setCellValue(value.getCardNo());
            row.createCell(5).setCellValue(value.getAlipayNo());
            //订单状态
            messageHelper = new MessageHelper();
            String internalStatus = messageHelper.getString("order.Messages.status."+value.getInternalStatus());
            row.createCell(6).setCellValue(internalStatus);

            row.createCell(7).setCellValue(formatDouble(value.getProductTotal()));
            row.createCell(8).setCellValue(formatDouble(value.getShippingTotal()));
            row.createCell(9).setCellValue(value.getCreateTimeDay());
            row.createCell(10).setCellValue(value.getCreateTimeHour());
            row.createCell(11).setCellValue(value.getPaymentTimeDay());
            row.createCell(12).setCellValue(value.getPaymentTimeHour());
            row.createCell(13).setCellValue(value.getConsumer());
            row.createCell(14).setCellValue(value.getAddress());
            row.createCell(15).setCellValue(value.getPhone());
            row.createCell(16).setCellValue(value.getMobile());
            row.createCell(17).setCellValue(value.getShippingMethod());
        }
	}

}
