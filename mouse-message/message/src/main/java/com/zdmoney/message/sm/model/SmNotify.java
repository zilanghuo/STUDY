package com.zdmoney.message.sm.model;

import com.zdmoney.message.api.common.dto.MessageOperator;
import com.zdmoney.message.api.dto.sm.SendSmNotifyReqDto;
import com.zdmoney.message.api.dto.sm.SmChannelType;
import com.zdmoney.message.api.dto.sm.SmSendStatus;
import com.zdmoney.message.common.base.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 通知短信
 * Created by gaojc on 2016/11/12.
 */
@Data
@NoArgsConstructor
@Table(name = "T_SM_NOTIFY")
public class SmNotify extends BaseEntity {
    @NotNull(message = "手机号不能为空")
    private String mobile;    //手机号
    private String sendMsg;        //发送内容
    private SmSendStatus sendStatus;      //发送状态：成功/失败
    private String retMsg;          //响应报文
    private SmChannelType smChannelType;     //发送通道
    private String smSource;

    public SmNotify(SendSmNotifyReqDto reqDto) {
        this.mobile = reqDto.getMobile();
        this.sendMsg = reqDto.getSendMsg();
        this.createTime = new Date();
        this.modifyTime = createTime;
        this.operator = MessageOperator.SYS.name();
        this.smSource = reqDto.getSmSource().name();
    }
}
