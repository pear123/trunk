package com.arvato.cc.util.finance;

import com.arvato.cc.form.finance.QueryAccountReport;
import org.apache.poi.ss.usermodel.Row;

import java.util.List;

public class QueryAccountReportExcel extends BaseReportExcel<QueryAccountReport> {

    public QueryAccountReportExcel(List<QueryAccountReport> valueList) {
        super(valueList);
    }

    @Override
    protected void setTitle() {
        Row title = sheet.createRow(rowNum++);
        title.createCell(0).setCellValue("支付宝账号");
        title.createCell(1).setCellValue("账务流水号");
        title.createCell(2).setCellValue("业务流水号");
        title.createCell(3).setCellValue("商户订单号");
        title.createCell(4).setCellValue("商品信息");
        title.createCell(5).setCellValue("发生时间");
        title.createCell(6).setCellValue("对方账号");
        title.createCell(7).setCellValue("收入金额");
        title.createCell(8).setCellValue("支出金额");
        title.createCell(9).setCellValue("账户余额");
        title.createCell(10).setCellValue("交易渠道");
        title.createCell(11).setCellValue("业务类型");

        title.createCell(12).setCellValue("签约产品");
        title.createCell(13).setCellValue("费率");
        title.createCell(14).setCellValue("代发上传文件名");
        title.createCell(15).setCellValue("代发付款类型");

        title.createCell(16).setCellValue("备注");


//		title.createCell(1).setCellValue("账户余额");
//		title.createCell(2).setCellValue("账务流水号");
//		title.createCell(3).setCellValue("业务流水号");
//		title.createCell(4).setCellValue("商户订单号");
//		title.createCell(5).setCellValue("对方账号");
//		title.createCell(6).setCellValue("发生时间");
//		title.createCell(7).setCellValue("收入金额");
//		title.createCell(8).setCellValue("支出金额");
//		title.createCell(9).setCellValue("交易渠道");
//		title.createCell(10).setCellValue("业务类型");
//		title.createCell(11).setCellValue("商品信息");
//		title.createCell(12).setCellValue("备注");
    }

    @Override
    protected void setValue() {
        for (QueryAccountReport value : valueList) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(value.getSellerAccount());
            row.createCell(1).setCellValue(value.getIwAccountLogId());
            row.createCell(2).setCellValue(value.getTradeNo());
            row.createCell(3).setCellValue(value.getMerchantOutOrderNo());
            row.createCell(4).setCellValue(value.getGoodsTitle());
            row.createCell(5).setCellValue(formatTime(value.getTransDate()));
            row.createCell(6).setCellValue(value.getBuyerAccount());
            row.createCell(7).setCellValue(value.getIncome());
            row.createCell(8).setCellValue(value.getOutcome());
            row.createCell(9).setCellValue(value.getBalance());
            row.createCell(10).setCellValue(value.getTransAccount());
            row.createCell(11).setCellValue(value.getTransCodeMsg());

            if(value.getContractProduct()!=null && !"".equals(value.getContractProduct())){
                row.createCell(12).setCellValue(value.getContractProduct());
            }
            if(value.getDeductionRate()!=null && !"".equals(value.getDeductionRate())){
                row.createCell(13).setCellValue(value.getDeductionRate());
            }

            if(value.getProxyUploadFileName()!=null && !"".equals(value.getProxyUploadFileName())){
                row.createCell(14).setCellValue(value.getProxyUploadFileName());
            }

            if(value.getProxyDeductionType()!=null && !"".equals(value.getProxyDeductionType())){
                row.createCell(15).setCellValue(value.getProxyDeductionType());
            }

            row.createCell(16).setCellValue(value.getMemo());

//			row.createCell(0).setCellValue(value.getSellerAccount());
//			row.createCell(1).setCellValue(value.getBalance());
//			row.createCell(2).setCellValue(value.getIwAccountLogId());
//			row.createCell(3).setCellValue(value.getTradeNo());
//			row.createCell(4).setCellValue(value.getMerchantOutOrderNo());
//			row.createCell(5).setCellValue(value.getBuyerAccount());
//			row.createCell(6).setCellValue(formatTime(value.getTransDate()));
//			row.createCell(7).setCellValue(value.getIncome());
//			row.createCell(8).setCellValue(value.getOutcome());
//			row.createCell(9).setCellValue(value.getTransAccount());
//			row.createCell(10).setCellValue(value.getTransCodeMsg());
//			row.createCell(11).setCellValue(value.getGoodsTitle());
//			row.createCell(12).setCellValue(value.getMemo());
        }
    }

}