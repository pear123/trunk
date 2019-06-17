package com.arvato.cc.util;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileUtil {
	public static boolean renameFile(String srcname, String destname) {
		if (!FileUtil.isFileExist(srcname)) {
			return false;
		}
		
		try {
			File srcfile = new File(srcname);
            destname += srcname.substring(srcname.lastIndexOf('/'),srcname.lastIndexOf('.')) + CommonHelper.DateFormat(CommonHelper.getThisTimestamp(),"yyyMMddhhmmss")
                    + srcname.substring(srcname.lastIndexOf('.') ,srcname.length());
			File destfile = new File(destname);
			File path=new File(destfile.getPath());
			if(!path.exists()){
				path.mkdirs();
			}
			if (destfile.exists()) {
				destfile.delete();
			}
			return srcfile.renameTo(destfile);
		} catch (Exception e) {
			System.out.println("error while rename the file" + e);
		}
		return false;
	}


    public static boolean copyFile(String fileFrom, String fileTo) {
        try {
            FileInputStream in = new java.io.FileInputStream(fileFrom);
            FileOutputStream out = new FileOutputStream(fileTo);
            byte[] bt = new byte[1024];
            int count;
            while ((count = in.read(bt)) > 0) {
                out.write(bt, 0, count);
            }
            in.close();
            out.close();
            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }


	public static boolean removeFile(String file) {
		boolean ok = false;
		try {
			File f = new File(file);
			if (f.exists())
				f.delete();
			ok = true;
		} catch (Exception e) {
			System.out.println("ERROR DELETING " + file);
			e.printStackTrace();
		}
		return ok;
	}

	public static boolean isFileExist(String filename) {
		try {
			if ((filename == null) || ("".equals(filename.trim())))
				return false;
			File f1 = new File(filename);
			if (f1.exists()) {
				f1 = null;
				return true;
			}

		} catch (Exception e) {
		}
		return false;

	}
	
	public static String fileToString(String src) {
		StringBuffer sbuf = new StringBuffer();
		try
		{
			File inputFile = new File(src);
			FileInputStream in = new FileInputStream(inputFile);
			int c;
			while ((c = in.read()) != -1) sbuf.append((char) c);
			in.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return "";
		}
		return sbuf.toString();
	}

	public static boolean stringToFile(String filename, String s) {
		if (s == null) return true;
		if (! s.equals("")) {
			try {
				File f = new File(filename);
				BufferedWriter msgout = new BufferedWriter(new FileWriter(f));
				msgout.write(s);
				msgout.close();
				return true;
			}
			catch (Exception e) { e.printStackTrace(); return false; }
		}
		return true;
	}	

    /***
     * getExcelData
     * @param in
     * @param column：data columns
     * @param rowNum：start row
     * @return
     * @throws IOException
     */
    public static Map<Integer, Map<Integer, List<String>>> readExcel(FileInputStream in,int column,int rowNum,int ci,String fileType)
            throws IOException {
        Map<Integer, Map<Integer, List<String>>> map = new HashMap<Integer, Map<Integer, List<String>>>();// 总map
        Map<Integer, List<String>> sheetMap = null;// 每个sheet的map
        List<String> list = null;// 每行一个list
        Workbook workBook = null;
        try {
            if("xls".equals(fileType))
                workBook = new HSSFWorkbook(in);
            else
                workBook = new XSSFWorkbook(in);
        } catch (final Exception e) {
            throw new IOException("读取上传文件失败");
        }
        //获得Excel中工作表个数
        int sheetSize = workBook.getNumberOfSheets();
        for (int i = 0; i < sheetSize; i++) {
            sheetMap = new HashMap<Integer, List<String>>();
            Sheet sheet = workBook.getSheetAt(i);
            int rows = sheet.getPhysicalNumberOfRows(); // 获得行数
            if (rows > 0) {
                for (int j = rowNum; j <= rows +1 ; j++) { // 行循环
                    list = new ArrayList<String>();
                    Row row = sheet.getRow(j);
                    if (row != null) {
                        int cells = row.getLastCellNum();// 获得列数
                        for (int k = ci ; k < column; k++) { // 列循环
                            list.add(getCellContent(row.getCell(k)));
                        }
                        if(!isAllNull(list)){
                            sheetMap.put(j, list);
                        }
                    }
                }
            }
            map.put(i, sheetMap);
        }
        return map;
    }

    public static boolean generateExcelTemplates(String sourceFilePath,String exportFilePath){
        FileInputStream fis =null;
        FileOutputStream out=null;
        try {
            fis = new java.io.FileInputStream(sourceFilePath);
           // POIFSFileSystem fs = new POIFSFileSystem(in);
            out = new FileOutputStream(exportFilePath);

           String type=sourceFilePath.substring(sourceFilePath.indexOf("."));

            Workbook workbook=null;
            if("xls".equals(type)){
                workbook = new HSSFWorkbook(fis);
            }else{
                workbook = new XSSFWorkbook(fis);
            }
            Sheet sheet=workbook.getSheetAt(0);
            Cell cell=sheet.createRow(1).createCell(3);
//            Cell cell = sheet.getRow(1).getCell(3);
            cell.setCellValue("测试");
            workbook.write(out);

            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            try {
                fis.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }

    public static Workbook getWorkBookByExample(String sourceFilePath){
        InputStream fis =null;
        try {
            fis = FileUtil.class.getResourceAsStream(sourceFilePath);

            String type=sourceFilePath.substring(sourceFilePath.indexOf(".") + 1);

            if("xls".equals(type)){
                return new HSSFWorkbook(fis);
            }else{
                return new XSSFWorkbook(fis);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    public static void main(String args[]) throws IOException{
        FileUtil.generateExcelTemplates("D:\\OrderReport1.xlsx","D:\\OrderReport20150827_burce2.xlsx");
    }



    /**
     * Generate Excel line String list by parameters.
     * @param filePath
     * @param columns
     * @param startRowNum
     * @param startColNum
     * @param fileType
     * @return
     */
    public List<List<String>> getExcelLineStringList(String filePath,int columns,int startRowNum,int startColNum,String fileType){
        FileInputStream fis = null;
        Sheet sheet=null;
        try{
            fis = new FileInputStream(filePath);
            if("xls".equals(fileType)){
                sheet = new HSSFWorkbook(fis).getSheetAt(0);
            }else{
                sheet = new XSSFWorkbook(fis).getSheetAt(0);
            }
            if (sheet.getPhysicalNumberOfRows() >= startRowNum) {
                return getLineStringList(sheet,columns,startRowNum,startColNum);
            }
        }catch (IOException ex) {
            ex.printStackTrace();
        }finally {
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return  new ArrayList<List<String>>();
    }

    /**
     * Load line & column value to List
     * @param sheet
     * @param columns
     * @param startRowNum
     * @param startColNum
     * @return
     */
    public static List<List<String>> getLineStringList(Sheet sheet,int columns,int startRowNum,int startColNum){
        List<String> array = null;
        List<List<String>> lineList = new ArrayList<List<String>>();
        for (int j = startRowNum; j < sheet.getPhysicalNumberOfRows() +startRowNum ; j++) { // 行循环
            array = new ArrayList<String>();
            if (sheet.getRow(j) != null) {
                for (int k = startColNum ; k < columns + startColNum; k++) { // 列循环
                    array.add(getCellContent(sheet.getRow(j).getCell(k)));
                }
            }
            lineList.add(array);
        }
        return lineList;
    }


    /**
     * Return String cell content Data by Cell
     * @param cell
     * @return
     */
    public static String getCellContent(Cell cell){
        String value = "";
        if(cell != null){
            switch (cell.getCellType()) {
                case HSSFCell.CELL_TYPE_NUMERIC: // 数值型
                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
                        // 如果是date类型则 ，获取该cell的date值
                        value = CommonHelper.getDateString(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
                    } else {// 纯数字
                        value= new java.text.DecimalFormat("########.##").format(cell.getNumericCellValue());
                    }
                    break;
                case HSSFCell.CELL_TYPE_STRING: // 字符串型
                    value = cell.getRichStringCellValue().toString().trim();
                    break;
                case HSSFCell.CELL_TYPE_FORMULA:// 公式型
                    // 读公式计算值
                    value = String.valueOf(cell.getNumericCellValue());
                    if (value.equals("NaN")) {// 如果获取的数据值为非法值,则转换为获取字符串
                        value = cell.getRichStringCellValue().toString();
                    }
                    break;
                case HSSFCell.CELL_TYPE_BOOLEAN:// 布尔
                    value = "" + cell.getBooleanCellValue();
                    break;
                                    /* 此行表示该单元格值为空 */
                case HSSFCell.CELL_TYPE_BLANK: // 空值
                    value = "";
                    break;
                case HSSFCell.CELL_TYPE_ERROR: // 故障
                    value = "";
                    break;
                default:
                    value = cell.getRichStringCellValue().toString().trim();
            }
        }
        return value;
    }



    /**
     * 如果list里面的值全为空 则范围true 反之则为false
     * @param l list
     * @return
     */
    public static boolean isAllNull(List<String> l){
        int i=0;
        for(String s : l){
            if(!"".equals(s)){
                i++;
            }
        }
        if(i>0){
            return false;
        }
        return true;
    }

    public static boolean validateTemplate(List<String> title,List<String> contents){
        if(Validate.isNullOrEmpty(title)){
            return false;
        }
        if(Validate.isNullOrEmpty(contents)){
            return false;
        }
        if(!title.equals(contents)){
            return false;
        }
        return true;
    }

    public static CellStyle getCellStyle(Workbook workbook,int row,int cell){
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.cloneStyleFrom(workbook.getSheet("style").getRow(row).getCell(cell).getCellStyle());
        return cellStyle;
    }

    public static void setCellStyle(Sheet sheet,int row ,int cell,CellStyle cellStyle){
        sheet.getRow(row).getCell(cell).setCellStyle(cellStyle);
    }
}
