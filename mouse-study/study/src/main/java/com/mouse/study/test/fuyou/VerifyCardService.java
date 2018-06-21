package com.mouse.study.test.fuyou;

import com.mouse.study.test.fuyou.req.VerifyCardReqData;
import com.mouse.study.test.fuyou.res.VerifyCardRespData;
import com.mouse.study.utils.JackJsonUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lwf
 * @date 2018/6/21
 * use:
 */
public class VerifyCardService {

    public static void main(String[] args) throws Exception {
        VerifyCardReqData reqData = new VerifyCardReqData();
        reqData.setMchntCd(Constants.API_MCHNT_CD_2);
        reqData.setVer("1.30");
        reqData.setOSsn(System.currentTimeMillis() + "");
        reqData.setCardNo("6222001001127676964");
        reqData.setCardName("刘雨珍");
        reqData.setCardType("0");
        reqData.setIdCardNo("530122198505158369");
        reqData.setTelNo("18512365485");
        Map<String, String> params = new HashMap();
        params.put("FM", reqData.buildXml());
        String respStr = HttpPostUtil.postForward(Constants.VERIFY_CARD_URL, params);
        VerifyCardRespData respData= XmlBeanUtils.convertXml2Bean(respStr, VerifyCardRespData.class);
        System.out.println(JackJsonUtil.objToStr(respData));
        System.out.println(respData.sign());
    }
}
