package com.zdmoney.message.sm.model;

import com.zdmoney.message.api.dto.sm.SendSmType;
import com.zdmoney.message.api.dto.sm.SmChannelType;
import com.zdmoney.message.api.dto.sm.SmSendStatus;
import com.zdmoney.message.common.base.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by Administrator on 2016/11/7.
 */
@Data
@NoArgsConstructor
@Table(name = "T_SM_MARKET_HIS")
public class SmMarketHistory extends BaseEntity {
    @NotNull(message = "批次号不能为空")
    private String batchNo;         //批次号
    @NotNull(message = "手机号不能为空")
    private String mobile;    //手机号
    private Boolean isInstant;       //是否定时发送
    private Date sendTime;          //发送时间
    private String sendMsg;        //发送内容
    private SmSendStatus sendStatus;      //发送状态：成功/失败
    private String retMsg;          //响应报文
    private SmChannelType smChannelType;     //发送通道
    private SendSmType sendSmType;
    private String smSource;
}
