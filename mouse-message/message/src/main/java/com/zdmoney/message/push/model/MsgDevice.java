package com.zdmoney.message.push.model;

import com.zdmoney.message.api.common.dto.MessageOperator;
import com.zdmoney.message.api.dto.push.MsgDeviceCollectDto;
import com.zdmoney.message.api.dto.push.enums.DeviceType;
import com.zdmoney.message.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Table;
import java.util.Date;

/**
 * Created by admin on 2016/7/15.
 */
@Data
@NoArgsConstructor
@Table(name = "T_MSG_DEVICE")
@EqualsAndHashCode(callSuper = false)
@ToString
public class MsgDevice extends BaseEntity {
    //手机号
    private String cellphoneNum;
    //设备号
    private String deviceId;
    //设备类型
    private DeviceType deviceType;

    public MsgDevice(String phoneNum) {
        this.cellphoneNum = phoneNum;
    }

    public MsgDevice(MsgDeviceCollectDto msgDeviceCollectDto) {
        this.cellphoneNum = msgDeviceCollectDto.getCellphoneNum();
        this.deviceId = msgDeviceCollectDto.getDeviceId();
        this.deviceType = DeviceType.getDeviceType(msgDeviceCollectDto.getDeviceType());
        this.createTime = new Date();
        this.modifyTime = createTime;
        this.operator = MessageOperator.SYS.name();
    }

    public MsgDevice(MsgDeviceCollectDto msgDeviceCollectDto, Integer id) {
        this.id = id;
        this.cellphoneNum = msgDeviceCollectDto.getCellphoneNum();
        this.deviceId = msgDeviceCollectDto.getDeviceId();
        this.deviceType = DeviceType.getDeviceType(msgDeviceCollectDto.getDeviceType());
    }

    public void reset(MsgDeviceCollectDto collectDto) {
        this.deviceId = collectDto.getDeviceId();
        this.deviceType = DeviceType.getDeviceType(collectDto.getDeviceType());
        this.modifyTime = new Date();
    }
}
