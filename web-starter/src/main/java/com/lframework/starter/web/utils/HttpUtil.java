package com.lframework.starter.web.utils;

import com.lframework.common.utils.StringUtil;
import com.lframework.common.utils.XmlUtil;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 基于 httpclient 4.5版本的 http工具类
 */
@Slf4j
public class HttpUtil {

    public static final String CHARSET = "UTF-8";

    public static String doGet(String url, Map<String, Object> params) throws IOException {

        return doGet(url, params, CHARSET, null, null);
    }

    public static String doPost(String url, Map<String, Object> params) throws IOException {

        return doPost(url, params, CHARSET, null, null);
    }

    public static String doPostJson(String url, Map<String, Object> params) throws IOException {

        return doPostJson(url, params, CHARSET, null, null);
    }

    public static String doPostXml(String url, Map<String, Object> params) throws IOException {

        return doPostXml(url, params, CHARSET, null, null);
    }

    public static String doGet(String url, Map<String, Object> params, InputStream certStream, String certPsw)
            throws IOException {

        return doGet(url, params, CHARSET, certStream, certPsw);
    }

    public static String doPost(String url, Map<String, Object> params, InputStream certStream, String certPsw)
            throws IOException {

        return doPost(url, params, CHARSET, certStream, certPsw);
    }

    public static String doPostJson(String url, Map<String, Object> params, InputStream certStream, String certPsw)
            throws IOException {

        return doPostJson(url, params, CHARSET, certStream, certPsw);
    }

    public static String doPostXml(String url, Map<String, Object> params, InputStream certStream, String certPsw)
            throws IOException {

        return doPostXml(url, params, CHARSET, certStream, certPsw);
    }

    /**
     * HTTP Get 获取内容
     *
     * @param url     请求的url地址 ?之前的地址
     * @param requestParams  请求的参数
     * @param charset 编码格式
     * @return 页面内容
     */
    public static String doGet(String url, Map<String, Object> requestParams, String charset, InputStream certStream,
            String certPsw) throws IOException {

        SimpleMap<String, Object> params = new SimpleMap<>(requestParams);
        if (StringUtil.isBlank(url)) {
            throw new IllegalArgumentException("url不能为空！");
        }
        if (params != null && !params.isEmpty()) {
            List<NameValuePair> pairs = new ArrayList<NameValuePair>(params.size());
            for (String key : params.keySet()) {
                String value = params.getString(key);
                if (value != null) {
                    pairs.add(new BasicNameValuePair(key, value));
                }
            }
            // 将请求参数和url进行拼接
            url += "?" + EntityUtils.toString(new UrlEncodedFormEntity(pairs, charset));
        }

        if (log.isDebugEnabled()) {
            log.debug("http-get url：{}", url);
        }

        HttpGet httpGet = new HttpGet(url);

        CloseableHttpClient httpClient = buildHttpClient(certStream, certPsw);

        @Cleanup CloseableHttpResponse response = httpClient.execute(httpGet);
        int statusCode = response.getStatusLine().getStatusCode();

        if (statusCode != HttpStatus.SC_OK) {
            httpGet.abort();
            throw new RuntimeException("HttpClient,error status code :" + statusCode);
        }
        HttpEntity entity = response.getEntity();
        String result = null;
        if (entity != null) {
            result = EntityUtils.toString(entity, "utf-8");
        }

        if (log.isDebugEnabled()) {
            log.debug("http-get 响应消息：{}", result == null ? "无" : result);
        }

        EntityUtils.consume(entity);

        return result;
    }

    /**
     * HTTP Post 获取内容
     *
     * @param url     请求的url地址 ?之前的地址
     * @param requestParams  请求的参数
     * @param charset 编码格式
     * @return 页面内容
     * @throws IOException
     */
    public static String doPost(String url, Map<String, Object> requestParams, String charset, InputStream certStream,
            String certPsw) throws IOException {

        SimpleMap<String, Object> params = new SimpleMap<>(requestParams);
        if (StringUtil.isBlank(url)) {
            return null;
        }
        List<NameValuePair> pairs = null;
        if (params != null && !params.isEmpty()) {
            pairs = new ArrayList<NameValuePair>(params.size());
            for (String key : params.keySet()) {
                String value = params.getString(key);
                if (value != null) {
                    pairs.add(new BasicNameValuePair(key, value));
                }
            }
        }
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        if (pairs != null && pairs.size() > 0) {
            httpPost.setEntity(new UrlEncodedFormEntity(pairs, charset));
        }

        if (log.isDebugEnabled()) {
            log.debug("http-post url={}, params={}", url, JsonUtil.toJsonString(params));
        }

        CloseableHttpClient httpClient = buildHttpClient(certStream, certPsw);

        @Cleanup CloseableHttpResponse response = httpClient.execute(httpPost);

        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != HttpStatus.SC_OK) {
            httpPost.abort();
            throw new RuntimeException("HttpClient,error status code :" + statusCode);
        }
        HttpEntity entity = response.getEntity();
        String result = null;
        if (entity != null) {
            result = EntityUtils.toString(entity, charset);
        }

