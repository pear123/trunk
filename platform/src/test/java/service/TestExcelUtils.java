//package service;
//
///**
// * Created with IntelliJ IDEA.
// * User: caol005
// * Date: 15-7-14
// * Time: 下午2:23
// * To change this template use File | Settings | File Templates.
// */
///*
// * 针对POI、JXL做性能测试，对于1W行以上的数据量，JXL无论从时间消耗还内存消耗均优于POI
// */
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.text.DecimalFormat;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import jxl.Cell;
//import jxl.Sheet;
//import jxl.Workbook;
//
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.apache.poi.hssf.usermodel.HSSFCell;
//import org.apache.poi.hssf.usermodel.HSSFCellStyle;
//import org.apache.poi.hssf.usermodel.HSSFDateUtil;
//import org.apache.poi.hssf.usermodel.HSSFFont;
//import org.apache.poi.hssf.usermodel.HSSFRow;
//import org.apache.poi.hssf.usermodel.HSSFSheet;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.poifs.filesystem.POIFSFileSystem;
//
//
//
///**
// *
// * @author amosryan
// * @version 2.0
// */
//// HSSFWorkbook excell 文档对象介绍
//// HSSFSheet excell的表单
//// HSSFRow excell的行
//// HSSFCell excell的格子单元
//// HSSFFont excell字体
//// HSSFName 名称
//// HSSFDataFormat 日期格式
//// 在poi1.7中才有以下2项：
//// HSSFHeader sheet头
//// HSSFFooter sheet尾
//// 和这个样式
//// HSSFCellStyle cell样式
//// 辅助操作包括
//// HSSFDateUtil 日期
//// HSSFPrintSetup 打印
//// HSSFErrorConstants 错误信息表
//public class TestExcelUtils {
//    private static Log logger = LogFactory.getLog(TestExcelUtils.class);
//
//    /**
//     * 从excel表中读数据，返回一个List数组(默认忽略第1行)
//     *
//     * @return
//     */
//    // public static List[] readExcel(File excelFile) {
//    // return readExcel(excelFile, 1);
//    // }
//
//    /**
//     * 使用POI从excel表中读数据，返回一个List<String[]>[]
//     *
//     * @return
//     */
//    @SuppressWarnings("unchecked")
//    public static List<String[]>[] readExcelByPOI(File excelFile,
//                                                  int ignoredRows) {
//        List<String[]>[] excelData = null;
//        FileInputStream fileInputStream = null;
//        try {
//            long st = System.currentTimeMillis();
//            fileInputStream = new FileInputStream(excelFile);
//            POIFSFileSystem poifs = new POIFSFileSystem(fileInputStream);
//            HSSFWorkbook wb = new HSSFWorkbook(poifs);
//            long et = System.currentTimeMillis();
//            System.out.println("加载消耗时间：" + (et - st) + "ms");
//            int sheetCount = wb.getNumberOfSheets();
//            excelData = new ArrayList[sheetCount];
//            for (int i = 0; i < sheetCount; i++) {
//                excelData[i] = new ArrayList<String[]>();
//                HSSFSheet sheet = wb.getSheetAt(i);
//                List<String[]> sheetData = new ArrayList<String[]>();
//                outter: for (int j = sheet.getFirstRowNum() + ignoredRows; j <= sheet
//                        .getLastRowNum(); j++) {
//                    // 根据第一行Title的列数设置默认每行的数据值数
//                    int columns = sheet.getRow(0).getLastCellNum();
//                    HSSFRow row = sheet.getRow(j);
//                    String[] rowData = new String[columns];
//                    for (short k = 0; k <= row.getLastCellNum() && k < columns; k++) {
//                        String value = "";
//                        HSSFCell cell = row.getCell(k);
//                        // 判断excel单元格类型
//                        if (cell != null) {
//                            switch (cell.getCellType()) {
//                                case HSSFCell.CELL_TYPE_STRING:
//                                    value = cell.getStringCellValue();
//                                    break;
//                                case HSSFCell.CELL_TYPE_NUMERIC:
//                                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
//                                        Date date = cell.getDateCellValue();
//                                        if (date != null) {
//                                            value = new SimpleDateFormat(
//                                                    "yyyy-MM-dd").format(date);
//                                        } else {
//                                            value = "";
//                                        }
//                                    } else {
//                                        value = new DecimalFormat("0").format(cell
//                                                .getNumericCellValue());
//                                    }
//                                    break;
//                                case HSSFCell.CELL_TYPE_FORMULA:
//                                    // 导入时如果为公式生成的数据则无值
//                                    if (!cell.getStringCellValue().equals("")) {
//                                        value = cell.getStringCellValue();
//                                    } else {
//                                        value = cell.getNumericCellValue() + "";
//                                    }
//                                    break;
//                                case HSSFCell.CELL_TYPE_BLANK:
//                                    break;
//                                case HSSFCell.CELL_TYPE_ERROR:
//                                    break;
//                                case HSSFCell.CELL_TYPE_BOOLEAN:
//                                    value = (cell.getBooleanCellValue() == true ? "Y"
//                                            : "N");
//                                    break;
//                                default:
//                                    value = "";
//                            }
//                            //
//                        }
//                        // 空行判定条件：第一格内容为空
//                        // 出现空行则此sheet读取结束
//                        // 出现这种情况的原因是getLastCellNum可能比实际数据行数偏大
//                        if (k == 0 && value.trim().equals("")) {
//                            break outter;
//                        }
//                        rowData[k] = value;
//                        if (logger.isDebugEnabled()) {
//                            logger.debug(i + "|" + j + "|" + k + "|"
//                                    + rowData[k]);
//                        }
//                    }
//                    sheetData.add(rowData);
//                }
//                excelData[i] = sheetData;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                fileInputStream.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        return excelData;
//    }
//
//    /**
//     * 使用JXL从excel表中读数据
//     *
//     * @return List<String[]>[]
//     */
//    @SuppressWarnings("unchecked")
//    public static List<String[]>[] readExcelByJXL(File excelFile,
//                                                  int ignoredRows) {
//        List<String[]>[] excelData = null;
//        FileInputStream fileInputStream = null;
//        try {
//            long st = System.currentTimeMillis();
//            fileInputStream = new FileInputStream(excelFile);
//            Workbook wb = Workbook.getWorkbook(fileInputStream);
//            long et = System.currentTimeMillis();
//            System.out.println("加载消耗时间：" + (et - st) + "ms");
//            int sheetCount = wb.getSheets().length;
//            excelData = new ArrayList[sheetCount];
//            for (int i = 0; i < sheetCount; i++) {
//                Sheet sheet = wb.getSheet(i);
//                List<String[]> sheetData = new ArrayList<String[]>();
//                // 取得sheet头上面包含有多少列
//                int nColumn = sheet.getColumns();
//                // 获得sheet总共有多少row
//                int nRow = sheet.getRows();
//                for (int j = ignoredRows; j < nRow; j++) {
//                    // 定义存放信息字符串的一行数组,并进行初始化
//                    String[] rowData = new String[nColumn];
//                    Cell[] cells = sheet.getRow(j);
//                    for (int k = 0; k < cells.length && k < nColumn; k++) {
//                        rowData[k] = cells[k].getContents(); // System.out.println(i+"|"+j+"|"+k+"|"+rowData[k]);
//                        if (logger.isDebugEnabled()) {
//                            logger.debug(i + "|" + j + "|" + k + "|"
//                                    + rowData[k]);
//                        }
//                    }
//                    sheetData.add(rowData);
//                }
//                excelData[i] = sheetData;
//            }
//            //wb.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                fileInputStream.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        return excelData;
//    }
//
//    /**
//     * 向excel中写数据，其中传入的excelData为一维数组，维数即为sheet数
//     * 每个元素中有包含一个list，其中元素String[]为每行数据
//     *
//     * @param excelData
//     */
//    public static void writeExcel(List<String[]>[] excelData, String[] sheetNames,
//                                  File outputFile) {
//        FileOutputStream fileOut = null;
//        try {
//            HSSFWorkbook wb = new HSSFWorkbook();
//            int sheetCount = excelData.length;
//            for (int i = 0; i < sheetCount; i++) {// 每个sheet
//                HSSFSheet sheet = wb.createSheet();
//                wb.setSheetName(i, sheetNames[i]);
//                int rowCount = excelData[i].size();
//                for (int j = 0; j < rowCount; j++) {// 每行
//                    String[] rowData = excelData[i].get(j);
//                    int columnCount = rowData.length;
//                    HSSFRow row = sheet.createRow(j);
//                    for (int k = 0; k < columnCount; k++) {// 每单元格
//                        HSSFCell cell = row.createCell((short) k);
//                        if (j == 0) {
//                            HSSFCellStyle style = wb.createCellStyle();
//                            HSSFFont font = wb.createFont();
//                            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
//                            style.setFont(font);
//                            // style.setWrapText(true); //文字自动换行
//                            cell.setAsActiveCell();
//                            cell.setCellStyle(style);
//                        }
//                        if (null != rowData[k]) {
//                            cell.setCellValue(rowData[k]);
//                        }
//                    }
//                }
//            }
//
//            // 若文件不存在则创建，
//            if (outputFile.exists()) {
//                outputFile.createNewFile();
//            }
//
//            fileOut = new FileOutputStream(outputFile);
//            wb.write(fileOut);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                fileOut.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public static void comparePOIandJXL() throws Exception {
//        List<String[]>[] listArray = null;
//        File f = new File("D://BSH//sample//Test1.xlsx");
//        Runtime rt = Runtime.getRuntime();
//        if (true) {
//            System.out.println("测试JXL！");
//            long st = System.currentTimeMillis();
//            long stm = rt.freeMemory();
//            listArray = TestExcelUtils.readExcelByJXL(f, 1);
//            long etm = rt.freeMemory();
//            long et = System.currentTimeMillis();
//            System.out.println("消耗时间：" + (et - st) + "ms");
//            System.out.println("消耗内存：" + ((stm - etm) / 1024 / 1024) + "M");
//        }
//        if (false) {
//            System.out.println("测试POI！");
//            long st = System.currentTimeMillis();
//            long stm = rt.freeMemory();
//            listArray = TestExcelUtils.readExcelByPOI(f, 1);
//            long etm = rt.freeMemory();
//            long et = System.currentTimeMillis();
//            System.out.println("消耗时间：" + (et - st) + "ms");
//            System.out.println("消耗内存：" + ((stm - etm) / 1024 / 1024) + "M");
//        }
//        int sum = 0;
//        for (int i = 0; i < listArray.length; i++) {
//            sum += listArray[i].size();
//        }
//        System.out.println("共有" + sum + "条数据。");
//        //writeExcel(listArray,new String[]{"noname"},new File("E://test.xls"));
//    }
//
//    public static void main(String[] args) {
//        try {
//            //JXL、POI性能对比测试
//            TestExcelUtils.comparePOIandJXL();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//}
//
//
