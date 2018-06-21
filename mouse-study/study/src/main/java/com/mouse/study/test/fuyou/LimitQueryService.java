package com.mouse.study.test.fuyou;

import com.mouse.study.test.fuyou.req.LimitQueryReqData;
import com.mouse.study.test.fuyou.res.LimitQueryRespData;
import com.mouse.study.utils.JackJsonUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lwf
 * @date 2018/6/4
 * use:
 */
public class LimitQueryService {

    public static void main(String[] args) throws Exception {
        LimitQueryReqData reqData = new LimitQueryReqData();
        reqData.setMchntCd(Constants.API_MCHNT_CD_2);
        reqData.setInsCd("0803080000");
        Map<String, String> params = new HashMap();
        params.put("FM", reqData.buildXml(Constants.API_MCHNT_KEY_2));
        String respStr = HttpPostUtil.postForward(Constants.LIMIT_QUERY_URL, params);
        System.out.println(respStr);
        LimitQueryRespData respData= XmlBeanUtils.convertXml2Bean(respStr, LimitQueryRespData.class);
        System.out.println(JackJsonUtil.objToStr(respData));
    }

}
