package com.arvato.cc.JstWebService.service;

import com.arvato.cc.dao1.JdpRefundDao;
import com.arvato.cc.dao1.JdpTradeDao;
import com.arvato.cc.dao1.TempTradeDao;
import com.arvato.cc.dao1.TradeRemarkDao;
import com.arvato.cc.form.JdpRefundRequest;
import com.arvato.cc.form.JdpTradeRequest;
import com.arvato.cc.log.LogMessageUtil;
import com.arvato.cc.model.JdpRefund;
import com.arvato.cc.model.JdpTrade;
import com.arvato.cc.model.TempTrade;
import com.arvato.cc.model.TradeRemark;
import com.arvato.cc.service1.JdpRefundService;
import com.arvato.cc.service1.JdpTradeService;
import com.arvato.cc.service1.TempTradeService;
import com.arvato.cc.util.TaobaoJsonUtil;
import com.arvato.cc.util.Validate;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StopWatch;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: caol005
 * Date: 15-8-14
 * Time: 上午9:19
 * To change this template use File | Settings | File Templates.
 */
@Path("/test")
public class JstWebServiceImpl {
    private static final Log log = LogFactory.getLog(JstWebServiceImpl.class);
    private static LogMessageUtil msg = new LogMessageUtil("JST_ORDER_WS");
    private static LogMessageUtil errorMsg = new LogMessageUtil("SEND_ERROR_MAIL");
    @Autowired
    private JdpTradeDao jdpTradeDao;
    @Autowired
    private JdpRefundDao jdpRefundDao;

    @Autowired
    private JdpTradeService jdpTradeService;
    @Autowired
    private JdpRefundService jdpRefundService;

    @GET
    @Path("/{jsonMsg}")
    public String pushJstTrade(@PathParam("jsonMsg") String jsonMsg) {
        System.out.println(jsonMsg);
        return null;
    }

    @POST
    @Path("/sync")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String syncJdpTradeByBatchNo(String jdpRequestBean) {
        log.info(msg.getStartMessage("syncJdpTradeByBatchNo"));
        StopWatch fetchJST = new StopWatch("syncJdpTradeByBatchNo");
        fetchJST.start("jdpRequestBean to JSONObject");
        JSONObject jsonObject = JSONObject.fromObject(jdpRequestBean);
        fetchJST.stop();
        fetchJST.start("findMaxBatchNo");
        Integer batchNo = 0;
        //查询最大的batch_no
        if (!Validate.isNullOrEmpty(jdpTradeDao.findMaxBatchNo())) {
            batchNo = jdpTradeDao.findMaxBatchNo();
        }
        fetchJST.stop();

        JSONArray tradeList = jsonObject.getJSONArray("jdpTradeList");
        JdpTrade jdpTrade = null;
        JSONObject trade = null;
        List<JdpTrade> jdpTradeList = new ArrayList<JdpTrade>();
        batchNo++;
        fetchJST.start("for tradeList Size:" + tradeList.size());
        for (int i = 0; i < tradeList.size(); i++) {
            StopWatch fetchJOR = new StopWatch("tradeListFor");
            boolean flag = true;
            trade = (JSONObject) tradeList.get(i);
            String tidForJst = TaobaoJsonUtil.jsonObjectToString(trade.get("tid"));

            if (flag) {
                fetchJOR.start("flag is true:" + batchNo);
                jdpTrade = new JdpTrade();
                jdpTrade.setBuyerNick(TaobaoJsonUtil.jsonObjectToString(trade.get("buyerNick")));
                jdpTrade.setCreated(TaobaoJsonUtil.jsonObjectToTimestamp(trade.get("created"),"yyyy-MM-dd hh:mm:ss"));
                jdpTrade.setModified(TaobaoJsonUtil.jsonObjectToTimestamp(trade.get("modified"),"yyyy-MM-dd hh:mm:ss"));
                jdpTrade.setJdpCreated(TaobaoJsonUtil.jsonObjectToTimestamp(trade.get("jdpCreated"),"yyyy-MM-dd hh:mm:ss"));
                jdpTrade.setJdpHashcode(TaobaoJsonUtil.jsonObjectToString(trade.get("jdpHashcode")));
                jdpTrade.setJdpModified(TaobaoJsonUtil.jsonObjectToTimestamp(trade.get("jdpModified"),"yyyy-MM-dd hh:mm:ss"));
                jdpTrade.setJdpResponse(TaobaoJsonUtil.jsonObjectToString(trade.get("jdpResponse")));
                jdpTrade.setStatus(TaobaoJsonUtil.jsonObjectToString(trade.get("status")));
                jdpTrade.setTid(tidForJst);
                jdpTrade.setType(TaobaoJsonUtil.jsonObjectToString(trade.get("type")));
                jdpTrade.setSellerNick(TaobaoJsonUtil.jsonObjectToString(trade.get("sellerNick")));
                jdpTrade.setBatchNo(batchNo);
                jdpTradeList.add(jdpTrade);
                fetchJOR.stop();
            }
            log.info(msg.getProcessMessage("tradeListFor \r\n" + fetchJOR.prettyPrint()));
        }
        fetchJST.stop();
        fetchJST.start("save db:" + batchNo);
        JdpTradeRequest jdpTradeRequest = jdpTradeService.saveSyncJdpTrade(jdpTradeList,batchNo);
        if(Validate.isNullOrEmpty(jdpTradeRequest.getTidList())){
            jdpTradeRequest.setFlag(true);
        }else {
            jdpTradeRequest.setFlag(false);
            log.error(errorMsg.getErrorMessage("jdpTradeRequest:"+jdpTradeRequest.toString()));
        }
        fetchJST.stop();
        log.info(msg.getEndMessage("syncJdpTradeByBatchNo \r\n" + fetchJST.prettyPrint()));
        return JSONObject.fromObject(jdpTradeRequest).toString();
    }

