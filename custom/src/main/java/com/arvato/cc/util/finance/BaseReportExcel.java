package com.arvato.cc.util.finance;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public abstract class BaseReportExcel<T> {
    protected SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    protected DecimalFormat df = new DecimalFormat("##.00");
    protected SXSSFWorkbook excel;

    //protected HSSFSheet sheet;
    protected Sheet sheet;

    protected List<T> valueList;

    protected CellStyle style;

    protected int rowNum = 0;

    public BaseReportExcel(List<T> valueList) {
        excel = new SXSSFWorkbook();
        sheet = excel.createSheet();
        style = excel.createCellStyle();
        this.valueList = valueList;
    }

    public OutputStream getOutputStream() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        buildExcel();
        excel.write(bos);
        return bos;
    }

    protected void buildExcel() {
        setTitle();
        setValue();
    }

    protected abstract void setTitle();

    protected abstract void setValue();

    protected String formatTime(Date time) {
        if (time != null) {
            return sdf.format(time);
        } else {
            return null;
        }
    }

    protected Double formatDouble(Double d) {
        if (d != null) {
            return Double.parseDouble(df.format(d));
        } else {
            return 0.00;
        }
    }

    protected Integer formatInteger(Integer d) {
        if (d != null) {
            return Integer.valueOf(d);
        } else {
            return 0;
        }
    }
}
