<%
/* *
 *功能：批量付款到支付宝账户有密接口接入页
 *版本：3.2
 *日期：2011-03-17
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。

 *************************注意*****************
 *如果您在接口集成过程中遇到问题，可以按照下面的途径来解决
 *1、商户服务中心（https://b.alipay.com/support/helperApply.htm?action=consultationApply），提交申请集成协助，我们会有专业的技术工程师主动联系您协助解决
 *2、商户帮助中心（http://help.alipay.com/support/232511-16307/0-16307.htm?sh=Y&info_type=9）
 *3、支付宝论坛（http://club.alipay.com/read-htm-tid-8681712.html）
 *如果不想使用扩展功能请把扩展功能参数赋空值。
 **********************************************
 */
%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="com.alipay.services.*"%>
<%@ page import="com.alipay.util.*"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Map"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<!--<title>批量付款到支付宝账户有密接口</title>-->
	</head>
	<%
    
		////////////////////////////////////请求参数//////////////////////////////////////
		
		//必填参数//
		UtilDate date = new UtilDate();//调取支付宝工具类生成订单号
		//付款当天日期，获取当天日期，格式：年[4位]月[2位]日[2位]，如：20100801
		String pay_date = date.getDate();	
		
		//商家网站里的批次号，保证其唯一性，格式：当天日期[8位]+序列号[3至16位]，如：201008010000001
		String batch_no = String.valueOf(request.getAttribute("batch_no"));
        System.out.println("trans batch_no: "+batch_no);
		//付款总金额，即参数detail_data的值中所有金额的总和
		String batch_fee = String.valueOf(request.getAttribute("batch_fee"));
        System.out.println("trans batch_fee: "+batch_fee);
		//付款笔数，即参数detail_data的值中，“|”字符出现的数量加1，最大支持1000笔（即“|”字符出现的数量999个）。
		String batch_num =  String.valueOf(request.getAttribute("batch_num"));
        System.out.println("trans batch_num: "+batch_num);
		//付款详细数据
		//String detail_data =  new String(request.getParameter("detail_data").getBytes("ISO-8859-1"),"utf-8");
        String detail_data = String.valueOf(request.getAttribute("detail_data"));
		//格式：流水号1^收款方帐号1^收款帐号1真实姓名^付款金额1^备注说明1|流水号2^收款方帐号2^收款帐号2真实姓名^付款金额2^备注说明2....
        //如：20100801001^1111@163.com^张三^0.01^备注说明一|20100801002^2222@126.com^李四^0.01^备注说明二
        //注意：
        //1.detail_data中的付款金额总和要等亍参数batch_fee的值
        //2.detail_data中的付款笔数总和要等亍参数batch_num的值
        //3.detail_data的值中不能有“^”、“|”等影响detail_data的格式的特殊字符
        //4.收款方支付宝账号与这位收款方的真实姓名需要匹配，即需要实名认证。
		//////////////////////////////////////////////////////////////////////////////////
		
		//把请求参数打包成数组
		Map<String, String> sParaTemp = new HashMap<String, String>();
        sParaTemp.put("batch_fee", batch_fee);
        sParaTemp.put("batch_num", batch_num);
        sParaTemp.put("detail_data", detail_data);
        sParaTemp.put("pay_date", pay_date);
        sParaTemp.put("batch_no", batch_no);
		//构造函数，生成请求URL  
		String sHtmlText = AlipayService.batch_trans_notify(sParaTemp);
		out.println(sHtmlText);
	%>
	<body>
	</body>
</html>
