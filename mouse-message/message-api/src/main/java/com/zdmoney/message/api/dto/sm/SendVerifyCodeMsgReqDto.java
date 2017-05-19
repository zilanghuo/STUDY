package com.zdmoney.message.api.dto.sm;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 发送验证码请求
 * Created by Administrator on 2016/11/3.
 */
@Data
@NoArgsConstructor
public class SendVerifyCodeMsgReqDto extends BaseSmReqDto {

    private static final long serialVersionUID = -5568452636965393789L;

    @NotEmpty(message = "手机号不能为空")
    private String mobile;       //手机号

    @NotEmpty(message = "验证码不能为空 ")
    private String verifyCode;      //验证码

    private String sendMsg;         //内容


    public SendVerifyCodeMsgReqDto(String mobile, String verifyCode, String sendMsg) {
        this(mobile, verifyCode, sendMsg, SmSource.LCB);
    }

    public SendVerifyCodeMsgReqDto(String mobile, String verifyCode, String sendMsg, SmSource smSource) {
        this.mobile = mobile;
        this.verifyCode = verifyCode;
        this.sendMsg = sendMsg;
        this.smSource = smSource;
    }

}
