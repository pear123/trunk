package com.arvato.cc.util.finance;

import com.arvato.cc.form.SaleStructureBean;
import com.arvato.cc.util.Validate;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;

import java.util.List;

public class AreaSaleReportExcel extends BaseReportExcel<SaleStructureBean> {
    //自定义起始行
    private int rowNum = 1;

    //自定义其实列
    private int columnNum = 1;

    //报表名称
    private String reportName = "";

    public AreaSaleReportExcel(List<SaleStructureBean> saleStructureBeanList) {
        super(saleStructureBeanList);
    }

    @Override
    protected void setTitle() {
        Row title = sheet.createRow(rowNum++);
        getCellWithBorder(getCell(title.createCell(0)),HSSFColor.BLUE.index).setCellValue("No.");
        getCellWithBorder(getCell(title.createCell(1)),HSSFColor.BLUE.index).setCellValue("VIB");
        getCellWithBorder(getCell(title.createCell(2)),HSSFColor.BLUE.index).setCellValue("QTY");
        getCellWithBorder(getCell(title.createCell(3)),HSSFColor.BLUE.index).setCellValue("Value");
        getCellWithBorder(getCell(title.createCell(4)),HSSFColor.BLUE.index).setCellValue("AVP");
        getCellWithBorder(getCell(title.createCell(5)),HSSFColor.BLUE.index).setCellValue("");
        getCellWithBorder(getCell(title.createCell(6)),HSSFColor.BLUE.index).setCellValue("Qty Share");
        getCellWithBorder(getCell(title.createCell(7)),HSSFColor.BLUE.index).setCellValue("Value Share");
        getCellWithBorder(getCell(title.createCell(8)),HSSFColor.BLUE.index).setCellValue("");
        getCellWithBorder(getCell(title.createCell(9)),HSSFColor.BLUE.index).setCellValue("Supply Price");
        getCellWithBorder(getCell(title.createCell(10)),HSSFColor.BLUE.index).setCellValue("Orignal Value");
        getCellWithBorder(getCell(title.createCell(11)),HSSFColor.BLUE.index).setCellValue("Discount Rate");
    }

    @Override
    protected void setValue() {
        for (SaleStructureBean value : valueList) {
            Row row = sheet.createRow(rowNum++);
            getCellWithBorder(getCell(row.createCell(0)),HSSFColor.BLUE.index).setCellValue(value.getNum());
            getCellWithBorder(getCell(row.createCell(1)),HSSFColor.RED.index).setCellValue(value.getMatnr());
            getCellWithBorder(getCell(row.createCell(2)),HSSFColor.BLACK.index).setCellValue(value.getSaleQuantity());
            getCellWithBorder(getCell(row.createCell(3)),HSSFColor.BLACK.index).setCellValue(value.getSaleAmount());
            getCellWithBorder(getCell(row.createCell(4)),HSSFColor.BLACK.index).setCellValue(value.getSaleAvg());
            getCellWithBorder(getCell(row.createCell(5)),HSSFColor.BLACK.index).setCellValue("");
            getCellWithBorder(getCell(row.createCell(6)),HSSFColor.BLACK.index).setCellValue(value.getSaleQuantityPercentage());
            getCellWithBorder(getCell(row.createCell(7)),HSSFColor.BLACK.index).setCellValue(value.getSaleAmountPercentage());
            getCellWithBorder(getCell(row.createCell(8)),HSSFColor.BLACK.index).setCellValue("");
            getCellWithBorder(getCell(row.createCell(9)),HSSFColor.BLACK.index).setCellValue(value.getSapPrice());
            getCellWithBorder(getCell(row.createCell(10)),HSSFColor.BLACK.index).setCellValue(value.getSapAmount());
            getCellWithBorder(getCell(row.createCell(11)),HSSFColor.BLACK.index).setCellValue(value.getSapDiscount());
        }
    }
    /**
     * 创建单元格
     */
    private Cell getCell(Cell cell){
        cell.setCellStyle(style);
        return cell;
    }

    /**
     * 单元格细边框
     * @param cell
     * @return
     */
    private Cell getCellWithBorder(Cell cell,short color) {
        CellStyle cellStyle = cell.getCellStyle();
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBottomBorderColor(color);
        cellStyle.setLeftBorderColor(color);
        cellStyle.setRightBorderColor(color);
        cellStyle.setTopBorderColor(color);
        cell.setCellStyle(cellStyle);
        return cell;
    }

    /**
     * 单元格字体和颜色
     * @param cell
     * @param fontName
     * @param fontSize
     * @param fontWeight
     * @param fontColor
     * @return
     */
    private Cell getCellWithFontAndColor(Cell cell,String fontName,int fontSize,boolean fontWeight,short fontColor){
        XSSFFont font = (XSSFFont) excel.createFont();
        CellStyle cellStyle = cell.getCellStyle();
        if(!Validate.isNullOrEmpty(fontName)){
            font.setFontName(fontName);
        }
        if(!Validate.isNullOrEmpty(fontSize)){
            font.setFontHeightInPoints((short)fontSize);
        }
        if(fontWeight){
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        }
        if(!Validate.isNullOrEmpty(fontColor)){
            font.setColor(fontColor);//HSSFColor.GREEN.index
        }
        cellStyle.setFont(font);
        cell.setCellStyle(cellStyle);
        return cell;
    }

    //背景色
    private Cell getCellWithBackgroundColor(Cell cell,int color){
        CellStyle cellStyle = cell.getCellStyle();
        cellStyle.setFillForegroundColor(HSSFColor.LIME.index);
        cellStyle.setFillBackgroundColor(HSSFColor.GREEN.index);
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cell.setCellStyle(cellStyle);
        return cell;
    }

}
