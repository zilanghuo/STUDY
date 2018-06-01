package com.mouse.study.test.fuyou;

import cn.hutool.core.bean.BeanUtil;
import com.mouse.study.test.fuyou.req.CheckIdReqData;
import com.mouse.study.test.fuyou.res.CheckIdRespData;
import com.mouse.study.utils.JackJsonUtil;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author lwf
 * @date 2018/5/29
 * use:
 */
public class CheckIdService {

    public static void main(String[] args) throws Exception {
        CheckIdReqData data = new CheckIdReqData();
        data.setIdNo("350322199112096814");
        data.setMchntOrderid("2016021600001");
        data.setName("张三");
        data.setTypeId("NN");
        data.setVersion("1.0");

        Map<String, String> params = new HashMap();
        params.put("FM", data.buildXml());
        System.out.println("请求报文:" + params);
        String respStr = HttpPostUtil.postForward(Constants.CHECK_ID_REQ_URL, params);
        System.out.println("返回报文:" + respStr);
        CheckIdRespData respData = XmlBeanUtils.convertXml2Bean(respStr, CheckIdRespData.class);
        System.out.println(JackJsonUtil.objToStr(respData));

        Map<String, Object> map = BeanUtil.beanToMap(data);
        Iterator<Map.Entry<String, Object>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> next = iterator.next();
            System.out.println(next.getKey() + ":" + next.getValue());
        }
    }
}
