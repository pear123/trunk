
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="com.alipay.services.*"%>
<%@ page import="com.alipay.util.*"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Map"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <!--<title>即时到帐批量退款有密接口</title>-->
</head>



<%

    ////////////////////////////////////请求参数//////////////////////////////////////

    //必填参数//

    //退款批次号。格式为：退款日期（8位当天日期）+流水号（3～24位，不能接受“000”，但是可以接受英文字符）
    String batch_no = String.valueOf(request.getAttribute("batch_no"));
    System.out.println("refund batch_no: "+batch_no);
    //退款请求时间
    //String refund_date = request.getHeader("refund_date");
    String refund_date = String.valueOf(request.getAttribute("refund_date"));
    System.out.println("refund refund_date: "+refund_date);
    //退款总笔数
    String batch_num = String.valueOf(request.getAttribute("batch_num"));
    System.out.println("refund batch_num: "+batch_num);

    //单笔数据集
    String detail_data = String.valueOf(request.getAttribute("detail_data"));
    //String detail_data =  new String(request.getParameter("detail_data").getBytes("ISO-8859-1"),"gbk");
    System.out.println("refund detail_data: "+detail_data);
    //格式：第一笔交易#第二笔交易#第三笔交易
    //第N笔交易格式：交易退款信息
    //交易退款信息格式：原付款支付宝交易号^退款总金额^退款理由
    //注意：
    //1.detail_data中的退款笔数总和要等于参数batch_num的值
    //2.detail_data的值中不能有“^”、“|”、“#”、“$”等影响detail_data的格式的特殊字符
    //3.detail_data中退款总金额不能大于交易总金额
    //4.一笔交易可以多次退款，只需要遵守多次退款的总金额不超过该笔交易付款时金额。
    //5.不支持退分润功能
    //选填参数（以下两个参数不能同时为空）
    //卖家支付宝账号
    String seller_email = String.valueOf(request.getAttribute("seller_email"));

    //卖家用户ID
    String seller_user_id = String.valueOf(request.getAttribute("seller_user_id"));

    //////////////////////////////////////////////////////////////////////////////////

    //把请求参数打包成数组
    Map<String, String> sParaTemp = new HashMap<String, String>();
    sParaTemp.put("seller_email", seller_email);
    sParaTemp.put("seller_user_id", seller_user_id);
    sParaTemp.put("batch_no", batch_no);
    sParaTemp.put("refund_date", refund_date);
    sParaTemp.put("batch_num", batch_num);
    sParaTemp.put("detail_data", detail_data);
    //构造函数，生成请求URL
    String sHtmlText = AlipayService.refund_fastpay_by_platform_pwd(sParaTemp);
    out.println(sHtmlText);
%>
<body>
</body>
</html>
