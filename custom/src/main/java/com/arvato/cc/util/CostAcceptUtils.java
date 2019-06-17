package com.arvato.cc.util;

import java.util.HashMap;
import java.util.Map;

public class CostAcceptUtils {
	public static final String  ACCEPT_BUYER = "买家";
	public static final String  ACCEPT_DELIVERY = "快递公司";
	public static final String  ACCEPT_WATSONS = "Watsons";
	public static final String  ACCEPT_NONE = "/";
	
	public static final String  TYPE_RETURN = "退货";
	public static final String  TYPE_CHANGE = "重发货(换货)";
	public static final String  TYPE_REFUSE = "屈臣氏拒绝退货或换货";
	
	private static Map<String, Map<String, String[]>> map = new HashMap<String, Map<String, String[]>>();	
	
	static {
		//该map根据《退货/重发货-各方成本承担对照表》创建
		//String[]为5列，分别为：商品价值，佣金，原送货费，买家退回送货费，商家重发货单送货费
		Map<String, String[]> returnMap = new HashMap<String, String[]>();
		String[] returnR001 = {ACCEPT_WATSONS,ACCEPT_WATSONS,ACCEPT_WATSONS,ACCEPT_WATSONS,ACCEPT_NONE}; 
		String[] returnR002 = {ACCEPT_DELIVERY,ACCEPT_WATSONS,ACCEPT_DELIVERY,ACCEPT_NONE,ACCEPT_NONE}; 
		String[] returnR003 = {ACCEPT_DELIVERY,ACCEPT_WATSONS,ACCEPT_DELIVERY,ACCEPT_NONE,ACCEPT_NONE};  
		String[] returnR004 = {ACCEPT_WATSONS,ACCEPT_WATSONS,ACCEPT_WATSONS,ACCEPT_WATSONS,ACCEPT_NONE}; 
		String[] returnR005 = {ACCEPT_BUYER,ACCEPT_WATSONS,ACCEPT_BUYER,ACCEPT_BUYER,ACCEPT_NONE}; 
		String[] returnR006 = {ACCEPT_BUYER,ACCEPT_WATSONS,ACCEPT_BUYER,ACCEPT_BUYER,ACCEPT_NONE}; 
		returnMap.put("R001", returnR001);
		returnMap.put("R002", returnR002);
		returnMap.put("R003", returnR003);
		returnMap.put("R004", returnR004);
		returnMap.put("R005", returnR005);
		returnMap.put("R006", returnR006);
		map.put(TYPE_RETURN, returnMap);

		Map<String, String[]> changeMap = new HashMap<String, String[]>();
		String[] changeR001 = {ACCEPT_WATSONS,ACCEPT_WATSONS,ACCEPT_BUYER,ACCEPT_WATSONS,ACCEPT_WATSONS}; 
		String[] changeR002 = {ACCEPT_DELIVERY,ACCEPT_WATSONS,ACCEPT_DELIVERY,ACCEPT_NONE,ACCEPT_BUYER}; 
		String[] changeR003 = {ACCEPT_DELIVERY,ACCEPT_WATSONS,ACCEPT_DELIVERY,ACCEPT_NONE,ACCEPT_BUYER};  
		String[] changeR004 = {ACCEPT_WATSONS,ACCEPT_WATSONS,ACCEPT_BUYER,ACCEPT_WATSONS,ACCEPT_WATSONS}; 
		String[] changeR005 = {ACCEPT_NONE,ACCEPT_NONE,ACCEPT_NONE,ACCEPT_NONE,ACCEPT_NONE}; 
		String[] changeR006 = {ACCEPT_BUYER,ACCEPT_WATSONS,ACCEPT_BUYER,ACCEPT_BUYER,ACCEPT_BUYER}; 
		changeMap.put("R001", changeR001);
		changeMap.put("R002", changeR002);
		changeMap.put("R003", changeR003);
		changeMap.put("R004", changeR004);
		changeMap.put("R005", changeR005);
		changeMap.put("R006", changeR006);
		map.put(TYPE_CHANGE, changeMap);
		
		Map<String, String[]> refuseMap = new HashMap<String, String[]>();
		String[] refuseR001 = {ACCEPT_NONE,ACCEPT_NONE,ACCEPT_NONE,ACCEPT_NONE,ACCEPT_NONE}; 
		String[] refuseR002 = {ACCEPT_NONE,ACCEPT_NONE,ACCEPT_NONE,ACCEPT_NONE,ACCEPT_NONE}; 
		String[] refuseR003 = {ACCEPT_NONE,ACCEPT_NONE,ACCEPT_NONE,ACCEPT_NONE,ACCEPT_NONE};  
		String[] refuseR004 = {ACCEPT_NONE,ACCEPT_NONE,ACCEPT_NONE,ACCEPT_NONE,ACCEPT_NONE}; 
		String[] refuseR005 = {ACCEPT_NONE,ACCEPT_NONE,ACCEPT_NONE,ACCEPT_NONE,ACCEPT_NONE}; 
		String[] refuseR006 = {ACCEPT_BUYER,ACCEPT_WATSONS,ACCEPT_BUYER,ACCEPT_NONE,ACCEPT_NONE}; 
		refuseMap.put("R001", refuseR001);
		refuseMap.put("R002", refuseR002);
		refuseMap.put("R003", refuseR003);
		refuseMap.put("R004", refuseR004);
		refuseMap.put("R005", refuseR005);
		refuseMap.put("R006", refuseR006);
		map.put(TYPE_REFUSE, refuseMap);
	}

}
