//package service;
//
//import com.arvato.cc.service1.OrderProducerService;
//import com.arvato.oms.file.props.HybrisPropertiesFileReader;
//import org.apache.commons.httpclient.HttpClient;
//import org.apache.commons.httpclient.HttpStatus;
//import org.apache.commons.httpclient.NameValuePair;
//import org.apache.commons.httpclient.methods.GetMethod;
//import org.apache.commons.httpclient.methods.PostMethod;
//import org.junit.Before;
//import org.junit.Test;
//import org.springframework.context.ApplicationContext;
//
//import java.io.*;
//import java.net.URLDecoder;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.Iterator;
//import java.util.Map;
//import java.util.TreeMap;
//
///**
// * Created with IntelliJ IDEA.
// * User: pang008
// * Date: 29/10/12
// * Time: 14:43
// * To change this template use File | Settings | File Templates.
// */
//public class ProductServiceTC {
//
//    private static ApplicationContext ac = null;
//
//    @Before
//    public void before() {
////        ac = new ClassPathXmlApplicationContext("classpath*:*/applicationContext-*.xml");
//    }
//
//
//    @Test
//    public void JS() {
////        double d = 1.2567;
////        BigDecimal bigDecimal = new BigDecimal(d);
////        bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
////        System.out.println(bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
////        HybrisPropertiesFileReader reader = HybrisPropertiesFileReader.getInstance();
////        System.out.println(reader);
////        EOSPropertiesFileReader reader1 = EOSPropertiesFileReader.getInstance();
////        System.out.print(reader1);
////        OmsInterfaceLogService omsInterfaceLogService = ac.getBean(OmsInterfaceLogService.class);
////        OmsInterfaceLog omsInterfaceLog = new OmsInterfaceLog();
////        omsInterfaceLog.setUrl("http://10.82.20.207:8080/oms/update");
////        omsInterfaceLog.setParameters("");
////        omsInterfaceLog.setSource("Infinity AOMS");
////        omsInterfaceLog.setDestination("Infinity Hybris");
////        omsInterfaceLog.setCreateTime(new Timestamp(System.currentTimeMillis()));
////        omsInterfaceLog.setStatus(String.valueOf(200));
////        omsInterfaceLog.setResult("Success");
////        omsInterfaceLogService.save(omsInterfaceLog);
//        HybrisPropertiesFileReader reader = HybrisPropertiesFileReader.getInstance();
//        System.out.print(reader);
//
//        String s1 = "12";
//        String s2 = "12,23";
//        System.out.println(s2.contains(","));
//    }
//
//    @Test
//    public void testEmail() {
//
////        EmailService emailService = ac.getBean(EmailService.class);
////        emailService.send("Test", "Test");
//
////        OrderReturnService orderReturnService = ac.getBean(OrderReturnService.class);
////        System.out.print(orderReturnService.checkNeedPostFeeOrNot("8ad294563be0695c013be44d7c4001db", 19, 6, new Date()));
//
//        Double d1 = 2.01;
//        Double d2 = 3.01;
//        System.out.print(d2.compareTo(d1));
//    }
//
//    @Test
//    public void testIndex() {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmsss");
//        for (int i = 0; i < 10; i++) {
//            System.out.println(System.nanoTime());
//            System.out.println(sdf.format(new Date(System.nanoTime())));
////            System.out.println(SequenceUtil.generateStkInNo());
//        }
//    }
//
//    @Test
//    public void testPromotion() {
//        OrderProducerService orderProducerService = ac.getBean(OrderProducerService.class);
//        orderProducerService.allocateOrderById("8a8522ba3b21d708013b21ee83ba003b");
//    }
//
//    @Test
//    public void testFeedbackOfStkin() {
//        String methodName = "executeToEOS";
//        TreeMap<String, String> params = new TreeMap<String, String>();
//        String resultString = null;
//
////        String eosUrl = readProperties().getProperty("eos.url");
////        String appId = readProperties().getProperty("eos.appId");
////        String certiId = readProperties().getProperty("eos.certiId");
//
//        String EOS_URL = "http://localhost:8080/interfaces/eos/feedBack?userName=test&password=test";
//
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        HttpClient client = new HttpClient();
//        PostMethod post = new PostMethod(EOS_URL);
//        post.setRequestHeader("Content-Type", "text/html;charset=UTF-8");
//        client.getHttpConnectionManager().getParams().setConnectionTimeout(30000);
//        post.getParams().setSoTimeout(30000);// 30S
//
//        // 组装得到SIGN签名
//        //String jsonStr= "{'receipt_details':[{'status':'S005','quantity':1,'memo':'','sku_code':'100000443','unit_id':'101022341','parent_unit_id':'101022341','promo_parent_unit_id':'101022341','item_who_pay':'P002','post_who_pay':'P002','confirm_code':'C001','confirm_reson':'R002'}],'mall_no':'1888','warehouse_no':'1688','rid':'SI4034401100350001','in_time':'2013-04-10 09:55:54'}";
//        String jsonStr = "{'ship_orders':[{'wid':'2000569009','tid':'0000000000608001','invoice_no':'','invoice_code':'','order_details':[{'quantity':1,'sku_code':'100000335','unit_id':'101398962','parent_unit_id':'101398962','promo_parent_unit_id':'101398962'},{'quantity':1,'sku_code':'100141365','unit_id':'101398959','parent_unit_id':'101398959','promo_parent_unit_id':'101398959'},{'quantity':2,'sku_code':'100141341','unit_id':'101398960','parent_unit_id':'101398960','promo_parent_unit_id':'101398960'},{'quantity':1,'sku_code':'100141342','unit_id':'101398961','parent_unit_id':'101398961','promo_parent_unit_id':'101398961'},{'quantity':1,'sku_code':'100141388','unit_id':'101398958','parent_unit_id':'101398958','promo_parent_unit_id':'101398958'}],'out_status':'Y','out_time':'2013-04-25 12:18:49','wtc_card_no':'11005253085'}],'oid':'SO12131801300454023','mall_no':'1888','ship_type':'N','packageDetails':[{'delivery_no':'6000000964','weight':200,'record_id':1}]}";
//        params.put("method", "wms.warehouse.deliver.feedback");
//        try {
//            params.put("data", URLDecoder.decode(jsonStr, "utf-8"));
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }
//        String nowDateTime = format.format(new Date());
//
//        // String sign = ApiUtil.getSign(params, token);//供签名使用
//        String sign = "9299A37286BC93A7CAC393FAD57A5398";
//        params.put("sign", sign);
//
//        // 设置POST参数及值
//        /* 以NameValuePair形式传输 */
//        Iterator<Map.Entry<String, String>> it = params.entrySet().iterator();
//        NameValuePair[] data = new NameValuePair[params.size()];
//        int i = 0;
//        while (it.hasNext()) {
//            Map.Entry entry = (Map.Entry) it.next();
//            if (entry.getValue() != null) {
//                String name = entry.getKey().toString();
//                String value = entry.getValue().toString();
//                data[i] = new NameValuePair(name, value);
//                i++;
//            }
//        }
//        if (data != null && data.length > 0) {
//            post.setRequestBody(data);
//        }
//        //传输数据
//        int statusCode = 0;
//        try {
//            statusCode = client.executeMethod(post);
//        } catch (IOException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }
//        // 判断返回的状态
//        if (statusCode != HttpStatus.SC_OK) {
//            //return "{\"error_code\":\"404\",\"is_success\":false,\"message\":\"服务器访问错误:not catenation\",\"data\":null}";
//        }
//        //取出结果
//        try {
//            resultString = post.getResponseBodyAsString();
//        } catch (IOException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }
//        post.releaseConnection();
//        System.out.println("resultString===" + resultString);
//        //return resultString;
//
//    }
//
//
//
//    @Test
//    public void testCouponIntegration() throws IOException {
//        String hybrisUrl = "http://10.5.162.27:9001/infinitycommercewebservices/v1/coupon/update?portStr=9002&ip=10.5.162.27&username=webclient&password=123&code=0000000000198003";
////        String hybrisUrl = "http://10.82.20.207:9001/order/update?portStr=9002&ip=10.82.20.207&username=admin&password=arvarto123&code=0000000000734011&statusCode=WAIT_STKOUT";
//        HttpClient client = new HttpClient();
//        GetMethod post = new GetMethod(hybrisUrl);
//        post.setRequestHeader("Content-Type", "text/html;charset=UTF-8");
//        client.getHttpConnectionManager().getParams().setConnectionTimeout(3000);
//        post.getParams().setSoTimeout(3000);// 30S        //传输数据
//        int statusCode = client.executeMethod(post);
//        String result = post.getResponseBodyAsString();
//        System.out.println("status code:" + statusCode + ",result:" + result);
//        post.releaseConnection();
//    }
//}
