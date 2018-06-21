package com.mouse.study.test.fuyou;

import com.mouse.study.test.fuyou.req.QueryCardBinReqData;
import com.mouse.study.utils.HttpUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lwf
 * @date 2018/6/21
 * use:
 */
public class QueryCardBinService {

    public static void main(String[] args) throws Exception{
        QueryCardBinReqData reqData = new QueryCardBinReqData();
        reqData.setMchntCd(Constants.API_MCHNT_CD_2);
        reqData.setOno("6222001001127676964");
        reqData.setSign(reqData.buildXml(Constants.API_MCHNT_KEY_2));
        Map<String, String> params = new HashMap();
        params.put("FM", reqData.buildXml(Constants.API_MCHNT_KEY_2));
        StringBuffer respStr = HttpUtils.URLPost(Constants.QUERY_CARD_BIN_URL, params);
        System.out.println(respStr);

    }


}