    @POST
    @Path("/syncRefund")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String syncJdpRefundByBatchNo(String jdpRequestBean) {
        log.info(msg.getStartMessage("syncJdpRefundByBatchNo"));
        StopWatch fetchJST = new StopWatch("syncJdpRefundByBatchNo");
        fetchJST.start("jdpRequestBean to JSONObject");
        JSONObject jsonObject = JSONObject.fromObject(jdpRequestBean);
        fetchJST.stop();
        fetchJST.start("findMaxBatchNo");
        Integer batchNo = 0;
        //查询最大的batch_no
        if (!Validate.isNullOrEmpty(jdpRefundDao.findMaxBatchNo())) {
            batchNo = jdpTradeDao.findMaxBatchNo();
        }
        fetchJST.stop();

        JSONArray refundList = jsonObject.getJSONArray("jdpRefundList");
        JdpRefund jdpRefund = null;
        JSONObject refund = null;
        List<JdpRefund> jdpRefundList = new ArrayList<JdpRefund>();
        batchNo++;
        fetchJST.start("for refundList Size:" + refundList.size());
        for (int i = 0; i < refundList.size(); i++) {
            StopWatch fetchJOR = new StopWatch("RefundListFor");
            boolean flag = true;
            refund = (JSONObject) refundList.get(i);
            String tidForJst = TaobaoJsonUtil.jsonObjectToString(refund.get("tid"));

            if (flag) {
                fetchJOR.start("flag is true:" + batchNo);
                jdpRefund = new JdpRefund();
                jdpRefund.setOid(TaobaoJsonUtil.jsonObjectToString(refund.get("oid")));
                jdpRefund.setRefundId(TaobaoJsonUtil.jsonObjectToString(refund.get("refundId")));
                jdpRefund.setBuyerNick(TaobaoJsonUtil.jsonObjectToString(refund.get("buyerNick")));
                jdpRefund.setCreated(TaobaoJsonUtil.jsonObjectToTimestamp(refund.get("created"), "yyyy-MM-dd hh:mm:ss"));
                jdpRefund.setModified(TaobaoJsonUtil.jsonObjectToTimestamp(refund.get("modified"), "yyyy-MM-dd hh:mm:ss"));
                jdpRefund.setJdpCreated(TaobaoJsonUtil.jsonObjectToTimestamp(refund.get("jdpCreated"), "yyyy-MM-dd hh:mm:ss"));
                jdpRefund.setJdpHashcode(TaobaoJsonUtil.jsonObjectToString(refund.get("jdpHashcode")));
                jdpRefund.setJdpModified(TaobaoJsonUtil.jsonObjectToTimestamp(refund.get("jdpModified"), "yyyy-MM-dd hh:mm:ss"));
                jdpRefund.setJdpResponse(TaobaoJsonUtil.jsonObjectToString(refund.get("jdpResponse")));
                jdpRefund.setStatus(TaobaoJsonUtil.jsonObjectToString(refund.get("status")));
                jdpRefund.setTid(tidForJst);
                jdpRefund.setSellerNick(TaobaoJsonUtil.jsonObjectToString(refund.get("sellerNick")));
                jdpRefund.setBatchNo(batchNo);
                jdpRefundList.add(jdpRefund);
                fetchJOR.stop();
            }
            log.info(msg.getProcessMessage("RefundListFor \r\n" + fetchJOR.prettyPrint()));
        }
        fetchJST.stop();
        fetchJST.start("save db:" + batchNo);
        JdpRefundRequest jdpRefundRequest = jdpRefundService.saveSyncJdpRefund(jdpRefundList, batchNo);
        if(Validate.isNullOrEmpty(jdpRefundRequest.getRefundIdList())){
            jdpRefundRequest.setFlag(true);
        }else {
            jdpRefundRequest.setFlag(false);
            log.error(errorMsg.getErrorMessage("jdpRefundRequest:"+jdpRefundRequest.toString()));
        }
        fetchJST.stop();
        log.info(msg.getEndMessage("syncJdpTradeByBatchNo \r\n" + fetchJST.prettyPrint()));
        return JSONObject.fromObject(jdpRefundRequest).toString();
    }
}
