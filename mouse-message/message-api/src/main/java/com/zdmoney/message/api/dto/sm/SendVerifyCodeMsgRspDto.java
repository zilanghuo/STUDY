package com.zdmoney.message.api.dto.sm;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/11/3.
 */
@Data
@NoArgsConstructor
public class SendVerifyCodeMsgRspDto implements Serializable {

    private static final long serialVersionUID = 5405891122387324082L;
    private boolean isSuccess;
    private String message;
    private String respTime;
    private String respStatus;
    private String msgId;

    public SendVerifyCodeMsgRspDto(SendSmRspDto sendSmResDto) {
        this.respTime = sendSmResDto.getRespTime();
        this.respStatus = sendSmResDto.getRespStatus();
    }

}
