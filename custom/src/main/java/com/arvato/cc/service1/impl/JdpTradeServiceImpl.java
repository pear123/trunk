package com.arvato.cc.service1.impl;

import com.arvato.cc.dao1.*;
import com.arvato.cc.form.CheckJstTrade;
import com.arvato.cc.form.JdpTradeBean;
import com.arvato.cc.form.JdpTradeRequest;
import com.arvato.cc.log.LogMessageUtil;
import com.arvato.cc.model.*;
import com.arvato.cc.service1.JdpTradeService;
import com.arvato.cc.util.TaobaoJsonUtil;
import com.arvato.cc.util.Validate;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.AuthCache;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.cert.CertificateException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class JdpTradeServiceImpl implements JdpTradeService {

    private static final Log log = LogFactory.getLog(JdpTradeServiceImpl.class);
    private static LogMessageUtil msg = new LogMessageUtil("JST_ORDER_WS");

    private static SimpleDateFormat sdf = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");

    @Autowired
    private JdpTradeDao jdpTradeDao;

    @Autowired
    private TempTradeDao tempTradeDao;

    @Autowired
    private TradeRemarkDao tradeRemarkDao;

    @Autowired
    private TaobaoStoreDao taobaoStoreDao;

    @Autowired
    private JobExecDao jobExecDao;

    @Value("${JST.WEBSERVICE}")
    private String webUrl;

    /**
     * 调用webservice
     *
     * @param jsonObject
     * @return
     */
    private String callFetchTradeWebService(JSONObject jsonObject)
            throws Exception {
        log.info(msg.getStartMessage("callFetchTradeWebService"));
//  List<Object> providers = new ArrayList<Object>();
//  providers.add(new JacksonJaxbJsonProvider());
//          String jsonString = WebClient.create(webUrl, providers)
//                  .path("/sync")
//                  .accept(MediaType.APPLICATION_JSON).type(
//                          MediaType.APPLICATION_JSON).post(jsonObject.toString(),
//                          String.class);

        String jsonString = "";
        enableSSL();
        URL url = new URL(webUrl);

        String domain = url.getHost();
        log.info(msg.getProcessMessage("domain:" + domain));
        HttpHost target = new HttpHost(domain, 443, "https");

        RequestConfig defaultRequestConfig = RequestConfig.custom().setCookieSpec(
                CookieSpecs.STANDARD_STRICT)
                .setExpectContinueEnabled(true).setTargetPreferredAuthSchemes(
                        Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST))
                .setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC))
                .build();
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder
                .<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE)
                .register("https", JdpTradeServiceImpl.sockerFactory).build();
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(
                socketFactoryRegistry);

        CloseableHttpClient httpclient = HttpClients.custom()
                .setConnectionManager(connectionManager).setDefaultRequestConfig(
                        defaultRequestConfig).build();

        try {

            // Create AuthCache instance
            AuthCache authCache = new BasicAuthCache();
            // Generate BASIC scheme object and add it to the local
            // auth cache
            BasicScheme basicAuth = new BasicScheme();
            authCache.put(target, basicAuth);

            // Add AuthCache to the execution context
            HttpClientContext localContext = HttpClientContext.create();
            localContext.setAuthCache(authCache);

            log.info(msg.getProcessMessage("webUrl:" + webUrl));

            HttpPost httpPost = new HttpPost(webUrl);
            httpPost.addHeader(HTTP.CONTENT_TYPE,
                    ContentType.APPLICATION_JSON.getMimeType());

            StringEntity se = new StringEntity(jsonObject.toString(),
                    ContentType.APPLICATION_JSON.getCharset());
            httpPost.setEntity(se);
            log.info(msg.getProcessMessage(
                    "Executing request " + httpPost.getRequestLine() + " to target "
                            + target));
            StopWatch fetchJST = new StopWatch("httpclient");
            fetchJST.start(webUrl);
            HttpResponse response = httpclient.execute(target, httpPost, localContext);
            fetchJST.stop();
            log.info(msg.getProcessMessage(fetchJST.prettyPrint()));
            jsonString = JdpTradeServiceImpl
                    .inputStream2String(response.getEntity().getContent());
            EntityUtils.consume(response.getEntity());
        } catch (Exception e) {
            log.error(msg.getErrorMessage(e.getMessage()), e);
            throw e;
        } finally {
            httpclient.close();
        }
        log.info(msg.getEndMessage("callFetchTradeWebService:" + jsonString));
        return jsonString;
    }

    public static String inputStream2String(InputStream in) throws IOException {
        StringBuffer out = new StringBuffer();
        byte[] b = new byte[4096];
        for (int n; (n = in.read(b)) != -1; ) {
            out.append(new String(b, 0, n, "utf-8"));
        }
        return out.toString();
    }

    private static void enableSSL() {
        //调用ssl
        try {
            SSLContext sslcontext = SSLContext.getInstance("TLS");
            sslcontext.init(null, new TrustManager[]{truseAllManager}, null);
            sockerFactory = new SSLConnectionSocketFactory(sslcontext,
                    NoopHostnameVerifier.INSTANCE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static SSLConnectionSocketFactory sockerFactory;
    /**
     * 重写验证方法，取消检测ssl
     */
    private static TrustManager truseAllManager = new X509TrustManager() {

        public void checkClientTrusted(
                java.security.cert.X509Certificate[] arg0, String arg1)
                throws CertificateException {

        }

        public void checkServerTrusted(
                java.security.cert.X509Certificate[] arg0, String arg1)
                throws CertificateException {

        }

        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return null;
        }

    };

    /**
     * 根据数据库查询数据，转换bean
     *
     * @param resultList
     * @return
     */
    private List<JdpTradeBean> generateJdpTradeBeanList(
            List<Map<String, Object>> resultList) {
        List<JdpTradeBean> jdpTradeList = new ArrayList<JdpTradeBean>();
        JdpTradeBean jdpTrade = null;
        for (Map<String, Object> map : resultList) {
            jdpTrade = new JdpTradeBean();
            jdpTrade.setTid(map.get("tid").toString());
            jdpTrade.setStatus(map.get("status").toString());
            jdpTrade.setType(map.get("type").toString());
            jdpTrade.setSellerNick(map.get("seller_nick").toString());
            jdpTrade.setBuyerNick(map.get("buyer_nick").toString());
            jdpTrade.setCreated(map.get("created").toString());
            jdpTrade.setModified(map.get("modified").toString());
            jdpTrade.setJdpHashcode(map.get("jdp_hashcode").toString());
            jdpTrade.setJdpResponse(map.get("jdp_response").toString());
            jdpTrade.setJdpCreated(map.get("jdp_created").toString());
            jdpTrade.setJdpModified(map.get("jdp_modified").toString());
            jdpTradeList.add(jdpTrade);
        }
        return jdpTradeList;
    }

    /**
     * 查询这批次是否有数据
     *
     * @return
     * @throws SQLException
     */
    @Override
    public List<Map<String, Object>> findJdpTradeByBatch(Calendar startTime,Date endTime)
            throws SQLException {
        StopWatch fetchJST = new StopWatch("findJdpTradeByBatch");
        log.info(msg.getStartMessage("findJdpTradeByBatch"));

        //上次拉单的时间
        List<Map<String, Object>> resultList = null;

        StringBuffer sql = new StringBuffer(
                "select jdp.tid,jdp.status,jdp.type,jdp.seller_nick,jdp.buyer_nick,jdp.created,jdp.modified,jdp.jdp_hashcode,jdp.jdp_response,jdp.jdp_created,jdp.jdp_modified from sys_info.jdp_tb_trade jdp where NOT EXISTS (SELECT ID, model, sub_model, operate_time, operate_op FROM bsh_ops.current_operation bcp WHERE bcp.sub_model ='N' AND jdp.tid = bcp.model)");
        if (!Validate.isNullOrEmpty(startTime)) {
            log.info(msg.getProcessMessage("startTime:" + sdf.format(startTime.getTime())));
            sql.append(" and jdp.jdp_modified > '" + Timestamp.valueOf(sdf.format(startTime.getTime())) + "'");
        }
        if (!Validate.isNullOrEmpty(endTime)) {
            log.info(msg.getProcessMessage("endTime:" + sdf.format(endTime)));
            sql.append(" and jdp.jdp_modified < '" + Timestamp.valueOf(sdf.format(endTime)) + "'");
        }
        sql.append(" order by jdp.jdp_modified ");
        log.info(msg.getProcessMessage("findJdpTradeByBatch sql:" + sql.toString()));
        fetchJST.start("fetchJstTrade");
        resultList = jdpTradeDao.fetchJstTrade(sql);
        fetchJST.stop();
        log.info(msg.getProcessMessage("findJdpTradeByBatch fetchJstTrade StopWatch:\r\n" + fetchJST.prettyPrint()));
        log.info(msg.getEndMessage("findJdpTradeByBatch"));
        return resultList;
    }

    @Override
    public List<JdpTrade> findByBatchNo(int batchNo) {
        return jdpTradeDao.findByBatchNo(batchNo);
    }

    /**
     * 获取聚石塔订单
     *
     * @return
     */
    public String fetchJstTrade(List<Map<String, Object>> resultList,Integer batchNo) throws Exception {
        StopWatch fetchJST = new StopWatch("fetchJstTrade");
        log.info(msg.getStartMessage("fetchJstTrade"));
        log.info(msg.getProcessMessage("fetchJstTrade batchNo:" + batchNo));
        List<JdpTradeBean> jdpTradeList = generateJdpTradeBeanList(resultList);
        JSONArray jsonArray = JSONArray.fromObject(jdpTradeList);
        JSONObject jsonObject = new JSONObject();
        log.info(msg.getProcessMessage("fetchJstTrade batchNo++:" + batchNo));
        jsonObject.put("batchNo", batchNo);
        jsonObject.put("jdpTradeList", jsonArray);
        List<Object> providers = new ArrayList<Object>();
        providers.add(new JacksonJaxbJsonProvider());
        fetchJST.start("callFetchTradeWebService");
        String response = callFetchTradeWebService(jsonObject);
        fetchJST.stop();
        log.info(msg.getProcessMessage("fetchJstTrade callFetchTradeWebService StopWatch:\r\n" + fetchJST.prettyPrint()));
        log.info(msg.getEndMessage("fetchJstTrade callFetchTradeWebService response:" + response));
        return response;
    }

    @Override
    public Map<String, JdpTrade> findAllByMap() {
        Map<String, JdpTrade> jdpTradeMap = new HashMap<String, JdpTrade>();
        List<JdpTrade> jdpTradeList = jdpTradeDao.findAll();
        for (JdpTrade jdpTrade : jdpTradeList) {
            jdpTradeMap.put(jdpTrade.getTid(), jdpTrade);
        }
        return jdpTradeMap;
    }

    /**
     * 获取发生
     *
     * @return
     * @throws SQLException
     */
    @Override
    public List<Map<String, Object>> findJdpTradeByError(String tids) throws SQLException {
        log.info(msg.getStartMessage("findJdpTradeByError"));
        StopWatch fetchJST = new StopWatch("findJdpTradeByError");
        StringBuffer sql = new StringBuffer(
                "SELECT tid,status,type,seller_nick,buyer_nick,created,modified,jdp_hashcode,jdp_response,jdp_created,jdp_modified FROM sys_info.jdp_tb_trade jdp WHERE tid in (" + tids + ")");
        log.info(msg.getProcessMessage("sql:" + sql));
        fetchJST.start("findError");
        List<Map<String, Object>> resultList = jdpTradeDao.fetchJstTrade(sql);
        fetchJST.stop();
        log.info(msg.getEndMessage("findJdpTradeByError:\r\n" + fetchJST.prettyPrint()));
        return resultList;
    }


    @Override
    public JdpTradeRequest saveSyncJdpTrade(List<JdpTrade> jdpTradeList, Integer batchNo) {
        log.info(msg.getStartMessage("saveSyncJdpTrade"));
        JdpTradeRequest jdpTradeRequest = new JdpTradeRequest();

        List<String> tidList = new ArrayList<String>();
        TaobaoStore taobaoStore = null;

        for (JdpTrade jdpTradeMod : jdpTradeList) {
            try {
                jdpTradeDao.saveJdpTrade(jdpTradeMod);

                String response = jdpTradeMod.getJdpResponse();
                TempTrade tempTrade = TaobaoJsonUtil.generateTempTrade(response);
                log.debug(msg.getProcessMessage("单号:" + tempTrade.getTid() + ",红包:" + tempTrade.getPaidCouponFee()));
                if(!Validate.isNullOrEmpty(tempTrade.getSellerNick())){
                    taobaoStore = taobaoStoreDao.getStoreCodeByName(tempTrade.getSellerNick());
                    if(!Validate.isNullOrEmpty(taobaoStore)){
                        tempTrade.setStoreId(taobaoStore.getCode());
                    }
                }
                //get current batchNo in webservice
                tempTrade.setBatchNo(batchNo);

                //get remark start

                tradeRemarkDao.deleteTradeRemark(tempTrade.getTid());
                TradeRemark tradeRemark = TaobaoJsonUtil.getTradeRemarkBySellerMemo(tempTrade.getSellerMemo());
                if (!Validate.isNullOrEmpty(tradeRemark)) {
                    tradeRemark.setTid(tempTrade.getTid());
                    tradeRemarkDao.saveTradeRemark(tradeRemark);
                }

                tempTradeDao.saveTempTrade(tempTrade);
            } catch (Exception ex) {
                log.error(msg.getErrorMessage(ex.getMessage()), ex);
                tidList.add(jdpTradeMod.getTid());
            }
        }
        jdpTradeRequest.setTidList(tidList);
        log.info(msg.getEndMessage("saveSyncJdpTrade"));
        return jdpTradeRequest;
    }



    /**
     * 查询这批次是否有数据
     *
     * @return
     * @throws SQLException
     */
    @Override
    public List<Map<String, Object>> findJdpTradeBySeller(Date startTime,Date endTime,String seller,String tid)
            throws SQLException {
        StopWatch fetchJST = new StopWatch("findJdpTradeByBatch");
        log.info(msg.getStartMessage("findJdpTradeByBatch"));
        //查询当前最大的batch
        //        Integer batchNo = jdpTradeDao.findMaxBatchNo();

        //上次拉单的时间
        List<Map<String, Object>> resultList = null;

        StringBuffer sql = new StringBuffer(
                "select jdp.tid,jdp.status,jdp.type,jdp.seller_nick,jdp.buyer_nick,jdp.created,jdp.modified,jdp.jdp_hashcode,jdp.jdp_response,jdp.jdp_created,jdp.jdp_modified from sys_info.jdp_tb_trade jdp where 1=1 AND jdp.status IN ('TRADE_FINISHED','TRADE_CLOSED') ");
        if(!Validate.isNullOrEmpty(seller)){
            log.info(msg.getProcessMessage("seller:" + seller));
            sql.append(" and jdp.seller_nick LIKE '%"+seller+"%'");
        }
        if(!Validate.isNullOrEmpty(tid)){
            log.info(msg.getProcessMessage("tid:" + tid));
            sql.append(" and jdp.tid ="+tid);
        }
        if (!Validate.isNullOrEmpty(startTime)) {
            log.info(msg.getProcessMessage(
                    "startTime:" + sdf.format(startTime)));
            sql.append(" and jdp.jdp_modified >= '" + Timestamp
                    .valueOf(sdf.format(startTime)) + "'");
        }
        if (!Validate.isNullOrEmpty(endTime)) {
            log.info(msg.getProcessMessage("endTime:" + sdf.format(endTime)));
            sql.append(" and jdp.jdp_modified <= '" + Timestamp
                    .valueOf(sdf.format(endTime)) + "'");
        }
        sql.append(" order by jdp.jdp_modified ");

        log.info(msg.getProcessMessage(
                "findJdpTradeByBatch sql:" + sql.toString()));
        fetchJST.start("fetchJstTrade");
        resultList = jdpTradeDao.fetchJstTrade(sql);
        fetchJST.stop();
        log.info(msg.getProcessMessage(
                "findJdpTradeByBatch fetchJstTrade StopWatch:\r\n" + fetchJST
                        .prettyPrint()));
        log.info(msg.getEndMessage("findJdpTradeByBatch"));
        return resultList;
    }

    /**
     * 检查时间段内是否存在漏单
     * @return
     */
    @Override
    public CheckJstTrade checkJstTrade() {
        CheckJstTrade checkJstTrade = new CheckJstTrade();
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(new Date());
        System.out.println(calendar.get(Calendar.DAY_OF_MONTH));//今天的日期
        calendar.set(Calendar.DAY_OF_MONTH,calendar.get(Calendar.DAY_OF_MONTH)-1);
        System.out.println(calendar.get(Calendar.DATE));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdfstart = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        SimpleDateFormat sdfend = new SimpleDateFormat("yyyy-MM-dd 23:59:59");

        String queryParam =  "and jdp_modified>='"+Timestamp.valueOf(sdfstart.format(calendar.getTime()))+"' and jdp_modified <= '"+Timestamp.valueOf(sdfend.format(calendar.getTime()))+"'";

        StringBuffer jdpTradeSql = new StringBuffer("select count(distinct tid) as num from sys_info.jdp_tb_trade where 1=1 ");
        jdpTradeSql.append(queryParam);

        //mod by kalend
        StringBuffer ccTradeSql = new StringBuffer("select count(distinct tid) as num from jdp_trade where 1=1 ");
        ccTradeSql.append(queryParam);
        //mod by kalend
        String compareSql = "select str.tid,str.jdp_modified from sys_info.jdp_tb_trade str where not EXISTS (" +
                "    select * from jdp_trade ctr where ctr.jdp_modified>='"+Timestamp.valueOf(sdfstart.format(calendar.getTime()))+"' and ctr.jdp_modified <= '"+Timestamp.valueOf(sdfend.format(calendar.getTime()))+"'" +
                "    and str.tid = ctr.tid ) and str.jdp_modified>='"+Timestamp.valueOf(sdfstart.format(calendar.getTime()))+"' and str.jdp_modified <= '"+Timestamp.valueOf(sdfend.format(calendar.getTime()))+"'";
        try{
            String jdpTradeNum = jdpTradeDao.findTradeNum(jdpTradeSql.toString());
            String ccTradeNum = jdpTradeDao.findTradeNum(ccTradeSql.toString());

            List<Object[]> compareDate = jdpTradeDao.findCompareDate(compareSql);
            checkJstTrade.setFindTime(sdf.format(calendar.getTime()));

            if(!Validate.isNullOrEmpty(jdpTradeNum)){
                checkJstTrade.setJstTradeNum(jdpTradeNum);

            }
            if(!Validate.isNullOrEmpty(ccTradeNum)){
                checkJstTrade.setCcTradeNum(ccTradeNum);
            }


            if(!Validate.isNullOrEmpty(compareDate)){
                List<JdpTradeBean> jdpTradeBeans = new ArrayList<JdpTradeBean>();
                for(Object[] obj:compareDate){
                    JdpTradeBean jdpTradeBean = new JdpTradeBean();
                    jdpTradeBean.setTid(String.valueOf(obj[0]));
                    jdpTradeBean.setJdpModified(String.valueOf(obj[1]));
                    jdpTradeBeans.add(jdpTradeBean);
                }
                checkJstTrade.setJdpTrades(jdpTradeBeans);
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return checkJstTrade;
    }

    //mod by kalend
    @Override
    public JobExec getResultByBatch(JobExec jobExec) {
        log.info(msg.getStartMessage("getResultByBatch"));
        List<JdpTrade> jdpTradeList = jdpTradeDao.findByBatchNo(jobExec.getBatchNo());
        if (Validate.isNullOrEmpty(jdpTradeList)) {
            log.info(msg.getEndMessage("getResultByBatch tempTradeList is null"));
            return null;
        }
        return jobExecDao.saveJobExec(jobExec);
    }
}
