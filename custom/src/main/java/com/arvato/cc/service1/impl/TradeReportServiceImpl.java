package com.arvato.cc.service1.impl;

import com.arvato.cc.constant.Constants;
import com.arvato.cc.dao1.*;
import com.arvato.cc.log.LogMessageUtil;
import com.arvato.cc.model.*;
import com.arvato.cc.service1.TradeReportService;
import com.arvato.cc.util.CommonHelper;
import com.arvato.cc.util.TaobaoJsonUtil;
import com.arvato.cc.util.Validate;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: caol005
 * Date: 15-8-27
 * Time: 下午9:26
 * To change this template use File | Settings | File Templates.
 */
@Service
public class TradeReportServiceImpl implements TradeReportService{
    private static final Log log = LogFactory.getLog(TradeReportServiceImpl.class);
    private static LogMessageUtil msg = new LogMessageUtil("JOB");
    @Autowired
    private TempTradeDao tempTradeDao;

    @Autowired
    private TradeRemarkDao tradeRemarkDao;

    @Autowired
    private ExpOrderDao expOrderDao;

    @Autowired
    private JobExecDao jobExecDao;

    @Autowired
    private ReportConfigDao reportConfigDao;

    public JobExec getResultByBatch(JobExec jobExec) {
        log.info(msg.getStartMessage("getResultByBatch"));
        List<TempTrade> tempTradeList = tempTradeDao.findByBatchNo(jobExec.getBatchNo());
        if (Validate.isNullOrEmpty(tempTradeList)) {
            log.info(msg.getEndMessage("getResultByBatch tempTradeList is null"));
            return null;
        }
        return jobExecDao.saveJobExec(jobExec);
    }

    /**
     * generate trade report
     */
    public String generateTradeReport(int batchNo) {
        log.info(msg.getStartMessage("generateTradeReport"));
        String result =  "success";
        try{
            List<TempTrade> tempTradeList = tempTradeDao.findByBatchNo(batchNo);

            List<ExpOrder> expOrderList = null;
            TempTrade tempTrade = null;
            for (int i = 0; i < tempTradeList.size(); i++) {
                //find temp trade by tid ,if exist ,delete it and save new record ,if not exist ,save directly
                log.info(msg.getStartMessage("deleteExeOrder tempTrade index:"+i));
                tempTrade = tempTradeList.get(i);
                log.info(msg.getProcessMessage("deleteExeOrder deleteExeOrder tid:" + tempTrade.getTid()));
                expOrderDao.deleteExeOrder(tempTrade.getTid());
                log.info(msg.getProcessMessage("deleteExeOrder deleteExeOrder to generateExpOrder"));
                expOrderList = generateExpOrder(tempTrade);
                log.info(msg.getProcessMessage("generateExpOrder to save"));
                expOrderDao.saveExpOrder(expOrderList);
                log.info(msg.getEndMessage("generateExpOrder saved"));
            }

        }catch (Exception e){
            log.error(msg.getErrorMessage("generateTradeReport:" + e.getMessage()), e);
            result =  "failure";
        }
        //mod by kalend
        log.info(msg.getEndMessage("generateTradeReport:result"+result));
        return result;

    }

