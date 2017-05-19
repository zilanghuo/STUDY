package com.zdmoney.message.api.dto.sm;

import com.zdmoney.zdqd.util.StringUtils;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/11/3.
 */
@Data
@NoArgsConstructor
public class SendSmRspDto implements Serializable {

    private static final long serialVersionUID = -2653626489869971492L;
    private String respTime;
    private String respStatus;
    private String msgId;
    private SmSendStatus smSendStatus;
    private String thirdText;

    public SendSmRspDto(String respTime, String respStatus) {
        this.respTime = respTime;
        this.respStatus = respStatus;
        this.setSmSendStatus(SmSendStatus.FAIL);
    }

    public SendSmRspDto(String respTime, String respStatus, String msgId) {
        this(respTime, respStatus);
        this.msgId = msgId;
        this.setSmSendStatus(SmSendStatus.SUCCESS);
    }

    public boolean isSuccess() {
        return StringUtils.equals("0", respStatus);
    }

    public boolean isFail() {
        return !StringUtils.equals("0", respStatus);
    }

    public static SendSmRspDto FAIL() {
        SendSmRspDto sendSmRspDto = new SendSmRspDto();
        sendSmRspDto.setSmSendStatus(SmSendStatus.FAIL);
        return sendSmRspDto;
    }

}
