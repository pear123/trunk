package com.arvato.cc.util.finance;

import com.arvato.cc.form.finance.RefundReport;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;

import java.util.List;

public class RefundReportExcel extends BaseReportExcel<RefundReport> {

	public RefundReportExcel(List<RefundReport> valueList) {
		super(valueList);
	}

	@Override
	protected void setTitle() {
		Row title = sheet.createRow(rowNum++);
		title.createCell(0).setCellValue("退货时间");
		title.createCell(1).setCellValue("订单号(Hybris)");
		title.createCell(2).setCellValue("发货/退货单号(Watsons)");
		title.createCell(3).setCellValue("流水号(AOMS)");
		title.createCell(4).setCellValue("退款原因代码");
		title.createCell(5).setCellValue("客户ID");
		title.createCell(6).setCellValue("订单状态");
		title.createCell(7).setCellValue("退款类型");
		title.createCell(8).setCellValue("交易类型");
		title.createCell(9).setCellValue("促销ID");
		title.createCell(10).setCellValue("商品SKU");
		title.createCell(11).setCellValue("销售商品数量");
		title.createCell(12).setCellValue("退货商品SKU");
		title.createCell(13).setCellValue("退货商品数量");
		title.createCell(14).setCellValue("退款商品原含税金额");
		title.createCell(15).setCellValue("退款商品普通折扣");
		title.createCell(16).setCellValue("其他礼券");
		title.createCell(17).setCellValue("其他折扣");
		title.createCell(18).setCellValue("会员积分换购");
		title.createCell(19).setCellValue("是否需退还邮费");
		title.createCell(20).setCellValue("订单原邮费");
		title.createCell(21).setCellValue("需收回邮费");
		title.createCell(22).setCellValue("订单交易应退款总金额");
		title.createCell(23).setCellValue("实际收款时间");
		title.createCell(24).setCellValue("顾客付款方式");
		title.createCell(25).setCellValue("订单买家实付金额");
		title.createCell(26).setCellValue("实付金额是否大于应退款总金额");
		title.createCell(27).setCellValue("差异原因");
		title.createCell(28).setCellValue("申请退款时间");
		title.createCell(29).setCellValue("退货商品包裹快递单号");
		title.createCell(30).setCellValue("退回商品仓库收货入库情况");
		title.createCell(31).setCellValue("退款原因描述");
		title.createCell(32).setCellValue("重量");
		title.createCell(33).setCellValue("收货地");
		title.createCell(34).setCellValue("赔付邮费");
		title.createCell(35).setCellValue("赔付邮费承担方");
		title.createCell(36).setCellValue("退款账户");
		title.createCell(37).setCellValue("订单实际退款总金额");
		title.createCell(38).setCellValue("退款处理状态");
		title.createCell(39).setCellValue("退款失败原因");
		title.createCell(40).setCellValue("退款时间");
	}

	@Override
	protected void setValue() {
		CellStyle cellStyle=excel.createCellStyle();
		cellStyle.setWrapText(true);
		
		for (RefundReport value : valueList) {
			Row row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(formatTime(value.getApplyReturnTime()));
			row.createCell(1).setCellValue(value.getHybrisOrderNo());
			row.createCell(2).setCellValue(value.getEosNo());
			row.createCell(3).setCellValue(value.getOmsOrderNo());
			row.createCell(4).setCellValue(value.getReasonCode());
			row.createCell(5).setCellValue(value.getUserId());
			row.createCell(6).setCellValue(value.getOmsOrderStatus());
			row.createCell(7).setCellValue(value.getRefundType());
			row.createCell(8).setCellValue(value.getType());
			row.createCell(9).setCellValue(value.getPromotionIds());
			
			Cell r10 = row.createCell(10);
			r10.setCellStyle(cellStyle);
			r10.setCellValue(new HSSFRichTextString(value.getProductSKUs()));
			
			Cell r11 = row.createCell(11);
			r11.setCellStyle(cellStyle);
			r11.setCellValue(new HSSFRichTextString(value.getQuantitys()));
			
			Cell r12 = row.createCell(12);
			r12.setCellStyle(cellStyle);
			r12.setCellValue(value.getReturnProductSKUs());
			
			Cell r13 = row.createCell(13);
			r13.setCellStyle(cellStyle);
			r13.setCellValue(value.getReturnQuantitys());
			
			Cell r14 = row.createCell(14);
			r14.setCellStyle(cellStyle);
			r14.setCellValue(value.getReturnProductAmount());

			row.createCell(15).setCellValue(formatDouble(value.getReturnPromotion()));
			row.createCell(16).setCellValue(formatDouble(value.getReturnVoucher()));
			row.createCell(17).setCellValue(formatDouble(value.getOtherPromotion()));
			row.createCell(18).setCellValue(formatDouble(value.getMemberIntegralAmount()));
			row.createCell(19).setCellValue(value.getReturnPostage());
			row.createCell(20).setCellValue(formatDouble(value.getPostage()));
			row.createCell(21).setCellValue(formatDouble(value.getRetrievePostage()));
			row.createCell(22).setCellValue(formatDouble(value.getRefundAmount()));
			row.createCell(23).setCellValue(formatTime(value.getReallyReceiveMoneyTime()));
			row.createCell(24).setCellValue(value.getPspType());
			row.createCell(25).setCellValue(formatDouble(value.getUserReallyPayAmount()));
			row.createCell(26).setCellValue(value.getRefundAmountPayAmountDifferent());
			row.createCell(27).setCellValue(value.getDifferentReason());
			row.createCell(28).setCellValue(value.getReturnInTime());
			row.createCell(29).setCellValue(value.getReturnExpTrackingNum());
			row.createCell(30).setCellValue(value.getReturnInStatus());
			row.createCell(31).setCellValue(value.getRefundReason());
			row.createCell(32).setCellValue(formatDouble(value.getReturnWeight()));
			row.createCell(33).setCellValue(value.getReceiveAddress());
			row.createCell(34).setCellValue(formatDouble(value.getPayPostage()));
			row.createCell(35).setCellValue(value.getPostageAccepter());
			row.createCell(36).setCellValue(value.getUserAccount());
			row.createCell(37).setCellValue(formatDouble(value.getReallyRefundAmount()));
			row.createCell(38).setCellValue(value.getRefundStatus());
			row.createCell(39).setCellValue(value.getRefundFailedReason());
			row.createCell(40).setCellValue(formatTime(value.getRefundTime()));
		}
	}

}