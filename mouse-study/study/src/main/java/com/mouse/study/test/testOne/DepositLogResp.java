package com.mouse.study.test.testOne;

import lombok.Data;

import java.util.Date;

/**
 * @author laiwufa
 * @date 2018/8/20
 * use:
 */
@Data
public class DepositLogResp {

    /**
     * id
     */
    private String id;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date modifyTime;

    /**
     * 存管id
     */
    private String loginId;

    /**
     * 接口编号
     */
    private String interfaceCode;

    /**
     * 接口名
     */
    private String interfaceName;

    /**
     * 客户端类型：0：PC，1：APP
     */
    protected String clientType;

    /**
     * 商户流水号
     */
    private String merchantSerialNo;

    /**
     * 商户请求报文
     */
    private String merchantReq;

    /**
     * 商户同步返回报文
     */
    private String merchantSynResp;

    /**
     * 商户异步返回报文
     */
    private String merchantAsynResp;

    /**
     * 商户回调地址
     */
    private String merchantNotifyUrl;

    /**
     * 商户页面地址
     */
    private String merchantPageUrl;

    /**
     * 商户回调报文
     */
    private String merchantNotifyResp;

    /**
     * 第三方流水号
     */
    private String thirdSerialNo;

    /**
     * 第三方请求报文
     */
    private String thirdReq;

    /**
     * 第三方同步返回报文
     */
    private String thirdSynResp;

    /**
     * 第三方异步返回报文
     */
    private String thirdAsynResp;

    /**
     * 第三方回调码
     */
    private String thirdResultCode;

    /**
     * 第三方回调信息
     */
    private String thirdResultMsg;

}
