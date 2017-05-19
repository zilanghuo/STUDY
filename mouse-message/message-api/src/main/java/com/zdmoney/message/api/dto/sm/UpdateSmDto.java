package com.zdmoney.message.api.dto.sm;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/11/11.
 */
@Data
@NoArgsConstructor
public class UpdateSmDto implements Serializable {

    private static final long serialVersionUID = -603649751964273743L;
    private SendSmReqDto sendSmReqDto;
    private SendSmRspDto sendSmRspDto;
    private SendSmType sendSmType;

    private SendVerifyCodeMsgReqDto sendVerifyCodeMsgReqDto;
    private SendVerifyCodeMsgRspDto sendVerifyCodeMsgRspDto;

    public UpdateSmDto(SendSmReqDto sendSmReqDto, SendSmRspDto sendSmRspDto) {
        this.sendSmReqDto = sendSmReqDto;
        this.sendSmRspDto = sendSmRspDto;
        this.sendSmType = SendSmType.MARKET;
    }

    public UpdateSmDto(SendSmReqDto sendSmReqDto, SendSmRspDto sendSmRspDto, SendSmType sendSmType) {
        this.sendSmReqDto = sendSmReqDto;
        this.sendSmRspDto = sendSmRspDto;
        this.sendSmType = sendSmType;
    }

    public UpdateSmDto(SendVerifyCodeMsgReqDto sendVerifyCodeMsgReqDto, SendVerifyCodeMsgRspDto sendVerifyCodeMsgRspDto) {
        this.sendVerifyCodeMsgReqDto = sendVerifyCodeMsgReqDto;
        this.sendVerifyCodeMsgRspDto = sendVerifyCodeMsgRspDto;
    }
}
