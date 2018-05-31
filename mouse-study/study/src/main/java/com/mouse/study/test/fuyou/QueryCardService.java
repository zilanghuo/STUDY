package com.mouse.study.test.fuyou;

import com.mouse.study.test.fuyou.req.QueryCardReqData;
import com.mouse.study.test.fuyou.res.QueryCardRespData;
import com.mouse.study.utils.JackJsonUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lwf
 * @date 2018/5/31
 * use:
 */
public class QueryCardService {

    public static void main(String[] args) throws Exception {
        QueryCardReqData reqData = new QueryCardReqData();
        reqData.setMchntCd(Constants.API_MCHNT_CD);
        reqData.setVer("1.30");
        reqData.setOSsn(System.currentTimeMillis()+"");
        reqData.setOno("6222001001127676964");
        reqData.setOnm("刘雨珍");
        reqData.setOCerTp("0");
        reqData.setOCerNo("530122198505158369");
        reqData.setSignTp("md5");
        reqData.setSign(reqData.buildXml());
        Map<String, String> params = new HashMap();
        params.put("FM", reqData.buildXml());
        String respStr = HttpPostUtil.postForward(Constants.QUERY_CARD_URL, params);
        QueryCardRespData respData= XmlBeanUtils.convertXml2Bean(respStr, QueryCardRespData.class);
        System.out.println(respData.sign());
        System.out.println(JackJsonUtil.objToStr(respData));


    }
}
