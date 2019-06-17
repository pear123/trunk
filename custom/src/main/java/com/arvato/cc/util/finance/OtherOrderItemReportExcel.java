//package com.arvato.cc.util.finance;
//
//import com.arvato.cc.form.OrderItem;
//import org.apache.poi.hssf.usermodel.HSSFCell;
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.CellStyle;
//import org.apache.poi.ss.usermodel.DataFormat;
//import org.apache.poi.ss.usermodel.Row;
//
//import java.util.List;
//
///**
// * Created with IntelliJ IDEA.
// * User: zhan005
// * Date: 12-11-27
// * Time: 上午11:46
// * To change this template use File | Settings | File Templates.
// */
//public class OtherOrderItemReportExcel extends BaseReportExcel<OrderItem> {
//	public OtherOrderItemReportExcel(List<OrderItem> brokerageList) {
//		super(brokerageList);
//	}
//
//	@Override
//	protected void setTitle() {
//		Row title = sheet.createRow(rowNum++);
//		title.createCell(0).setCellValue("商品编码");
//		title.createCell(1).setCellValue("商品名称");
//		title.createCell(2).setCellValue("原价格");
//		title.createCell(3).setCellValue("销售价格");
//		title.createCell(4).setCellValue("数量");
//		title.createCell(5).setCellValue("折扣");
//		title.createCell(6).setCellValue("促销");
//		title.createCell(7).setCellValue("促销顺序");
//		title.createCell(8).setCellValue("促销ID");
//		title.createCell(9).setCellValue("促销名称");
//		title.createCell(10).setCellValue("重量");
//		title.createCell(11).setCellValue("行金额");
//	}
//
//	@Override
//	protected void setValue() {
//		CellStyle cellStyle = excel.createCellStyle();
//		DataFormat format = excel.createDataFormat();
//
//		cellStyle.setDataFormat(format.getFormat("#,##0.00"));
//
//		for (OrderItem value : valueList) {
//			Row row = sheet.createRow(rowNum++);
//			row.createCell(0).setCellValue(value.getItemSku());
//			row.createCell(1).setCellValue(value.getItemName());
//			Cell r2 = row.createCell(2);
//			if (null != value.getItemPrice()) {
//				r2.setCellValue(Double.valueOf(value.getItemPrice()));
//				r2.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
//				r2.setCellStyle(cellStyle);
//			} else {
//				r2.setCellValue(value.getItemPrice());
//			}
//
//			//row.createCell(2).setCellValue(value.getItemPrice());
//			//row.createCell(3).setCellValue(value.getItemAdjustment());
//			Cell r3 = row.createCell(3);
//			r3.setCellValue(Double.valueOf(value.getItemAdjustment()));
//			r3.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
//			r3.setCellStyle(cellStyle);
//
//			//row.createCell(4).setCellValue(value.getItemQuantity());
//			Cell r4 = row.createCell(4);
//			r4.setCellValue(Double.valueOf(value.getItemQuantity()));
//			r4.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
//			r4.setCellStyle(cellStyle);
//
//			row.createCell(5).setCellValue(value.getItemType());
//			row.createCell(6).setCellValue(value.getPromotionType());
//			row.createCell(7).setCellValue(value.getPromotionSque());
////			HSSFCell r7 = row.createCell(7);
////			if(null != value.getPromotionSque()){
////				r7.setCellValue(Integer.valueOf(value.getPromotionSque()));
////				r7.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
////				r7.setCellStyle(cellStyle);
////			}else {
////				r7.setCellValue(value.getPromotionSque());
////			}
//			row.createCell(8).setCellValue(value.getPromotionCode());
//			row.createCell(9).setCellValue(value.getPromotionDesc());
//			//row.createCell(10).setCellValue(value.getWeight());
//			Cell r10 = row.createCell(10);
//			if (null != value.getWeight()) {
//				r10.setCellValue(Double.valueOf(value.getWeight()));
//				r10.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
//				r10.setCellStyle(cellStyle);
//			} else {
//				r10.setCellValue(value.getWeight());
//			}
//			//row.createCell(11).setCellValue(value.getLienTotle());
//			Cell r11 = row.createCell(11);
//			if (null != value.getLienTotle()) {
//				r11.setCellValue(Double.valueOf(value.getLienTotle()));
//				r11.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
//				r11.setCellStyle(cellStyle);
//			} else {
//				r11.setCellValue(value.getLienTotle());
//			}
//		}
//	}
//
//}
