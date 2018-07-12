package com.mouse.study.utils;

import cn.hutool.http.HttpUtil;
import com.fuiou.util.MD5;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lwf
 * @date 2018/7/9
 * use:
 */
public class TestDemo {

    public static void main(String[] args) {
        String strUrl = "http://172.16.235.171:8080/financial-api/Api/requestDeal";//请求网关URL
        String key = "bGNiLW9wZW4="; //MD5Key
        String arg0 = "103011"; //接口号
        String arg1 = ""; //json 参数
        String arg2 = "";//签名
        Map<String, Object> reqMain = new HashMap();
        reqMain.put("reqUrl", "http");    // 访问http（固定）
        reqMain.put("platform", "THUMBAPP");//平台code（固定）
        reqMain.put("reqTimestamp", "1528352594");//时间戳
        reqMain.put("sn", "420621198209121866");    //流水号 消息来源项目编号+自定义流水号
        Map<String, Object> reqHeadParam = new HashMap();
        reqHeadParam.put("sessionToken", ""); //sessionToken  登录通过后，由服务端生成。后面每次请求带上sessionToken
        reqHeadParam.put("platform", "App");//网关后台平台标识移动端请求（固定）
        reqHeadParam.put("deviceID", "");//设备号
        reqMain.put("reqHeadParam", reqHeadParam);

        DpayNotifyReq notifyReq = new DpayNotifyReq();
        notifyReq.setIsSuccess(0);
        notifyReq.setType(2);
        notifyReq.setTradeNo("2018071100010");

        reqMain.put("reqParam", notifyReq);
        arg1 = JackJsonUtil.objToStr(reqMain);
        //生成签名
        arg2 = MD5.MD5Encode(arg0 + "|" + arg1 + "|" + key);
        //组装参数
        Map<String, Object> paramMap = new HashMap();
        paramMap.put("arg0", arg0);
        paramMap.put("arg1", arg1);
        paramMap.put("arg2", arg2);
        //发送请求
        String result = HttpUtil.post(strUrl, paramMap);
        System.out.println("=====>>请求URL:" + strUrl + ";");
        System.out.println("=====>>请求入参arg0:" + arg0 + ";");
        System.out.println("=====>>请求入参arg1:" + arg1 + ";");
        System.out.println("=====>>请求入参arg2:" + arg2 + ";");
        System.out.println("=====>>请求出参:" + result + ";");
    }

}

@Data
class DpayNotifyReq {

    private String tradeNo;

    private Integer type;

    private Integer isSuccess;

    private String memo;
}
