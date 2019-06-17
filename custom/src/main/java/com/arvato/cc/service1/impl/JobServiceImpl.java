package com.arvato.cc.service1.impl;

import com.arvato.cc.constant.Constants;
import com.arvato.cc.dao1.*;
import com.arvato.cc.log.LogMessageUtil;
import com.arvato.cc.model.*;
import com.arvato.cc.service1.JobService;
import com.arvato.cc.util.CommonHelper;
import com.arvato.cc.util.TaobaoJsonUtil;
import com.arvato.cc.util.Validate;
import net.sf.cglib.beans.BeanCopier;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: caol005
 * Date: 15-8-12
 * Time: 下午2:37
 * To change this template use File | Settings | File Templates.
 */
@Service
public class JobServiceImpl implements JobService {
    private static final Log log = LogFactory.getLog(JobServiceImpl.class);
    private static LogMessageUtil msg = new LogMessageUtil("JOB");

    @Autowired
    private JdpTradeDao jdpTradeDao;

    @Autowired
    private JstTradeDao jstTradeDao;

    @Autowired
    private TempTradeDao tempTradeDao;

    @Autowired
    private AlipayDao alipayDao;

    @Autowired
    private FinancialDao financialDao;

    @Autowired
    private TradeRemarkDao tradeRemarkDao;

    @Autowired
    private TradeRemarkPresentDao tradeRemarkPresentDao;

    @Autowired
    private GiftDataDao giftDataDao;

    @Autowired
    private DeliveryDao deliveryDao;

    @Autowired
    private SapInvoiceDao sapInvoiceDao;

    @Autowired
    private TradeHistoryDao tradeHistoryDao;

    @Autowired
    private JobExecDao jobExecDao;


    @Autowired
    private TaobaoStoreDao taobaoStoreDao;

    /**
     * generate trade data
     *
     * @return
     */
    public String generateTradeData(int batchNo) {
        log.info(msg.getStartMessage("generateTradeData" + batchNo));
        String result = "success";
        try {
            //get data from jdp_trade by batchNo
            List<JdpTrade> jdpTradeList = jdpTradeDao.findByBatchNo(batchNo);

            //save jdp trade
            if (!Validate.isNullOrEmpty(jdpTradeList)) {
                TaobaoStore taobaoStore = null;
                for (JdpTrade jdpTrade : jdpTradeList) {
                    log.info(msg.getStartMessage("generateTradeData tempTrade"));
                    String response = jdpTrade.getJdpResponse();
                    log.info(msg.getProcessMessage("generateTradeData tempTrade tid:" + jdpTrade.getTid()));
                    jstTradeDao.deleteByTid(jdpTrade.getTid());
                    log.info(msg.getProcessMessage("generateTradeData deleted to response"));
                    JstTrade jstTrade = TaobaoJsonUtil.generateJstTrade(response);
                    log.info(msg.getProcessMessage("generateTradeData response generated"));
                    if(!Validate.isNullOrEmpty(jstTrade.getSellerNick())){
                        log.info(msg.getProcessMessage("generateTradeData response to getStoreCodeByName"));
                        taobaoStore = taobaoStoreDao.getStoreCodeByName(jstTrade.getSellerNick());
                        if(!Validate.isNullOrEmpty(taobaoStore)){
                            log.info(msg.getProcessMessage("generateTradeData getStoreCodeByName setStoreId"));
                            jstTrade.setStoreId(taobaoStore.getCode());
                        }
                    }
                    log.info(msg.getProcessMessage("generateTradeData tp save"));
                    jstTradeDao.saveJstTrade(jstTrade);
                    log.info(msg.getEndMessage("generateTradeData saved"));
                }
            }
        } catch (Exception ex) {
            log.error(msg.getErrorMessage("generateTradeData:" + ex.getMessage()),ex);
            result = "failure";
        }
        return result;
    }


