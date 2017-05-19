package com.zdmoney.message.api.dto.sm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by Administrator on 2016/11/3.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerifyCodeReqDto implements Serializable {
    private static final long serialVersionUID = 7363947068794566660L;
    @NotNull(message = "手机号不能为空")
    private String mobile;
    @NotNull(message = "验证码不能为空")
    private String verifyCode;
}
