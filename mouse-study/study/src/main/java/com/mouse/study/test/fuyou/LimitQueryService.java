package com.mouse.study.test.fuyou;

import com.mouse.study.test.fuyou.req.LimitQueryReqData;

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
        reqData.setMchntCd(Constants.API_MCHNT_CD);
        reqData.setInsCd("0803080000");
        Map<String, String> params = new HashMap();
        params.put("FM", reqData.buildXml());
        String respStr = HttpPostUtil.postForward(Constants.LIMIT_QUERY_URL, params);
        System.out.println(respStr);
    }

}
