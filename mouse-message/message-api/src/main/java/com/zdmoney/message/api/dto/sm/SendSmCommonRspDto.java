package com.zdmoney.message.api.dto.sm;

import com.zdmoney.zdqd.util.StringUtils;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/12/12.
 */
@Data
@NoArgsConstructor
public class SendSmCommonRspDto implements Serializable {

    private static final long serialVersionUID = -135892864732142276L;

    protected String respTime;
    protected String respStatus;
    protected String msgId;
    protected SmSendStatus smSendStatus;
    private SmChannelType smChannelType;
    protected String thirdText;

    public SendSmCommonRspDto(String respTime, String respStatus, String msgId, SmSendStatus smSendStatus, String thirdText) {
        this.respTime = respTime;
        this.respStatus =respStatus;
        this.msgId = msgId;
        this.smSendStatus = smSendStatus;
        this.setThirdText(thirdText);
    }

    public SendSmCommonRspDto(String respTime, String respStatus) {
        this.respTime = respTime;
        this.respStatus = respStatus;
        this.setSmSendStatus(SmSendStatus.FAIL);
    }

    public SendSmCommonRspDto(String respTime, String respStatus, String msgId) {
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

    public static SendSmCommonRspDto FAIL() {
        SendSmCommonRspDto sendSmRspDto = new SendSmCommonRspDto();
        sendSmRspDto.setSmSendStatus(SmSendStatus.FAIL);
        return sendSmRspDto;
    }

    public void setThirdText(String thirdText){
        this.thirdText=(!StringUtils.isEmpty(thirdText)&&thirdText.length()>1000)?thirdText.substring(0,1000):thirdText;
    }

}
