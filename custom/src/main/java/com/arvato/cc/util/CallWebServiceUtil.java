package com.arvato.cc.util;

import com.arvato.cc.log.LogMessageUtil;
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
import org.springframework.util.StopWatch;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.cert.CertificateException;
import java.util.Arrays;

public class CallWebServiceUtil {

    private static SSLConnectionSocketFactory sockerFactory;
    private static final Log log = LogFactory.getLog(CallWebServiceUtil.class);
    private static LogMessageUtil msg = new LogMessageUtil("JST_ORDER");

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
     * 调用webservice
     *
     * @param jsonObject
     * @return
     */
    public String callWebService(JSONObject jsonObject,String webUrl)
            throws Exception {
        log.info(msg.getStartMessage("callWebService"));
//  List<Object> providers = new ArrayList<Object>();
//  providers.add(new JacksonJaxbJsonProvider());
//          String jsonString = WebClient.create(webUrl, providers)
//                  .path("/syncRefund")
//                  .accept(MediaType.APPLICATION_JSON).type(
//                          MediaType.APPLICATION_JSON).post(jsonObject.toString(),
//                          String.class);
//          return jsonString;
        String jsonString = "";
        enableSSL();
        URL url = new URL(webUrl);

        String domain = url.getHost();
        log.info(msg.getProcessMessage("domain:" + domain));
        HttpHost target = new HttpHost(domain, 443, "https");

        RequestConfig defaultRequestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD_STRICT)
                .setExpectContinueEnabled(true).setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST))
                .setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC))
                .build();
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder
                .<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE)
                .register("https", CallWebServiceUtil.sockerFactory).build();
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);

        CloseableHttpClient httpclient = HttpClients.custom()
                .setConnectionManager(connectionManager).setDefaultRequestConfig(defaultRequestConfig).build();

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
            httpPost.addHeader(HTTP.CONTENT_TYPE,ContentType.APPLICATION_JSON.getMimeType());

            StringEntity se = new StringEntity(jsonObject.toString(),ContentType.APPLICATION_JSON.getCharset());
            httpPost.setEntity(se);
            log.info(msg.getProcessMessage("Executing request " + httpPost.getRequestLine() + " to target " + target));
            StopWatch fetchJST = new StopWatch("httpclient");
            fetchJST.start(webUrl);
            HttpResponse response = httpclient.execute(target, httpPost, localContext);
            fetchJST.stop();
            log.info(msg.getProcessMessage(fetchJST.prettyPrint()));
            jsonString = CallWebServiceUtil.inputStream2String(response.getEntity().getContent());
            EntityUtils.consume(response.getEntity());
        } catch (Exception e) {
            log.error(msg.getErrorMessage(e.getMessage()), e);
            throw e;
        } finally {
            httpclient.close();
        }
        log.info(msg.getEndMessage("callWebService:" + jsonString));
        return jsonString;
    }

}