    public void moveTempTrade(int maxBatchNo) throws Exception {
        //select by batchNo
        List<TempTrade> tempTradeList = tempTradeDao.findByMaxBatchNo(maxBatchNo);

        //move temp trade to trade history
        List<TradeHistory> tradeHistoryList = new ArrayList<TradeHistory>();
        for (TempTrade tempTrade : tempTradeList) {
            TradeHistory tradeHistory = new TradeHistory();

            Set<OrderHistory> orderHistorySet = new HashSet<OrderHistory>();
            Set<TempOrder> tempOrderSet = tempTrade.getTempOrders();
            OrderHistory orderHistory = null;
            for (TempOrder tempOrder : tempOrderSet) {
                orderHistory = new OrderHistory();
                BeanUtils.copyProperties(orderHistory, tempOrder);
                BeanCopier copier = BeanCopier.create(TempOrder.class,OrderHistory.class,false);
                copier.copy(tempOrder, orderHistory,null);
                orderHistory.setTradeHistory(tradeHistory);
                orderHistorySet.add(orderHistory);
            }
            tradeHistory.setOrderHistories(orderHistorySet);

            Set<PromotionDetailHistory> promotionDetailHistorySet = new HashSet<PromotionDetailHistory>();
            Set<TempPromotionDetail> tempPromotionDetailSet = tempTrade.getTempPromotionDetails();
            PromotionDetailHistory promotionDetailHistory = null;
            for (TempPromotionDetail tempPromotionDetail : tempPromotionDetailSet) {
                promotionDetailHistory = new PromotionDetailHistory();
                BeanCopier copier = BeanCopier.create( TempPromotionDetail.class,PromotionDetailHistory.class,false);
                copier.copy(tempPromotionDetail, promotionDetailHistory,null);
                promotionDetailHistory.setTradeHistory(tradeHistory);
                promotionDetailHistorySet.add(promotionDetailHistory);
            }
            tradeHistory.setPromotionDetailHistories(promotionDetailHistorySet);

            BeanCopier copier = BeanCopier.create( TempTrade.class,TradeHistory.class,false);
            copier.copy( tempTrade, tradeHistory,null);
            tradeHistoryList.add(tradeHistory);
        }
        tradeHistoryDao.saveTradeHistory(tradeHistoryList);

        //delete from temp trade
        tempTradeDao.deleteByMaxBatchNo(maxBatchNo);
    }


    public String generateFinanceReport(int batchNo) {
        log.info(msg.getStartMessage("generateFinanceReport"+batchNo));
        String result = "success";
        try {
            List<TempTrade> tempTradeList = tempTradeDao.findByBatchNo(batchNo);
            if (Validate.isNullOrEmpty(tempTradeList)) {
                log.info(msg.getEndMessage("generateFinanceReport tempTradeList is null"));
                return result;
            }
            for (TempTrade tTrade : tempTradeList) {
                log.info(msg.getStartMessage("tempTradeList tTrade"));
                financialDao.deleteExpFinancialData(tTrade.getTid());
                log.info(msg.getProcessMessage("deleteExpFinancialData tid:" + tTrade.getTid()));
                ExpFinacial expFinacial = generateExpFinancial(tTrade);
                log.info(msg.getProcessMessage("generateExpFinancial to save"));
                financialDao.saveExpFinancialData(expFinacial);
                log.info(msg.getEndMessage("generateExpFinancial saved"));
            }
        } catch (Exception e){
            log.error(msg.getErrorMessage("generateFinanceReport:"+e.getMessage()),e);
            result = "failure";
        }
        log.info(msg.getEndMessage("generateFinanceReport"));
        return result;
    }

    public String generateDeliveryReport(int batchNo) {
        log.info(msg.getStartMessage("generateDeliveryReport "+batchNo));
        String result = "success";
        try{
            List<TempTrade> tempTradeList = tempTradeDao.findByBatchNoNotVirtual(batchNo);
            if (Validate.isNullOrEmpty(tempTradeList)) {
                return result;
            }
            for (TempTrade tTrade : tempTradeList) {
                log.info(msg.getStartMessage("tempTradeList tTrade"));
                deliveryDao.deleteExpDeliveryData(tTrade.getTid());
                log.info(msg.getProcessMessage("deleteExpDeliveryData tid:"+tTrade.getTid()));
                ExpDelivery expDelivery = generateExpDelivery(tTrade);
                log.info(msg.getProcessMessage("deleteExpDeliveryData to save"));
                deliveryDao.saveExpDeliveryData(expDelivery);
                log.info(msg.getEndMessage("deleteExpDeliveryData saved"));
            }
        }catch(Exception e){
            log.error(msg.getErrorMessage("generateDeliveryReport"+e.getMessage()),e);
            result = "failure";
        }

        return result;
    }

