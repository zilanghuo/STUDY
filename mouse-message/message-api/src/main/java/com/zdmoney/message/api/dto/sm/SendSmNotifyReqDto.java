package com.zdmoney.message.api.dto.sm;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 发送通知短信dto
 * Created by gaojc on 2016/11/12.
 */
@Data
@NoArgsConstructor
public class SendSmNotifyReqDto extends BaseSmReqDto {

    private static final long serialVersionUID = -7725794047040129756L;
    //手机号
    @NotEmpty(message = "手机号不能为空")
    private String mobile;
    //发送内容
    private String sendMsg;

    public SendSmNotifyReqDto(String mobile, String sendMsg, SmSource smSource) {
        this.mobile = mobile;
        this.sendMsg = sendMsg;
        this.smSource = smSource;
    }

    public SendSmNotifyReqDto(String mobile, String sendMsg) {
        this(mobile, sendMsg, SmSource.LCB);
    }

}
