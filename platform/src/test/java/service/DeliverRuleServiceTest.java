//package service;
//
//import com.arvato.oms.service1.OmsDeliverRuleCustomerService;
//import com.arvato.oms.service1.OmsDeliverRuleService;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.transaction.TransactionConfiguration;
//import org.springframework.transaction.annotation.Transactional;
//
///**
//* Created with IntelliJ IDEA.
//* User: shao011
//* Date: 12-11-14
//* Time: 上午10:55
//* To change this template use File | Settings | File Templates.
//*/
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration("classpath*:*/applicationContext-*.xml")
//@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
//@Transactional
//public class DeliverRuleServiceTest {
//    @Autowired
//    private OmsDeliverRuleCustomerService deliverRuleCustomerService;
//    @Autowired
//    private OmsDeliverRuleService deliverRuleService;
//    @Test
//    public void testCalculateCustomerFee() {
//        String shippingCode = "YTO";
//        String provc = "北京";
//        String city = "北京市";
//        String district = "顺义区";
//        double totalWeight = 3990;
//        double fee = deliverRuleCustomerService.calculateDeliverFee(shippingCode, provc, city, district, totalWeight);
//        System.out.println(fee);
//    }
//
//    @Test
//    public void testCalculateFee() {
//        String shippingCode = "YTO";
//        String provc = "北京";
//        String city = "北京市";
//        String district = "顺义区";
//        double totalWeight = 3990;
//        double fee = deliverRuleService.calculateDeliverFee(shippingCode, provc, city, district, totalWeight);
//        System.out.println(fee);
//    }
//
//}
