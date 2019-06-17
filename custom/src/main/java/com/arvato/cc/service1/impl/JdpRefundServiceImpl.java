package com.arvato.cc.service1.impl;

import com.arvato.cc.dao1.JdpRefundDao;
import com.arvato.cc.dao1.TaobaoStoreDao;
import com.arvato.cc.dao1.TradeRefundDao;
import com.arvato.cc.form.JdpRefundBean;
import com.arvato.cc.form.JdpRefundRequest;
import com.arvato.cc.log.LogMessageUtil;
import com.arvato.cc.model.*;
import com.arvato.cc.service1.JdpRefundService;
import com.arvato.cc.util.CallWebServiceUtil;
import com.arvato.cc.util.TaobaoJsonUtil;
import com.arvato.cc.util.Validate;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class JdpRefundServiceImpl implements JdpRefundService {

    private static final Log log = LogFactory.getLog(JdpRefundServiceImpl.class);
    private static LogMessageUtil msg = new LogMessageUtil("JST_ORDER_WS");
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private JdpRefundDao jdpRefundDao;
    @Autowired
    private TradeRefundDao tradeRefundDao;

    @Autowired
    private TaobaoStoreDao taobaoStoreDao;

    @Value("${JST.REFUND.WEBSERVICE}")
    private String webRefundUrl;

    /**
     * 根据数据库查询数据，转换bean
     *
     * @param resultList
     * @return
     */
    private List<JdpRefundBean> generateJdpRefundBeanList(
            List<Map<String, Object>> resultList) {
        List<JdpRefundBean> jdpTradeList = new ArrayList<JdpRefundBean>();
        JdpRefundBean jdpRefund = null;
        for (Map<String, Object> map : resultList) {
            jdpRefund = new JdpRefundBean();
            jdpRefund.setTid(map.get("tid").toString());
            jdpRefund.setOid(map.get("oid").toString());
            jdpRefund.setRefundId(map.get("refund_id").toString());
            jdpRefund.setStatus(map.get("status").toString());
            jdpRefund.setSellerNick(map.get("seller_nick").toString());
            jdpRefund.setBuyerNick(map.get("buyer_nick").toString());
            jdpRefund.setCreated(map.get("created").toString());
            jdpRefund.setModified(map.get("modified").toString());
            jdpRefund.setJdpHashcode(map.get("jdp_hashcode").toString());
            jdpRefund.setJdpResponse(map.get("jdp_response").toString());
            jdpRefund.setJdpCreated(map.get("jdp_created").toString());
            jdpRefund.setJdpModified(map.get("jdp_modified").toString());
            jdpTradeList.add(jdpRefund);
        }
        return jdpTradeList;
    }

    /**
     * 查询这批次是否有数据
     *
     * @return
     * @throws java.sql.SQLException
     */
    @Override
    public List<Map<String, Object>> findJdpRefundByBatch(Calendar startTime,Date endTime)
            throws SQLException {
        StopWatch fetchJST = new StopWatch("findJdpRefundByBatch");
        log.info(msg.getStartMessage("findJdpRefundByBatch"));

        //上次拉单的时间
        List<Map<String, Object>> resultList = null;

        StringBuffer sql = new StringBuffer(
                "select jdp.refund_id,jdp.seller_nick,jdp.buyer_nick,jdp.status,jdp.created,jdp.tid,jdp.oid,jdp.modified,jdp.jdp_response,jdp.jdp_hashcode,jdp.jdp_created,jdp.jdp_modified from jdp_tb_refund jdp where not EXISTS (select refund_id from bsh_ops.TRADE_REFUND_ERROR refundError where jdp.refund_id = refundError.refund_id )");
        if (!Validate.isNullOrEmpty(startTime)) {
            log.info(msg.getProcessMessage("startTime:" + sdf.format(startTime.getTime())));
            sql.append(" and jdp.jdp_modified > '" + Timestamp.valueOf(sdf.format(startTime.getTime())) + "'");
        }
        if (!Validate.isNullOrEmpty(endTime)) {
            log.info(msg.getProcessMessage("endTime:" + sdf.format(endTime)));
            sql.append(" and jdp.jdp_modified < '" + Timestamp.valueOf(sdf.format(endTime)) + "'");
        }
        sql.append(" order by jdp.jdp_modified ");
        log.info(msg.getProcessMessage("findJdpRefundByBatch sql:" + sql.toString()));
        fetchJST.start("fetchJstTrade");
        resultList = jdpRefundDao.fetchJstRefund(sql);
        fetchJST.stop();
        log.info(msg.getProcessMessage("findJdpTradeByBatch fetchJstTrade StopWatch:\r\n" + fetchJST.prettyPrint()));
        log.info(msg.getEndMessage("findJdpTradeByBatch"));
        return resultList;
    }

//    @Override
//    public List<JdpTrade> findByBatchNo(int batchNo) {
//        return jdpTradeDao.findByBatchNo(batchNo);
//    }

    /**
     * 获取聚石塔退款单
     *
     * @return
     */
    public String fetchJstRefund(List<Map<String, Object>> resultList,Integer batchNo) throws Exception {
        StopWatch fetchJST = new StopWatch("fetchJstRefund");
        log.info(msg.getStartMessage("fetchJstRefund"));
        log.info(msg.getProcessMessage("fetchJstRefund batchNo:" + batchNo));
        List<JdpRefundBean> jdpRefundList = generateJdpRefundBeanList(resultList);
        JSONArray jsonArray = JSONArray.fromObject(jdpRefundList);
        JSONObject jsonObject = new JSONObject();
        log.info(msg.getProcessMessage("fetchJstRefund batchNo++:" + batchNo));
        jsonObject.put("batchNo", batchNo);
        jsonObject.put("jdpRefundList", jsonArray);
        List<Object> providers = new ArrayList<Object>();
        providers.add(new JacksonJaxbJsonProvider());
        fetchJST.start("callWebService");
        String response = new CallWebServiceUtil().callWebService(jsonObject,webRefundUrl);
        fetchJST.stop();
        log.info(msg.getProcessMessage("fetchJstRefund callWebService StopWatch:\r\n" + fetchJST.prettyPrint()));
        log.info(msg.getEndMessage("fetchJstRefund callWebService response:" + response));
        return response;
    }

//    @Override
//    public Map<String, JdpTrade> findAllByMap() {
//        Map<String, JdpTrade> jdpTradeMap = new HashMap<String, JdpTrade>();
//        List<JdpTrade> jdpTradeList = jdpTradeDao.findAll();
//        for (JdpTrade jdpTrade : jdpTradeList) {
//            jdpTradeMap.put(jdpTrade.getTid(), jdpTrade);
//        }
//        return jdpTradeMap;
//    }

    /**
     * 获取发生
     *
     * @return
     * @throws java.sql.SQLException
     */
//    @Override
//    public List<Map<String, Object>> findJdpTradeByError(String tids) throws SQLException {
//        log.info(msg.getStartMessage("findJdpTradeByError"));
//        StopWatch fetchJST = new StopWatch("findJdpTradeByError");
//        StringBuffer sql = new StringBuffer(
//                "SELECT tid,status,type,seller_nick,buyer_nick,created,modified,jdp_hashcode,jdp_response,jdp_created,jdp_modified FROM sys_info.jdp_tb_trade jdp WHERE tid in (" + tids + ")");
//        log.info(msg.getProcessMessage("sql:" + sql));
//        fetchJST.start("findError");
//        List<Map<String, Object>> resultList = jdpTradeDao.fetchJstTrade(sql);
//        fetchJST.stop();
//        log.info(msg.getEndMessage("findJdpTradeByError:\r\n" + fetchJST.prettyPrint()));
//        return resultList;
//    }


    @Override
    public JdpRefundRequest saveSyncJdpRefund(List<JdpRefund> jdpRefundList, Integer batchNo) {
        log.info(msg.getStartMessage("saveSyncJdpTrade"));
        JdpRefundRequest jdpTradeRequest = new JdpRefundRequest();

        List<String> tidList = new ArrayList<String>();
        TaobaoStore taobaoStore = null;

        for (JdpRefund jdpRefundMod : jdpRefundList) {
            try {
                jdpRefundDao.saveJdpRefund(jdpRefundMod);

                String response = jdpRefundMod.getJdpResponse();

                TradeRefund tradeRefund = TaobaoJsonUtil.generateTradeRefund(response);
                tradeRefundDao.deleteByRefundId(tradeRefund.getRefundId());
                if(!Validate.isNullOrEmpty(tradeRefund.getSellerNick())){
                    taobaoStore = taobaoStoreDao.getStoreCodeByName(tradeRefund.getSellerNick());
                    if(!Validate.isNullOrEmpty(taobaoStore)){
                        tradeRefund.setStoreId(taobaoStore.getCode());
                    }
                }
                //get current batchNo in webservice
                tradeRefund.setBatchNo(batchNo);

                tradeRefundDao.saveTradeRefund(tradeRefund);
            } catch (Exception ex) {
//                log.error(msg.getErrorMessage(ex.getMessage()), ex);
                ex.printStackTrace();
                tidList.add(jdpRefundMod.getRefundId());
            }
        }
        jdpTradeRequest.setRefundIdList(tidList);
        log.info(msg.getEndMessage("saveSyncJdpTrade"));
        return jdpTradeRequest;
    }
}
