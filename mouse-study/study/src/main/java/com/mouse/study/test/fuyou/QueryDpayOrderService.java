package com.mouse.study.test.fuyou;

import com.mouse.study.test.fuyou.req.QueryDpayOrderReqData;
import com.mouse.study.test.fuyou.res.QueryDpayOrderRespData;
import com.mouse.study.utils.JackJsonUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lwf
 * @date 2018/7/10
 * use:
 */
public class QueryDpayOrderService {

    public static void main(String[] args) throws Exception {
        QueryDpayOrderReqData reqData = new QueryDpayOrderReqData();
        reqData.setStartDt("20180630");
        reqData.setEndDt("20180710");
         reqData.setOrderNo("1492018070900000002");
        Map<String, String> params = new HashMap();
        params.put("merid",Constants.API_MCHNT_CD);
        params.put("reqtype","qrytransreq");
        params.put("xml",reqData.buildXml());
        params.put("mac",reqData.sign());
        String respStr = HttpPostUtil.postForward(Constants.QUERY_DPAY_ORDER_URL, params);
        QueryDpayOrderRespData respData= XmlBeanUtils.convertXml2Bean(respStr, QueryDpayOrderRespData.class);
        System.out.println(JackJsonUtil.objToStr(respData));

    }

}
