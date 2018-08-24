package com.mouse.study.utils;


import com.mouse.trace.common.Constant;
import com.mouse.trace.utils.TraceGenerator;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

@Slf4j
public class HttpUtils {

    private static int connTimeOut = 10000;
    private static int readTimeOut = 10000;

    public static StringBuffer URLGet(String strUrl, Map parameterMap) throws IOException {
        String strTotalURL = "";
        strTotalURL = getTotalURL(strUrl, parameterMap);
        URL url = new URL(strTotalURL);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty(Constant.TRACE_ID, TraceGenerator.generatorId());
        con.setRequestProperty("X-Client-Id", "b8t77weRaMeDruMAtrAhUPU8AjaphaVe");
        con.setRequestProperty("Authorization", "17bde4522a25ff50b95050cffb81ecf5a8587913c4efc3da1eb8d9b3508e0681");
        con.setUseCaches(false);
        con.setConnectTimeout(connTimeOut);
        con.setReadTimeout(readTimeOut);
        HttpURLConnection.setFollowRedirects(true);
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        return getStringBufferFormBufferedReader(in);
    }

    public static StringBuffer URLPost(String strUrl, Map map) throws IOException {
        String content = getContentURL(map);
        URL url = new URL(strUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setDoInput(true);
        con.setDoOutput(true);
        con.setAllowUserInteraction(false);
        con.setUseCaches(false);
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
        con.setConnectTimeout(connTimeOut);
        con.setReadTimeout(readTimeOut);
        BufferedWriter bout = new BufferedWriter(new OutputStreamWriter(con.getOutputStream()));
        bout.write(content);
        bout.flush();
        bout.close();
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
        return getStringBufferFormBufferedReader(in);
    }

    private static StringBuffer getStringBufferFormBufferedReader(BufferedReader in)
            throws IOException {
        StringBuffer returnStringBuffer = new StringBuffer();
        char[] tmpbuf = new char[1024];
        int num = in.read(tmpbuf);
        while (num != -1) {
            returnStringBuffer.append(tmpbuf, 0, num);
            num = in.read(tmpbuf);
        }
        in.close();
        return returnStringBuffer;
    }

    private static String getContentURL(Map parameterMap) {
        if ((parameterMap == null) || (parameterMap.keySet().size() == 0)) {
            return "";
        }
        StringBuffer url = new StringBuffer();
        Set keys = parameterMap.keySet();
        for (Iterator i = keys.iterator(); i.hasNext(); ) {
            String key = String.valueOf(i.next());
            if (!(parameterMap.containsKey(key))) {
                break;
            }
            Object val = parameterMap.get(key);
            String str = (val != null) ? val.toString() : "";
            try {
                str = URLEncoder.encode(str, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            url.append(key).append("=").append(str).append("&");
        }
        String strURL = "";
        strURL = url.toString();
        if ('&' == strURL.charAt(strURL.length() - 1)) {
            strURL = strURL.substring(0, strURL.length() - 1);
        }
        return strURL;
    }

    public static String getTotalURL(String strUrl, Map parameterMap) {
        String content = getContentURL(parameterMap);
        return getTotalURL(strUrl, content);
    }

    public static String getTotalURL(String strUrl, String content) {
        String totalURL = strUrl;
        if (totalURL.indexOf("?") == -1) {
            totalURL = totalURL + "?";
        } else {
            totalURL = totalURL + "&";
        }

        totalURL = totalURL + content;
        return totalURL;
    }

    public static void main(String[] args) throws Exception {
       String aa = "acct_name=xiaoming&bank_code=01050000&dt_order=20180108195053&id_no=320921198706292431&id_type=0&money_order=0.01&no_agree=2018010842980842&no_order=20170906180801600042155&oid_partner=201408071000001543&oid_paybill=2018010837031026&pay_type=D&result_pay=SUCCESS&settle_date=20180108&sign=O&sign_type=RSA";
        Map map = (Map) JackJsonUtil.strToObj(aa, Map.class);


        TreeMap<String, Object> treeMap = new TreeMap();
        treeMap.put("acct_name", "xiaoming");
        treeMap.put("bank_code", "01050000");
        treeMap.put("dt_order", "20180108195053");
        treeMap.put("id_no", "320921198706292431");
        treeMap.put("id_type", "0");
        treeMap.put("money_order", 0.01);
        treeMap.put("no_agree", "2018010842980842");
        treeMap.put("no_order", "20170906180801600042155");
        treeMap.put("oid_partner", "201408071000001543");
        treeMap.put("oid_paybill", "2018010837031026");
        treeMap.put("pay_type", "D");
        treeMap.put("result_pay", "SUCCESS");
        treeMap.put("settle_date", "20180108");
        treeMap.put("sign", "O");
        treeMap.put("sign_type", "RSA");
        StringBuffer buffer = URLPost("http://localhost:8080/cashier/callback/lianlianCallback", treeMap);
        System.out.println(buffer.toString());

    }

}