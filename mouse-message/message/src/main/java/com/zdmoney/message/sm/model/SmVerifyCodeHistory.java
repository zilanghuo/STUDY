package com.zdmoney.message.sm.model;

import com.zdmoney.message.api.dto.sm.SmChannelType;
import com.zdmoney.message.common.base.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Created by Administrator on 2016/11/7.
 */
@Data
@NoArgsConstructor
@Table(name = "T_SM_VERIFYCODE_HIS")
public class SmVerifyCodeHistory extends BaseEntity {
    @NotNull(message = "手机号不能为空")
    private String mobile;    //手机号
    @NotNull(message = "验证码不能为空")
    private String verifyCode;      //验证码
    private String sendMsg;        //发送内容
    private Boolean sendStatus;      //发送状态：成功/失败
    private String retMsg;          //响应报文
    private SmChannelType smChannelType;     //发送通道
    private String smSource;
}
