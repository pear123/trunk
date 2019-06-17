package com.arvato.cc.util;


import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("unchecked")
public class Validate {

	public static boolean isNullOrEmpty(Long obj) {
		return (obj == null || obj == 0L);
	}
	
	//add by neal 6-29
	public static boolean isNullOrEmpty(Double obj) {
		return (obj == null || obj == 0d);
	}
	
	public static boolean isNullOrEmpty(Object obj) {
		return (obj == null);
	}
	
	public static boolean isNullOrEmpty(Integer obj) {
		return (obj == null || obj == 0);
	}

	public static boolean isNullOrEmpty(List obj) {
		return (obj == null || obj.isEmpty());
	}
	
	public static boolean isNullOrEmpty(Set obj){
		return (obj == null || obj.isEmpty());
	}
	
	public static boolean isNullOrEmpty(Map obj) {
        return (obj == null || obj.isEmpty());
    }
	
	public static boolean isNullOrEmpty(String obj){
		return (obj == null || obj.isEmpty() || "".equals(obj));
	}
	
	public static boolean isNullOrEmpty(Object[] objs){
		return (objs == null || objs.length == 0);
	}
	
	public static boolean zhengshuValidate(String number) {// 判断 正整数的格式
		Pattern pattern = Pattern.compile("^\\d+$");
		Matcher mc = pattern.matcher(number);
		return mc.matches(); 
		}
	
	public static boolean isBooleanFlag(String str) {
        return ("0".equals(str) || "1".equals(str));
    }
	
	public static boolean isLogicFlag(String str) {
        return !("+".equals(str) || "".equals(str));
    }
	
	public static boolean numberValidation(String str) {
	    String pattern = "^\\d+$";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(str);
        if(m.matches()){
           return true; 
        }else{
            return false;
        }
    }
	
	public static boolean isPositiveNumber(String str) {//验证正数
        String pattern = "^\\d+(\\.\\d+)?$";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(str);
        return m.matches();
    }
	
	public static boolean isPhone(String str) {//验证手机
        String pattern = "^1[3|4|5|8][0-9]\\d{4,8}$";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(str);
        return m.matches();
    }
	
	public static boolean isMobile(String str) {//验证电话
        String pattern = "^(0[0-9]{2,3}\\-)?([2-9][0-9]{6,7})$";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(str);
        return m.matches();
    }
	
	public static boolean isCodeNoBlankline(String str) {//验证编码，支持英文，数字，不含特殊字符，大小写不敏感
        String pattern = "^[0-9|a-z|A-Z]+$";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(str);
        return m.matches();
    }
	
	public static boolean isZip(String str)
    {
	    String pattern = "^[1-9][0-9]{5}$";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(str);
        return m.matches();
    }
	
	public static void main(String[] args)
    {
//        System.out.println(isCodeNoBlankline("asdZa9"));
    }

    
}