    public ExpDelivery generateExpDelivery(TempTrade tempTrade) {
        ExpDelivery expDelivery = new ExpDelivery();
        //CR:20160930*****************************************************
        expDelivery.setSellerMemo(tempTrade.getSellerMemo());
        expDelivery.setTradeAddress(tempTrade.getReceiverAddress());
        expDelivery.setTradeMobile(tempTrade.getReceiverMobile());
        expDelivery.setTradeName(tempTrade.getReceiverName());
        expDelivery.setAlipayNo(tempTrade.getAlipayNo());
        TaobaoStore taobaoStore = taobaoStoreDao.getStoreNameByCode(tempTrade.getStoreId());
        if(!Validate.isNullOrEmpty(taobaoStore)){
            expDelivery.setStoreCode(taobaoStore.getSname());
        }
        //CR:20160930*****************************************************
        // 订单号
        expDelivery.setBuyerNick(CommonHelper.getString(tempTrade.getBuyerNick()));
        expDelivery.setOrderId(CommonHelper.getString(tempTrade.getTid()));
        //收方地址  1.备注2(地址姓名电话) 2.收货人地址
        TradeRemark tradeRemark = tradeRemarkDao.getByTid(tempTrade.getTid());
        String receiverAddress = CommonHelper.getString(tempTrade.getReceiverState()) + CommonHelper.getString(tempTrade.getReceiverCity()) + CommonHelper.getString(tempTrade.getReceiverDistrict()) + CommonHelper.getString(tempTrade.getReceiverAddress());
        if (!Validate.isNullOrEmpty(tradeRemark) && !Validate.isNullOrEmpty(tradeRemark.getReceiveAddress())) {
            receiverAddress = tradeRemark.getReceiveAddress();
        }
        expDelivery.setReceiverAddress(receiverAddress);
        //收方联系电话   1.备注2(地址姓名电话) 2.收货人电话
        String receiverPhone = CommonHelper.getString(tempTrade.getReceiverPhone());
        if (!Validate.isNullOrEmpty(tradeRemark) && !Validate.isNullOrEmpty(tradeRemark.getMobile())) {
            receiverPhone = tradeRemark.getMobile();
//        } else if (!Validate.isNullOrEmpty(tradeRemark) && !Validate.isNullOrEmpty(tradeRemark.getPhone())) {
//            receiverPhone = tradeRemark.getPhone();
        }else if(!"".equals(CommonHelper.getString(tempTrade.getReceiverMobile()))){
            receiverPhone = CommonHelper.getString(tempTrade.getReceiverMobile());
        }
        expDelivery.setRecevierPhone(receiverPhone);
        //收方联系人
        String receiverName =  CommonHelper.getString(tempTrade.getReceiverName());
        if (!Validate.isNullOrEmpty(tradeRemark) && !Validate.isNullOrEmpty(tradeRemark.getReceiveName())) {
            receiverName = tradeRemark.getReceiveName();
        }
        expDelivery.setReceiverName(CommonHelper.getString(receiverName));
        //收方公司
        expDelivery.setRecevierCompany(CommonHelper.getString(tempTrade.getReceiverName()));
        //收方手机号码
        String receiverMobile = CommonHelper.getString(tempTrade.getReceiverMobile());
        if (!Validate.isNullOrEmpty(tradeRemark) && !Validate.isNullOrEmpty(tradeRemark.getMobile())) {
            receiverMobile = tradeRemark.getMobile();
        }
        expDelivery.setReceiverMobile(receiverMobile);
        //寄托物内容=发票号+礼品
        // 1.发票号
        String tid = CommonHelper.getString(tempTrade.getTid());
        String tidStr = tid + "天猫";
        String invoiceNo = "";
        String mater = "";
        List<UpdInvoice> invoiceList = sapInvoiceDao.findGitNoByTid(tidStr);
        if (!CollectionUtils.isEmpty(invoiceList)) {
            if (invoiceList.size() == 0) {
                invoiceNo = CommonHelper.getString(invoiceList.get(0).getGitno());
            } else {
                //根据订单号和物料号，取出
//                mater = orderHistoryDao.getMaterByTid(tid); //todo
//                UpdInvoice updInvoice = sapInvoiceDao.findGitNoByTidAndMater(tidStr, mater);
//                if (null != updInvoice) {
//                    invoiceNo = updInvoice.getGitno();
//                }
            }
        }
        //发票号
        expDelivery.setInvoiceNo(invoiceNo);
//      int id = tradeRemarkDao.getIdByTid(tempTrade.getTid());
        // 2  礼品
        List<TradeRemarkPresent> tradeRemarkPresentList = tradeRemarkPresentDao.getPresentById(tempTrade.getTid());
        StringBuffer allPresentsAndCountStr = new StringBuffer();
        List<String> allPresentNameList = new ArrayList<String>();
        int presentQuantityCount = 0;
        int presentQuantityNum = 0;
        String presentName = "";
        StringBuffer presents = new StringBuffer();
        //3 托寄物内容
        if (!"".equals(invoiceNo)) {
            allPresentsAndCountStr.append("发票");
            allPresentsAndCountStr.append(invoiceNo);
        }
        if (!CollectionUtils.isEmpty(tradeRemarkPresentList)) {
            for (TradeRemarkPresent tradeRemarkPresent : tradeRemarkPresentList) {
                presentName = tradeRemarkPresent.getPresentName();
                presentQuantityNum = tradeRemarkPresent.getPresentQuantity();
                allPresentsAndCountStr.append(presentName);
                allPresentsAndCountStr.append(presentQuantityNum);
                presentQuantityCount += presentQuantityNum;
                allPresentNameList.add(presentName);
            }
            expDelivery.setSendContent(allPresentsAndCountStr.toString());
            //托寄物数量
            expDelivery.setSendNum(presentQuantityCount);
            int count = 0;
            for(String presentNameStr:allPresentNameList){
                if(!Validate.isNullOrEmpty(presentNameStr)){
                    presents.append(presentNameStr);
                }
                count++;
                if(count != allPresentNameList.size()){
                    presents.append(",");
                }
            }

            expDelivery.setPresentNames(presents.toString());
        }

        //客户ID
/*        String receiverStatus = tempTrade.getReceiverState();
        String receiverCity = tempTrade.getReceiverCity();
        UpdCpd updCpdObj = cpdDataService.findCpdDataByCityCode(receiverStatus, receiverCity);
        if (null != updCpdObj) {
            expDelivery.setCustomerId(CommonHelper.getString(updCpdObj.getCode()));
        }*/
        expDelivery.setCustomerId(CommonHelper.getString(tempTrade.getCpdCode()));
        //发票抬头
   /*     String invoiceTitle = "";
        TradeRemark tradeRemarkObj = tradeRemarkDao.findInvoiceTitleById(tempTrade.getTradeSysId());
        if (null != tradeRemarkObj) {
            invoiceTitle = CommonHelper.getString(tradeRemarkObj.getInvoiceTitle());
        }*/
        expDelivery.setInvoiceTitle(getInvoiceTitlePriority(tradeRemark, tempTrade.getInvoiceName(), tempTrade.getReceiverName()));
        //开票金额
        //mod by songsong start
        expDelivery.setInvoiceFee(tempTrade.getPayment()-tempTrade.getPointFee() / 100);
        //mod by songsong end
        //模板匹配客服备注简写，获取SKU
        //礼品物料号
        if (CollectionUtils.isEmpty(allPresentNameList)) {
            GiftData giftData = null;
            StringBuffer skuNoSb = new StringBuffer();
            for (String nameStr : allPresentNameList) {
                giftData = giftDataDao.findSkuByname(presentName);
                if (giftData != null) {
                    skuNoSb.append(giftData.getSkuNo());
                    skuNoSb.append(" ");
                }
            }
            expDelivery.setGiftSku(skuNoSb.toString());
        }
        //礼品数量
        if (presentQuantityCount > 0) {
            expDelivery.setGiftNum(presentQuantityCount - 1);
        }
        //订单的创建时间
        expDelivery.setPricingDate(CommonHelper.getTimestamp(tempTrade.getCreated()));
        //商家店铺
        expDelivery.setPointFee(CommonHelper.getDouble(tempTrade.getPointFee()));
        expDelivery.setStoreId(CommonHelper.getString(tempTrade.getStoreId()));
        expDelivery.setOrderStatus(CommonHelper.getString(tempTrade.getStatus()));
        expDelivery.setReceiverState(CommonHelper.getString(tempTrade.getReceiverState()));
        expDelivery.setReceiverCity(CommonHelper.getString(tempTrade.getReceiverCity()));
        expDelivery.setReceiverDistrict(CommonHelper.getString(tempTrade.getReceiverDistrict()));
        return expDelivery;
    }

