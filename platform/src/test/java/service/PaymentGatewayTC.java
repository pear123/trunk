//package service;
//
//import com.alipay.services.AlipayService;
//import com.arvato.oms.model.QueryAccount;
//import CommonHelper;
//import com.tenPay.service.InvalidPrivateKeyException;
//import com.tenPay.service.TenpayService;
//import com.unionPay.service.UnionPayService;
//import org.apache.commons.lang.StringUtils;
//import org.junit.Test;
//
//import javax.xml.stream.XMLInputFactory;
//import javax.xml.stream.XMLStreamException;
//import javax.xml.stream.XMLStreamReader;
//import javax.xml.stream.events.XMLEvent;
//import java.io.StringReader;
//import java.io.UnsupportedEncodingException;
//import java.sql.Timestamp;
//import java.text.ParseException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import static CommonHelper.DateFormat;
//
///**
// * Created with IntelliJ IDEA.
// * User: pang008
// * Date: 13-7-4
// * Time: 下午5:48
// * To change this template use File | Settings | File Templates.
// */
//public class PaymentGatewayTC {
//
//    private Map<String, Object> convert(String result) throws XMLStreamException {
//
//        Map<String, Object> map = new HashMap<String, Object>();
//        XMLStreamReader reader = XMLInputFactory.newInstance().createXMLStreamReader(new StringReader(result));
//
//        List<QueryAccount> list = new ArrayList<QueryAccount>();
//        QueryAccount account = null;
//        while (reader.hasNext()) {
//            int point = reader.next();
//            //represent start element
//            if (point == XMLStreamReader.START_ELEMENT) {
//                String eleName = reader.getName().toString();
//                System.out.println("Element name:" + eleName);
//                if ("is_success".equals(eleName)) {
//                    if ("F".equals(reader.getElementText())) {
//                        break;
//                    }
//                } else if ("has_next_page".equals(eleName)) {
//                    String text = reader.getElementText();
//                    if ("T".equals(text)) {
//                        map.put("hasNext", true);
//                    } else if ("F".equals(text)) {
//                        map.put("hasNext", false);
//                    }
//                } else if ("AccountQueryAccountLogVO".equals(eleName)) {
//                    account = new QueryAccount();
//                    list.add(account);
//                } else if ("merchant_out_order_no".equals(eleName)) {
////                    account.setIwAccountLogId();
//                    account.setMerchantOutOrderNo(reader.getElementText());
//                } else if ("balance".equals(eleName)) {
//                    account.setBalance(reader.getElementText());
//                } else if ("buyer_account".equals(eleName)) {
//                    account.setBuyerAccount(reader.getElementText());
//                } else if ("currency".equals(eleName)) {
//                    String text = reader.getElementText();
//                    if (StringUtils.isNotEmpty(text)) {
//                        account.setCurrency(Double.parseDouble(text));
//                    } else {
//                        account.setCurrency(0.00d);
//                    }
//                } else if ("deposit_bank_no".equals(eleName)) {
//                    account.setDepositBankNo(reader.getElementText());
//                } else if ("goods_title".equals(eleName)) {
//                    account.setGoodsTitle(reader.getElementText());
//                } else if ("income".equals(eleName)) {
//                    String text = reader.getElementText();
//                    if (StringUtils.isNotEmpty(text)) {
//                        account.setIncome(Double.parseDouble(text));
//                    } else {
//                        account.setIncome(0.00d);
//                    }
//                } else if ("trans_code_msg".equals(eleName)) {
//                    account.setTransCodeMsg(reader.getElementText());
//                } else if ("trans_date".equals(eleName)) {
//                    try {
//                        account.setTransDate(CommonHelper.parseFromString(reader.getElementText(), "yyyy-MM-dd HH:mm:ss"));
//                    } catch (ParseException e) {
//
//                    }
//                } else if ("trans_out_order_no".equals(eleName)) {
//                    account.setTransOutOrderNo(reader.getElementText());
//                } else if ("iw_account_log_id".equals(eleName)) {
//                    account.setIwAccountLogId(reader.getElementText());
//                } else if ("memo".equals(eleName)) {
//                    account.setMemo(reader.getElementText());
//                } else if ("outcome".equals(eleName)) {
//                    String text = reader.getElementText();
//                    if (StringUtils.isNotEmpty(text)) {
//                        account.setOutcome(Double.parseDouble(text));
//                    } else {
//                        account.setOutcome(0.00d);
//                    }
//                } else if ("partner_id".equals(eleName)) {
//                    account.setPartnerId(reader.getElementText());
//                } else if ("rate".equals(eleName)) {
//                    String text = reader.getElementText();
//                    if (StringUtils.isNotEmpty(text)) {
//                        account.setDeductionRate(Double.parseDouble(text));
//                    } else {
//                        account.setDeductionRate(0.00d);
//                    }
//                } else if ("seller_account".equals(eleName)) {
//                    account.setSellerAccount(reader.getElementText());
//                } else if ("seller_fullname".equals(eleName)) {
//                    account.setSellerFullname(reader.getElementText());
//                } else if ("service_fee".equals(eleName)) {
//                    String text = reader.getElementText();
//                    if (StringUtils.isNotEmpty(text)) {
//                        account.setServiceFee(Double.parseDouble(text));
//                    } else {
//                        account.setServiceFee(0.00d);
//                    }
//                } else if ("service_fee_ratio".equals(eleName)) {
//                    account.setServiceFeeRatio(reader.getElementText());
//                } else if ("sign_product_name".equals(eleName)) {
//                    account.setContractProduct(reader.getElementText());
//                } else if ("sub_trans_code_msg".equals(eleName)) {
//
//                } else if ("total_fee".equals(eleName)) {
//                    String text = reader.getElementText();
//                    if (StringUtils.isNotEmpty(text)) {
//                        account.setTotalFee(Double.parseDouble(text));
//                    } else {
//                        account.setTotalFee(0.00d);
//                    }
//                } else if ("trade_no".equals(eleName)) {
//                    account.setTradeNo(reader.getElementText());
//                } else if ("trade_refund_amount".equals(eleName)) {
//                    String text = reader.getElementText();
//                    if (StringUtils.isNotEmpty(text)) {
//                        account.setTradeRefundAmount(Double.parseDouble(text));
//                    } else {
//                        account.setTradeRefundAmount(0.00d);
//                    }
//                }
//            }
//
//        }
//
//        map.put("list", list);
//        return map;
//    }
//
//    @Test
//    public void testQueryTransactionFromAlipay() throws Exception {
//        Map<String, String> map = new HashMap<String, String>();
//
//        boolean hasNext = true;
//        int i = 1;
//        map.put("gmt_start_time", "2013-07-01 00:00:00");
//        map.put("gmt_end_time", "2013-07-02 00:00:00");
//        map.put("logon_id", "estore1@watsons.com.cn");
//
//        List<QueryAccount> queryAccountList = new ArrayList<QueryAccount>();
//        while (hasNext) {
//            map.put("page_no", String.valueOf(i));
//            String result = AlipayService.account_page_query(map);
//
//            if (StringUtils.isNotEmpty(result)) {
//
//                Map<String, Object> results = convert(result);
//                if (null != results.get("list")) {
//                    queryAccountList.addAll((List) results.get("list"));
//                }
//
//                hasNext = (Boolean) results.get("hasNext");
//            }
//
//            ++i;
//        }
//    }
//
//    @Test
//    public void testQueryTransactionFromChinapay() {
//        Map<String, String> parameters = new HashMap<String, String>();
//
//        try {
//            parameters.put("MerId", "808080081792384");
//            parameters.put("TransType", "001");
//            parameters.put("OrdId", "0000000010096806");
//            parameters.put("TransDate", "20130801");
//            parameters.put("Version", "20070129");
//            parameters.put("Resv", "10001223");
////            parameters.put("ChkValue", "20070129");
//            System.out.println("result:" + UnionPayService.queryTrans(parameters));
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        } catch (InvalidPrivateKeyException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }
//    }
//
//    @Test
//    public void testQueryTransactionFromUnionpay() {
//        Map<String, String> parameters = new HashMap<String, String>();
//
//        try {
//            parameters.put("spid", "1900000109");
//            parameters.put("trans_time", DateFormat(new Timestamp(System.currentTimeMillis()), "yyyy-MM-dd"));
//            System.out.println(TenpayService.downloadBills(parameters));
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }
//    }
//}
