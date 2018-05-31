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
        reqData.setMchntOrderId("2018052900000002");
        reqData.setUserId("20180529");
        reqData.setAmt(1000);
        reqData.setBankCard("6222001001127676964");
        reqData.setBackUrl("www.baidu.com");
        reqData.setReUrl("www.baidu.com");
        reqData.setHomeUrl("www.baidu.com");
        reqData.setName("刘雨珍");
        reqData.setIdType("0");
        reqData.setSignTp("md5");
        reqData.setIdNo("530122198505158369");
        System.out.println(reqData.buildXml());
        Map<String, String> params = new HashMap(5);
        params.put("FM", reqData.buildXml());
        params.put("ENCTP","1");
        params.put("VERSION","2.0");
        params.put("MCHNTCD","0002900F0006944");

    }

}
