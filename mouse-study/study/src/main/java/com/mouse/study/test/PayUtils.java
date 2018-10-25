package com.mouse.study.test;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 支付工具类
 * Date: 2017/8/16
 *
 * @author dailf@zendaimoney.com
 */
public class PayUtils {

    private static Logger log = LoggerFactory.getLogger(PayUtils.class);

    private static final String SIGN_TYPE_MD5 = "MD5";
    private static final String SIGN_TYPE_HMAC_SHA256 = "HMAC_SHA256";

    /**
     * 获取经过处理后的参数
     * @param paramsMap
     * @return
     */
    public static Map<String, String> getRequestParams(Map paramsMap){
        Map<String, String> returnParams = new HashMap<>();
        for (Iterator iter = paramsMap.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) paramsMap.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            returnParams.put(name, valueStr);
        }
        return returnParams;
    }

    /**
     * 拼接参数
     * @param map
     * @param encode
     * @return
     */
    public static String buildParams(Map<String, String> map, Boolean encode, Boolean toLower){
        if(null == toLower) {
            toLower = false;
        }
        if(null == encode) {
            encode = false;
        }
        StringBuilder sb = new StringBuilder();
        //最后增加的值
        StringBuilder appendLast = new StringBuilder();
        ArrayList<String> list = new ArrayList(map.keySet());
        Collections.sort(list);
        try {
            for(String key : list){
                String value = map.get(key);//防止参数不是字符串
                if (encode){
                    value = URLEncoder.encode(value, "utf-8");
                }
                //过滤关键字
                if("key".equals(key)){
                    appendLast.append((toLower?key.toLowerCase():key) + "=" + value);
                    continue;
                }
                if(!StringUtils.isBlank(value)){
                    sb.append((toLower?key.toLowerCase():key) + "=" + value + "&");
                }
            }
        }catch (Exception e){
            //pass
        }
        return appendLast.length() > 0 ? sb.append(appendLast).toString(): sb.substring(0, sb.length()-1);
    }
    /**
     * 微信随机数生成
     * @return
     */
    public static String randomStr(){
        return UUID.randomUUID().toString().replace("-", "").substring(0, 32);
    }

    /**
     * 创建XML字符串
     * @param params
     * @return
     */
    public static String createXmlStr(Map<String, String> params, String startTag) {
        StringBuilder xmlStr = new StringBuilder();
        xmlStr.append("<" + startTag + ">");
        for(String key : params.keySet()){
            String value = params.get(key);
            if(!StringUtils.isBlank(value)){
                xmlStr.append("<" + key + ">");
                xmlStr.append(value);
                xmlStr.append("</" + key + ">");
            }
        }
        xmlStr.append("</" + startTag + ">");
        return xmlStr.toString();
    }

    //自增原子，每天重置，用来拼接单号
    private static AtomicLong idNumForPayNo = new AtomicLong(1);
    private static String tempDateStr = "";//对比之前的时间yyyyMMdd
    /**
     * 获取唯一的支付单号
     * @return
     */
    public static synchronized String getPayOrderNo() {
        String returnStr;
        //时分秒+毫秒
        String currentTimeStamp = DateFormatUtils.format(new Date(), "yyyyMMddHHmmssSSSS");
        String currentDateStr = currentTimeStamp.substring(0,8);
        //2次时间不相等，则为新的一天
        if(!tempDateStr.equals(currentDateStr)){
            tempDateStr = currentDateStr;
            //重置
            idNumForPayNo = new AtomicLong(1);
        }
        String idStr = idNumForPayNo.getAndIncrement() + "";
        //小于4位，则空位用0代替
        if(idStr.length() < 4){
            String preStr = "";
            for(int i = 0 ; i < 4 - idStr.length() ; i++){
                preStr += "0";
            }
            returnStr = currentTimeStamp + preStr + idStr;
        }else{
            returnStr = currentTimeStamp + idStr;
        }
        return returnStr;
    }
    /**
     * XML格式字符串转换为Map
     *
     * @return XML数据转换后的Map
     * @throws Exception
     */
    public static Map<String, String> xmlToMap(InputStream inputStream){
        Map<String, String> data = new HashMap<String, String>();
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            // 以下为修复微信的xml
            String FEATURE = "http://apache.org/xml/features/disallow-doctype-decl";
            documentBuilderFactory.setFeature(FEATURE, true);

            FEATURE = "http://xml.org/sax/features/external-general-entities";
            documentBuilderFactory.setFeature(FEATURE, false);

            FEATURE = "http://xml.org/sax/features/external-parameter-entities";
            documentBuilderFactory.setFeature(FEATURE, false);

            FEATURE = "http://apache.org/xml/features/nonvalidating/load-external-dtd";
            documentBuilderFactory.setFeature(FEATURE, false);

            documentBuilderFactory.setXIncludeAware(false);
            documentBuilderFactory.setExpandEntityReferences(false);


            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            org.w3c.dom.Document doc = documentBuilder.parse(inputStream);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getDocumentElement().getChildNodes();
            for (int idx = 0; idx < nodeList.getLength(); ++idx) {
                Node node = nodeList.item(idx);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    org.w3c.dom.Element element = (org.w3c.dom.Element) node;
                    data.put(element.getNodeName(), element.getTextContent());
                }
            }
            try {
                inputStream.close();
            } catch (Exception ex) {
                // do nothing
            }
        } catch (Exception ex) {
            log.error("Invalid XML, can not convert to map. Error message: {}", ex.getMessage());
//            throw ex;
        }
        return data;
    }
    /**
     * XML格式字符串转换为Map
     *
     * @param strXML XML字符串
     * @return XML数据转换后的Map
     * @throws Exception
     */
    public static Map<String, String> xmlToMap(String strXML) throws Exception {
        try {
            Map<String, String> data = new HashMap<String, String>();
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            // 以下为修复微信的xml
            String FEATURE = "http://apache.org/xml/features/disallow-doctype-decl";
            documentBuilderFactory.setFeature(FEATURE, true);

            FEATURE = "http://xml.org/sax/features/external-general-entities";
            documentBuilderFactory.setFeature(FEATURE, false);

            FEATURE = "http://xml.org/sax/features/external-parameter-entities";
            documentBuilderFactory.setFeature(FEATURE, false);

            FEATURE = "http://apache.org/xml/features/nonvalidating/load-external-dtd";
            documentBuilderFactory.setFeature(FEATURE, false);

            documentBuilderFactory.setXIncludeAware(false);
            documentBuilderFactory.setExpandEntityReferences(false);



            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            InputStream stream = new ByteArrayInputStream(strXML.getBytes("UTF-8"));
            org.w3c.dom.Document doc = documentBuilder.parse(stream);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getDocumentElement().getChildNodes();
            for (int idx = 0; idx < nodeList.getLength(); ++idx) {
                Node node = nodeList.item(idx);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    org.w3c.dom.Element element = (org.w3c.dom.Element) node;
                    data.put(element.getNodeName(), element.getTextContent());
                }
            }
            try {
                stream.close();
            } catch (Exception ex) {
                // do nothing
            }
            return data;
        } catch (Exception ex) {
            log.warn("Invalid XML, can not convert to map. Error message: {}. XML content: {}", ex.getMessage(), strXML);
            throw new Exception("XML_TO_MAP_ERROR");
        }
    }

    /**
     * 将Map转换为XML格式的字符串
     *
     * @param data Map类型数据
     * @return XML格式的字符串
     * @throws Exception
     */
    public static String mapToXml(Map<String, String> data){
        String output = "";
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

            // 以下为修复微信的xml
            String FEATURE = "http://apache.org/xml/features/disallow-doctype-decl";
           // documentBuilderFactory.setFeature(FEATURE, true);

            FEATURE = "http://xml.org/sax/features/external-general-entities";
            //documentBuilderFactory.setFeature(FEATURE, false);

            FEATURE = "http://xml.org/sax/features/external-parameter-entities";
            //documentBuilderFactory.setFeature(FEATURE, false);

            FEATURE = "http://apache.org/xml/features/nonvalidating/load-external-dtd";
            //documentBuilderFactory.setFeature(FEATURE, false);

            documentBuilderFactory.setXIncludeAware(false);
            documentBuilderFactory.setExpandEntityReferences(false);

            DocumentBuilder documentBuilder= documentBuilderFactory.newDocumentBuilder();
            org.w3c.dom.Document document = documentBuilder.newDocument();
            org.w3c.dom.Element root = document.createElement("xml");
            document.appendChild(root);
            for (String key: data.keySet()) {
                String value = data.get(key);
                if (value == null) {
                    value = "";
                }
                value = value.trim();
                org.w3c.dom.Element filed = document.createElement(key);
                filed.appendChild(document.createTextNode(value));
                root.appendChild(filed);
            }
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            DOMSource source = new DOMSource(document);
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            transformer.transform(source, result);
            output = writer.getBuffer().toString(); //.replaceAll("\n|\r", "");
            writer.close();
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return output;
    }
    /**
     * 生成签名. 注意，若含有sign_type字段，必须和signType参数保持一致。
     *
     * @param data 待签名数据
     * @param key API密钥
     * @param signType 签名方式
     * @return 签名
     */
    public static String generateSignature(final Map<String, String> data, String key, String signType){
        try {
            Set<String> keySet = data.keySet();
            String[] keyArray = keySet.toArray(new String[keySet.size()]);
            Arrays.sort(keyArray);
            StringBuilder sb = new StringBuilder();
            for (String k : keyArray) {
                if (k.equals("sign")) {
                    continue;
                }
                if (data.get(k).trim().length() > 0) // 参数值为空，则不参与签名
                    sb.append(k).append("=").append(data.get(k).trim()).append("&");
            }
            sb.append("key=").append(key);
            if ("MD5".equals(signType)) {
                return MD5(sb.toString()).toUpperCase();
            }
            else if ("HMACSHA256".equals(signType)) {
                return HMACSHA256(sb.toString(), key);
            }
            else {
                throw new Exception(String.format("Invalid sign_type: %s", signType));
            }
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return "";
    }
    /**
     * 生成 MD5
     *
     * @param data 待处理数据
     * @return MD5结果
     */
    public static String MD5(String data) throws Exception {
        java.security.MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] array = md.digest(data.getBytes("UTF-8"));
        StringBuilder sb = new StringBuilder();
        for (byte item : array) {
            sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString().toUpperCase();
    }

    /**
     * 生成 HMACSHA256
     * @param data 待处理数据
     * @param key 密钥
     * @return 加密结果
     * @throws Exception
     */
    public static String HMACSHA256(String data, String key) throws Exception {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
        sha256_HMAC.init(secret_key);
        byte[] array = sha256_HMAC.doFinal(data.getBytes("UTF-8"));
        StringBuilder sb = new StringBuilder();
        for (byte item : array) {
            sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString().toUpperCase();
    }

}
