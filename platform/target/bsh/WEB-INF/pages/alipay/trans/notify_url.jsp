<%
/* *
 功能：支付宝服务器异步通知页面
 版本：3.2
 日期：2011-03-17
 说明：
 以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 该代码仅供学习和研究支付宝接口使用，只是提供一个参考。

 //***********页面功能说明***********
 创建该页面文件时，请留心该页面文件中无任何HTML代码及空格。
 该页面不能在本机电脑测试，请到服务器上做测试。请确保外部可以访问该页面。
 该页面调试工具请使用写文本函数logResult，该函数在com.alipay.util文件夹的AlipayNotify.java类文件中
 如果没有收到该页面返回的 success 信息，支付宝会在24小时内按一定的时间策略重发通知
 //********************************
 * */
%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="com.alipay.config.*"%>
<%@ page import="com.alipay.services.*"%>
<%@ page import="com.alipay.util.*"%>
<%@ page import="com.arvato.cc.constant.Constants"%>
<%@ page import="com.arvato.cc.model.OmsAlipayBatch" %>
<%@ page import="com.arvato.cc.util.Validate" %>
<%@ page import="com.arvato.oms.model.OmsOrderRefund" %>
<%@ page import="com.arvato.oms.service1.OmsAlipayBatchService" %>
<%@ page import="com.arvato.oms.service1.RefundService" %>
<%@ page import="com.arvato.oms.service1.impl.OmsAlipayBatchServiceImpl" %>
<%@ page import="com.arvato.oms.service1.impl.RefundServiceImpl" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%
	//获取支付宝POST过来反馈信息
	Map<String,String> params = new HashMap<String,String>();
	Map requestParams = request.getParameterMap();
	for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
		String name = (String) iter.next();
		String[] values = (String[]) requestParams.get(name);
		String valueStr = "";
		for (int i = 0; i < values.length; i++) {
			valueStr = (i == values.length - 1) ? valueStr + values[i]
					: valueStr + values[i] + ",";
		}
		//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
		//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
		params.put(name, valueStr);
	}

	
	//获取批量付款数据中转账成功的详细信息
	String success_details = new String(request.getParameter("success_details").getBytes("ISO-8859-1"),"utf-8");
	//格式：()括号中的信息代表默认值
	//流水号1^收款方帐号1^收款帐号真实姓名^付款金额^成功标识(S)^
	//成功原因(null)^内部流水号^完成时间|流水号2^收款方帐号2^收款帐号真实姓名^付款金额^成功标识(S)^成功原因(null)^内部流水号^完成时间

	//获取批量付款数据中转账失败的详细信息
	String fail_details = new String(request.getParameter("fail_details").getBytes("ISO-8859-1"), "utf-8");
	//格式：()括号中的信息代表默认值
	//流水号1^收款方帐号1^收款帐号真实姓名^付款金额^成功标识(F)^
	//失败原因^内部流水号^完成时间|流水号2^收款方帐号2^收款帐号真实姓名^付款金额^成功标识(F)^失败原因^内部流水号^完成时间
	
	//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//

	if(AlipayNotify.verify(params)){//验证成功

		//——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
        String[] succdetails = success_details.split("|");
        String[] faildetails = fail_details.split("|");

        RefundService refundService = new RefundServiceImpl();
        OmsAlipayBatchService omsAlipayBatchService = new OmsAlipayBatchServiceImpl();
        String batchNo = params.get("batch_no");
        OmsAlipayBatch omsAlipayBatch = omsAlipayBatchService.findByBatchNo(batchNo);
        if (!Validate.isNullOrEmpty(omsAlipayBatch)){
            List<OmsOrderRefund> omsOrderRefunds = refundService.getByBatch(batchNo);
            if(!Validate.isNullOrEmpty(omsOrderRefunds)){
                out.println("success");	//请不要修改或删除
                for(OmsOrderRefund omsOrderRefund : omsOrderRefunds){
                    for(int i = 0; i<succdetails.length;i++){
                        String[] succ = succdetails[i].split("^");
                        if(omsOrderRefund.getPaymentTransactionId().equals(succ[0])){
                            omsOrderRefund.setRefundStatus(Constants.RefundStatus.SUCCESS.toString());
                            refundService.modeifyByRefundStatus(omsOrderRefund);
                        }
                    }
                    for(int j = 0; j<faildetails.length;j++){
                        String[] fails = faildetails[j].split("^");
                        if(omsOrderRefund.getPaymentTransactionId().equals(fails[0])){
                            omsOrderRefund.setRefundStatus(Constants.RefundStatus.FAILURE.toString());
                            omsOrderRefund.setField1(fails[5]);
                            refundService.modeifyByRefundStatus(omsOrderRefund);
                        }
                    }

                }
            //判断是否在商户网站中已经做过了这次通知返回的处理（可参考“集成教程”中“3.4返回数据处理”）
                //如果没有做过处理，那么执行商户的业务程序
                //如果有做过处理，那么不执行商户的业务程序
            }else {
                out.println("fail");
            }
        }else {
            out.println("fail");
        }
	}else{//验证失败
		out.println("fail");
	}
%>
