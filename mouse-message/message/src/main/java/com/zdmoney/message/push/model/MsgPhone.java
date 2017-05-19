package com.zdmoney.message.push.model;

import com.zdmoney.message.api.common.dto.MessageOperator;
import com.zdmoney.message.api.dto.push.MsgPhoneDto;
import com.zdmoney.message.api.dto.push.MsgPushDto;
import com.zdmoney.message.api.dto.push.enums.DeviceType;
import com.zdmoney.message.api.dto.push.enums.PushStatus;
import com.zdmoney.message.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 手机号推送情况表
 * Created by gaojc on 2016/7/18.
 */
@Data
@NoArgsConstructor
@Table(name = "T_MSG_PHONE")
@EqualsAndHashCode(callSuper = false)
@ToString
public class MsgPhone extends BaseEntity {
    @NotNull(message = "手机号不能为空")
    private String cellphoneNum;//手机号

    private String deviceId;    //设备号

    private String batchNo;     //批次号

    private String taskNo;      //任务号

    private Date pushTime;      //推送时间

    private PushStatus status;  //推送状态

    private String retMsg;      //推送返回信息

    private DeviceType deviceType;//设备类型

    public MsgPhone(String batchNo, DeviceType deviceType) {
        this.batchNo = batchNo;
        this.deviceType = deviceType;
    }

    public MsgPhone(MsgPhoneDto msgPhoneDto) {
        this.cellphoneNum = msgPhoneDto.getCellphoneNum();
        this.batchNo = msgPhoneDto.getBatchNo();
        this.deviceId = msgPhoneDto.getDeviceId();
        this.pushTime = msgPhoneDto.getPushTime();
        this.deviceType = DeviceType.getDeviceType(msgPhoneDto.getDeviceType());
        this.createTime = new Date();
        this.modifyTime = createTime;
        this.operator = MessageOperator.SYS.name();
    }

    public MsgPhone(MsgPushDto msgPushDto) {
        this.createTime = new Date();
        this.modifyTime = createTime;
        this.pushTime = msgPushDto.getPushTime();
        this.operator = MessageOperator.SYS.name();
    }

}