    /**
     * 解析临时表数据到订单报表
     * @param tempTrade
     * @return
     */
    private List<ExpOrder> generateExpOrder(TempTrade tempTrade) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, ParseException, IntrospectionException {
        log.info(msg.getStartMessage("generateExpOrder"));
        List<ExpOrder> expOrderList = new ArrayList<ExpOrder>();
        ExpOrder expOrder = new ExpOrder();
        //获取备注信息
        TradeRemark tradeRemark = tradeRemarkDao.getByTid(tempTrade.getTid());
        expOrder.setSalesorg("CN00");
        expOrder.setDistrchannel("30");
        expOrder.setSalesdoctype("SKKE");
        expOrder.setCustomer(tempTrade.getCpdCode());
        expOrder.setPoNumber(tempTrade.getTid() + "天猫");
        expOrder.setPricingdate(tempTrade.getCreated());
        expOrder.setPartnerfunctn("AG");
        expOrder.setShipToParty(tempTrade.getCpdCode());
        expOrder.setName(TaobaoJsonUtil.getInvoiceTitlePriority(tradeRemark, tempTrade.getInvoiceName(), tempTrade.getReceiverName())); //开票抬头
        expOrder.setStreet(tempTrade.getReceiverState() + " " + tempTrade.getReceiverCity() + " " + tempTrade.getReceiverDistrict());
        expOrder.setCity("T" + tempTrade.getTid());
        expOrder.setPostalCode("999999");
        expOrder.setTmallPrice("EDI1");
        expOrder.setTmallPoints("KMA1");
        expOrder.setTmallCoupon("KMA6");
        if(!Validate.isNullOrEmpty(tradeRemark)){
            if (!Validate.isNullOrEmpty(tradeRemark.getTicketName())) {
                expOrder.setPoachiveno("Z009");
                expOrder.setText1("Tmall 增票");
                expOrder.setInternalnote("Z004");
                expOrder.setText2(tradeRemark.getTicketName() );  //+ tradeRemark.getTicketCode() + tradeRemark.getTicketAddress() + tradeRemark.getTicketAccount()
            }
        }
        if(!Validate.isNullOrEmpty(tempTrade.getPaidCouponFee())) {
            expOrder.setBillingtext("Z003");
            expOrder.setText3("买一赠电子红包"+tempTrade.getPaidCouponFee()+"元");
        }
        expOrder.setStoreId(tempTrade.getStoreId());
        expOrder.setTradeStatus(tempTrade.getStatus());
        expOrder.setTid(tempTrade.getTid());
        expOrder.setAlipayNo(tempTrade.getAlipayNo());
        expOrder.setReceiverCity(tempTrade.getReceiverCity());
        expOrder.setReceiverState(tempTrade.getReceiverDistrict());
        expOrder.setPayment(tempTrade.getPayment());
        //对报表数据需做的样式添加标记位

        //copy same value in line,expect material
        Set<TempOrder> tempOrderSet = tempTrade.getTempOrders();
//        String storeAddress = null;
        if(!Validate.isNullOrEmpty(tempOrderSet)){
            if(tempOrderSet.size() > 1) {
                expOrder.setCountRemark("1");
            }
        }
        //***************************************************
        //CR:20150929:
        expOrder.setItemType(Constants.OrderReportItemType.order.toString());
        expOrder.setSellerMemo(tempTrade.getSellerMemo());
        expOrder.setInvoiceName(tempTrade.getInvoiceName());
        expOrder.setReceiverName(tempTrade.getReceiverName());
        expOrder.setOrderPay(CommonHelper.getSub(tempTrade.getPayment(), tempTrade.getPointFee() / 100));
        expOrder.setOrderPayment(tempTrade.getPayment());
        //***************************************************

        boolean isMarkPoint = true;
        Double pointFee = 0.0;
        StringBuffer title = new StringBuffer("");
        int quantity = 0;
        String bundleSkuRemark = "0";
        Double coupon = 0.0;
        Double coupon_apportion = 0.0;
        boolean need_release_coupon = true;
        for (TempOrder tempOrder : tempOrderSet) {
            ExpOrder expOrderRow = (ExpOrder) BeanUtils.cloneBean(expOrder);
            //***************************************************
            //CR:20150929:
            bundleSkuRemark = "0" ;
            expOrderRow.setRefundId(tempOrder.getRefundId());
            expOrderRow.setItemType(Constants.OrderReportItemType.item.toString());
            expOrderRow.setItemTitle(tempOrder.getTitle());
            title.append(tempOrder.getTitle());
            title.append(";");
            quantity += tempOrder.getNum();
            if(!Validate.isNullOrEmpty(tempOrder.getMatrn()) && tempOrder.getMatrn().contains(",")){
                bundleSkuRemark = "1";
                expOrder.setAddressRemark(bundleSkuRemark);
            }
            expOrderRow.setAddressRemark(bundleSkuRemark);
            //**************************************************
            //2015-12-01 双十一预售订单优惠券总额 start
            String configStatus = "step";
            List<ReportConfig> configColumn = reportConfigDao.getReportConfigByName("order_report");
            expOrderRow.setOriginalCoupon(0.0);
            if(tempTrade.getType().equals(configStatus)) {
                Class expClass = (Class) expOrderRow.getClass();
                Class tempClass = (Class) tempOrder.getClass();
                if(!Validate.isNullOrEmpty(configColumn)) {
                    for(ReportConfig rc :configColumn) {
                        PropertyDescriptor pd = new PropertyDescriptor(rc.getTaobaoColumn(),tempClass);
                        if(!Validate.isNullOrEmpty(pd)) {
                            Method method = pd.getReadMethod();//获得get方法
                            Object o = method.invoke(tempOrder);//执行get方法返回一个Object
                            if (!Validate.isNullOrEmpty(o)) {
                                if(CommonHelper.DoubleParse(o) != 0 ) {
                                    pd = new PropertyDescriptor(rc.getReportColumn(), expClass);
                                    method = pd.getWriteMethod();//获得set方法
                                    method.invoke(expOrderRow, Math.abs(CommonHelper.DoubleParse(o)));
                                    if(rc.getOrderIndex() > 1) {
                                        need_release_coupon = false;
                                    }
                                    break;
                                }
                            }
                        }
                    }
                }
                coupon += expOrderRow.getOriginalCoupon();
                coupon_apportion = expOrderRow.getOriginalCoupon();
            }else{
                coupon += tempOrder.getPartMjzDiscount();
                coupon_apportion = tempOrder.getPartMjzDiscount();
            }
            if(need_release_coupon) {
                expOrderRow.setItemPayment(tempOrder.getPayment());
            } else {
                expOrderRow.setItemPayment(tempOrder.getPayment() + coupon_apportion);
            }
            //2015-12-01 双十一预售订单优惠券总额 end

            //***************************************************
            expOrderRow.setRefundStatus(tempOrder.getRefundStatus());
            expOrderRow.setMaterial(tempOrder.getMatrn());
            expOrderRow.setQuantity(tempOrder.getNum());
            expOrderRow.setPointAmout(0.0);
            expOrderRow.setPointFeeRemark("0");
            pointFee = 0.0;
            if(isMarkPoint){
                //标记位1.积分除不尽
                if(tempTrade.getPointFee().doubleValue() > 0) {
                    //取余：除不尽，添加样式，显示积分总和
                    if(CommonHelper.getMod(tempTrade.getPointFee() * 100,expOrderRow.getQuantity()) > 0){
                        expOrderRow.setPointAmout(tempTrade.getPointFee());
                        expOrderRow.setPointFeeRemark("1");
                    }else{
                        //显示：积分/数量/100
                        expOrderRow.setPointAmout(new BigDecimal(tempTrade.getPointFee()).divide(new BigDecimal(expOrderRow.getQuantity())).divide(new BigDecimal(100)).doubleValue());
                        expOrderRow.setPointFeeRemark("0");
                        pointFee = expOrderRow.getPointAmout();
                    }
                } else{
                    expOrderRow.setPointAmout(0.0);
                    expOrderRow.setPointFeeRemark("0");
                }
                isMarkPoint = false;
            }
            //计算开票金额
            log.info(msg.getProcessMessage("generateExpOrder,tid:" + tempTrade.getTid()));
            if(tempOrder.getPayment() > 0) {
                log.info(msg.getProcessMessage("generateExpOrder,payment:" + tempOrder.getPayment())  );
                log.info(msg.getProcessMessage("generateExpOrder,partMjzDiscount:" + coupon_apportion));
                Double fee = CommonHelper.getSub(expOrderRow.getItemPayment(), coupon_apportion);//总金额 减去 优惠券分摊金额
                log.info(msg.getProcessMessage("generateExpOrder,fee:" + fee) );
                log.info(msg.getProcessMessage("generateExpOrder,fee * 100 :" + fee * 100) );
                log.info(msg.getProcessMessage("generateExpOrder,quantity:" + expOrderRow.getQuantity()) );
                log.info(msg.getProcessMessage("generateExpOrder,mod:" + CommonHelper.getMod(fee * 100, expOrderRow.getQuantity())) );
                if(CommonHelper.getMod(fee * 100,expOrderRow.getQuantity()) > 0){ //如果除以数量除不尽则高亮，并添加样式
                    expOrderRow.setConditionAmountRemark("1");
                    expOrderRow.setConditionAmount(fee);
                    log.info(msg.getProcessMessage("generateExpOrder,1"));
                } else {
                    expOrderRow.setConditionAmountRemark("0");
                    expOrderRow.setConditionAmount(CommonHelper.getSub(CommonHelper.getDiv(fee,expOrderRow.getQuantity()),pointFee));
                    log.info(msg.getProcessMessage("generateExpOrder,0"));
                }
            } else{
                expOrderRow.setConditionAmountRemark("2");
                expOrderRow.setConditionAmount(0.0);
                log.info(msg.getProcessMessage("generateExpOrder,2"));
            }
            //标记位2.优惠券除不尽
            if(coupon_apportion.doubleValue() > 0) {
                //取余：除不尽，添加样式，显示优惠券总和
                if(CommonHelper.getMod(coupon_apportion * 100,expOrderRow.getQuantity()) > 0) {
                    expOrderRow.setCouponAmount(coupon_apportion);
                    expOrderRow.setCouponRemark("1");
                }else{
                    //显示：优惠券/数量
                    expOrderRow.setCouponAmount(new BigDecimal(coupon_apportion).divide(new BigDecimal(expOrderRow.getQuantity())).doubleValue());
                    expOrderRow.setCouponRemark("0");
                }
            }else{
                expOrderRow.setCouponAmount(0.0);
                expOrderRow.setCouponRemark("0");
            }
            //标记位3.地址不相同，标记位
            //根据仓库码，查询城市地址，跟当前收货地址进行匹配
//            storeAddress = storeDao.findByCode(tempOrder.getStoreCode());
//            if(!Validate.isNullOrEmpty(storeAddress)) {
//                if(tempTrade.getReceiverCity().indexOf(storeAddress) >= 0) {
//                    expOrderRow.setAddressRemark("0");
//                }else{
//                    expOrderRow.setAddressRemark("1");
//                }
//            }else{
//                expOrderRow.setAddressRemark("1");
//            }
            expOrderRow.setOriginalPointFee(tempTrade.getPointFee());
            expOrderList.add(expOrderRow);
        }
        expOrder.setQuantity(quantity);
        expOrder.setItemTitle(title.toString());
        expOrder.setOriginalCoupon(coupon);
        expOrder.setOriginalPointFee(tempTrade.getPointFee());
        expOrderList.add(expOrder);
        log.info(msg.getEndMessage("generateOrderReportByExample"));
        return expOrderList;
    }




}
