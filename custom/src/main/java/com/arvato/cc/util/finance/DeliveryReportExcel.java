package com.arvato.cc.util.finance;

import com.arvato.cc.form.finance.DeliveryReport;
import org.apache.poi.ss.usermodel.Row;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ZHAN405
 * Date: 15-8-19
 * Time: 下午12:48
 * To change this template use File | Settings | File Templates.
 */
public class DeliveryReportExcel extends BaseReportExcel<DeliveryReport>{
    public DeliveryReportExcel(List<DeliveryReport> deliveryReportsList) {
        super(deliveryReportsList);
    }

    @Override
    protected void setTitle() {
        //共53列
        Row title = sheet.createRow(rowNum++);
        title.createCell(0).setCellValue("XX天猫旗舰店发票和赠品寄送清单");
        title = sheet.createRow(rowNum++);
        title.createCell(0).setCellValue("日期：2015年4月13号");
        rowNum++;
        title = sheet.createRow(rowNum++);
        title.createCell(0).setCellValue("订单号");
        title.createCell(1).setCellValue("运单号");
        title.createCell(2).setCellValue("子单号");
        title.createCell(3).setCellValue("收方地址");
        title.createCell(4).setCellValue("收方联系电话");
        title.createCell(5).setCellValue("收方联系人");
        title.createCell(6).setCellValue("收方公司");
        title.createCell(7).setCellValue("收方手机号码");
        title.createCell(8).setCellValue("打印次数");
        title.createCell(9).setCellValue("托寄物内容");
        title.createCell(10).setCellValue("托寄物数量");
        title.createCell(11).setCellValue("寄件公司");
        title.createCell(12).setCellValue("寄件联系地址");
        title.createCell(13).setCellValue("寄件联系电话");
        title.createCell(14).setCellValue("寄件联系人");
        title.createCell(15).setCellValue("原寄地");
        title.createCell(16).setCellValue("目的地");
        title.createCell(17).setCellValue("计费重量");
        title.createCell(18).setCellValue("实际重量");
        title.createCell(19).setCellValue("收派员工号");
        title.createCell(20).setCellValue("寄件日期");
        title.createCell(21).setCellValue("备注");
        title.createCell(22).setCellValue("业务类型");
        title.createCell(23).setCellValue("是否电商加急");
        title.createCell(24).setCellValue("付款方式");
        title.createCell(25).setCellValue("是否代收货款");
        title.createCell(26).setCellValue("代收货款卡号");
        title.createCell(27).setCellValue("代收金额");
        title.createCell(28).setCellValue("签单返还单号");
        title.createCell(29).setCellValue("其它费用");
        title.createCell(30).setCellValue("件数");
        title.createCell(31).setCellValue("是否保价");
        title.createCell(32).setCellValue("保价金额");
        title.createCell(33).setCellValue("是否自取");
        title.createCell(34).setCellValue("是否签回单");
        title.createCell(35).setCellValue("是否定时派送");
        title.createCell(36).setCellValue("派送日期");
        title.createCell(37).setCellValue("派送时段");
        title.createCell(38).setCellValue("运费");
        title.createCell(39).setCellValue("长（cm）");
        title.createCell(40).setCellValue("宽（cm）");
        title.createCell(41).setCellValue("高（cm）");
        title.createCell(42).setCellValue("扩展字段1");
        title.createCell(43).setCellValue("扩展字段2");
        title.createCell(44).setCellValue("扩展字段3");
        title.createCell(45).setCellValue("寄件时间");
        title.createCell(46).setCellValue("收件状态");
        title.createCell(47).setCellValue("用户名");
        title.createCell(48).setCellValue("客户ID");
        title.createCell(49).setCellValue("发票抬头");
        title.createCell(50).setCellValue("开票金额");
        title.createCell(51).setCellValue("礼品物料号");
        title.createCell(52).setCellValue("礼品数量");
    }

    @Override
    protected void setValue() {
        if(null != valueList){
            for (DeliveryReport value : valueList) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(value.getOrderId());
                row.createCell(3).setCellValue(value.getReceiverAddress());
                row.createCell(4).setCellValue(value.getReceiverMobile());
                row.createCell(5).setCellValue(value.getReceiverName());
                row.createCell(6).setCellValue(value.getReceiverCompany());
                row.createCell(7).setCellValue(value.getReceiverPhone());
                row.createCell(9).setCellValue(value.getSendContent());
                row.createCell(10).setCellValue(value.getSendNum());
                row.createCell(48).setCellValue(value.getCustomerID());
                row.createCell(49).setCellValue(value.getInvoiceTitle());
                row.createCell(50).setCellValue(value.getInvoiceFee());
                row.createCell(51).setCellValue(value.getSkuNos());
                row.createCell(52).setCellValue(value.getGiftNum());
            }
        }
    }
}
