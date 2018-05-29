package com.mouse.study.test.fuyou;

import com.mouse.study.test.fuyou.req.OrderReqData;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lwf
 * @date 2018/5/29
 * use:
 */
public class OrderService {

    public static void main(String[] args) throws Exception {
        OrderReqData reqData = new OrderReqData();
        reqData.setMchntCd(Constants.API_MCHNT_CD);
        reqData.setType("10");
        reqData.setVersion("2.0");
        reqData.setLogoTp("0");
        reqData.setMchntOrderId("2018052900000001");
        reqData.setUserId("20180529");
        reqData.setAmt(1);
        reqData.setBankCard("6222001001127676964");
        reqData.setBackUrl("www.baidu.com");
        reqData.setReUrl("www.baidu.com");
        reqData.setHomeUrl("www.baidu.com");
        reqData.setName("张三");
        reqData.setIdType("0");
        reqData.setSignTp("md5");
        reqData.setIdNo("350300199111163692");
        System.out.println(reqData.buildXml());
        Map<String, String> params = new HashMap(5);
        params.put("FM", "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><ORDER><AMT>1</AMT><BACKURL>www.baidu.com</BACKURL><BANKCARD>6222001001127676964</BANKCARD><HOMEURL>www.baidu.com</HOMEURL><IDNO>350300199111163692</IDNO><IDTYPE>0</IDTYPE><LOGOTP>0</LOGOTP><MCHNTCD>0002900F0096235</MCHNTCD><MCHNTORDERID>2018052900000001</MCHNTORDERID><NAME>张三</NAME><REURL>www.baidu.com</REURL><SIGN>081a72e320ed39bd1d0ddc6ee92d8bcc</SIGN><SIGNTP>md5</SIGNTP><TYPE>10</TYPE><USERID>20180529</USERID><VERSION>2.0</VERSION></ORDER>");
        params.put("ENCTP","1");
        params.put("VERSION","2.0");
        params.put("MCHNTCD","0002900F0006944");
        String respStr = HttpPostUtil.postForward(Constants.ORDER_URL, params);
        System.out.println("返回报文:"+respStr);

    }

}
