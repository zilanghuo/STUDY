package com.mouse.study.test.fuyou;

import com.mouse.study.test.fuyou.req.QueryWithdrawOrderReqData;
import com.mouse.study.test.fuyou.res.QueryWithdrawOrderRespData;
import com.mouse.study.utils.JackJsonUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lwf
 * @date 2018/6/5
 * use:
 */
public class QueryWithdrawOrderService {

    public static void main(String[] args) throws Exception {
        QueryWithdrawOrderReqData reqData = new QueryWithdrawOrderReqData();
        reqData.setStartDt("20180601");
        reqData.setEndDt("20180606");
        reqData.setOrderNo("20180605000010000002");


        Map<String, String> params = new HashMap();
        params.put("merid",Constants.API_MCHNT_CD);
        params.put("reqtype","qrytransreq");
        params.put("xml",reqData.buildXml());
        params.put("mac",reqData.sign());
        String respStr = HttpPostUtil.postForward(Constants.WITHDRAW_URL, params);
        System.out.println(respStr);
        QueryWithdrawOrderRespData respData = XmlBeanUtils.convertXml2Bean(respStr, QueryWithdrawOrderRespData.class);
        System.out.println(JackJsonUtil.objToStr(respData));

    }
}