    /**
     * 根据优先级获取发票抬头
     *
     * @param tradeRemark
     * @param invoiceName
     * @param receiveName
     * @return
     */
    private String getInvoiceTitlePriority(TradeRemark tradeRemark, String invoiceName, String receiveName) {
        if (!Validate.isNullOrEmpty(tradeRemark)) {
            if (!Validate.isNullOrEmpty(tradeRemark.getInvoiceTitle())) {
                return tradeRemark.getInvoiceTitle();
            }
        }
        if (!Validate.isNullOrEmpty(invoiceName)) {
            return invoiceName;
        }
        if (!Validate.isNullOrEmpty(receiveName)) {
            return receiveName;
        }
        return "";
    }


    public ExpFinacial generateExpFinancial(TempTrade tempTrade) {
        ExpFinacial expFinancial = new ExpFinacial();
        try {
            expFinancial.setBldat(CommonHelper.getThisTimestamp()); //Document Date入账时间
            expFinancial.setBudat(CommonHelper.getThisTimestamp()); //Posting Date入账时间
        } catch (Exception e) {
            e.printStackTrace();
        }
        //获取当前月份
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH) + 1;
        expFinancial.setMonat(Integer.toString(month));
        //固定列值
        expFinancial.setBkpf("DZ");
        expFinancial.setBukrs("CN11");
        expFinancial.setWaers("CNY");
        expFinancial.setNewbs("40");
        expFinancial.setNewko(tempTrade.getStoreId());//店铺
        //alipay实际收款金额，所有的金额汇总
        List<Alipay> alipays = alipayDao.findByAlipayNo(tempTrade.getTid());
        Double totalNum = 0.0;
        for (Alipay alipay : alipays) {
            totalNum += alipay.getInFee();
        }
        expFinancial.setWrbtr(totalNum);
        String tid = tempTrade.getTid();
        String tidAndTmStr = tid + "天猫";
        expFinancial.setZuonr(tidAndTmStr);
        String invoiceTitle = "";
        expFinancial.setNewbs1("15");
        TradeRemark tradeRemark = tradeRemarkDao.getByTid(tempTrade.getTid());
        invoiceTitle = getInvoiceTitlePriority(tradeRemark, tempTrade.getInvoiceName(), tempTrade.getReceiverName());
        expFinancial.setInvoiceTitle(invoiceTitle);
        //SGTXT= Text支付宝到账时间+收tmall销售款+天猫订单号+发票抬头
        String sgtxt = tempTrade.getAlipayCreateTime() + "收tmall销售款" + tempTrade.getTid() + invoiceTitle;
        expFinancial.setSgtxt(sgtxt);
        expFinancial.setNewko1(CommonHelper.getString(tempTrade.getCpdCode()));
        expFinancial.setNewko2("6200709245");
        if (invoiceTitle.length() > 4) {
            expFinancial.setName1(invoiceTitle.substring(0, 4));
        } else {
            expFinancial.setName1(invoiceTitle);
        }
        expFinancial.setOrt01(tempTrade.getTid());
        //mod by songsong start
        expFinancial.setWrbtr1(tempTrade.getPayment()-tempTrade.getPointFee() / 100);
        //mod by songsong end
        expFinancial.setZuonr1(tempTrade.getTid() + "天猫");
        expFinancial.setSgtxt1(sgtxt);
        expFinancial.setNewbs2("15");
        expFinancial.setWrbtr2(tempTrade.getPointFee() / 100);//积分/100
        expFinancial.setZuonr2(tidAndTmStr);
        expFinancial.setTid(tempTrade.getTid());
        expFinancial.setPricingDate(tempTrade.getCreated());
        expFinancial.setOrderStatus(CommonHelper.getString(tempTrade.getStatus()));
        expFinancial.setStoreId(CommonHelper.getString(tempTrade.getStoreId()));
        expFinancial.setPointFee(CommonHelper.getDouble(tempTrade.getPointFee()));
        expFinancial.setAlipayNo(CommonHelper.getString(tempTrade.getAlipayNo()));
        String sgtxt2 = "";     //SGTXT2 = SGTXT+/积分/100/博世或空
        if ("22511920".equals(tempTrade.getStoreId())) {
            sgtxt2 = sgtxt + "/" + tempTrade.getPointFee() / 100 + "/博世";
        } else {
            sgtxt2 = sgtxt + "/" + tempTrade.getPointFee() / 100;
        }
        expFinancial.setSgtxt2(sgtxt2);
        return expFinancial;
    }

    @Override
    public JobExec getCurrentJobExec(String jobName) {
        log.info(msg.getStartMessage("getCurrentJobExec jobName:"+jobName));
        JobExec jobExec = null;
        JobExec jobExec1 = getCurrentJobBatchNo(jobName);
        if (!Validate.isNullOrEmpty(jobExec1)) {
            log.info(msg.getProcessMessage("getCurrentJobExec jobExec1:" + jobExec1.toString()));
            jobExec = new JobExec();
            jobExec.setId(jobExec1.getId());
            jobExec.setBatchNo(jobExec1.getBatchNo() + 1);
            jobExec.setJobName(jobName);
            jobExec.setStatus(Constants.JobStatus.process.toString());
            jobExec.setNum(0);
            log.info(msg.getProcessMessage("getCurrentJobExec jobExec:" + jobExec.toString()));
        }
        log.info(msg.getEndMessage("getCurrentJobExec"));
        return jobExec;
    }

    public JobExec getCurrentJobBatchNo(String jobName) {
        List<JobExec> jobExecList = jobExecDao.findByJobName(jobName);
        JobExec jobExec = null;
        if (Validate.isNullOrEmpty(jobExecList)) {
            jobExec = new JobExec();
            jobExec.setBatchNo(0);
            return jobExec;
        }
        jobExec = jobExecList.get(0);
        if (!Validate.isNullOrEmpty(jobExec) && jobExec.getStatus().equals(Constants.JobStatus.process.toString())) {
            return null;
        }
        return jobExec;
    }

    //mod by kalend
    public JobExec getResultNotVirtualByBatch(JobExec jobExec) {
        log.info(msg.getStartMessage("getFinanceResultByBatch"));
        List<TempTrade> tempTradeList = jobExecDao.findByBatchNoNotVirtual(jobExec.getBatchNo());
        if (Validate.isNullOrEmpty(tempTradeList)) {
            log.info(msg.getEndMessage("getFinanceResultByBatch tempTradeList in null"));
            return null;
        }
        return jobExecDao.saveJobExec(jobExec);
    }

    //mod by kalend
    public JobExec getResultByBatch(JobExec jobExec) {
        log.info(msg.getStartMessage("getDeliveryResultByBatch"));
        List<TempTrade> tempTradeList = jobExecDao.findByBatchNo(jobExec.getBatchNo());
        if (Validate.isNullOrEmpty(tempTradeList)) {
            log.info(msg.getEndMessage("getDeliveryResultByBatch tempTradeList is null"));
            return null;
        }
        return jobExecDao.saveJobExec(jobExec);
    }

}
