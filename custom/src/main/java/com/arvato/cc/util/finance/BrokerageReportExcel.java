package com.arvato.cc.util.finance;

import com.arvato.cc.form.finance.BrokerageReport;
import org.apache.poi.ss.usermodel.Row;

import java.util.List;

public class BrokerageReportExcel extends BaseReportExcel<BrokerageReport> {

    public BrokerageReportExcel(List<BrokerageReport> brokerageList) {
        super(brokerageList);
    }

    @Override
    protected void setTitle() {
        Row title = sheet.createRow(rowNum++);
        title.createCell(0).setCellValue("订单号(Hybris)");
        title.createCell(1).setCellValue("发货/退货单号(Watsons)");
        title.createCell(2).setCellValue("流水号(AOMS)");
        title.createCell(3).setCellValue("发货日期");
        title.createCell(4).setCellValue("订单交易日期");
        title.createCell(5).setCellValue("顾客确认收货日期");
        title.createCell(6).setCellValue("实际收款时间");
        title.createCell(7).setCellValue("银行到账时间");
        title.createCell(8).setCellValue("收款方式");
        title.createCell(9).setCellValue("支付宝(银联/财付通)实收款+");
        title.createCell(10).setCellValue("支付宝(银联/财付通)实扣款-");
        title.createCell(11).setCellValue("支付宝(银联/财付通)扣款费率");
        title.createCell(12).setCellValue("支付宝(银联/财付通)应扣款");
        title.createCell(13).setCellValue("支付宝(银联/财付通)实扣/应扣差异");
        title.createCell(14).setCellValue("支付宝(银联/财付通)扣款类型");
        //title.createCell(15).setCellValue("客服商应付佣金");
    }

    @Override
    protected void setValue() {
        for (BrokerageReport value : valueList) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(value.getHybrisOrderNo());
            row.createCell(1).setCellValue(value.getEosNo());
            row.createCell(2).setCellValue(value.getOmsOrderNo());
            row.createCell(3).setCellValue(formatTime(value.getDeliverTime()));
            row.createCell(4).setCellValue(formatTime(value.getHybrisOrderTradeTime()));
            row.createCell(5).setCellValue(formatTime(value.getUserConfirmReceiveTime()));
            row.createCell(6).setCellValue(formatTime(value.getReallyReceiveMoneyTime()));
            row.createCell(7).setCellValue(formatTime(value.getBankReceiveMoneyTime()));
            row.createCell(8).setCellValue(value.getPspType());
            row.createCell(9).setCellValue(value.getPayAmount());
            row.createCell(10).setCellValue(value.getReallyPspBrokerage());
            row.createCell(11).setCellValue(value.getPspRate());
            row.createCell(12).setCellValue(value.getPspBrokerage());
            row.createCell(13).setCellValue(value.getPspBrokerageDifferent());
            row.createCell(14).setCellValue(value.getPspProductType());
            //row.createCell(15).setCellValue(formatDouble(value.getCsBrokerage()));


        }
    }

}
