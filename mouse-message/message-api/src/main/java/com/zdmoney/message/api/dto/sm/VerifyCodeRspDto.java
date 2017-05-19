package com.zdmoney.message.api.dto.sm;

import com.zdmoney.message.api.base.BaseDto;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Administrator on 2016/11/3.
 */
@Data
@NoArgsConstructor
public class VerifyCodeRspDto extends BaseDto {
    private static final long serialVersionUID = 3863246891777355718L;
    private String mobile;    //手机号
    private String verifyCode;      //验证码
    private SmSendStatus sendStatus;
    private String sendMsg;        //发送内容
    private String retMsg;          //响应报文
    private SmChannelType smChannelType;     //发送通道
}
