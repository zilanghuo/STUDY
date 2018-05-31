package com.mouse.study.controller;

import com.mouse.study.test.fuyou.Constants;
import com.mouse.study.test.fuyou.req.OrderReqData;
import com.mouse.study.utils.DESCoderUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by lwf on 2017/5/23.
 */
@Slf4j
@Controller
@Scope("singleton")
@RequestMapping("/fuyou")
public class FuYouController {


    @RequestMapping(value = "/order")
    public String order(ModelMap params) throws Exception {
        OrderReqData reqData = new OrderReqData();
        reqData.setMchntCd(Constants.API_MCHNT_CD);
        reqData.setType("10");
        reqData.setVersion("2.0");
        reqData.setLogoTp("0");
        reqData.setMchntOrderId("2018052900000001");
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
        params.put("FM", DESCoderUtil.desEncrypt(reqData.buildXml().toString(), DESCoderUtil.getKeyLength8(Constants.API_MCHNT_KEY)));
        params.put("ENCTP","1");
        params.put("VERSION","2.0");
        params.put("MCHNTCD",Constants.API_MCHNT_CD);
        return "/hidden";
    }
}
