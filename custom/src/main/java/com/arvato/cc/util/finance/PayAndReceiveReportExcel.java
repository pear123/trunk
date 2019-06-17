package com.arvato.cc.util.finance;

import com.arvato.cc.form.finance.PayAndReceiveReport;
import org.apache.poi.ss.usermodel.Row;

import java.util.List;

public class PayAndReceiveReportExcel extends BaseReportExcel<PayAndReceiveReport> {

	public PayAndReceiveReportExcel(List<PayAndReceiveReport> valueList) {
		super(valueList);
	}

	@Override
	protected void setTitle() {
		Row title = sheet.createRow(rowNum++);
		title.createCell(0).setCellValue("顾客交易付款时间");
		title.createCell(1).setCellValue("顾客付款方式");
		title.createCell(2).setCellValue("发货/退货时间");
		title.createCell(3).setCellValue("是否交易当周发货");
		title.createCell(4).setCellValue("订单号(Hybris)");
		title.createCell(5).setCellValue("发货/退货单号(Watsons)");
		title.createCell(6).setCellValue("退货流水号(AOMS)");
		title.createCell(7).setCellValue("行类型");
		title.createCell(8).setCellValue("订单状态");
		title.createCell(9).setCellValue("交易类型");
		title.createCell(10).setCellValue("退款类型");
		title.createCell(11).setCellValue("备注");
		title.createCell(12).setCellValue("促销ID");
		title.createCell(13).setCellValue("原因代码");
		title.createCell(14).setCellValue("礼券类型");
		title.createCell(15).setCellValue("使用券号");
		title.createCell(16).setCellValue("商品原含税销售金额");
		title.createCell(17).setCellValue("普通折扣");
		title.createCell(18).setCellValue("其他礼券");
		title.createCell(19).setCellValue("其他折扣");
		title.createCell(20).setCellValue("会员积分换购");
		title.createCell(21).setCellValue("邮费");
		title.createCell(22).setCellValue("应收顾客商品交易金额");
		title.createCell(23).setCellValue("其中免税商品销售促销折后含税销售金额");
		title.createCell(24).setCellValue("免税商品其他礼券");
		title.createCell(25).setCellValue("非免税商品其它礼券");
		title.createCell(26).setCellValue("免税商品其他折扣");
		title.createCell(27).setCellValue("非免税商品其他折扣");
		title.createCell(28).setCellValue("免税商品会员积分换购");
		title.createCell(29).setCellValue("非免税商品会员积分换购");
		title.createCell(30).setCellValue("需收回邮费");
		title.createCell(31).setCellValue("顾客应付金额");
		title.createCell(32).setCellValue("应收款时间");
		title.createCell(33).setCellValue("实际收款时间");
		title.createCell(34).setCellValue("银行到账时间");
		title.createCell(35).setCellValue("超期收款天数");
		title.createCell(36).setCellValue("实收交易金额");
		title.createCell(37).setCellValue("交易应收差异金额");
		title.createCell(38).setCellValue("预提手续费");
		title.createCell(39).setCellValue("实付手续费");
		title.createCell(40).setCellValue("应付手续费差异金额");
	}

	@Override
	protected void setValue() {
		for (PayAndReceiveReport value : valueList) {
			Row row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(formatTime(value.getOrderPayTime()));
			row.createCell(1).setCellValue(value.getPspType());
			row.createCell(2).setCellValue(value.getDeliverOrReturnInTime());
			row.createCell(3).setCellValue(value.getInSameWeekDeliver());
			row.createCell(4).setCellValue(value.getHybrisOrderNo());
			row.createCell(5).setCellValue(value.getEosNo());
			row.createCell(6).setCellValue(value.getOmsOrderNo());
			row.createCell(7).setCellValue(value.getRowType());
			row.createCell(8).setCellValue(value.getOrderStatus());
			row.createCell(9).setCellValue(value.getTradeType());
			row.createCell(10).setCellValue(value.getRefundType());
			row.createCell(11).setCellValue(value.getRemark());
			row.createCell(12).setCellValue(value.getPromotionIds());
			row.createCell(13).setCellValue(value.getReasonCode());
			row.createCell(14).setCellValue(value.getCouponType());
			row.createCell(15).setCellValue(value.getCouponNo());
			row.createCell(16).setCellValue(formatDouble(value.getOriginalOrderAmount()));
			row.createCell(17).setCellValue(formatDouble(value.getPromotion()));
			row.createCell(18).setCellValue(formatDouble(value.getVoucher()));
			row.createCell(19).setCellValue(formatDouble(value.getOtherPromotion()));
			row.createCell(20).setCellValue(formatDouble(value.getMemberIntegralAmount()));
			row.createCell(21).setCellValue(formatDouble(value.getPostage()));
			row.createCell(22).setCellValue(formatDouble(value.getTradeAmount()));
			row.createCell(23).setCellValue(formatDouble(value.getNoTaxProductsAmount()));
			row.createCell(24).setCellValue(formatDouble(value.getNoTaxProductsOtherVoucher()));
			row.createCell(25).setCellValue(formatDouble(value.getProductsOtherVoucher()));
			row.createCell(26).setCellValue(formatDouble(value.getNoTaxProductsOtherPromotion()));
			row.createCell(27).setCellValue(formatDouble(value.getProductsOtherPromotion()));
			row.createCell(28).setCellValue(formatDouble(value.getNoTaxProductsUseIntegral()));
			row.createCell(29).setCellValue(formatDouble(value.getProductsUseIntegral()));
			row.createCell(30).setCellValue(formatDouble(value.getRetrievePostage()));
			row.createCell(31).setCellValue(formatDouble(value.getUserPayAmount()));
			row.createCell(32).setCellValue(formatTime(value.getReceiveMoneyTime()));
			row.createCell(33).setCellValue(formatTime(value.getReallyReceiveMoneyTime()));
			row.createCell(34).setCellValue(formatTime(value.getBankReceiveMoneyTime()));
			row.createCell(35).setCellValue(value.getReceiveOvertime());
			row.createCell(36).setCellValue(formatDouble(value.getReallyReceiveAmount()));
			row.createCell(37).setCellValue(formatDouble(value.getReceiveAmountDiff()));
			row.createCell(38).setCellValue(formatDouble(value.getPspBrokerage()));
			row.createCell(39).setCellValue(formatDouble(value.getReallyPpspBrokerage()));
			row.createCell(40).setCellValue(formatDouble(value.getPspBrokerageDiff()));
		}
	}

}