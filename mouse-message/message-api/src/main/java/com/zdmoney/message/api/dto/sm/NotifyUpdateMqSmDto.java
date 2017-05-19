package com.zdmoney.message.api.dto.sm;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/11/12.
 */
@Data
@NoArgsConstructor
public class NotifyUpdateMqSmDto implements Serializable {

    private static final long serialVersionUID = 7926364630519911988L;
    private SendSmCommonReqDto sendSmCommonReqDto;
    private SendSmCommonRspDto sendSmCommonRspDto;
    private SendSmType sendSmType;

    public NotifyUpdateMqSmDto(SendSmCommonReqDto sendSmReqDto, SendSmCommonRspDto sendSmRspDto, SendSmType sendSmType) {
        this.sendSmCommonReqDto = sendSmReqDto;
        this.sendSmCommonRspDto = sendSmRspDto;
        this.sendSmType = sendSmType;
    }

    @Override
    public String toString() {
        return "NotifyUpdateMqSmDto{" +
                "sendSmCommonReqDto=" + sendSmCommonReqDto +
                ", sendSmCommonRspDto=" + sendSmCommonRspDto +
                ", sendSmType=" + sendSmType +
                '}';
    }
}
