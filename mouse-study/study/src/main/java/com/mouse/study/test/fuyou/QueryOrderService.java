package com.mouse.study.test.fuyou;

import com.mouse.study.test.fuyou.req.QueryOrderReqData;
import com.mouse.study.test.fuyou.res.QueryOrderRespData;
import com.mouse.study.utils.JackJsonUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lwf
 * @date 2018/5/30
 * use:
 */
public class QueryOrderService {

    public static void main(String[] args) throws Exception {
        QueryOrderReqData reqData = new QueryOrderReqData();
        reqData.setMchntCd(Constants.API_MCHNT_CD);
        reqData.setMchntOrderId("2018052900000002");
        reqData.setVersion("2.0");
        reqData.setSign(reqData.buildXml());
        Map<String, String> params = new HashMap();
        params.put("FM", reqData.buildXml());
        String respStr = HttpPostUtil.postForward(Constants.QUERY_ORDER_URL, params);
        QueryOrderRespData respData= XmlBeanUtils.convertXml2Bean(respStr, QueryOrderRespData.class);
        System.out.println(respData.sign());
        System.out.println(JackJsonUtil.objToStr(respData));
    }

}
