package com.mouse.study.test.fuyou;

import com.mouse.study.test.fuyou.req.DisableOrderReqData;
import com.mouse.study.test.fuyou.res.DisableOrderRespData;
import com.mouse.study.utils.JackJsonUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lwf
 * @date 2018/6/4
 * use:
 */
public class DisableOrderService {

    public static void main(String[] args) throws Exception {
        DisableOrderReqData reqData = new DisableOrderReqData();
        reqData.setAmt(100);
        reqData.setMchntCd(Constants.API_MCHNT_CD);
        reqData.setMchntOrderId("2018053100020");
        Map<String, String> params = new HashMap();
        params.put("FM", reqData.buildXml());
        String respStr = HttpPostUtil.postForward(Constants.DISABLE_ORDER_URL, params);
        DisableOrderRespData respData= XmlBeanUtils.convertXml2Bean(respStr, DisableOrderRespData.class);
        System.out.println(respData.sign());
        System.out.println(JackJsonUtil.objToStr(respData));
    }

}
