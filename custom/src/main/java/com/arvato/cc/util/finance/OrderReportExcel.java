package com.arvato.cc.util.finance;

import com.arvato.cc.form.finance.OrderReport;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Row;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ZHAN405
 * Date: 15-8-18
 * Time: 上午11:17
 * To change this template use File | Settings | File Templates.
 */
public class OrderReportExcel extends BaseReportExcel<OrderReport>{
    public OrderReportExcel(List<OrderReport> orderReportsList) {
        super(orderReportsList);
    }

    @Override
    protected void setTitle() {
        //共45列
        Row title = sheet.createRow(rowNum++);
        title.createCell(0).setCellValue("Sales Org.(SALESORG)");
        title.createCell(1).setCellValue("Distr. Channel(DISTRCHAN)");
        title.createCell(2).setCellValue("Sales Doc. Type(ORDERTYPE)");
        title.createCell(3).setCellValue("Customer(CUSTOMER)");
        title.createCell(4).setCellValue("PO number(CUSTORDNUM)");
        title.createCell(5).setCellValue("PO date(ORDERDATE)");
        title.createCell(6).setCellValue("Pricing date(PRICEDATE)");
        title.createCell(7).setCellValue("Partner Functn(PARVW)");
        title.createCell(8).setCellValue("ship to party(KUNNR)");
        title.createCell(9).setCellValue("Name(NAME)");
        title.createCell(10).setCellValue("Street(STREET)");
        title.createCell(11).setCellValue("City(CITY)");
        title.createCell(12).setCellValue("Postal Code(POSTL_COD1)");
        title.createCell(13).setCellValue("Partner Functn(PARVW)");
        title.createCell(14).setCellValue("Sold to party(KUNNR)");
        title.createCell(15).setCellValue("Name(NAME)");
        title.createCell(16).setCellValue("Street(STREET)");
        title.createCell(17).setCellValue("City(CITY)");
        title.createCell(18).setCellValue("Postal Code(POSTL_COD1)");
        title.createCell(19).setCellValue("Partner Functn(PARVW)");
        title.createCell(20).setCellValue("Bill to party(KUNNR)");
        title.createCell(21).setCellValue("Name(NAME)");
        title.createCell(22).setCellValue("Street(STREET)");
        title.createCell(23).setCellValue("City(CITY)");
        title.createCell(24).setCellValue("Postal Code(POSTL_COD1)");
        title.createCell(25).setCellValue("Partner Functn(PARVW)");
        title.createCell(26).setCellValue("Sold to party(KUNNR)");
        title.createCell(27).setCellValue("Name(NAME)");
        title.createCell(28).setCellValue("Street(STREET)");
        title.createCell(29).setCellValue("City(CITY)");
        title.createCell(30).setCellValue("Postal Code(POSTL_COD1)");
        title.createCell(31).setCellValue("Material(MATNR)");
        title.createCell(32).setCellValue("(QUANTITY)");
        title.createCell(33).setCellValue("T-Mall price(KSCHL)");
        title.createCell(34).setCellValue("Condition amount(KBETR)");
        title.createCell(35).setCellValue("KMA1 T-Mall points(KSCHL)");
        title.createCell(36).setCellValue("Condition amount(KBETR)");
        title.createCell(37).setCellValue("KMA6 T-Mall coupon(KSCHL)");
        title.createCell(38).setCellValue("Condition amount(KBETR)");
        title.createCell(39).setCellValue("PO archive No(TEXTID)");
        title.createCell(40).setCellValue("Text(TEXT)");
        title.createCell(41).setCellValue("Internal note(TEXTID)");
        title.createCell(42).setCellValue("Text(TEXT)");
        title.createCell(43).setCellValue("Billing text(TEXTID)");
        title.createCell(44).setCellValue("Text(TEXT)");
    }

    @Override
    protected void setValue() {
        if(null != valueList){
            for (OrderReport value : valueList) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(value.getSaleSorg());
                row.createCell(1).setCellValue(value.getDistrchan());
                row.createCell(2).setCellValue(value.getOrdertype());
                row.createCell(3).setCellValue(value.getCustomer());
                row.createCell(4).setCellValue(value.getCustOrdNum());
                row.createCell(5).setCellValue(value.getOrderDate());
                row.createCell(6).setCellValue(value.getPriceDate());
                row.createCell(7).setCellValue(value.getParvw());
                row.createCell(8).setCellValue(value.getKunnr());
                row.createCell(9).setCellValue(value.getName());
                row.createCell(10).setCellValue(value.getStreet());
                row.createCell(11).setCellValue(value.getCity());
                row.createCell(12).setCellValue(value.getPostalCode());
                row.createCell(13).setCellValue(value.getParvw1());
                row.createCell(14).setCellValue(value.getKunnr1());
                row.createCell(15).setCellValue(value.getName1());
                row.createCell(16).setCellValue(value.getStreet1());
                row.createCell(17).setCellValue(value.getCity1());
                row.createCell(18).setCellValue(value.getPostalCode1());
                row.createCell(19).setCellValue(value.getParvw2());
                row.createCell(20).setCellValue(value.getKunnr2());
                row.createCell(21).setCellValue(value.getName2());
                row.createCell(22).setCellValue(value.getStreet2());
                row.createCell(23).setCellValue(value.getCity2());
                row.createCell(24).setCellValue(value.getPostalCode2());
                row.createCell(25).setCellValue(value.getParvw3());
                row.createCell(26).setCellValue(value.getKunnr3());
                row.createCell(27).setCellValue(value.getName3());
                row.createCell(28).setCellValue(value.getStreet3());
                row.createCell(29).setCellValue(value.getCity3());
                row.createCell(30).setCellValue(value.getPostalCode3());
                row.createCell(31).setCellValue(value.getMatnr());
                row.createCell(32).setCellValue(value.getQuantity());
                row.createCell(33).setCellValue(value.getKschl());
                row.createCell(34).setCellValue(value.getKbetr());
                row.createCell(35).setCellValue(value.getKschl());
                row.createCell(36).setCellValue(value.getKbetr1());
                row.createCell(37).setCellValue(value.getKschl2());
                row.createCell(38).setCellValue(value.getKbetr2());
                row.createCell(39).setCellValue(value.getTextID());
                row.createCell(40).setCellValue(value.getText());
                row.createCell(41).setCellValue(value.getTextID1());
                row.createCell(42).setCellValue(value.getText1());
                row.createCell(43).setCellValue(value.getTextID2());
                row.createCell(44).setCellValue(value.getText2());
            }
        }
    }
}
