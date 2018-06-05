package com.mouse.study.test.fuyou;

import com.mouse.study.test.fuyou.req.WithdrawReqData;
import com.mouse.study.test.fuyou.res.WithdrawRespData;
import com.mouse.study.utils.JackJsonUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lwf
 * @date 2018/6/5
 * use: 提现
 */
public class WithdrawService {

    public static void main(String[] args) throws Exception {
        WithdrawReqData reqData = new WithdrawReqData();
        reqData.setMerdt("20180605");
        reqData.setOrderNo("2018060500001" + System.currentTimeMillis());
        reqData.setBankNo("0102");
        reqData.setCityNo("1000");
        reqData.setAccntNo("01160325000005544");
        reqData.setAccntNm("刘雨珍");
        reqData.setAmt(1000);
        reqData.setMobile("18650309360");
        reqData.setAddDesc("1");
        Map<String, String> params = new HashMap();
        params.put("merid",Constants.API_MCHNT_CD);
        params.put("reqtype","payforreq");
        params.put("xml",reqData.buildXml());
        params.put("mac",reqData.sign());
        String respStr = HttpPostUtil.postForward(Constants.WITHDRAW_URL, params);
        System.out.println(respStr);
        WithdrawRespData respData = XmlBeanUtils.convertXml2Bean(respStr, WithdrawRespData.class);
        System.out.println(JackJsonUtil.objToStr(respData));

    }
}
