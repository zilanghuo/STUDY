package com.mouse.study.utils;

import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by lwf on 2017/8/21.
 * use to do:
 */
public class HttpsClientUtils {

    /**
     * https get请求:get 方法
     *
     * @param url
     * @param valueMap
     * @param headMap
     * @throws Exception
     */
    public static String get(String url, Map<String, String> valueMap, Map<String, String> headMap) throws Exception {
        StringBuilder st = new StringBuilder("");
        Iterator<Map.Entry<String, String>> iterator = valueMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            st.append(entry.getKey() + "=" + entry.getValue() + "&");
        }
        String query = st.substring(0, st.length() - 1);
        String result = "";
        BufferedReader in = null;
        try {
            String urlStr = url + "?" + query;
            System.out.println("GET请求的URL为：" + urlStr);
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, new TrustManager[]{new HttpsClientUtils.TrustAnyTrustManager()}, new java.security.SecureRandom());
            URL realUrl = new URL(urlStr);
            // 打开和URL之间的连接
            HttpsURLConnection connection = (HttpsURLConnection) realUrl.openConnection();
            // 设置https相关属性
            connection.setSSLSocketFactory(sc.getSocketFactory());
            connection.setHostnameVerifier(new HttpsClientUtils.TrustAnyHostnameVerifier());
            connection.setDoOutput(true);

            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            //设置头
            Iterator<Map.Entry<String, String>> iteratorHead = headMap.entrySet().iterator();
            while (iteratorHead.hasNext()) {
                Map.Entry<String, String> entry = iteratorHead.next();
                connection.setRequestProperty(entry.getKey(), entry.getValue());
            }
            // 建立实际的连接
            connection.connect();
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            System.out.println("获取的结果为：" + result);
            in.close();
            return result;
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            throw e;
        }
        // 使用finally块来关闭输入流

    }

    /**
     * https post请求
     *
     * @param url
     * @param valueMap
     * @param headMap
     * @throws Exception
     */
    public static void post(String url, Map<String, String> valueMap, Map<String, String> headMap) throws Exception {
        StringBuilder st = new StringBuilder("");
        Iterator<Map.Entry<String, String>> iterator = valueMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            st.append(entry.getKey() + "=" + entry.getValue() + "&");
        }

        String query = st.substring(0, st.length() - 1);
        URL myurl = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) myurl.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-length", String.valueOf(query.length()));
        con.setRequestProperty("Content-Type", "application/x-www- form-urlencoded");
        con.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0;Windows98;DigExt)");
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        //设置头
        Iterator<Map.Entry<String, String>> iteratorHead = headMap.entrySet().iterator();
        while (iteratorHead.hasNext()) {
            Map.Entry<String, String> entry = iteratorHead.next();
            con.setRequestProperty(entry.getKey(), entry.getValue());
        }
        con.setDoOutput(true);
        con.setDoInput(true);

        DataOutputStream output = new DataOutputStream(con.getOutputStream());
        output.writeBytes(query);
        output.close();

        DataInputStream input = new DataInputStream(con.getInputStream());
        for (int c = input.read(); c != -1; c = input.read()) {
            System.out.print((char) c);
        }
        input.close();
        System.out.println("Resp Code:" + con.getResponseCode());
        System.out.println("Resp Message:" + con.getResponseMessage());

    }

    private static class TrustAnyTrustManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[]{};
        }
    }

    private static class TrustAnyHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }
}
