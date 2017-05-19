package com.zdmoney.message.api.dto.sm;

import com.zdmoney.message.api.base.BaseDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 短信
 * Created by Administrator on 2016/11/3.
 */
@Data
@NoArgsConstructor
public class ShortMsgDto extends BaseDto {
    private static final long serialVersionUID = -6952008170377170287L;
    //批次号
    private String batchNo;
    //手机号
    private String mobile;
    //发送时间
    private Date sendTime;
    //发送内容
    private String sendMsg;
    //是否定时发送
    private Boolean isInstant;
    //发送状态
    private SmSendStatus sendStatus;
    //响应报文
    private String retMsg;
    //发送通道
    private SmChannelType smChannelType;
}
