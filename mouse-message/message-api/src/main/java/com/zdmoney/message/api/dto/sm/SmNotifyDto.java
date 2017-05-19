package com.zdmoney.message.api.dto.sm;

import com.zdmoney.message.api.base.BaseDto;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 通知短信dto
 * Created by gaojc on 2016/11/12.
 */
@Data
@NoArgsConstructor
public class SmNotifyDto extends BaseDto {
    private static final long serialVersionUID = 6311894646344848063L;
    //手机号
    private String mobile;
    //发送内容
    private String sendMsg;
    //发送状态
    private SmSendStatus sendStatus;
    //响应报文
    private String retMsg;
    //发送通道名称
    private SmChannelType smChannelType;
}
