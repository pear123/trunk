package com.arvato.cc.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public abstract class CommonHelper {

    private static Log log = LogFactory.getLog(CommonHelper.class);

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 返回当前系统时间
     *
     * @return
     */
    public static Timestamp getThisTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    /**
     * 返回(days)天前的日期
     * days为以前的天数
     */
    public static Timestamp getBeforeToday(int days) {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_YEAR) - days;
        calendar.set(Calendar.DAY_OF_YEAR, day);
        return new Timestamp(calendar.getTimeInMillis());
    }

    /**
     * 格式化日期(自定义格式)
     *
     * @param format("yyyyMMddHHmmss")
     * @param date
     * @return
     */
    public static String DateFormat(Timestamp date, String format) {
        if (Validate.isNullOrEmpty(date)) {
            return "";
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    /**
     * 格式化日期(默认)
     *
     * @param date
     * @return
     */
    public static String DateFormat(Timestamp date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }


    /**
     * 格式化日期时间(默认 yyyy-MM-dd HH:mm:ss)
     *
     * @param date
     * @return
     */
    public static Timestamp toTime(String date,String format) {

        if (Validate.isNullOrEmpty(date)) {
            return null;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        Timestamp timestamp = null;
        try {
            timestamp = new Timestamp(dateFormat.parse(date).getTime());
        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return timestamp;
    }

    /**
     * 格式化日期时间(默认)
     *
     * @param date
     * @return
     */
    public static String TimeFormat(Timestamp date) {
        if (date == null) return "";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date);
    }

    public static String ObjectToTime(Object date,String format) {
        if (date == null) return "";
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    /**
     * 单据号
     *
     * @param storeCode
     * @param code
     * @return
     */
    public static String getBillNumber(String storeCode, String code) {
        long current = System.currentTimeMillis();
        String currentTime = Long.toString(current);
        String date = CommonHelper.DateFormat(CommonHelper.getThisTimestamp(), "yyyyMMdd");
        String billNumber = storeCode + date + currentTime;
        return billNumber;
    }

    public static String getResult(Object result, Integer recordCount, String message, boolean b) {
        return "{'result':" + result + ",'recordCount':" + recordCount + ",'message':'" + message + "','success':" + b + "}";
    }

    /**
     * 格式化时间(用户高级查询)
     *
     * @param time
     * @return
     */
    public static String getTime(String time) {
        String[] ymd = time.split("-");
        String yearmonthday = "";
        String year = ymd[0].substring(2, 4);
        String month = "";
        String day = ymd[2];
        int monthInt = ymd[1].indexOf("0");
        if (monthInt == 0) {
            String[] monthValue = ymd[1].split("0");
            month = monthValue[1];
        } else {
            month = ymd[1];
        }
        yearmonthday = day + "-" + month + "月" + "-" + year;
        return yearmonthday;
    }

    public static Timestamp getTimestamp(Date date) {
        Timestamp tsTime = new Timestamp(date.getTime());
        return tsTime;
    }

    /**
     * 格式化时间(用户高级查询)
     *
     * @return
     */
    public static String getRandom() {
        String timeStr = new SimpleDateFormat("yyyyMMddHHmmsss").format(new Date());
        int randomStr = new Random().nextInt(9);
        String codeStr = timeStr + randomStr;
        return codeStr;
    }


    public static Double DoubleParse(Object value) {
        try {
            if (!Validate.isNullOrEmpty(value)) {
                DecimalFormat format = new DecimalFormat("##0.00");
                String str = format.format(Double.parseDouble(String.valueOf(value)));
                return Double.parseDouble(str);
//                return Double.parseDouble(value.toString());
            }
        } catch (Exception ex) {
            log.warn("value format error, in exception, return  0.00");
            return 0.000;
        }
        return 0.000;
    }

    public static Double DoubleParseOnePercent(Object value) {
        try {
            if (!Validate.isNullOrEmpty(value)) {
                DecimalFormat format = new DecimalFormat("##0.0");
                String str = format.format(Double.parseDouble(String.valueOf(value)));
                return Double.parseDouble(str);
//                return Double.parseDouble(value.toString());
            }
        } catch (Exception ex) {
            log.warn("value format error, in exception, return  0.0");
            return 0.0;
        }
        return 0.0;
    }

    public static Integer IntegerParse(Object value) {
        try {
            if (!Validate.isNullOrEmpty(value)) {
                DecimalFormat format = new DecimalFormat("##0");
                String str = format.format(Integer.parseInt(value.toString()));
                return Integer.parseInt(str);
//                return Integer.parseInt(value.toString());
            }
        } catch (Exception ex) {
            return 0;
        }
        return 0;
    }

    public static Timestamp TimeParse(Object value,String format) {
        try {
            if (!Validate.isNullOrEmpty(value)) {
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                return new Timestamp(sdf.parse(value.toString()).getTime());
            }
        } catch (Exception ex) {
            return null;
        }
        return null;
    }

    public static String TimeParseToStr(Object value,String format) {
        try {
            if (!Validate.isNullOrEmpty(value)) {
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                return sdf.format(sdf.parse(value.toString()));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
        return "";
    }

    public static Long longParse(Object value) {
        try {
            if (!Validate.isNullOrEmpty(value)) {
                Double doubleValue = Double.parseDouble(value.toString());
                return doubleValue.longValue();
//                return Integer.parseInt(value.toString());
            }
        } catch (Exception ex) {
            return 0L;
        }
        return 0L;
    }

    public static String getDoubleString(Object value) {
        try {
            if (!Validate.isNullOrEmpty(value)) {
                DecimalFormat format = new DecimalFormat("##0.00");
                return format.format(value);
//                return value.toString();
            }
        } catch (Exception ex) {
            return "0.00";
        }
        return "0.00";
    }

    public static String getIntegerString(Object value) {
        try {
            if (!Validate.isNullOrEmpty(value)) {
                DecimalFormat format = new DecimalFormat("##0");
                return format.format(value);
            }
        } catch (Exception ex) {
            return "0.00";
        }
        return "0.00";
    }

    public static String getString(Object obj) {
        if (Validate.isNullOrEmpty(obj) || "null".equals(obj)) {
            return "";
        }
        return obj.toString();
    }

    /**
     * 生成随机出库单号
     * Robin
     *
     * @return
     */
    public static String getStkout() {
        //声明变量，每次调用的时候都可以重新赋值，不会重复
        Random random = new Random();
        // 取随机产生的认证码(13位数字)
        String sRand = "";
        String stkOut = "STKO";
        for (int i = 0; i < 13; i++) {
            String rand = String.valueOf(random.nextInt(10));
            sRand += rand;
        }
        return stkOut + sRand;
    }

    /**
     * 异常的日志管理
     *
     * @param e1 模块中的异常对象
     * @return 异常字符串
     */
    public static String getExceptionMessage(Exception e1) {
        try {
            StringWriter sw = new StringWriter();
            PrintWriter w = new PrintWriter(sw);
            e1.printStackTrace(w);
            String result = "\n" + sw.getBuffer().toString();
            sw.close();
            w.close();
            return result;
        } catch (Exception e) {
            return "\n exception message util go wrong! " + e.toString();
        }
    }


    /**
     * 订单列表排序将列名转成sql的格式
     *
     * @param column
     * @return
     * @author caol001
     */
    public static String getOrderByColumn(String column) {
        StringBuffer sb = new StringBuffer("");
        char[] chars = column.toCharArray();
        for (char c : chars) {
            if (Character.isUpperCase(c)) {
                sb.append("_" + c);
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static String objToString(Object o) {
        if (Validate.isNullOrEmpty(o)) {
            return "";
        } else {
            return String.valueOf(o);
        }
    }

    public static String listToQuery(List<String> list) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < list.size(); i++) {
            buffer.append("'");
            buffer.append(list.get(i));
            buffer.append("'");

            if (i == list.size()) {
                buffer.append(",");
            }
        }
        return buffer.toString();
    }

    /**
     * 产生唯一UUID
     *
     * @return
     */
    public static String UUID() {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        BigInteger big1 = new BigInteger(uuid, 16);

        return big1.toString();
    }

    public static String getStkInNumber(String storeCode, String code) {
        String date = CommonHelper.DateFormat(CommonHelper.getThisTimestamp(), "yyyyMMddHHmmssm");
        String billNumber = storeCode + date;
        return billNumber;
    }

    // 从本地工作目录读取文件中的数据
    public static List<String> readContentLineFromLocal(String SFileName,
                                                        String localAddress) {
        String column = null;
        List<String> sBufferReader = new ArrayList<String>();
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    new FileInputStream(localAddress + SFileName), "8859_1"));

            while ((column = in.readLine()) != null) {
                sBufferReader.add(column);
            }
            in.close();
        } catch (IOException e) {
            return null;
        }
        return sBufferReader;
    }

    public static Double saveByScale(Double d, int scale) {
        BigDecimal bigDecimal = new BigDecimal(String.valueOf(d));
        return bigDecimal.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static Double saveByScale(Double d) {
        BigDecimal bigDecimal = new BigDecimal(String.valueOf(d));
        return bigDecimal.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static boolean isInSameWeek(Date start, Date end) {
        if (null == start || null == end) {
            return false;
        }
        Date temp = new Date();
        if (start.after(end)) {
            temp = start;
            start = end;
            end = temp;
        }

        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(start);

        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(end);

        startCalendar.setFirstDayOfWeek(Calendar.MONDAY);
        endCalendar.setFirstDayOfWeek(Calendar.MONDAY);
        //Get the relevant year.
        int startYear = startCalendar.get(Calendar.YEAR);
        int endYear = endCalendar.get(Calendar.YEAR);

        //Get the relevant month based on 1 by default.
        int startMonth = startCalendar.get(Calendar.MONTH) + 1;
        int endMonth = endCalendar.get(Calendar.MONTH) + 1;

        //Get the relevant day in a year.
        int startDay = startCalendar.get(Calendar.DAY_OF_YEAR);
        int endDay = endCalendar.get(Calendar.DAY_OF_YEAR);

        //Get the relevant day in a week.
        int startWeek = startCalendar.get(Calendar.DAY_OF_WEEK);
        int endWeek = endCalendar.get(Calendar.DAY_OF_WEEK);

        int startWeekInMonth = startCalendar.get(Calendar.WEEK_OF_YEAR);
        int endWeekInMonth = endCalendar.get(Calendar.WEEK_OF_YEAR);

        int disDay = endDay - startDay;
        int dis = endWeek - startWeek;

        boolean isSame;
        // If end year equals start year, go to the logic as below.
        if (startYear == endYear) {
            if (startWeekInMonth == endWeekInMonth && disDay >= 0 && disDay <= 7) {
                isSame = true;
            } else {
                isSame = false;
            }
        } else if ((endYear - startYear == 1) && (endMonth == 1 && startMonth == 12)) {
            if (startWeekInMonth == endWeekInMonth && dis >= 0 && dis <= 7) {
                isSame = true;
            } else {
                isSame = false;
            }
        } else {
            isSame = false;
        }

        return isSame;
    }

    public static Properties loadProperties(String fileName) {
        InputStream inputStream = CommonHelper.class.getClassLoader().getResourceAsStream(fileName);
        Properties p = new Properties();
        try {
            p.load(inputStream);
        } catch (IOException e) {
            log.error("load properties exception");
        }
        return p;
    }

    public static Timestamp parseFromString(String string, String format) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return new Timestamp(sdf.parse(string).getTime());
    }

    public static String encodeWithMD5(String psw, String salt) {
        Md5PasswordEncoder encoder = new Md5PasswordEncoder();
        return encoder.encodePassword(psw, salt);
    }

    public static String decodeWithMD5(String psw){
        return null;
    }

    public static String getDateString(Date date){
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        try {
            return df.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static double getAdd(double a,double b){
        BigDecimal b1=new BigDecimal(Double.toString(a));
        BigDecimal b2=new BigDecimal(Double.toString(b));
        return b1.add(b2).setScale(2).doubleValue();
    }

    public static double getSub(double a,double b){
        BigDecimal b1=new BigDecimal(Double.toString(a));
        BigDecimal b2=new BigDecimal(Double.toString(b));
        return b1.subtract(b2).setScale(3,BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static double getMul(double a,double b){
        BigDecimal b1=new BigDecimal(Double.toString(a));
        BigDecimal b2=new BigDecimal(Double.toString(b));
        return b1.multiply(b2).setScale(3).doubleValue();
    }

    public static double getDiv(double a,double b){
        BigDecimal b1=new BigDecimal(Double.toString(a));
        BigDecimal b2=new BigDecimal(Double.toString(b));
        return b1.divide(b2).setScale(3,BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static double getDouble(double a){
        BigDecimal b1=new BigDecimal(Double.toString(a));
        b1.setScale(2,BigDecimal.ROUND_HALF_UP);
        return b1.doubleValue();
    }

    public static int getMod(double a,double b){
        return new BigDecimal(a).remainder(new BigDecimal(b)).intValue();
    }

    public static double getModDouble(double a,double b){
        return new BigDecimal(a).remainder(new BigDecimal(b)).setScale(2,BigDecimal.ROUND_HALF_EVEN).doubleValue();
    }

    public static String getDateBeforeMonth(String currentTime,int number) {
        Calendar cal = Calendar.getInstance();
        Date date = stringToDate(currentTime);
        if(!Validate.isNullOrEmpty(date)) {
            cal.setTime(date);
            cal.add(Calendar.MONTH, -number);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd dd:mm:ss");
        String dateStr = sdf.format(cal.getTime());
        return dateStr;
    }

    public static String getDateAfterMonth(String currentTime,int number) {
        Calendar cal = Calendar.getInstance();
        Date date = stringToDate(currentTime);
        if(!Validate.isNullOrEmpty(date)) {
            cal.setTime(date);
            cal.add(Calendar.MONTH, number);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd dd:mm:ss");
        String dateStr = sdf.format(cal.getTime());
        return dateStr;
    }

    public static Date stringToDate(String currentTime){
        try
        {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
            Date date = sdf.parse(currentTime);
            return date;
        }
        catch (ParseException e)
        {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static Double objectToDouble(Object object){
        if(Validate.isNullOrEmpty(object)){
            return 0.00;
        }
        return Double.parseDouble(object.toString());
    }

    public static String objectToTime(Object obj,String format){
        if (obj == null) return "";
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(obj);
    }

    public static String getTrimString(String str){
        if(Validate.isNullOrEmpty(str)){
            return "";
        }
        return str.trim();
    }

}