        if (log.isDebugEnabled()) {
            log.debug("http-post 响应消息：{}", result == null ? "无" : result);
        }

        EntityUtils.consume(entity);
        return result;
    }

    public static String doPostJson(String url, Map<String, Object> params, String charset, InputStream certStream,
            String certPsw) throws IOException {

        if (StringUtil.isBlank(url)) {
            throw new IllegalArgumentException("url不能为空！");
        }

        String jsonParams = JsonUtil.toJsonString(params);
        StringEntity requestEntity = new StringEntity(jsonParams, charset);
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-type", "application/json");
        httpPost.setEntity(requestEntity);

        if (log.isDebugEnabled()) {
            log.debug("http-post-json url={}, params={}", url, jsonParams);
        }

        CloseableHttpClient httpClient = buildHttpClient(certStream, certPsw);

        @Cleanup CloseableHttpResponse response = httpClient.execute(httpPost);

        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != HttpStatus.SC_OK) {
            httpPost.abort();
            throw new RuntimeException("HttpClient,error status code :" + statusCode);
        }
        HttpEntity entity = response.getEntity();
        String result = null;
        if (entity != null) {
            result = EntityUtils.toString(entity, charset);
        }

        if (log.isDebugEnabled()) {
            log.debug("http-post-json 响应消息：{}", result == null ? "无" : result);
        }

        EntityUtils.consume(entity);
        return result;
    }

    public static String doPostXml(String url, Map<String, Object> params, String charset, InputStream certStream,
            String certPsw) throws IOException {

        if (StringUtil.isBlank(url)) {
            throw new IllegalArgumentException("url不能为空！");
        }

        String xmlParams = XmlUtil.formatXml(XmlUtil.map2xml(params, "xml"));

        StringEntity requestEntity = new StringEntity(xmlParams, charset);
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-type", "application/xml");
        httpPost.setEntity(requestEntity);

        if (log.isDebugEnabled()) {
            log.debug("http-post-xml url={}, params={}", url, xmlParams);
        }


        CloseableHttpClient httpClient = buildHttpClient(certStream, certPsw);

        @Cleanup CloseableHttpResponse response = httpClient.execute(httpPost);

        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != HttpStatus.SC_OK) {
            httpPost.abort();
            throw new RuntimeException("HttpClient,error status code :" + statusCode);
        }
        HttpEntity entity = response.getEntity();
        String result = null;
        if (entity != null) {
            result = EntityUtils.toString(entity, charset);
        }

        if (log.isDebugEnabled()) {
            log.debug("http-post-xml 响应消息：{}", result == null ? "无" : result);
        }

        EntityUtils.consume(entity);
        return result;
    }

    private static CloseableHttpClient buildHttpClient(InputStream certStream, String certPsw) {

        try {
            BasicHttpClientConnectionManager connManager;
            if (certStream != null) {
                char[] password = certPsw.toCharArray();
                KeyStore ks = KeyStore.getInstance("PKCS12");
                ks.load(certStream, password);

                // 实例化密钥库 & 初始化密钥工厂
                KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
                kmf.init(ks, password);

                // 创建 SSLContext
                SSLContext sslContext = SSLContext.getInstance("TLS");
                sslContext.init(kmf.getKeyManagers(), null, new SecureRandom());

                SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext,
                        new String[]{"TLSv1"}, null, new DefaultHostnameVerifier());

                connManager = new BasicHttpClientConnectionManager(RegistryBuilder.<ConnectionSocketFactory>create()
                        .register("http", PlainConnectionSocketFactory.getSocketFactory())
                        .register("https", sslConnectionSocketFactory).build(), null, null, null);
            } else {
                connManager = new BasicHttpClientConnectionManager(RegistryBuilder.<ConnectionSocketFactory>create()
                        .register("http", PlainConnectionSocketFactory.getSocketFactory())
                        .register("https", SSLConnectionSocketFactory.getSocketFactory()).build(), null, null, null);
            }

            RequestConfig config = RequestConfig.custom().setConnectTimeout(60000).setSocketTimeout(15000).build();
            CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config)
                    .setConnectionManager(connManager).build();

            return httpClient;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new IllegalStateException("无法构建HttpClient！");
        }
    }
}

